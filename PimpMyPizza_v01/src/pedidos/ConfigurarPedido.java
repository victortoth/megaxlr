package pedidos;

import java.util.ArrayList;
import java.util.List;

import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import utils.Helper;
import utils.JSonResponse;
import Model.CarrinhoModel;
import Model.IngredienteModel;
import Model.PizzaModel;
import Model.SaborModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.pimpmypizza_beta.Favoritos;
import com.beta.pimpmypizza_beta.MainActivity;
import com.beta.pimpmypizza_beta.R;

public class ConfigurarPedido extends Activity {

	/********************************************************************************************************
	 * 
	 * Elementos da Activity
	 * 
	 *********************************************************************************************************/
	// Spinner tamanhos;// Drop Down do android
	NumberPicker npQtd;// Seleciona o numero de pizzas a serem feitas
	ArrayAdapter<CharSequence> sAdapter;// adapter para popular o Sipnner
	ArrayAdapter<CharSequence> pAdapter;
	ArrayAdapter<CharSequence> bAdapter;
	TextView tvSabor;// Mostra a pizza principal
	Button sendData;// envia o pedido
	Button btnTamanhos;
	Button btnPedacos;
	Button btnBorda;
	Button btnSabor1, btnSabor2, btnSabor3, btnSabor4, btnCustomPizza,
			btnAddExtra;
	AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_config);

		android.app.ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();

		/*
		 * Refer�ncias aos elementos criando no xml "pedido_config"
		 */
		tvSabor = (TextView) findViewById(R.id.tvSabor);
		sendData = (Button) findViewById(R.id.btnSend);
		btnTamanhos = (Button) findViewById(R.id.btnTamanhos);
		btnPedacos = (Button) findViewById(R.id.btnPedacos);
		btnBorda = (Button) findViewById(R.id.btnBorda);
		npQtd = (NumberPicker) findViewById(R.id.npQtd);
		/*btnSabor1 = (Button) findViewById(R.id.btnSabor1);
		btnSabor2 = (Button) findViewById(R.id.btnSabor2);
		btnSabor3 = (Button) findViewById(R.id.btnSabor3);
		btnSabor4 = (Button) findViewById(R.id.btnSabor4);
		btnCustomPizza = (Button) findViewById(R.id.btnCustomPizza);
		btnAddExtra = (Button) findViewById(R.id.btnAddExtra);*/

		btnTamanhos.setBackgroundResource(android.R.drawable.btn_default);
		btnPedacos.setBackgroundResource(android.R.drawable.btn_default);
		btnBorda.setBackgroundResource(android.R.drawable.btn_default);
		/*btnSabor1.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor2.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor3.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor4.setBackgroundResource(android.R.drawable.btn_default);
		btnAddExtra.setBackgroundResource(android.R.drawable.btn_default);
		btnCustomPizza.setBackgroundResource(android.R.drawable.btn_default);*/
		sendData.setBackgroundResource(android.R.drawable.btn_default);

		//btnSabor1.setBackgroundColor(Color.rgb(255, 158, 82));
		;
		// configura o NumberPicker
		npQtd.setMinValue(1);
		npQtd.setMaxValue(20);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		sAdapter = ArrayAdapter.createFromResource(this,
				R.array.Tamanhos_array,
				android.R.layout.simple_spinner_dropdown_item);// array de
																// string//
																// res/values/strings.xml
		// Spinner tamanos
		sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// pedacos adapter
		pAdapter = ArrayAdapter.createFromResource(this, R.array.default_array,
				android.R.layout.simple_spinner_dropdown_item);
		pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// borda adapter
		bAdapter = ArrayAdapter.createFromResource(this, R.array.Borda_array,
				android.R.layout.simple_spinner_dropdown_item);
		bAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		if (PimpController.pizzaAtual.getSabores().get(0).getCategoria()
				.equals("Doce")) {
			btnBorda.setText("Normal");
			btnBorda.setClickable(false);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
		try {
			PreencherCampos();
			if (getIntent().getIntExtra("acao", -1) > 0) {
				AlterarArrayPedacos(btnTamanhos.getText().toString());
				setTitle("R$: " + PimpController.pedido.getPreco());

			} else {
				setTitle("R$: "
						+ (PimpController.pedido.getPreco() + PimpController.pizzaAtual
								.getPreco()));
				npQtd.setValue(1);
				PimpController.id = -1;
			}
		} catch (Exception e) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

	/***************************************************************************************************************
	 * 
	 * Eventos dos bot�es na Activity
	 * 
	 **************************************************************************************************************/

	public void CustomPizza(View v) {

		final ArrayList<String> nomesIngredientes = new ArrayList<String>();
		/*
		 * monta um vetor com o tamanho do array de ingredientes do sabor
		 * selecionado
		 */
		final boolean[] ingredientesbool = new boolean[PimpController.pizzaAtual
				.getSabores().get(PimpController.saborAtual).getIngredientes()
				.size()];

		for (int i = 0; i < PimpController.pizzaAtual.getSabores()
				.get(PimpController.saborAtual).getIngredientes().size(); i++) {

			ingredientesbool[i] = PimpController.pizzaAtual.getSabores()
					.get(PimpController.saborAtual).getIngredientes().get(i)
					.isSelecionado();

			nomesIngredientes.add(PimpController.pizzaAtual.getSabores()
					.get(PimpController.saborAtual).getIngredientes().get(i)
					.getNome());
		}

		new AlertDialog.Builder(this)
				.setTitle("Ingredientes")
				.setMultiChoiceItems(
						nomesIngredientes.toArray(new CharSequence[nomesIngredientes
								.size()]), ingredientesbool,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {

								if (isChecked) {

									PimpController.pizzaAtual.getSabores()
											.get(PimpController.saborAtual)
											.getIngredientes().get(which)
											.setSelecionado(true);

								} else {

									PimpController.pizzaAtual.getSabores()
											.get(PimpController.saborAtual)
											.getIngredientes().get(which)
											.setSelecionado(false);
								}

							}
						})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						for (int i = 0; i < PimpController.pizzaAtual
								.getSabores().get(PimpController.saborAtual)
								.getIngredientes().size(); i++) {

							/* verifica se o ingrediente foi alterado */
							if (!PimpController.pizzaAtual.getSabores()
									.get(PimpController.saborAtual)
									.getIngredientes().get(i).isSelecionado()) {

								/*
								 * verifica se o ingrediente j� n�o est� na
								 * lista de ingredientes retirados
								 */
								if (!PimpController.pizzaAtual
										.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientesRetirados()
										.contains(
												PimpController.pizzaAtual
														.getSabores()
														.get(PimpController.saborAtual)
														.getIngredientes()
														.get(i))) {

									/* caso n�o contenha ele adiciona */
									PimpController.pizzaAtual

											.getSabores()
											.get(PimpController.saborAtual)
											.getIngredientesRetirados()
											.add(PimpController.pizzaAtual
													.getSabores()
													.get(PimpController.saborAtual)
													.getIngredientes().get(i));
								}
							} else {

								PimpController.pizzaAtual.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientes().get(i)
										.setSelecionado(false);

								if (PimpController.pizzaAtual
										.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientesRetirados()
										.contains(
												PimpController.pizzaAtual
														.getSabores()
														.get(PimpController.saborAtual)
														.getIngredientes()
														.get(i))) {
									PimpController.pizzaAtual
											.getSabores()
											.get(PimpController.saborAtual)
											.getIngredientesRetirados()
											.remove(PimpController.pizzaAtual
													.getSabores()
													.get(PimpController.saborAtual)
													.getIngredientes().get(i));
								}

								PimpController.pizzaAtual.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientes().get(i)
										.setSelecionado(true);
							}

						}
					}

				}).create().show();

	}

	// � gambiarra que da meeedo #N�o Abra
	public void AddExtra(View v) {
		final ArrayList<IngredienteModel> Auxi = new ArrayList<IngredienteModel>();

		/*
		 * monta um array com todos os nomes dos ingredientes que podem ser
		 * adicionados
		 */
		final ArrayList<String> nTodosIngredientes = new ArrayList<String>(
				PimpController.ingredientes.keySet());
		for (IngredienteModel aux : PimpController.pizzaAtual.getSabores()
				.get(PimpController.saborAtual).getIngredientes()) {
			nTodosIngredientes.remove(aux.getNome());
		}

		/*
		 * clona os objetos da classe estatica para a lista de ingredientes
		 * adicionaveis
		 */
		for (String aux : nTodosIngredientes) {
			IngredienteModel ingAux = PimpController.ingredientes.get(aux);
			IngredienteModel ingAux2 = new IngredienteModel(ingAux.getNome(),
					ingAux.getPreco(), false);

			for (IngredienteModel x : PimpController.pizzaAtual.getSabores()
					.get(PimpController.saborAtual)
					.getIngredientesAdicionados()) {

				if (x.getNome().equals(ingAux2.getNome())) {
					ingAux2.setSelecionado(true);
				}

			}

			Auxi.add(ingAux2);
		}

		final boolean[] ingredientesbool = new boolean[Auxi.size()];

		for (int i = 0; i < Auxi.size(); i++) {
			ingredientesbool[i] = Auxi.get(i).isSelecionado();

		}

		new AlertDialog.Builder(this)
				.setTitle("Ingredientes extras ser�o cobrados")
				.setMultiChoiceItems(
						nTodosIngredientes.toArray(new CharSequence[nTodosIngredientes
								.size()]), ingredientesbool,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {

								if (isChecked) {

									Auxi.get(which).setSelecionado(true);

								} else {

									Auxi.get(which).setSelecionado(false);

								}

							}
						})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						for (int i = 0; i < Auxi.size(); i++) {

							if (Auxi.get(i).isSelecionado()) {

								int cont = 0;
								for (IngredienteModel aux : PimpController.pizzaAtual
										.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientesAdicionados()) {

									if (!Auxi.get(i).getNome()
											.equals(aux.getNome())) {
										cont++;
									}
								}

								if (cont == PimpController.pizzaAtual
										.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientesAdicionados().size()) {

									PimpController.pizzaAtual.getSabores()
											.get(PimpController.saborAtual)
											.getIngredientesAdicionados()
											.add(Auxi.get(i));

									PimpController.pizzaAtual
											.setPreco(PimpController.pizzaAtual
													.getPreco()
													+ Auxi.get(i).getPreco());
								}

							} else {

								for (IngredienteModel aux : PimpController.pizzaAtual
										.getSabores()
										.get(PimpController.saborAtual)
										.getIngredientesAdicionados()) {

									if (Auxi.get(i).getNome()
											.equals(aux.getNome())) {

										PimpController.pizzaAtual.getSabores()
												.get(PimpController.saborAtual)
												.getIngredientesAdicionados()
												.remove(aux);

										PimpController.pizzaAtual
												.setPreco(PimpController.pizzaAtual
														.getPreco()
														- aux.getPreco());
										break;

									}

								}

							}

						}
						// PimpController.pizzaAtual.calcularPreco();
						setTitle("R$: "
								+ (PimpController.pedido.getPreco() + PimpController.pizzaAtual
										.getPreco()));
					}

				}).create().show();

	}

	public void SendData(View v) {

		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), MainActivity.class);
		
		if (!btnTamanhos.getText().equals("Tamanho")
				&& !btnBorda.getText().equals("Borda")
				&& !btnPedacos.getText().equals("Peda�os")) {

			/*
			 * Atualiza os dados da pizza atual
			 */
			PimpController.pizzaAtual.setTamanho(btnTamanhos.getText());
			PimpController.pizzaAtual.setnPedacos(Integer
					.parseInt(btnPedacos
							.getText()
							.toString()
							.substring(
									0,
									btnPedacos.getText().toString()
											.indexOf(" "))));
			PimpController.pizzaAtual.setBorda(btnBorda.getText().toString());
			PimpController.pizzaAtual.setQtd(npQtd.getValue());
			 PimpController.pizzaAtual.calcularPreco();
			if (getIntent().getIntExtra("acao", -1) > 0) {

				if (PimpController.pedido.getPizzas().size() != 0) {

					// arrumar este metodo
					// PimpController.pedido.SetPizzaPedido(PimpController.id,
					// PimpController.pizzaAtual);
					PimpController.pedido.getPizzas().remove(PimpController.id);
					PimpController.pedido.getPizzas().add(PimpController.id,
							PimpController.pizzaAtual);

					PimpController.carrinhoList.remove(PimpController.id);
					PimpController.carrinhoList
							.add(PimpController.id,
									new CarrinhoModel(
											Helper.getNomePedidoteste(PimpController.pizzaAtual),
											Helper.getPedidoDesc(PimpController.pizzaAtual),											
											String.valueOf(PimpController.pizzaAtual.getPreco())));
					
					PimpController.id = -1;
				}

				Toast.makeText(this, "Pedido alterado ", Toast.LENGTH_SHORT)
						.show();
				startActivity(intent);

			} else {
				PimpController.AddPizzaPedido(PimpController.pizzaAtual);
				PimpController.carrinhoList.add(new CarrinhoModel(Helper
						.getNomePedidoteste(PimpController.pizzaAtual), Helper
						.getPedidoDesc(PimpController.pizzaAtual), String
						.valueOf(PimpController.pizzaAtual.getPreco())));

				Toast.makeText(this, "Pedido adicionado ao Carrinho",
						Toast.LENGTH_SHORT).show();
				startActivity(intent);

			}

			PimpController.pizzaAtual = null;
			finish();

		} else {
			Toast.makeText(this, "Preencha todos os dados", Toast.LENGTH_LONG)
					.show();
		}

	}

	public void SetTamanho(View v) {
		new AlertDialog.Builder(this).setTitle("Tamanhos")
				.setAdapter(sAdapter, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// TODO: user specific action - pega o item selecionado
						ListView lw = ((AlertDialog) dialog).getListView();
						Object checkedItem = lw.getAdapter().getItem(which);
						if (PimpController.pizzaAtual.getSabores().size() > 2
								&& checkedItem.toString().equals("Brotinho")) {
							Toast.makeText(getApplicationContext(),
									"Dois sabores no m�ximo na Brotinho",
									Toast.LENGTH_LONG).show();
						} else {
							btnTamanhos.setText(checkedItem.toString());
							PimpController.pizzaAtual.setTamanho(checkedItem
									.toString());
							AlterarArrayPedacos(checkedItem.toString());

							dialog.dismiss();
						}
					}
				}).create().show();
	}

	public void SetPedacos(View v) {
		new AlertDialog.Builder(this).setTitle("Peda�os")
				.setAdapter(pAdapter, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// TODO: user specific action - pega o item selecionado
						ListView lw = ((AlertDialog) dialog).getListView();
						Object checkedItem = lw.getAdapter().getItem(which);
						btnPedacos.setText(checkedItem.toString());
						if (!btnPedacos.getText().equals("Peda�os")) {
							PimpController.pizzaAtual.setnPedacos(Integer
									.parseInt(btnPedacos
											.getText()
											.toString()
											.substring(
													0,
													btnPedacos.getText()
															.toString()
															.indexOf(" "))));

						}

						dialog.dismiss();
					}
				}).create().show();
	}

	public void SetBorda(View v) {
		new AlertDialog.Builder(this).setTitle("Borda")
				.setAdapter(bAdapter, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// TODO: user specific action - pega o item selecionado
						ListView lw = ((AlertDialog) dialog).getListView();
						Object checkedItem = lw.getAdapter().getItem(which);
						btnBorda.setText(checkedItem.toString());
						PimpController.pizzaAtual.setBorda(checkedItem
								.toString());

						dialog.dismiss();
					}
				}).create().show();

	}

	public void btnAdicionarSaborClicked(View view) {
		Button btnAdicionarSabor = (Button) view;

		if (!btnAdicionarSabor.getText().equals("+")) {
			Toast.makeText(getApplicationContext(), "Selecionado",
					Toast.LENGTH_SHORT).show();
			btnAdicionarSabor.setBackgroundColor(Color.rgb(255, 158, 82));

			if (view.getId() == R.id.btnSabor1) {
				PimpController.saborAtual = 0;
				btnSabor2.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor3.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor4.setBackgroundResource(android.R.drawable.btn_default);
			} else if (view.getId() == R.id.btnSabor2) {
				PimpController.saborAtual = 1;
				btnSabor1.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor3.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor4.setBackgroundResource(android.R.drawable.btn_default);
			} else if (view.getId() == R.id.btnSabor3) {
				PimpController.saborAtual = 2;
				btnSabor1.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor2.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor4.setBackgroundResource(android.R.drawable.btn_default);
			} else if (view.getId() == R.id.btnSabor4) {
				PimpController.saborAtual = 3;
				btnSabor1.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor2.setBackgroundResource(android.R.drawable.btn_default);
				btnSabor3.setBackgroundResource(android.R.drawable.btn_default);
			}

		} else {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);

			if (btnTamanhos.getText().equals("Brotinho")
					&& PimpController.pizzaAtual.getSabores().size() > 1) {
				Toast.makeText(getApplicationContext(),
						"Tamanho Brotinho permite apenas dois sabores",
						Toast.LENGTH_SHORT).show();
			} else {
				if (getIntent().getIntExtra("acao", -1) > 0) {
					intent.putExtra("acao", 1);
				}
				startActivity(intent);
			}
		}

	}

	/********************************************************************************************************
	 * 
	 * Metodos Auxiliares
	 * 
	 *********************************************************************************************************/

	/*
	 * public void SelecionarMaiorPreco() { double pAux = 0.0; //double pAux2 =
	 * 0.0;
	 * 
	 * <<<<<<< .mine if (PimpController.pizzaAtual.getSabores().size() > 1) {
	 * PimpController.pedido.preco -= PimpController.pizzaAtual.getPreco();
	 * ======= if (PimpController.pizzaAtual.getSabores().size() > 1) { >>>>>>>
	 * .r27376 for (SaborModel aux : PimpController.pizzaAtual.getSabores()) {
	 * if (aux.getPreco() > pAux) { pAux = aux.getPreco(); } } /*for (SaborModel
	 * aux : PimpController.pizzaAtual.getSabores()) { if (aux.getPreco() !=
	 * pAux) { if (aux.getPreco() > pAux2) { pAux2 = aux.getPreco(); } } }
	 * 
	 * if(PimpController.pizzaAtual.getSabores().size() > 1 && pAux2 != 0.0)
	 * PimpController.pedido.setPreco(PimpController.pedido.getPreco() + pAux-
	 * pAux2); }else{
	 * PimpController.pedido.setPreco(PimpController.pizzaAtual.getSabores
	 * ().get(0).getPreco()); }
	 * 
	 * 
	 * PimpController.pedido.setPreco(PimpController.pedido.getPreco() + pAux);
	 * 
	 * setTitle("R$: " + PimpController.pedido.getPreco());
	 * 
	 * }
	 */

	public void AlterarArrayPedacos(String tamanho) {
		if (tamanho.equals("M�dia")) {
			pAdapter = ArrayAdapter.createFromResource(this,
					R.array.Medio_array,
					android.R.layout.simple_spinner_dropdown_item);
			btnPedacos.setText("8 peda�os");
		} else if (tamanho.equals("Grande")) {
			pAdapter = ArrayAdapter.createFromResource(this,
					R.array.Grande_array,
					android.R.layout.simple_spinner_dropdown_item);
			btnPedacos.setText("10 peda�os");
		} else if (tamanho.equals("Brotinho")) {
			pAdapter = ArrayAdapter.createFromResource(this,
					R.array.Brotinho_array,
					android.R.layout.simple_spinner_dropdown_item);
			btnPedacos.setText("4 peda�os");
		} else {
			pAdapter = ArrayAdapter.createFromResource(this,
					R.array.default_array,
					android.R.layout.simple_spinner_dropdown_item);
		}

		if (!btnPedacos.getText().equals("Peda�os")) {
			PimpController.pizzaAtual.setnPedacos(Integer
					.parseInt(btnPedacos
							.getText()
							.toString()
							.substring(
									0,
									btnPedacos.getText().toString()
											.indexOf(" "))));

		}
	}

	public void PreencherCampos() {
		if (!PimpController.pizzaAtual.getTamanho().equals(""))
			btnTamanhos.setText(PimpController.pizzaAtual.getTamanho());
		if (PimpController.pizzaAtual.getnPedacos() != 0)
			btnPedacos.setText(String.valueOf(PimpController.pizzaAtual
					.getnPedacos()) + " peda�os");
		if (!PimpController.pizzaAtual.getBorda().equals(""))
			btnBorda.setText(PimpController.pizzaAtual.getBorda());

		npQtd.setValue(PimpController.pizzaAtual.getQtd());

		PreencherNomeSabores();

	}

	public void PreencherNomeSabores() {

		try {

			btnSabor1.setText(PimpController.pizzaAtual.getSabores().get(0)
					.getNome());

			if (PimpController.pizzaAtual.getSabores().size() > 1) {
				switch (PimpController.pizzaAtual.getSabores().size()) {
				case 2:
					btnSabor2.setText(PimpController.pizzaAtual.getSabores()
							.get(1).getNome());
					break;
				case 3:
					btnSabor2.setText(PimpController.pizzaAtual.getSabores()
							.get(1).getNome());
					btnSabor3.setText(PimpController.pizzaAtual.getSabores()
							.get(2).getNome());
					break;
				case 4:
					btnSabor2.setText(PimpController.pizzaAtual.getSabores()
							.get(1).getNome());
					btnSabor3.setText(PimpController.pizzaAtual.getSabores()
							.get(2).getNome());
					btnSabor4.setText(PimpController.pizzaAtual.getSabores()
							.get(3).getNome());
					break;
				}
			}
		} catch (Exception e) {

		}

	}

	/********************************************************************************************************
	 * 
	 * BOTOES NA ACTION BAR
	 * 
	 *********************************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	// EVENTO DO BOTAO
	public boolean onOptionsItemSelected(MenuItem item) {

		//PimpController.pizzaAtual = null;
		Intent intent = new Intent();
		boolean exited = false;

		switch (item.getItemId()) {
		case android.R.id.home:
			// this.finish();
			//criarPopupSaida("home");

			this.finish();
			return true;
		case R.id.action_carrinho:
			// intent.setClass(getApplicationContext(), Carrinho.class);
			// startActivity(intent);
			// this.finish();
			criarPopupSaida("carrinho");
			return true;
		case R.id.action_favoritos:
			// intent.setClass(getApplicationContext(), Favoritos.class);
			// startActivity(intent);
			criarPopupSaida("favoritos");
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	boolean exit = false;

	public void criarPopupSaida(final String param) {

		final Context context = ConfigurarPedido.this;

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Sair de Configura��o");
		alertDialogBuilder
				.setMessage(
						"Voc� deseja sair da configura��o? Se sair, perder� os dados da pizza atual")

				.setPositiveButton("Sair",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								PimpController.pizzaAtual = null;
								Intent intent = new Intent();
								if (param.equalsIgnoreCase("carrinho")) {
									intent.setClass(getApplicationContext(),
											Carrinho.class);
									startActivity(intent);
								} else if (param.equalsIgnoreCase("favoritos")) {
									intent.setClass(getApplicationContext(),
											Favoritos.class);
									startActivity(intent);
								}
								((Activity) context).finish();
							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

}
