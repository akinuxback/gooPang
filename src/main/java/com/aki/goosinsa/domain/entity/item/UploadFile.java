package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "UPLOAD_FILE_SEQ_GEN",
        sequenceName = "UPLOAD_FILE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "ftype")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPLOAD_FILE_SEQ_GEN")
    private Long id;
    private String uploadFolder;
    private String uploadPath;
    private String clientFileName;
    private String serverFileName;
    private String extFileName; // 파일 확장자명
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private String fullPath;

    @OneToOne(mappedBy = "uploadFile", fetch = FetchType.LAZY)
    private Item item;

    public static UploadFile createUploadFile(UploadFileDto dto){
        UploadFile uf = new UploadFile();
        uf.uploadFolder = dto.getUploadFolder();
        uf.uploadPath = dto.getUploadPath();
        uf.clientFileName = dto.getClientFileName();
        uf.serverFileName = dto.getServerFileName();
        uf.extFileName = dto.getExtFileName();
        uf.fileType = dto.getFileType();
        uf.fullPath = dto.getFullPath();
        return uf;
    }


}
