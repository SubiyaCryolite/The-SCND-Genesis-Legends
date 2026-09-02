/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Objects;

import static com.scndgen.legends.constants.NetworkConstants.CONNECT_TO_HOST;

/**
 * Host session worker: accepts one client and pumps UTF messages via queues.
 */
public class NetworkServer extends NetworkBase {

    private String hostName = "";
    private String hostAddress = "";

    public NetworkServer() {
        initServerDetails();
        running = true;
        startWorker("net-server", this::runSession);
    }

    private void initServerDetails() {
        try {
            var interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                interfaces.nextElement();
                var local = InetAddress.getLocalHost();
                hostName = local.getHostName();
                hostAddress = local.getHostAddress();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void playerFound() {
        ScndGenLegends.get().engine().ui().push(NkDialogs.yesNo(
                "Heads Up",
                "Someone wants to fight you!",
                "Wanna waste em?",
                answer -> {
                    var networkManager = NetworkManager.get();
                    switch (answer) {
                        case YES -> {
                            networkManager.setConnectedToPartner(true);
                            sendData(CONNECT_TO_HOST);
                            sendData(NetworkConstants.connectToHost(State.get().getLogin().getTimeLimit()));
                        }
                        case NO -> {
                            networkManager.setConnectedToPartner(false);
                            sendData(NetworkConstants.DISCONNECT_FROM_HOST);
                        }
                        default -> {
                        }
                    }
                }
        ));
    }

    private void runSession() {
        try {
            System.out.printf("<Server> Started on %s.%n", InetAddress.getLocalHost());
            try (var listen = new ServerSocket(NetworkManager.PORT, 1)) {
                this.serverSocket = listen;
                listen.setSoTimeout(0);
                try (var connected = listen.accept()) {
                    System.out.printf("Client [%s] connected to server.%n", connected);
                    if (this.serverSocket == listen) {
                        this.serverSocket = null;
                    }
                    runConnectedSocket(connected);
                }
            }
        } catch (Exception ex) {
            if (running) {
                System.err.println(Objects.requireNonNullElse(ex.getMessage(), ex.toString()));
                signalSessionError();
            }
        } finally {
            closeSockets();
            System.out.println("Closed the server");
        }
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostAddress() {
        return hostAddress;
    }
}
