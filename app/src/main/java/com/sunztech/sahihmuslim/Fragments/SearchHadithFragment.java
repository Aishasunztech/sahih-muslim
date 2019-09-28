package com.sunztech.sahihmuslim.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunztech.sahihmuslim.ChapterDetailsActivity;
import com.sunztech.sahihmuslim.R;
import com.sunztech.sahihmuslim.Utilities.AppConstants;
import com.sunztech.sahihmuslim.Utilities.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHadithFragment extends Fragment {

    @BindView(R.id.et_search_hadith)
    EditText et_search;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btnSearch)
    TextView btnSearc;
    RadioButton radioButton;


    private String columnName;


    public SearchHadithFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_hadith, container, false);
        ButterKnife.bind(this, view);

        radioButton = view.findViewById(R.id.radioEng);
        MyUtils.setRadioTypeFace(getActivity(),radioButton);
        MyUtils.setTypeface(getActivity(),null,null,btnSearc);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioEng:
                        radioButton = group.findViewById(R.id.radioEng);
                        et_search.setText(null);
                        et_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                        break;
                    case R.id.radioUrdu:
                        radioButton = group.findViewById(R.id.radioUrdu);
                        et_search.setText(null);

                        et_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                        break;
                    case R.id.radioHadees:
                        radioButton = group.findViewById(R.id.radioHadees);
                        et_search.setText(null);

                        et_search.setInputType(InputType.TYPE_CLASS_NUMBER);
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                        break;
                    case R.id.radioRavi:
                        radioButton = group.findViewById(R.id.radioRavi);
                        et_search.setText(null);

                        et_search.setInputType(InputType.TYPE_CLASS_TEXT);

                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        break;
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @OnClick(R.id.btnSearch)
    public void searchClick() {
        String searchingValue = et_search.getText().toString();

        if (!searchingValue.equals("")) {

            Intent intent = new Intent(getActivity(), ChapterDetailsActivity.class);
            intent.putExtra(AppConstants.CHAPTER_DETAILS_SENDER, AppConstants.SEARCH_FRAGMENT);
            String columnName = MyUtils.refactorColumn(radioButton.getText().toString());
            intent.putExtra(AppConstants.SEARCH_COLUMN_NAME, columnName);
            intent.putExtra(AppConstants.SEARCHING_VALUE, searchingValue);

            startActivity(intent);
        }
        else{
            Toast.makeText(getActivity(), "Please search any word", Toast.LENGTH_SHORT).show();
        }
    }




}
