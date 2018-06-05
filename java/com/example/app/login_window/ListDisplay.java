package com.example.app.login_window;

import android.app.Activity;
import android.app.LauncherActivity.*;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDisplay extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] listItems = new String[50];
    Button btn1;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);

        session = new UserSessionManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        Button btn1 = (Button)findViewById(R.id.btn1);
        /*Intent i = getIntent();
        String myResponse3 = i.getStringExtra("myResponse3");*/

        if (session.checkLogin()){
            finish();
        }



        String response = session.getUserDetails();

        String id, title;
        try {
            JSONObject reader = new JSONObject(response);
            JSONArray data = reader.getJSONArray("data");
            for (int j=0; j<data.length();j++){
                JSONObject o = data.getJSONObject(j);
                id = o.getString("id");
                title = o.getString("title");
                listItems[j] = id+"   "+title;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(listItems);
        mRecyclerView.setAdapter(mAdapter);

    }
}