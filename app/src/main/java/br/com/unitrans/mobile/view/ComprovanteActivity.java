package br.com.unitrans.mobile.view;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.unitrans.R;
import br.com.unitrans.mobile.conexao.URL;
import br.com.unitrans.mobile.model.Usuario;

public class ComprovanteActivity extends Activity implements AdapterView.OnItemSelectedListener {
	
	//LogCat
	private static final String TAG = ComprovanteActivity.class.getSimpleName();
    //Codigos de requisição da camera
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private Button btnCapturePicture;
    private Spinner spinnerMes;

    static Usuario user = new Usuario();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envia_pgto);
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        //Verificando compatibilidade da camera
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Dispositivo não suporta câmera!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
 
    //Metodo de verificar compatibilidade da câmera
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
 
    //Metodo de capturar imagem
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //salva url arquivo se orientação da tela for nula
        outState.putParcelable("file_uri", fileUri);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //obtem o file da foto
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    //Metodo responsavel por receber o "resultado" apos fechar a camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Captura cancelada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Falha na captura da imagem!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void launchUploadActivity(boolean isImage){
    	Intent i = new Intent(ComprovanteActivity.this, ComprovanteUploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
    }

    //Criando file uri da umagem
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
 

    //Retorna imagem /video
    private static File getOutputMediaFile(int type) {
        // SDcard externo
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                URL.IMAGE_DIRECTORY_NAME);
        // Cria o diretorio
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //Criar o arquivo
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "PGTO_" +timeStamp + "_"+ user.getId()+ user.getLogin()+".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    //Método quando pressionado o botão voltar
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}