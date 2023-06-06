package ru.itgirl.jdbcspringexample.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itgirl.jdbcspringexample.model.Book;
//import ru.itgirl.jdbcspringexample.model.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    public DataSource dataSource;

    public int id;


    public BookRepositoryImpl (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {
        //создаем пустой список, в который сложим наши книги из БД
        List<Book> result = new ArrayList<>();

        //наш SQL-запрос
        String SQL_findAllBooks = "select * from books;";

        //Создаём новые объекты Connection и Statement
        //Использование try-with-resources необходимо для гарантированного закрытия connection и statement,
        //вне зависимости от успешности операции.


        //Создаем подключение к БД

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             //если подключение к БД успешно выполненно, то передаем наш SQL запрос
             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            //ResultSet - итерируемый объект.
            //Пока есть что доставать, идём по нему и преобразовываем sql-строки в объекты класса Book.
            //Добавляем полученный объект в ArrayList.
            while (resultSet.next()) {
                Book book = converRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            //Если операция провалилась, обернём пойманное исключение в непроверяемое и пробросим дальше(best-practise)
            throw new IllegalStateException(e);
        }
        //Возвращаем полученный в результате операции ArrayList
        return result;
    }

    public Book converRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
    }

    @Override
    public List<Book> findBookById(int id) {
        List<Book> result = new ArrayList<>();

        String SQL_findBookById = "SELECT * FROM books WHERE id = " + id;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findBookById)) {
            while (resultSet.next()) {
                Book book = converRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}