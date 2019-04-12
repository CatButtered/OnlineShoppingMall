package com.onlineshoppingmall.communication.PageList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.ChatList.ChatActivity;
import com.onlineshoppingmall.communication.Database.MessageRecord;
import com.onlineshoppingmall.communication.Database.MessageRecordDao;
import com.onlineshoppingmall.communication.Database.ObjectRecordDao;
import com.onlineshoppingmall.communication.PageList.MessageContent.MessageItem;
import com.onlineshoppingmall.until.CircleImageView;
import com.onlineshoppingmall.until.MyRequest;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MessageRecyclerViewAdap";
    private List<MessageItem> mValues;
    private ObjectRecordDao objectRecordDao;
    private MessageRecordDao messageRecordDao;

    public MessageRecyclerViewAdapter() {
        mValues = new ArrayList<>();
        mValues.add(new MessageItem(2, "戴俊明", "你好", "12:00", "2"));
        mValues.add(new MessageItem(27, "陈扬", "你好", "12:01", "1"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        objectRecordDao = new ObjectRecordDao(view.getContext(), 1);
        messageRecordDao = new MessageRecordDao(view.getContext(), 1);

        List<MessageRecord> messageRecordList = messageRecordDao.query();
        System.out.println(messageRecordList.toString());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        MyRequest.setGoodImg(holder.mView.getContext(), holder.mAvatar, holder.mItem.avatar);
        holder.mName.setText(mValues.get(position).name);
        holder.mContent.setText(mValues.get(position).content);
        holder.mTime.setText(mValues.get(position).time);

        Badge badge = new QBadgeView(holder.mView.getContext())
                .bindTarget(holder.mMsg_num)
                .setBadgeNumber(Integer.valueOf(mValues.get(position).msg_num));
        badge.setBadgeGravity(Gravity.END | Gravity.BOTTOM);
        badge.setBadgeTextSize(16, true);
//        badge.setBadgePadding(8, true);
        badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                if (dragState == STATE_SUCCEED) {
//                    Toast.makeText(MainActivity.this, "1111", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", holder.mItem);
                intent.putExtras(bundle);
                holder.mView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MessageItem mItem;
        public CircleImageView mAvatar;
        public AppCompatTextView mName;
        public AppCompatTextView mContent;
        public AppCompatTextView mTime;
        public View mMsg_num;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatar = view.findViewById(R.id.avatar);
            mName = view.findViewById(R.id.name);
            mContent = view.findViewById(R.id.content);
            mTime = view.findViewById(R.id.time);
            mMsg_num = view.findViewById(R.id.msg_num);
        }

    }
}
