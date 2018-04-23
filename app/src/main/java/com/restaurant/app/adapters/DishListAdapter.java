package com.restaurant.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.restaurant.app.activities.R;
import com.restaurant.app.models.Dish;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.ViewHolder>
{

    private View.OnClickListener mOnClickItem;
    private View.OnClickListener mOnClickAdd;

    private List<Dish> mDataSet;


    public DishListAdapter(View.OnClickListener onClickItem,
            View.OnClickListener onClickAdd)
    {
        mOnClickItem = onClickItem;
        mOnClickAdd = onClickAdd;
        mDataSet = new ArrayList<>();
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
        Dish data = mDataSet.get(position);
        holder.title.setText(data.name);
        holder.description.setText(data.description);
        Glide.with(holder.image.getContext()) //
                .load(data.url) //
                .apply(bitmapTransform(new CropCircleTransformation()))
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

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View      container;
        ImageView image;
        TextView  title;
        TextView  description;
        ImageView add;

        ViewHolder(View v)
        {
            super(v);
            container = v.findViewById(R.id.dish_item_container);
            image = v.findViewById(R.id.dish_item_image);
            title = v.findViewById(R.id.dish_item_title);
            description = v.findViewById(R.id.dish_item_description);
            add = v.findViewById(R.id.dish_item_add);
        }
    }
}