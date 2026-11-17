package com.example.sms.quote.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.product.service.impl.ProductMapper;
import com.example.sms.quote.service.QuoteService;
import com.example.sms.quote.service.QuoteVO;

/**
 * 견적 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.06.20   김철수      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2020.02.10   김철수      기간별 단가 적용 (기준일 = 견적일)
 * </pre>
 */
@Service("quoteService")
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<QuoteVO> selectQuoteList(HashMap<String, Object> param) {
        return quoteMapper.selectQuoteList(param);
    }

    @Override
    public QuoteVO selectQuote(String quoteId) {
        QuoteVO vo = quoteMapper.selectQuote(quoteId);
        if (vo != null) {
            vo.setItemList(quoteMapper.selectQuoteItemList(quoteId));
        }
        return vo;
    }

    @Override
    public String insertQuote(QuoteVO vo) {
        // 2018.09 견적번호 채번 변경
        // vo.setQuoteId("QT" + SmsUtil.getToday() + quoteMapper.selectSeq());
        String quoteId = SmsUtil.genId("QT");
        vo.setQuoteId(quoteId);
        vo.setQuoteDt(SmsUtil.getToday("yyyy-MM-dd"));
        if (vo.getValidDt() == null) {
            vo.setValidDt(SmsUtil.addDays(vo.getQuoteDt(), 30));
        }

        // 품목 단가는 제품 단가표에서 조회해서 채운다
        long lTotal = 0L;
        int iSeq = 1;
        List<HashMap<String, Object>> itemList = vo.getItemList();
        if (itemList != null) {
            for (HashMap<String, Object> item : itemList) {
                String strProductId = (String) item.get("productId");
                Long unitPrice = productMapper.selectUnitPrice(strProductId, vo.getQuoteDt());
                if (unitPrice == null) {
                    throw new IllegalArgumentException("단가가 없는 제품입니다 : " + strProductId);
                }
                int iQty = SmsUtil.toInt(item.get("qty"), 1);
                long lAmt = unitPrice.longValue() * iQty;
                item.put("quoteId", quoteId);
                item.put("itemSeq", iSeq++);
                item.put("qty", iQty);
                item.put("unitPrice", unitPrice);
                item.put("amt", lAmt);
                lTotal += lAmt;
            }
        }
        vo.setTotalAmt(lTotal);

        quoteMapper.insertQuote(vo);
        if (itemList != null) {
            for (HashMap<String, Object> item : itemList) {
                quoteMapper.insertQuoteItem(item);
            }
        }
        return quoteId;
    }

    @Override
    public int updateQuoteStatus(String quoteId, String statusCd) {
        return quoteMapper.updateQuoteStatus(quoteId, statusCd);
    }
}
