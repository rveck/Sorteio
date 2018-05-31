package veck.com.br.sorteio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import veck.com.br.sorteio.ListSorteioHolder;
import veck.com.br.sorteio.R;
import veck.com.br.sorteio.sqllite.BancoController;
import veck.com.br.sorteio.sqllite.TbSorteios;

/**
 * Created by rafaelveck on 28/05/18.
 */

public class ListSorteiosAdapter extends RecyclerView.Adapter {

    private List<TbSorteios> list;
    private Activity act;

    public ListSorteiosAdapter(Activity act, List<TbSorteios> list){
        this.act = act;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.sorteios_salvos, parent, false);

        ListSorteioHolder holder = new ListSorteioHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ListSorteioHolder) holder).getDescricao().setText(list.get(position).getDescricao());

        SimpleDateFormat sdf = new SimpleDateFormat(BancoController.DATE_FORMAT);
        String data = sdf.format(list.get(position).getData_update());

        ((ListSorteioHolder) holder).getData().setText(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
