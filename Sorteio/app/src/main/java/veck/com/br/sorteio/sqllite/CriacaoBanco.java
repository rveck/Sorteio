package veck.com.br.sorteio.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rafaelveck on 25/05/18.
 */

public class CriacaoBanco extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "banco.db";
    private static final int VERSAO = 3;

    private static final String CREATE_TB_SORTEIOS =
            "CREATE TABLE " + TbSorteios.TABLE_NAME + " ("
            + TbSorteios.ID + " integer primary key autoincrement,"
            + TbSorteios.DESCRICAO + " text,"
            + TbSorteios.DATA_UPDATE + " date"
            +")";

    private static final String CREATE_TB_ITENS_SORTEADOS =
            "CREATE TABLE " + TbItensSorteados.TABLE_NAME + " ("
                    + TbItensSorteados.ID + " integer primary key autoincrement,"
                    + TbItensSorteados.DESCRICAO + " text,"
                    + TbItensSorteados.QTD_SORTEADO + " integer,"
                    + TbItensSorteados.ID_SORTEIO + " integer"
                    +")";

    private static final String DROP_TB_SORTEIOS =
            "DROP TABLE " + TbSorteios.TABLE_NAME;

    private static final String DROP_TB_ITENS_SORTEADOS =
            "DROP TABLE " +  TbItensSorteados.TABLE_NAME;

    public CriacaoBanco(Context context) {
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_SORTEIOS);
        db.execSQL(CREATE_TB_ITENS_SORTEADOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TB_SORTEIOS);
        db.execSQL(DROP_TB_ITENS_SORTEADOS);
        onCreate(db);

    }
}
