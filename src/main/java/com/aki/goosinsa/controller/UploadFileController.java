package com.aki.goosinsa.controller;

import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@Slf4j
public class UploadFileController {

    private String UPLOAD_FOLDER = System.getProperty("user.home") + File.separator + "upload" + File.separator;

    //파일을 rest 방식으로 바로 업로드 할수 있게한다.
    @PostMapping("/singleFileUpload")
    public ResponseEntity<UploadFileDto> uploadMultipartFile(MultipartFile singleFile, RedirectAttributes rttr, Model model) throws IOException {
        UploadFileDto uploadFileDto = new UploadFileDto(UPLOAD_FOLDER, singleFile, FileType.IMAGE);
        log.info(uploadFileDto.toString());
        return new ResponseEntity<>(uploadFileDto, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> display(@RequestParam String fullPath) throws IOException {
        ResponseEntity<byte[]> result = null;
        File file = null;

        try {
            file = new File(fullPath);
            log.info("display = {} ", fullPath);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.info("file ={}", file);
            e.printStackTrace();
        }

        return result;
    }

}
