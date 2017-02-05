package org.dfw.spark.core.mybatis;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * MyBatis默认类型处理器
 */
public class DefaultTypeHandler {

    /**
     * boolean -> TRUE | FALSE
     */
    @MappedTypes(Boolean.class)
    public static class BooleanHandler implements TypeHandler<Boolean> {
        static final String TRUE = "TRUE";
        static final String FALSE = "FALSE";

        /**
         * db 2 java
         */
        private static boolean boolean4db2java(String bool) {
            return TRUE.equals(bool);
        }

        /**
         * java 2 db
         */
        private static String boolean4java2db(Boolean bool) {
            if (bool == null) {
                return null;
            } else {
                if (bool) {
                    return TRUE;
                } else {
                    return FALSE;
                }
            }
        }

        @Override
        public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, boolean4java2db(parameter));
        }

        @Override
        public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
            return boolean4db2java(rs.getString(columnName));
        }

        @Override
        public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
            return boolean4db2java(rs.getString(columnIndex));
        }

        @Override
        public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
            return boolean4db2java(cs.getString(columnIndex));
        }
    }

    /**
     * List <-> JSON
     */
    @MappedTypes({List.class, ArrayList.class, LinkedList.class, Vector.class})
    public static class ListTypeHandler implements TypeHandler<List> {

        @Override
        public void setParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, JSON.toJSONString(parameter));
        }

        @Override
        public List getResult(ResultSet rs, String columnName) throws SQLException {
            return JSON.parseArray(rs.getString(columnName));
        }

        @Override
        public List getResult(ResultSet rs, int columnIndex) throws SQLException {
            return JSON.parseArray(rs.getString(columnIndex));
        }

        @Override
        public List getResult(CallableStatement cs, int columnIndex) throws SQLException {
            return JSON.parseArray(cs.getString(columnIndex));
        }
    }
}