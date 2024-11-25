package org.example.cruddbfile;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class FileUpload {
    public BoardVO uploadFile(HttpServletRequest request) {
        // 업로드 가능한 파일의 최대 크기 설정 (15MB)
        int sizeLimit = 15 * 1024 * 1024;

        // 업로드된 파일을 저장할 디렉토리 경로 설정 (웹서버 링크/upload)
        String realPath = request.getServletContext().getRealPath("/") + "upload";
        //경로 확인
        System.out.println("파일 업로드 경로: " + realPath);

       //dir 설정
        File dir = new File(realPath);
        // 디렉토리가 존재하지 않을 경우 생성
        if (!dir.exists()) dir.mkdirs();

        MultipartRequest multipartRequest;
        try {
            // MultipartRequest 객체를 생성하여 파일 업로드 처리
            multipartRequest = new MultipartRequest(
                    request,
                    realPath,                       // 파일 저장 경로
                    sizeLimit,                      // 파일 크기 제한
                    "utf-8",                        // 인코딩 방식
                    new DefaultFileRenamePolicy()   // 중복 파일명을 방지
            );
        } catch (IOException e) {
            // 파일 업로드 중 오류가 발생했을 경우
            System.err.println("파일 업로드 실패: " + e.getMessage());
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }

        String filename = multipartRequest.getFilesystemName("img"); // 업로드된 파일 이름
        String title = multipartRequest.getParameter("title");//제목
        String writer = multipartRequest.getParameter("writer");//작가
        String content = multipartRequest.getParameter("content");//내용

        //BoardVO 호출
        return new BoardVO(title, writer, content, filename);
    }
}

