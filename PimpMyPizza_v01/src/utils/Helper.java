package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pedidos.PimpController;
import Model.BebidaModel;
import Model.CarrinhoModel;
import Model.IngredienteModel;
import Model.PedidoModel;
import Model.PizzaModel;
import Model.SaborModel;
import Model.SobremesaModel;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.JsonWriter;
import android.util.Log;

public class Helper {

	public static boolean isConnectedtoInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}

			}

		}
		return false;
	}

	/****************************************************************************************************
	 * 
	 * Tratamento de JSON
	 * 
	 ***************************************************************************************************/
	/*
	 * parece-me legitimo
	 */
	public static void getCategorias(String data) {
		try {
			
			/*
			 * recebe jason (data)e cria o objeto em JAVA
			 */
			JSONObject json = new JSONObject(data); 
			

			/*
			 * Seleciona um array dentro de um Json Object
			 */
			JSONArray array;
			array = json.getJSONObject("Cardapio").getJSONArray("Categorias");

			// Popula Lista de categorias para criar as tabs
			for (int i = 0; i < array.length(); i++) {
				PimpController.categorias.add(array.getString(i));
				
			}
			
			if(PimpController.categorias.contains("Doce") ){
				PimpController.categorias.remove("Doce");
				PimpController.categorias.add("Doce");
			}

			
			if(PimpController.categorias.contains("Bebidas") ){
				PimpController.categorias.remove("Bebidas");
				PimpController.categorias.add("Bebidas");
			}

			if (PimpController.bebidas.size() > 0)
				PimpController.categorias.add("Bebidas");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}

	}

	
	public static void getIngredientes(String data) {

		try {
			JSONObject json = new JSONObject(data);
			
			/* Pega um array de contem objetos em Json */
			JSONArray array;
			array = json.getJSONObject("Cardapio").getJSONArray("Ingredientes");

			/*
			 * Pega o objeto do indice i e transforma ele em um Ingrediente
			 * Model, depois adiciona o ingrediente no Hash de ingredientes
			 */
			for (int i = 0; i < array.length(); i++) {

				// monta o objeto a partir do json
				IngredienteModel aux = new IngredienteModel(
						array.getJSONObject(i).get("Nome").toString(),
						Double.parseDouble(array.getJSONObject(i).get("Preco").toString()),
						false);

				/* Adiciona Ingrediente no hash (key: nome  value:objeto)*/
				PimpController.ingredientes.put(array.getJSONObject(i).get("Nome").toString(), aux);
				
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}

	}

	public static void getSabores(String data) {

		try {
			JSONObject json = new JSONObject(data);
			JSONArray array;
			array = json.getJSONObject("Cardapio").getJSONArray("Sabores");

			/*
			 * Pega as categorias da lista interna (estática) e separa os sabores por
			 * categoria
			 */
			for (int i = 0; i < PimpController.categorias.size(); i++) {
				
				PimpController.listSabores.put(
						PimpController.categorias.get(i),
						new TreeMap<String, SaborModel>());
				
				/*
				 * Pega o array de objetos Json (Sabores) e para cada elemento
				 * da lista, instancia um objeto SaborModel e o adiciona no Hash
				 * de acordo com a sua categoria
				 */
				for (int y = 0; y < array.length(); y++) {
									/*
					 * Verifica se a categoria atual (definida pelo for de index
					 * "i" é igual a categoria do sabor
					 */
					if (array.getJSONObject(y).get("Categoria").toString()
							.equals(PimpController.categorias.get(i))) {

						ArrayList<IngredienteModel> iAux = new ArrayList<IngredienteModel>();

						/******************************************
						/*
						 * Pega a lista de ingredientes dentro do objeto Json, e
						 * para cada nome de ingrediente na lista busca o seu
						 * objeto no hash "Ingredientes" e o adiciona na lista
						 * auxiliar de IngredienteModel
						 
						
						  for (int z = 0; z < array.getJSONObject(i)
						  .getJSONArray("Ingredientes").length(); z++) {
						  iAux.add(PimpController.ingredientes.get(array
						  .getJSONObject(i)
						  .getJSONArray("Ingredientes").get(z) .toString()));
						  
						  System.out.println("ingredienteSabor: " +
						  array.getJSONObject(i) .getJSONArray("Ingredientes")
						  .get(z).toString()); }
						 ****************************************************/

						

						try{
						List<String> ingredientes = Arrays.asList(array.getJSONObject(y).get("Ingredientes").toString().split("\\s*,\\s*")); 
						for(String ingrediente : ingredientes){

							IngredienteModel aux = PimpController.ingredientes.get(ingrediente);
							IngredienteModel aux2 = new IngredienteModel(aux.getNome(), aux.getPreco(), true);						
							iAux.add(aux2);
							System.out.println("Ingrediente: " + ingrediente);
						}
						}catch(Exception e){
							JSONArray ingredientes_array = array.getJSONObject(y).getJSONArray("Ingredientes");
							for(int u = 0; u < ingredientes_array.length(); u++){
								IngredienteModel aux = PimpController.ingredientes.get(ingredientes_array.get(u));
								IngredienteModel aux2 = new IngredienteModel(aux.getNome(), aux.getPreco(), true);						
								iAux.add(aux2);
							}
						}
						/*
						 * Monta o Objeto SaborModel e o adiciona no hash de
						 * acordo com a sua categoria P.s - Seria mais
						 * organizado fazer um metodo estático para adicionar o
						 * Sabor ao hash
						 */
						PimpController.listSabores.get(
								PimpController.categorias.get(i)).put(
								array.getJSONObject(y).get("Nome").toString(),
								new SaborModel(array.getJSONObject(y).get("Nome").toString(),
											array.getJSONObject(y).get("Categoria").toString(),
											iAux,
											Double.parseDouble(array.getJSONObject(y)
												.get("Preco").toString())));

					}
				}//

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
		}

	}

	public static void getBebidas(String data) {
		try {
			JSONObject json = new JSONObject(data);
			JSONArray array;
			array = json.getJSONObject("Cardapio").getJSONArray("Bebidas");

			for (int i = 0; i < array.length(); i++) {
				BebidaModel bebida = new BebidaModel(array.getJSONObject(i)
						.getString("Nome"), array.getJSONObject(i).getString(
						"Tamanho"), array.getJSONObject(i).getDouble("Preco"));
				PimpController.bebidas.add(bebida);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}
	}

	public static List<PizzaModel> getPizzasFavoritas(String data) {
		List<PizzaModel> list_pizzas = new ArrayList<PizzaModel>();
		try {
			JSONObject json = new JSONObject(data);
			JSONArray array;
			array = json.getJSONArray("pizzas");
			for (int i = 0; i < array.length(); i++) {
				PizzaModel pizza = new PizzaModel();
				pizza.setnPedacos(Integer.parseInt(array.getJSONObject(i)
						.getString("nPedacos")));
				pizza.setPreco(Double.parseDouble(array.getJSONObject(i)
						.getString("preco")));
				pizza.setTamanho(array.getJSONObject(i).getString("tamanho"));
				pizza.setQtd(Integer.parseInt(array.getJSONObject(i).getString(
						"quantidade")));
				pizza.setBorda(array.getJSONObject(i).getString("borda"));
				pizza.setSabores(getSaboresFavoritos(array.getJSONObject(i)
						.getJSONArray("sabores")));
				list_pizzas.add(pizza);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}
		return list_pizzas;
	}

	public static List<SaborModel> getSaboresFavoritos(JSONArray sabores) {
		List<SaborModel> list_sabores = new ArrayList<SaborModel>();
		try {
			for (int i = 0; i < sabores.length(); i++) {
				SaborModel sabor = new SaborModel();
				sabor.setNome(sabores.getJSONObject(i).getString("nome"));
				sabor.setCategoria(sabores.getJSONObject(i).getString(
						"categoria"));
				sabor.setIngredientes(getIngredientesFavoritos(sabores
						.getJSONObject(i).getJSONArray("ingredientes")));
				sabor.setIngredientesAdicionados(getIngredientesFavoritos(sabores
						.getJSONObject(i).getJSONArray("adicionados")));
				sabor.setIngredientesRetirados(getIngredientesFavoritos(sabores
						.getJSONObject(i).getJSONArray("retirados")));
				list_sabores.add(sabor);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}
		return list_sabores;
	}

	public static List<IngredienteModel> getIngredientesFavoritos(
			JSONArray ingredientes) {
		List<IngredienteModel> list_ingredientes = new ArrayList<IngredienteModel>();
		;
		try {
			for (int i = 0; i < ingredientes.length(); i++) {
				IngredienteModel ingrediente = new IngredienteModel(
						ingredientes.getJSONObject(i).getString("nome"),
						ingredientes.getJSONObject(i).getDouble("preco"), true);
				list_ingredientes.add(ingrediente);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("getCategoria", e.getMessage());
			
		}
		return list_ingredientes;
	}

	/****************************************************************************************************
	 * 
	 * Montagem de JSON
	 * 
	 ***************************************************************************************************/

	public static String PizzaToJson(PizzaModel pizza, String pizzas_favoritas) {
		JSONObject pizza_json = new JSONObject();
		JSONObject new_json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			JSONObject json = new JSONObject(pizzas_favoritas);
			jsonArray = json.getJSONArray("pizzas");
			pizza_json.put("nPedacos", pizza.getnPedacos());
			pizza_json.put("tamanho", pizza.getTamanho());
			pizza_json.put("borda", pizza.getBorda());
			pizza_json.put("quantidade", pizza.getQtd());
			pizza_json.put("preco", pizza.getPreco());
			pizza_json.put("sabores", SaboresToJson(pizza.getSabores()));
			jsonArray.put(pizza_json);
			new_json.put("pizzas", jsonArray);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new_json.toString();
	}

	public static JSONArray SaboresToJson(List<SaborModel> sabores) {
		JSONArray jsonArray = new JSONArray();
		try {
			for (SaborModel sabor : sabores) {
				JSONObject saborObj = new JSONObject();
				saborObj.put("nome", sabor.getNome());
				saborObj.put("categoria", sabor.getCategoria());
				saborObj.put("ingredientes",
						IngredientesToJson(sabor.getIngredientes()));
				saborObj.put("adicionados",
						IngredientesToJson(sabor.getIngredientesAdicionados()));
				saborObj.put("retirados",
						IngredientesToJson(sabor.getIngredientesRetirados()));
				jsonArray.put(saborObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public static JSONArray IngredientesToJson(List<IngredienteModel> list) {
		JSONArray jsonArray = new JSONArray();
		try {
			for (IngredienteModel ingrediente : list) {
				JSONObject ingObj = new JSONObject();
				ingObj.put("nome", ingrediente.getNome());
				ingObj.put("preco", ingrediente.getPreco());
				jsonArray.put(ingObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public static String criarPizzasFavoritas(PizzaModel pizza) {
		JSONObject pizza_json = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			pizza_json.put("nPedacos", pizza.getnPedacos());
			pizza_json.put("tamanho", pizza.getTamanho());
			pizza_json.put("borda", pizza.getBorda());
			pizza_json.put("quantidade", pizza.getQtd());
			pizza_json.put("preco", pizza.getPreco());
			pizza_json.put("sabores", SaboresToJson(pizza.getSabores()));
			array.put(pizza_json);
			json.put("pizzas", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public static String atualizarPizzasFavoritas(List<PizzaModel> pizzas) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			for (PizzaModel p : pizzas) {
				JSONObject pizza_json = new JSONObject();
				pizza_json.put("nPedacos", p.getnPedacos());
				pizza_json.put("tamanho", p.getTamanho());
				pizza_json.put("borda", p.getBorda());
				pizza_json.put("quantidade", p.getQtd());
				pizza_json.put("preco", p.getPreco());
				pizza_json.put("sabores", SaboresToJson(p.getSabores()));
				array.put(pizza_json);
			}
			json.put("pizzas", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public static String PedidoToJson(PedidoModel pedido, String nome,
			String endereco, String telefone, String unidade, String forma_pagamento, String troco) {
		JSONObject pedido_json = new JSONObject();
		try {
			System.out.println("Esta no try!!");
			/*String x = "";			
			for(PizzaModel aux: PimpController.pedido.getPizzas()){
				x += "pizza 1";
				System.out.println("Aqui: " +getNomePedidoteste(aux).toString());
			}
			
			JSONObject pizzas = new JSONObject();
			pizzas.put("nome", x);*/
			
			pedido_json.put("nome", nome);
			pedido_json.put("endereco", endereco);
			pedido_json.put("telefone", telefone);
			pedido_json.put("unidade", unidade);
			pedido_json.put("preco", pedido.getPreco());
			pedido_json.put("pizzas", PizzasToJson(pedido.getPizzas()));
			//pedido_json.put("Pizzas", pizzas);
			pedido_json.put("bebidas", BebidasToJson(pedido.getBebidas()));
			pedido_json.put("outros", OutrosToJson(pedido.getSobremesas()));
			pedido_json.put("date", getDate());
			pedido_json.put("time", getTime());
			pedido_json.put("pagamento", forma_pagamento);
			pedido_json.put("troco", troco);
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Pedido Json: " + pedido_json.toString());
		return pedido_json.toString();
	}

	public static JSONArray PizzasToJson(List<PizzaModel> pizzas) {
		JSONArray array = new JSONArray();
		for (PizzaModel pizza : pizzas) {
			JSONObject pizza_json = new JSONObject();
			try {
				pizza_json.put("nPedacos", pizza.getnPedacos());
				pizza_json.put("tamanho", pizza.getTamanho());
				pizza_json.put("borda", pizza.getBorda());
				pizza_json.put("quantidade", pizza.getQtd());
				pizza_json.put("preco", pizza.getPreco());
				pizza_json.put("sabores", SaboresToJson(pizza.getSabores()));
				array.put(pizza_json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return array;
	}

	public static JSONArray BebidasToJson(List<BebidaModel> bebidas) {
		JSONArray array = new JSONArray();
		for (BebidaModel b : bebidas) {
			JSONObject json = new JSONObject();
			try {
				json.put("nome", b.getNome());
				json.put("tamanho", b.getTamanho());
				json.put("quantidade", b.getQtd());
				array.put(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return array;
	}

	public static JSONArray OutrosToJson(List<SobremesaModel> sobremesas) {
		JSONArray array = new JSONArray();
		for (SobremesaModel s : sobremesas) {
			JSONObject json = new JSONObject();
			try {
				json.put("nome", s.getNome());
				json.put("descricao", s.getDescricao());
				json.put("quantidade", s.getQuantidade());
				array.put(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return array;
	}

	/****************************************************************************************************
	 * 
	 * Métodos Auxiliares
	 * 
	 ***************************************************************************************************/

	public static String getNomePedido(PizzaModel pa) {
		String result = "|";

		if (pa.getSabores().size() == 1) {
			result += pa.getSabores().get(0).getNome() + "|";
		} else {
			for (int i = 0; i < pa.getSabores().size(); i++) {
				result += pa.getSabores().get(i).getNome() + "|";
			}
		}

		return result;
	}

	public static String getNomePedidoteste(PizzaModel pa) {
		String result = "";
		for (int i = 0; i < pa.getSabores().size(); i++) {
			result += pa.getSabores().get(i).getNome();
			if (pa.getSabores().get(i).getIngredientesRetirados().size() > 0) {
				result += "\n  Retirar: ";
				for (IngredienteModel aux : pa.getSabores().get(i)
						.getIngredientesRetirados()) {
					result += aux.getNome() + ", ";
				}
			}
			if (pa.getSabores().get(i).getIngredientesAdicionados().size() > 0) {
				result += "\n  Adicionar: ";
				for (IngredienteModel aux : pa.getSabores().get(i)
						.getIngredientesAdicionados()) {
					result += aux.getNome() + ", ";
				}
			} else {
				result += ",\n";
			}
			result += "\n";

		}

		return result;
	}

	public static String getPedidoDesc(PizzaModel pa) {
		String result = "";

		result = "UNIDADES: " + pa.getQtd() + "\nTAMANHO: " + pa.getTamanho()
				+ "\nPEDAÇOS: " + pa.getnPedacos() + "\nBORDA: "
				+ pa.getBorda();

		return result;
	}
	
	public static boolean verificaPizzaExistente(List<SaborModel> pizza_sabores, List<SaborModel> sabores_cardapio){
		boolean ret = true;
		for(SaborModel sabor : pizza_sabores){
			for(SaborModel sabor_cardapio : sabores_cardapio){
				if(sabor.equals(sabor_cardapio)){
					ret = true;
					break;
				}else{
					ret = false;
				}
			}
		}
		return ret;
	}

	/****************************************************************************************************
	 * 
	 * Get Phonenumber
	 * 
	 ***************************************************************************************************/

	public static String getPhonenumber(Context context) {
		TelephonyManager tMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String telefone = "";
		if(tMgr.getLine1Number().length() < 9 && !tMgr.getLine1Number().equals("")){
			telefone = "9" + tMgr.getLine1Number();
		}else{
			telefone = tMgr.getLine1Number();
		}
		return telefone;
	}

	
	/****************************************************************************************************
	 * 
	 * Get Date/Time
	 * 
	 ***************************************************************************************************/
	public static String getDate(){
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DATE)) + String.valueOf(calendar.get(Calendar.MONTH)) + String.valueOf(calendar.get(Calendar.YEAR));
		
	}
	
	public static String getTime(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND); 
				
	}
	
	
	}


