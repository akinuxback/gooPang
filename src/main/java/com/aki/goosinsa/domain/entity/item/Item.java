package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.ItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "dtype")
@SequenceGenerator(
        name = "ITEM_SEQ_GEN",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GEN")
    private Long id;
    private String itemName;
    private int price;
    private int stockQuantity;
    private String explains;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UploadFile uploadFile;

    public Item(ItemDto itemDto) {
        this.itemName = itemDto.getItemName();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
        this.explains = itemDto.getExplains();
        this.uploadFile = UploadFile.createUploadFile(itemDto.getUploadFileDto());
    }

}
