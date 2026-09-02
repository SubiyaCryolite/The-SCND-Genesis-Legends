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

import com.scndgen.legends.constants.NetworkConstants;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

/**
 * Client session worker: connects to a host and pumps UTF messages via queues.
 */
public class NetworkClient extends NetworkBase {

    private final String serverIpAddress;

    public NetworkClient(String ip) {
        serverIpAddress = ip;
        running = true;
        sendData(NetworkConstants.CONNECT_TO_HOST);
        startWorker("net-client", this::runSession);
    }

    private void runSession() {
        try {
            var address = InetAddress.getByName(serverIpAddress);
            System.out.printf("Attempting to connect to %s%n", address);
            try (var connected = new Socket(address, NetworkManager.PORT)) {
                System.out.println("Connected to " + connected.getInetAddress());
                runConnectedSocket(connected);
            }
        } catch (Exception ex) {
            if (running) {
                System.err.println(Objects.requireNonNullElse(ex.getMessage(), ex.toString()));
                signalSessionError();
            }
        } finally {
            closeSockets();
            System.out.println("Closed the Client");
        }
    }
}
