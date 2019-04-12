package com.onlineshoppingmall.shoppingcart.cart.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.onlineshoppingmall.shoppingcart.cart.GoodContent;

import java.util.List;

public class ModelViewModel extends ViewModel {

    public MutableLiveData<Boolean> isAll = new MutableLiveData<>();

    public MutableLiveData<Boolean> isNone = new MutableLiveData<>();

    public MutableLiveData<Boolean> isChanged = new MutableLiveData<>();

}
