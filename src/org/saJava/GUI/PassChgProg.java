package org.saJava.GUI;

import org.saJava.connect.WizCON;
import org.saJava.connect.WizOUT;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 3/8/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PassChgProg extends JDialog implements Runnable {

    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    Thread thread;
    Task task;
    int progress=0;
    JDialog frame=this,dialog=this;
    String oldPass,newPass,cfPass;
    boolean suspend=true;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
        if(suspend)  {
            for(int i=1;i<=19;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                System.out.println(i);
                jProgressBar1.setValue(i*5);
            }
        }

    }

    class Task extends SwingWorker<Void,Void> {


        @Override
        public Void doInBackground() throws IOException {


            setProgress(0);

            while (progress<100) {
                //Experiment code
                try {
                    System.out.println(oldPass+" "+newPass+" "+cfPass);
                    boolean check= WizCON.changePasswd(oldPass,newPass,cfPass);
                    if(!check
                            && WizCON.message.equals("=Old Password is not matching")){
                        System.out.println(WizCON.message);
                        JOptionPane.showMessageDialog(dialog,"Old Password is wrong.Please Check.");


                    }
                    if(!check
                            && WizCON.message.equals("=Password successfully changed")){
                        System.out.println(WizCON.message);
                        JOptionPane.showMessageDialog(dialog,"PassWord Changed Successfully","WizChange",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/success.png")));
                        WizCON.message="";
                        dispose();
                        //new ConnectProg().setVisible(true);

                    }
                    else if(WizCON.message.equals("noRoute")){
                        JOptionPane.showMessageDialog(dialog,"RJ45 Disconnected.Please Check","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));

                        dialog.dispose();
                    }

                    else if(WizCON.message.equals("connectTimeOut")){
                        JOptionPane.showMessageDialog(dialog,"Server Down.Try Changing password later.","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));

                        dialog.dispose();

                    }
                    else if(WizCON.message.equals("noEthernet")){

                        JOptionPane.showMessageDialog(dialog,"Ethernet may be disabled or not working properly","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));
                        dialog.dispose();
                    }
                    else if(WizCON.message.equals("passChanged"))   {
                        System.out.print(WizCON.message);
                        JOptionPane.showMessageDialog(dialog,"PassWord Changed Successfully","WizChange",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/success.png")));
                        dispose();
                        WizCON.message="";
                       // new ConnectProg().setVisible(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                break;

            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            thread.suspend();
            setVisible(false);
            dispose();
            if(WizCON.message.equals("=Old Password is not matching"))
                new ChangePwd().setVisible(true);
            else
                new ConnectProg().setVisible(true);
        }
    }


    public PassChgProg(String oldPass,String newPass,String cfPass) {
        this.oldPass=oldPass;
        this.newPass=newPass;
        this.cfPass=cfPass;

        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setBounds(400,200,100,100);
        initComponents();

        thread=new Thread(this);
        thread.start();
        task = new Task();

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

        jLabel1.setText("Changing Pass");

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
