package ru.idaspin.helperforbulgakov.repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import okio.ByteString;
import ru.arturvasilov.rxloader.RxUtils;
import ru.idaspin.helperforbulgakov.api.ApiFactory;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.content.Note;
import rx.Observable;

/**
 * Реализация репозитория
 *
 * Created by idaspin.
 * Date: 7/16/2017
 * Time: 11:51 AM
 */

class DefaultBulgakovRepository implements BulgakovRepository {

    /**
     * Отправляет на сервер объект {@link Book}
     * @param title Заголовок
     * @param description Описание
     * @param poster URI на постер
     * @param file URI на файл
     * @return {@link Observable} для обработки результата
     */
    @Override
    public Observable<Object> addBook(String title, String description, String poster, String file) {
        return ApiFactory.getRisensService()
                .addBook(
                        ByteString.encodeUtf8(title).base64(),
                        ByteString.encodeUtf8(description).base64(),
                        ByteString.encodeUtf8(poster).base64(),
                        ByteString.encodeUtf8(file).base64()
                )
                .doOnError(Throwable::toString)
                .compose(RxUtils.async());
    }

    /**
     * Отправляет на сервер объект {@link Note}
     * @param top Заголовок ("На что")
     * @param text Комментарий
     * @param date Дата создания заметки
     * @param budgetFor Стоимость
     * @return {@link Observable} для обработки результата
     */
    @Override
    public Observable<Object> addNote(String top, String text, String date, String budgetFor) {
        return ApiFactory.getRisensService()
                .addNote(ByteString.encodeUtf8(top).base64(), ByteString.encodeUtf8(text).base64(), ByteString.encodeUtf8(date).base64(), ByteString.encodeUtf8(budgetFor).base64())
                .doOnError(Throwable::toString)
                .compose(RxUtils.async());
    }

    /**
     * Получает из Базы Данных список данных типа {@link Book}
     * Если данные с сервера получены, сохраняет или перезаписывает записи в локальной Базе Данных.
     * Иначе, делает попытку вернуть список сразу из локальной Базы.
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Book>} обернутые в {@link Observable} для обработки результата
     */
    @Override
    public Observable<List<Book>> getBooks(int page) {
        return ApiFactory.getRisensService()
                .getBook(page)
                .flatMap(books -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        for (Book book : books) {
                            RealmResults<Book> p = realm.where(Book.class).equalTo("id", book.getId()).findAll();
                            if (p != null && p.size() != 0) {
                                p.deleteAllFromRealm();
                            }
                            realm.insertOrUpdate(book);
                        }
                    });
                    return Observable.just(books);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Book> posts = realm.where(Book.class).findAllSorted("id", Sort.DESCENDING);
                    return Observable.just(
                            realm.copyFromRealm(
                                    posts.subList(
                                            posts.size() >= page * 10 ? page * 10 : posts.size(),
                                            posts.size() > page * 10 + 9 ? page * 10 + 9 : posts.size()
                                    )
                            )
                    );
                })
                .compose(RxUtils.async());
    }

    /**
     * Получает из Базы Данных список данных типа {@link Note}
     * Если данные с сервера получены, сохраняет или перезаписывает записи в локальной Базе Данных.
     * Иначе, делает попытку вернуть список сразу из локальной Базы.
     * @param page Номер страницы (10 объектов на одной)
     * @return {@link List<Note>} обернутые в {@link Observable} для обработки результата
     */
    @Override
    public Observable<List<Note>> getNote(int page) {
        return ApiFactory.getRisensService()
                .getNote(page)
                .flatMap(notes -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        for (Note note : notes) {
                            RealmResults<Note> p = realm.where(Note.class).equalTo("id", note.getId()).findAll();
                            if (p != null && p.size() != 0) {
                                p.deleteAllFromRealm();
                            }
                            realm.insertOrUpdate(note);
                        }
                    });
                    return Observable.just(notes);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Note> posts = realm.where(Note.class).findAllSorted("id", Sort.DESCENDING);
                    return Observable.just(
                            realm.copyFromRealm(
                                    posts.subList(
                                            posts.size() >= page * 10 ? page * 10 : posts.size(),
                                            posts.size() > page * 10 + 9 ? page * 10 + 9 : posts.size()
                                    )
                            )
                    );
                })
                .compose(RxUtils.async());
    }

    /**
     * Отправляет на сервер запрос на удаление объекта {@link Book} из Базы Данных.
     * Также, удаляет этот объект из локальной БД.
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    @Override
    public Observable<Object> removeBook(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            RealmResults<Book> p = realm.where(Book.class).equalTo("id", id).findAll();
            if (p != null && p.size() != 0) {
                p.deleteAllFromRealm();
            }
        });
        return ApiFactory.getRisensService()
                .removeBook(id)
                .doOnError(Throwable::toString)
                .compose(RxUtils.async());
    }

    /**
     * Отправляет на сервер запрос на удаление объекта {@link Note} из Базы Данных.
     * Также, удаляет этот объект из локальной БД.
     * @param id Персональный идентификационный ключ объекта
     * @return {@link Observable} для обработки результата
     */
    @Override
    public Observable<Object> removeNote(int id) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            RealmResults<Note> p = realm.where(Note.class).equalTo("id", id).findAll();
            if (p != null && p.size() != 0) {
                p.deleteAllFromRealm();
            }
        });
        return ApiFactory.getRisensService()
                .removeNote(id)
                .doOnError(Throwable::toString)
                .compose(RxUtils.async());
    }

}
