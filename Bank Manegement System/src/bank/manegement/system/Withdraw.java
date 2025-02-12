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
import javax.swing.*;
import java.util.Date;
import java.sql.*;
import javax.imageio.ImageIO;
import java.io.File;
public class Withdraw extends JFrame implements ActionListener{
    
    JTextField t1,t2;
    JButton b1,b2,b3;
    JLabel l1,l2,l4;
    String pin;
    Withdraw(String pin){
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
        
        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        
        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");
        
        setLayout(null);
        
        l1.setBounds(190,350,400,20);
        l3.add(l1);
        
        l2.setBounds(190,400,400,20);
        l3.add(l2);
        
        t1.setBounds(190,450,330,30);
        l3.add(t1);
        
        b1.setBounds(390,588,150,35);
        l3.add(b1);
        
        b2.setBounds(390,633,150,35);
        l3.add(b2);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText();
            Date date = new Date();
            if(ae.getSource()==b1){
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
                }else{
                    Conn c1 = new Conn();
                    
                    ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
                    int balance = 0;
                    while(rs.next()){
                       if(rs.getString("type").equals("Deposit") || rs.getString("type").equals("Transfer Amount Deposited")){
                           balance += Integer.parseInt(rs.getString("amount"));
                       }else{
                           balance -= Integer.parseInt(rs.getString("amount"));
                       }
                    }
                    if(balance < Integer.parseInt(amount)){
                        JOptionPane.showMessageDialog(null, "Insuffient Balance");
                        return;
                    }
                    
                    c1.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawl', '"+amount+"')");
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
                    setVisible(false);
                    new Transaction(pin).setVisible(true);
                }
            }else if(ae.getSource()==b2){
                setVisible(false);
                new Transaction(pin).setVisible(true);
            }
        }catch(Exception e){
    
                System.out.println("error: "+e);
        }
            
    }

    
    public static void main(String[] args){
        new Withdraw("").setVisible(true);
    }
}
