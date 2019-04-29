package com.example.hd.pwpadfornsw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import android.os.Handler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final int PORT = 5000;

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream inputStream;
    private Handler handler;

    private String read;
    private char command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    socket = serverSocket.accept();
                    inputStream = new DataInputStream(socket.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    try {
                        read = inputStream.readUTF();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                command = read.charAt(0);
                                control();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void control() {

    }
}
