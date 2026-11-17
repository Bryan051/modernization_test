package com.example.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 영업관리시스템(SMS) 메인 클래스
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.03.07   홍길동      최초 생성 (web.xml + XML 설정)
 *  2019.08.22   홍길동      Spring Boot 1.5 로 이전 (XML 설정은 그대로 읽음)
 *  2022.05.30   김철수      스케줄 작업 추가 (@EnableScheduling)
 * </pre>
 */
@SpringBootApplication
@EnableScheduling
@ImportResource({
        "classpath:spring/context-transaction.xml",
        "classpath:spring/context-ibatis.xml"
})
public class SmsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SmsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
