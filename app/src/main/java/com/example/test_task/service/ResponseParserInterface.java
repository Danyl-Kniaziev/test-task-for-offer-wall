package com.example.test_task.service;

import com.example.test_task.entity.EntityDto;

import java.util.List;

public interface ResponseParserInterface {
    public List<String> getAllData (String string);

    public EntityDto getEntity(String Sting);
}
