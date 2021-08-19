package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.domain.entity.item.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}
