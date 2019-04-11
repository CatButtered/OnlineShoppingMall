package com.onlineshoppingmall.shoppingcart.cart.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.onlineshoppingmall.shoppingcart.cart.GoodContent;

import java.util.List;

public class ModelViewModel extends ViewModel {

    public MutableLiveData<Double> total = new MutableLiveData<>();

    public MutableLiveData<Double> all_total = new MutableLiveData<>();

//    public MutableLiveData<List<GoodContent.GoodItem>> list = new MutableLiveData<>();

}
