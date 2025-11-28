package service.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import view.model.SaleReportDTO;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReportService {

    public void generateReport(List<SaleReportDTO> sales) {
        Document document = new Document();
        try {
            String filename = "Monthly_Sales_Report_" + LocalDate.now() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Monthly Sales Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            double totalRevenue = sales.stream()
                    .mapToDouble(SaleReportDTO::getTotalPrice)
                    .sum();
            int totalBooksSold = sales.stream()
                    .mapToInt(SaleReportDTO::getQuantity)
                    .sum();

            document.add(new Paragraph("Generated on: " + LocalDate.now()));
            document.add(new Paragraph("Total Revenue: $" + String.format("%.2f", totalRevenue)));
            document.add(new Paragraph("Total Books Sold: " + totalBooksSold));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.setWidths(new float[]{1, 4, 3, 3, 1, 2});

            addTableHeader(table, "ID");
            addTableHeader(table, "Book Title");
            addTableHeader(table, "Employee");
            addTableHeader(table, "Customer");
            addTableHeader(table, "Qty");
            addTableHeader(table, "Price");

            for (SaleReportDTO sale : sales) {
                table.addCell(String.valueOf(sale.getSaleId()));
                table.addCell(sale.getBookTitle());
                table.addCell(sale.getEmployeeName());
                table.addCell(sale.getCustomerName());
                table.addCell(String.valueOf(sale.getQuantity()));
                table.addCell("$" + sale.getTotalPrice());
            }

            document.add(table);
            document.close();

            System.out.println("PDF generated successfully: " + filename);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(Color.LIGHT_GRAY);
        header.setPhrase(new Phrase(headerTitle));
        table.addCell(header);
    }
}