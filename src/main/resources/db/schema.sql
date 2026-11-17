-- ============================================================================
--  SMS 영업관리시스템 DDL  (MySQL 5.6 / 5.7, InnoDB, utf8)
--
--  2016.03.07  최초 작성 (홍길동)
--  2017.02.20  데이터 이관 중 일부 FK 가 빠짐 (TB_ORDER.QUOTE_ID 등) - 정리 후 다시 걸기로 함
--  2019.08.22  TB_CUSTOMER.CREDIT_STATUS 추가 (여신 관리)
--  2019.11.05  TB_BILL.ETAX_NO / ISSUE_STATUS 추가 (전자세금계산서 연동)
--  2021.03.15  TB_ORDER / TB_BILL 마감 컬럼(CLOSE_YN, CLOSE_YM) 추가
--  2022.05.30  TB_CUSTOMER.LAST_NOTICE_DT 추가
-- ============================================================================

SET NAMES utf8;

-- ----------------------------------------------------------------------------
-- 사원 · 부서 · 권한
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_DEPT (
    DEPT_ID         VARCHAR(10)   NOT NULL COMMENT '부서ID',
    DEPT_NM         VARCHAR(50)   NOT NULL COMMENT '부서명',
    PARENT_DEPT_ID  VARCHAR(10)   NULL     COMMENT '상위부서ID',
    USE_YN          CHAR(1)       NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    REG_DT          DATETIME      NOT NULL COMMENT '등록일시',
    PRIMARY KEY (DEPT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='부서';

CREATE TABLE IF NOT EXISTS TB_AUTH (
    AUTH_CD    VARCHAR(10)   NOT NULL COMMENT '권한코드',
    AUTH_NM    VARCHAR(30)   NOT NULL COMMENT '권한명',
    AUTH_DESC  VARCHAR(200)  NULL     COMMENT '설명',
    PRIMARY KEY (AUTH_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한';

CREATE TABLE IF NOT EXISTS TB_USER (
    USER_ID        VARCHAR(20)   NOT NULL COMMENT '사원ID',
    USER_NM        VARCHAR(30)   NOT NULL COMMENT '사원명',
    PASSWD         VARCHAR(100)  NOT NULL COMMENT '비밀번호(SHA2 해시)',
    DEPT_ID        VARCHAR(10)   NULL     COMMENT '부서ID',
    AUTH_CD        VARCHAR(10)   NOT NULL DEFAULT 'USER' COMMENT '권한코드',
    EMAIL          VARCHAR(100)  NULL,
    TEL_NO         VARCHAR(20)   NULL,
    USE_YN         CHAR(1)       NOT NULL DEFAULT 'Y',
    LAST_LOGIN_DT  DATETIME      NULL,
    REG_DT         DATETIME      NOT NULL,
    PRIMARY KEY (USER_ID),
    KEY IDX_USER_DEPT (DEPT_ID),
    CONSTRAINT FK_USER_DEPT FOREIGN KEY (DEPT_ID) REFERENCES TB_DEPT (DEPT_ID)
    -- AUTH_CD 는 FK 없음 (권한 코드가 자주 바뀌어서)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사원';

-- ----------------------------------------------------------------------------
-- 거래처
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_CUSTOMER (
    CUST_ID         VARCHAR(20)    NOT NULL COMMENT '거래처ID',
    CUST_NM         VARCHAR(100)   NOT NULL COMMENT '거래처명',
    BIZ_NO          VARCHAR(12)    NULL     COMMENT '사업자등록번호',
    CEO_NM          VARCHAR(30)    NULL     COMMENT '대표자명',
    ADDR            VARCHAR(200)   NULL     COMMENT '주소',
    ADDR_DTL        VARCHAR(100)   NULL     COMMENT '상세주소',
    TEL_NO          VARCHAR(20)    NULL,
    CUST_GRADE      CHAR(1)        NOT NULL DEFAULT 'C' COMMENT '거래처등급 A/B/C',
    CREDIT_STATUS   VARCHAR(2)     NOT NULL DEFAULT '01' COMMENT '여신상태 01정상 02미수 03연체',
    CREDIT_LIMIT    DECIMAL(15,0)  NULL     COMMENT '여신한도',
    CHARGE_USER_ID  VARCHAR(20)    NULL     COMMENT '담당사원',
    LAST_NOTICE_DT  DATETIME       NULL     COMMENT '마지막 미수안내 발송일시',
    USE_YN          CHAR(1)        NOT NULL DEFAULT 'Y',
    REG_USER_ID     VARCHAR(20)    NULL,
    REG_DT          DATETIME       NOT NULL,
    UPD_DT          DATETIME       NULL,
    PRIMARY KEY (CUST_ID),
    KEY IDX_CUSTOMER_NM (CUST_NM),
    KEY IDX_CUSTOMER_BIZ (BIZ_NO),
    CONSTRAINT FK_CUSTOMER_USER FOREIGN KEY (CHARGE_USER_ID) REFERENCES TB_USER (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='거래처';

CREATE TABLE IF NOT EXISTS TB_CUST_CONTACT (
    CONTACT_ID    BIGINT        NOT NULL AUTO_INCREMENT,
    CUST_ID       VARCHAR(20)   NOT NULL,
    CONTACT_NM    VARCHAR(30)   NOT NULL COMMENT '담당자명',
    DEPT_NM       VARCHAR(50)   NULL,
    POSITION_NM   VARCHAR(30)   NULL     COMMENT '직위',
    EMAIL         VARCHAR(100)  NULL,
    MOBILE_NO     VARCHAR(20)   NULL,
    MAIN_YN       CHAR(1)       NOT NULL DEFAULT 'N' COMMENT '대표담당자 여부',
    REG_DT        DATETIME      NOT NULL,
    PRIMARY KEY (CONTACT_ID),
    KEY IDX_CONTACT_CUST (CUST_ID),
    CONSTRAINT FK_CONTACT_CUST FOREIGN KEY (CUST_ID) REFERENCES TB_CUSTOMER (CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='거래처 담당자';

-- ----------------------------------------------------------------------------
-- 제품 · 단가
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_PRODUCT (
    PRODUCT_ID    VARCHAR(20)    NOT NULL COMMENT '제품ID',
    PRODUCT_NM    VARCHAR(100)   NOT NULL COMMENT '제품명',
    PRODUCT_TYPE  VARCHAR(10)    NULL     COMMENT '제품유형',
    UNIT_CD       VARCHAR(5)     NULL     COMMENT '단위',
    UNIT_PRICE    DECIMAL(15,0)  NOT NULL DEFAULT 0 COMMENT '기본단가',
    USE_YN        CHAR(1)        NOT NULL DEFAULT 'Y',
    REG_DT        DATETIME       NOT NULL,
    UPD_DT        DATETIME       NULL,
    PRIMARY KEY (PRODUCT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='제품';

CREATE TABLE IF NOT EXISTS TB_PRODUCT_PRICE (
    PRICE_ID       BIGINT         NOT NULL AUTO_INCREMENT,
    PRODUCT_ID     VARCHAR(20)    NOT NULL,
    CUST_GRADE     CHAR(1)        NULL     COMMENT '적용 거래처등급(없으면 공통)',
    UNIT_PRICE     DECIMAL(15,0)  NOT NULL,
    APPLY_FROM_DT  DATE           NOT NULL,
    APPLY_TO_DT    DATE           NOT NULL,
    PRIMARY KEY (PRICE_ID),
    KEY IDX_PRICE_PRODUCT (PRODUCT_ID, APPLY_FROM_DT),
    CONSTRAINT FK_PRICE_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES TB_PRODUCT (PRODUCT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='제품 기간별 단가';

-- ----------------------------------------------------------------------------
-- 영업기회 · 견적
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_OPPTY (
    OPPTY_ID          VARCHAR(20)    NOT NULL COMMENT '영업기회ID',
    CUST_ID           VARCHAR(20)    NOT NULL,
    OPPTY_NM          VARCHAR(100)   NOT NULL,
    STAGE_CD          VARCHAR(2)     NOT NULL DEFAULT '10' COMMENT '10발굴 20제안 30협상 40수주 90실주',
    EXPECT_AMT        DECIMAL(15,0)  NULL,
    EXPECT_CLOSE_DT   DATE           NULL,
    CHARGE_USER_ID    VARCHAR(20)    NULL,
    STATUS_CD         VARCHAR(2)     NOT NULL DEFAULT '10' COMMENT '10진행 90종결',
    REG_DT            DATETIME       NOT NULL,
    UPD_DT            DATETIME       NULL,
    PRIMARY KEY (OPPTY_ID),
    KEY IDX_OPPTY_CUST (CUST_ID)
    -- CUST_ID FK 없음 (2017.02 이관 때 빠짐)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='영업기회';

CREATE TABLE IF NOT EXISTS TB_QUOTE (
    QUOTE_ID         VARCHAR(20)    NOT NULL COMMENT '견적ID',
    OPPTY_ID         VARCHAR(20)    NULL,
    CUST_ID          VARCHAR(20)    NOT NULL,
    QUOTE_NM         VARCHAR(100)   NULL,
    QUOTE_DT         DATE           NOT NULL,
    VALID_DT         DATE           NULL     COMMENT '유효기한',
    TOTAL_AMT        DECIMAL(15,0)  NOT NULL DEFAULT 0,
    STATUS_CD        VARCHAR(2)     NOT NULL DEFAULT '10' COMMENT '10작성 20제출 90종결',
    CHARGE_USER_ID   VARCHAR(20)    NULL,
    REG_DT           DATETIME       NOT NULL,
    UPD_DT           DATETIME       NULL,
    PRIMARY KEY (QUOTE_ID),
    KEY IDX_QUOTE_CUST (CUST_ID),
    CONSTRAINT FK_QUOTE_CUST FOREIGN KEY (CUST_ID) REFERENCES TB_CUSTOMER (CUST_ID)
    -- OPPTY_ID FK 없음
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='견적';

CREATE TABLE IF NOT EXISTS TB_QUOTE_ITEM (
    QUOTE_ID    VARCHAR(20)    NOT NULL,
    ITEM_SEQ    INT            NOT NULL,
    PRODUCT_ID  VARCHAR(20)    NOT NULL,
    QTY         INT            NOT NULL DEFAULT 1,
    UNIT_PRICE  DECIMAL(15,0)  NOT NULL DEFAULT 0,
    AMT         DECIMAL(15,0)  NOT NULL DEFAULT 0,
    PRIMARY KEY (QUOTE_ID, ITEM_SEQ),
    CONSTRAINT FK_QITEM_QUOTE FOREIGN KEY (QUOTE_ID) REFERENCES TB_QUOTE (QUOTE_ID)
    -- PRODUCT_ID FK 없음 (단종 제품이 있어서)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='견적 품목';

-- ----------------------------------------------------------------------------
-- 수주 · 계약
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_ORDER (
    ORDER_ID        VARCHAR(20)    NOT NULL COMMENT '수주ID',
    QUOTE_ID        VARCHAR(20)    NULL,
    CUST_ID         VARCHAR(20)    NOT NULL,
    ORDER_DT        DATE           NOT NULL,
    ORDER_AMT       DECIMAL(15,0)  NOT NULL DEFAULT 0,
    ORDER_STATUS    VARCHAR(20)    NOT NULL DEFAULT '접수' COMMENT '접수/계약완료/청구완료/수금완료/취소',
    CONTRACT_ID     VARCHAR(20)    NULL,
    CLOSE_YN        CHAR(1)        NOT NULL DEFAULT 'N' COMMENT '월마감 여부',
    CLOSE_YM        CHAR(6)        NULL     COMMENT '마감년월 YYYYMM',
    CHARGE_USER_ID  VARCHAR(20)    NULL,
    REG_DT          DATETIME       NOT NULL,
    UPD_DT          DATETIME       NULL,
    PRIMARY KEY (ORDER_ID),
    KEY IDX_ORDER_CUST (CUST_ID, ORDER_DT),
    KEY IDX_ORDER_STATUS (ORDER_STATUS),
    CONSTRAINT FK_ORDER_CUST FOREIGN KEY (CUST_ID) REFERENCES TB_CUSTOMER (CUST_ID)
    -- QUOTE_ID · CONTRACT_ID FK 없음 (순서 문제로 못 걸었음)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='수주';

CREATE TABLE IF NOT EXISTS TB_ORDER_ITEM (
    ORDER_ID    VARCHAR(20)    NOT NULL,
    ITEM_SEQ    INT            NOT NULL,
    PRODUCT_ID  VARCHAR(20)    NOT NULL,
    QTY         INT            NOT NULL DEFAULT 1,
    UNIT_PRICE  DECIMAL(15,0)  NOT NULL DEFAULT 0,
    AMT         DECIMAL(15,0)  NOT NULL DEFAULT 0,
    PRIMARY KEY (ORDER_ID, ITEM_SEQ),
    KEY IDX_OITEM_PRODUCT (PRODUCT_ID),
    CONSTRAINT FK_OITEM_ORDER FOREIGN KEY (ORDER_ID) REFERENCES TB_ORDER (ORDER_ID)
    -- PRODUCT_ID FK 없음
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='수주 품목';

CREATE TABLE IF NOT EXISTS TB_CONTRACT (
    CONTRACT_ID   VARCHAR(20)    NOT NULL COMMENT '계약ID',
    ORDER_ID      VARCHAR(20)    NOT NULL,
    CUST_ID       VARCHAR(20)    NOT NULL,
    CONTRACT_NM   VARCHAR(100)   NULL,
    CONTRACT_AMT  DECIMAL(15,0)  NOT NULL DEFAULT 0,
    START_DT      DATE           NULL,
    END_DT        DATE           NULL,
    STATUS_CD     VARCHAR(2)     NOT NULL DEFAULT '10' COMMENT '10진행 90종료',
    REG_DT        DATETIME       NOT NULL,
    UPD_DT        DATETIME       NULL,
    PRIMARY KEY (CONTRACT_ID),
    KEY IDX_CONTRACT_ORDER (ORDER_ID)
    -- ORDER_ID · CUST_ID FK 없음
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='계약';

-- ----------------------------------------------------------------------------
-- 청구 · 수금
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_BILL (
    BILL_ID       VARCHAR(20)    NOT NULL COMMENT '청구ID',
    ORDER_ID      VARCHAR(20)    NOT NULL,
    CONTRACT_ID   VARCHAR(20)    NULL,
    CUST_ID       VARCHAR(20)    NOT NULL,
    BILL_DT       DATE           NOT NULL,
    SUPPLY_AMT    DECIMAL(15,0)  NOT NULL DEFAULT 0 COMMENT '공급가액',
    TAX_AMT       DECIMAL(15,0)  NOT NULL DEFAULT 0 COMMENT '세액',
    TOTAL_AMT     DECIMAL(15,0)  NOT NULL DEFAULT 0 COMMENT '합계',
    ETAX_NO       VARCHAR(30)    NULL     COMMENT '전자세금계산서 승인번호',
    ISSUE_STATUS  VARCHAR(2)     NOT NULL DEFAULT '00' COMMENT '00대기 10발행 90실패',
    CLOSE_YN      CHAR(1)        NOT NULL DEFAULT 'N',
    CLOSE_YM      CHAR(6)        NULL,
    REG_DT        DATETIME       NOT NULL,
    UPD_DT        DATETIME       NULL,
    PRIMARY KEY (BILL_ID),
    KEY IDX_BILL_CUST (CUST_ID, BILL_DT),
    KEY IDX_BILL_ORDER (ORDER_ID),
    CONSTRAINT FK_BILL_CUST FOREIGN KEY (CUST_ID) REFERENCES TB_CUSTOMER (CUST_ID)
    -- ORDER_ID · CONTRACT_ID FK 없음
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='청구(세금계산서)';

CREATE TABLE IF NOT EXISTS TB_COLLECT (
    COLLECT_ID      VARCHAR(20)    NOT NULL COMMENT '수금ID',
    BILL_ID         VARCHAR(20)    NOT NULL,
    ORDER_ID        VARCHAR(20)    NULL,
    CUST_ID         VARCHAR(20)    NOT NULL,
    COLLECT_DT      DATE           NOT NULL,
    COLLECT_AMT     DECIMAL(15,0)  NOT NULL,
    COLLECT_METHOD  VARCHAR(2)     NULL     COMMENT '01계좌이체 02현금 03어음',
    REG_USER_ID     VARCHAR(20)    NULL,
    REG_DT          DATETIME       NOT NULL,
    PRIMARY KEY (COLLECT_ID),
    KEY IDX_COLLECT_BILL (BILL_ID),
    KEY IDX_COLLECT_CUST (CUST_ID),
    CONSTRAINT FK_COLLECT_BILL FOREIGN KEY (BILL_ID) REFERENCES TB_BILL (BILL_ID)
    -- ORDER_ID · CUST_ID FK 없음
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='수금';

-- ----------------------------------------------------------------------------
-- 공통코드 · 공지
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS TB_CODE (
    GRP_CD    VARCHAR(20)   NOT NULL COMMENT '코드그룹',
    CD        VARCHAR(20)   NOT NULL COMMENT '코드',
    CD_NM     VARCHAR(100)  NOT NULL COMMENT '코드명',
    SORT_ORD  INT           NOT NULL DEFAULT 0,
    USE_YN    CHAR(1)       NOT NULL DEFAULT 'Y',
    PRIMARY KEY (GRP_CD, CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통코드';

CREATE TABLE IF NOT EXISTS TB_NOTICE (
    NOTICE_ID  BIGINT        NOT NULL AUTO_INCREMENT,
    TITLE      VARCHAR(200)  NOT NULL,
    CONTENT    TEXT          NULL,
    WRITER_ID  VARCHAR(20)   NULL,
    VIEW_CNT   INT           NOT NULL DEFAULT 0,
    TOP_YN     CHAR(1)       NOT NULL DEFAULT 'N' COMMENT '상단고정',
    REG_DT     DATETIME      NOT NULL,
    PRIMARY KEY (NOTICE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공지사항';

-- ----------------------------------------------------------------------------
-- 초기 데이터
-- ----------------------------------------------------------------------------
INSERT INTO TB_AUTH (AUTH_CD, AUTH_NM, AUTH_DESC) VALUES
    ('ADMIN',   '관리자',   '전체 메뉴'),
    ('MANAGER', '팀장',     '팀 단위 조회 · 승인'),
    ('USER',    '영업사원', '본인 담당 거래처');

INSERT INTO TB_CODE (GRP_CD, CD, CD_NM, SORT_ORD, USE_YN) VALUES
    ('CUST_GRADE',    'A',  'A등급',     1, 'Y'),
    ('CUST_GRADE',    'B',  'B등급',     2, 'Y'),
    ('CUST_GRADE',    'C',  'C등급',     3, 'Y'),
    ('CREDIT_STATUS', '01', '정상',      1, 'Y'),
    ('CREDIT_STATUS', '02', '미수발생',  2, 'Y'),
    ('CREDIT_STATUS', '03', '연체',      3, 'Y'),
    ('COLLECT_METHOD','01', '계좌이체',  1, 'Y'),
    ('COLLECT_METHOD','02', '현금',      2, 'Y'),
    ('COLLECT_METHOD','03', '어음',      3, 'Y');
