package com.restaurant.app.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.restaurant.app.adapters.OrderListAdapter;
import com.restaurant.app.controllers.PreferenceController;
import com.restaurant.app.controllers.RestController;

public class OrderList extends AppCompatActivity
{

    private SwipeRefreshLayout   mSwipeRefresh;
    private RecyclerView         mRecyclerView;
    private FloatingActionButton mEditButton;

    private RestController       mRest;
    private PreferenceController mPrefs;

    private OrderListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        mSwipeRefresh = findViewById(R.id.order_list_refresh);
        mRecyclerView = findViewById(R.id.order_list_recycler);
        mEditButton = findViewById(R.id.order_list_edit_button);

        mRest = new RestController(this);
        mPrefs = new PreferenceController(this);
        initSwipeRefresh();
        initRecyclerView();
        initEditButton();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.order_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_bill, null, false);

        TextView        total       = view.findViewById(R.id.bill_total);
        TextView        description = view.findViewById(R.id.bill_description);
        AppCompatButton pay         = view.findViewById(R.id.bill_pay);
        View            progress    = view.findViewById(R.id.bill_progress);
        total.setText("$" + mAdapter.getTotal());
        description.setText(Html.fromHtml(mAdapter.getDescription()));
        pay.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            payBill(pay, progress);
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    private void initEditButton()
    {
        mEditButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, DishList.class);
            startActivity(intent);
        });
    }

    private void initSwipeRefresh()
    {
        mSwipeRefresh.setOnRefreshListener(this::loadData);
    }

    private void initRecyclerView()
    {
        mAdapter = new OrderListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadData()
    {
        int sessionId = mPrefs.getSessionId();
        mRest.getOrderList(sessionId, (result, status) -> {
            mSwipeRefresh.setRefreshing(false);
            mAdapter.setDataSet(result);
        });
        mRest.getDishList((result, status) -> mAdapter.setDishSet(result));
    }

    private void payBill(AppCompatButton button, View progress)
    {
        int sessionId = mPrefs.getSessionId();
        mRest.setSessionStatus(sessionId, "to_paid", (result, status) -> {
            progress.setVisibility(View.GONE);
            button.setText("Thank you");
            button.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#20c0d0")));
            Handler  handler  = new Handler();
            Runnable runnable = this::closeSession;
            handler.postDelayed(runnable, 1500);
        });
    }

    private void closeSession()
    {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
