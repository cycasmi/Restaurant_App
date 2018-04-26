package com.restaurant.app.controllers;

import android.content.Context;

public class PreferenceController
{

    private final String SESSION_KEY = "sessionKey";

    private Context mContext;

    public PreferenceController(Context context)
    {
        mContext = context;
    }

    public int getSessionId()
    {
        return mContext.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE)
                .getInt(SESSION_KEY, 0);
    }


}
