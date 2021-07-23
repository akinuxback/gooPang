package com.aki.goosinsa.domain.dto.uploadFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FileType {
    TEXT("텍스트", Arrays.asList(FileExtType.TXT)),
    IMAGE("이미지", Arrays.asList(FileExtType.JPG, FileExtType.JPEG,
            FileExtType.PNG,  FileExtType.BMP, FileExtType.GIF,
            FileExtType.TIF, FileExtType.TIFF, FileExtType.RAW)),
    VIDEO("동영상", Arrays.asList(FileExtType.MP4, FileExtType.MOV,
            FileExtType.WMV, FileExtType.AVI, FileExtType.AVCHD,
            FileExtType.FLV, FileExtType.F4V, FileExtType.SWF, FileExtType.MKV)),
    EMPTY("없음",Collections.EMPTY_LIST);

    private String fileTypeValue;
    private List<FileExtType> fileExtTypeList;

    FileType(String fileTypeValue, List<FileExtType> fileExtTypeList) {
        this.fileTypeValue = fileTypeValue;
        this.fileExtTypeList = fileExtTypeList;
    }

    public static FileType findByFileType(FileExtType ext){
        return Arrays.stream(FileType.values())
                .filter(fileExtType -> fileExtType.hasFileExt(ext))
                .findAny()
                .orElse(EMPTY);
    }

    private boolean hasFileExt(FileExtType ext) {
        return fileExtTypeList.stream()
                .anyMatch(e -> e.equals(ext));
    }

    public String getFileTypeValue() {
        return fileTypeValue;
    }


}
