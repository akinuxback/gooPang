//package com.aki.goosinsa.repository.uploadFile;
//
//import com.aki.goosinsa.domain.dto.food.FoodGroups;
//import com.aki.goosinsa.domain.dto.food.FoodItemDto;
//import com.aki.goosinsa.domain.dto.uploadFile.FileType;
//import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
//import com.aki.goosinsa.domain.entity.item.FoodItem;
//import com.aki.goosinsa.repository.ItemRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class UploadFileTest {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    TestMultipartFileImpl mf;
//    FoodItemDto foodItemDto;
//    FoodItem foodItem;
//    FoodItem savedItem;
//
//    @BeforeAll
//    public void beforeEach() throws IOException {
//        mf = new TestMultipartFileImpl("image", "image.jpg");
//        UploadFileDto uploadFileDto = new UploadFileDto("c:/menu", mf);
//
//        foodItemDto = new FoodItemDto(
//                1L, "맛없는 3분 짜장", 10000, 1,
//                "존맛없탱 짜장 맛보면 욕나와!!!", FoodGroups.SCHOOLFOOD, uploadFileDto);
//
//        foodItem = FoodItem.toEntity(foodItemDto);
//        savedItem = itemRepository.save(foodItem);
//    }
//
//    @Test
//    public void 파일타입_체크하기() throws Exception{
//
//        assertThat(savedItem.getUploadFile().getFileType())
//                .isEqualTo(FileType.IMAGE);
//        assertThat(savedItem.getUploadFile().getFileType().getFileTypeValue()).isEqualTo("이미지");
//
//    }
//
//    @Test
//    public void 파일_fullPath_확인() throws Exception{
//        log.info(savedItem.getUploadFile().getFullPath());
//    }
//
//}