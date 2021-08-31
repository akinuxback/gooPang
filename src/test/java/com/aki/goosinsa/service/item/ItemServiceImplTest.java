package com.aki.goosinsa.service.item;

import com.aki.goosinsa.repository.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceImplTest {

    ItemRepository itemRepository;
    ItemServiceImpl itemService;

    @Test
    public void update() throws Exception{
        itemRepository.findById(1L);
        //given

        //when
        
        //then
        
    }

}