package com.onlineshoppingmall.communication.PageList;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.ChatList.ChatActivity;
import com.onlineshoppingmall.communication.PageList.MessageContent.MessageItem;
import com.onlineshoppingmall.communication.PageList.MessageFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.until.CircleImageView;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * {@link RecyclerView.Adapter} that can display a {@link MessageItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {

    private final List<MessageItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private static final BitmapFactory bitmapFactory = new BitmapFactory();

    public MessageRecyclerViewAdapter(List<MessageItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        MyRequest.setBitmap(holder.mView.getContext(), holder.mAvatar, holder.mItem.avatar);
        holder.mShop.setText(mValues.get(position).shop);
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
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Toast.makeText(holder.mView.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(holder.mView.getContext(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", holder.mItem);
                    intent.putExtras(bundle);
                    holder.mView.getContext().startActivity(intent);
//                    mListener.onListFragmentInteraction(holder.mItem);
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
        public MessageItem mItem;
        public CircleImageView mAvatar;
        public AppCompatTextView mShop;
        public AppCompatTextView mContent;
        public AppCompatTextView mTime;
        public View mMsg_num;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatar = view.findViewById(R.id.avatar);
            mShop = view.findViewById(R.id.shop);
            mContent = view.findViewById(R.id.content);
            mTime = view.findViewById(R.id.time);
            mMsg_num = view.findViewById(R.id.msg_num);
        }

    }
}
