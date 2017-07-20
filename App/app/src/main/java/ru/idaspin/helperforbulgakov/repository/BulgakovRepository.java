package ru.idaspin.helperforbulgakov.repository;

import java.util.List;

import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.content.Note;
import rx.Observable;

/**
 * Интерфейс репозитория
 *
 * Created by idaspin.
 * Date: 7/16/2017
 * Time: 11:50 AM
 */

public interface BulgakovRepository {

    /**
     * Сохраняет {@link Book} в Базу Данных
     * @param title Заголовок
     * @param description Описание
     * @param poster URI на постер
     * @param file URI на файл
     * @return {@link Observable} для обработки результата
     */
    Observable<Object> addBook(String title, String description, String poster, String file);

    /**
     * Сохраняет {@link Note} в Базу Данных
     * @param top Заголовок ("На что")
     * @param text Комментарий
     * @param date Дата создания заметки
     * @param budgetFor Стоимость
     * @return {@link Observable} для обработки результата
     */
    Observable<Object> addNote(String top, String text, String date, String budgetFor);

    /**
     * Получает из Базы Данных список данных типа {@link Book}
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Book>} обернутые в {@link Observable} для обработки результата
     */
    Observable<List<Book>> getBooks(int page);

    /**
     * Получает из Базы Данных список данных типа {@link Note}
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Note>} обернутые в {@link Observable} для обработки результата
     */
    Observable<List<Note>> getNote(int page);

    /**
     * Удаляет из Базы Данных объект {@link Note}
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    Observable<Object> removeNote(int id);

    /**
     * Удаляет из Базы Данных объект {@link Book}
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    Observable<Object> removeBook(int id);

}
