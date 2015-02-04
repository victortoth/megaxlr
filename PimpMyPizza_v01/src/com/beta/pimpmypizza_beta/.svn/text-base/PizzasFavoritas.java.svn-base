package com.beta.pimpmypizza_beta;

import java.util.ArrayList;
import java.util.List;

import pedidos.ConfigurarPedido;
import pedidos.PimpController;
import pedidos.Carrinho.MAdapter;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import utils.Helper;

import Model.CarrinhoModel;
import Model.PizzaModel;
import Model.SaborModel;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PizzasFavoritas extends Activity {

	ListView listPizzas;
	private static SharedPreferences sharedPreferences;
	
	@Override
	protected void onStart() {
		super.onStart();
		PreencherLista();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pizzas_favoritas);
		android.app.ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();
		
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		listPizzas = (ListView) findViewById(R.id.listPizzas);
		
		
	}

	private void PreencherLista(){
		try{
			PimpController.lista_favoritos = (ArrayList<PizzaModel>)Helper.getPizzasFavoritas(SharedPreferencesController.load(Keys.PizzasFavoritas, sharedPreferences));
			MAdapter mAdapter = new MAdapter(this,
					R.layout.favoritos_single_row,
					PimpController.lista_favoritos);
			listPizzas.setItemsCanFocus(false);
			listPizzas.setAdapter(mAdapter);
		}catch(Exception e){
			
		}
	}
	/****************************************************************************************************************
	 * 
	 * BOTOES NA ACTIONBAR
	 * 
	 ****************************************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		case R.id.action_carrinho:
			intent.setClass(getApplicationContext(), MainActivity.class);
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
	
	/****************************************************************************************************************
	 * 
	 * BASE ADAPTER CLASS
	 * 
	 ****************************************************************************************************************/

	public static class MAdapter extends ArrayAdapter<PizzaModel> {
		Context context;
		int layoutResourceId;
		ArrayList<PizzaModel> data = new ArrayList<PizzaModel>();
		
		public MAdapter(Context context, int layoutResourceId,ArrayList<PizzaModel> data) {
			super(context, layoutResourceId, data);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.data = data;
		}
		
		@Override
		public View getView(final int position, final View convertView,ViewGroup parent) {
			View row = convertView;
			PizzaHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.favoritos_single_row, parent,
						false);
				holder = new PizzaHolder();
				holder.nome = (TextView) row.findViewById(R.id.tvNomePedido);
				holder.desc = (TextView) row.findViewById(R.id.tvDesc);
				holder.preco = (TextView) row.findViewById(R.id.tvPreco);
				holder.btnSalvar = (Button) row.findViewById(R.id.btnSalvar);
				holder.btnDeletar = (Button) row.findViewById(R.id.btnDeletar);
				row.setTag(holder);
			} else {
				holder = (PizzaHolder) row.getTag();
			}

			holder.nome.setText(Helper.getNomePedidoteste(data.get(position)));
			holder.desc.setText(data.get(position).getPedidoDesc());
			holder.preco.setText("R$" + String.valueOf(data.get(position).getPreco()));
			
			holder.btnSalvar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Adicionar ao Carrinho");
					alertDialogBuilder.setMessage("Você deseja adicionar esta pizza ao carrinho?")

							.setPositiveButton("Adicionar",new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,int which) {
											Toast.makeText(context,"Pizza adicionada ao carrinho com sucesso",Toast.LENGTH_SHORT).show();
											//ArrayList<SaborModel> sabores_cardapio = new ArrayList<SaborModel>(PimpController.listSabores.values());
											//if(Helper.verificaPizzaExistente(PimpController.lista_favoritos.get(position).getSabores(), PimpController.listSabores.values());
											PimpController.AddPizzaPedido(PimpController.lista_favoritos.get(position));
											PimpController.carrinhoList.add(new CarrinhoModel(
													Helper.getNomePedidoteste(PimpController.lista_favoritos.get(position)),
													Helper.getPedidoDesc(PimpController.lista_favoritos.get(position)),
													String.valueOf(PimpController.lista_favoritos.get(position).getPreco())));
										((Activity) getContext()).setTitle("R$: " + PimpController.pedido.getPreco());
										}
									})
							.setNegativeButton("Cancelar",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();

				}
			});
			
			holder.btnDeletar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setTitle("Remover Pizza");
					alertDialogBuilder
							.setMessage("Você deseja remover esta pizza dos favoritos?")
							.setPositiveButton("Remover",
									new DialogInterface.OnClickListener() {								

										@Override
										public void onClick(DialogInterface dialog,int which) {
											PizzaModel pizza = data.get(position);
										PimpController.lista_favoritos.remove(pizza);
										for(PizzaModel p : PimpController.lista_favoritos){
											System.out.println("List Favoritos: " + p.getNomePedido());
										}
										SharedPreferencesController.save(Keys.PizzasFavoritas, 
												Helper.atualizarPizzasFavoritas(PimpController.lista_favoritos), sharedPreferences);
											Toast.makeText(context,"Pizza removida com sucesso",Toast.LENGTH_SHORT).show();
											((Activity) context).recreate();
										}
							})
							.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
										}
									});
			
			AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			});

			
			
			return row;

		}

		static class PizzaHolder {
			TextView nome;
			TextView desc;
			TextView preco;
			Button btnSalvar;
			Button btnDeletar;
		}

	}
}
