package com.onlineshoppingmall.shoppingcart.cart;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.Good;
import com.onlineshoppingmall.shoppingcart.cart.GoodItemFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent.GoodItem;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

public class GoodItemRecyclerViewAdapter extends RecyclerView.Adapter<GoodItemRecyclerViewAdapter.ViewHolder> {

    private final List<GoodItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public GoodItemRecyclerViewAdapter(List<GoodItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_gooditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.select.setSelected(mValues.get(position).selected);
        holder.amount.setText(String.valueOf(mValues.get(position).amount));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount.setText(String.valueOf(Integer.valueOf((String) holder.amount.getText()) + 1));
                //
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int old_amount = Integer.valueOf((String) holder.amount.getText());
                if (old_amount > 1) {
                    holder.amount.setText(String.valueOf(old_amount - 1));
                }
                //
            }
        });

        RequestQueue queue = MyApplication.getHttpQueue();
        String url = MyApplication.getHost() + "good/" + mValues.get(position).gid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                holder.rItem = MyApplication.getGson().fromJson(s, Good.class);

                MyRequest.setBitmap(holder.mView.getContext(), holder.image, holder.rItem.getId());
                holder.name.setText(holder.rItem.getName());
                holder.price.setText("￥" + holder.rItem.getPrice());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public AppCompatRadioButton select;
        public AppCompatImageView image;
        public AppCompatTextView name;
        public AppCompatTextView price;
        public AppCompatButton add;
        public AppCompatTextView amount;
        public AppCompatButton sub;
        public GoodItem mItem;
        public Good rItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            select = view.findViewById(R.id.cart_good_selected);
            image = view.findViewById(R.id.cart_good_image);
            name = view.findViewById(R.id.cart_good_name);
            price = view.findViewById(R.id.cart_price);
            add = view.findViewById(R.id.cart_amount_add);
            amount = view.findViewById(R.id.cart_amount);
            sub = view.findViewById(R.id.cart_amount_sub);
        }
    }
}
