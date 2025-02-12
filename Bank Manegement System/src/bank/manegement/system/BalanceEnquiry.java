/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank.manegement.system;

/**
 *
 * @author PARAM
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.*;
import javax.imageio.ImageIO;

public class BalanceEnquiry extends JFrame implements ActionListener{
    JButton b1;
    JLabel l1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        ImageIcon i1 = null;
        try {
            File imageFile = new File("C:\\Users\\PARAM\\OneDrive\\Desktop\\NFC 3.1\\Bank Manegement System\\src\\icons\\atm.jpg");
            Image image = ImageIO.read(imageFile);
            Image scaledImage = image.getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
            i1 = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        JLabel l3 = new JLabel(i1);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);
        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        b1.setBounds(390, 633, 150, 35);
        l3.add(b1);
        int balance = 0;
        try{
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
            while (rs.next()) {
               if(rs.getString("type").equals("Deposit") || rs.getString("type").equals("Transfer Amount Deposited")){
                           balance += Integer.parseInt(rs.getString("amount"));
                }else{
                           balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        l1.setText("Your Current Account Balance is Rs "+balance);

        b1.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transaction(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}
