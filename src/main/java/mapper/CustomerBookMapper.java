package mapper;

import model.Book;
import view.model.CustomerBookDTO;
import view.model.builder.CustomerBookDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerBookMapper {

    public static CustomerBookDTO convertBookToCustomerDTO(Book book) {
        return new CustomerBookDTOBuilder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setPrice(book.getPrice())
                .setStock(book.getStock())
                .build();
    }

    public static List<CustomerBookDTO> convertBookListToCustomerDTOList(List<Book> books) {
        return books.parallelStream()
                .map(CustomerBookMapper::convertBookToCustomerDTO)
                .collect(Collectors.toList());
    }
}