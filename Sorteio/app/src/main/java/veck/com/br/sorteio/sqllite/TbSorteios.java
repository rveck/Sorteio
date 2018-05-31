package veck.com.br.sorteio.sqllite;

import java.util.Date;

/**
 * Created by rafaelveck on 25/05/18.
 */

public class TbSorteios {
    public static final String TABLE_NAME = "TbSorteios";
    public static final String ID = "id";
    public static final String DESCRICAO = "descricao";
    public static final String DATA_UPDATE = "data";

    private int id;
    private String descricao;
    private Date data_update;

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

    public Date getData_update() {
        return data_update;
    }

    public void setData_update(Date data_update) {
        this.data_update = data_update;
    }
}
