package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ItemDto {

    protected Long id;

    @NotBlank(message = "상품이름 입력")
    protected String itemName;

    @NotNull
    protected Integer price;

    @NotNull
    @Max(9999)
    protected Integer stockQuantity;

    @NotBlank
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
