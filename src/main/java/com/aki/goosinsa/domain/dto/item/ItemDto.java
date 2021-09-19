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

    protected Long id;
    protected String itemName;
    protected Integer price;
    protected Integer stockQuantity;
    protected String explains;
    protected UploadFileDto uploadFileDto;
    protected Company company;

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
