package veck.com.br.sorteio.sqllite;

/**
 * Created by rafaelveck on 25/05/18.
 */

public class TbItensSorteados {
    public static final String TABLE_NAME = "TbItensSorteados";
    public static final String ID = "id";
    public static final String DESCRICAO = "descricao";
    public static final String QTD_SORTEADO = "qtd_sorteado";
    public static final String ID_SORTEIO = "idSorteio";

    private int id;
    private String descricao;
    private int qtdSorteado;
    private int idSorteio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
