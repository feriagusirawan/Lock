package com.lockcode.root.lock;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import android.widget.ProgressBar;

import android.widget.Toast;


//http://square.github.io/okhttp/
public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    final String strURL = "https://partnerapi.igloohome.co/v1/locks/IGB2-C2A4P2_69ac6f/lockcodes";
    final String strApikey = "KAMISPACE-7det1dtlI05Ya7lHWGV90sQn62AjeSiq";
    final String strBodyJson = "{\n" +
            "\"description\":\"testing\",\n" +
            "\"startDate\":\"2019-12-12T12:00:00\", \n" +
            "\"durationCode\": 1 \n" +
            "}\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);

        final Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.textView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleProgressBar.setVisibility(View.VISIBLE);
//        AsyncTask asyncTask = new AsyncTask() {
                AsyncTask asyncTask = new AsyncTask<String, Object, String>() {


                    @Override
//  protected Object doInBackground(Object[] objects) {
                    protected String doInBackground(String[] objects) {

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = RequestBody.create(JSON, strBodyJson);
                        Request request = new Request.Builder()
                                .url(strURL)
                                .addHeader("Content_Type", "application/json")
                                .addHeader("X-IGLOOHOME-APIKEY", strApikey)
                                .post(body)
                                .build();

//        Response response = null;
                        Response response;

                        try {
                            response = client.newCall(request).execute();
                            return response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;

                    }


                    @Override
//          protected void onPostExecute(Object code) {
//          textView.setText(code.toString());
                    protected void onPostExecute(String strResponse) {
                        simpleProgressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObjectResponse = new JSONObject(strResponse);
                            String code = jsonObjectResponse.getString("code");
                            textView.setText(code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textView.setText("-");
                        }
                    }

                }.execute();

            }
        });
    }
}

//Toast.makeText(getApplication(), "You don't have connection.", Toast.LENGTH_SHORT).show();
//https://github.com/dinogregorich/Android_GetJsonDataFromRestAPI


