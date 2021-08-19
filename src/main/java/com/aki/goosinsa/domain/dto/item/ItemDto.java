package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String explains;
    private UploadFileDto uploadFileDto;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.explains = item.getExplains();
        this.uploadFileDto = UploadFileDto.entityToDto(item.getUploadFile());
    }

}
