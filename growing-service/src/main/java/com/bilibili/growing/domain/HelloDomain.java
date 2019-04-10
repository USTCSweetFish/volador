package com.bilibili.growing.domain;

import com.bilibili.growing.entity.PayOrderEntity;
import com.bilibili.growing.provider.HelloProvider;
import org.apache.ibatis.annotations.*;
@Mapper
public interface HelloDomain {

//    @SelectProvider(type = HelloProvider.class, method = "findByPayChannelOrderno")
//    PayOrderEntity findByPayChannelOrderno(@Param("orderNo") Long orderNo,
//                                           @Param("channelId") Integer channelId);


    @InsertProvider(type = HelloProvider.class, method = "orderInsertSql")
    @Options(useGeneratedKeys = true, keyProperty = SqlProviderHelper.SQL_HELPER_ITEM_KEY + ".id")
    Integer insertOrder(@Param("item") PayOrderEntity orderEntity);

    @UpdateProvider(type = HelloProvider.class, method = "orderUpdateSql")
    Integer updateOrder(@Param("item") PayOrderEntity orderEntity,
                        @Param("txId") Long txId);

//    @UpdateProvider(type = HelloProvider.class, method = "orderCloseSql")
//    Integer closeOrder(@Param("item") PayOrderEntity payOrderEntity);

    @InsertProvider(type = HelloProvider.class, method = "execSql")
    Integer insertSql(@Param("sql") String sql);

    @UpdateProvider(type = HelloProvider.class, method = "execSql")
    Integer updateSql(@Param("sql") String sql);

    @SelectProvider(type = HelloProvider.class, method = "execSql")
    Object selectSql(@Param("sql") String sql);
}
