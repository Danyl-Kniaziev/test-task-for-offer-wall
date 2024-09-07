package com.example.test_task.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test_task.R;


public class TextViewFragment extends Fragment {

    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text_view, container, false);
        textView = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    public void setTextInTextView(String text) {
        textView.setText(text);
    }
}