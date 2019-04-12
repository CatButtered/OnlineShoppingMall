package com.onlineshoppingmall.shoppingcart.cart;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.remote_entity.Good;
import com.onlineshoppingmall.shoppingcart.Database.GoodItemDao;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent.GoodItem;
import com.onlineshoppingmall.shoppingcart.cart.GoodItemFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.shoppingcart.cart.data.ModelViewModel;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

public class GoodItemRecyclerViewAdapter extends RecyclerView.Adapter<GoodItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "GoodItemRecyclerViewAda";
    private final List<GoodItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private GoodItemDao goodItemDao;

    private ModelViewModel mViewModel;

    public GoodItemRecyclerViewAdapter(List<GoodItem> items, OnListFragmentInteractionListener listener, ModelViewModel viewModel) {
        mValues = items;
        mListener = listener;
        mViewModel = viewModel;
        viewModel.isAll.setValue(false);
        viewModel.isNone.setValue(false);
        viewModel.isChanged.setValue(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_gooditem, parent, false);
        goodItemDao = new GoodItemDao(view.getContext(), 1);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.select.setChecked(mValues.get(position).getSelected() != 0);
        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        holder.mItem.setSelected(1);
                        goodItemDao.updateSelected(holder.mItem.getId(), 1);
                        mViewModel.isChanged.setValue(true);

                    } else {
//                        if (mViewModel.isAll.getValue()) {
//                            mViewModel.isAll.setValue(false);
//                        }
                        holder.mItem.setSelected(0);
                        goodItemDao.updateSelected(holder.mItem.getId(), 0);
                        mViewModel.isChanged.setValue(true);

                    }
                }
            }
        });
        holder.amount.setText(String.valueOf(mValues.get(position).getAmount()));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.valueOf((String) holder.amount.getText()) + 1;
                holder.amount.setText(String.valueOf(amount));
                holder.mItem.setAmount(amount);
                goodItemDao.updateAmount(holder.mItem.getId(), amount);
                mViewModel.isChanged.setValue(true);

            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int old_amount = Integer.valueOf((String) holder.amount.getText());
                if (old_amount > 1) {
                    int amount = old_amount - 1;
                    holder.amount.setText(String.valueOf(amount));
                    holder.mItem.setAmount(amount);
                    goodItemDao.updateAmount(holder.mItem.getId(), amount);
                    mViewModel.isChanged.setValue(true);

                }
            }
        });

        if (!GoodContent.getCache().containsKey(holder.mItem.getGid())) {
            RequestQueue queue = MyApplication.getHttpQueue();
            String url = MyApplication.getHost() + "good/id/" + holder.mItem.getGid();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    holder.rItem = MyApplication.getGson().fromJson(s, Good.class);
                    GoodContent.getCache().put(holder.rItem.getId(), holder.rItem);

                    MyRequest.setGoodImg(holder.mView.getContext(), holder.image, holder.rItem.getId());
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
        } else {
            holder.rItem = GoodContent.getCache().get(holder.mItem.getGid());

            MyRequest.setGoodImg(holder.mView.getContext(), holder.image, holder.rItem.getId());
            holder.name.setText(holder.rItem.getName());
            holder.price.setText("￥" + holder.rItem.getPrice());

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
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
        public AppCompatCheckBox select;
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
