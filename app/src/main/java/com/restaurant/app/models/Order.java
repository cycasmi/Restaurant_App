package com.restaurant.app.models;

public class Order
{

    public final int    id;
    public final int    sessionId;
    public final int    dishId;
    public final String comments;
    public final String status;


    public Order(int id, int sessionId, int dishId, String comments, String status)
    {
        this.id = id;
        this.sessionId = sessionId;
        this.dishId = dishId;
        this.comments = comments;
        this.status = status;
    }
}
