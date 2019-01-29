package br.com.unitrans.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.unitrans.R;
import br.com.unitrans.mobile.model.Estudante;

/**
 * Created by heloise on 28/09/16.
 */

public class EstudanteAdapter extends ArrayAdapter<Estudante> {
    ArrayList<Estudante> estudanteList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public EstudanteAdapter(Context context, int resource, ArrayList<Estudante> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        estudanteList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvNome = (TextView) v.findViewById(R.id.tvNome);
            holder.tvNasc = (TextView) v.findViewById(R.id.tvNasc);
            holder.tvUni = (TextView) v.findViewById(R.id.tvUni);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.imageview).execute(estudanteList.get(position).getFoto());
        holder.tvNome.setText(estudanteList.get(position).getNome_aluno());
        holder.tvNasc.setText("Nascimento: " + estudanteList.get(position).getData_de_nascimento());
        holder.tvUni.setText("Instituição: " + estudanteList.get(position).getUniversidade());
        //holder.tvCountry.setText("Horário:" + estudanteList.get(position).getHorario_de_ida()+"-"+estudanteList.get(position).getHorario_de_volta());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvNome;
        public TextView tvNasc;
        public TextView tvUni;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}
