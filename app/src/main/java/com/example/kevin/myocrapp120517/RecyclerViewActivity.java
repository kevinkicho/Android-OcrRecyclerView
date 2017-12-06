package com.example.kevin.myocrapp120517;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by kevin on 12/5/2017.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ListItem> listItems;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            Intent i = this.getIntent();
            Bundle bundle = i.getExtras();

            List<ListItem> listItems = (List<ListItem>)bundle.getSerializable("data");
            adapter = new MyAdapter(listItems, getApplication());
            recyclerView.setAdapter(adapter);
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }
}
