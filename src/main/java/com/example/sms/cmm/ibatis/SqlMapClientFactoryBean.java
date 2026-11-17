package com.example.sms.cmm.ibatis;

import java.io.InputStream;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * iBATIS SqlMapClient 팩토리 빈
 *
 * Spring 4 부터는 iBATIS 지원 클래스(SqlMapClientFactoryBean)가 빠져서 직접 만들었다.
 * (2019.08 홍길동)
 */
public class SqlMapClientFactoryBean implements FactoryBean<SqlMapClient>, InitializingBean {

    private Resource configLocation;

    private SqlMapClient sqlMapClient;

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (configLocation == null) {
            throw new IllegalArgumentException("configLocation 은 필수입니다.");
        }
        InputStream is = configLocation.getInputStream();
        try {
            this.sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(is);
        } finally {
            is.close();
        }
    }

    @Override
    public SqlMapClient getObject() throws Exception {
        return sqlMapClient;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlMapClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
