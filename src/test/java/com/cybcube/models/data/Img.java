package com.cybcube.models.data;

import java.io.File;

public class Img {
    private String validImg;
    private String invalidImg;

    public File getValidImg() {
        return new File(validImg);
    }

    public File getInvalidImg() {
        return new File(invalidImg);
    }
}
