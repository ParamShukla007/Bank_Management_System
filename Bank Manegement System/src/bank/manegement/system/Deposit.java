package bank.manegement.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class Deposit extends JFrame implements ActionListener {
    JTextField t1;
    JButton b1, b2;
    JLabel l1;
    String pin;

    Deposit(String pin) {
        this.pin = pin;

        // Load image correctly
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

        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));

        b1 = new JButton("DEPOSIT");
        b2 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        l3.add(l1);

        t1.setBounds(190, 420, 320, 25);
        l3.add(t1);

        b1.setBounds(390, 588, 150, 35);
        l3.add(b1);

        b2.setBounds(390, 633, 150, 35);
        l3.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String dept = t1.getText();
            Date date = new Date();
            if (dept.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to deposit");
            } else {
                try {
                    Conn c = new Conn();
                    String query = "insert into bank values('" + pin + "','" + date + "','Deposit','" + dept + "')";
                    c.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Rs " + dept + " Deposited Successfully");
                    setVisible(false);
                    new Transaction(pin).setVisible(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Transaction(pin).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
