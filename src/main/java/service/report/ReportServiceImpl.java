package service.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import model.Order;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService{
    @Override
    public void generateReport(List<Order> orders, String path) throws FileNotFoundException {

        LocalDate date = LocalDate.now();
        int year = date.getYear();
        Month month = date.getMonth();

        List<Order> monthOrders = orders.stream()
                .filter(order -> order.getDate().getYear() == year &&
                                order.getDate().getMonth() == month)
                .collect(Collectors.toList());

        Map<Long, List<Order>> ordersByUserId = monthOrders.stream()
                .collect(Collectors.groupingBy(Order::getUserId));

        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addNewPage();
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Orders Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16));

        document.add(new Paragraph("~ " + month + " " + year + " ~")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14));

        for (Map.Entry<Long, List<Order>> userEntry: ordersByUserId.entrySet()) {
            Long userId = userEntry.getKey();
            List<Order> userOrders = userEntry.getValue();

            document.add(new Paragraph("Employee ID: " + userId)
                    .setBold()
                    .setFontSize(12));

            Table table = new Table(6);
            table.addHeaderCell(new Cell().add(new Paragraph("Order ID"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Book's Author"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Book's Title"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Quantity"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Price"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Order Date"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());

            for (Order order: userOrders) {
                table.addCell(String.valueOf(order.getId()))
                        .setTextAlignment(TextAlignment.CENTER);
                table.addCell(order.getAuthor())
                        .setTextAlignment(TextAlignment.CENTER);
                table.addCell(order.getTitle())
                        .setTextAlignment(TextAlignment.CENTER);
                table.addCell(String.valueOf(order.getQuantity()))
                        .setTextAlignment(TextAlignment.CENTER);
                table.addCell(String.valueOf(order.getPrice()))
                        .setTextAlignment(TextAlignment.CENTER);
                String formattedDate = order.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                table.addCell(formattedDate)
                        .setTextAlignment(TextAlignment.CENTER);
            }
            document.add(table);
            document.add(new Paragraph("\n"));
        }
        document.close();
    }
}
