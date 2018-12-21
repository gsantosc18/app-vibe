package vibe.com.br.vibe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import vibe.com.br.vibe.model.Candidate;
import vibe.com.br.vibe.model.Job;
import vibe.com.br.vibe.model.JobRequest;

public class MoreCandidate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_candidate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = (Bundle) getIntent().getExtras();


        new AsyncCandidate(extras.getString("id")).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MoreCandidate.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class AsyncCandidate extends AsyncTask<Void, Void, Candidate> {

        private String id;

        public AsyncCandidate(String id)
        {
            this.id = id;
        }

        @Override
        protected Candidate doInBackground(Void... voids) {
            JobRequest jRequest = new JobRequest();
            try {
                return jRequest.findCandidate(id);
            }
            catch (MalformedURLException e) {}
            catch (IOException e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Candidate candidate) {
            getSupportActionBar().setTitle(candidate.getNome());

            TextView nome = (TextView) findViewById(R.id.txtNomeCandidato);
            TextView email = (TextView) findViewById(R.id.txtEmailCandidato);
            TextView experiencia = (TextView) findViewById(R.id.txtExperienciaCadidato);
            TextView resumo = (TextView) findViewById(R.id.txtResumoCandidato);

            nome.setText(candidate.getNome());
            email.setText(candidate.getEmail());
            experiencia.setText(candidate.getExperiencia());
            resumo.setText(candidate.getResumo());
        }
    }
}
