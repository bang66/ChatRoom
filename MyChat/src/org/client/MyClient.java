package org.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient implements Runnable{

    BufferedReader br=null;
    String text=null;
    Socket s=null;
    PrintStream ps=null;

    public MyClient(Socket s) throws IOException {
        this.s=s;
    }
    @Override
    public void run() {
        while (true){
            try {
                ps=new PrintStream(s.getOutputStream());
                br=new BufferedReader(new InputStreamReader(System.in));
                text=br.readLine();
                ps.println(text);
                ps.flush();
                System.out.println("我："+text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        try {
            Socket socket=new Socket("127.0.0.1",30000);
            int port=socket.getPort();

            BufferedReader br=null;

            String text="";
            new Thread(new MyClient(socket)).start();
            while (true) {
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                text = br.readLine();
//                if (text != null){
//                    if (text.contains("@")) {
//                        String[] strs = text.split("@");
//                        if (strs[0].equals(1) || strs[1].equals(1)) {
//                            System.out.println(text);
//                        } else {
//                            continue;
//                        }
//                    } else {
//                        System.out.println(text);
//                    }
//                } else {
//                    continue;
//                }
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
