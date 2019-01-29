package br.com.unitrans.mobile.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import br.com.unitrans.R;
import br.com.unitrans.mobile.conexao.ConexaoHttpClient;
import br.com.unitrans.mobile.conexao.URL;
import br.com.unitrans.mobile.model.Ponto;
import br.com.unitrans.mobile.model.Usuario;

public class HorarioPontoActivity extends Activity implements OnItemSelectedListener, View.OnClickListener {
    //UI References
    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_ponto);
        //Corrigi problema de thread na requisição
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnConfirmar = (Button) findViewById(R.id.botaoConfirmarHorario);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        List<Ponto> listPontos = new ArrayList<Ponto>();
        String urlGet = ConexaoHttpClient.http + ConexaoHttpClient.ip + URL.RetornaPonto;
        String respostaRetornada = null;
        try {
            respostaRetornada = ConexaoHttpClient.executaHttpGet(urlGet);
            String response = respostaRetornada.toString();
            JSONArray jsonarray = new JSONArray(response);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Ponto ponto = new Ponto();
                ponto.setId(Integer.parseInt(jsonobject.getString("id")));
                ponto.setEndereco(jsonobject.getString("endereco"));
                ponto.setBairro(jsonobject.getString("bairro"));
                ponto.setCidade(jsonobject.getString("cidade"));
                listPontos.add(ponto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SPINNER LOCAL DE IDA
        final Spinner spinnerPartida = (Spinner) findViewById(R.id.spinnerPartida);
        ArrayAdapter<Ponto> dataAdapter = new ArrayAdapter<Ponto>(this, android.R.layout.simple_spinner_item, listPontos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPartida.setAdapter(dataAdapter);
        final String[] localPartida2 = new String[1];
        spinnerPartida.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String localPartida = spinnerPartida.getSelectedItem().toString();
                localPartida = String.valueOf(localPartida.charAt(0));
                localPartida2[0] = localPartida;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //SPINNER LOCAL DE VOLTA
        final Spinner spinnerChegada = (Spinner) findViewById(R.id.spinnerChegada);
        spinnerChegada.setOnItemSelectedListener(this);
        ArrayAdapter<Ponto> dataAdapter3 = new ArrayAdapter<Ponto>(this, android.R.layout.simple_spinner_item, listPontos);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChegada.setAdapter(dataAdapter3);
        final String[] localVolta2 = new String[1];
        spinnerChegada.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String localVolta = spinnerChegada.getSelectedItem().toString();
                localVolta = String.valueOf(localVolta.charAt(0));
                localVolta2[0] = localVolta;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //SPINNER HORARIO DE PARTIDA
        final Spinner spinnerHoraP = (Spinner) findViewById(R.id.spinnerHoraP);
        spinnerHoraP.setOnItemSelectedListener(this);
        List<String> horaP = new ArrayList<String>();
        horaP.add("07:00");
        horaP.add("08:00");
        horaP.add("09:00");
        horaP.add("17:00");
        horaP.add("18:00");
        horaP.add("19:00");
        horaP.add("23:00");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horaP);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHoraP.setAdapter(dataAdapter2);
        final String[] horaIda = new String[1];
        spinnerHoraP.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
               horaIda[0] = spinnerHoraP.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //SPINNER HORARIO DE PARTIDA
        final Spinner spinnerHoraC = (Spinner) findViewById(R.id.spinnerHoraC);
        spinnerHoraP.setOnItemSelectedListener(this);
        List<String> horaC = new ArrayList<String>();
        horaC.add("07:00");
        horaC.add("08:00");
        horaC.add("09:00");
        horaC.add("17:00");
        horaC.add("18:00");
        horaC.add("19:00");
        horaC.add("23:00");
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horaC);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHoraC.setAdapter(dataAdapter4);
        final String[] horaVolta= new String[1];
        spinnerHoraC.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                horaVolta[0] = spinnerHoraC.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPost = ConexaoHttpClient.http + ConexaoHttpClient.ip + URL.AlteraPonto;
                ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
                Usuario user = new Usuario();
                Integer id = user.getId();
                parametrosPost.add(new BasicNameValuePair("fk_id_estudante", id.toString()));
                parametrosPost.add(new BasicNameValuePair("fk_id_ponto_embarque", localPartida2[0]));
                parametrosPost.add(new BasicNameValuePair("horarioIda", horaIda[0]));
                parametrosPost.add(new BasicNameValuePair("fk_id_ponto_desembarque", localVolta2[0]));
                parametrosPost.add(new BasicNameValuePair("horarioVolta", horaVolta[0]));

                String respostaRetornada = null;
                try {
                    respostaRetornada = ConexaoHttpClient.executaHttpPost(urlPost, parametrosPost);
                    String resposta = respostaRetornada.toString();
                    resposta = resposta.replaceAll("\\n+", "");
                    if (resposta.equals("true")) {
                        Toast.makeText(HorarioPontoActivity.this, "Alteração de Horário Enviada!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(HorarioPontoActivity.this, "Erro!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception erro) {
                    Toast.makeText(HorarioPontoActivity.this, "Erro.: " + erro, Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    //Método quando pressionado o botão voltar
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_diahorario);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }
}
