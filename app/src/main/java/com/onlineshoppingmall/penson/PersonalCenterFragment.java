package com.onlineshoppingmall.penson;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.logreg.LoginActivity;
import com.onlineshoppingmall.penson.addord.add.AddressActivity;
import com.onlineshoppingmall.until.ShareData;

public class PersonalCenterFragment extends Fragment {

    private View statusBarView;
    private LinearLayoutCompat address_block;

    private AppCompatImageView logout;
    private ShareData shareData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        getActivity().getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                initstatusBarView();
                getActivity().getWindow().getDecorView().removeOnLayoutChangeListener(this);
            }
        });

        address_block = view.findViewById(R.id.pc_address_block);
        address_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });

        shareData = new ShareData(view.getContext());
        logout = view.findViewById(R.id.pc_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData.removeShared("uid");
                shareData.removeShared("username");
                shareData.removeShared("password");
                shareData.removeShared("isrem");
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

        return view;
    }

    private void initstatusBarView() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getActivity().getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.pc_bg_top);
        }
    }

}
