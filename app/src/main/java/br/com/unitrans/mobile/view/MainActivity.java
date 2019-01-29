package br.com.unitrans.mobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import br.com.unitrans.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView btnMenu;

        //Seleciona opções
        final String[] option = new String[]{"Perfil", "Comprovante", "Horário", "Ausência"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, VerificaStatusActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (which == 1) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MesActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (which == 2) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, HorarioPontoActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (which == 3) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, AusenciaActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        final AlertDialog dialog = builder.create();
        btnMenu = (ImageView) findViewById(R.id.menu_principal);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

}
