package com.example.gaoyounan.weather_forecast;

/**
 * Created by gaoyounan on 2018/3/14.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gaoyounan on 2018/3/5.
 */

public class RequestQueueSignleton {

    private static RequestQueueSignleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestQueueSignleton(Context context)
    {
        mCtx = context.getApplicationContext();
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSignleton getInstance(Context context){

        if(mInstance == null)
        {
            mInstance = new RequestQueueSignleton(context.getApplicationContext());
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

}
