package hiram.vendimia.services;

import android.content.Context;

import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class indexApi {
    static String api="http://www.mocky.io/v2/";
    static String api_sales ="5b4a542a2f000079001e0e4b";
    static String api_clients = "http://www.mocky.io/v2/5b4d8c3631000055005ebc16";

    public static void GetRequest(String path, final Map<String, String> params, final Map<String, String> headers,
                                  Response.Listener<String> onSuccess, Response.ErrorListener onError,
                                  final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api+path, onSuccess, onError){
            @Override
            protected Map<String, String> getParams() { return params;}
            @Override
            public  Map<String, String> getHeaders(){
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}
