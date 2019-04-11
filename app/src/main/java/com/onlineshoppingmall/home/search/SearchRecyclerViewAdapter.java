package com.onlineshoppingmall.home.search;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.home.search.SearchFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.remote_entity.Good;
import com.onlineshoppingmall.shoppingcart.Database.GoodItemDao;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private final List<Good> mValues;
    private final OnListFragmentInteractionListener mListener;
    private GoodItemDao dao;

    public SearchRecyclerViewAdapter(List<Good> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_searchitem, parent, false);
        dao = new GoodItemDao(view.getContext(), 1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        MyRequest.setGoodImg(holder.mView.getContext(), holder.image, holder.mItem.getId());
        holder.name.setText(holder.mItem.getName());
        holder.price.setText("￥" + holder.mItem.getPrice());
        holder.amount.setText("数量：" + holder.mItem.getAmount());
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodContent.GoodItem item = dao.selectByGid(holder.mItem.getId());
                if (item == null) {
                    item = new GoodContent.GoodItem();
                    item.setGid(holder.mItem.getId());
                    item.setAmount(1);
                    item.setSelected(0);
                    dao.addGood(item);
                } else {
                    dao.updateAmount(item.getId(), item.getAmount() + 1);
                }
            }
        });

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
        public Good mItem;

        public AppCompatImageView image;
        public AppCompatTextView name;
        public AppCompatTextView price;
        public AppCompatTextView amount;
        public AppCompatButton buy;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.search_image);
            name = view.findViewById(R.id.search_good_name);
            price = view.findViewById(R.id.search_price);
            amount = view.findViewById(R.id.search_amount);
            buy = view.findViewById(R.id.search_into_cart);
        }

    }
}
