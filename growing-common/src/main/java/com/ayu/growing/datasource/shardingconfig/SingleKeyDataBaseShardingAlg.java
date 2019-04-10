package com.ayu.growing.datasource.shardingconfig;

import com.ayu.growing.datasource.dbconfig.DSName;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * tx_id以4开头，落到open_pay2库，其他落到open_pay库
 */
public class SingleKeyDataBaseShardingAlg implements SingleKeyDatabaseShardingAlgorithm<Long> {

    private final List<String> openPayTxIdStartWith = Arrays.asList("1", "2", "3", "9");//open_pay库对应的tx_id开头数字

    private final List<String> legalTxIdStartWith = Arrays.asList("1", "2", "3", "4", "9");//open_pay库对应的tx_id开头数字

    /**
     * equals  tx_id=
     *
     * @param dataSourceNames
     * @param txId
     * @return
     */
    @Override
    public String doEqualSharding(Collection<String> dataSourceNames, ShardingValue<Long> txId) {

        Long tx_id = txId.getValue();
        checkTxId(tx_id);
        String startChar = Character.toString(String.valueOf(tx_id).charAt(0));

        for (String dsName : dataSourceNames) {
            if (dsName.equals(DSName.openPay2Salve) && startChar.equals("4")) {
                return DSName.openPay2Salve;
            } else if (dsName.equals(DSName.openPaySalve) && openPayTxIdStartWith.contains(startChar)) {
                return DSName.openPaySalve;
            }
        }
        throw new IllegalArgumentException("不支持的分库规则！");
    }

    /**
     * in  tx_id in()
     *
     * @param dataSourceNames
     * @param txIds
     * @return
     */
    @Override
    public Collection<String> doInSharding(Collection<String> dataSourceNames, ShardingValue<Long> txIds) {

        Set<String> result = new HashSet<>();

        for (Long txId : txIds.getValues()) {

            checkTxId(txId);

            String startChar = Character.toString(String.valueOf(txId).charAt(0));//txId以 startChar开头

            for (String dsName : dataSourceNames) {

                if (dsName.equals(DSName.openPay2Salve) && startChar.equals("4")) {
                    result.add(dsName);
                } else if (dsName.equals(DSName.openPaySalve) && openPayTxIdStartWith.contains(startChar)) {
                    result.add(dsName);
                }
            }

        }
        return result;

    }

    /**
     * between tx_id between 1 and 20   这种操作不存在
     *
     * @param dataSourceNames
     * @param txIdRange
     * @return
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> dataSourceNames, ShardingValue<Long> txIdRange) {
        return new HashSet<>();
    }

    private void checkTxId(Long txId) {
        if (txId == null || txId.toString().length() > 20 || (!legalTxIdStartWith.contains(Character.toString(String.valueOf(txId).charAt(0))))) {
            throw new InvalidParameterException("txid以1、2、3、4、9开头illegal TxId=" + txId);
        }
    }
}
