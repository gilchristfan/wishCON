package org.saJava.GUI;

import org.saJava.connect.WizCON;
import org.saJava.preferences.UserSettings;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;


public class ChangePwd extends JDialog {

    /**
     * Creates new form ChangePwd
     */
    JDialog dialog=this;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    public ChangePwd() {
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
                dispose();
                new Progress("Fetching Data").setVisible(true);
            }
        });
        setBounds(450,200,300,300);
        setDefaultLookAndFeelDecorated(true);
        initComponents();

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                dispose();
                new Progress("Fetching Data").setVisible(true);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                String pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^_&+=])(?=\\S+$).{8,}$";
                String passwd=jPasswordField2.getText();

                if(jPasswordField1.getText().equals("")){
                    JOptionPane.showMessageDialog(dialog,"Old Password Field cannot be blank");
                }
                else if(jPasswordField2.getText().equals("")){
                    JOptionPane.showMessageDialog(dialog,"New Password Field cannot be blank");
                }
                else if(!jPasswordField2.getText().equals(jPasswordField3.getText())){
                    JOptionPane.showMessageDialog(dialog,"New Password and confirm password must match");
                }
                else if(!passwd.matches(pattern)){
                    JOptionPane.showMessageDialog(dialog,"Incorrect password format","Oops",
                            JOptionPane.INFORMATION_MESSAGE,new ImageIcon(getClass().getResource("image/error.png")));

                }

                else{
                    String oldpass=jPasswordField1.getText(),newPass=jPasswordField2.getText(),cfPass=jPasswordField3.getText();
                    dispose();
                    new PassChgProg(oldpass,newPass,cfPass).setVisible(true);
                    /*try {


                        System.out.println(oldpass+" "+newPass+" "+cfPass);
                        boolean check= WizCON.changePasswd(oldpass,newPass,cfPass);
                        if(!check
                                && WizCON.message.equals("=Old Password is not matching")){
                            System.out.println(WizCON.message);
                            JOptionPane.showMessageDialog(dialog,"Old Password is wrong.Please Check.");
                            WizCON.message="";

                        }
                        if(!check
                                && WizCON.message.equals("=Password successfully changed")){
                            System.out.println(WizCON.message);
                            JOptionPane.showMessageDialog(dialog,"PassWord Changed Successfully.");
                            WizCON.message="";
                            dispose();
                            new ConnectProg().setVisible(true);

                        }
                        else if(WizCON.message.equals("noRoute")){
                            JOptionPane.showMessageDialog(dialog,"RJ45 Disconnected.Please Check");
                            jButton1.setEnabled(false);
                            jButton2.setEnabled(false);
                            dialog.dispose();
                        }

                        else if(WizCON.message.equals("connectTimeOut")){
                            JOptionPane.showMessageDialog(dialog, "Server Down.Try changing password later.");
                            jButton1.setEnabled(true);
                            jButton2.setEnabled(false);
                            dialog.dispose();
                            new ChangePwd().setVisible(true);
                        }
                        else if(WizCON.message.equals("noEthernet")){
                            jButton2.setEnabled(false);
                            jButton1.setEnabled(false);
                            JOptionPane.showMessageDialog(dialog,"Ethernet may be disabled or not working.Please check.");
                            dialog.dispose();
                        }
                        else if(WizCON.message.equals("passChanged"))   {
                            System.out.print(WizCON.message);
                            JOptionPane.showMessageDialog(dialog,"PassWord Changed Successfully");
                            dispose();
                            WizCON.message="";
                            new ConnectProg().setVisible(true);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }    */

                }
            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                jPasswordField1.setText("");
                jPasswordField2.setText("");
                jPasswordField3.setText("");
            }
        });


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Old Password");

        jLabel2.setText("New Password");

        jLabel3.setText("Confirm Password");



        jButton1.setText("Exit");


        jLabel5.setText("Hello");

        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        UserSettings userSettings=new UserSettings();
        userSettings.setPreference();
        jLabel6.setText(userSettings.prefs.get(userSettings.pUname,""));



        jButton2.setText("Save");

        jButton3.setText("Clear");

        jLabel4.setForeground(new java.awt.Color(204, 51, 0));
        jLabel4.setText("*The Password must contain atleast one letter from a-z and A-Z");

        jButton1.setText("Exit");


        jLabel5.setText("Hello");

        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText(WizCON.username);

        jButton2.setText("Save");

        jButton3.setText("Clear");

        jLabel7.setForeground(new java.awt.Color(204, 51, 0));
        jLabel7.setText("*Must contain a digit from 0-9");

        jLabel8.setForeground(new java.awt.Color(204, 51, 0));
        jLabel8.setText("*Must contain a special char from @#$%*^_&+=");

        jLabel9.setIcon(new ImageIcon(getClass().getResource("image/magic.png")));

        jLabel10.setForeground(new java.awt.Color(204, 51, 0));
        jLabel10.setText("*Total password length must be altleast 8");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel9)
                                                .addGap(107, 107, 107)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(47, 47, 47)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel1))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(19, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(111, 111, 111))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(26, 26, 26))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(58, 58, 58))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel10)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(jButton2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1)))
                                                .addGap(72, 72, 72))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton3});

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>
 }
