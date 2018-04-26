package com.restaurant.app.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.restaurant.app.activities.R;
import com.restaurant.app.models.Dish;
import com.restaurant.app.models.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>
{

    private List<Order>        mDataSet;
    private Map<Integer, Dish> mDishSet;


    public OrderListAdapter()
    {
        mDataSet = new ArrayList<>();
        mDishSet = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Order order = mDataSet.get(position);
        if (!mDishSet.containsKey(order.dishId)) return;
        Dish data = mDishSet.get(order.dishId);

        holder.title.setText(data.name);
        holder.price.setText("$" + data.cost);
        if (data.discount > 0)
            holder.price.setText(Html.fromHtml("<strike>$" + data.cost + "</strike>"));
        holder.discount.setText("($" + data.cost * (100 - data.discount) / 100 + ")");
        holder.discount.setVisibility(data.discount > 0 ? View.VISIBLE : View.GONE);

        holder.status.setBackgroundTintList(ColorStateList.valueOf(
                Color.parseColor(order.status.equals("ready") ? "#20c0d0" : "#ffffff")));
        holder.status.setTextColor(
                Color.parseColor(order.status.equals("ready") ? "#ffffff" : "#777777"));
        holder.status.setText(order.status.equals("ready") ? "Ready" : "Preparing");


        Glide.with(holder.image.getContext()) //
                .load(data.url) //
                .apply(bitmapTransform(new CropCircleTransformation()))
                .into(holder.image);

        holder.container.setTag(data.id);
    }

    @Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

    public void setDataSet(List<Order> dataSet)
    {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public void setDishSet(List<Dish> dishSet)
    {
        mDishSet.clear();
        for (Dish dish : dishSet)
            mDishSet.put(dish.id, dish);
        notifyDataSetChanged();
    }

    public double getTotal()
    {
        double total = 0;
        for (Order order : mDataSet) {
            Dish dish = mDishSet.get(order.dishId);
            double cost = dish.discount > 0 ? (100 - dish.discount) / 100 * dish.cost :
                          dish.cost;
            total += cost;
        }
        return total;
    }

    public String getDescription()
    {
        String description = "";
        for (Order order : mDataSet) {
            Dish dish = mDishSet.get(order.dishId);
            double cost = dish.discount > 0 ? (100 - dish.discount) / 100 * dish.cost :
                          dish.cost;
            description += "<br/>$" + cost + " <i>" + dish.name + "</i>";
        }
        return description;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View            container;
        ImageView       image;
        TextView        title;
        TextView        price;
        TextView        discount;
        AppCompatButton status;

        ViewHolder(View v)
        {
            super(v);
            container = v.findViewById(R.id.order_item_container);
            image = v.findViewById(R.id.order_item_image);
            title = v.findViewById(R.id.order_item_title);
            price = v.findViewById(R.id.order_item_price);
            discount = v.findViewById(R.id.order_item_discount);
            status = v.findViewById(R.id.order_item_status);
        }
    }
}