package com.example.sms.cmm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 스프링 컨텍스트 보관용 (static 코드에서 빈을 꺼낼 때 사용)
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.applicationContext = context;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext 가 아직 설정되지 않았습니다.");
        }
        return applicationContext.getBean(beanName);
    }
}
