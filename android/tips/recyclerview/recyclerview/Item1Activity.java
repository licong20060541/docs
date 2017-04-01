package com.example.xumin.items.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xumin.items.R;

/**
 * Created by xumin on 17/4/1.
 */

public class Item1Activity extends Activity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item1_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.addItemDecoration(new MyItemDecor());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
