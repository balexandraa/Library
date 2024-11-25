package repository.book.soldBook;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoldBookRepositoryMySQL implements SoldBookRepository{
    private final Connection connection;

    public SoldBookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean save(Book book) {
        String checkSql = "SELECT quantity FROM sold_book WHERE author = ? AND title = ?";
        String updateSql = "UPDATE sold_book SET quantity = quantity + 1 WHERE author = ? AND title = ?";
        String insertSql = "INSERT INTO sold_book (author, title, quantity) VALUES (?, ?, 1)";

        try {
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);

            checkStatement.setString(1, book.getAuthor());
            checkStatement.setString(2, book.getTitle());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);

                updateStatement.setString(1, book.getAuthor());
                updateStatement.setString(2, book.getTitle());

                int rowsUpdated = updateStatement.executeUpdate();

                return rowsUpdated > 0;
            } else {
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);

                insertStatement.setString(1, book.getAuthor());
                insertStatement.setString(2, book.getTitle());

                int rowsInserted = insertStatement.executeUpdate();

                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM sold_book;";

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

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setStock(resultSet.getInt("quantity"))
                .build();
    }
}
