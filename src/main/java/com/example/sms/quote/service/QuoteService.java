package com.example.sms.quote.service;

import java.util.HashMap;
import java.util.List;

/**
 * 견적 서비스
 */
public interface QuoteService {

    List<QuoteVO> selectQuoteList(HashMap<String, Object> param);

    /** 견적 + 품목 */
    QuoteVO selectQuote(String quoteId);

    /** 견적 등록 (생성된 견적ID 반환) */
    String insertQuote(QuoteVO vo);

    /** 10 작성 / 20 제출 / 90 종결 */
    int updateQuoteStatus(String quoteId, String statusCd);
}
