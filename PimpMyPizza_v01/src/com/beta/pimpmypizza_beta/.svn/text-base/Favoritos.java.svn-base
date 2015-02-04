	package com.beta.pimpmypizza_beta;

import java.util.ArrayList;

import pedidos.Carrinho;
import pedidos.PimpController;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Favoritos extends Activity {

	Button btnPerfil, btnEnderecos, btnPizzas;
	SharedPreferences sharedPreferences;
	private Activity mActivityContext = null;
	ArrayList<String> list = new ArrayList<String>();

	@Override
	protected void onStart() {
		super.onStart();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos);
		android.app.ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();
		
		mActivityContext = this;
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		btnPerfil = (Button) findViewById(R.id.btnPerfil);
		btnEnderecos = (Button) findViewById(R.id.btnEnderecos);
		btnPizzas = (Button) findViewById(R.id.btnPizzas);
	
	}

	public void btnPerfilClicked(View v) {
		// TODO : Open Perfil Activity
		Intent intent = new Intent();
		intent.setClass(mActivityContext, Perfil.class);
		startActivity(intent);
	}

	public void btnEnderecosClicked(View v) {
		// TODO : Open Enderecos Activity
		// Toast.makeText(getApplicationContext(), "Abrir Tela de enderecos",
		// Toast.LENGTH_SHORT).show();
		if (SharedPreferencesController.load(Keys.EnderecosSalvos,
				sharedPreferences).equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					mActivityContext);
			builder.setTitle("Endereços Salvos");
			builder.setMessage("Você não possui endereços salvos.");

			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.cancel();
						}
					});

			AlertDialog alertDialog = builder.create();
			alertDialog.show();

		} else {
			// CharSequence[] items =
			// SharedPreferencesController.loadList(Keys.EnderecosSalvos,
			// sharedPreferences).toArray(new
			// CharSequence[SharedPreferencesController.loadList(Keys.EnderecosSalvos,
			// sharedPreferences).size()]);
			final ArrayAdapter<String> list = new ArrayAdapter<String>(
					mActivityContext, android.R.layout.simple_list_item_1,
					SharedPreferencesController.loadList(Keys.EnderecosSalvos,
							sharedPreferences));
			AlertDialog.Builder builder = new AlertDialog.Builder(
					mActivityContext);
			builder.setTitle("Escolha um endereço:");
			builder.setAdapter(list, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					PimpController.usuario.setEnderecoEntrega(SharedPreferencesController.loadList(Keys.EnderecosSalvos, sharedPreferences).get(which));                                                            
					Toast.makeText(
							mActivityContext,
							"Endereço escolhido com sucesso!", Toast.LENGTH_SHORT).show();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	public void btnPizzasClicked(View v) {
		Intent intent = new Intent();
		intent.setClass(mActivityContext, PizzasFavoritas.class);
		startActivity(intent);
	}

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
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
