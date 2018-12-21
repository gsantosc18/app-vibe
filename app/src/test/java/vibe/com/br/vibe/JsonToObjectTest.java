package vibe.com.br.vibe;

import junit.framework.TestCase;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import vibe.com.br.vibe.model.Job;
import vibe.com.br.vibe.model.JobRequest;

public class JsonToObjectTest extends TestCase {

    public void testUrl() throws IOException {
        List<Job> jobs = (new JobRequest()).get();
        System.out.println(jobs.size());
        for (Job job : jobs) {
            System.out.println(job.getArea_de_atuacao());
        }
        assertTrue(true);
    }

}
