package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import utility.PollyConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Vector;

public class Report {
    DefaultTableModel model;
    FileOutputStream dest;
    JTable table;

    String REPORT_PREFIX = "POLLY_RELATÓRIO_COMPRAS_";
    String fileName;

    LocalDateTime now = LocalDateTime.now();

    static Font BOLD = null, NORMAL = null;
    static  BaseFont bBold = null, bNormal = null;
    static {


        try {
            bBold = BaseFont.createFont(PollyConstants.FUTURA_BOLD_PATH, BaseFont.WINANSI, BaseFont.EMBEDDED);
            bNormal =  BaseFont.createFont(PollyConstants.FUTURA_LIGHT_PATH, BaseFont.WINANSI, BaseFont.EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BOLD = new Font(bBold, 10);
        NORMAL = new Font(bNormal, 8);

        BOLD.setColor(BaseColor.WHITE);
        NORMAL.setColor(BaseColor.WHITE);
    }

    public Report(JTable table, String dest) {
        this.model = (DefaultTableModel) table.getModel();
        this.table = table;


        this.fileName = dest + "\\" + REPORT_PREFIX + getDateTime() +".pdf";
        try {
            this.dest = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    public void createReport()  {
        Chunk chunk; Paragraph paragraph;
        try {
        Document doc = new Document();

        PdfWriter.getInstance(doc, dest);
        doc.open();
        doc.setPageSize(PageSize.A4);

        Image imagePolly = Image.getInstance(PollyConstants.ASSETSPATH + "images\\pollygray_header_report.png");
        //imagePolly.setAlignment(Element.ALIGN_CENTER);

        int indentation = 0;
        float scaler = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin() - indentation) / imagePolly.getWidth()) * 100;
        imagePolly.scalePercent(scaler);
        doc.add(imagePolly);


        chunk = new Chunk("Tabela de Compras");
        chunk.setFont(new Font(bBold, 14, 0, BaseColor.BLACK));
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        doc.add(new Paragraph("\n"));
        doc.add(paragraph);
        doc.add(new Paragraph("\n"));

        doc.add(getTable());

        chunk = new Chunk("\nRelatório Gerado em: " + DateFormat.getDateInstance(DateFormat.FULL).format(Date.valueOf(now.toLocalDate())));
        chunk.setFont(new Font(bNormal, 12, 0, BaseColor.BLACK));
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        doc.add(paragraph);



        doc.close();
        dest.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private PdfPTable getTable(){

        String column;
        Chunk chunk;
        PdfPCell cell;


        PdfPTable pTable = new PdfPTable(model.getColumnCount());
        Vector data = model.getDataVector();



        Enumeration<TableColumn> columns = table.getTableHeader().getColumnModel().getColumns();
        while (columns.hasMoreElements()){

            column = columns.nextElement().getIdentifier().toString();
            chunk = new Chunk(column, BOLD);

            cell = new PdfPCell(new Paragraph(chunk));


            //headerCell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(10f);
            cell.setBackgroundColor(
                    new BaseColor(
                            PollyConstants.BROWN.getRed(),
                            PollyConstants.BROWN.getGreen(),
                            PollyConstants.BROWN.getBlue()
                    )
            );


            pTable.addCell(cell);
        }

        int r = 0;
        for (Object o: data) {
            Vector row = (Vector) o;

            for (int i = 0; i < row.size(); i++) {

                column = row.get(i).toString();
                chunk = new Chunk(column, NORMAL);
                cell = new PdfPCell(new Paragraph(chunk));

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(10f);

                if (r % 2 == 0){
                    cell.setBackgroundColor(
                            new BaseColor(
                                    PollyConstants.DARK.getRed(),
                                    PollyConstants.DARK.getGreen(),
                                    PollyConstants.DARK.getBlue()
                            )
                    );
                }else{
                    cell.setBackgroundColor(
                            new BaseColor(
                                    Color.DARK_GRAY.getRed(),
                                    Color.DARK_GRAY.getGreen(),
                                    Color.DARK_GRAY.getBlue()
                            )
                    );
                }

                pTable.addCell(cell);
            }
            r++;
        }

        return pTable;
    }

    public String getFileName() {
        return fileName;
    }

    private String getDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");


        return dtf.format(now);
    }

    public static void main(String[] args) {

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Coluna 1", "Coluna 2"});
        for (int i = 0; i < 10; i+=2) {
            model.addRow(new String[]{"Row "+ i, "Row "+ (i+1)});
        }
        JTable t = new JTable(model);


        Report rp = new Report(t, "C:\\Users\\tux\\OneDrive\\Desktop");
        rp.createReport();
        open(rp.getFileName());
        //open("C:\\Users\\tux\\Downloads\\Documents\\lecture1.pdf");

    }


    public static void open(String file){
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.open(new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
