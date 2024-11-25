package view.model.builder;

import view.model.BookDTO;
import view.model.SoldBookDTO;

public class SoldBookDTOBuilder {
    private SoldBookDTO soldBookDTO;

    public SoldBookDTOBuilder() {
        soldBookDTO = new SoldBookDTO();
    }

    public SoldBookDTOBuilder setAuthor(String author) {
        soldBookDTO.setAuthor(author);
        return this;
    }

    public SoldBookDTOBuilder setTitle(String title) {
        soldBookDTO.setTitle(title);
        return this;
    }

    public SoldBookDTOBuilder setQuantity(Integer quantity) {
        soldBookDTO.setQuantity(quantity);
        return this;
    }

    public SoldBookDTO build() {
        return soldBookDTO;
    }
}
