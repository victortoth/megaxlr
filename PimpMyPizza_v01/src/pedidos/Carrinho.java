package pedidos;

import java.util.ArrayList;
import java.util.List;

import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import utils.Helper;

import utils.JSonResponse;

import Model.BebidaModel;
import Model.CarrinhoModel;
import Model.PedidoModel;
import Model.PizzaModel;
import Model.SobremesaModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.pimpmypizza_beta.Favoritos;
import com.beta.pimpmypizza_beta.MainActivity;
import com.beta.pimpmypizza_beta.R;

public class Carrinho extends Activity {

	ListView listPedidos;
	private static SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedidos);

		android.app.ActionBar action_bar = getActionBar();
		action_bar.setHomeButtonEnabled(true);
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.show();
		
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		listPedidos = (ListView) findViewById(R.id.listPedidos);
	}

	@Override
	protected void onStart() {
		super.onStart();
		PreencherLista();
		setTitle("R$: " + PimpController.pedido.calcularPrecoPedido());
	}

	public void onBackPressed() {
		this.finish();
	}

	/****************************************************************************************************************
	 * 
	 * METODOS AUXILIARES
	 * 
	 ****************************************************************************************************************/

	public void PreencherLista() {
		try {

			MAdapter mAdapter = new MAdapter(this,
					R.layout.carrinho_single_row,
					(ArrayList<Object>) PimpController.pedido.getTodosItens());
			listPedidos.setItemsCanFocus(false);
			listPedidos.setAdapter(mAdapter);
			listPedidos.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						final int position, long id) {
					PimpController.pizzaAtual = PimpController.pedido
							.GetPizzaPedido(position);

					Intent intent = new Intent(getApplicationContext(),
							ConfigurarPedido.class);
					intent.putExtra("id", position);
					intent.putExtra("acao", 1);
					startActivity(intent);
				}

			});
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());

		}
	}
	
	String name = "";
	String endereco = "";
	
	public void btnEnviarClicked(View v){
				
		if(PimpController.pedido.getTodosItens().size() > 0){
					
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Digite seu nome");
				builder.setMessage("Seu nome ser� salvo na p�gina de Perfil ");

				// Set up the input
				final EditText input = new EditText(this);
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				input.setText(SharedPreferencesController.load(Keys.Nome_Atual, sharedPreferences));
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        name = input.getText().toString();
				        SharedPreferencesController.save(Keys.Nome_Atual, name, sharedPreferences);
				        
				        List<String> nomes;
												
				        try{
				    		nomes = SharedPreferencesController.loadList(Keys.Nomes, sharedPreferences);
				    		
				    		}catch(Exception e){
				    		nomes = new ArrayList<String>();
				    		
				    		}
				        boolean ja_cadastrado = false;
				        for(String nome : nomes){
				        	if(nome.equals(name))
				        		ja_cadastrado = true;
				        }
				        
				        if(!ja_cadastrado)
				        	nomes.add(name);
				        
				        PimpController.usuario.setNome(name);
				        SharedPreferencesController.save(Keys.Nome_Atual, name, sharedPreferences);
				        SharedPreferencesController.save(Keys.Nomes, nomes, sharedPreferences);
				        criarDialogEndereco();
				        
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});
				builder.show();
			
			
		}else{
			Toast.makeText(getApplicationContext(), "N�o itens para envio", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void criarDialogEndereco(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Endere�o de Entrega");
		builder.setMessage("Seu endere�o ser� salvo na p�gina de Favoritos ");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		input.setText(PimpController.usuario.getEnderecoEntrega());
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	if(input.getText().toString().isEmpty()){
		    		Toast.makeText(getApplicationContext(), "Informe um endere�o!", Toast.LENGTH_LONG).show();
		    		criarDialogEndereco();
		    	}else{
		    	name = input.getText().toString();
		        SharedPreferencesController.save(Keys.Endereco_Atual, name, sharedPreferences);
		        
		        List<String> enderecos;
							
		        try{
		    		enderecos = SharedPreferencesController.loadList(Keys.EnderecosSalvos, sharedPreferences);
		    		
		    		}catch(Exception e){
		    		enderecos = new ArrayList<String>();
		    		
		    		}
		        
		        boolean ja_cadastrado = false;
		        for(String endereco : enderecos){
		        	if(endereco.equals(name))
		        		ja_cadastrado = true;
		        }
		        
		        if(!ja_cadastrado)
		        	enderecos.add(name);
		        
		        PimpController.usuario.setEnderecoEntrega(name);
		        SharedPreferencesController.save(Keys.EnderecosSalvos, enderecos, sharedPreferences);
		        PimpController.usuario.setUnidade_entrega(SharedPreferencesController.load(Keys.UnidadeEntrega, sharedPreferences));
		        criarTelefoneDialog();
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
	}
	
	public void criarTelefoneDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Telefone de contato");
		builder.setMessage("Informe um telefone de contato para facilitar a entrega: ");
		
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		input.setText(Helper.getPhonenumber(getApplicationContext()));
		builder.setView(input);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		       PimpController.usuario.setTelefone(input.getText().toString()); 
		       criarPagamentoDialog();
		    			    			        
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		

		builder.show();
		
	}
	
	public void criarPagamentoDialog(){
		final List<String> formas_pagamento = new ArrayList<String>();
		formas_pagamento.add("Dinheiro");
		formas_pagamento.add("Cart�o");
		
		final ArrayAdapter<String> list = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,formas_pagamento);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Escolha uma forma de pagamento:");
		builder.setAdapter(list, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				PimpController.usuario.setForma_pagamento(formas_pagamento.get(which));
				if(PimpController.usuario.getForma_pagamento().equals("Dinheiro")){
					criarTrocoDialog();
				}else if(!PimpController.isServerOff){
				JSonResponse response = new JSonResponse(getApplicationContext());
		    	response.EnviarPedido(PimpController.pedido);
				}else{
					 Toast.makeText(getApplicationContext(), "N�o � possivel enviar o pedido no momento, por favor tente mais tarde", Toast.LENGTH_LONG).show();	
				}
			}
		});
		builder.show();
	}
	
	public void criarTrocoDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Necessita de Troco?");
		builder.setMessage("Caso n�o necessite de troco, basta deixar o campo vazio e pressionar 'OK'");
		
		final EditText input = new EditText(this);
		
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		       PimpController.usuario.setValor_troco(input.getText().toString());
		       if(!PimpController.isServerOff){
		       JSonResponse response = new JSonResponse(getApplicationContext());
		       response.EnviarPedido(PimpController.pedido);
		       }else{
		    	   Toast.makeText(getApplicationContext(), "N�o � possivel enviar o pedido no momento, por favor tente mais tarde", Toast.LENGTH_LONG).show();
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

	// EVENTO DO BOTAO
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

	public static class MAdapter extends BaseAdapter {

		Context context;
		int layoutResourceId;
		ArrayList<Object> data;

		public MAdapter(Context context, int layoutResourceId,
				ArrayList<Object> data) {
			super();
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.data = data;
		}

		@Override
		public View getView(final int position, final View convertView,
				ViewGroup parent) {
			View row = convertView;
			PizzaHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.carrinho_single_row, parent,
						false);
				holder = new PizzaHolder();
				holder.nome = (TextView) row.findViewById(R.id.tvNomePedido);
				holder.desc = (TextView) row.findViewById(R.id.tvDesc);
				holder.preco = (TextView) row.findViewById(R.id.tvPreco);
				holder.btnSalvar = (Button) row.findViewById(R.id.btnSalvar);
				holder.btnDeletar = (Button) row.findViewById(R.id.btnDeletar);
				holder.btnConfigurar = (Button) row
						.findViewById(R.id.btnConfig);
				if (data.get(position).getClass().equals(BebidaModel.class)) {
					holder.btnSalvar.setVisibility(View.GONE);
					//holder.btnConfigurar.setVisibility(View.GONE);
					
				}
				row.setTag(holder);
			} else {
				holder = (PizzaHolder) row.getTag();
			}

			holder.nome.setText(PimpController.carrinhoList.get(position)
					.getNomePedido());
			holder.desc.setText(PimpController.carrinhoList.get(position)
					.getDesc());
			holder.preco.setText("R$ "
					+ PimpController.carrinhoList.get(position).getPreco());

			holder.btnSalvar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Salvar Pizza");
					alertDialogBuilder
							.setMessage(
									"Voc� deseja salvar esta pizza nos favoritos?")

							.setPositiveButton("Salvar",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Toast.makeText(context,
													"Pizza salva com sucesso",
													Toast.LENGTH_SHORT).show();
											
											if (SharedPreferencesController
													.load(Keys.PizzasFavoritas,
															sharedPreferences)
													.equals("")) {
												
												SharedPreferencesController
														.save(Keys.PizzasFavoritas,
																Helper.criarPizzasFavoritas(PimpController.pedido
																		.GetPizzaPedido(position)),
																sharedPreferences);
											} else {
												String json = SharedPreferencesController
														.load(Keys.PizzasFavoritas,
																sharedPreferences);
												SharedPreferencesController
														.save(Keys.PizzasFavoritas,
																Helper.PizzaToJson(
																		PimpController.pedido
																				.GetPizzaPedido(position),
																		json),
																sharedPreferences);

												List<PizzaModel> pizzas = Helper
														.getPizzasFavoritas(SharedPreferencesController
																.load(Keys.PizzasFavoritas,
																		sharedPreferences));
												
											}
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
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Remover item");
					alertDialogBuilder
							.setMessage(
									"Voc� deseja remover este item do pedido?")
							.setPositiveButton("Remover",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (data.get(position).getClass()
													.equals(BebidaModel.class)) {
												BebidaModel bebida = (BebidaModel) data
														.get(position);
												PimpController.pedido
														.RemoveBebida(bebida);
												PimpController
														.RemoveFromCarrinhoList(position);
												
											} else if (data.get(position)
													.getClass()
													.equals(PizzaModel.class)) {
												PizzaModel pizza = (PizzaModel) data
														.get(position);
												PimpController.pedido
														.RemovePizza(pizza);
												PimpController
														.RemoveFromCarrinhoList(position);
											} else {
												SobremesaModel sobremesa = (SobremesaModel) data
														.get(position);
												PimpController.pedido
														.RemoveSobremesa(sobremesa);
												PimpController
														.RemoveFromCarrinhoList(position);

											}
											Toast.makeText(
													context,
													"Item removido com sucesso",
													Toast.LENGTH_SHORT).show();
											((Activity) context).recreate();
											
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

			holder.btnConfigurar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(data.get(position).getClass().equals(BebidaModel.class)){
						
						final BebidaModel selected_bebida = (BebidaModel) data.get(position);
						final BebidaModel new_bebida = selected_bebida;
						
						AlertDialog.Builder alertBw;
						AlertDialog alertDw;
						RelativeLayout layout = new RelativeLayout(context);
						NumberPicker nPicker;
						nPicker = new NumberPicker(context);
						nPicker.setMaxValue(10);
						nPicker.setMinValue(1);
						nPicker.setWrapSelectorWheel(false);
						nPicker.setClickable(false);
						nPicker.setEnabled(true);
						
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50,50);
						RelativeLayout.LayoutParams nPickerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
						
						nPickerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		                
				      layout.setLayoutParams(params);
				     layout.addView(nPicker,nPickerParams);
				     layout.isClickable();
				                       
				           nPicker.setOnValueChangedListener(new OnValueChangeListener() {
				         @Override
				      public void onValueChange(NumberPicker picker, int oldVal, int newVal){
				        	 new_bebida.setQtd(newVal);
				      }
				                 });

				     alertBw=new AlertDialog.Builder(context);
				     alertBw.setTitle(selected_bebida.getNome() + " - R$ " + selected_bebida.getPreco());
				     alertBw.setView(layout);
				     alertBw.setMessage("Quantidade: ").setCancelable(false); 
				     alertBw.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				                                  
				     @Override
				                 public void onClick(DialogInterface dialog, int which) {
				     // TODO Auto-generated method stub
				           dialog.dismiss();
				      }
				     });
				     alertBw.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
				         @Override
				      public void onClick(DialogInterface dialog, int which){         
				        	 
				        	 PimpController.RemoveBebidaPedido(selected_bebida);
				        	 PimpController.AddBebidaPedido(new_bebida);
				        	 
				        	 PimpController.RemoveFromCarrinhoList(position);
				        	 PimpController.carrinhoList.add(new CarrinhoModel(new_bebida.getNome(), 
				        			 "Tamanho: " + String.valueOf(new_bebida.getTamanho()) + "\nQuantidade: " + String.valueOf(new_bebida.getQtd()), String.valueOf(new_bebida.getPreco())));
				        	 ((Activity) context).setTitle("R$: " + PimpController.pedido.getPreco());
				        	 Toast.makeText(context, "Quantidade atualizada", Toast.LENGTH_SHORT).show();
				        	 dialog.dismiss();
				      }                     
				     });                  
				     alertDw=alertBw.create(); 
				     alertDw.show();
				             
					
					}else{
					PimpController.id = position;					
					PimpController.pizzaAtual = PimpController.pedido.GetPizzaPedido(position);
					Intent intent = new Intent(context, CustomSabores.class);
					intent.putExtra("acao", 1);
					context.startActivity(intent);
					}
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
			Button btnConfigurar;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

	}

}
