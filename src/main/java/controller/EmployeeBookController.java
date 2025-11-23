package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import launcher.AdminComponentFactory;
import launcher.SaleComponentFactory;
import mapper.EmployeeBookMapper;
import service.book.BookService;
import view.EmployeeView;
import view.model.EmployeeBookDTO;
import view.model.builder.EmployeeBookDTOBuilder;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class EmployeeBookController {
    private final EmployeeView bookView;
    private final BookService bookService;
    private final Long currentUserId;
    private final Boolean componentsForTest;

    public EmployeeBookController(EmployeeView bookView, BookService bookService, Boolean componentsForTest, Long currentUserId) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.currentUserId = currentUserId;
        this.componentsForTest = componentsForTest;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
        this.bookView.addTableMouseListener(new UpdateBookListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String priceString = bookView.getPrice();
            String stockString = bookView.getStock();

            if (title.isEmpty() || author.isEmpty() || priceString.isEmpty() || stockString.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Empty fields", "All fields must be filled.");
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
                        bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book added to database.");
                        refreshData();
                    } else {
                        bookView.addDisplayAlertMessage("Save Error", "Database Error", "Problem adding book.");
                    }
                } catch (NumberFormatException e) {
                    bookView.addDisplayAlertMessage("Save Error", "Invalid Format", "Price/Stock must be numbers.");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeBookDTO employeeBookDTO = bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (employeeBookDTO != null) {
                boolean deletionSuccessful = bookService.delete(EmployeeBookMapper.convertBookDTOToBook(employeeBookDTO));

                if (deletionSuccessful) {
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book deleted from database.");
                    bookView.removeBookFromObservableList(employeeBookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error", "Delete Problem", "Database error.");
                }
            } else {
                bookView.addDisplayAlertMessage("Selection Error", "No Book Selected", "Please select a book.");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeBookDTO selectedBook = bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if (selectedBook != null) {
                if (selectedBook.getStock() <= 0) {
                    bookView.addDisplayAlertMessage("Error", "Out of Stock", "Cannot sell a book with 0 stock.");
                    return;
                }

                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Sell Book");
                dialog.setHeaderText("Selling: " + selectedBook.getTitle());
                dialog.setContentText("Please enter quantity:");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(qtyString -> {
                    try {
                        int quantity = Integer.parseInt(qtyString);

                        if (quantity <= 0) {
                            bookView.addDisplayAlertMessage("Error", "Invalid Quantity", "Quantity must be > 0.");
                        } else if (quantity > selectedBook.getStock()) {
                            bookView.addDisplayAlertMessage("Error", "Stock Error",
                                    "Not enough stock! Available: " + selectedBook.getStock());
                        } else {
                            SaleComponentFactory.getInstance(
                                    componentsForTest,
                                    bookView.getPrimaryStage(),
                                    selectedBook,
                                    quantity,
                                    currentUserId
                            ).getSaleView().show();
                        }
                    } catch (NumberFormatException e) {
                        bookView.addDisplayAlertMessage("Error", "Invalid Input", "Please enter a valid number.");
                    }
                });
            } else {
                bookView.addDisplayAlertMessage("Selection Error", "No Book Selected", "Please select a book to sell.");
            }
        }
    }

    private class UpdateBookListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {

                EmployeeBookDTO selectedBook = bookView.getBookTableView().getSelectionModel().getSelectedItem();

                if (selectedBook != null) {
                    EmployeeBookDTO newValues = bookView.showUpdateDialog(selectedBook);

                    if (newValues != null) {
                        String finalTitle = newValues.getTitle().isEmpty() ? selectedBook.getTitle() : newValues.getTitle();
                        String finalAuthor = newValues.getAuthor().isEmpty() ? selectedBook.getAuthor() : newValues.getAuthor();

                        double finalPrice = (newValues.getPrice() == -1) ? selectedBook.getPrice() : newValues.getPrice();
                        int finalStock = (newValues.getStock() == -1) ? selectedBook.getStock() : newValues.getStock();

                        EmployeeBookDTO finalBookDTO = new EmployeeBookDTOBuilder()
                                .setId(selectedBook.getId())
                                .setTitle(finalTitle)
                                .setAuthor(finalAuthor)
                                .setPrice(finalPrice)
                                .setStock(finalStock)
                                .build();

                        boolean success = bookService.update(EmployeeBookMapper.convertBookDTOToBook(finalBookDTO));

                        if (success) {
                            bookView.addDisplayAlertMessage("Success", "Updated", "Book updated successfully.");
                            refreshData();
                        } else {
                            bookView.addDisplayAlertMessage("Error", "Update Failed", "Could not update database.");
                        }
                    }
                }
            }
        }
    }

    public void refreshData() {
        var books = bookService.findAll();

        var bookDTOs = EmployeeBookMapper.convertBookListToBookDTOList(books);

        bookView.refreshTableData(bookDTOs);
    }
}