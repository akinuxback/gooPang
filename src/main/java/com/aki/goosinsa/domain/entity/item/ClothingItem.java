package com.aki.goosinsa.domain.entity.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
@Getter @Setter
public class ClothingItem extends Item {



}
