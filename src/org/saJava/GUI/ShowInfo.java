package org.saJava.GUI;

import org.saJava.connect.WizCON;
import org.saJava.connect.WizOUT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/27/13
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowInfo extends JDialog {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private JButton jButton3;
    private JButton jButton4;
    JDialog frame=this;

    public ShowInfo(){
        setDefaultLookAndFeelDecorated(true);
        setVisible(true);
        setResizable(false);
        setBounds(400,200,300,300);
        setTitle("Wish2Connect");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        jLabel2.setText(WizCON.username);
        jLabel5.setText(WizCON.expiry);
        jLabel6.setText(WizCON.diff);

        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
                frame.dispose();
                System.exit(0);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                try {
                    if(WizOUT.disConnect()){
                        System.out.println("Logged out in launchUI");
                        ImageIcon icon=new ImageIcon(getClass().getResource("image/success.png"));
                        JOptionPane.showMessageDialog(frame,"Successfully logged out","WizOUT",JOptionPane.INFORMATION_MESSAGE,
                                icon);
                        jButton1.setEnabled(true);
                        jButton2.setEnabled(false);
                        frame.dispose();
                        WizCON.status=false;
                    }
                    else if(WizCON.message.equals("noRoute")){
                        JOptionPane.showMessageDialog(frame,"RJ45 Disconnected.Please Check");
                        jButton1.setEnabled(false);
                        jButton2.setEnabled(false);
                        frame.dispose();
                    }
                    else if(WizCON.message.equals("mLogout")){
                        JOptionPane.showMessageDialog(frame,"You have already logged out from browser.");
                        jButton1.setEnabled(true);
                        jButton2.setEnabled(false);
                        //frame.setVisible(false);
                        frame.dispose();
                    }
                    else if(WizCON.message.equals("connectTimeOut")){
                        JOptionPane.showMessageDialog(frame,"Server Down.Try Logout Later");
                        jButton1.setEnabled(true);
                        jButton2.setEnabled(false);
                    }
                    else if(WizCON.message.equals("noEthernet")){
                        jButton2.setEnabled(false);
                        jButton1.setEnabled(false);
                        JOptionPane.showMessageDialog(frame,"Ethernet may be disabled or not working.Please check.");
                        frame.dispose();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                WizCON.message="";
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        //To change body of implemented methods use File | Settings | File Templates.
                        new launchUI().setVisible(true);
                    }
                });

            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                frame.dispose();
                System.exit(0);
            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
               // WizCON.fetchMonths();
                frame.dispose();
                new ChangePwd().setVisible(true);
            }
        });

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                if(WizCON.fetchMonths()){
                    dispose();
                    System.out.println("Usage clicked");
                    WizCON.message="";
                    new ShowUsage().setVisible(true);

                }
                else if(WizCON.message.equals("noRoute")){
                    JOptionPane.showMessageDialog(frame,"RJ45 Disconnected.Please Check","Oops",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(false);
                    frame.dispose();
                    WizCON.message="";
                    new Progress("Fetching Data").setVisible(true);
                }
                else if(WizCON.message.equals("mLogout")){
                    JOptionPane.showMessageDialog(frame,"You have already logged out from browser.","Logged Out",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/success.png")));
                    jButton1.setEnabled(true);
                    jButton2.setEnabled(false);
                    //frame.setVisible(false);
                    frame.dispose();
                    WizCON.message="";
                    new Progress("Fetching Data").setVisible(true);
                }
                else if(WizCON.message.equals("connectTimeOut")){
                    JOptionPane.showMessageDialog(frame,"Server Down.Try Logout Later","Oops",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                    jButton1.setEnabled(true);
                    jButton2.setEnabled(false);
                    WizCON.message="";
                    dispose();
                    new Progress("Fetching Data").setVisible(true);
                }
                else if(WizCON.message.equals("noEthernet")){
                    jButton2.setEnabled(false);
                    jButton1.setEnabled(false);
                    JOptionPane.showMessageDialog(frame, "Ethernet may be disabled or not working.Please check.", "Oops", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                    frame.dispose();
                    WizCON.message="";
                    new Progress("Fetching Data").setVisible(true);
                }

            }
        });

    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        ImageIcon icon=new ImageIcon(getClass().getResource("image/magic.png"));
        jLabel7 = new javax.swing.JLabel(icon);
        jLabel8=new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Hello");

        jLabel2.setForeground(new Color(66, 53, 143));
        jLabel2.setText("jLabel2");

        jLabel3.setText("Expiry Date");

        jLabel4.setText("Time Remaining");

        jLabel5.setForeground(new java.awt.Color(204, 0, 0));
        jLabel5.setText("jLabel5");

        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("jLabel6");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Logout");

        //jLabel7.setText("jLabel7");
        jLabel7.setToolTipText("");
        jLabel7.setOpaque(true);

        jLabel8.setForeground(new Color(59, 58, 98));
        jLabel8.setText("Created by Arun@2013 ");

        jButton3.setText("Change Password");

        jButton4.setText("Usage");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel7)
                                                .addGap(46, 46, 46)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton4))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(44, 44, 44)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel4))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(36, 36, 36)
                                                                .addComponent(jButton1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton3)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addGap(104, 104, 104))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel7)
                                        .addComponent(jButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
}
