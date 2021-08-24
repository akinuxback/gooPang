package com.aki.goosinsa.service.item;

import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.item.QDItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    public final ItemRepository itemRepository;
    public final QDItemRepository qdItemRepository;

    @Override
    public Page<FoodItemDto> findAllPaging(Pageable pageable, FoodSearch foodSearch) {
        return qdItemRepository.findAllPaging(pageable, foodSearch);
    }

    @Override
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item findById(Long id) {
        return (Item) itemRepository.findById(id).get();
    }

    //    @Override
//    public Page<Item> itemAllPage(int pageNum) {
//        PageRequest pageRequest = PageRequest.of(pageNum, 3);
//        Page<Item> page = itemRepository.findAllPaging(pageRequest);
//        return page;
//    }
}
