package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class QDItemRepositoryImplTest {

    @Autowired
    QDItemRepository qdItemRepository;


    @Test
    public void test() throws Exception{

        Page<FoodItemDto> result = qdItemRepository.findAllPaging(PageRequest.of(0, 3));
        List<FoodItemDto> content = result.getContent();
        result.getTotalPages();

        content.forEach(c -> log.info(c.toString()));
    }

}