package ro.pub.cs.systems.eim.practicaltest02;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class PracticalTest02MainActivity extends AppCompatActivity {


    // Server widgets
    private EditText serverPortEditText = null;
    private Button startButton = null;

    // Client widgets
    private EditText wordEditText = null;
    private EditText ipAddressEditText = null;
    private EditText clientPortEditText = null;
    private TextView wordInformationTextView = null;
    private Button lookupButton = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private StartButtonClickListener startButtonClickListener = new StartButtonClickListener();
    private class StartButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    private GetWordButtonClickListener getWordButtonClickListener = new GetWordButtonClickListener();
    private class GetWordButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String word = wordEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (word == null || word.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }

            String ipAddress = ipAddressEditText.getText().toString();
            if (ipAddress == null || ipAddress.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (ipAddress) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            wordInformationTextView.setText(Constants.EMPTY_STRING);

            clientThread = new ClientThread(Integer.parseInt(clientPort), word, ipAddress, wordInformationTextView);
            clientThread.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");
        setContentView(R.layout.activity_practical_test02_main);
        ipAddressEditText = (EditText)findViewById(R.id.ip_server_edit_text);
        serverPortEditText = (EditText)findViewById(R.id.server_port);
        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(startButtonClickListener);

        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        wordEditText = (EditText)findViewById(R.id.word_edit_text);
        lookupButton = (Button)findViewById(R.id.lookup_button);
        lookupButton.setOnClickListener(getWordButtonClickListener);
        wordInformationTextView = (TextView)findViewById(R.id.word_information_text_view);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
