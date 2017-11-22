package esi.kaly.com.AndroidCodingChallenge.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Book model class
 */
public class Book implements Serializable {

    @Nullable
    @SerializedName("title")
    private String title;

    @Nullable
    @SerializedName("author")
    private String author;

    @Nullable
    @SerializedName("imageURL")
    private String imageURL;


    /**
     * No args constructor for use in serialization
     */
    public Book() {
    }

    /**
     * @param author of book
     * @param title of book
     * @param imageURL for book image
     */
    public Book(@Nullable String title, @Nullable String author, @Nullable String imageURL) {
        super();
        this.title = title;
        this.author = author;
        this.imageURL = imageURL;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nullable String author) {
        this.author = author;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(@Nullable String imageURL) {
        this.imageURL = imageURL;
    }


}
