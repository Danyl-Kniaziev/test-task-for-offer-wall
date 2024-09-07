package com.example.test_task.service;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StrategyChangeFragment implements StrategyChangeFragmentInterface{
    Map<String, ChangeFragmentServiceInterface> strategies;

    public StrategyChangeFragment() {
        strategies = new HashMap<String, ChangeFragmentServiceInterface>();
    }

    public void setNewStrategy(String type,
                               ChangeFragmentServiceInterface changeFragmentService) {
        strategies.put(type, changeFragmentService);
    }


    @Override
    public Optional<ChangeFragmentServiceInterface> getFragment(String type) {
        return Optional.ofNullable(strategies.getOrDefault(type, null));
    }
}
