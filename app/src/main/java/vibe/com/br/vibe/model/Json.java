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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Json {

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

    public String getFromUrl(String requestURL, final Map<String, String> params ) throws MalformedURLException
    {
        try{

            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : params.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            //print result
            return response.toString();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String postFromUrl(String requestURL, final Map<String, String> params ) throws MalformedURLException
    {
        try{

            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            for (Map.Entry<String, String> entry : params.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(params.toString());
            writer.flush();
            writer.close();
            os.close();

            int code = connection.getResponseCode();

            if ( code == HttpURLConnection.HTTP_OK ) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }
}
