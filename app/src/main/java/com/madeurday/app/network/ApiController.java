package com.madeurday.app.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.madeurday.app.savedDataHelper.SaveDataManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ApiController {
    private static ApiController mInstance;
    private Context context;
    private Map<String, String> params;

    public  ApiController(Context context) {
        this.context = context;
    }

    public static ApiController getInstance(Context context) {
        if (mInstance == null)
            mInstance = new ApiController(context);
        return mInstance;
    }

    public void setParams(Map<String, String> params){
       this.params = params;
    }

    public void getDataFromNetwork(String url,JSONObject jsonObject, final NetworkCallBack mNetworkCallBack){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.
//        requestQueue.


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject
            ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("===========", "   "+response.toString());
                       // JSONObject obj = null;
                        try {
                          //  obj = new JSONObject(response);
                            mNetworkCallBack.successResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
//                        error.
                        Log.e("===========", " eroor  "+error.getMessage());

                        mNetworkCallBack.error();
                        // Do something when error occurred

                    }
                }


        ){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e("===========", " eroor  "+response.allHeaders);
                SaveDataManager saveDataManager = new SaveDataManager(context);
                saveDataManager.setLoginToken(response.allHeaders.get(5).getValue());
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams(){
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                return params;
            }
        };
        ;

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.
        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
//        requestQueue.


    }

    public void getDataFromNetworkGetMethod(String url, final NetworkCallBack mNetworkCallBack){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.
//        requestQueue.
        // Initialize a new JsonObjectRequest instance
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.GET,
                url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        Log.e("===========", "   "+response.toString());
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            mNetworkCallBack.successResponse(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
//                        error.
                        Log.e("======jhgsdfj=====", " eroor  "+error.getMessage());

                        mNetworkCallBack.error();
                        // Do something when error occurred

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams(){


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
//                try {
//                    SaveDataManager sm = SaveDataManager.getInstance(context);
//                    String token = sm.getOauthToken();
//                    String userKey = token.trim() +":" + sm.getPhoneNumber().trim();
//                    params.put("Content-Type","application/json");
//                   // byte[] encodedBytes= android.util.Base64.encode(userKey.getBytes("UTF-8"), android.util.Base64.DEFAULT);
//                   String encodedBytes=  Base64.encodeToString(userKey.getBytes(), Base64.NO_WRAP);
//
//                    params.put("oauthToken", token);
//                    params.put("userKey", encodedBytes);
//                    return params;
//                }catch (Exception e){
//
//                }
                params.put("authorization", "Basic YWRtaW46MTIzNA==");
                if(params!=null){
                    return params;
                }


                return super.getHeaders();
            }
        };
        ;


//        requestQueue.
        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
//        requestQueue.


    }


}
