package com.ayu.growing.datasource;

import com.ayu.growing.datasource.dbconfig.DSName;
import com.ayu.growing.datasource.dsconfig.BaseDS;
import com.ayu.growing.datasource.shardingconfig.OpenPayAndOpenPay2Sharding;
import com.ayu.growing.datasource.shardingconfig.PayMngSharding;
import com.ayu.growing.datasource.shardingconfig.PaySharding;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfig {

    @Autowired
    private BaseDS baseDS;

    @Autowired
    private PaySharding paySharding;

    @Autowired
    private OpenPayAndOpenPay2Sharding openPayAndOpenPay2Sharding;

    @Autowired
    private PayMngSharding payMngSharding;

    @Bean(name = "dynamicDS")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        dynamicDataSource.setDefaultTargetDataSource(baseDS.openPaySource());

        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put(DSName.openPay, baseDS.openPaySource());
        dsMap.put(DSName.openPaySalve, baseDS.openPaySlaveSource());

        dsMap.put(DSName.shield, baseDS.shieldSource());
        dsMap.put(DSName.shieldSalve, baseDS.shieldSlaveSource());
        dsMap.put(DSName.openPay2, baseDS.openPay2Source());
        dsMap.put(DSName.pay, baseDS.paySource());
        dsMap.put(DSName.paySalve, paySharding.payShardingSlaveSource());

        //dsMap.put(DSName.openPayOpenPay2, openPayAndOpenPay2Sharding.openPayAndOpenPay2ShardingSource());
        dsMap.put(DSName.openPayOpenPay2Slave, openPayAndOpenPay2Sharding.openPayAndOpenPay2SlaveShardingSource());

        dsMap.put(DSName.openPayMng, payMngSharding.payShardingSlaveSource());
        dsMap.put(DSName.openPayMngWithOutSharding,baseDS.openPayMngSource());
        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate jtaIncomeSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
