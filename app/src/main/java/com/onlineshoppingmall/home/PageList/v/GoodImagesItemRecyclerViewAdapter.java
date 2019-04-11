package com.onlineshoppingmall.home.PageList.v;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.home.PageList.v.GoodImagesFragment.OnListFragmentInteractionListener;
import com.onlineshoppingmall.home.PageList.v.GoodsImagesContent.GoodsImagesItem;
import com.onlineshoppingmall.until.MyRequest;

import java.util.List;

public class GoodImagesItemRecyclerViewAdapter extends RecyclerView.Adapter<GoodImagesItemRecyclerViewAdapter.ViewHolder> {

    private final List<GoodsImagesItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public GoodImagesItemRecyclerViewAdapter(List<GoodsImagesItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goodimagesitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.title.setCompoundDrawablesWithIntrinsicBounds(holder.mItem.small_icon, 0, 0, 0);
        holder.title.setText(holder.mItem.title);
        holder.intro.setText(holder.mItem.intro);
        MyRequest.setBitmap(holder.mView.getContext(),holder.good1,holder.mItem.good1);
        MyRequest.setBitmap(holder.mView.getContext(),holder.good2,holder.mItem.good2);

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
        public GoodsImagesItem mItem;

        public AppCompatTextView title;
        public AppCompatTextView intro;
        public AppCompatImageView good1;
        public AppCompatImageView good2;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.home_page_v_title);
            intro = view.findViewById(R.id.home_page_v_introduction);
            good1 = view.findViewById(R.id.home_page_v_good1);
            good2 = view.findViewById(R.id.home_page_v_good2);
        }

    }
}
