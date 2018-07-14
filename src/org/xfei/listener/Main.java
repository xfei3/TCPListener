package org.xfei.listener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {
    private static void log(String path, String content) {
        try {
            File strFile = new File(path + "/" + new Date().getTime() + ".txt");
            boolean fileCreated = strFile.createNewFile();
            Writer objWriter = new BufferedWriter(new FileWriter(strFile));
            objWriter.write(content);
            objWriter.flush();
            objWriter.close();
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) {
        String path = "/tmp";
        Integer port = 4444;
        if (args.length == 2) {
            port = Integer.valueOf(args[0]);
            path = args[1];
        }
        System.out.println("Usage: java -jar listerner.jar {port number} {path/to/log}");
        System.out.println("Using port " + port + " and path " + path);

        ServerSocket listener;
        BufferedReader input;
        String data;
        DataInputStream dataStream;
        StringBuffer buf;
        Socket socket;
        try {
            listener = new ServerSocket(port);
            while (true) {
                socket = listener.accept();
                try {
                    dataStream = new DataInputStream(socket.getInputStream());
                    if (dataStream.available() > 0) {
                        input = new BufferedReader(new InputStreamReader(dataStream));
                        buf = new StringBuffer();
                        while ((data = input.readLine()) != null) {
                            buf.append(data);
                        }
                        log(path, buf.toString());
                    }
                    PrintWriter out =
                            new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                    listener.close();
                } catch (Exception ex) {

                } finally {
                    socket.close();
                }
            }
        } catch (Exception ex) {

        }
    }
}
