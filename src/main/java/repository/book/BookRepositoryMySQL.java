package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;
    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";
        List<Book> books = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        //String sql = "SELECT * FROM book WHERE id=" + id;
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();
        try{
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
//        String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() + "\', \'" + book.getTitle() +
//                "\', \'" + book.getPublishedDate() + "\' )";
        try {
            String newSql = "INSERT INTO book (author, title, publishedDate, price, stock) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(newSql);

            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Book book) {
//        String newSql = "DELETE FROM book WHERE author=\'" + book.getAuthor() +
//                "\' AND title=\'" + book.getTitle() + "\'";
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());

            int rowsDeleted = preparedStatement.executeUpdate();
            return (rowsDeleted != 1) ? false : true;

//            Statement statement = connection.createStatement();
//            statement.executeUpdate(newSql);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
//        return true;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE book SET author = ?, title = ?, publishedDate = ?, price = ?, stock = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());
            preparedStatement.setLong(6, book.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getDouble("price"))
                .setStock(resultSet.getInt("stock"))
                .build();
    }
}
