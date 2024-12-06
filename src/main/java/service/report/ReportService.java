package service.report;

import model.Order;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {
    void generateReport(List<Order> orders, String path) throws FileNotFoundException;
}
