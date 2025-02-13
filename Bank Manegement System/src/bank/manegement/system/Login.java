/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank.manegement.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author PARAM
 */


public class Login extends JFrame implements ActionListener{
    
    JButton login,signup,clear;
    JTextField cardTextField;
    JPasswordField pinTextField;
    
    Login()
    {
        setTitle("ATM");
        setSize(800,480);
        setVisible(true);
        setLocation(350,200);
        //to get custom layout
        setLayout(null);
        
        getContentPane().setBackground(Color.WHITE);
   
        JLabel text  = new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward",Font.BOLD,38));
        text.setBounds(250,40,400,40);
        add(text);
        
        JLabel cardno  = new JLabel("Card No:");
        cardno.setFont(new Font("Raieway",Font.BOLD,28));
        cardno.setBounds(120,150,400,30);
        add(cardno);
        
        cardTextField = new JTextField();
        cardTextField.setBounds(300,150,230,30);
        cardTextField.setFont(new Font("Arial",Font.BOLD,14));
        add(cardTextField);
        
        JLabel pin  = new JLabel("PIN:");
        pin.setFont(new Font("Raieway",Font.BOLD,28));
        pin.setBounds(120,220,400,30);
        add(pin);
        
        pinTextField = new JPasswordField();
        pinTextField.setBounds(300,220,230,30);
        add(pinTextField);
        
        login = new JButton("SIGN IN");
        login.setBounds(300,300,100,30);
        login.setBackground(Color.black);
        login.setForeground(Color.white);
        login.addActionListener(this);
        add(login);
        
        clear = new JButton("CLEAR");
        clear.setBounds(430,300,100,30);
        clear.setBackground(Color.black);
        clear.setForeground(Color.white);
        clear.addActionListener(this);
        add(clear);
        
        signup = new JButton("SIGN UP");
        signup.setBounds(300,350,230,30);
        signup.setBackground(Color.black);
        signup.setForeground(Color.white);
        signup.addActionListener(this);
        add(signup);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        
        //to adjust the image
        Image i2 = i1.getImage().getScaledInstance(100, 100, WIDTH);
        ImageIcon i3 = new ImageIcon(i2);
        
        JLabel label = new JLabel(i3);
        
        //To change the location
        label.setBounds(100,10,100,100);
        add(label);
        
         setResizable(false);
   
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == clear)
        {
            cardTextField.setText("");
            pinTextField.setText("");
        }
        else if(ae.getSource() == login)
        {
           Conn c = new Conn();
           String cardNo = cardTextField.getText();
           String pinNo = pinTextField.getText();
           System.out.println(pinNo);
           String query = "select * from login where cardNo ='"+cardNo+"' and pin = '"+pinNo+"'";
           try
           {
              ResultSet rs =  c.s.executeQuery(query);
               if(rs.next())
               {
                   setVisible(false);
                   new Transaction(pinNo).setVisible(true);
               }
               else
               {
                  JOptionPane.showMessageDialog(null,"Incorrect Card Number or Pin");
               }
           }
           catch(Exception e)
           {
               System.out.println(e);
           }
        }
        else if(ae.getSource() == signup)
        {
            setVisible(false);
            new SignUp().setVisible(true);
        }
    }
    
    public static void main(String args[])
    {
        new Login();
    }
}
