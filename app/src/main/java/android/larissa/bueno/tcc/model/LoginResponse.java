package android.larissa.bueno.tcc.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("nome")
    private String nome;

    @SerializedName("id")
    private String id;

    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }
}
