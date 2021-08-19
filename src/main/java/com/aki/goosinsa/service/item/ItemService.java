package com.aki.goosinsa.service.item;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {

//    public Page<Item> itemAllPage(int pageNum);

    Page<FoodItemDto> findAllPaging(Pageable pageable);

    public void saveItem(Item item);

    public Item findById(Long id);
}
