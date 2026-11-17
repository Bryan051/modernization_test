package com.example.sms.quote.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.quote.service.QuoteVO;

/**
 * 견적 매퍼 (mapper/quote/Quote_SQL.xml)
 */
@Mapper
public interface QuoteMapper {

    List<QuoteVO> selectQuoteList(HashMap<String, Object> param);

    QuoteVO selectQuote(String quoteId);

    List<HashMap<String, Object>> selectQuoteItemList(String quoteId);

    int insertQuote(QuoteVO vo);

    int insertQuoteItem(HashMap<String, Object> item);

    int updateQuoteStatus(@Param("quoteId") String quoteId, @Param("statusCd") String statusCd);
}
