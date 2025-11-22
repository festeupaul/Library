package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.model.EmployeeBookDTO;
import view.model.builder.EmployeeBookDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeBookMapper {
    public static EmployeeBookDTO convertBookToBookDTO(Book book) {
        return new EmployeeBookDTOBuilder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setPrice(book.getPrice())
                .setStock(book.getStock())
                .build();
    }

    public static Book convertBookDTOToBook(EmployeeBookDTO employeeBookDTO) {
        return new BookBuilder()
                .setTitle(employeeBookDTO.getTitle())
                .setAuthor(employeeBookDTO.getAuthor())
                .setPublishedDate(LocalDate.of(2010, 1, 1))
                .setPrice(employeeBookDTO.getPrice())
                .setStock(employeeBookDTO.getStock())
                .build();
    }

    public static List<EmployeeBookDTO> convertBookListToBookDTOList(List<Book> books) {
        return books.parallelStream()
                .map(EmployeeBookMapper::convertBookToBookDTO)
                .collect(Collectors.toList());
    }

    public static List<Book> convertBookDTOListToBookList(List<EmployeeBookDTO> employeeBookDTOS) {
        return employeeBookDTOS.parallelStream()
                .map(EmployeeBookMapper::convertBookDTOToBook)
                .collect(Collectors.toList());
    }
}
