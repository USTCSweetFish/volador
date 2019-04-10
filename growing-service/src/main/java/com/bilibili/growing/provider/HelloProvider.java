package com.bilibili.growing.provider;

import com.bilibili.growing.constant.DbConstant;
import com.bilibili.growing.domain.SqlProviderHelper;
import com.bilibili.growing.domain.Tuple;
import com.bilibili.growing.entity.PayOrderEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelloProvider {

    public String execSql(Map map) {
        String sql = (String) map.get("sql");
        return sql;
    }

    public String orderUpdateSql(Map map) {
        PayOrderEntity payOrderEntity = (PayOrderEntity) map.get("item");
        Long txId = (Long) map.get("txId");
        payOrderEntity.setTxId(txId);
        List<Tuple> tupleList = new ArrayList<Tuple>() {
            {
                add(new Tuple("tx_id", "#{txId}"));
                add(new Tuple("is_deleted", DbConstant.DELETED_FLAG_NORMAL.toString()));
            }
        };

        return SqlProviderHelper.updateHelper(payOrderEntity, "pay_order", tupleList);
    }

    public String orderCloseSql(Map map) {
        return "";
    }

    public String findByPayChannelOrderno(Map map) {
        return "";
    }

    public String findByTxIdsOrOrderIds (Map map) {
        String txIds = map.get("txIds") != null ? map.get("txIds").toString() : null;
        String orderIds = map.get("orderIds") != null ? (String)map.get("orderIds") : null;
        List<String> dedicateOrderIds = new ArrayList<>();
        if (StringUtils.isNotBlank(orderIds)) {
            for (String orderId: orderIds.split(",")) {
                dedicateOrderIds.add(String.format("\'%s\'", orderId));
            }
        }
        List<Tuple> tupleList = new ArrayList<Tuple>() {
            {

                if (StringUtils.isNotBlank(txIds)) {
                    add(new Tuple(String.format("tx_id in (%s)", txIds), null));
                }else if (CollectionUtils.isNotEmpty(dedicateOrderIds)) {
                    add(new Tuple(String.format("order_id in (%s)", StringUtils.join(dedicateOrderIds, ",")), null));
                }
                add(new Tuple("customer_id", "#{customerId}"));
                add(new Tuple("is_deleted", DbConstant.DELETED_FLAG_NORMAL.toString()));
            }
        };
        return SqlProviderHelper.selectHelper(PayOrderEntity.class, "pay_order" , tupleList, null);
    }

    public String orderInsertSql(Map map) {
        PayOrderEntity payOrderEntity = (PayOrderEntity) map.get("item");
        return SqlProviderHelper.insertHelper(payOrderEntity, "pay_order");
    }

}
