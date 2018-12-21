package vibe.com.br.vibe.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRequest {

    private final String ENDPOINT = "http://jobappliances.herokuapp.com/api/v1/jobs";

    public List<Job> get() throws IOException {
        List<Job> jobs = new ArrayList<>();
        Gson gson = new Gson();
        Json json = new Json();

        String jsonRequest = json.getFromUrl(ENDPOINT);
        String index = "jobs";

        JsonObject jObject = (new JsonParser()).parse(jsonRequest).getAsJsonObject();

        JsonArray jArray = jObject.get(index).getAsJsonArray();

        for (JsonElement element : jArray) {
            Job job = gson.fromJson(element,Job.class);
            jobs.add(job);
        }

        return jobs;
    }

    public List<Candidate> candidates(Job job, Context context) throws MalformedURLException {
        List<Candidate> candidates = new ArrayList<>();
        Gson gson = new Gson();
        Json json = new Json();
        String url = "http://jobappliances.herokuapp.com/api/v1/jobs/"+job.getId()+"/candidates";

        Map<String,String> paramns = new HashMap<>();
        paramns.put("Authorization","vibe2018");

        String jsonRequest = json.postFromUrl(url,context,paramns);
        String index = "candidates";

//        JsonParser jParser = new JsonParser();

//        JsonObject jObject = jParser.parse(jsonRequest).getAsJsonObject();

//        JsonArray jArray = jObject.get(index).getAsJsonArray();

        Log.d("Resultado",paramns.toString());

//
//        for (JsonElement element : jArray) {
//            Candidate candidate = gson.fromJson(element, Candidate.class);
//            candidates.add(candidate);
//        }

        return candidates;
    }

}
