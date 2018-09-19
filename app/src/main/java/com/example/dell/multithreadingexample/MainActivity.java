package com.example.dell.multithreadingexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edit_value;
    TextView result;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_value = findViewById(R.id.Edit_Value);
        result = findViewById(R.id.result);
        btn = findViewById(R.id.Submit_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go(view); // it throw not Responding App because Main/UR thread have gone to sleeping .
                goWithMultiThread(view); // here I used MultiThread  to remove the unresponsive error.
            }
        });
    }

    private void goWithMultiThread(View view) {
        int numFiles = Integer.parseInt(edit_value.getText().toString());
        MyMultiTask myMultiTask = new MyMultiTask();
        myMultiTask.execute(numFiles);
    }

    private void go(View view) {
        int numFiles = Integer.parseInt(edit_value.getText().toString());
        result.setText("Started the Simulation");
        for(int i = 1;i<=numFiles;i++){
            try {
                Thread.sleep(3000);
                result.setText(i+" Files Processed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result.setText("Done Processing "+numFiles+" Files");
    }

    private class MyMultiTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPostExecute(String s) {
            result.setText(s);
        }

        @Override
        protected void onPreExecute() {
            result.setText("Start the Simulation");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i = 1;i<=integers[0];i++){
                try {
                    Thread.sleep(3000);
                    //result.setText(i+" Files Processed");
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Done Processing " + integers[0] + " Files";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            result.setText(values[0]+ " files uploaded");
        }
    }


}
