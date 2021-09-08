package com.aki.goosinsa.repository;

import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.item.ItemEnums.ColorEnum;
import com.aki.goosinsa.domain.dto.item.ItemEnums.PassionType;
import com.aki.goosinsa.domain.dto.item.ItemEnums.SizeEnum;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.item.Passion;
import com.aki.goosinsa.repository.item.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Log4j2
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach(){
        ItemDto nike = ItemDto.builder()
                .itemName("나이키")
                .stockQuantity(10)
                .price(10000)
                .explains("블라블라")
                .uploadFileDto(new UploadFileDto())
                .build();

        Passion nikeTop = Passion.builder()
                .itemDto(nike)
                .passionType(PassionType.TOP)
                .colorEnum(ColorEnum.Black)
                .sizeEnum(SizeEnum.XL)
                .build();

        itemRepository.save(nikeTop);


    }
    
    @Test
    public void findItem() throws Exception{

        Passion passion = (Passion) itemRepository.findById(21L).orElseThrow();
        log.info(passion.getDtype());





        //given
        
        //when
        
        //then
        
    }

    @Rollback(value = false)
    @Test
    public void insertPassion() throws Exception{

        ItemDto nike = ItemDto.builder()
                .itemName("나이키")
                .stockQuantity(10)
                .price(10000)
                .explains("블라블라")
                .uploadFileDto(new UploadFileDto())
                .build();

        Passion nikeTop = Passion.builder()
                .itemDto(nike)
                .passionType(PassionType.TOP)
                .colorEnum(ColorEnum.Black)
                .sizeEnum(SizeEnum.XL)
                .build();

        itemRepository.save(nikeTop);

        //given
        
        //when
        
        //then
        
    }
    
    @Test
    public void typeSelect() throws Exception{
        
        //given
        itemRepository.findByDtype("F");
        //when
        
        //then
        
    }

    @Test
    public void dto반환_테스트() throws Exception{

        //given
        List<FoodItem> foodEntityList = itemRepository.findAll();
        foodEntityList.stream().forEach(e -> {
            String serverFileName = e.getUploadFile().getServerFileName();
            System.out.println(serverFileName);
        });
        //when
        
        //then
        
    }
    
    @Test
    public void 페이징() throws Exception{
        
        //given
        PageRequest pageRequest = PageRequest.of(1, 3);

        //when
        Page<Item> page = itemRepository.findAllPaging(pageRequest);
        //then
        List<Item> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Item item : content) {
            System.out.println("item = " + item);
        }

        System.out.println("totalElements = " + totalElements);

    }

    @Test
    public void 슬라이스() throws Exception{

        PageRequest pageRequest = PageRequest.of(2, 3);

        //when
        Slice<Item> page = itemRepository.findAllSlice(pageRequest);
        //then
        List<Item> content = page.getContent();

        for (Item item : content) {
            System.out.println("item = " + item);
        }

    }
}