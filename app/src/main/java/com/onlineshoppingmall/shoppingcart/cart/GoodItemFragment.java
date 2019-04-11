package com.onlineshoppingmall.shoppingcart.cart;

import android.arch.lifecycle.ViewModelProviders;
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
import com.onlineshoppingmall.shoppingcart.Database.GoodItemDao;
import com.onlineshoppingmall.shoppingcart.cart.GoodContent.GoodItem;
import com.onlineshoppingmall.shoppingcart.cart.data.ModelViewModel;

public class GoodItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private GoodItemDao dao;

    public GoodItemFragment() {
    }

    @SuppressWarnings("unused")
    public static GoodItemFragment newInstance(int columnCount) {
        GoodItemFragment fragment = new GoodItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_gooditem_list, container, false);

        dao = new GoodItemDao(view.getContext(), 1);
        GoodContent.setITEMS(dao.query());

        ModelViewModel viewModel = ViewModelProviders.of(getActivity()).get(ModelViewModel.class);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            GoodItemRecyclerViewAdapter adapter = new GoodItemRecyclerViewAdapter(GoodContent.getITEMS(), mListener, viewModel);
            GoodContent.setAdapter(adapter);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(GoodItem item);
    }
}
