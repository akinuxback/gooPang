package com.aki.goosinsa.domain.dto.uploadFile;

public enum FileExtType {
    //텍스트
    TXT("txt"),

    //이미지
    JPG("jpg"), JPEG("jpeg"), PNG("png"),
    BMP("bmp"), GIF("gif"), TIF("tif"),
    TIFF("tiff"), RAW("raw"),

    //동영상
    MP4("mp4"), MOV("mov"), WMV("wmv"),
    AVI("avi"), AVCHD("avchd"), FLV("flv"),
    F4V("f4v"), SWF("swf"), MKV("mkv"),

    //없음
    EMPTY("없음");

    private String title;

    public static FileExtType findFileExtType(String extname){
        FileExtType[] values = FileExtType.values();
        for (FileExtType value : values) {
            if(value.title.equals(extname))
                return value;
        }

        return FileExtType.EMPTY;
    }



    FileExtType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
