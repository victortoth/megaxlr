package com.beta.pimpmypizza_beta;

import java.util.ArrayList;
import java.util.List;

import pedidos.Carrinho;
import pedidos.PimpController;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class Perfil extends Activity {

	private Switch swLocalizacao;
	private SharedPreferences sharedPreferences;
	private Spinner nomes_spinner;
	
	@Override
	protected void onStart() {
		super.onStart();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		
		ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();
		
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		swLocalizacao = (Switch) findViewById(R.id.localizacaoSwitch);
				
		nomes_spinner = (Spinner) findViewById(R.id.nomes_spinner);
				
		List<String> nomes;
			
		try{
		nomes = SharedPreferencesController.loadList(Keys.Nomes, sharedPreferences);
		//telefones = SharedPreferencesController.loadList(Keys.Telefones, sharedPreferences);
		}catch(Exception e){
		nomes = new ArrayList<String>();
		//telefones = new ArrayList<String>();
		}
		//nomes.add(0, SharedPreferencesController.load(Keys.Nome_Atual, sharedPreferences));
		
		final ArrayAdapter<String> nomes_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_custom_lout, nomes);
		nomes_adapter.setDropDownViewResource(R.layout.spinner_custom_lout);
		nomes_spinner.setAdapter(nomes_adapter);
		
		nomes_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String selected = nomes_spinner.getSelectedItem().toString();
				SharedPreferencesController.save(Keys.Nome_Atual, selected, sharedPreferences);		
				List<String> nomes = SharedPreferencesController.loadList(Keys.Nomes, sharedPreferences);
				
				int index = nomes.indexOf(selected);
				nomes.remove(index);
				nomes.add(0, selected);
				
				SharedPreferencesController.save(Keys.Nomes, nomes, sharedPreferences);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
			
		
		swLocalizacao.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Toast.makeText(getApplicationContext(), "Localização Ativada", Toast.LENGTH_SHORT).show();
					SharedPreferencesController.save(Keys.Location_Enabled, true, sharedPreferences);
				}else{
					Toast.makeText(getApplicationContext(), "Localização Desativada", Toast.LENGTH_SHORT).show();
					SharedPreferencesController.save(Keys.Location_Enabled, false, sharedPreferences);
				}
			}
		});
		
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
			intent.setClass(getApplicationContext(), Favoritos.class);
			startActivity(intent);
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
}
