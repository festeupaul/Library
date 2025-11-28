package repository.sale;

import model.Sale;
import view.model.SaleReportDTO;

import java.util.List;

public interface SaleRepository {
    boolean save(Sale sale);
    List<SaleReportDTO> findAllSalesLastMonth();
}
