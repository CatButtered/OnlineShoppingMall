package com.onlineshoppingmall.communication.ChatList;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.Database.MessageRecord;
import com.onlineshoppingmall.communication.Database.MessageRecordDao;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;


public class ChatItemRecyclerViewAdapter extends RecyclerView.Adapter<ChatItemRecyclerViewAdapter.ViewHolder> {

    private List<MessageRecord> mValues;
    private MessageRecordDao messageRecordDao;

    public ChatItemRecyclerViewAdapter(List<MessageRecord> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chatitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        MyRequest.setGoodImg(holder.mView.getContext(),holder.image1,2);
        MyRequest.setGoodImg(holder.mView.getContext(),holder.image2,2);
        if (holder.mItem.getKind() != 0) {
            holder.content1.setText(holder.mItem.getContent());
            holder.content1.setVisibility(View.VISIBLE);
            holder.content2.setVisibility(View.GONE);
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.GONE);
        } else {
            holder.content2.setText(holder.mItem.getContent());
            holder.content2.setVisibility(View.VISIBLE);
            holder.content1.setVisibility(View.GONE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image1.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public AppCompatImageView image1;
        public AppCompatImageView image2;

        public AppCompatTextView content1;
        public AppCompatTextView content2;
        public MessageRecord mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image1 = view.findViewById(R.id.receive_icon);
            image2 = view.findViewById(R.id.send_icon);
            content1 = view.findViewById(R.id.receive_msg_text);
            content2 = view.findViewById(R.id.send_msg_text);
        }

    }
}
