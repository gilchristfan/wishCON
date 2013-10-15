package org.saJava.GUI;

import org.saJava.connect.WizCON;
import org.saJava.preferences.UserSettings;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/27/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Progress extends JDialog {

    String name;
    /**
     * Creates new form JDialog
     *  private javax.swing.JLabel jLabel1;
     private javax.swing.JProgressBar jProgressBar1;
     */
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    JDialog frame=this;

    class Task extends SwingWorker<Void,Void>{


        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            try {
                if(WizCON.getUserDetails()){

                    setVisible(false);
                    //WizCON.changePasswd("a","b","c");
                    new ShowInfo();
                    UserSettings userSettings=new UserSettings();
                    userSettings.setPreference();
                    userSettings.prefs.put(userSettings.pSession,"true");
                    if(!WizCON.status)
                        WizCON.launchBrowser();
                    WizCON.status=true;
                }
                else{
                    if(WizCON.message.equals("validityExpire")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame,"Pack validity expired.Please Recharge.","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                    else if(WizCON.message.equals("errorValidate")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame,"Invalid Credentials.Please re-validate.","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                   else if(WizCON.message.equals("noRoute")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame,"RJ45 Disconnected.Please Check","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                    else if(WizCON.message.equals("mLogout")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame, "You have already logged out manually.","Logged out",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/success.png")));
                        new ConnectProg().setVisible(true);
                    }
                    else if(WizCON.message.equals("noEthernet")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame, "Ethernet may be disabled or not working.Please check.","Oops",
                                JOptionPane.INFORMATION_MESSAGE,new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                    else if(WizCON.message.equals("connectTimeOut")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame,"Server Down.Login may not have occurred or unable to fetch data","Oops",JOptionPane.INFORMATION_MESSAGE
                        ,new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                    else if(WizCON.message.equals("connectFail")){
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame,"Server Down.Failed to fetch Data","Oops",JOptionPane.INFORMATION_MESSAGE
                                ,new ImageIcon(getClass().getResource("image/error.png")));
                        new ConnectProg().setVisible(true);
                    }
                    WizCON.message="";
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    public Progress(String name) {
        this.name=name;
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setSize(100,100);
        setBounds(500,200,100,100);
        initComponents();
        Task task = new Task();

        task.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress" == evt.getPropertyName()) {
                    int progress = (Integer) evt.getNewValue();
                    jProgressBar1.setValue(progress);
                }
            }
        });

        task.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(name);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(103, 103, 103)
                                                .addComponent(jLabel1)))
                                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>


}
