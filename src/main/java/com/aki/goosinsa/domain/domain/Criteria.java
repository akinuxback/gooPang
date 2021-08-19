package com.aki.goosinsa.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Criteria {

    private int page;
    private int size;

    public Criteria(int page) {
        this.page = page;
        this.size = 10;
    }
}
