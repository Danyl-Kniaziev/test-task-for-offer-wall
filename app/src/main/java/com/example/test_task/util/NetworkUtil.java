package com.example.test_task.util;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {
    private static final String API_BASE_URL = "http://demo3005513.mockable.io/api/v1/";
    private static final String ALL_IDS_GET = "entities/getAllIds";
    private static final String OBJECT_ID_GET = "object/";
    private static final String URI_FOR_GET_ALL_IDS = API_BASE_URL + ALL_IDS_GET;

    public static URL generateURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getUrlStringForGetAllIds() {
        return  URI_FOR_GET_ALL_IDS;
    }

    public static String getUrlForGetObjectById(String objectId) {
        return API_BASE_URL + OBJECT_ID_GET + objectId;
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i("done", "urlConnection = (HttpURLConnection) url.openConnection()");
            urlConnection.connect();
            Log.i("done", "urlConnection.connect();");
            InputStream inputStream = urlConnection.getInputStream();
            Log.i("done", "InputStream inputStream = urlConnection.getInputStream();");
            Scanner scanner = new Scanner(inputStream);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
