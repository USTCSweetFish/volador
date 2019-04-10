package com.ayu.growing.datasource.shardingconfig;

import com.ayu.growing.datasource.dsconfig.BaseDS;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pay库sharidng配置
 */
@Configuration
public class PaySharding {

    @Autowired
    private BaseDS baseDS;

    //pay从库 分表
    @Bean(name = "payShardingSlaveSource")
    public DataSource payShardingSlaveSource() {
        try {
            return ShardingDataSourceFactory.createDataSource(payShardingRule(payDataSourceRule(baseDS.paySlaveSource())));
        } catch (SQLException e) {
            return null;
        }
    }

    @Bean(name = "payShardingRule")
    public ShardingRule payShardingRule(@Qualifier("payDataSourceRule") DataSourceRule dataSourceRule) {

        //老支付b币充值表
        List<String> payRechargeOrderTables = new ArrayList<>();
        //新支付b币充值表
        List<String> walletOrderTables = new ArrayList<>();
        //新支付b币充值扩展表
        List<String> walletOrderExtTables = new ArrayList<>();
        //新支付b币券领取表
        List<String> userCouponTables = new ArrayList<>();
        for (int i = 0; i <= 19; i++) {
            payRechargeOrderTables.add(String.format("pay_recharge_order%s", i));
            walletOrderTables.add(String.format("wallet_order%s", i));
            walletOrderExtTables.add(String.format("wallet_order_ext%s", i));
            userCouponTables.add(String.format("pay_user_coupon%s", i));
        }

        //老支付b币充值表 表策略
        TableRule payRechargeOrderRule = TableRule.builder("pay_recharge_order")
                .actualTables(payRechargeOrderTables)
                .tableShardingStrategy(new TableShardingStrategy("recharge_mid", new SingleKeyShardingAlg()))
                .dataSourceRule(dataSourceRule)
                .build();
        //老支付b币充值表 表策略
        TableRule walletOrderTableRule = TableRule.builder("wallet_order")
                .actualTables(walletOrderTables)
                .tableShardingStrategy(new TableShardingStrategy("mid", new SingleKeyShardingAlg()))
                .dataSourceRule(dataSourceRule)
                .build();
        TableRule walletOrderExtTableRule = TableRule.builder("wallet_order_ext")
                .actualTables(walletOrderExtTables)
                .tableShardingStrategy(new TableShardingStrategy("mid", new SingleKeyShardingAlg()))
                .dataSourceRule(dataSourceRule)
                .build();


        TableRule payUserCouponRule = TableRule.builder("pay_user_coupon")
                .actualTables(userCouponTables)
                .tableShardingStrategy(new TableShardingStrategy("mid", new SingleKeyShardingAlg()))
                .dataSourceRule(dataSourceRule)
                .build();
        //绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
        //List<BindingTableRule> bindingTableRules = new ArrayList<BindingTableRule>();
        //bindingTableRules.add(new BindingTableRule(Arrays.asList(orderTableRule, orderItemTableRule)));
        List<TableRule> rules = new ArrayList<>();
        rules.add(payRechargeOrderRule);
        rules.add(walletOrderTableRule);
        rules.add(walletOrderExtTableRule);
        rules.add(payUserCouponRule);
        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(rules)
                //.bindingTableRules(bindingTableRules)
                //.tableShardingStrategy(new TableShardingStrategy("mid", new SingleKeyShardingAlg()))
                .build();
    }

    @Bean(name = "payDataSourceRule")
    public DataSourceRule payDataSourceRule(@Qualifier("paySlaveSource") DataSource paySource) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("paySalveSource", paySource);
        return new DataSourceRule(dataSourceMap, "paySlaveSource");
    }


}
