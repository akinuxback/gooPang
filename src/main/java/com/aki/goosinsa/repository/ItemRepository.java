package com.aki.goosinsa.repository;

import com.aki.goosinsa.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

//    @Query("select i from Item i where i.fileNameEmbedded.dbFileName = :dbFileName")
//    Item findByDbFileName(String dbFileName);

}
