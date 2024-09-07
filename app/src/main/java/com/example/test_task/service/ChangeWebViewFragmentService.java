package com.example.test_task.service;

import androidx.fragment.app.Fragment;

import com.example.test_task.fragment.WebViewFragment;

public class ChangeWebViewFragmentService implements ChangeFragmentServiceInterface{
    private WebViewFragment webViewFragment;

    public ChangeWebViewFragmentService(WebViewFragment fragment) {
        webViewFragment = fragment;
    }



    @Override
    public Fragment changeFragment(String url) {
        webViewFragment.loadNewUrl(url);
        return webViewFragment;
    }
}
