package repository.sale;

import model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}