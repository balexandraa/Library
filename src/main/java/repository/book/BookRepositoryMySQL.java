package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository{
    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        //String sql = "SELECT * FROM book WHERE id=" + id;
        String sql = "SELECT * FROM book WHERE id = ?";

        Optional<Book> book = Optional.empty();
        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
       // String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() +"\', \'" + book.getTitle()+"\', \'" + book.getPublishedDate() + "\' );";
        String newSql = "INSERT INTO book VALUES(null, ?, ?, ?, ?, ?);";

        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(newSql);
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            // salvam si price + stock
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());

            int rowInserted = preparedStatement.executeUpdate();

            return (rowInserted != 1) ? false : true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        //return true;
    }

    @Override
    public boolean delete(Book book) {
        //String newSql = "DELETE FROM book WHERE author =\'" + book.getAuthor() + "\' AND title=\'" + book.getTitle() + "\';";
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?";

        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(newSql);
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());

            int rowInserted = preparedStatement.executeUpdate();

            return rowInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
       // return true;
    }

    @Override
    public void removeAll() {
        String sql = "TRUNCATE TABLE book;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateStock(String title, String author, int newStock) {
        String sql = "UPDATE book SET stock = ? WHERE title = ? AND author = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        String sql = "SELECT * FROM book WHERE title = ? AND author = ?";

        Optional<Book> book = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
//    @Override
//    public boolean sell(Book book) {
//        String checkSql = "SELECT quantity FROM sold_book WHERE author = ? AND title = ?";
//        String updateSql = "UPDATE sold_book SET quantity = quantity + 1 WHERE author = ? AND title = ?";
//        String insertSql = "INSERT INTO sold_book VALUES (null, ?, ?, 1)";
//
//        try {
//            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
//
//            checkStatement.setString(1, book.getAuthor());
//            checkStatement.setString(2, book.getTitle());
//            ResultSet resultSet = checkStatement.executeQuery();
//
//            if (resultSet.next()) {
//                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
//
//                int newStock = book.getStock() - 1;
//                boolean updateBookStock = updateStock(book.getId(), newStock);
//
//                if (updateBookStock) {
//                    updateStatement.setString(1, book.getAuthor());
//                    updateStatement.setString(2, book.getTitle());
//
//                    int rowsUpdated = updateStatement.executeUpdate();
//
//                    return rowsUpdated > 0;
//                }
//                else {
//                    return false;
//                }
//
//            } else {
//                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
//
//                insertStatement.setString(1, book.getAuthor());
//                insertStatement.setString(2, book.getTitle());
//
//                int rowsInserted = insertStatement.executeUpdate();
//
//                return rowsInserted > 0;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                // adaugam price + stock
                .setPrice(resultSet.getDouble("price"))
                .setStock(resultSet.getInt("stock"))
                .build();
    }
}
