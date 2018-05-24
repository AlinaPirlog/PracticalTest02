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

public class PracticalTest02MainActivity extends AppCompatActivity {


    // Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    // Client widgets
    private EditText hourEditText = null;
    private EditText minuteEditText = null;
    private EditText clientPortEditText = null;
    private Button setAlarm = null;
    private Button resetAlarm = null;
    private Button pollAlarm = null;
    private TextView alarmInformationTextView = null;

    //private ServerThread serverThread = null;
    //private ClientThread clientThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
//            serverThread = new ServerThread(Integer.parseInt(serverPort));
//            if (serverThread.getServerSocket() == null) {
//                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
//                return;
//            }
//            serverThread.start();

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        hourEditText = (EditText)findViewById(R.id.client_hour);
        minuteEditText = (EditText)findViewById(R.id.client_minute);
        setAlarm = (Button)findViewById(R.id.set_alarm);
        resetAlarm = (Button)findViewById(R.id.reset_alarm);
        pollAlarm = (Button)findViewById(R.id.poll_alarm);

        alarmInformationTextView = (TextView)findViewById(R.id.alarm_information_text_view);
    }

    @Override
    protected void onDestroy() {
//        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
//        if (serverThread != null) {
//            serverThread.stopThread();
//        }
        super.onDestroy();
    }
}
