package android.larissa.bueno.tcc;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.larissa.bueno.tcc.api.ApiService;
import android.larissa.bueno.tcc.api.RetrofitClient;
import android.larissa.bueno.tcc.model.LoginRequest;
import android.larissa.bueno.tcc.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;
    private TextView txtCliqueAqui;

    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        progressBar = findViewById(R.id.progressBar);
        txtCliqueAqui = findViewById(R.id.txtCliqueAqui);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnEntrar.setOnClickListener(v -> tentarLogin());

        txtCliqueAqui.setOnClickListener(v ->
                Toast.makeText(this, "Ir para tela de cadastro", Toast.LENGTH_SHORT).show()
        );
    }

    private void tentarLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (!validarCampos(email, senha)) {
            return;
        }

        setLoading(true);

        LoginRequest request = new LoginRequest(email, senha);

        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    getSharedPreferences("TechWayPrefs", MODE_PRIVATE)
                            .edit()
                            .putString("token", loginResponse.getToken())
                            .apply();

                    Toast.makeText(LoginActivity.this,
                            "Bem-vindo(a), " + loginResponse.getNome() + "!",
                            Toast.LENGTH_SHORT).show();

                    // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    // finish();

                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao fazer login (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarCampos(String email, String senha) {
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Informe o email");
            edtEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email inválido");
            edtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(senha)) {
            edtSenha.setError("Informe a senha");
            edtSenha.requestFocus();
            return false;
        }
        if (senha.length() < 6) {
            edtSenha.setError("A senha deve ter pelo menos 6 caracteres");
            edtSenha.requestFocus();
            return false;
        }
        return true;
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnEntrar.setEnabled(!loading);
    }
}