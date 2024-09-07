package com.example.test_task.service;


import java.util.Optional;

public interface StrategyChangeFragmentInterface {
    public Optional<ChangeFragmentServiceInterface> getFragment(String type);

    public void setNewStrategy(String type,
                               ChangeFragmentServiceInterface changeFragmentService);
}
