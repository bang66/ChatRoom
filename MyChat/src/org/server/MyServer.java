package org.server;

import org.server.MyServerRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyServer {
    public static List<Object> socketList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        HashMap<Integer,Integer> map=new HashMap<>();
        try {
            ServerSocket ss=new ServerSocket(30000);
            int i=0;
            String text=null;
            while (true){
                Socket socket=ss.accept();
                map.put(i,socket.getPort());
                i++;
                for (int j=0;j<map.size();j++) {
                    text = j + "\t" + map.get(j);
                    System.out.println(text);
                }
                socketList.add(socket);
                new Thread(new MyServerRunnable(socket,map)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
