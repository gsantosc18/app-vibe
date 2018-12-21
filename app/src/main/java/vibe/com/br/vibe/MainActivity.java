package vibe.com.br.vibe;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vibe.com.br.vibe.model.Candidate;
import vibe.com.br.vibe.model.Job;
import vibe.com.br.vibe.model.JobRequest;
import vibe.com.br.vibe.model.Json;

public class MainActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Assyc().execute();
    }

    private AdapterView.OnItemClickListener onClickItem(final List<Job> jobs)
    {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job job = (Job) jobs.get(position);

//                Intent intent = new Intent(MainActivity.this);
                Log.d("Info","Foi clicado em algum anuncio!");

                Intent intent = new Intent(MainActivity.this, ViewJob.class);
                intent.putExtra("id",job.getId());
                intent.putExtra("titulo",job.getTitulo());

                startActivity(intent);

//                new AssyncOnItemClick(job,MainActivity.this).execute();
            }
        };
    }

    private class CustomJobAdapter extends BaseAdapter {
        private List<Job> jobs;

        public CustomJobAdapter(List<Job> jobs)
        {
            this.jobs = jobs;
        }

        @Override
        public int getCount() {
            return this.jobs.size();
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
            Job job = jobs.get(position);

            convertView = getLayoutInflater().inflate(R.layout.card_job,null);

            TextView titulo = convertView.findViewById(R.id.txtTitulo);
            TextView escolaridade = convertView.findViewById(R.id.txtEscolaridade);
            TextView tipoContrato = convertView.findViewById(R.id.txtTipoContrato);

            titulo.setText(job.getTitulo());
            escolaridade.setText("Escolaridade: "+job.getNivel_de_escolaridade());
            tipoContrato.setText("Contrato: "+job.getTipo_de_contrato());

            return convertView;
        }
    }

    private class Assyc extends AsyncTask<Void, Void,List<Job>> {

        @Override
        protected List<Job> doInBackground(Void... voids) {
            try {
                return (new JobRequest()).get();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Job> jobs) {

            lista = (ListView) findViewById(R.id.listViewId);

            CustomJobAdapter customJobAdapter = new CustomJobAdapter(jobs);

            lista.setAdapter(customJobAdapter);
            lista.setOnItemClickListener(onClickItem(jobs));
        }
    }

    private class AssyncOnItemClick extends AsyncTask<Void, Void, Boolean>{

        private Job job;
        private Context context;

        public AssyncOnItemClick(Job job, Context context)
        {
            this.job = job;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            JobRequest jobRequest = new JobRequest();

            try {
                List<Candidate> candidates = jobRequest.candidates(job,MainActivity.this);
            } catch (MalformedURLException e) {
                return false;
            }

            return true;
        }
    }
}
