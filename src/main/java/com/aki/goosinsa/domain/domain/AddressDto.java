package com.aki.goosinsa.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    public static AddressDto toDto(Address address){
        return AddressDto.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .build();
    }
}