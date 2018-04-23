package com.restaurant.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.restaurant.app.adapters.DishListAdapter;
import com.restaurant.app.controllers.RestController;

public class DishList extends AppCompatActivity
{

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView       mRecyclerView;

    private RestController  mRest;
    private DishListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        mSwipeRefresh = findViewById(R.id.dish_list_swipe_refresh);
        mRecyclerView = findViewById(R.id.dish_list_recycler);

        mRest = new RestController(this);
        initSwipeRefresh();
        initRecyclerView();
        loadData();
    }

    private void initSwipeRefresh()
    {
        mSwipeRefresh.setOnRefreshListener(this::loadData);
    }

    private void initRecyclerView()
    {
        View.OnClickListener onClickItem = view -> {
            int    dishId = (int) view.getTag();
            Intent intent = new Intent(this, DishDetails.class);
            intent.putExtra(DishDetails.ARGS_DISH_ID, dishId);
            startActivity(intent);
        };

        View.OnClickListener onClickAdd = view -> {
        };

        mAdapter = new DishListAdapter(onClickItem, onClickAdd);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadData()
    {
        mRest.getDishList((result, status) -> {
            mSwipeRefresh.setRefreshing(false);
            mAdapter.setDataSet(result);
        });
    }
}
