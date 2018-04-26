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

public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.ViewHolder>
{

    private View.OnClickListener mOnClickItem;
    private View.OnClickListener mOnClickAdd;

    private List<Dish>            mDataSet;
    private Map<Integer, Integer> mOrderCount;


    public DishListAdapter(View.OnClickListener onClickItem,
            View.OnClickListener onClickAdd)
    {
        mOnClickItem = onClickItem;
        mOnClickAdd = onClickAdd;
        mDataSet = new ArrayList<>();
        mOrderCount = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Dish data  = mDataSet.get(position);
        int  count = mOrderCount.containsKey(data.id) ? mOrderCount.get(data.id) : 0;

        holder.title.setText(data.name);
        holder.price.setText("$" + data.cost);
        if (data.discount > 0)
            holder.price.setText(Html.fromHtml("<strike>$" + data.cost + "</strike>"));
        holder.original.setVisibility(data.original ? View.VISIBLE : View.GONE);
        holder.discount.setText("($" + data.cost * (100 - data.discount) / 100 + ")");
        holder.discount.setVisibility(data.discount > 0 ? View.VISIBLE : View.GONE);
        holder.add.setBackgroundTintList(ColorStateList
                .valueOf(Color.parseColor(count > 0 ? "#ff9800" : "#ffffff")));
        holder.add.setTextColor(Color.parseColor(count > 0 ? "#ffffff" : "#777777"));
        holder.add.setText(count > 0 ? "Add (" + count + ")" : "Add");
        holder.remove.setVisibility(count > 0 ? View.VISIBLE : View.GONE);


        Glide.with(holder.image.getContext()) //
                .load(data.url) //
                .into(holder.image);

        holder.container.setTag(data.id);
        holder.add.setTag(data.id);

        holder.container.setOnClickListener(mOnClickItem);
        holder.add.setOnClickListener(mOnClickAdd);
    }

    @Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

    public void setDataSet(List<Dish> dataSet)
    {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public void setOrderSet(List<Order> orderSet)
    {
        for (Order order : orderSet) {
            if (!mOrderCount.containsKey(order.dishId)) mOrderCount.put(order.dishId, 0);
            mOrderCount.put(order.dishId, mOrderCount.get(order.dishId) + 1);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View            container;
        ImageView       image;
        TextView        title;
        ImageView       original;
        TextView        price;
        TextView        discount;
        AppCompatButton add;
        AppCompatButton remove;


        ViewHolder(View v)
        {
            super(v);
            container = v.findViewById(R.id.dish_item_container);
            image = v.findViewById(R.id.dish_item_image);
            title = v.findViewById(R.id.dish_item_title);
            original = v.findViewById(R.id.dish_item_original);
            price = v.findViewById(R.id.dish_item_price);
            discount = v.findViewById(R.id.dish_item_discount);
            add = v.findViewById(R.id.dish_item_add);
            remove = v.findViewById(R.id.dish_item_remove);
        }
    }
}