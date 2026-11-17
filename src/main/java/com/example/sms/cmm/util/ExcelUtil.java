package com.example.sms.cmm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 엑셀 다운로드 유틸 (목록 -> xlsx)
 *
 * 2018.04.17 김철수  최초 작성 (POI 3.17)
 */
public class ExcelUtil {

    private ExcelUtil() {
    }

    public static void write(String sheetNm, String[] headers, String[] keys,
                             List<HashMap<String, Object>> rows, OutputStream os) throws IOException {
        Workbook wb = new XSSFWorkbook();
        try {
            Sheet sheet = wb.createSheet(sheetNm);
            Row head = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                head.createCell(i).setCellValue(headers[i]);
            }
            int iRow = 1;
            for (HashMap<String, Object> map : rows) {
                Row row = sheet.createRow(iRow++);
                for (int c = 0; c < keys.length; c++) {
                    Object val = map.get(keys[c]);
                    row.createCell(c).setCellValue(val == null ? "" : String.valueOf(val));
                }
            }
            wb.write(os);
        } finally {
            wb.close();
        }
    }
}
