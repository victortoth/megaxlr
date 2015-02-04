package pedidos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import Model.BebidaModel;
import Model.CarrinhoModel;
import Model.IngredienteModel;
import Model.PedidoModel;
import Model.PizzaModel;
import Model.SaborModel;
import Model.UsuarioModel;

public class PimpController {
	
	public static TreeMap<String, IngredienteModel> ingredientes = new TreeMap<String, IngredienteModel>();
	public static ArrayList<BebidaModel> bebidas = new ArrayList<BebidaModel>();
	public static TreeMap<String, TreeMap<String, SaborModel>> listSabores = new TreeMap<String, TreeMap<String, SaborModel>>();
	
	public static ArrayList<String> categorias = new ArrayList<String>();
	public static ArrayList<PizzaModel> listaPedidos = new ArrayList<PizzaModel>();
	public static ArrayList<PizzaModel> lista_favoritos = new ArrayList<PizzaModel>();
	
	public static PizzaModel pizzaAtual = null;
	public static PedidoModel pedido = new PedidoModel();
	public static UsuarioModel usuario = new UsuarioModel();
	
	public static ArrayList<CarrinhoModel> carrinhoList = new ArrayList<CarrinhoModel>(); 
	
	public static int id = -1;
	public static int saborAtual = 0;
	public static boolean isServerOff = false;
	
	/************************************************************************************************************
	 * 
	 * Metodos Auxiliares
	 * 
	 ***********************************************************************************************************/

	public static void AddSaborPizzaAtual(SaborModel sabor){
		ArrayList<IngredienteModel> iAuxList = new ArrayList<IngredienteModel>();
		for (IngredienteModel x: sabor.getIngredientes()) {
			IngredienteModel iaux2 = new IngredienteModel(
					x.getNome(), x.getPreco(), true);
			iAuxList.add(iaux2);
		}
		
		SaborModel aux2 = new SaborModel(sabor.getNome(),
				sabor.getCategoria(), iAuxList,
				sabor.getPreco());
		
		
		pizzaAtual.AddSabor(aux2);
	}
	public static void AddPizzaPedido(PizzaModel pizza){
		pedido.AddPizza(pizza);
	}
	public static void AddBebidaPedido(BebidaModel bebida){
		pedido.AddBebida(bebida);
	}
	public static void AddSobreMesaPedido(PizzaModel pizza){
		pedido.AddPizza(pizza);
	}
		
	public static void RemoveBebidaPedido(BebidaModel bebida){
		pedido.RemoveBebida(bebida);
	}
	
	public static void RemoveFromCarrinhoList(int position){
		carrinhoList.remove(position);
	}
}
