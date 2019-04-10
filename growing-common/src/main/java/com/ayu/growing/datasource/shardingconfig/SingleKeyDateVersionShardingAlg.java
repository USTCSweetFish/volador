package com.ayu.growing.datasource.shardingconfig;

import com.ayu.growing.constant.ShardingConstant;
import com.ayu.growing.constant.TimeConstant;
import com.ayu.growing.exception.ExceptionEnum;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SingleKeyDateVersionShardingAlg implements SingleKeyTableShardingAlgorithm<String> {

    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        DateTime time = TimeConstant.payMngPayHourDateVersionFormatter.parseDateTime(shardingValue.getValue());
        int tableIndex = time.getHourOfDay() % ShardingConstant.payMngPayHourDataTableCount;

        String logicTableName = shardingValue.getLogicTableName();
        return getRealTableName(collection, String.format("%s%s", logicTableName, tableIndex));
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        throw ExceptionEnum.INTERNAL_ERROR.createException("表pay_hour_data表不支持 date_version in ()操作");
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Set<String> physicalTables = new HashSet();
        String logicTable = shardingValue.getLogicTableName();
        String lowerEndpoint = shardingValue.getValueRange().lowerEndpoint();
        String upperEndpoint = shardingValue.getValueRange().upperEndpoint();

        DateTime timeStart = TimeConstant.payMngPayHourDateVersionFormatter.parseDateTime(lowerEndpoint);
        DateTime timeEnd = TimeConstant.payMngPayHourDateVersionFormatter.parseDateTime(upperEndpoint);

        if (timeStart.isBefore(timeEnd)) {
            Period p = new Period(timeStart, timeEnd, PeriodType.hours());
            if (p.getHours() < 12) {
                physicalTables.add(this.getRealTableName(collection, String.format("%s%s", logicTable, timeStart.getHourOfDay() % ShardingConstant.payMngPayHourDataTableCount)));
                timeStart = timeStart.plusHours(1);
                while (!timeStart.isEqual(timeEnd)){
                    int needSuffix = timeStart.getHourOfDay() % ShardingConstant.payMngPayHourDataTableCount;
                    physicalTables.add(this.getRealTableName(collection, String.format("%s%s", logicTable, needSuffix)));
                    timeStart = timeStart.plusHours(1);
                }
                physicalTables.add(String.format("%s%s", "tableName", timeEnd.getHourOfDay() % ShardingConstant.payMngPayHourDataTableCount));
            }
            else {
                for (int i = 0; i < ShardingConstant.payMngPayHourDataTableCount; i++) {
                    physicalTables.add(this.getRealTableName(collection, String.format("%s%s", logicTable, i)));
                }
            }

        } else if (timeStart.isEqual(timeEnd)) {
            int needSuffix = timeStart.getHourOfDay() % ShardingConstant.payMngPayHourDataTableCount;
            physicalTables.add(this.getRealTableName(collection, String.format("%s%s", logicTable, needSuffix)));
        }


        return physicalTables;
    }

    private String getRealTableName(Collection<String> usableTables, String queryTableName) {

        Optional<String> result = usableTables.stream()
                .filter(it ->
                        it.equals(queryTableName)
                ).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw ExceptionEnum.SHARDING_CONFIG_BAD.createException();
    }
}
