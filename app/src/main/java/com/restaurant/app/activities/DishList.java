package com.restaurant.app.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.restaurant.app.adapters.DishListAdapter;
import com.restaurant.app.controllers.PreferenceController;
import com.restaurant.app.controllers.RestController;

public class DishList extends AppCompatActivity
{

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView       mRecyclerView;

    private RestController       mRest;
    private PreferenceController mPrefs;

    private DishListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        mSwipeRefresh = findViewById(R.id.dish_list_refresh);
        mRecyclerView = findViewById(R.id.dish_list_recycler);

        mRest = new RestController(this);
        mPrefs = new PreferenceController(this);
        initSwipeRefresh();
        initRecyclerView();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.dish_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(this, OrderList.class);
        startActivity(intent);
        return true;
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

            View image = view.findViewById(R.id.dish_item_image);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this,  //
                            Pair.create(image, "image"));
            startActivity(intent, options.toBundle());
        };

        View.OnClickListener onClickAdd = view -> {
            int dishId = (int) view.getTag();
            addDish(dishId);
        };

        mAdapter = new DishListAdapter(onClickItem, onClickAdd);
        int numColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addDish(int dishId)
    {
        int sessionId = mPrefs.getSessionId();
        mRest.saveOrder(sessionId, dishId, (result, status) -> {
            Toast.makeText(this, "Dish added", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadData()
    {
        int sessionId = mPrefs.getSessionId();
        mRest.getDishList((result, status) -> {
            mSwipeRefresh.setRefreshing(false);
            mAdapter.setDataSet(result);
        });
        mRest.getOrderList(sessionId, (result, status) -> mAdapter.setOrderSet(result));
    }
}
