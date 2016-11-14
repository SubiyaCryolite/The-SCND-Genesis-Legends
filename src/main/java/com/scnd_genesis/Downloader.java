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
package com.scnd_genesis;

import java.io.*;
import java.net.URL;

public class Downloader {

    private URL url;
    private BufferedInputStream in;
    private FileOutputStream fos;
    private BufferedOutputStream bout;
    private File out;
    private byte[] data;
    private int x;

    public Downloader() {
        try {
            url = new URL("http://localhost/wordpress/wp-content/uploads/2010/12/Screenie_3_hiy_3947.jpg");
            in = new BufferedInputStream(url.openStream());
            out = new File("scndupd.xml");
            fos = new FileOutputStream(out);
            bout = new BufferedOutputStream(fos, 1024);
            data = new byte[1024];
            x = 0;
            while ((x = in.read(data, 0, 1024)) >= 0) {
                bout.write(data, 0, x);
                System.out.println("Download: " + out.length() / 1000 + "kb");
            }
            bout.close();
            in.close();
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) throws IOException {
        new Downloader();
    }
}
