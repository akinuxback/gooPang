package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.item.ItemEnums.ColorEnum;
import com.aki.goosinsa.domain.dto.item.ItemEnums.PassionType;
import com.aki.goosinsa.domain.dto.item.ItemEnums.SizeEnum;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor
public class Passion extends Item {

    @Enumerated(EnumType.STRING)
    private PassionType passionType;
    @Enumerated(EnumType.STRING)
    private ColorEnum colorEnum;
    @Enumerated(EnumType.STRING)
    private SizeEnum sizeEnum;

    @Builder
    public Passion(ItemDto itemDto, PassionType passionType, ColorEnum colorEnum, SizeEnum sizeEnum) {
        super(itemDto);
        this.passionType = passionType;
        this.colorEnum = colorEnum;
        this.sizeEnum = sizeEnum;
    }

}
