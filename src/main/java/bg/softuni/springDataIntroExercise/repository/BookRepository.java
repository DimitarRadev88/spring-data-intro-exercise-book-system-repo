package bg.softuni.springDataIntroExercise.repository;

import bg.softuni.springDataIntroExercise.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("FROM Book b WHERE YEAR(b.releaseDate) > ?1")
    List<Book> findAllByReleaseDateYearGreaterThan(int year);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

}
