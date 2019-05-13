package com.example.hd.pwpadfornsw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.DataInputStream;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private static final int PORT = 5672;

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream inputStream;
    private Handler handler;
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

    private String read;
    private char command;

    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        btnConnect = (Button) findViewById(R.id.button_connect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectClickEvent();
            }
        });
    }

    void connectClickEvent() {
        Log.d("ServerLog", "Clicked");
        Collections.synchronizedMap(clientsMap);
        try {
            serverSocket = new ServerSocket(PORT);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Log.d("ServerLog", "Server Waiting...");
                        try {
                            socket = serverSocket.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d("ServerLog", "Connected");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.d("ServerLog", "success");

                                    inputStream = new DataInputStream(socket.getInputStream());
                                    read = inputStream.readUTF();
                                    clientsMap.put("user", new DataOutputStream(out));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    while(inputStream != null) {
                                        read = inputStream.readUTF();
                                        control();
                                    }
                                }   catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void control() {
        if(read.equals(new String("U"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("D"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("L"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("R"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("A"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("B"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("X"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("Y"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("Sl"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("Sr"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("Z"))) {
            Log.d("ServerReadLog", read);
        }
        else if(read.equals(new String("Zl"))) {
            Log.d("ServerReadLog", read);
        }
    }
}
