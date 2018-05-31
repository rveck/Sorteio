package veck.com.br.sorteio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import android.view.View.OnClickListener;
import android.widget.Toast;

import veck.com.br.sorteio.adapter.ListAdapter;
import veck.com.br.sorteio.dao.ItemSorteio;
import veck.com.br.sorteio.sqllite.BancoController;

public class ItensDigitados extends AppCompatActivity {

    private static ArrayList<ItemSorteio> lstItens = null;
    private static ListAdapter listAdapter = null;
    private static BancoController crud = null;
    private Fragment fragmentSorteio;
    private Fragment fragmentListSorteios;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_itens_digitados);

        lstItens = new ArrayList<ItemSorteio>();

        listAdapter = new ListAdapter(lstItens, this);

        crud = new BancoController(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tab = (TabLayout) findViewById(R.id.tab_layout);
        tab.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itens_digitados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, Sobre.class);
            startActivityForResult(intent, 2);

            return true;
        } else if (id == R.id.action_save) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Descrição do sorteio");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    if (null != m_Text && !"".equals(m_Text)) {
                        crud.insertSorteio(m_Text, lstItens);

                        Snackbar snackbar = Snackbar.make(mViewPager, "Sorteio salvo com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                    } else {
                        Snackbar snackbar = Snackbar.make(mViewPager, "Sorteio não foi salvo", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        } else if (id == R.id.action_open) {

            Intent intent = new Intent(this, SorteiosSalvos.class);
            startActivityForResult(intent, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        ((TextView) fragmentSorteio.getView().findViewById(R.id.edit_text)).setText("");

        if (resultCode == 2) {
            int idsorteio = data.getIntExtra("IDSORTEIO",0);

            ArrayList<ItemSorteio> lsBanco = crud.consultaItens(idsorteio);
            Iterator it = lsBanco.iterator();
            lstItens.clear();

            while(it.hasNext()){
                lstItens.add((ItemSorteio) it.next());
            }

            listAdapter.notifyDataSetChanged();
            String qtd = String.valueOf(lstItens.size());
            ((TextView)fragmentSorteio.getView().findViewById(R.id.txt_contador)).setText(qtd);

            Snackbar.make(fragmentSorteio.getView(), R.string.texto_sorteio_carregado, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            super.onCreateView(inflater, container, savedInstanceState);

            int mFragmentIndex = getArguments().getInt(ARG_SECTION_NUMBER);

            View viewSorteio = inflater.inflate(R.layout.fragment_itens_digitados, container, false);
            View viewLista = inflater.inflate(R.layout.fragment_lista_itens, container, false);

            final TextView txtSorteado = (TextView) viewSorteio.findViewById(R.id.txt_sorteado);
            final TextView txtSorteadoLabel = (TextView) viewSorteio.findViewById(R.id.txt_sorteado_label);
            final TextView txtContagem = (TextView) viewSorteio.findViewById(R.id.txt_contador);
            final EditText editText = (EditText) viewSorteio.findViewById(R.id.edit_text);

            final Button btnAdicionar = (Button) viewSorteio.findViewById(R.id.btn_adicionar);
            final Button btnLimpar = (Button) viewSorteio.findViewById(R.id.btn_limpar);
            final Button btnSortear = (Button) viewSorteio.findViewById(R.id.btn_sortear);

            final ListView lstView = (ListView) viewLista.findViewById(R.id.lstView);
            lstView.setEmptyView(viewLista.findViewById(R.id.text_view_lista_vazia));
            lstView.setAdapter(listAdapter);

            editText.setImeActionLabel(getString(R.string.btn_adicionar), EditorInfo.IME_ACTION_GO);

            final InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            btnSortear.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View rootView) {

                    if (lstItens.size() > 0) {
                        Random aleatorio = new Random();
                        int numero = aleatorio.nextInt(lstItens.size());

                        ItemSorteio sorteado = lstItens.get(numero);
                        sorteado.setQtdSorteado(sorteado.getQtdSorteado() + 1);
                        listAdapter.notifyDataSetChanged();

                        txtSorteado.setText(sorteado.getNome());
                        txtSorteado.setVisibility(View.VISIBLE);
                        txtSorteadoLabel.setText(R.string.texto_sorteado);
                    } else {
                        Snackbar snackbar = Snackbar.make(rootView, R.string.nenhum_item_lista, Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(Color.BLACK);
                        snackbar.show();
                    }
                }
            });

            btnAdicionar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View rootView) {
                    btnAdicionar.setEnabled(false);

                    ItemSorteio item = new ItemSorteio();
                    item.setId(lstItens.size() + 1);
                    item.setNome(editText.getText().toString());
                    item.setQtdSorteado(0);

                    lstItens.add(item);

                    listAdapter.notifyDataSetChanged();

                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                    Snackbar.make(rootView, R.string.texto_adicionado_sucesso, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    txtContagem.setText(String.valueOf(lstItens.size()));

                    editText.setText("");
                    btnAdicionar.setEnabled(true);
                }
            });

            btnLimpar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View rootView) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle(R.string.titulo_confirmacao)
                            .setMessage(R.string.texto_pergunta_reinicializacao)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    lstItens.clear();

                                    listAdapter.notifyDataSetChanged();
                                    txtSorteado.setText(R.string.texto_nenhum_sorteado);
                                    txtContagem.setText(String.valueOf(lstItens.size()));
                                    txtSorteado.setVisibility(View.INVISIBLE);
                                    txtSorteadoLabel.setText(R.string.texto_instrucao);

                                    Toast toast = Toast.makeText(getContext(), R.string.operacao_realizada, Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            if (mFragmentIndex == 1) {
                return viewSorteio;
            } else {
                return viewLista;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sorteio";
                case 1:
                    return "Salvos";
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    fragmentSorteio = createdFragment;
                    break;
                case 1:
                    fragmentListSorteios = createdFragment;
                    break;
            }
            return createdFragment;
        }
    }
}