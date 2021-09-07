package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String explains;
    private UploadFileDto uploadFileDto;
    private Company company;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName().trim();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.explains = item.getExplains().trim();
        this.uploadFileDto = UploadFileDto.entityToDto(item.getUploadFile());
        this.company = item.getCompany();
    }

    // 상품등록시 fk 를 등록하려고 setter 열어둠
    public void setCompany(Company company) {
        this.company = company;
    }
}
