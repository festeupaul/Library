package repository.sale;

import database.DatabaseConnectionFactory;
import model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryMySQL implements SaleRepository {

    private final Connection connection;

    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Sale sale) {
        String sql = "INSERT INTO sale (book_id, customer_id, employee_id, sale_date, quantity, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, sale.getBookId());
            statement.setLong(2, sale.getCustomerId());
            statement.setLong(3, sale.getEmployeeID());
            statement.setTimestamp(4, Timestamp.valueOf(sale.getSaleDate()));
            statement.setInt(5, sale.getQuantity());
            statement.setDouble(6, sale.getTotalPrice());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Sale> findAllSalesLastMonth() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM sale " +
                "WHERE sale_date >= " +
                "NOW() - INTERVAL 1 MONTH";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Sale sale = new Sale(
                        resultSet.getLong("id"),
                        resultSet.getLong("book_id"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("employee_id"),
                        resultSet.getTimestamp("sale_date").toLocalDateTime(),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total_price")
                );
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}