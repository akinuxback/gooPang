package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.item.UploadFile;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
@Log4j2
class QDItemRepositoryImplTest {

    @Autowired
    QDItemRepository qdItemRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void beforeEach(){
        UploadFileDto uploadFile1 = UploadFileDto.builder()
                .clientFileName("박보영10.jpg")
                .extFileName("jpg")
                .fileType(FileType.IMAGE)
                .fullPath("C:\\Users\\icino\\upload\\2021\\08\\24\\d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
                .serverFileName("d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
                .uploadFolder("C:\\Users\\icino\\upload\\")
                .uploadPath("2021\\08\\24")
                .build();

        Company company = companyRepository.findAll().get(0);

        FoodItemDto foodItemDto1 = new FoodItemDto("스프링", 10000, 30, "상품설명 블라블라", uploadFile1, company,
                FoodGroups.CHICKEN, "순살");



        IntStream.rangeClosed(1, 100).forEach(i ->{
            FoodItem foodItem1 = new FoodItem(foodItemDto1);
            itemRepository.save(foodItem1);
        });

        em.flush();
        em.clear();

    }

    @Test
    @Rollback(value = false)
    public void testtttt() throws Exception{

    }

    @Test
    public void test() throws Exception{
        FoodSearch foodSearch = new FoodSearch();
//        foodSearch.setFoodGroups("SCHOOLFOOD");
//        foodSearch.setFoodGroupsOfTitle("튀김");
        foodSearch.setFoodName("스프링");
//        foodSearch.setPrice(8000);

        Page<FoodItemDto> result = qdItemRepository.findAllPaging(PageRequest.of(0, 3), foodSearch);
        List<FoodItemDto> content = result.getContent();
        result.getTotalPages();

        content.forEach(c -> log.info(c.toString()));
    }

    @Test
    public void findByIdJoinUploadFile() throws Exception{
        FoodItemDto byIdJoinUploadFile = qdItemRepository.findByIdJoinUploadFile(1L);
        log.info("=================>" + byIdJoinUploadFile.getUploadFileDto().getFullPath());
    }
    
}