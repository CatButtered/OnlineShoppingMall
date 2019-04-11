package com.onlineshoppingmall.home.PageList.h;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.home.PageList.h.CircleIconFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.home.PageList.h.CircleItemContent.CircleItem;
import com.onlineshoppingmall.until.CircleImageView;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CircleItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CircleIconItemRecyclerViewAdapter extends RecyclerView.Adapter<CircleIconItemRecyclerViewAdapter.ViewHolder> {

    private final List<CircleItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CircleIconItemRecyclerViewAdapter(List<CircleItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_circleiconitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        MyRequest.setBitmap(holder.mView.getContext(), holder.imageView, holder.mItem.resourceId);
        holder.textView.setText(holder.mItem.content);

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
        public CircleItem mItem;

        public CircleImageView imageView;
        public AppCompatTextView textView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.home_page_list_h_icon);
            textView = view.findViewById(R.id.home_page_list_h_text);
        }

    }
}
