package tests;

import java.util.ArrayList;
import java.util.List;

import pedidos.PimpController;

import utils.Helper;
import Model.PizzaModel;
import android.content.Context;
import android.location.LocationManager;
import android.test.AndroidTestCase;
import junit.framework.*;

public class HelperTest extends AndroidTestCase{

	private String phonenumber = "94272653";
	private List<PizzaModel> list_pizzas = new ArrayList<PizzaModel>();
	private boolean isConnectedToInternet = true;
	private String result = "{\"Pizzaria\": {\"Descricao\": \"Pizza todo dia\", \"Nome\": \"Pizzaria do Joao\", \"Localizacao\": \"Quebradas da ZL\", \"Horario_de_funcionamento\": \"8 da manha ao infinito\"},\"Cardapio\": {\"Categorias\": [\"mussarela\", \"calabresa\", \"Doce\"], \"Bebidas\": [{\"Tamanho\": \"1l\", \"Nome\": \"Coca Cola\", \"Preco\": \"10.00\"},{\"Tamanho\": \"3l\", \"Nome\": \"Doly\", \"Preco\": \"9.00\"}], \"Sabores\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Mussarela de Bufalo\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Catupiry\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Margherita\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana especial\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Dois Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Tres Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Quatro Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 1\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 2\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Toscana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Baiana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Banana\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Banana 1\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Brigadeiro\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"California\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Casadinha\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Romeu e Julieta\", \"Categoria\": \"Doce\", \"Ingredientes\": [\"Chocolate Derretido\"], \"Preco\": \"34.00\"}],\"Ingredientes\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"queijo\", \"Preco\": \"2.50\"},{\"Nome\": \"Molho\", \"Categoria\": \"padrao\", \"Preco\": \"2.50\"}, {\"Nome\": \"Azeitona\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}, {\"Nome\": \"Cebola\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Chocolate Derretido\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}]}}";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	
			}
	
	public void testGetPhonenumber(){
		assertEquals(phonenumber, Helper.getPhonenumber(getContext()));
	}
	
	public void testGetCategorias(){
		Helper.getCategorias(result);
		assertNotNull(PimpController.categorias);
	}
	
	public void testGetIngredientes(){
		Helper.getIngredientes(result);
		assertNotNull(PimpController.ingredientes);
	}
	public void testGetSabores(){
		Helper.getSabores(result);
		assertNotNull(PimpController.listSabores);
	}
	
	public void testGetBebidas(){
		Helper.getBebidas(result);
		assertNotNull(PimpController.bebidas);
	}
	
	public void testIsConnectedToInternet(){
		assertEquals(isConnectedToInternet, Helper.isConnectedtoInternet(getContext()));
	}
	
}
