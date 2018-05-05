package jawegiel.pl.androidclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {

    private EditText textField;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = findViewById(R.id.editText1); // reference to the text field
        Button button = findViewById(R.id.button2);
        textView = findViewById(R.id.textView); // reference to the text field


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullTask fullTask = new FullTask(MainActivity.this);
                fullTask.execute();
            }
        });
    }


    public class FullTask extends AsyncTask<Void, Void, Void> {

//        Socket socket = null;
//
//        private String dstAddress;
//        private int dstPort;
        private URL url;
        private URLConnection connection;
        private String number;
//        OutputStream os;
//        OutputStreamWriter osw;
//        BufferedWriter bw;
        private InputStream is;
        private InputStreamReader isr;
        private BufferedReader br;

        private Activity activity;
        private ProgressDialog dialog;
        private int doubledValue = 0;


        public FullTask(Activity activity) {
            this.activity = activity;
            dialog = new ProgressDialog(activity);
        }




        protected void onPreExecute() {
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }


        @Override
        protected Void doInBackground(Void... arg0) {


            try {

                number = textField.getText().toString();

//                socket = new Socket("192.168.1.182", 4446);
//                socket = new Socket("http://10.0.2.2:8081/ServerMultiply", 8081);
//                URL url = new URL("http://192.168.1.182:8081/welcome2?param1="+number);
                url = new URL("https://jawegiel-web.herokuapp.com?param1=" + number);
                connection = url.openConnection();
                connection.setDoOutput(true);

//                os = connection.getOutputStream();
//                osw = new OutputStreamWriter(os);
//                bw = new BufferedWriter(osw);
//
//
//                bw.write(number);
//                bw.flush();

                is = connection.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);


                String returnString;
                doubledValue = 0;

                while ((returnString = br.readLine()) != null) {
                    doubledValue = Integer.parseInt(returnString);
                }
                br.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            textView.setText(String.valueOf(doubledValue));
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}