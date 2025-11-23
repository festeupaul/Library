package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {
    private final List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean update(Book book) {
        Optional<Book> existingBook = findById(book.getId());

        if (existingBook.isPresent()) {
            books.remove(existingBook.get());
            books.add(book);
            return true;
        }

        return false;
    }
}
