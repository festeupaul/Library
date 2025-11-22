package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.EmployeeBookMapper;
import service.book.BookService;
import view.EmployeeView;
import view.model.EmployeeBookDTO;
import view.model.builder.EmployeeBookDTOBuilder;
import launcher.AdminComponentFactory;

public class EmployeeBookController {
    private final EmployeeView bookView;
    private final BookService bookService;

    public EmployeeBookController(EmployeeView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());

        this.bookView.addBackButtonListener(new BackButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String priceString = bookView.getPrice();
            String stockString = bookView.getStock();

            if (title.isEmpty() || author.isEmpty() || priceString.isEmpty() || stockString.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error",
                        "Empty fields",
                        "All fields (Title, Author, Price, Stock) must be filled.");
            } else {
                try {
                    double price = Double.parseDouble(priceString);
                    int stock = Integer.parseInt(stockString);

                    EmployeeBookDTO employeeBookDTO = new EmployeeBookDTOBuilder()
                            .setTitle(title)
                            .setAuthor(author)
                            .setPrice(price)
                            .setStock(stock)
                            .build();

                    boolean savedBook = bookService.save(EmployeeBookMapper.convertBookDTOToBook(employeeBookDTO));

                    if (savedBook) {
                        bookView.addDisplayAlertMessage("Save Successful",
                                "Book Added",
                                "Book was successfully added to the database.");
                        bookView.addBookToObservableList(employeeBookDTO);
                    } else {
                        bookView.addDisplayAlertMessage("Save Error",
                                "Problem at adding Book",
                                "There was a problem at adding the book to the database.");
                    }
                } catch (NumberFormatException e) {
                    bookView.addDisplayAlertMessage("Save Error",
                            "Invalid Number Format",
                            "Price must be a number (e.g. 10.5) and Stock must be an integer.");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeBookDTO employeeBookDTO = (EmployeeBookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (employeeBookDTO != null) {
                boolean deletionSuccessful = bookService.delete(EmployeeBookMapper.convertBookDTOToBook(employeeBookDTO));

                if (deletionSuccessful) {
                    bookView.addDisplayAlertMessage("Delete Successful",
                            "Book Deleted",
                            "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(employeeBookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error",
                            "Problem at deleting Book",
                            "There was a problem with the database. Please try again.");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error",
                        "Problem at deleting Book",
                        "You must select a book before pressing the delete button.");
            }
        }
    }

    private class BackButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            AdminComponentFactory.getInstance(false, bookView.getPrimaryStage())
                    .getAdminView()
                    .show();
        }
    }
}