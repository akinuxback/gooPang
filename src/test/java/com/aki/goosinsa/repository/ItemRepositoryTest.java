package com.aki.goosinsa.repository;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach(){


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

        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        Slice<Item> page = itemRepository.findAllSlice(pageRequest);
        //then
        List<Item> content = page.getContent();

        for (Item item : content) {
            System.out.println("item = " + item);
        }

    }
}