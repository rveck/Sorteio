package veck.com.br.sorteio.sqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import veck.com.br.sorteio.ItensDigitados;
import veck.com.br.sorteio.dao.ItemSorteio;

/**
 * Created by rafaelveck on 25/05/18.
 */

public class BancoController {

    private CriacaoBanco criacaoBanco;
    private SQLiteDatabase db;

    public final static String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public BancoController(Context context){
        criacaoBanco = new CriacaoBanco(context);
    }

    public void insertSorteio(String descricao, ArrayList<ItemSorteio> itens){
        ContentValues valores;
        long idSorteio = -1;

        try {
            db = criacaoBanco.getWritableDatabase();

            db.beginTransaction();

            valores = new ContentValues();
            valores.put(TbSorteios.DESCRICAO, descricao);

            DateFormat dateFormat = new SimpleDateFormat(this.DATE_FORMAT);
            valores.put(TbSorteios.DATA_UPDATE, dateFormat.format(new Date()));

            idSorteio = db.insert(TbSorteios.TABLE_NAME, null, valores);

            Iterator it = itens.iterator();

            while(it.hasNext()){
                ItemSorteio item = (ItemSorteio)it.next();
                valores = new ContentValues();
                valores.put(TbItensSorteados.DESCRICAO, item.getNome());
                valores.put(TbItensSorteados.QTD_SORTEADO, item.getQtdSorteado());
                valores.put(TbItensSorteados.ID_SORTEIO, idSorteio);


                long idItem = db.insert(TbItensSorteados.TABLE_NAME, null, valores);

            }

            db.setTransactionSuccessful();

        } catch(Exception e) {
            //TODO tratar erro
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<TbSorteios> consultaSorteios(){

        ArrayList<TbSorteios> lstSorteios = new ArrayList<TbSorteios>();

        Cursor cursor;
        db = criacaoBanco.getReadableDatabase();
        String[] campos = {TbSorteios.ID, TbSorteios.DESCRICAO, TbSorteios.DATA_UPDATE};
        cursor = db.query(TbSorteios.TABLE_NAME, campos, null, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();

            while(cursor.moveToNext())
            {
                TbSorteios sorteio = new TbSorteios();
                sorteio.setId(cursor.getInt(cursor.getColumnIndex(TbSorteios.ID)));
                sorteio.setDescricao(cursor.getString(cursor.getColumnIndex(TbSorteios.DESCRICAO)));

                SimpleDateFormat format = new SimpleDateFormat(this.DATE_FORMAT);
                try {
                    sorteio.setData_update(format.parse(cursor.getString(cursor.getColumnIndex(TbSorteios.DATA_UPDATE))));
                }catch (Exception e){
                    //TODO tratar caso de erro
                }

                lstSorteios.add(sorteio);
            }
        }

        db.close();

        return lstSorteios;
    }

    public ArrayList<ItemSorteio> consultaItens(int idSorteio){
        ArrayList<ItemSorteio> lista = new ArrayList<ItemSorteio>();

        String query =
                "SELECT "
                        + TbItensSorteados.ID + ", "
                        + TbItensSorteados.DESCRICAO + ", "
                        + TbItensSorteados.QTD_SORTEADO + ", "
                        + TbItensSorteados.ID_SORTEIO
                        + " FROM "
                        + TbItensSorteados.TABLE_NAME
                + " WHERE " + TbItensSorteados.ID_SORTEIO + " = " + String.valueOf(idSorteio);

        Cursor cursor;
        db = criacaoBanco.getReadableDatabase();
        cursor = db.rawQuery(query, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                ItemSorteio item = new ItemSorteio();
                item.setId(cursor.getInt(cursor.getColumnIndex(TbItensSorteados.ID)));
                item.setNome(cursor.getString(cursor.getColumnIndex(TbItensSorteados.DESCRICAO)));
                item.setQtdSorteado(cursor.getInt(cursor.getColumnIndex(TbItensSorteados.QTD_SORTEADO)));
                item.setIdSorteio(cursor.getInt(cursor.getColumnIndex(TbItensSorteados.ID_SORTEIO)));
                lista.add(item);
            }
        }
        return lista;
    }

}
