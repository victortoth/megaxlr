package pedidos;

import java.util.ArrayList;

import com.beta.pimpmypizza_beta.Favoritos;
import com.beta.pimpmypizza_beta.MainActivity;
import com.beta.pimpmypizza_beta.R;

import Model.IngredienteModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CustomSabores extends Activity {

	Button btnSabor1, btnSabor2, btnSabor3, btnSabor4, btnCustomPizza,
			btnAddExtra, btnProximoPasso;
	AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.custom_sabores);

		if (getIntent().getIntExtra("acao", -1) < 0) {
			setTitle("R$: "
					+ (PimpController.pedido.getPreco() + PimpController.pizzaAtual
							.getPreco()));

		}else{
			setTitle("R$: "
					+ (PimpController.pedido.getPreco()));
		}
		android.app.ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();

		btnSabor1 = (Button) findViewById(R.id.btnSabor1);
		btnSabor2 = (Button) findViewById(R.id.btnSabor2);
		btnSabor3 = (Button) findViewById(R.id.btnSabor3);
		btnSabor4 = (Button) findViewById(R.id.btnSabor4);
		btnCustomPizza = (Button) findViewById(R.id.btnCustomPizza);
		btnAddExtra = (Button) findViewById(R.id.btnAddExtra);
		btnProximoPasso = (Button) findViewById(R.id.btnProximo);

		btnSabor1.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor2.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor3.setBackgroundResource(android.R.drawable.btn_default);
		btnSabor4.setBackgroundResource(android.R.drawable.btn_default);
		btnAddExtra.setBackgroundResource(android.R.drawable.btn_default);
		btnCustomPizza.setBackgroundResource(android.R.drawable.btn_default);
		btnProximoPasso.setBackgroundResource(android.R.drawable.btn_default);

		btnSabor1.setBackgroundColor(Color.rgb(255, 158, 82));

	}

	@Override
	protected void onStart() {
		super.onStart();
		try {
			PreencherNomeSabores();
		} catch (Exception e) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
		criarPopupSaida("back");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

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
			if (getIntent().getIntExtra("acao", -1) > 0) {
				intent.putExtra("acao", 1);
			}
			startActivity(intent);
		}

	}

	public void btnProximoClicked(View view) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), ConfigurarPedido.class);
		if (getIntent().getIntExtra("acao", -1) > 0) {
			intent.putExtra("acao", 1);
		}
		startActivity(intent);

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

		PimpController.pizzaAtual = null;
		Intent intent = new Intent();
		boolean exited = false;

		switch (item.getItemId()) {
		case android.R.id.home:
			// this.finish();
			criarPopupSaida("home");
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

		final Context context = CustomSabores.this;

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
