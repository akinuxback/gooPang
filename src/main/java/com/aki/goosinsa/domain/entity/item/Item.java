package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.ItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@SequenceGenerator(
        name = "ITEM_SEQ_GEN",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GEN")
    private Long id;
    private String itemName;
    private int price;
    private int stockQuantity;
    private String explains;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UploadFile uploadFile;

//    public static Item createItem(ItemDto itemDto) {
//        Item item = new Item();
//        item.itemName = itemDto.getItemName();
//        item.price = itemDto.getPrice();
//        item.stockQuantity = itemDto.getStockQuantity();
//        item.explains = itemDto.getExplains();
//        item.uploadFile = UploadFile.createUploadFile(itemDto.getUploadFileDto());
//
//        return item;
//    }

    public Item(ItemDto itemDto) {
        this.itemName = itemDto.getItemName();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
        this.explains = itemDto.getExplains();
        this.uploadFile = UploadFile.createUploadFile(itemDto.getUploadFileDto());
    }

}
