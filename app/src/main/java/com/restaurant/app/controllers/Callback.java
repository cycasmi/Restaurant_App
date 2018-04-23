package com.restaurant.app.controllers;


public interface Callback<T>
{
    int OK    = 1;
    int ERROR = 2;
    void execute(T result, int status);
}
