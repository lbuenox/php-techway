package android.larissa.bueno.tcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.larissa.bueno.tcc.api.ApiService;
import android.larissa.bueno.tcc.api.RetrofitCliente;
import android.larissa.bueno.tcc.model.LoginRequest;
import android.larissa.bueno.tcc.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> fazerLogin());
    }

    private void fazerLogin() {

        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.setError("Digite seu e-mail");
            return;
        }

        if (senha.isEmpty()) {
            edtSenha.setError("Digite sua senha");
            return;
        }

        LoginRequest request =
                new LoginRequest(email, senha);

        ApiService api = RetrofitCliente
                .getClient()
                .create(ApiService.class);

        Call<LoginResponse> chamada =
                api.login(request);

        chamada.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(
                    Call<LoginResponse> call,
                    Response<LoginResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    LoginResponse resultado =
                            response.body();

                    if (resultado.isSucesso()) {

                        Toast.makeText(
                                LoginActivity.this,
                                "Login realizado!",
                                Toast.LENGTH_SHORT
                        ).show();

                        abrirPrincipal();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                resultado.getMensagem(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                } else {

                    Toast.makeText(
                            LoginActivity.this,
                            "Erro no servidor",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<LoginResponse> call,
                    Throwable t) {

                Toast.makeText(
                        LoginActivity.this,
                        "Erro ao conectar com a API",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void abrirPrincipal() {

        Intent intent = new Intent(
                LoginActivity.this,
                MainActivity.class
        );

        startActivity(intent);
        finish();
    }
}