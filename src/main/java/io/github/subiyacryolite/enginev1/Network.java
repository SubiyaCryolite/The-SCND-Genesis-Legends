package io.github.subiyacryolite.enginev1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by ifung on 22/04/2017.
 */
public class Network {
    public boolean downloadFile(String source, String destination) {
        boolean managedToDownload;
        int bufferSize = 1024;
        File out = new File(destination);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(source).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize)) {
            byte[] data = new byte[bufferSize];
            int x;
            while ((x = bufferedInputStream.read(data, 0, bufferSize)) >= 0) {
                bufferedOutputStream.write(data, 0, x);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            managedToDownload = true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            managedToDownload = false;
        }
        return managedToDownload;
    }
}
