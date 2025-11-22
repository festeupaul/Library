package controller;

import service.book.BookService;
import view.CustomerView;
import view.model.CustomerBookDTO;

public class CustomerBookController {
    private final CustomerView customerView;
    private final BookService bookService;

    public CustomerBookController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        initializeTableCLickListener();
    }

    private void initializeTableCLickListener() {
        this.customerView.getBookTableView().setOnMouseClicked(event ->{
            CustomerBookDTO selectedBook = customerView.getBookTableView().getSelectionModel().getSelectedItem();
            if(selectedBook != null && selectedBook.getStock() <= 0){
                customerView.addDisplayAlertMessage(
                        "Out of Stock",
                        "Book Unavailable",
                        "The selected book '" + selectedBook.getTitle() + "' is currently out of stock."
                );
            }
        });
    }
}
