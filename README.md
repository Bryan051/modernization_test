# SMS (영업관리시스템)

## 1. 시스템 소개

영업팀이 거래처 · 영업기회 · 견적 · 수주 · 계약 · 청구 · 수금까지 한 곳에서 관리하는 사내 웹 시스템입니다.
2016년에 구축한 뒤 고객 요청에 따라 기능을 덧붙여 가며 운영하고 있습니다.
영업사원은 본인 담당 거래처 위주로, 관리자는 전체 데이터를 조회합니다.

## 2. 모듈 목록

| 모듈 | 패키지 | 내용 |
|---|---|---|
| 거래처 | `com.example.sms.cust` | 거래처 · 거래처 담당자 관리, 여신상태 조회 |
| 영업기회 | `com.example.sms.oppty` | 영업기회 등록, 단계 관리 |
| 견적 | `com.example.sms.quote` | 견적서 작성 · 제출, 품목별 단가 적용 |
| 수주 | `com.example.sms.order` | 수주 등록, 수주 완료 처리 |
| 계약 | `com.example.sms.contract` | 계약 조회 · 금액 변경 · 계약서 첨부 |
| 청구 | `com.example.sms.bill` | 청구(세금계산서) 발행, 전자세금계산서 연동 |
| 수금 | `com.example.sms.collect` | 수금 등록, 미수 현황 |
| 제품 | `com.example.sms.product` | 제품 · 기간별 단가 관리 |
| 사원 | `com.example.sms.user` | 사원 · 부서 · 권한, 로그인 |
| 공통 | `com.example.sms.cmm` | 공통코드 · 공지사항, 메일 · 엑셀 · 첨부 · 날짜/금액 유틸 |
| 리포트 | `com.example.sms.report` | 매출 집계, 대시보드 |
| 배치 | `com.example.sms.batch` | 미수금 안내 메일, 월마감 |

각 모듈은 `web`(컨트롤러) / `service`(서비스 인터페이스 · VO) / `service.impl`(구현 · 매퍼 · DAO) 패키지로 나뉘어 있습니다.
화면 URL 은 `*.do` 형식이며 JSP 는 `src/main/webapp/WEB-INF/jsp/sms/<모듈>/` 아래에 있습니다.

## 3. 기술 구성

- Java 1.8, Spring Boot 1.5.22 (war), Spring MVC + JSP/JSTL + jQuery 1.x
- DB : MySQL 5.7 (Connector/J 5.1.49), 스키마는 `src/main/resources/db/schema.sql`
- SQL 매핑
  - MyBatis 3 (`mybatis-spring-boot-starter` 1.3.2) : `src/main/resources/mapper/<모듈>/*_SQL.xml`
  - iBATIS 2.3.4 : 공통코드 · 공지사항 (`src/main/resources/sqlmap/`)
- 트랜잭션 : `src/main/resources/spring/context-transaction.xml` (AOP)
- 엑셀 Apache POI 3.17, 메일 JavaMail(spring-boot-starter-mail)

## 4. 구축 · 변경 이력

| 시기 | 내용 |
|---|---|
| 2016.03 ~ 2016.09 | 1차 구축 : 사원 · 거래처 · 제품 · 영업기회 · 견적 · 수주 · 계약 · 청구 (Spring 4.1 + XML 설정 + iBATIS 2, Tomcat 8) |
| 2017.03 | 수금 · 미수 관리 추가 |
| 2017.04 | 트랜잭션을 AOP 방식으로 변경 |
| 2019.08 | Spring Boot 1.5.22 로 이전 (war 배포 유지). 거래처 · 영업기회 · 견적 · 수주 · 계약 · 청구 · 수금 · 제품 · 사원 모듈을 iBATIS 에서 MyBatis 로 이전, 공통코드 · 공지사항은 iBATIS 유지 |
| 2019.11 | 전자세금계산서 발행 연동, 수주 완료 처리 추가 (고객 요청) |
| 2020.02 | 제품 기간별 단가 도입 |
| 2021.03 | 매출 리포트 · 대시보드 추가 |
| 2022.05 | 배치 추가 (미수금 안내 메일, 월마감) |

## 5. 실행 방법

1. MySQL 에 `sms` 스키마를 만들고 `src/main/resources/db/schema.sql` 을 실행합니다.
2. 실행 환경에 아래 환경변수를 설정합니다. (값은 환경에 맞게 넣습니다)

   ```
   export DB_URL="jdbc:mysql://<DB 호스트>:3306/sms?useUnicode=true&characterEncoding=utf8"
   export DB_USER="<DB 사용자>"
   export DB_PASSWORD="<DB 비밀번호>"
   export MAIL_HOST="<메일 서버>"
   ```

3. 빌드 : `mvn clean package` (결과물 `target/sms.war`)
4. 실행 : Tomcat 8.5 의 `webapps` 에 `sms.war` 를 배포하거나 `java -jar target/sms.war`
5. 접속 : `http://localhost:8080/sms/` (로그인 후 대시보드로 이동). 최초 관리자 계정은 `TB_USER` 에 직접 등록합니다.

js · css 정적 파일(`/js`, `/css`)은 별도로 배포하며 이 저장소에는 포함하지 않습니다.

※ 이 저장소에 나오는 회사 · 사람 · 고객 정보는 모두 가상의 예시입니다.
