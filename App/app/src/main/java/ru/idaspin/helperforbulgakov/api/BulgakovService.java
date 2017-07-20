package ru.idaspin.helperforbulgakov.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.content.Note;
import rx.Observable;

/**
 * Интерфейс API методов
 *
 * Created by idaspin.
 * Date: 7/16/2017
 * Time: 11:40 AM
 */
public interface BulgakovService {

    /**
     * Отправляет на сервер поля объекта {@link Book}
     * @param title Заголовок
     * @param description Описание
     * @param poster URI на постер
     * @param file URI на файл
     * @return {@link Observable} для обработки результата
     */
    @POST("api/practic/add/book")
    Observable<Object> addBook(@Header("title") String title, @Header("description") String description, @Header("poster") String poster, @Header("file") String file);

    /**
     * Отправляет на сервер поля объекта {@link Note}
     * @param top Заголовок ("На что")
     * @param text Комментарий
     * @param date Дата создания заметки
     * @param budgetFor Стоимость
     * @return {@link Observable} для обработки результата
     */
    @POST("api/practic/add/note")
    Observable<Object> addNote(@Header("top") String top, @Header("text") String text, @Header("date") String date, @Header("budgetFor") String budgetFor);

    /**
     * Запрос к серверу выдать 10 объектов {@link Book}
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Book>} обернутые в {@link Observable} для обработки результата
     */
    @GET("api/practic/get/books")
    Observable<List<Book>> getBook(@Query("page") int page);

    /**
     * Запрос к серверу выдать 10 объектов {@link Note}
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Note>} обернутые в {@link Observable} для обработки результата
     */
    @GET("api/practic/get/notes")
    Observable<List<Note>> getNote(@Query("page") int page);

    /**
     * Отправляет на сервер запрос на удаление объекта {@link Note} из Базы Данных.
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    @POST("api/practic/remove/note")
    Observable<Object> removeNote(@Header("id") int id);

    /**
     * Отправляет на сервер запрос на удаление объекта {@link Book} из Базы Данных.
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    @POST("api/practic/remove/book")
    Observable<Object> removeBook(@Header("id") int id);
}
