package veck.com.br.sorteio;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by rafaelveck on 28/05/18.
 */

public class ListSorteioHolder extends RecyclerView.ViewHolder {

    private TextView descricao;
    private TextView data;

    public ListSorteioHolder(View itemView) {
        super(itemView);

        descricao = (TextView) itemView.findViewById(R.id.txt_nome_sorteio);
        data = (TextView) itemView.findViewById(R.id.txt_data_sorteio);

    }

    public TextView getDescricao() {
        return descricao;
    }

    public void setDescricao(TextView descricao) {
        this.descricao = descricao;
    }

    public TextView getData() {
        return data;
    }

    public void setData(TextView data) {
        this.data = data;
    }
}
