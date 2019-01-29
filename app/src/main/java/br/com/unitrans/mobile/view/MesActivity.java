package br.com.unitrans.mobile.view;
import br.com.unitrans.R;
import br.com.unitrans.mobile.model.Usuario;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MesActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerMes;
    private Button btnEnviarMes;
    private TextView nomeUsuario;
    public static String[] mesCompro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes);
        Usuario user = new Usuario();
        spinnerMes = (Spinner)findViewById(R.id.spinnerMes);
        btnEnviarMes = (Button)findViewById(R.id.btnNext);
        nomeUsuario = (TextView)findViewById(R.id.txtNameUser);
        nomeUsuario.setText(user.getLogin());

        //SPINNER HORARIO DE PARTIDA
        spinnerMes.setOnItemSelectedListener(MesActivity.this);
        List<String> mesComprovante = new ArrayList<String>();
        mesComprovante.add("Janeiro");
        mesComprovante.add("Fevereiro");
        mesComprovante.add("Março");
        mesComprovante.add("Abril");
        mesComprovante.add("Maio");
        mesComprovante.add("Junho");
        mesComprovante.add("Julho");
        mesComprovante.add("Agosto");
        mesComprovante.add("Setembro");
        mesComprovante.add("Outubro");
        mesComprovante.add("Novembro");
        mesComprovante.add("Dezembro");
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mesComprovante);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(dataAdapter4);
        final String[] mesComp = new String[1];
        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mesComp[0] = spinnerMes.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        mesCompro = mesComp;
        btnEnviarMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MesActivity.this, ComprovanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
