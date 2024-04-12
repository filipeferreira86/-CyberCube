package com.cybcube.models.data.api.config;

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
