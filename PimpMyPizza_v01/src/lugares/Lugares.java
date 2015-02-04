package lugares;

import java.util.ArrayList;

import pedidos.ConfigurarPedido;
import pedidos.Carrinho;
import pedidos.CustomSabores;
import pedidos.PimpController;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import Model.UnidadeModel;
import android.animation.ArgbEvaluator;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.pimpmypizza_beta.Favoritos;
import com.beta.pimpmypizza_beta.R;

public class Lugares extends FragmentActivity {

	
	static final int NUM_PAGES = 2; // Numero de Paginas
	MyAdapter mAdapter; // Adapter, funciona como Fragment Manager
	ViewPager mPager; // Fragment
	static String[] tabsNames = { "Unidades", "Localizar" };
	
	// Array de unidades e array de nomes das unidades
	public static ArrayList<UnidadeModel> unidades = new ArrayList<UnidadeModel>();
	public static String[] nomes = { "Granja Julieta", "Tucuruvi",
			"Aricanduva", "Perdizes", "Santana", "Vila Mariana", "Mackenzie",
			"Federal", "Casa de Pedra" };

	TextView txtEndereco;
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		finish();
		
	}
	LayoutInflater inflater;
	View localizarView;

	
	@Override
	protected void onStart() {
		super.onStart();
		setTitle("R$: "+ PimpController.pedido.calcularPrecoPedido());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lugares_activity);

		ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		localizarView = inflater.inflate(R.layout.lugares, null);

		txtEndereco = (TextView) localizarView.findViewById(R.id.txtEndereco);

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// Selecionar a tab correspondente com
				// a mudança de pagina, por swype
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		mPager.setAdapter(mAdapter);

		final ActionBar actionBar = getActionBar();

		// Modo de navegacao por tabs, na actionBar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Tab Listener (eventos das tabs)
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// Mostra o conteudo da tab
				mPager.setCurrentItem(tab.getPosition());

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};

		// Cria as tabs e renomeia de acordo com o array declarado no inicio
		for (int i = 0; i < 2; i++) {
			actionBar.addTab(actionBar.newTab().setText(tabsNames[i])
					.setTabListener(tabListener));
		}

	}

/********************************************************************************************************
*
*										BOTOES NA ACTIONBAR	 
* 
*********************************************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	//EVENTO DO BOTAO 
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

/******************************************************************************************************
 * 
 * 									SharedPreferences
 * 	
 *******************************************************************************************************/
	//private static SharedPreferencesController spController;
	private static SharedPreferences sharedPreferences;

/******************************************************************************************************
* 
* 									Adapter Class
* 	
*******************************************************************************************************/
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
			Fragment fragment = null;
			if (position == 1) {
				fragment = new Localizar();
				return fragment;
			}
			return ArrayListFragment.newInstance(position);
		}
	}
/******************************************************************************************************
* 
* 									ArrayListFragment Class
* 	
*******************************************************************************************************/
	public static class ArrayListFragment extends ListFragment {
		int mNum; // funciona como id da fragment
		String nomeUnidade = "";
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

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
			popularVetorUnidades();
		}

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

			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, nomes));
		}

		// Evento ao clicar em uma opçao da lista
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			UnidadeModel unidade = new UnidadeModel();

			for (UnidadeModel u : unidades) {
				if (nomes[position].equalsIgnoreCase(u.getNomeUnidade())) {
					unidade = u;
				}
			}
			this.nomeUnidade = unidade.getNomeUnidade();
			final double latitude = unidade.getLatitude();
			final double longitude = unidade.getLongitude();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
			alertDialogBuilder.setTitle("Unidade " + unidade.getNomeUnidade());
			alertDialogBuilder.setMessage("Endereco: " + unidade.getEndereco())

					.setNeutralButton("Escolher",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SharedPreferencesController.save(Keys.UnidadeEntrega, nomeUnidade, sharedPreferences);
									Intent intent = new Intent();
									intent.setClass(getActivity(), CustomSabores.class);
									startActivityForResult(intent, 100);
									//startActivity(intent);
								}
							})
					.setPositiveButton("Cancelar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							})
					.setNegativeButton("Ver Mapas",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											android.content.Intent.ACTION_VIEW);
									intent.setData(Uri.parse("geo:" + latitude
											+ "," + longitude));
									Intent chooser = Intent.createChooser(
											intent, "Launch Maps");
									startActivity(chooser);

								}
							});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}

		private void popularVetorUnidades() {
			String enderecos[] = { "Rua João Elias, 33", "Rua Ausônia, 100",
					"Avenida Aricanduva, 150", "Rua Monte Alegre, 300",
					"Rua Darzan, 200", "Avenida Lins de Vasconcelos, 45",
					"Rua da Consolação, 600", "Rua Pedro Vicente, 625",
					"Avenida Nossa Senhora da Concordia, 150" };
			double[] latitudes = { -23.6382112, -23.4779586, -23.5423691,
					-23.5448904, -23.5041902, -23.5729401, -23.5473146,
					-23.5251839, -23.4543254 };
			double[] longitudes = { -46.70947530000001, -46.60506950000001,
					-46.53369429999998, -46.67574560000003, -46.62443289999999,
					-46.62280899999996, -46.64587719999997, -46.62730379999999,
					-46.598784499999965 };

			for (int i = 0; i < nomes.length; i++) {
				UnidadeModel unidade = new UnidadeModel();
				unidade.setNomeUnidade(nomes[i]);
				unidade.setEndereco(enderecos[i]);
				unidade.setLatitude(latitudes[i]);
				unidade.setLongitude(longitudes[i]);

				unidades.add(unidade);
			}
		}

	}

}
