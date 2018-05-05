package jawegiel.pl.androidclient;

import java.io.*;
import java.net.*;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is a simple Android mobile client
 * This application read any string massage typed on the text field and
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 */
public class MainActivity extends Activity {

    //private Socket socket;
    private PrintWriter printwriter;
    private EditText textField;
    private String messsage;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (EditText) findViewById(R.id.editText1); // reference to the text field

        Button b3 = findViewById(R.id.button2);

        textView = (TextView) findViewById(R.id.textView); // reference to the text field


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsAvailable ia = new IsAvailable();

                FullTask fullTask = new FullTask();
                fullTask.execute();


            }
        });
    }

    class IsAvailable extends Thread
    {
        public boolean isAvailable() throws IOException {
            return InetAddress.getByName("192.168.1.182").isReachable(1000);
        }
    }

    public class FullTask extends AsyncTask<Void, Integer, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        StringBuilder sb = new StringBuilder();
        Integer doubledValue = 0;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }


        @Override
        protected Void doInBackground(Void... arg0) {

            //Socket socket = null;

            try {
                //socket = new Socket("192.168.1.182", 4446);
                //socket = new Socket("http://10.0.2.2:8081/ServerMultiply", 8081);
                String number = textField.getText().toString();

                //URL url = new URL("http://192.168.1.182:8081/welcome2?param1="+number);
                URL url = new URL("https://jawegiel-web.herokuapp.com?param1="+number);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);



                String sendMessage = number + "\n";
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server : " + sendMessage);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);


                String returnString = "";
                doubledValue = 0;

                while ((returnString = br.readLine()) != null) {
                    doubledValue = Integer.parseInt(returnString);
                }
                br.close();

//                for (String line = br.readLine(); line != null; line = br.readLine()) {
//                    sb.append(line);
//                }


                response = br.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            textView.setText(String.valueOf(doubledValue));
            super.onPostExecute(result);
        }
    }
}