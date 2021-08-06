package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String explains;
    private UploadFileDto uploadFileDto;

}
