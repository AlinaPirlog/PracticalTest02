package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private int port;
    private String word;
    private String ipAddress;
    private TextView wordTextView;

    private Socket socket;

    public ClientThread(int port, String word, String ipAddress, TextView wordForecastTextView) {
        this.port = port;
        this.word = word;
        this.ipAddress = ipAddress;
        this.wordTextView = wordForecastTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ipAddress, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(word);
            printWriter.flush();
            String wordInformation;
            String finalStr = "";
            while ((wordInformation = bufferedReader.readLine()) != null) {
                final String finalizedWeateherInformation = wordInformation;
                wordTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        wordTextView.setText(finalizedWeateherInformation);
                    }
                });
            }

            //wordTextView.setText(finalStr);
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}