package veck.com.br.sorteio.dao;

/**
 * Created by rafaelveck on 24/05/18.
 */

public class ItemSorteio {

    private int id;
    private String nome;
    private int qtdSorteado;
    private int idSorteio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdSorteado() {
        return qtdSorteado;
    }

    public void setQtdSorteado(int qtdSorteado) {
        this.qtdSorteado = qtdSorteado;
    }

    public int getIdSorteio() {
        return idSorteio;
    }

    public void setIdSorteio(int idSorteio) {
        this.idSorteio = idSorteio;
    }
}
