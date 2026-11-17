package com.example.sms.cmm.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * iBATIS DAO 공통 부모
 *
 * 스프링 트랜잭션과 같은 커넥션을 쓰도록 DataSourceUtils 로 커넥션을 받아서 넘긴다.
 * 문 id 는 "네임스페이스.문id" 문자열 (예: code.selectCodeList)
 */
public abstract class AbstractIbatisDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Autowired
    private DataSource dataSource;

    protected Object queryForObject(String stmtId, Object param) {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            sqlMapClient.setUserConnection(conn);
            return sqlMapClient.queryForObject(stmtId, param);
        } catch (SQLException e) {
            throw new RuntimeException("iBATIS 조회 오류 : " + stmtId, e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    protected List<?> queryForList(String stmtId, Object param) {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            sqlMapClient.setUserConnection(conn);
            return sqlMapClient.queryForList(stmtId, param);
        } catch (SQLException e) {
            throw new RuntimeException("iBATIS 목록 조회 오류 : " + stmtId, e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    protected Object insert(String stmtId, Object param) {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            sqlMapClient.setUserConnection(conn);
            return sqlMapClient.insert(stmtId, param);
        } catch (SQLException e) {
            throw new RuntimeException("iBATIS 등록 오류 : " + stmtId, e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    protected int update(String stmtId, Object param) {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            sqlMapClient.setUserConnection(conn);
            return sqlMapClient.update(stmtId, param);
        } catch (SQLException e) {
            throw new RuntimeException("iBATIS 수정 오류 : " + stmtId, e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    protected int delete(String stmtId, Object param) {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            sqlMapClient.setUserConnection(conn);
            return sqlMapClient.delete(stmtId, param);
        } catch (SQLException e) {
            throw new RuntimeException("iBATIS 삭제 오류 : " + stmtId, e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }
}
