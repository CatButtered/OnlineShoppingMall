package com.onlineshoppingmall.penson.addord.ord;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.penson.addord.ord.OrderContent.OrderItem;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

public class OrderItemRecyclerViewAdapter extends RecyclerView.Adapter<OrderItemRecyclerViewAdapter.ViewHolder> {

    private final List<OrderItem> mValues;

    public OrderItemRecyclerViewAdapter(List<OrderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orderitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        MyRequest.setGoodImg(holder.mView.getContext(), holder.image, holder.mItem.getGid());
        holder.name.setText(holder.mItem.getName());
        holder.amount.setText("X " + String.valueOf(holder.mItem.getAmount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public OrderItem mItem;

        public AppCompatImageView image;
        public AppCompatTextView name;
        public AppCompatTextView amount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.good_image);
            name = view.findViewById(R.id.good_name);
            amount = view.findViewById(R.id.good_amount);
        }

    }
}
