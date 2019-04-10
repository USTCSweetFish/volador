package com.ayu.growing.domain;

import com.ayu.growing.annotation.SqlKey;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SqlProviderHelper {
    public final static String SQL_HELPER_ITEM_KEY = "item";

    public static String genKey(String key) {
        return String.format("%s.%s", SqlProviderHelper.SQL_HELPER_ITEM_KEY, key);
    }

    public static String genItemKey(String key) {
        return String.format("#{%s}", genKey(key));
    }

    public static String buildUpdatePhase(String sqlKey, String key) {
        return String.format("%s = #{%s.%s}", sqlKey, SqlProviderHelper.SQL_HELPER_ITEM_KEY, key);
    }

    public static String insertHelper(Object entity, String tableName) {
        return insertHelper(entity, tableName, true, false);
    }

    public static String insertIgnoreUniqueHelper(Object entity, String tableName) {
        return insertHelper(entity, tableName, true, true);
    }

    public static String updateHelper(Object entity, String tableName, List<Tuple> whereBlock) {
        return updateHelper(entity, tableName, whereBlock, true);
    }

    public static String updateHelper(Object entity, String tableName, List<Tuple> whereCause ,boolean ignoreNull) {
        List<Tuple> tuples = getAllFields(entity, ignoreNull);
        List<String> updateList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");
        for (Tuple tuple: tuples) {
            if(0 == "version".compareTo(tuple.getValue())) {
                updateList.add("`version` = `version` + 1");
            } else {
                updateList.add(String.format("%s = %s", tuple.getKey(), genItemKey(tuple.getValue())));
            }
        }
        sb.append(StringUtils.join(updateList, ", "));

        if (whereCause != null) {
            sb.append(buildWhereCause(whereCause));
        }
        return sb.toString();
    }

    public static String insertHelper(Object entity, String tableName, boolean ignoreNull, boolean ignoreUnique) {
        List<Tuple> tuples = getAllFields(entity, ignoreNull);

        List<String> keyList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();

        for (Tuple tuple: tuples) {
            keyList.add(tuple.getKey());
            valueList.add(genItemKey(tuple.getValue()));
        }

        String insert = ignoreUnique ? "INSERT IGNORE INTO " : "INSERT INTO ";
        StringBuilder sb = new StringBuilder();
        sb.append(insert);
        sb.append(tableName);
        sb.append("(");
        sb.append(StringUtils.join(keyList, ","));
        sb.append(") VALUES (");
        sb.append(StringUtils.join(valueList, ","));
        sb.append(");");
        return sb.toString();
    }

    public static String deleteHelper(String tableName, List<Tuple> whereCause) {
        List<String> updateList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(tableName);

        if (whereCause != null) {
            sb.append(buildWhereCause(whereCause));
        }
        return sb.toString();
    }

    public static String selectHelper(Object entity, String tableName,
                                      List<Tuple> whereCause, Set<String> specKeys) {
        return selectHelper(entity, tableName, whereCause, specKeys, null);
    }

    public static String selectHelper(Object entity, String tableName,
                                      List<Tuple> whereCause, Set<String> specKeys, String appends) {
        List<Tuple> tuples = getAllFields(entity, false);
        StringBuilder sb = new StringBuilder();
        List<String> keyList = new ArrayList<>();
        for (Tuple tuple: tuples) {
            if (specKeys == null || specKeys.contains(tuple.getValue())) {
                keyList.add(String.format("%s as %s", tuple.getKey(), tuple.getValue()));
            }
        }
        sb.append("SELECT ");
        sb.append(StringUtils.join(keyList, ","));
        sb.append(" FROM ");
        sb.append(tableName);
        if (whereCause != null) {
            sb.append(buildWhereCause(whereCause));
        }
        if (appends != null) {
            sb.append(" ");
            sb.append(appends);
        }
        return sb.toString();
    }

    private static String buildWhereCause(List<Tuple> whereCasue) {
        List<String> whereList = new ArrayList<>();
        for (Tuple tuple: whereCasue) {
            if (tuple.getKey() != null) {
                if (tuple.getValue() != null) {
                    whereList.add(String.format("%s = %s", tuple.getKey(), tuple.getValue()));
                } else {
                    whereList.add(tuple.getKey());
                }
            }
        }
        if (whereList.size() > 0) {
            return "  WHERE  " + StringUtils.join(whereList, " and ");
        } else {
            return "";
        }
    }

    private static List<Tuple> getAllFields (Object entity, boolean ignoreNull) {
        List<Tuple> tuples = new ArrayList<>();
        Field[] fields;
        if (entity instanceof Class) {
            fields = ((Class) entity).getDeclaredFields();
        } else {
            fields = entity.getClass().getDeclaredFields();
        }
        try {
            Field.setAccessible(fields, true);
            for (Field field: fields) {
                SqlKey sqlKey = field.getDeclaredAnnotation(SqlKey.class);
                if (sqlKey != null) {
                    if (!ignoreNull || field.get(entity) != null) {
                        Tuple tuple = new Tuple(String.format("`%s`", sqlKey.value()),
                                String.format("%s", field.getName()));
                        tuples.add(tuple);
                    }
                }
            }
        } catch (Exception e) {
        }
        return tuples;
    }

    public static String getAppends(String orderBy, Integer currentPage, Integer pageSize){
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(orderBy)) {
            sb.append(" order by ").append(orderBy).append(" ");

        }
        if (currentPage != null && pageSize != null) {
            sb.append(" limit ").append((currentPage - 1) * pageSize).append(",").append(pageSize);
        }
        return sb.toString();
    }
}
