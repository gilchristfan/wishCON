package org.saJava.GUI;

import org.saJava.connect.WizCON;
import org.saJava.preferences.UserSettings;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 3/9/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageProg extends JDialog implements Runnable{

    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    Thread thread;
    Task task;
    int progress=0;
    String index,month,monthIndex;
    JDialog frame=this,dialog=this;
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
                UserSettings settings=new UserSettings();
                settings.setPreference();

                if(WizCON.sessionDetails(settings.prefs.get(settings.pUname,""),index,monthIndex))  {
                    jProgressBar1.setValue(100);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    suspend=false;
                    //dispose();
                    //new UsageTable().setVisible(true);
                }
                else if(WizCON.message.equals("connectTimeOut")){
                    thread.suspend();
                    JOptionPane.showMessageDialog(dialog,"Server Down.Try Later.","Oops",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                    //dispose();
                    //new ShowUsage().setVisible(true);
                }
                else if(WizCON.message.equals("noData")) {
                    {
                        //thread.suspend();
                        JOptionPane.showMessageDialog(dialog,"No Data Available for this month","Oops",JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon(getClass().getResource("image/error.png")));
                        //dispose();
                        //new ShowUsage().setVisible(true);
                    }
                }
                else if(WizCON.message.equals("noRoute")){
                    //thread.suspend();
                    JOptionPane.showMessageDialog(dialog,"RJ45 Disconnected.Please Check","Oops",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                   // dispose();
                    //new ShowUsage().setVisible(true);
                }
                else if(WizCON.message.equals("noEthernet")){
                    //thread.suspend();
                    JOptionPane.showMessageDialog(dialog,"Ethernet may be disabled.Please Check","Oops",JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("image/error.png")));
                    //dispose();
                    //new ShowUsage().setVisible(true);
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
            WizCON.message="";
            dispose();
            if(suspend)
                new ShowUsage().setVisible(true);
            else
                new UsageTable().setVisible(true);


        }
    }


    public UsageProg(String index,String monthIndex,String month) {
        this.index=index;
        this.month=month;
        this.monthIndex=monthIndex;

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

        jLabel1.setText("Fetching Usage");

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
