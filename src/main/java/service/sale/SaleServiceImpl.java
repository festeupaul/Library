package service.sale;

import model.Book;
import model.Sale;
import model.builder.SaleBuilder;
import repository.sale.SaleRepository;
import service.book.BookService;

import java.time.LocalDateTime;

public class SaleServiceImpl implements SaleService {

    private final BookService bookService;
    private final SaleRepository saleRepository;

    public SaleServiceImpl(BookService bookService, SaleRepository saleRepository) {
        this.bookService = bookService;
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean sell(Long bookId, Long customerId, Long employeeId, int quantity) {

        Book book = bookService.findById(bookId);

        if (book == null) {
            return false;
        }

        if (book.getStock() < quantity) {
            return false;
        }

        int newStock = book.getStock() - quantity;
        book.setStock(newStock);

        boolean stockUpdated = bookService.update(book);
        if (!stockUpdated) {
            return false;
        }

        double totalPrice = book.getPrice() * quantity;

        Sale sale = new SaleBuilder()
                .setId(null)
                .setBookId(bookId)
                .setCustomerId(customerId)
                .setEmployeeID(employeeId)
                .setSaleDate(LocalDateTime.now())
                .setQuantity(quantity)
                .setTotalPrice(totalPrice)
                .build();


        return saleRepository.save(sale);
    }
}