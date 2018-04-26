package com.restaurant.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.restaurant.app.controllers.PreferenceController;
import com.restaurant.app.controllers.RestController;
import com.restaurant.app.models.Dish;

public class DishDetails extends AppCompatActivity
{

    public static final String ARGS_DISH_ID = "dishId";

    private ImageView       mImage;
    private TextView        mTitle;
    private ImageView       mOriginal;
    private TextView        mPrice;
    private TextView        mDiscount;
    private TextView        mDescription;
    private EditText        mComments;
    private AppCompatButton mAdd;

    private RestController       mRest;
    private PreferenceController mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        mImage = findViewById(R.id.dish_details_image);
        mTitle = findViewById(R.id.dish_details_title);
        mOriginal = findViewById(R.id.dish_details_original);
        mPrice = findViewById(R.id.dish_details_price);
        mDiscount = findViewById(R.id.dish_details_discount);
        mDescription = findViewById(R.id.dish_details_description);
        mComments = findViewById(R.id.dish_details_comments);
        mAdd = findViewById(R.id.dish_details_add);

        mRest = new RestController(this);
        mPrefs = new PreferenceController(this);
        loadData();
    }


    private void buildDetails(Dish data)
    {
        mTitle.setText(data.name);
        mPrice.setText("$" + data.cost);
        if (data.discount > 0)
            mPrice.setText(Html.fromHtml("<strike>$" + data.cost + "</strike>"));
        mOriginal.setVisibility(data.original ? View.VISIBLE : View.GONE);
        mDiscount.setText("($" + data.cost * (100 - data.discount) / 100 + ")");
        mDiscount.setVisibility(data.discount > 0 ? View.VISIBLE : View.GONE);
        String description = data.description + "<br/><br/>";
        description += "&#09;&#09;<i>Calories: </i>" + data.calories + "kcal<br/>";
        description += "&#09;&#09;<i>Chef: </i>" + data.chef + "<br/>";
        description += "&#09;&#09;<i>Tag: </i> #" + data.category + "";

        mDescription.setText(Html.fromHtml(description));
        Glide.with(this) //
                .load(data.url) //
                .into(mImage);

        mAdd.setOnClickListener(view -> addDish());
    }

    private void addDish()
    {
        int    sessionId = mPrefs.getSessionId();
        int    dishId    = getIntent().getIntExtra(ARGS_DISH_ID, 0);
        String comments  = mComments.getText().toString();
        mRest.saveOrder(sessionId, dishId, comments, (result, status) -> {
            Intent intent = new Intent(this, DishList.class);
            Toast.makeText(this, "Dish added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void loadData()
    {
        int dishId = getIntent().getIntExtra(ARGS_DISH_ID, 0);
        mRest.getDishDetails(dishId, (result, status) -> buildDetails(result));
    }
}
