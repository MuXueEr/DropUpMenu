package com.zhangyp.higo.dropupmenu.fragment;//package com.higo.readbook.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zhangyp.higo.dropupmenu.R;
import com.zhangyp.higo.dropupmenu.adapter.BookSettingAdapter;
import com.zhangyp.higo.dropupmenu.bean.SettingArray;
import com.zhangyp.higo.dropupmenu.bean.SettingItem;
import com.zhangyp.higo.dropupmenu.view.FixGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BookSettingFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.setting_gridView)
    FixGridView gridView;

    // TODO: Rename and change types of parameters
    private SettingArray mParam1;

    private OnFragmentInteractionListener mListener;
    private View view;

    public BookSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BookSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookSettingFragment newInstance(ArrayList<SettingItem> param1) {

        SettingArray settingArray = new SettingArray();
        settingArray.setList(param1);
        BookSettingFragment fragment = new BookSettingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, settingArray);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SettingArray) getArguments().getSerializable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_setting_fragment, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;

    }

    private void init() {

        BookSettingAdapter adapter = new BookSettingAdapter(getActivity(), mParam1.getList());
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String action = mParam1.getList().get(position).getAction();

        Toast.makeText(getActivity(),"点击条目"+position,Toast.LENGTH_SHORT).show();

        onButtonPressed();


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
