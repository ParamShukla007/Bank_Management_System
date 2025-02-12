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
import javax.swing.*;
import java.sql.*;
import javax.imageio.ImageIO;

public class PinChange extends JFrame implements ActionListener{
    
    JPasswordField t1,t2;
    JButton b1,b2;                               
    JLabel l1,l2,l3;
    String pin;
    PinChange(String pin){
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

        JLabel l4 = new JLabel(i1);
        l4.setBounds(0, 0, 960, 1080);
        add(l4);
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setLayout(null);
        
        l1.setBounds(280,330,800,35);
        l4.add(l1);
        
        l2.setBounds(180,390,150,35);
        l4.add(l2);
        
        l3.setBounds(180,440,200,35);
        l4.add(l3);
        
        t1.setBounds(350,390,180,25);
        l4.add(t1);
        
        t2.setBounds(350,440,180,25);
        l4.add(t2);
        
        b1.setBounds(390,588,150,35);
        l4.add(b1);
        
        b2.setBounds(390,633,150,35);
        l4.add(b2);
        
        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1)
        {
            try{
                String newPin = t1.getText();
                String rePin = t2.getText();

                if(!newPin.equals(rePin))
                {
                    JOptionPane.showMessageDialog(null, "Entered Pin Does not match the Re-entered Pin");
                    return;
                }
            
                if(newPin.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please enter Pin");
                    return;
                }
                
                if(rePin.equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please re-enter new Pin");
                    return;
                }
                Conn c = new Conn();
                String query1 = "Update bank set pin = '"+rePin+"' where pin = '"+pin+"'";
                String query2 = "Update login set pin = '"+rePin+"' where pin = '"+pin+"'";
                String query3 = "Update add3_user_details set pin = '"+rePin+"' where pinNo = '"+pin+"'";
                
                c.s.executeUpdate(query1);
                c.s.executeUpdate(query2);
                c.s.executeUpdate(query3);
                
                JOptionPane.showMessageDialog(null,"PIN changed succesfully");
            
                setVisible(false);
                new Transaction(rePin).setVisible(true);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            setVisible(false);
            new Transaction(pin).setVisible(true);
        }
    }

    public static void main(String[] args){
        new PinChange("").setVisible(true);
    }
}
