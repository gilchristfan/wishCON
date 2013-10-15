package org.saJava.connect;

import org.saJava.GUI.launchUI;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 3/10/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class WizSysTray {
    public  void getSetSysTray(){
        TrayIcon trayIcons[]=SystemTray.getSystemTray().getTrayIcons();
        for(int i=0;i<trayIcons.length;i++){
            SystemTray.getSystemTray().remove(trayIcons[i]);
        }

        MenuItem exitItem=new MenuItem("Exit");
        PopupMenu popupMenu=new PopupMenu();

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.

                System.exit(0);
            }
        });

        popupMenu.add(exitItem);

        Image image=Toolkit.getDefaultToolkit().getImage(getClass().getResource("magic.png"));
        SystemTray tray=SystemTray.getSystemTray();
        final TrayIcon trayIcon=new TrayIcon(image,"System Tray",popupMenu);

        try {
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

        } catch (AWTException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                new launchUI().setVisible(true);
            }
        });
    }
}
