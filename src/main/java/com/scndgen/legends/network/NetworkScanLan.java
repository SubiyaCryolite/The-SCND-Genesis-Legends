/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.windows.JenesisPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkScanLan extends JFrame implements ActionListener {

    private static JenesisPanel startApp;
    private InetAddress ia;
    private JFrame frame;
    private JPanel buttons, panel, txtP;
    private JButton connect;
    private Object source;
    private int count;
    private JLabel label;
    private JTextField txt;

    public NetworkScanLan() {
        connect = new JButton("Connect");
        connect.addActionListener(this);
        buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(connect);

        label = new JLabel("Server Name");
        txt = new JTextField("", 10);
        txtP = new JPanel();
        txtP.add(label);
        txtP.add(txt);

        panel = new JPanel(new BorderLayout());
        panel.add(txtP, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] arg) throws URISyntaxException {
        new NetworkScanLan();
    }

    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        if (source == connect) {
            try {
                //int v = list.getSelectedIndex();
                LoginScreen.getInstance().setIP(txt.getText());
                System.out.println("Attempting to connect to : " + txt.getText());
                Thread.sleep(0050);
                LoginScreen.getInstance().getMenu().joinGame();
                LoginScreen.getInstance().getMenu().terminateThis();
                frame.dispose();
            } catch (InterruptedException ex) {
                Logger.getLogger(NetworkScanLan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /*
    private void IPOnly(String input)
    {
    if(input.contains(":"))
    {}

    else
    {
    String pimping=input.replaceAll("/","");
    {
    IP.add(pimping);
    Name.add(pimping);
    System.out.print(count+" NAME: "+Name.get(count)+" IP: "+IP.get(count)+"\n");
    count++;
    }
    }
    }

    private void detectMach()
    {
    try
    {
    Enumeration e=NetworkInterface.getNetworkInterfaces();
    while(e.hasMoreElements())
    {
    NetworkInterface ni = (NetworkInterface)e.nextElement();
    //System.out.println(ni.toString());

    Enumeration a=ni.getInetAddresses();
    while(a.hasMoreElements())
    {
    ia=(InetAddress)a.nextElement();
    IPOnly(""+ia.getHostAddress());
    System.out.println("IA: "+ia.getHostAddress());//+" "+ia.getHostAddress()+"    "+ia.getCanonicalHostName());
    }
    }

    }

    catch (Exception ex)
    {
    ex.printStackTrace();
    }
    }*/
}
