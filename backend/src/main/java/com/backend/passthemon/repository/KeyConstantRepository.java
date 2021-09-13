package com.backend.passthemon.repository;

public interface KeyConstantRepository {
    String getValueByName(String name);
    void updateValueByName(String name,String value);
    boolean checkStatusCode(String name,String value);
}
