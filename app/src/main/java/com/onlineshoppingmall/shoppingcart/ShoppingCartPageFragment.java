package com.onlineshoppingmall.shoppingcart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineshoppingmall.R;
import com.onlineshoppingmall.shoppingcart.cart.GoodItemFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartPageFragment extends Fragment {


    public ShoppingCartPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart_page, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.cart_page_list, GoodItemFragment.newInstance(1));
        transaction.commit();
        return view;
    }

}
