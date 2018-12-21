package vibe.com.br.vibe;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        Job job = new Job();
        job.setId(extras.getString("id"));
        job.setTitulo(extras.getString("titulo"));
        job.setNivel_de_escolaridade(extras.getString("escolaridade"));
        job.setTipo_de_contrato(extras.getString("contrato"));
        job.setArea_de_atuacao(extras.getString("area"));

        TextView txtTitulo = (TextView) findViewById(R.id.txtTituloVaga);
        TextView txtEscolaridade = (TextView) findViewById(R.id.txtEscolaridadeVaga);
        TextView txtContrato = (TextView) findViewById(R.id.txtContratoVaga);
        TextView txtArea = (TextView) findViewById(R.id.txtAreaVaga);

        txtTitulo.setText(job.getTitulo());
        txtEscolaridade.setText("Escolaridade: "+job.getNivel_de_escolaridade());
        txtContrato.setText("Contrato: "+job.getTipo_de_contrato());
        txtArea.setText("Área de Atuação: "+job.getArea_de_atuacao());

        getSupportActionBar().setTitle(job.getTitulo());

        new AsyncCandidate(job).execute();
    }

    private AdapterView.OnItemClickListener onClickItem(final List<Candidate> candidates)
    {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Candidate candidate = (Candidate) candidates.get(position);

                Log.d("Info","Foi clicado em algum anuncio!");

                Intent intent = new Intent(ViewJob.this, MoreCandidate.class);
                intent.putExtra("id",candidate.getId());

                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ViewJob.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        public AsyncCandidate(Job job)
        {
            this.job = job;
        }

        @Override
        protected List<Candidate> doInBackground(Void... voids) {
            JobRequest jRequest = new JobRequest();
            try {
                return jRequest.candidates(job);
            } catch (MalformedURLException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Candidate> candidates) {
            lista = (ListView) findViewById(R.id.listViewCandidateId);

            CustomJobAdapter customJobAdapter = new CustomJobAdapter(candidates);

            lista.setAdapter(customJobAdapter);
            lista.setOnItemClickListener(onClickItem(candidates));
        }
    }
}
