package com.example.hd.pwpadfornsw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.DataInputStream;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

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

    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        btnConnect = (Button) findViewById(R.id.button_connect);
        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                connectClickEvent(v);
            }
        });
    }

    void connectClickEvent(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    socket = serverSocket.accept();
                    inputStream = new DataInputStream(socket.getInputStream());

                    while(true){
                        read = inputStream.readUTF();
                        command = read.charAt(0);
                        control();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void control() {

    }
}
