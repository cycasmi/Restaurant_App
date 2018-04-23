package com.restaurant.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

public class OrderList extends AppCompatActivity
{

    private FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        mAddButton = findViewById(R.id.order_list_add_button);
        initAddButton();
    }

    private void initAddButton()
    {
        mAddButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, DishList.class);
            startActivity(intent);
        });
    }

}
