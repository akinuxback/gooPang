package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface QDItemRepository {

    public Page<FoodItemDto> findAllPaging(Pageable pageable);

    public Slice<ItemDto> findAllSlice(Pageable pageable);

}
