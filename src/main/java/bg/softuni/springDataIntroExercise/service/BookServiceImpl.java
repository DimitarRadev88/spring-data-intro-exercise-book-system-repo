package bg.softuni.springDataIntroExercise.service;

import bg.softuni.springDataIntroExercise.entity.Book;
import bg.softuni.springDataIntroExercise.repository.BookRepository;
import bg.softuni.springDataIntroExercise.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void add(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void addAll(List<Book> books) {
        bookRepository.saveAll(books);
    }

    @Override
    public List<Book> getAllBooksReleasedAfterYear(int year) {
        return bookRepository.findAllByReleaseDateYearGreaterThan(year);
    }

    @Override
    public List<Book> getAllBooksFromAuthorOrderedByReleaseDateDescendingAndBookTitileAscending(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);

    }


}
