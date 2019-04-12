package com.onlineshoppingmall.shoppingcart;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.penson.addord.OrderActivity;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent;
import com.onlineshoppingmall.shoppingcart.cart.GoodItemFragment;
import com.onlineshoppingmall.shoppingcart.cart.data.ModelViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartPageFragment extends Fragment {

    private ModelViewModel mViewModel;
    private AppCompatCheckBox all_selected;
    private AppCompatTextView cart_total;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                all_selected.setChecked(false);
            } else if (msg.what == 1) {
                GoodContent.getAdapter().notifyDataSetChanged();
                cart_total.setText(String.valueOf(GoodContent.getPrice()));
            }
        }
    };

    public ShoppingCartPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_shopping_cart_page, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.cart_page_list, GoodItemFragment.newInstance(1));
        transaction.commit();

        mViewModel = ViewModelProviders.of(getActivity()).get(ModelViewModel.class);
        all_selected = view.findViewById(R.id.cart_all_selected);
        all_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GoodContent.setITEMSChecked(true);
                    mViewModel.isAll.setValue(true);
                    mViewModel.isChanged.setValue(true);
                } else {
                    GoodContent.setITEMSChecked(false);
                    mViewModel.isChanged.setValue(true);
                }
            }
        });
        cart_total = view.findViewById(R.id.cart_total);
        mViewModel.isAll.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (!aBoolean) {
                    handler.sendEmptyMessage(0);
                }
            }
        });

        mViewModel.isChanged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    handler.sendEmptyMessage(1);
                    mViewModel.isChanged.setValue(false);
                }
            }
        });
//        cart_total.setText(String.valueOf(GoodContent.getPrice()));
        AppCompatButton cart_settle = view.findViewById(R.id.cart_settle);
        cart_settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



}
