package com.aki.goosinsa.domain.dto.uploadFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Slf4j
public class UploadFileDto {

    private String uploadFolder;
    private String uploadPath;
    private String clientFileName;
    private String extFileName; // 파일 확장자명
    @Enumerated(EnumType.STRING)
    private FileType fileType; // 파일 타입 - IMAGE or VIDEO 등등
    private String serverFileName;
    private String fullPath;


    // 디렉토리에 파일저장과 동시에, +  dto 생성
    public UploadFileDto(String UPLOAD_FOLDER, MultipartFile mf, FileType fileType) throws IOException {

        createUploadFileDto(UPLOAD_FOLDER, mf);
        if(this.fileType != fileType){
            log.info("fileType = {}", this.fileType);
            throw new AccessDeniedException("이미지 파일만 업로드 가능 합니다.");
        }

        if(!mf.isEmpty()) {
            File uploadFolderPath = new File(uploadFolder, uploadPath);
            if (uploadFolderPath.exists() == false) {
                uploadFolderPath.mkdirs();
            }

            File saveFile = new File(uploadFolderPath, File.separator + serverFileName);
            this.fullPath = saveFile.getPath();
            log.info("fulPath ============================= {} ", fullPath);
            //file save
            mf.transferTo(saveFile);
        }
    }

    private void createUploadFileDto(String UPLOAD_FOLDER, MultipartFile mf) {
        this.uploadFolder = UPLOAD_FOLDER; // 기본저장폴더 경로
        this.uploadPath = makeSubFolders(); // 추가경로만들기 21/07/05/
        this.clientFileName = mf.getOriginalFilename(); // 박보영.jpg - 업로드한 사용자에게 보여줄 파일이름
        this.extFileName = makeExtFileName(clientFileName); // 확장자명 추출 jpg
        this.fileType = makeFileExtType(this.extFileName); // 파일 확장자 타입 enum
        this.serverFileName = makeServerFileName(extFileName); // uuid + extFileName -> uuid + .jpg
    }

    // FileType
    private FileType makeFileExtType(String extFileName) {
        FileType extType = FileType.findByFileType(FileExtType.findFileExtType(extFileName));
        return extType;
    }

    // 추가 디렉토리경로 만들기 21/07/05/
    private String makeSubFolders() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    // 확장자명 추출 jpg
    private String makeExtFileName(String originalFilename) {
        // image.jpg
        int pos = originalFilename.lastIndexOf(".");
        // return -> jpg
        return originalFilename.substring(pos + 1).toLowerCase();
    }

    // uuid + extFileName -> 박보영.jpg
    private String makeServerFileName(String clientFileName) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + clientFileName;
    }

}
