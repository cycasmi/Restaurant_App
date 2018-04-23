package com.restaurant.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.restaurant.app.controllers.RestController;
import com.restaurant.app.models.Dish;

public class DishDetails extends AppCompatActivity
{

    public static final String ARGS_DISH_ID = "dishId";

    private ImageView mImage;
    private TextView  mTitle;
    private TextView  mDescription;

    private RestController mRest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        mImage = findViewById(R.id.dish_details_image);
        mTitle = findViewById(R.id.dish_details_title);
        mDescription = findViewById(R.id.dish_details_description);


        mRest = new RestController(this);
        loadData();
    }


    private void buildDetails(Dish data)
    {
        mTitle.setText(data.name);
        mDescription.setText(data.description);
        Glide.with(this) //
                .load(data.url) //
                .into(mImage);
    }

    private void loadData()
    {
        int dishId = getIntent().getIntExtra(ARGS_DISH_ID, 0);
        mRest.getDishDetails(dishId, (result, status) -> buildDetails(result));
    }
}
