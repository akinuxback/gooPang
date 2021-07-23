//package com.aki.goosinsa.controller;
//
//import com.aki.goosinsa.domain.entity.item.Item;
//import com.aki.goosinsa.repository.ItemRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.File;
//import java.net.MalformedURLException;
//
//@ResponseBody
//@RequiredArgsConstructor
//@Slf4j
//public class FileUploadController {
//
//    private String UPLOAD_FOLDER = System.getProperty("user.home") + File.separator + "upload" + File.separator;;
//    private final ItemRepository itemRepository;
//
//    @ResponseBody
//    @GetMapping("/images/{fileuri}")
//    public Resource downloadImage(@PathVariable String fileuri) throws
//            MalformedURLException {
//        Item findItem = itemRepository.findByDbFileName(fileuri);
//        log.info("findItem = {} ", findItem);
//        String uploadPath = findItem.getUploadFile().getUploadPath();
//        String fileCallPath = getFullPath(UPLOAD_FOLDER, uploadPath, fileuri);
//        return new UrlResource("file:" + fileCallPath);
//    }
//
//    private String getFullPath(String UPLOAD_FOLDER, String uploadPath, String filename) {
//        return UPLOAD_FOLDER + uploadPath + File.separator + filename;
//    }
//
//
//}
