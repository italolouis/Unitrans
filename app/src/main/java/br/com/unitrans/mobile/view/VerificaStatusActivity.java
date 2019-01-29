package br.com.unitrans.mobile.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import br.com.unitrans.R;
import br.com.unitrans.mobile.conexao.URL;
import br.com.unitrans.mobile.adapter.EstudanteAdapter;
import br.com.unitrans.mobile.conexao.ConexaoHttpClient;
import br.com.unitrans.mobile.model.Estudante;
import br.com.unitrans.mobile.model.Usuario;


public class VerificaStatusActivity extends Activity {
    ArrayList<Estudante> estudanteList = new ArrayList<Estudante>();
    EstudanteAdapter adapter;
    Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        //Corrigi problema de thread na requisição
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btnStatus = (Button)findViewById(R.id.btnSitAcesso);
        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new EstudanteAdapter(getApplicationContext(), R.layout.perfil_list, estudanteList);
        listview.setAdapter(adapter);

        //recupera id do estudante
        Integer id = user.getId();
        //url de definição
        String urlGet = ConexaoHttpClient.http+ConexaoHttpClient.ip+ URL.VerificaEstudante+id.toString();
        String respostaRetornada = null;
        try {
            respostaRetornada = ConexaoHttpClient.executaHttpGet(urlGet);
            String response = respostaRetornada.toString();
            JSONObject object = new JSONObject(response);
            Estudante estudante = new Estudante();
            estudante.setId(0);
            estudante.setNome_aluno(object.getString("nome_aluno"));
            estudante.setData_de_nascimento(object.getString("data_de_nascimento"));
            estudante.setUniversidade(object.getString("universidade"));
            estudante.setHorario_de_ida(object.getString("horario_de_ida"));
            estudante.setHorario_de_volta(object.getString("horario_de_volta"));
            //tratamento da url de foto
            String foto1 = (object.getString("foto"));
            foto1 = "http://192.168.0.3/"+foto1.replaceAll("\\+", "");
            estudante.setFoto(foto1);
            estudante.setStatus(Integer.parseInt(object.getString("status")));
            //adiciona estudante no array
            estudanteList.add(estudante);
            //validação do status do estudante
            if (estudante.getStatus()==1){
                btnStatus.setBackgroundColor(Color.parseColor("#32CD32"));
                btnStatus.setText("Acesso Liberado");
            }else {
                btnStatus.setBackgroundColor(Color.parseColor("#FF0000"));
                btnStatus.setText("Acesso Bloqueado");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método quando pressionado o botão voltar
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
