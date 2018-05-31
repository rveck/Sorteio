package veck.com.br.sorteio;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import veck.com.br.sorteio.adapter.ListSorteiosAdapter;
import veck.com.br.sorteio.adapter.RecyclerItemClickListener;
import veck.com.br.sorteio.sqllite.BancoController;
import veck.com.br.sorteio.sqllite.TbSorteios;

public class SorteiosSalvos extends AppCompatActivity {

    private ArrayList<TbSorteios> lstSorteios = new ArrayList<>();

    private static ListSorteiosAdapter listSorteiosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteios_salvos);
        setTitle("Sorteios Salvos");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        BancoController crud = new BancoController(getBaseContext());

        lstSorteios = crud.consultaSorteios();

        listSorteiosAdapter = new ListSorteiosAdapter(this, lstSorteios);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_sorteios_salvos);

        recyclerView.setAdapter(listSorteiosAdapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getBaseContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    Intent intent = getIntent();
                    intent.putExtra("IDSORTEIO",lstSorteios.get(position).getId());
                    setResult(2,intent);
                    finish();
                }

                @Override public void onLongItemClick(View view, int position) {
                    //LONG CLICK
                }
            })
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
