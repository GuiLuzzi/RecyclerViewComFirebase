package guilherme.luzzi.projetofinal.model;

public class Cliente {
    private String key;
    private String nome;
    private String sexo;
    private String cidade;

    public Cliente() {
    }

    public Cliente(String key, String nome, String sexo, String cidade) {
        this.key = key;
        this.nome = nome;
        this.sexo = sexo;
        this.cidade = cidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return  "\nkey = " + key +
                "\nnome = " + nome +
                "\nsexo = " + sexo;
    }
}//fecha classe

