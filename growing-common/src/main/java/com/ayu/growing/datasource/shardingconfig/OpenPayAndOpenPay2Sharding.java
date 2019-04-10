package com.ayu.growing.datasource.shardingconfig;


import com.ayu.growing.datasource.dbconfig.DSName;
import com.ayu.growing.datasource.dsconfig.BaseDS;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * open_pay与open_pay2 sharding配置
 */
@Configuration
public class OpenPayAndOpenPay2Sharding {

    @Autowired
    private BaseDS baseDS;

    //open_pay open_pay2  从库分库
    @Bean(name = "openPayAndOpenPay2SlaveShardingSource")
    public DataSource openPayAndOpenPay2SlaveShardingSource() {
        try {
            return ShardingDataSourceFactory.createDataSource(openPayShardingRule(openPayDataSourceRule()));
        } catch (SQLException e) {
            return null;
        }
    }

    @Bean(name = "openPayDataSourceRule")
    public DataSourceRule openPayDataSourceRule() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DSName.openPaySalve, baseDS.openPaySlaveSource());
        dataSourceMap.put(DSName.openPay2Salve, baseDS.openPay2SlaveSource());
        return new DataSourceRule(dataSourceMap, DSName.openPaySalve);
    }

    //对于openPay和openPay2进行分库操作（如果目前规则不满足需求，请重新进行定义规则，返回另一个ShardingRule，让sharding-jdbc
    // 维护多个规则的分库分表）
    @Bean(name = "openPayShardingRule")
    public ShardingRule openPayShardingRule(@Qualifier("openPayDataSourceRule") DataSourceRule dataSourceRule) {

        //涉及到的  需要分库的表，必须进行定义；
        TableRule payOrderTableRule = TableRule.builder("pay_order")//逻辑表名
                .actualTables(Collections.singletonList("pay_order")) //物理表名，因为没有进行分表操作，所以逻辑表名与物理表名一样
                .dataSourceRule(dataSourceRule)//表示在两个数据源DbName.openPay  DSName.openPay2中进行分库
                .build();

        TableRule payRefundTableRule = TableRule.builder("pay_refund")
                .actualTables(Collections.singletonList("pay_refund"))
                .dataSourceRule(dataSourceRule)
                .build();


        TableRule payBatchRefundRule = TableRule.builder("pay_batch_refund")
                .actualTables(Collections.singletonList("pay_batch_refund"))
                .dataSourceRule(dataSourceRule)
                .build();

        TableRule payOrderExtRule = TableRule.builder("pay_order_ext")
                .actualTables(Collections.singletonList("pay_order_ext"))
                .dataSourceRule(dataSourceRule)
                .build();

        //创建sharding规则，根据两个数据源DbName.openPay  DSName.openPay2进行sharding操作，影响的表由tableRules方法指定；
        //分库的规则是databaseShardingStrategy方法指定，如果在进行sql操作时，没有txid，这次操作会进入默认的数据库进行操作；如果有txid，则触发
        //分库规则，使sql到特定的数据库进行操作；
        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(payOrderTableRule, payRefundTableRule, payBatchRefundRule, payOrderExtRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("tx_id", new SingleKeyDataBaseShardingAlg()))
                //这里指定了分库规则为txid，如果query时没有txid 则会进行聚合查询
                //.bindingTableRules(bindingTableRules)
                //.tableShardingStrategy(new TableShardingStrategy("mid", new SingleKeyShardingAlg()))
                .build();
    }
}
