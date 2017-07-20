package ru.idaspin.helperforbulgakov.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by User.
 * Date: 14.07.2017
 * Time: 1:23 PM
 *
 * Updated by idaspin on 16.07.2017.
 * + serialization
 * + realm compatibility
 */

public class Note extends RealmObject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("top")
    @Expose
    private String top;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("budgetFor")
    @Expose
    private String budgetFor;

    /**
     * No args constructor for use in serialization
     */
    public Note() {
    }

    /**
     *
     * @param budgetFor Стоимость товарва/услуги
     * @param id Уникальный идентификационный ключ
     * @param text Комментарий к заметке
     * @param date Дата создания заметки
     * @param top Название товара или услуги
     */
    public Note(Integer id, String top, String date, String text, String budgetFor) {
        super();
        this.id = id;
        this.top = top;
        this.date = date;
        this.text = text;
        this.budgetFor = budgetFor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBudgetFor() {
        return budgetFor;
    }

    public void setBudgetFor(String budgetFor) {
        this.budgetFor = budgetFor;
    }

}