package com.example.sms.cmm.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.example.sms.cmm.service.impl.CodeDAO;

/**
 * 공통 유틸 (날짜 · 금액 서식, ID 채번, 공통코드명 조회)
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.03.07   홍길동      최초 생성
 *  2017.08.30   김철수      금액 서식 추가
 *  2019.08.22   홍길동      getCodeName 추가 (코드명 조회가 여기저기 중복돼서)
 * </pre>
 */
public class SmsUtil {

    private static final AtomicInteger SEQ = new AtomicInteger(0);

    /** 부가세율 10% */
    private static final double VAT_RATE = 0.1;

    private SmsUtil() {
    }

    /** 오늘 날짜 yyyyMMdd */
    public static String getToday() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getToday(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /** @deprecated 2017.08 이후 getToday() 로 교체 */
    @Deprecated
    public static String getCurrentDate() {
        return getToday();
    }

    public static String getYearMonth() {
        return getToday("yyyyMM");
    }

    public static String getPrevYearMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMM").format(cal.getTime());
    }

    /** 20191105 -> 2019-11-05 */
    public static String formatDate(String strDt) {
        if (strDt == null || strDt.length() != 8) {
            return strDt;
        }
        return strDt.substring(0, 4) + "-" + strDt.substring(4, 6) + "-" + strDt.substring(6, 8);
    }

    /** yyyy-MM-dd 기준으로 일수 더하기 */
    public static String addDays(String strDt, int iDays) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(strDt));
            cal.add(Calendar.DAY_OF_MONTH, iDays);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("날짜 형식 오류 : " + strDt);
        }
    }

    /** 1234567 -> 1,234,567 */
    public static String formatAmt(long lAmt) {
        return new DecimalFormat("#,##0").format(lAmt);
    }

    public static String formatAmt(BigDecimal amt) {
        return amt == null ? "0" : formatAmt(amt.longValue());
    }

    /** 공급가액으로 세액 계산 (원 단위 반올림) */
    public static long calcVat(long lSupplyAmt) {
        return Math.round(lSupplyAmt * VAT_RATE);
    }

    public static String nvl(String str, String def) {
        return StringUtils.isEmpty(str) ? def : str;
    }

    public static int toInt(Object obj, int def) {
        if (obj == null) {
            return def;
        }
        try {
            return Integer.parseInt(String.valueOf(obj).trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /** 접두어 + 일시 + 일련번호 (예: OD20191105103015007) */
    public static String genId(String prefix) {
        int iSeq = SEQ.incrementAndGet() % 1000;
        return prefix + getToday("yyyyMMddHHmmss") + String.format("%03d", iSeq);
    }

    /** 목록 조회용 LIMIT 값 설정 (pageNo, pageSize -> startRow, pageSize) */
    public static void setPaging(HashMap<String, Object> param) {
        int iPageNo = toInt(param.get("pageNo"), 1);
        int iPageSize = toInt(param.get("pageSize"), 20);
        param.put("startRow", (iPageNo - 1) * iPageSize);
        param.put("pageSize", iPageSize);
    }

    /** 공통코드명 조회 */
    public static String getCodeName(String grpCd, String cd) {
        if (cd == null) {
            return "";
        }
        CodeDAO codeDAO = (CodeDAO) SpringContextHolder.getBean("codeDAO");
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("grpCd", grpCd);
        param.put("cd", cd);
        String strNm = codeDAO.selectCodeName(param);
        return strNm == null ? cd : strNm;
    }

    public static List<HashMap<String, Object>> getCodeList(String grpCd) {
        CodeDAO codeDAO = (CodeDAO) SpringContextHolder.getBean("codeDAO");
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("grpCd", grpCd);
        param.put("sortCol", "SORT_ORD");
        return codeDAO.selectCodeList(param);
    }
}
