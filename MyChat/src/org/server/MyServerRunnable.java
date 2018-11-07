package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

public class MyServerRunnable implements Runnable {
    Socket socket=null;
    BufferedReader br=null;
    HashMap<Integer,Integer> map=null;
    public MyServerRunnable(Socket socket, HashMap<Integer, Integer> map){
        this.map=map;
        this.socket=socket;
        try {
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String content=null;
        PrintStream ps=null;
        while ((content=readText()) != null){
            for (Object o:MyServer.socketList){
                try {
                    ps=new PrintStream(((Socket) o).getOutputStream());
//                    ps.println(content);
//                    ps.flush();
//                    ps.println();
                    if (content != null){
                        if (content.contains("@")) {
                            String[] strs = content.split("@");
                            String s=null;
                            for (int j = 0; j < map.size(); j++){
                                if (strs[0].equals(String.valueOf(j)) || strs[1].equals(String.valueOf(j))) {
                                    if (((Socket) o).getPort() == map.get(j)) {
                                        ps.println(content);
                                        ps.flush();
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } else {
                            ps.println(content);
                            ps.flush();
                        }
                    } else {
                        continue;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
    public String readText(){
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("已删除该客户端："+socket.getInetAddress().getHostName());
            MyServer.socketList.remove(socket);
            e.printStackTrace();
        }
        return null;
    }
}
