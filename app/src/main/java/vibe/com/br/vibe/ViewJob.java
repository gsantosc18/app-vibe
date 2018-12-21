package vibe.com.br.vibe;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.util.List;

import vibe.com.br.vibe.model.Candidate;
import vibe.com.br.vibe.model.Job;
import vibe.com.br.vibe.model.JobRequest;

public class ViewJob extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Bundle extras = getIntent().getExtras();

        Job job = new Job();
        job.setId(extras.getString("id"));
        job.setTitulo(extras.getString("titulo"));

        lista = (ListView) findViewById(R.id.listViewCandidateId);

        TextView txtTitulo = (TextView) findViewById(R.id.txtTituloCandidate);

        txtTitulo.setText(job.getTitulo());

        new AsyncCandidate(job, this).execute();
    }

    private class CustomJobAdapter extends BaseAdapter {
        private List<Candidate> candidates;

        public CustomJobAdapter(List<Candidate> candidates)
        {
            this.candidates = candidates;
        }

        @Override
        public int getCount() {
            return this.candidates.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Candidate candidate = candidates.get(position);

            convertView = getLayoutInflater().inflate(R.layout.card_candidate,null);

            TextView nome = convertView.findViewById(R.id.txtNomeCandidate);
            TextView email = convertView.findViewById(R.id.txtEmailCandidate);

            nome.setText(candidate.getNome());
            email.setText("email do candidato: "+candidate.getEmail());

            return convertView;
        }
    }

    private class AsyncCandidate extends AsyncTask<Void, Void, List<Candidate>> {

        private Job job;
        private Context context;

        public AsyncCandidate(Job job, Context context)
        {
            this.job = job;
            this.context = context;
        }

        @Override
        protected List<Candidate> doInBackground(Void... voids) {
            JobRequest jRequest = new JobRequest();
            try {
                return jRequest.candidates(job,context);
            } catch (MalformedURLException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Candidate> candidates) {
//                Log.d("isso", String.valueOf(candidates.size()));
//            lista = (ListView) findViewById(R.id.listViewId);
//
//            CustomJobAdapter customJobAdapter = new CustomJobAdapter(candidates);
//
//            lista.setAdapter(customJobAdapter);
        }
    }
}
