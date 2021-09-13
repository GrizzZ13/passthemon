package com.backend.passthemon.service;

public interface KeyConstantService {
    String getValueByName(String name);
    void updateValueByName(String name,String value);
    boolean checkStatusCode(String name,String value);
}
