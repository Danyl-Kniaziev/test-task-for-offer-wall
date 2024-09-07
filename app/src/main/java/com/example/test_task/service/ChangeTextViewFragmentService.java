package com.example.test_task.service;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.test_task.entity.EntityDto;
import com.example.test_task.fragment.TextViewFragment;

public class ChangeTextViewFragmentService implements ChangeFragmentServiceInterface{
    private TextViewFragment textViewFragment;

    public ChangeTextViewFragmentService(TextViewFragment fragment) {
        textViewFragment = fragment;
    }

    @Override
    public Fragment changeFragment(String message) {
        textViewFragment.setTextInTextView(message);
        return textViewFragment;
    }
}
