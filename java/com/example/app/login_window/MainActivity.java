package com.example.app.login_window;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    Button btn;
    EditText et1, et2;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new UserSessionManager(getApplicationContext());

        tv1 = (TextView) findViewById(R.id.tv1);
        btn = (Button) findViewById(R.id.btn);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);

        if (!(session.getUserDetails()==null)){
            Intent i = new Intent(getApplicationContext(), ListDisplay.class);
            startActivity(i);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OkHttpClient client = new OkHttpClient();

                String url = "https://www.getpostman.com/collections/496f8e9fde98fccf395b";

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            JSONObject reader = null;
                                    try {
                                        reader = new JSONObject(myResponse);
                                        JSONArray requests = reader.getJSONArray("requests");
                                        JSONObject q = requests.getJSONObject(1);
                                        final JSONArray data = q.getJSONArray("data");
                                        JSONObject p1 = data.getJSONObject(0);
                                        JSONObject p2 = data.getJSONObject(1);
                                        final String email = p1.getString("value");
                                        final String pass = p2.getString("value");

                                    if (email.equals(et1.getText().toString())
                                                && pass.equals(et2.getText().toString())){
                                            OkHttpClient client2 = new OkHttpClient();
                                            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                                            RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\ntest@test.com\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\ntesttest\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                                            Request request2 = new Request.Builder()
                                                    .url("http://hackerkernel.com/demo/ios_api/login.php")
                                                    .post(body)
                                                    .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                                                    .addHeader("cache-control", "no-cache")
                                                    .addHeader("postman-token", "5d2cdf66-234f-9882-d56a-94591999474d")
                                                    .build();
                                            client2.newCall(request2).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    e.printStackTrace();
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    final String myResponse2 = response.body().string();

                                                            try {
                                                                JSONObject reader = new JSONObject(myResponse2);
                                                                String value = reader.getString("key");
                                                                OkHttpClient client3 = new OkHttpClient();
                                                                Request request3 = new Request.Builder()
                                                                        .url("http://hackerkernel.com/demo/ios_api/list.php?key="+value)
                                                                        .get()
                                                                        .addHeader("cache-control", "no-cache")
                                                                        .addHeader("postman-token", "a8c1e634-7730-3772-abfe-5de640ecffd7")
                                                                        .build();

                                                                client3.newCall(request3).enqueue(new Callback() {
                                                                    @Override
                                                                    public void onFailure(Call call, IOException e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                    @Override
                                                                    public void onResponse(Call call, Response response) throws IOException {
                                                                        final String myResponse3 = response.body().string();

                                                                        MainActivity.this.runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                //tv1.setText(myResponse3);
                                                                                session.createUserLoginSession(myResponse3);

                                                                                    Intent i = new Intent(getApplicationContext(), ListDisplay.class);
                                                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                                                                    getApplicationContext().startActivity(i);
                                                                                    finish();


                                                                            }
                                                                        });
                                                                    }
                                                                });

                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                }
                                            });
                                        }
                                        else {
                                        MainActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                    }
                });



            }
        });

    }
}