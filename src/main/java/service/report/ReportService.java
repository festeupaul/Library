package service.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import model.Sale;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReportService {

    public void generateReport(List<Sale> sales) {
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
                    .mapToDouble(Sale::getTotalPrice)
                    .sum();
            int totalBooksSold = sales.stream()
                    .mapToInt(Sale::getQuantity)
                    .sum();

            document.add(new Paragraph("Generated on: " + LocalDate.now()));
            document.add(new Paragraph("Total Revenue: $" + totalRevenue));
            document.add(new Paragraph("Total Books Sold: " + totalBooksSold));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            addTableHeader(table, "Sale ID");
            addTableHeader(table, "Book ID");
            addTableHeader(table, "Employee ID");
            addTableHeader(table, "Qty");
            addTableHeader(table, "Total Price");

            for (Sale sale : sales) {
                table.addCell(String.valueOf(sale.getId()));
                table.addCell(String.valueOf(sale.getBookId()));
                table.addCell(String.valueOf(sale.getEmployeeID()));
                table.addCell(String.valueOf(sale.getQuantity()));
                table.addCell(String.valueOf(sale.getTotalPrice()));
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