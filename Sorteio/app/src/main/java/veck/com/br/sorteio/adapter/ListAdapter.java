package veck.com.br.sorteio.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import veck.com.br.sorteio.R;
import veck.com.br.sorteio.dao.ItemSorteio;

/**
 * Created by rafaelveck on 24/05/18.
 */

public class ListAdapter extends BaseAdapter {

    private final List<ItemSorteio> lstItensSorteio;
    private final Activity act;

    public ListAdapter(List<ItemSorteio> lstItensSorteio, Activity act){
        this.lstItensSorteio = lstItensSorteio;
        this.act = act;
    }

    @Override
    public int getCount() {
        return lstItensSorteio.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItensSorteio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater()
                .inflate(R.layout.list_item, parent, false);

        TextView nome = (TextView) view.findViewById(R.id.edit_text_nome_item);
        TextView qtd = (TextView) view.findViewById(R.id.edit_text_qtd_sorteado);

        ItemSorteio item = lstItensSorteio.get(position);
        nome.setText(item.getNome());
        qtd.setText(String.valueOf(item.getQtdSorteado()));

        return view;
    }
}
