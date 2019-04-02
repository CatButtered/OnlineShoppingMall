package com.onlineshoppingmall.communication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;

import cn.bingoogolapple.badgeview.annotation.BGABadge;


/**
 * A simple {@link Fragment} subclass.
 */
@BGABadge({AppCompatTextView.class})
public class MessagePageFragment extends Fragment {


    public MessagePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_page, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.list, MessageFragment.newInstance(1));
        transaction.commit();
        return view;
    }

}
