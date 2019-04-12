package com.onlineshoppingmall.penson.addord.add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.onlineshoppingmall.MyApplication;
import com.onlineshoppingmall.R;
import com.onlineshoppingmall.penson.addord.add.AddressContent.AddressItem;
import com.onlineshoppingmall.remote_entity.User;
import com.onlineshoppingmall.until.ShareData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddressRecyclerViewAdapter extends RecyclerView.Adapter<AddressRecyclerViewAdapter.ViewHolder> {

    private final List<AddressItem> mValues;

    private String uid;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AddressRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    };

    public AddressRecyclerViewAdapter(List<AddressItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_address_item, parent, false);

        ShareData shareData = new ShareData(view.getContext());
        uid = shareData.readShared("uid");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.username.setText(holder.mItem.getUsername());
        holder.name.setText(holder.mItem.getName());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), AddressInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", holder.mItem);
                intent.putExtras(bundle);
                holder.mView.getContext().startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = MyApplication.getHttpQueue();
                String _url = MyApplication.getHost() + "address/deleteAddress";
                StringRequest request = new StringRequest(Request.Method.POST, _url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        User user = MyApplication.getGson().fromJson(s, User.class);
                        if (user == null) {
                            Toast.makeText(holder.mView.getContext(), "删除错误!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(holder.mView.getContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                            mValues.remove(holder.mItem);
                            handler.sendEmptyMessage(1);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> map = new HashMap<>();
                        map.put("uid", uid);
                        map.put("address", holder.mItem.getName());
                        return map;
                    }
                };
                queue.add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public AddressItem mItem;

        public AppCompatTextView username;
        public AppCompatTextView name;
        public AppCompatButton edit;
        public AppCompatButton delete;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            username = view.findViewById(R.id.address_username);
            name = view.findViewById(R.id.address_name);
            edit = view.findViewById(R.id.address_edit);
            delete = view.findViewById(R.id.address_delete);
        }

    }
}
