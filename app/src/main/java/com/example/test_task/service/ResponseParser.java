package com.example.test_task.service;

import com.example.test_task.entity.EntityDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class ResponseParser implements ResponseParserInterface {
    private final static String IDS_KEY = "data";
    private final static String ID_KEY = "id";
    private final static String TYPE_KEY = "type";
    private final static String TEXT_KEY = "text";
    private final static String WEB_VIEW_KEY = "webview";
    private final static String IMAGE_KEY = "image";
    private final static String MESSAGE = "message";
    private final static String URL = "url";
    private static final ResponseParser  responseParser= new ResponseParser();

    private ResponseParser() {

    }

    public static ResponseParser getInstance() {
        return responseParser;
    }
    @Override
    public List<String> getAllData(String response) {
        List<String> allData = new LinkedList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(IDS_KEY);
            for (int i = 0; i < jsonArray.length(); i++) {
                allData.add(jsonArray.getJSONObject(i).getString(ID_KEY));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return allData;
    }

    @Override
    public EntityDto getEntity(String response) {
        EntityDto entityDto = new EntityDto();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            entityDto.setId(jsonResponse.getString(ID_KEY));
            String type = jsonResponse.getString(TYPE_KEY);
            entityDto.setType(type);
            if (type.equals(TEXT_KEY)) {
                entityDto.setMessageOrUrl(jsonResponse.getString(MESSAGE));
            } else if (type.equals(IMAGE_KEY) || type.equals(WEB_VIEW_KEY)) {
                entityDto.setMessageOrUrl(jsonResponse.getString(URL));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return entityDto;
    }
}
