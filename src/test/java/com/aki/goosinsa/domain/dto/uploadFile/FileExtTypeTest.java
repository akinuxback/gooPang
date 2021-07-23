package com.aki.goosinsa.domain.dto.uploadFile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class FileExtTypeTest {

    @Test
    void findByExt() {
        String ext1 = extracted("jpg");
        String ext2 = extracted("png");
        String ext3 = extracted("avi");
        String ext4 = extracted("");

        assertThat(ext1).isEqualTo("IMAGE");
        assertThat(ext2).isEqualTo("IMAGE");
        assertThat(ext3).isEqualTo("VIDEO");
        assertThat(ext4).isEqualTo("EMPTY");


    }

    private String extracted(String extName) {
        FileType jpg = FileType.findByFileType(FileExtType.findFileExtType(extName));
        String name = jpg.name();
        log.info("enum 키워드 name() 으로찾기 : ===>  " + name);
        String fileType = jpg.getFileTypeValue();
        log.info("fileType 찾기 : ==> " + fileType);

        return name;
    }

    @Test
    void getFileType() {
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}