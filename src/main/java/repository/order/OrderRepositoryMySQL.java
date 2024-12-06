package repository.order;

import model.Book;
import model.Order;
import model.builder.BookBuilder;
import model.builder.OrderBuilder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryMySQL implements OrderRepository {
    private final Connection connection;

    public OrderRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean save(Book book, Long userId) {
        String sql = "INSERT INTO `order` VALUES (null, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, 1);
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setLong(5, userId);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Book> findAllBooks() {
        String sql = "SELECT * FROM `order`;";

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
    public List<Order> findAll() {
        String sql = "SELECT * FROM `order`";
        List<Order> orders = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setStock(resultSet.getInt("quantity"))
                .build();
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException{
        return new OrderBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setQuantity(resultSet.getInt("quantity"))
                .setPrice(resultSet.getDouble("price"))
                .setUserId(resultSet.getLong("user_id"))
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .build();

    }
}
