package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
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
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorValue(value = "ftype")
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

    /**
    *  company 와  item 엔티티는 동일한 uploadFile 엔티티를 참조한다.
     *  그래서 당연 둘다 양방향을 걸고 참조해서 해야 할줄 알아서 아래 주석처럼 걸어줬더니
     *  fetch lazy 설정이 디폴트 그대로 두어도, company 는 item List 들이 1+N 문제가 발생 했었다. ,
     *  그래서 왜그럴까??? 하다가 oneToOne 이 양쪽일시 --- 아... 머리에서 지금 정리가 잘안된다.. 스트레스를 너무 많이 받았나보다..
     *  일단 나중에 궁금 하면 주석을 풀어보고 테스트를 해보자
     *  -> 여러 방면으로 생각한 결과 현재 uploadFile 엔티티로 조회나 서비스 처리를 할일이 없기에 양방향이 필요 없는것 같다 ....
     *  지금은 일단 패쓰 ....
    * */
//    @OneToOne(mappedBy = "uploadFile", fetch = FetchType.LAZY)
//    private Item item;
//
//    @OneToOne(mappedBy = "uploadFile", fetch = FetchType.LAZY)
//    private Company company;

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
