package bank.manegement.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class MiniStatement extends JFrame implements ActionListener {
    JButton b1, b2;
    JLabel l1, l3, l4;
    String pin;

    MiniStatement(String pin) {
        super("Mini Statement");
        this.pin = pin;

        getContentPane().setBackground(Color.WHITE);
        setSize(400, 600);
        setLocation(20, 20);

        l1 = new JLabel();
        add(l1);

        JLabel l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 20, 100, 20);
        add(l2);

        l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);

        l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);
        add(l4);

        try {
            Conn c1 = new Conn();
            String query = "select * from login where pin = '" + pin + "'";
            ResultSet rs = c1.s.executeQuery(query);
            while (rs.next()) {
                l3.setText("Card Number: " + rs.getString("cardNo").substring(0, 4) + "XXXXXXXX" + rs.getString("cardNo").substring(12));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            int balance = 0;
            Conn c = new Conn();
            String query1 = "select * from bank where pin = '" + pin + "'";
            ResultSet rs = c.s.executeQuery(query1);
            StringBuilder transactions = new StringBuilder();

            while (rs.next()) {
                String transaction = rs.getString("date") + "   " + rs.getString("type") + "   " + rs.getString("amount");
                transactions.append(transaction).append("\n");

                l1.setText(l1.getText() + "<html>" + transaction + "<br><br><html>");
                if (rs.getString("type").equals("Deposit") || rs.getString("type").equals("Transfer Amount Deposited")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            l4.setText("Your total Balance is Rs " + balance);
        } catch (Exception e) {
            System.out.println(e);
        }

        setLayout(null);
        b1 = new JButton("Exit");
        add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Download PDF");
        add(b2);
        b2.addActionListener(this);

        l1.setBounds(20, 140, 400, 200);
        b1.setBounds(20, 500, 100, 25);
        b2.setBounds(240, 500, 120, 25);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            this.setVisible(false);
        } else if (ae.getSource() == b2) {
            generatePDF();
        }
    }

    public void generatePDF() {
    Document document = new Document();
    try {
        // Saving the file in the Downloads folder
        String downloadPath = System.getProperty("user.home") + "/Downloads/";
        String filename = downloadPath + "MiniStatement_" + pin + ".pdf";

        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        // Adding Title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Mini Statement - Indian Bank\n\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Card Number
        Paragraph cardNumber = new Paragraph("Card Number: " + l3.getText().substring(13) + "\n", 
                                             new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        document.add(cardNumber);

        // Add some space before the table
        document.add(new Paragraph("\n\n"));

        // Transactions Table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Date");
        table.addCell("Type");
        table.addCell("Amount");

        Conn c = new Conn();
        String query1 = "select * from bank where pin = '" + pin + "'";
        ResultSet rs = c.s.executeQuery(query1);

        while (rs.next()) {
            table.addCell(rs.getString("date"));
            table.addCell(rs.getString("type"));
            table.addCell(rs.getString("amount"));
        }
        document.add(table);

        // Add some space before the balance
        document.add(new Paragraph("\n\n"));

        // Balance
        document.add(new Paragraph(l4.getText(), new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));

        document.close();
        
        JOptionPane.showMessageDialog(null, "Mini Statement PDF downloaded successfully!");
    } catch (Exception e) {
        System.out.println(e);
        JOptionPane.showMessageDialog(null, "Error generating PDF! ");
    }
    }


    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);
    }
}
