package bg.softuni.springDataIntroExercise;

import bg.softuni.springDataIntroExercise.entity.Author;
import bg.softuni.springDataIntroExercise.entity.Book;
import bg.softuni.springDataIntroExercise.entity.Category;
import bg.softuni.springDataIntroExercise.enums.AgeRestriction;
import bg.softuni.springDataIntroExercise.enums.EditionType;
import bg.softuni.springDataIntroExercise.service.interfaces.AuthorService;
import bg.softuni.springDataIntroExercise.service.interfaces.BookService;
import bg.softuni.springDataIntroExercise.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final String RESOURCES_FILES_PATH = "src/main/resources/files/";
    private static final String AUTHORS_FILE_NAME = "authors.txt";
    private static final String CATEGORIES_FILE_NAME = "categories.txt";
    private static final String BOOKS_FILE_NAME = "books.txt";
    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedDatabase();
        printAllBooksReleasedAfterYear(2000);
        printAllAuthorsFirstAndLastNamesWhoReleasedABookBeforeYear(1990);
        printAllAuthorsFirstLastNamesAndBookCount();
        printAllBooksTitlesReleaseDateAndCopiesByAuthor("George", "Powell");

    }

    private void printAllBooksTitlesReleaseDateAndCopiesByAuthor(String firstName, String lastName) {
        bookService
                .getAllBooksFromAuthorOrderedByReleaseDateDescendingAndBookTitileAscending(firstName, lastName)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));
    }

    private void printAllAuthorsFirstLastNamesAndBookCount() {
        List<Author> authors = authorService.getAllAuthorsOrderedByCountOfBooksDescending();
        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size()));
    }

    private void printAllAuthorsFirstAndLastNamesWhoReleasedABookBeforeYear(int year) {
        List<Author> authors = authorService.getAllAuthorsWithBooksReleasedBefore(year);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void printAllBooksReleasedAfterYear(int year) {
        List<Book> books = bookService.getAllBooksReleasedAfterYear(year);
        System.out.println(books.size());
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    private void seedDatabase() throws IOException {
        seedAuthors();
        seedCategories();
        seedBooks();
    }

    private void seedBooks() throws IOException {
        List<Book> books = parseBooksFromBooksFile();

        bookService.addAll(books);
    }

    private List<Book> parseBooksFromBooksFile() throws IOException {
        List<String> booksInfo = Files.readAllLines(Path.of(RESOURCES_FILES_PATH + BOOKS_FILE_NAME));

        return booksInfo.stream()
                .map(row -> row.split(" "))
                .map(this::parseBook).toList();
    }

    private Book parseBook(String[] bookInfo) {
        String title = parseBookTitle(bookInfo);
        LocalDate releaseDate = getReleaseDate(bookInfo);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        EditionType editionType = getEditionType(bookInfo);
        int copies = Integer.parseInt(bookInfo[2]);
        AgeRestriction ageRestriction = getAgeRestriction(bookInfo);
        Set<Category> categories = categoryService.getRandomCategories();
        Author author = authorService.getRandomAuthor();

        return new Book(title, releaseDate, price, editionType, copies, ageRestriction, categories, author);
    }

    private static EditionType getEditionType(String[] bookInfo) {
        return EditionType.values()[Integer.parseInt(bookInfo[0])];
    }

    private static LocalDate getReleaseDate(String[] bookInfo) {
        return LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
    }

    private static AgeRestriction getAgeRestriction(String[] bookInfo) {
        return AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
    }

    private static String parseBookTitle(String[] row) {
        return Arrays.stream(row).skip(5).collect(Collectors.joining(" "));
    }

    private void seedCategories() throws IOException {
        List<Category> categories = parseCategoriesFromCategoriesFile();
        categoryService.addAll(categories);
    }

    private List<Category> parseCategoriesFromCategoriesFile() throws IOException {
        List<String> categories = Files.readAllLines(Path.of(RESOURCES_FILES_PATH + CATEGORIES_FILE_NAME));
        return categories.stream().map(Category::new).toList();
    }

    private void seedAuthors() throws IOException {
        List<Author> authors = parseAuthorsFromAuthorsFile();

        authors.forEach(System.out::println);

        authorService.addAll(authors);
    }

    private List<Author> parseAuthorsFromAuthorsFile() throws IOException {
        List<String> authorNames = Files.readAllLines(Path.of(RESOURCES_FILES_PATH + AUTHORS_FILE_NAME));

        return authorNames.stream()
                .map(line -> line.split(" "))
                .map(info -> new Author(info[0], info[1]))
                .toList();
    }

}
