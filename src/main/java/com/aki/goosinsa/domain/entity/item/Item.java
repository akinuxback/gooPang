package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.category.Category;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.exception.NotEnoughStockException;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "dtype")
@SequenceGenerator(
        name = "ITEM_SEQ_GEN",
        sequenceName = "ITEM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GEN")
    protected Long id;
    protected String itemName;
    protected int price;
    protected int stockQuantity;
    protected String explains;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    protected UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    protected Company company;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public Item(ItemDto itemDto) {
        this.itemName = itemDto.getItemName().trim();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
        this.explains = itemDto.getExplains().trim();
        this.uploadFile = UploadFile.createUploadFile(itemDto.getUploadFileDto());
        this.company = itemDto.getCompany();
    }


    //==비즈니스 로직==//
    /**
     * stock 증가
     * */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
