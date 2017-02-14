package com.sourceedge.preco.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.location.controller.MapLocation;
import com.sourceedge.preco.register.controller.PrefManager;
import com.sourceedge.preco.register.controller.WelcomeActivity;
import com.sourceedge.preco.support.models.KeyValuePair;
import com.sourceedge.preco.support.models.LocationDevices;
import com.sourceedge.preco.support.models.Locations;
import com.sourceedge.preco.support.models.Options;
import com.sourceedge.preco.support.models.ServiceOptions;
import com.sourceedge.preco.support.models.Services;
import com.sourceedge.preco.support.models.SubmitOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Centura User3 on 1/27/2017.
 */

public class Class_SyncApi {
    static SharedPreferences sharedPreferences;
    private static PrefManager prefManager;
    static int mStatusCode = 0;
    static Gson gson;

    public static void LoginApi(final Context context, final EditText username, final EditText password, String deviceid, String os) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("UserName", username.getText().toString().trim()));
        params.add(new KeyValuePair("Password", password.getText().toString().trim()));
        params.add(new KeyValuePair("DeviceId", deviceid));
        params.add(new KeyValuePair("OS", os));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.Login, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            String s = jsonObject.optString("Token");
                            editor.putString(Class_Genric.Sp_Status, "LoggedIn");
                            editor.putString(Class_Genric.Sp_Token, jsonObject.optString("Token"));
                            editor.putString(Class_Genric.Sp_Balance, jsonObject.optString("Balance"));
                            editor.commit();
                            prefManager = new PrefManager(context);
                            if (!prefManager.isFirstTimeLaunch())
                                ((Activity) context).startActivity(new Intent(context, HomeScreen.class));
                            else
                                ((Activity) context).startActivity(new Intent(context, WelcomeActivity.class));
                            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            ((Activity) context).finish();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                username.setError("Username or Password Invalid");
                                break;
                            case 401:
                                password.setError("Password Invalid");
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void ExternalLoginApi(final Context context, final EditText username, final EditText password, String deviceid, String os, String type) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("UserName", username.getText().toString().trim()));
        params.add(new KeyValuePair("Password", password.getText().toString().trim()));
        params.add(new KeyValuePair("DeviceId", deviceid));
        params.add(new KeyValuePair("OS", os));
        params.add(new KeyValuePair("Type", type));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.ExternalLogin, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            String s = jsonObject.optString("Token");
                            editor.putString(Class_Genric.Sp_Status, "LoggedIn");
                            editor.putString(Class_Genric.Sp_Token, jsonObject.optString("Token"));
                            editor.putString(Class_Genric.Sp_Balance, jsonObject.optString("Balance"));
                            editor.commit();
                            prefManager = new PrefManager(context);
                            if (!prefManager.isFirstTimeLaunch())
                                ((Activity) context).startActivity(new Intent(context, HomeScreen.class));
                            else
                                ((Activity) context).startActivity(new Intent(context, WelcomeActivity.class));
                            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            ((Activity) context).finish();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                username.setError("Username or Password Invalid");
                                break;
                            case 401:
                                password.setError("Password Invalid");
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void SignUpApi(final Context context, final EditText emailid, final EditText password, EditText phoneno, String deviceid, String os, String referralcode) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("EmailId", emailid.getText().toString().trim()));
        params.add(new KeyValuePair("Password", password.getText().toString().trim()));
        params.add(new KeyValuePair("PhoneNo", phoneno.getText().toString().trim()));
        params.add(new KeyValuePair("DeviceId", deviceid));
        params.add(new KeyValuePair("OS", os));
        params.add(new KeyValuePair("ReferralCode", referralcode));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.SignUp, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            String s = jsonObject.optString("Token");
                            editor.putString(Class_Genric.Sp_Status, "LoggedIn");
                            editor.putString(Class_Genric.Sp_Token, jsonObject.optString("Token"));
                            editor.commit();
                            prefManager = new PrefManager(context);
                            if (!prefManager.isFirstTimeLaunch())
                                ((Activity) context).startActivity(new Intent(context, HomeScreen.class));
                            else
                                ((Activity) context).startActivity(new Intent(context, WelcomeActivity.class));
                            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            ((Activity) context).finish();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                password.setError("Username or Password Invalid");
                                break;
                            case 401:
                                password.setError("Password Invalid");
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void ServiceApi(final Context context) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);

        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.GetServices, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            ArrayList<Services> services = new ArrayList<Services>();
                            gson = new Gson();
                            JSONArray jsonObject = new JSONArray(response);
                            Type listtype = new TypeToken<ArrayList<Services>>() {
                            }.getType();
                            services = gson.fromJson(jsonObject.toString(), listtype);
                            Class_Model_DB.setServiceslist(services);
                            HomeScreen.InitializeAdapter(context);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                //username.setError("Username or Password Invalid");
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                //password.setError("Password Invalid");
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void GetLocationApi(final Context context, String serviceid) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("ServiceId", serviceid));
        //params.add(new KeyValuePair("Latitude", lat + ""));
        //params.add(new KeyValuePair("Longitude", longitude + ""));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.GetLocation, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            ArrayList<Locations> locations = new ArrayList<Locations>();
                            gson = new Gson();
                            JSONArray jsonObject = new JSONArray(response);
                            Type listtype = new TypeToken<ArrayList<Locations>>() {
                            }.getType();
                            locations = gson.fromJson(jsonObject.toString(), listtype);
                            Class_Model_DB.setLocationlist(locations);
                            context.startActivity(new Intent(context, MapLocation.class));

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void GetLocationDevicesApi(final Context context, String serviceid, String locationid) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("ServiceId", serviceid));
        params.add(new KeyValuePair("LocationId", locationid));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.GetLocationDevices, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            ArrayList<LocationDevices> locationDevices = new ArrayList<LocationDevices>();
                            gson = new Gson();
                            JSONArray jsonObject = new JSONArray(response);
                            Type listtype = new TypeToken<ArrayList<LocationDevices>>() {
                            }.getType();
                            locationDevices = gson.fromJson(jsonObject.toString(), listtype);
                            Class_Model_DB.setLocationDevicesList(locationDevices);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void GetServiceOptionsApi(final Context context, String serviceid, String locationid) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("ServiceId", serviceid));
        params.add(new KeyValuePair("LocationId", locationid));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.GetServiceOptions, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            ArrayList<ServiceOptions> serviceOptions = new ArrayList<ServiceOptions>();
                            gson = new Gson();
                            JSONArray jsonObject = new JSONArray(response);
                            Type listtype = new TypeToken<ArrayList<ServiceOptions>>() {
                            }.getType();
                            serviceOptions = gson.fromJson(jsonObject.toString(), listtype);
                            Class_Model_DB.setServiceOptionsList(serviceOptions);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void GetPriceApi(final Context context, String serviceid, String locationid, String deviceid, String time, String units, String qty, String couponcode, ArrayList<Options> options) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<Options> option = new ArrayList<Options>();

        for (int i = 0; i < options.size(); i++) {
            Options opt = new Options();
            opt.setId(options.get(i).getId());
            opt.setSelectedOption(option.get(i).getSelectedOption());
            option.add(opt);
        }
        SubmitOrder submitOrder = new SubmitOrder();
        submitOrder.setServiceId(serviceid);
        submitOrder.setDeviceId(deviceid);
        submitOrder.setLocationId(locationid);
        submitOrder.setTime(time);
        submitOrder.setUnits(units);
        submitOrder.setQty(qty);
        submitOrder.setCouponCode(couponcode);
        submitOrder.setOptions(option);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(submitOrder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class_Genric.ShowDialog(context, "Loading...", true);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.GetPrice, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        Double price = Double.valueOf(response.optString("Price"));
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void SubmitOrderApi(final Context context, String serviceid, String locationid, String deviceid, String time, String units, String qty, String couponcode, String fileid, ArrayList<Options> options) {
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<Options> option = new ArrayList<Options>();

        for (int i = 0; i < options.size(); i++) {
            Options opt = new Options();
            opt.setId(options.get(i).getId());
            opt.setSelectedOption(option.get(i).getSelectedOption());
            option.add(opt);
        }
        SubmitOrder submitOrder = new SubmitOrder();
        submitOrder.setServiceId(serviceid);
        submitOrder.setDeviceId(deviceid);
        submitOrder.setLocationId(locationid);
        submitOrder.setTime(time);
        submitOrder.setUnits(units);
        submitOrder.setQty(qty);
        submitOrder.setCouponCode(couponcode);
        submitOrder.setFileId(fileid);
        submitOrder.setOptions(option);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(submitOrder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class_Genric.ShowDialog(context, "Loading...", true);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.SubmitOrder, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        String jobOrderNo = response.optString("JobOrderNo");
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(context, "Token Invalid", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + sharedPreferences.getString(Class_Genric.Sp_Token, ""));
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

}