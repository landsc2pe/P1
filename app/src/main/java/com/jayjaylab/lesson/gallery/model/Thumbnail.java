package com.jayjaylab.lesson.gallery.model;

/**
 * Created by jjkim on 2016. 5. 17..
 */
public class Thumbnail {
    int id;
    int imageId;
    String path;

    public Thumbnail(int id, int imageId, String path) {
        this.id = id;
        this.imageId = imageId;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public Thumbnail setId(int id) {
        this.id = id;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public Thumbnail setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Thumbnail setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return Thumbnail.class.getSimpleName() +
                "{id : " + id +
                ", imageId : " + imageId +
                ", path : " + path +
                "}";
    }
}
