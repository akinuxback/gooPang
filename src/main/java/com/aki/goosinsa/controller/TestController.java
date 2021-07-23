//package com.aki.goosinsa.controller;
//
//import com.aki.goosinsa.domain.dto.util.UploadFileDto;
//import com.aki.goosinsa.domain.entity.item.UploadFile;
//import com.aki.goosinsa.domain.entity.item.FoodItem;
//import com.aki.goosinsa.domain.entity.item.Item;
//import com.aki.goosinsa.repository.ItemRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//
//@Controller
//@RequestMapping("/test")
//@Slf4j
//@RequiredArgsConstructor
//public class TestController {
//
//    private final String UPLOAD_FOLDER = System.getProperty("user.home") + File.separator + "upload" + File.separator;;
//
//    private final ItemRepository itemRepository;
//
//    @GetMapping("testFileUpload")
//    public String testFileUpload(){
//        log.info("UPLOAD_FOLDER = {}", UPLOAD_FOLDER);
//        return "test/testFileUpload";
//    }
//
//    @PostMapping("testFileUpload")
//    public String testFileUploadPost(RedirectAttributes rttr, Model model,
//                                     @RequestParam String itemName,
//                                     @RequestParam MultipartFile file) {
//
//        log.info("itemName = {}", itemName);
//        log.info("origin FileName = {}", file.getOriginalFilename());
//
//        try {
//            UploadFileDto uploadFileDto = new UploadFileDto(UPLOAD_FOLDER, file);
//            log.info("UploadFileDto = {}", uploadFileDto.toString());
//
//            Item foodItem = new FoodItem();
//            foodItem.setItemName(itemName);
//            foodItem.setUploadFile(UploadFile.createUploadFile(uploadFileDto));
//            Item saveItem = itemRepository.save(foodItem);
//
//            rttr.addAttribute("fileUri", uploadFileDto.getDbFileName());
//            log.info("uploadFile = {}", uploadFileDto.getDbFileName());
//            rttr.addAttribute("memo", "memo");
//
//            model.addAttribute("model", "model");
//        } catch(Exception e){
//            return "redirect:/test/testFileUpload";
//        }
//
//
//        return "redirect:/test/testFileUpload";
//    }
//
//    @ResponseBody
//    @GetMapping("/images/{fileUri}")
//    public Resource downloadImage(@PathVariable String fileUri) throws
//            MalformedURLException {
//        Item findItem = itemRepository.findByDbFileName(fileUri);
//        log.info("findItem = {} ", findItem);
//        String uploadPath = findItem.getUploadFile().getUploadPath();
//        String fileCallPath = getFullPath(UPLOAD_FOLDER, uploadPath, fileUri);
//        return new UrlResource("file:" + fileCallPath);
//    }
//
//    private String getFullPath(String UPLOAD_FOLDER, String uploadPath, String filename) {
//        return UPLOAD_FOLDER + uploadPath + File.separator + filename;
//    }
//
//    @GetMapping("/display")
//    @ResponseBody
//    public ResponseEntity<byte[]> getFile(String fileName) throws RuntimeException {
//
//        ResponseEntity<byte[]> result = null;
//        File file = null;
//        try {
//
//            file = new File(UPLOAD_FOLDER + fileName);
//            log.info("file = {}", file);
//            HttpHeaders header = new HttpHeaders();
//
//            header.add("Content-Type", Files.probeContentType(file.toPath()));
//            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            log.info("file = {}", file);
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//}
