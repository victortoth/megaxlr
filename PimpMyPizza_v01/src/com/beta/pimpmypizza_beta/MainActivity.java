package com.beta.pimpmypizza_beta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import lugares.Lugares;
import pedidos.ConfigurarPedido;
import pedidos.CustomSabores;
import pedidos.PimpController;
import pedidos.Carrinho;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import utils.Helper;
import utils.JSonRequest;
import Model.BebidaModel;
import Model.CarrinhoModel;
import Model.IngredienteModel;
import Model.PizzaModel;
import Model.SaborModel;
import Model.SobremesaModel;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	static int NUM_PAGES = 0;// Numero de Paginas
	MyAdapter mAdapter; // Adapter, funciona como Fragment Manager
	ViewPager mPager; // Fragment

	static String unidadeEntrega = "";
	static String nomeTab = "";
	static boolean isConfig = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		if (!getIntent().getBooleanExtra("sucess", true)) {
			String json = SharedPreferencesController.load(Keys.Cardapio,
					sharedPreferences);
			Helper.getBebidas(json);
			Helper.getCategorias(json);
			Helper.getIngredientes(json);
			Helper.getSabores(json);
		}

		NUM_PAGES = PimpController.categorias.size();

		final ActionBar actionBar = getActionBar();

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// Selecionar a tab correspondente com a mudan�a de pagina, por
				// swype
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		mPager.setAdapter(mAdapter);

		// Modo de navegacao por tabs, na actionBar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		final ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				/*
				 * RelativeLayout tabLayout = (RelativeLayout)
				 * tab.getCustomView();
				 * tabLayout.setBackgroundResource(R.drawable
				 * .tabs_selector_red); tab.setCustomView(tabLayout);
				 */

				// Mostra o conteudo da tab
				mPager.setCurrentItem(tab.getPosition());

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};

		for (int i = 0; i < PimpController.categorias.size(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(PimpController.categorias.get(i))
					.setTabListener(tabListener));

		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
		if (getIntent().getIntExtra("acao", -1) > 0
				&& PimpController.pizzaAtual != null) {
			isConfig = true;
		}

	}

	/*
	 * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub super.onBackPressed(); finish(); }
	 */

	@Override
	public void onDestroy() {
		super.onDestroy();
		SharedPreferencesController.save(Keys.UnidadeEntrega, "",
				sharedPreferences);
	}

	/****************************************************************************************************
	 * 
	 * SharedPreferences
	 * 
	 ***************************************************************************************************/

	private static SharedPreferences sharedPreferences;

	/*******************************************************************************************************
	 * 
	 * BOTOES NA ACTIONBAR
	 * 
	 *******************************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	// EVENTO DO BOTAO
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.action_carrinho:
			intent.setClass(getApplicationContext(), Carrinho.class);
			startActivity(intent);
			return true;
		case R.id.action_favoritos:
			intent.setClass(getApplicationContext(), Favoritos.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/********************************************************************************************************
	 * 
	 * Adapter Class
	 * 
	 ********************************************************************************************************/
	public static class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public Fragment getItem(int position) {
			// Fragment fragment = null;
			// if (position == 4) {
			// fragment = new Outros();
			// return fragment;
			// }

			return ArrayListFragment.newInstance(position);

		}
	}

	public static class ListPizzasAdapter extends BaseAdapter {
		Context context;
		int layoutResourceId;
		List<?> objetos;

		boolean isPizza;
		boolean isBebida;

		public ListPizzasAdapter(Context context, int layout,
				ArrayList<?> objetos, boolean isPizza, boolean isBebida) {
			this.context = context;
			this.layoutResourceId = layout;
			this.objetos = objetos;
			this.isPizza = isPizza;
			this.isBebida = isBebida;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			SaborModelHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				holder = new SaborModelHolder();
				holder.nome = (TextView) row.findViewById(R.id.tvNomePedido);
				holder.desc = (TextView) row.findViewById(R.id.tvDesc);
				holder.preco = (TextView) row.findViewById(R.id.tvPreco);
				row.setTag(holder);

			} else {
				holder = (SaborModelHolder) row.getTag();
			}
			// #pre�o
			if (isPizza) {
				SaborModel saborModel = (SaborModel) objetos.get(position);

				holder.nome.setText(saborModel.getNome());
				String ingredientes = "";
				for (IngredienteModel ingrediente : saborModel
						.getIngredientes()) {
					ingredientes += ingrediente.getNome() + " - ";
				}
				holder.desc.setText(ingredientes);
				holder.preco.setText("R$ " + saborModel.getPreco());
			} else if (isBebida) {
				BebidaModel bebidaModel = (BebidaModel) objetos.get(position);
				holder.nome.setText(bebidaModel.getNome());
				holder.desc.setText("Tamanho " + bebidaModel.getTamanho());
				holder.preco.setText("R$ " + bebidaModel.getPreco());
			} else {
				SobremesaModel sobremesaModel = (SobremesaModel) objetos
						.get(position);
				holder.nome.setText(sobremesaModel.getNome());
				holder.desc.setText(sobremesaModel.getDescricao());
				holder.preco.setText("R$ " + sobremesaModel.getPreco());
			}
			return row;
		}

		static class SaborModelHolder {
			TextView nome;
			TextView desc;
			TextView preco;
		}

		@Override
		public int getCount() {

			return objetos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return objetos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

	}

	/********************************************************************************************************
	 * 
	 * ArrayListFragment Class (Lista de Fragments, no nosso caso s�o as
	 * p�ginas)
	 ********************************************************************************************************/

	public static class ArrayListFragment extends ListFragment {
		int mNum; // funciona como id da fragment

		/**
		 * Create a new instance of CountingFragment, providing "num" as an
		 * argument.
		 */
		static ArrayListFragment newInstance(int num) {
			ArrayListFragment f = new ArrayListFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);
			return f;
		}

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_pager_list, container,
					false);
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			ArrayList<SaborModel> sabores_list = new ArrayList<SaborModel>(
					PimpController.listSabores.get(
							PimpController.categorias.get(mNum)).values());

			if (PimpController.categorias.get(mNum).equals("Bebidas")) {
				setListAdapter(new ListPizzasAdapter(getActivity(),
						R.layout.cardapio_single_row, PimpController.bebidas,
						false, true));
			} else {
				setListAdapter(new ListPizzasAdapter(getActivity(),
						R.layout.cardapio_single_row, sabores_list, true, false));
			}

		}

		// Evento ao clicar em uma op�ao da lista
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

			// realizar uma busca por nome atravez do nome do sabor
			SaborModel fullname = new SaborModel();
			BebidaModel bebida = new BebidaModel();
			SobremesaModel sobremesa = new SobremesaModel();

			if (l.getItemAtPosition(position).getClass()
					.equals(SaborModel.class)) {
				boolean saborJaExistente = false;
				fullname = (SaborModel) l.getItemAtPosition(position);
				Intent intent = new Intent();

				if (PimpController.pizzaAtual == null)
					PimpController.pizzaAtual = new PizzaModel();

				// if (mNum == 0 || mNum == 1) {
				if (PimpController.pizzaAtual.getSabores().size() > 0) {

					for (SaborModel sabor : PimpController.pizzaAtual
							.getSabores()) {

						if (sabor.getNome().equals(fullname.getNome()))
							saborJaExistente = true;
					}

					if (PimpController.pizzaAtual.getSabores().get(0)
							.getCategoria().equals("Doce")
							&& !fullname.getCategoria().equals("Doce")) {
						Toast.makeText(getActivity(),
								"Selecione uma Pizza Doce", Toast.LENGTH_LONG)
								.show();
					} else if (saborJaExistente) {
						Toast.makeText(getActivity(),
								"Sabor ja escolhido para a Pizza",
								Toast.LENGTH_SHORT).show();
					} else {

						if (!PimpController.pizzaAtual.getSabores().get(0)
								.getCategoria().equals("Doce")
								&& fullname.getCategoria().equals("Doce")) {
							Toast.makeText(getActivity(),
									"Selecione uma Pizza Salgada",
									Toast.LENGTH_LONG).show();
						} else {

							/*ArrayList<IngredienteModel> iAuxList = new ArrayList<IngredienteModel>();
							SaborModel aux = PimpController.listSabores.get(
									PimpController.categorias.get(mNum)).get(
									fullname.getNome());
							SaborModel aux2 = new SaborModel(aux.getNome(),
									aux.getCategoria(), aux.getIngredientes(),
									aux.getPreco());

							for (IngredienteModel x: aux.getIngredientes()) {
								IngredienteModel iaux2 = new IngredienteModel(
										x.getNome(), x.getPreco(), true);
								iAuxList.add(iaux2);
							}

							PimpController.AddSaborPizzaAtual(aux2);*/

							
							  PimpController.AddSaborPizzaAtual(PimpController.
							  listSabores .get(PimpController.categorias
							  .get(mNum)).get( fullname.getNome()));
							 

							if (SharedPreferencesController.load(
									Keys.UnidadeEntrega, sharedPreferences) == "") {
								SharedPreferencesController.save(
										Keys.UnidadeEntrega, "",
										sharedPreferences);
								intent.setClass(getActivity(), Lugares.class);
							} else {
								intent.setClass(getActivity(),
										CustomSabores.class);
							}

							if (isConfig) {
								intent.putExtra("acao", 1);
								isConfig = false;
							}
							startActivity(intent);
						}
					}
				} else {
					PimpController
							.AddSaborPizzaAtual(PimpController.listSabores.get(
									PimpController.categorias.get(mNum)).get(
									fullname.getNome()));

					if (SharedPreferencesController.load(Keys.UnidadeEntrega,
							sharedPreferences) == "") {
						SharedPreferencesController.save(Keys.UnidadeEntrega,
								"", sharedPreferences);
						intent.setClass(getActivity(), Lugares.class);
					} else {
						intent.setClass(getActivity(), CustomSabores.class);
					}

					if (isConfig) {
						intent.putExtra("acao", 1);
						isConfig = false;
					}
					startActivity(intent);
				}
			} else if (l.getItemAtPosition(position).getClass()
					.equals(BebidaModel.class)
					&& PimpController.pizzaAtual == null) {

				bebida = (BebidaModel) l.getItemAtPosition(position);
				final BebidaModel selected_bebida = bebida;
				AlertDialog.Builder alertBw;
				AlertDialog alertDw;
				RelativeLayout layout = new RelativeLayout(getActivity());
				NumberPicker nPicker;
				nPicker = new NumberPicker(getActivity());
				nPicker.setMaxValue(10);
				nPicker.setMinValue(1);
				nPicker.setWrapSelectorWheel(false);
				nPicker.setClickable(false);
				nPicker.setEnabled(true);

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						50, 50);
				RelativeLayout.LayoutParams nPickerParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				nPickerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

				layout.setLayoutParams(params);
				layout.addView(nPicker, nPickerParams);
				layout.isClickable();

				nPicker.setOnValueChangedListener(new OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						selected_bebida.setQtd(newVal);
					}
				});

				alertBw = new AlertDialog.Builder(getActivity());
				alertBw.setTitle(bebida.getNome() + " - R$ "
						+ bebida.getPreco());
				alertBw.setView(layout);
				alertBw.setMessage("Quantidade: ").setCancelable(false);
				alertBw.setPositiveButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				alertBw.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								PimpController.AddBebidaPedido(selected_bebida);
								PimpController.carrinhoList.add(new CarrinhoModel(
										selected_bebida.getNome(),
										"Tamanho: "
												+ String.valueOf(selected_bebida
														.getTamanho())
												+ "\nQuantidade: "
												+ String.valueOf(selected_bebida
														.getQtd()), String
												.valueOf(selected_bebida
														.getPreco())));
								
								getActivity().setTitle("R$: "
												+ PimpController.pedido.calcularPrecoPedido());
								Toast.makeText(getActivity(),
										"Bebida adicionada ao Carrinho",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						});
				alertDw = alertBw.create();
				alertDw.show();
			} else {
				Toast.makeText(getActivity(), "Escolha um sabor seu usu�rio!",
						Toast.LENGTH_SHORT).show();
			}

			// } else if (mNum == 2) {
			// list_content = sobremesas;
			// } else {
			// list_content_teste = jsonrequest.getTeste();

			// }
		}
	}

}
