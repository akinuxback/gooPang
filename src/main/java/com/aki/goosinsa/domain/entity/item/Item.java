package com.aki.goosinsa.domain.entity.item;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
