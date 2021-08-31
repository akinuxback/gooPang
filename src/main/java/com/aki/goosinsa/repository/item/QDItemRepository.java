package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface QDItemRepository {

    public Page<FoodItemDto> findAllPaging(Pageable pageable, FoodSearch foodSearch);

    public Slice<ItemDto> findAllSlice(Pageable pageable);

    public FoodItemDto findByIdJoinUploadFile(Long id);

}
