package com.onlineshoppingmall.communication.ChatList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.communication.Database.MessageRecordDao;

public class ChatFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MessageRecordDao messageRecordDao;

    public ChatItemRecyclerViewAdapter adapter;

    public ChatFragment() {
    }

    @SuppressWarnings("unused")
    public static ChatFragment newInstance(int columnCount) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatitem_list, container, false);

        messageRecordDao = new MessageRecordDao(view.getContext(), 1);
        MessageRecordContent.ITEMS = messageRecordDao.query();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new ChatItemRecyclerViewAdapter(MessageRecordContent.ITEMS);
            MessageRecordContent.adapter = adapter;
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

}
