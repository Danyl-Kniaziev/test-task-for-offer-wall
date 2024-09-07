package com.example.test_task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.test_task.entity.EntityDto;
import com.example.test_task.fragment.ImageViewFragment;
import com.example.test_task.fragment.TextViewFragment;
import com.example.test_task.fragment.WebViewFragment;
import com.example.test_task.service.ChangeFragmentServiceInterface;
import com.example.test_task.service.ChangeTextViewFragmentService;
import com.example.test_task.service.ChangeWebViewFragmentService;
import com.example.test_task.service.ResponseParserInterface;
import com.example.test_task.service.ResponseParser;
import com.example.test_task.service.StrategyChangeFragment;
import com.example.test_task.service.StrategyChangeFragmentInterface;
import com.example.test_task.util.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class MainActivity extends AppCompatActivity {
    private Button buttonNext;
    private FrameLayout frameLayout;
    private ResponseParserInterface responseParser;
    private List<String> allData;
    private int indexOfCurrentEntity;
    private StrategyChangeFragmentInterface strategyChangeFragment;
    private ImageViewFragment imageViewFragment;
    private WebViewFragment webViewFragment;
    private TextViewFragment textViewFragment;
    private ChangeFragmentServiceInterface changeTextViewFragmentService;
    private ChangeFragmentServiceInterface changeWebViewFragmentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        responseParser = ResponseParser.getInstance();

        new GetAllIdsTask().execute(NetworkUtil.generateURL(
                NetworkUtil.getUrlStringForGetAllIds()));
        indexOfCurrentEntity = -1;

        buttonNext = findViewById(R.id.buttonNext);

        imageViewFragment = new ImageViewFragment();
        webViewFragment = new WebViewFragment();
        textViewFragment = new TextViewFragment();

        changeFragment(imageViewFragment);
        changeFragment(webViewFragment);
        changeFragment(textViewFragment);

        changeWebViewFragmentService =
                new ChangeWebViewFragmentService(webViewFragment);
        changeTextViewFragmentService = new ChangeTextViewFragmentService(textViewFragment);

        strategyChangeFragment = new StrategyChangeFragment();
        strategyChangeFragment.setNewStrategy("webview", changeWebViewFragmentService);
        strategyChangeFragment.setNewStrategy("text", changeTextViewFragmentService);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexOfCurrentEntity++;
                indexOfCurrentEntity %= allData.size();
                String id = allData.get(indexOfCurrentEntity);

                URL url = NetworkUtil.generateURL(NetworkUtil.getUrlForGetObjectById(id));
                new GetByIdTask().execute(url);
            }
        });
    }

    void changeFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    class Task extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = NetworkUtil.getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    class GetAllIdsTask extends Task {
        @Override
        protected void onPostExecute(String response) {
            allData = responseParser.getAllData(response);
        }
    }

    class GetByIdTask extends Task {
        @Override
        protected void onPostExecute(String response) {
            EntityDto entityDto = responseParser.getEntity(response);
            Optional<ChangeFragmentServiceInterface> optional =
                    strategyChangeFragment.getFragment(entityDto.getType());
            if (optional.isPresent()) {
                ChangeFragmentServiceInterface changeFragmentService = optional.get();
                Fragment fragment = changeFragmentService
                        .changeFragment(entityDto.getMessageOrUrl());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                Log.i("commit", "id" + entityDto.getId());
            }
        }
    }
}