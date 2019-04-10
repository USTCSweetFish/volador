package com.ayu.growing.datasource.shardingconfig;


import com.ayu.growing.constant.ShardingConstant;
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

@Configuration
public class PayMngSharding {
    @Autowired
    private BaseDS baseDS;

    //pay从库 分表
    @Bean(name = "payMngShardingMainSource")
    public DataSource payShardingSlaveSource() {
        try {
            return ShardingDataSourceFactory.createDataSource(payMngShardingMainRule(payMngShardingDataSourceRule(baseDS.openPayMngSource())));
        } catch (SQLException e) {
            return null;
        }
    }

    @Bean(name = "payMngShardingMainRule")
    public ShardingRule payMngShardingMainRule(@Qualifier("payMngShardingDataSourceRule") DataSourceRule dataSourceRule) {

        List<String> payHourDataTables = new ArrayList<>();

        for (int i = 0; i <= ShardingConstant.payMngPayHourDataTableCount-1; i++) {
            payHourDataTables.add(String.format("pay_hour_data%s", i));
        }
        TableRule payHourDataTablesRule = TableRule.builder("pay_hour_data")
                .actualTables(payHourDataTables)
                .tableShardingStrategy(new TableShardingStrategy("date_version", new SingleKeyDateVersionShardingAlg()))
                .dataSourceRule(dataSourceRule)
                .build();

        List<TableRule> rules = new ArrayList<>();
        rules.add(payHourDataTablesRule);

        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(rules)
                .build();
    }

    @Bean(name = "payMngShardingDataSourceRule")
    public DataSourceRule payMngShardingDataSourceRule(@Qualifier("openPayMngSource") DataSource paySource) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("openPayMngSource", paySource);
        return new DataSourceRule(dataSourceMap, "openPayMngSource");
    }
}
