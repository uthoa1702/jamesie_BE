package com.example.jamesie_be.model.DTO;

public class ImageDTO {
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
