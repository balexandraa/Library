import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.*;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {
    private static BookRepository bookRepository;

    @BeforeAll
    public static void setup() {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
        bookRepository = new BookRepositoryMySQL(connection);

    }

    @BeforeEach
    public void initialiseData() {
        bookRepository.save(createTestBook(1L));
    }

    @AfterEach
    public void tearDown() {
        bookRepository.removeAll();
    }

    @Test
    public void testSave() {
        assertTrue(bookRepository.save(createTestBook(2L)));
    }

    @Test
    public void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void testFindById() {
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isPresent());
        assertEquals(book.get(), createTestBook(1L));
    }

    @Test
    public void testDelete() {
        assertTrue(bookRepository.delete(createTestBook(1L)));
        assertTrue(bookRepository.findById(1L).isEmpty());
    }

    public static Book createTestBook(Long id) {
        return new BookBuilder().setTitle("Ion" + id).setAuthor("Liviu Rebreanu" + id).setPublishedDate(LocalDate.of(1900, 10, 2)).setId(id).build();
    }
}
