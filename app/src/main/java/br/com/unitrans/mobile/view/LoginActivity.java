package br.com.unitrans.mobile.view;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import br.com.unitrans.R;
import br.com.unitrans.mobile.conexao.URL;
import br.com.unitrans.mobile.conexao.ConexaoHttpClient;
import br.com.unitrans.mobile.model.Usuario;


public class LoginActivity extends Activity {
    EditText etLogin, etSenha;
    Button btAcessar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Corrigi problema de thread na requisição
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        etLogin = (EditText) findViewById(R.id.edt_login);
        etSenha = (EditText) findViewById(R.id.edt_senha);
        btAcessar = (Button) findViewById(R.id.btnLogin);

        btAcessar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String urlPost = ConexaoHttpClient.http+ConexaoHttpClient.ip+URL.LoginController;
                ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
                parametrosPost.add(new BasicNameValuePair("login", etLogin.getText().toString()));
                parametrosPost.add(new BasicNameValuePair("senha", etSenha.getText().toString()));
                parametrosPost.add(new BasicNameValuePair("login-mobile", null));
                String respostaRetornada = null;
                try {
                    respostaRetornada = ConexaoHttpClient.executaHttpPost(urlPost, parametrosPost);
                    String resposta = respostaRetornada.toString();
                    resposta = resposta.replaceAll("\\s+", "");
                    int res = Integer.parseInt(resposta);
                    if (res >= 1) {
                        Usuario user = new Usuario();
                        user.setId(res);
                        user.setLogin(etLogin.getText().toString());
                        user.setSenha(etSenha.getText().toString());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else {
                        mensagemExibir("Login", "Usuario Inválido!");
                    }
                } catch (Exception erro) {
                    Log.i("erro", "erro = " + erro);
                    Toast.makeText(LoginActivity.this, "Erro.: " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void mensagemExibir(String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(LoginActivity.this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.show();
    }

}
