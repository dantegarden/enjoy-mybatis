package com.enjoy.myorm.orm.executor.resultset;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 暂存结果集信息
 * @author: lij
 * @create: 2019-10-05 23:49
 */
@Data
public class ResultSetContext {
    private Map<String,Object> resultColumn = new HashMap<>();

    public void handleResultColumn(ResultSet rs){
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = rsmd.getColumnName(i + 1);
                Object columnValue = rs.getObject(columnName);
                resultColumn.put(columnName, columnValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumn(String column){
        return this.resultColumn.containsKey(column);
    }

    public Object getColumn(String column){
        return this.resultColumn.get(column);
    }

}
