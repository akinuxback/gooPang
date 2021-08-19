package com.aki.goosinsa.domain.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(AddressDto dto) {
        this.city = dto.getCity();
        this.street = dto.getStreet();
        this.zipcode = dto.getZipcode();
    }

}
