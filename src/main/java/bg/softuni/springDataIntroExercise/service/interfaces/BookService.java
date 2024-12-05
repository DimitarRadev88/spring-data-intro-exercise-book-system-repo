package bg.softuni.springDataIntroExercise.service.interfaces;

import bg.softuni.springDataIntroExercise.entity.Book;

import java.util.List;

public interface BookService {

    void add(Book book);

    void addAll(List<Book> books);

    List<Book> getAllBooksReleasedAfterYear(int year);


    List<Book> getAllBooksFromAuthorOrderedByReleaseDateDescendingAndBookTitileAscending(String firstName, String lastName);
}
