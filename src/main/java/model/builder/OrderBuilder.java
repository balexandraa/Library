package model.builder;

import model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder setId(Long id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder setAuthor(String author) {
        order.setAuthor(author);
        return this;
    }

    public OrderBuilder setTitle(String title) {
        order.setTitle(title);
        return this;
    }

    public OrderBuilder setQuantity(Integer id) {
        order.setQuantity(id);
        return this;
    }

    public OrderBuilder setPrice(Double price) {
        order.setPrice(price);
        return this;
    }

    public OrderBuilder setUserId(Long userId) {
        order.setUserId(userId);
        return this;
    }

    public OrderBuilder setDate(LocalDateTime date) {
        order.setDate(date);
        return this;
    }

    public Order build() {
        return order;
    }
}
