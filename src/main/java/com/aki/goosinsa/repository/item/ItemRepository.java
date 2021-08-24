package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.domain.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {

    @Query(value = "select i from Item i where i.itemName = :itemName")
    Item findByItemName(String itemName);

    @Query(value = "select i from Item i join fetch i.uploadFile u", countQuery = "select count(i) from Item i")
    Page<T> findAllPaging(Pageable pageable);

    @Query(value = "select i from Item i join fetch i.uploadFile u")
    Slice<T> findAllSlice(Pageable pageable);
}
