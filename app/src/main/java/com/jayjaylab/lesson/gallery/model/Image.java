package com.jayjaylab.lesson.gallery.model;

/**
 * Created by jjkim on 2016. 5. 17..
 */
public class Image {
    int id;
    String path;
    Thumbnail thumbnail;

    public Image(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public Image setId(int id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Image setPath(String path) {
        this.path = path;
        return this;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Image setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    @Override
    public String toString() {
        return Image.class.getSimpleName() +
                "{id : " + id +
                ", path : " + path +
                ", thumbnail : " + thumbnail +
                "}";
    }
}
