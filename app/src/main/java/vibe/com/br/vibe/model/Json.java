package vibe.com.br.vibe.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Json {

    public static String response;

    public String getFromUrl(String requestURL) throws IOException
    {
        StringBuilder response;
        URL url;
        BufferedReader buffer;
        String inputLine;

        response = new StringBuilder();
        url = new URL(requestURL);

        buffer = new BufferedReader(new InputStreamReader(url.openStream()));

        while ((inputLine = buffer.readLine()) != null)
            response.append(inputLine);

        buffer.close();

        return response.toString();
    }

    public String postFromUrl(String requestURL, Context context, final Map<String, String> params ) throws MalformedURLException {
        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest strRequest = new StringRequest(
                Request.Method.POST,
                requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta) {
                        Json.response = resposta;
                        Log.d("Candidatos",resposta);
                    }
                }
                ,
                null
            ){
                @Override
                public Map<String, String> getHeaders()
                {
                    params.put("Content-Type", "application/form-data; charset=utf-8");
                    return params;
                }
            };

            queue.add(strRequest);

            return response;

        } catch (Exception e) {
            return null;
        }
    }
}
