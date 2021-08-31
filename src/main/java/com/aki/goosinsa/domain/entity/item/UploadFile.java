package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.*;

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
@Builder
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPLOAD_FILE_SEQ_GEN")
    protected Long id;
    protected String uploadFolder;
    protected String uploadPath;
    protected String clientFileName;
    protected String serverFileName;
    protected String extFileName; // 파일 확장자명
    @Enumerated(EnumType.STRING)
    protected FileType fileType;
    protected String fullPath;

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

    public UploadFile changeUploadFile(UploadFileDto dto){
        this.uploadFolder = dto.getUploadFolder();
        this.uploadPath = dto.getUploadPath();
        this.clientFileName = dto.getClientFileName();
        this.serverFileName = dto.getServerFileName();
        this.extFileName = dto.getExtFileName();
        this.fileType = dto.getFileType();
        this.fullPath = dto.getFullPath();
        return this;
    }

}
