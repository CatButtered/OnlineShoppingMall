package com.onlineshoppingmall.shoppingcart;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.onlineshoppingmall.R;
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

        mViewModel = ViewModelProviders.of(getActivity()).get(ModelViewModel.class);
        all_selected = view.findViewById(R.id.cart_all_selected);
        all_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    mViewModel.total.setValue(mViewModel.all_total.getValue());
                    mViewModel.total.setValue(0.0);
                    mViewModel.all_total.setValue(0.0);
                    GoodContent.setITEMSChecked(true);
                    GoodContent.getAdapter().notifyDataSetChanged();
                    mViewModel.total.setValue(mViewModel.all_total.getValue());
                } else {
                    mViewModel.total.setValue(0.0);
                    mViewModel.all_total.setValue(0.0);
                    GoodContent.setITEMSChecked(false);
                    GoodContent.getAdapter().notifyDataSetChanged();
                }
            }
        });
        cart_total = view.findViewById(R.id.cart_total);
        mViewModel.total.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                cart_total.setText(String.valueOf(aDouble));
            }
        });
        return view;
    }

}
