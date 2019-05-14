package com.example.hd.pwpadfornsw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.DataInputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private DataOutputStream outputStream;
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
    private String read;

    private TextView userPassword;
    private Button btnConnect;
    private Button btnToggle;
    private Button btnClear;

    private TextView text11;
    private TextView text12;
    private TextView text13;
    private TextView text14;
    private TextView text15;
    private TextView text21;
    private TextView text22;
    private TextView text23;
    private TextView text24;
    private TextView text25;
    private TextView text31;
    private TextView text32;
    private TextView text33;
    private TextView text34;
    private TextView text35;
    private TextView text41;
    private TextView text42;
    private TextView text43;
    private TextView text44;
    private TextView text45;
    private TextView text51;
    private TextView text52;
    private TextView text53;
    private TextView text54;
    private TextView text55;
    private TextView text61;
    private TextView text62;
    private TextView text63;
    private TextView text64;
    private TextView text65;

    private int defaultColumn = 3;
    private int defaultLayout = 0;
    private StringBuilder password = new StringBuilder();
    private char[][] key = new char[6][5];
    private int hide = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button) findViewById(R.id.button_connect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectClickEvent();
            }
        });

        btnToggle = (Button) findViewById(R.id.button_toggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hide == 1) {
                    hide = 0;
                    btnToggle.setText("Hide Password");
                }
                else {
                    hide = 1;
                    btnToggle.setText("View Password");
                }
                passwordInput();
            }
        });

        btnClear = (Button) findViewById(R.id.button_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.delete(0, password.length());
                passwordInput();
            }
        });

        userPassword = (TextView) findViewById(R.id.passwordText);

        text11 = (TextView) findViewById(R.id.text11);
        text12 = (TextView) findViewById(R.id.text12);
        text13 = (TextView) findViewById(R.id.text13);
        text14 = (TextView) findViewById(R.id.text14);
        text15 = (TextView) findViewById(R.id.text15);
        text21 = (TextView) findViewById(R.id.text21);
        text22 = (TextView) findViewById(R.id.text22);
        text23 = (TextView) findViewById(R.id.text23);
        text24 = (TextView) findViewById(R.id.text24);
        text25 = (TextView) findViewById(R.id.text25);
        text31 = (TextView) findViewById(R.id.text31);
        text32 = (TextView) findViewById(R.id.text32);
        text33 = (TextView) findViewById(R.id.text33);
        text34 = (TextView) findViewById(R.id.text34);
        text35 = (TextView) findViewById(R.id.text35);
        text41 = (TextView) findViewById(R.id.text41);
        text42 = (TextView) findViewById(R.id.text42);
        text43 = (TextView) findViewById(R.id.text43);
        text44 = (TextView) findViewById(R.id.text44);
        text45 = (TextView) findViewById(R.id.text45);
        text51 = (TextView) findViewById(R.id.text51);
        text52 = (TextView) findViewById(R.id.text52);
        text53 = (TextView) findViewById(R.id.text53);
        text54 = (TextView) findViewById(R.id.text54);
        text55 = (TextView) findViewById(R.id.text55);
        text61 = (TextView) findViewById(R.id.text61);
        text62 = (TextView) findViewById(R.id.text62);
        text63 = (TextView) findViewById(R.id.text63);
        text64 = (TextView) findViewById(R.id.text64);
        text65 = (TextView) findViewById(R.id.text65);

        changeKeyboard();
    }

    void connectClickEvent() {
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

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.d("ServerLog", "Connected");
                                    inputStream = new DataInputStream(socket.getInputStream());
                                    outputStream = new DataOutputStream(socket.getOutputStream());

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
            defaultColumn--;
            if(defaultColumn == 0 || defaultColumn < 0) {
                try {
                    outputStream.writeUTF("U");
                } catch(IOException e) {
                    e.printStackTrace();
                }
                defaultColumn = 0;
            }
        }
        else if(read.equals(new String("D"))) {
            defaultColumn++;
            if(defaultColumn == 5 || defaultColumn > 5) {
                try {
                    outputStream.writeUTF("D");
                } catch(IOException e) {
                    e.printStackTrace();
                }
                defaultColumn = 5;
            }
        }
        else if(read.equals(new String("L"))) {
            defaultLayout--;
            if(defaultLayout < 0)
                defaultLayout = 3;
            changeLayout();
        }
        else if(read.equals(new String("R"))) {
            defaultLayout++;
            if(defaultLayout > 3)
                defaultLayout = 0;
            changeLayout();
        }
        else if(read.equals(new String("A"))) {
            password.append(String.valueOf(key[defaultColumn][0]));
            passwordInput();
        }
        else if(read.equals(new String("B"))) {
            password.append(String.valueOf(key[defaultColumn][1]));
            passwordInput();
        }
        else if(read.equals(new String("X"))) {
            password.append(String.valueOf(key[defaultColumn][2]));
            passwordInput();
        }
        else if(read.equals(new String("Y"))) {
            password.append(String.valueOf(key[defaultColumn][3]));
            passwordInput();
        }
        else if(read.equals(new String("Sl"))) {
            if(password.length() != 0)
                password.setLength(password.length() - 1);
            passwordInput();
        }
        else if(read.equals(new String("Sr"))) {
            if(password.length() != 0)
                password.setLength(password.length() - 1);
            passwordInput();
        }
        else if(read.equals(new String("Z"))) {
            password.append(String.valueOf(key[defaultColumn][4]));
            passwordInput();
        }
    }

    private void changeLayout() {
        switch(defaultLayout) {
            case 0: {
                text11.setText(R.string.text_lower_11);
                text12.setText(R.string.text_lower_12);
                text13.setText(R.string.text_lower_13);
                text14.setText(R.string.text_lower_14);
                text15.setText(R.string.text_lower_15);
                text21.setText(R.string.text_lower_21);
                text22.setText(R.string.text_lower_22);
                text23.setText(R.string.text_lower_23);
                text24.setText(R.string.text_lower_24);
                text25.setText(R.string.text_lower_25);
                text31.setText(R.string.text_lower_31);
                text32.setText(R.string.text_lower_32);
                text33.setText(R.string.text_lower_33);
                text34.setText(R.string.text_lower_34);
                text35.setText(R.string.text_lower_35);
                text41.setText(R.string.text_lower_41);
                text42.setText(R.string.text_lower_42);
                text43.setText(R.string.text_lower_43);
                text44.setText(R.string.text_lower_44);
                text45.setText(R.string.text_lower_45);
                text51.setText(R.string.text_lower_51);
                text52.setText(R.string.text_lower_52);
                text53.setText(R.string.text_lower_53);
                text54.setText(R.string.text_lower_54);
                text55.setText(R.string.text_lower_55);
                text61.setText(R.string.text_lower_61);
                text62.setText(R.string.text_lower_62);
                text63.setText(R.string.text_lower_63);
                text64.setText(R.string.text_lower_64);
                text65.setText(R.string.text_lower_65);
            }
            break;
            case 1: {
                text11.setText(R.string.text_upper_11);
                text12.setText(R.string.text_upper_12);
                text13.setText(R.string.text_upper_13);
                text14.setText(R.string.text_upper_14);
                text15.setText(R.string.text_upper_15);
                text21.setText(R.string.text_upper_21);
                text22.setText(R.string.text_upper_22);
                text23.setText(R.string.text_upper_23);
                text24.setText(R.string.text_upper_24);
                text25.setText(R.string.text_upper_25);
                text31.setText(R.string.text_upper_31);
                text32.setText(R.string.text_upper_32);
                text33.setText(R.string.text_upper_33);
                text34.setText(R.string.text_upper_34);
                text35.setText(R.string.text_upper_35);
                text41.setText(R.string.text_upper_41);
                text42.setText(R.string.text_upper_42);
                text43.setText(R.string.text_upper_43);
                text44.setText(R.string.text_upper_44);
                text45.setText(R.string.text_upper_45);
                text51.setText(R.string.text_upper_51);
                text52.setText(R.string.text_upper_52);
                text53.setText(R.string.text_upper_53);
                text54.setText(R.string.text_upper_54);
                text55.setText(R.string.text_upper_55);
                text61.setText(R.string.text_upper_61);
                text62.setText(R.string.text_upper_62);
                text63.setText(R.string.text_upper_63);
                text64.setText(R.string.text_upper_64);
                text65.setText(R.string.text_upper_65);
            }
            break;
            case 2: {
                text11.setText(R.string.text_special1_11);
                text12.setText(R.string.text_special1_12);
                text13.setText(R.string.text_special1_13);
                text14.setText(R.string.text_special1_14);
                text15.setText(R.string.text_special1_15);
                text21.setText(R.string.text_special1_21);
                text22.setText(R.string.text_special1_22);
                text23.setText(R.string.text_special1_23);
                text24.setText(R.string.text_special1_24);
                text25.setText(R.string.text_special1_25);
                text31.setText(R.string.text_special1_31);
                text32.setText(R.string.text_special1_32);
                text33.setText(R.string.text_special1_33);
                text34.setText(R.string.text_special1_34);
                text35.setText(R.string.text_special1_35);
                text41.setText(R.string.text_special1_41);
                text42.setText(R.string.text_special1_42);
                text43.setText(R.string.text_special1_43);
                text44.setText(R.string.text_special1_44);
                text45.setText(R.string.text_special1_45);
                text51.setText(R.string.text_special1_51);
                text52.setText(R.string.text_special1_52);
                text53.setText(R.string.text_special1_53);
                text54.setText(R.string.text_special1_54);
                text55.setText(R.string.text_special1_55);
                text61.setText(R.string.text_special1_61);
                text62.setText(R.string.text_special1_62);
                text63.setText(R.string.text_special1_63);
                text64.setText(R.string.text_special1_64);
                text65.setText(R.string.text_special1_65);
            }
            break;
            case 3: {
                text11.setText(R.string.text_special2_11);
                text12.setText(R.string.text_special2_12);
                text13.setText(R.string.text_special2_13);
                text14.setText(R.string.text_special2_14);
                text15.setText(R.string.text_special2_15);
                text21.setText(R.string.text_special2_21);
                text22.setText(R.string.text_special2_22);
                text23.setText(R.string.text_special2_23);
                text24.setText(R.string.text_special2_24);
                text25.setText(R.string.text_special2_25);
                text31.setText(R.string.text_special2_31);
                text32.setText(R.string.text_special2_32);
                text33.setText(R.string.text_special2_33);
                text34.setText(R.string.text_special2_34);
                text35.setText(R.string.text_special2_35);
                text41.setText(R.string.text_special2_41);
                text42.setText(R.string.text_special2_42);
                text43.setText(R.string.text_special2_43);
                text44.setText(R.string.text_special2_44);
                text45.setText(R.string.text_special2_45);
                text51.setText(R.string.text_special2_51);
                text52.setText(R.string.text_special2_52);
                text53.setText(R.string.text_special2_53);
                text54.setText(R.string.text_special2_54);
                text55.setText(R.string.text_special2_55);
                text61.setText(R.string.text_special2_61);
                text62.setText(R.string.text_special2_62);
                text63.setText(R.string.text_special2_63);
                text64.setText(R.string.text_special2_64);
                text65.setText(R.string.text_special2_65);
            }
            break;
        }
        changeKeyboard();
    }

    private void changeKeyboard() {
        key[0][0] = text11.getText().charAt(0);
        key[0][1] = text12.getText().charAt(0);
        key[0][2] = text13.getText().charAt(0);
        key[0][3] = text14.getText().charAt(0);
        key[0][4] = text15.getText().charAt(0);
        key[1][0] = text21.getText().charAt(0);
        key[1][1] = text22.getText().charAt(0);
        key[1][2] = text23.getText().charAt(0);
        key[1][3] = text24.getText().charAt(0);
        key[1][4] = text25.getText().charAt(0);
        key[2][0] = text31.getText().charAt(0);
        key[2][1] = text32.getText().charAt(0);
        key[2][2] = text33.getText().charAt(0);
        key[2][3] = text34.getText().charAt(0);
        key[2][4] = text35.getText().charAt(0);
        key[3][0] = text41.getText().charAt(0);
        key[3][1] = text42.getText().charAt(0);
        key[3][2] = text43.getText().charAt(0);
        key[3][3] = text44.getText().charAt(0);
        key[3][4] = text45.getText().charAt(0);
        key[4][0] = text51.getText().charAt(0);
        key[4][1] = text52.getText().charAt(0);
        key[4][2] = text53.getText().charAt(0);
        key[4][3] = text54.getText().charAt(0);
        key[4][4] = text55.getText().charAt(0);
        key[5][0] = text61.getText().charAt(0);
        key[5][1] = text62.getText().charAt(0);

        if(defaultLayout != 2 && defaultLayout != 3) {
            key[5][2] = text63.getText().charAt(0);
            key[5][3] = text64.getText().charAt(0);
            key[5][4] = text65.getText().charAt(0);
        }
        else {
            key[5][2] = ' ';
            key[5][3] = ' ';
            key[5][4] = ' ';
        }
    }

    private void passwordInput() {
        switch(hide) {
            case 0: {
                userPassword.setText(password);
            }
            break;
            case 1: {
                int len = password.length();
                StringBuilder pw = new StringBuilder();
                for(int i = 0 ; i < len ; i++)
                    pw.append(String.valueOf("*"));
                userPassword.setText(pw.toString());
            }
            break;
        }
    }
}
