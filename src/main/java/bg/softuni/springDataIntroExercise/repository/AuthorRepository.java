package bg.softuni.springDataIntroExercise.repository;

import bg.softuni.springDataIntroExercise.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    @Query("""
            FROM Author a
            JOIN Book b ON b.author.id = a.id
            WHERE YEAR(b.releaseDate) < ?1
            """)
    List<Author> findAllByHavingBooksReleasedBeforeYear(int year);

    @Query("""
            FROM Author a
            ORDER BY size(a.books) DESC
            """)
    List<Author> findAllOrderByBooksCountDescending();

}
