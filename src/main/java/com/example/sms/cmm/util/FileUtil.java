package com.example.sms.cmm.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * 첨부파일 저장 유틸
 *
 * 2016.04.12 홍길동  최초 작성
 * 2020.06.08 김철수  하위 폴더 지정 추가
 */
public class FileUtil {

    /** 첨부파일 저장 위치 (운영 서버 기준) */
    public static final String UPLOAD_DIR = "/data/sms/upload";

    private FileUtil() {
    }

    /**
     * 파일을 UPLOAD_DIR/subDir 아래에 저장하고 저장된 파일명을 돌려준다.
     */
    public static String save(MultipartFile file, String subDir) throws IOException {
        File dir = new File(UPLOAD_DIR, subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String strOrgNm = new File(file.getOriginalFilename()).getName();
        String strSaveNm = SmsUtil.genId("F") + "_" + strOrgNm;
        file.transferTo(new File(dir, strSaveNm));
        return strSaveNm;
    }

    public static boolean delete(String subDir, String saveNm) {
        File f = new File(new File(UPLOAD_DIR, subDir), saveNm);
        return f.exists() && f.delete();
    }
}
