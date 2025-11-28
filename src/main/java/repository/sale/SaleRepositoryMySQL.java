package repository.sale;

import database.DatabaseConnectionFactory;
import model.Sale;
import model.builder.SaleBuilder;
import view.model.SaleReportDTO;
import view.model.builder.SaleReportDTOBuilder;

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
    public List<SaleReportDTO> findAllSalesLastMonth() {
        List<SaleReportDTO> reportData = new ArrayList<>();

        String sql = "SELECT s.id, s.quantity, s.total_price, " +
                "       b.title AS book_title, " +
                "       u1.username AS employee_name, " +
                "       u2.username AS customer_name " +
                "FROM sale s " +
                "JOIN book b ON s.book_id = b.id " +
                "JOIN user u1 ON s.employee_id = u1.id " +
                "JOIN user u2 ON s.customer_id = u2.id " +
                "WHERE s.sale_date >= NOW() - INTERVAL 1 MONTH";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                SaleReportDTO dto = new SaleReportDTOBuilder()
                        .setSaleId(resultSet.getLong("id"))
                        .setBookTitle(resultSet.getString("book_title"))
                        .setEmployeeName(resultSet.getString("employee_name"))
                        .setCustomerName(resultSet.getString("customer_name"))
                        .setQuantity(resultSet.getInt("quantity"))
                        .setTotalPrice(resultSet.getDouble("total_price"))
                        .build();

                reportData.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportData;
    }
}