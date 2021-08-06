package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.Item;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String explains;
    private UploadFileDto uploadFileDto;

    public ItemDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.explains = item.getExplains();
        this.uploadFileDto = UploadFileDto.entityToDto(item.getUploadFile());
    }

}
