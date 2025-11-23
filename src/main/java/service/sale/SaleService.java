package service.sale;

public interface SaleService {
    boolean sell(Long bookId, Long customerId, Long employeeId, int quantity);
}
