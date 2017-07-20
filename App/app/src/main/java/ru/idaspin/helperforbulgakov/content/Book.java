package ru.idaspin.helperforbulgakov.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jerdys.
 * Date: 14.07.2017
 * Time: 1:22 PM
 *
 * Updated by idaspin on 16.07.2017.
 * + serialization
 * + realm compatibility
 */

public class Book extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("poster")
    @Expose
    private String poster;

    /**
     * No args constructor for use in serialization
     */
    public Book() {
    }

    /**
     * @param id Уникальный ключ (AutoIncrement в удалённой БД)
     * @param title Заголовок
     * @param file URI путь к файлу
     * @param description Описание
     * @param poster URI путь к изображению
     */
    public Book(Integer id, String title, String description, String file, String poster) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.file = file;
        this.poster = poster;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}