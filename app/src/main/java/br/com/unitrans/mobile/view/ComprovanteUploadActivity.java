package br.com.unitrans.mobile.view;
import br.com.unitrans.R;
import br.com.unitrans.mobile.conexao.ConexaoHttpClient;
import br.com.unitrans.mobile.conexao.URL;
import br.com.unitrans.mobile.entity.AndroidMultiPartEntity;
import br.com.unitrans.mobile.model.Comprovante;
import br.com.unitrans.mobile.model.Estudante;
import br.com.unitrans.mobile.model.Usuario;
import br.com.unitrans.mobile.view.MainActivity;
import br.com.unitrans.mobile.entity.AndroidMultiPartEntity.ProgressListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ComprovanteUploadActivity extends Activity  {
	private static final String TAG = ComprovanteActivity.class.getSimpleName();
	private ProgressBar progressBar;
	private String filePath = null;
	private TextView txtPercentage;
	private ImageView imgPreview;
	private Button btnUpload;
	long totalSize = 0;
	Usuario user;
	MesActivity mes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_envia_pgto);
		txtPercentage = (TextView) findViewById(R.id.txtPercentage);
		btnUpload = (Button) findViewById(R.id.btnUpload);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		//Recebendo dados da intent anterior
		Intent i = getIntent();
		//Imagem capturada a itent anterior
		filePath = i.getStringExtra("filePath");
		//Boleano para identificar se é imagem ou video
		boolean isImage = i.getBooleanExtra("isImage", true);
		if (filePath != null) {
			//Mostrando a imagem
			previewMedia(isImage);
		} else {
			Toast.makeText(getApplicationContext(), "Sem imagem!", Toast.LENGTH_LONG).show();
		}

		btnUpload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// upload para o servidor
				new UploadFileToServer().execute();
			}
		});

	}

	//Mostrando Imagem capturada na view
	private void previewMedia(boolean isImage) {
		if (isImage) {
			imgPreview.setVisibility(View.VISIBLE);
			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
			imgPreview.setImageBitmap(bitmap);
		} else {
			imgPreview.setVisibility(View.GONE);
		}
	}

    //Classe responsável por enviar a imagem ao servidor
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			//Setando o progresso da bar para 0
			progressBar.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			progressBar.setVisibility(View.VISIBLE);
			// updating progress bar value
			progressBar.setProgress(progress[0]);
			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL.FILE_UPLOAD_URL);
			try {
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});

				File sourceFile = new File(filePath);
				Usuario user = new Usuario();
				Integer id = user.getId();
				// Adding file data to http body
				entity.addPart("imagem", new FileBody(sourceFile));
				entity.addPart("fk_id_estudante",new StringBody(id.toString()));
				//Descomentar para enviar o mes do comprovante
				entity.addPart("mes",new StringBody(mes.mesCompro[0]));
				entity.addPart("metodo",new StringBody("save"));
				totalSize = entity.getContentLength();
				httppost.setEntity(entity);
				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}
			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			//mostrar a resposta do servidor
			showAlert(result);
			super.onPostExecute(result);
		}

	}


	//Método quando pressionado o botão voltar
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	///Show alert dialog
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Atenção!")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}