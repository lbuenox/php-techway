package android.larissa.bueno.tcc.model;

public class LoginResponse {

    private boolean sucesso;
    private String mensagem;
    private String token;
    private String nome;
    private int id;

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}