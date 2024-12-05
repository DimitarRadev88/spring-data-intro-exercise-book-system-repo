package bg.softuni.springDataIntroExercise.service.interfaces;

import bg.softuni.springDataIntroExercise.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {


    void addAll(List<Author> authors);

    Author getRandomAuthor();

    List<Author> getAllAuthorsWithBooksReleasedBefore(int year);

    List<Author> getAllAuthorsOrderedByCountOfBooksDescending();

}
