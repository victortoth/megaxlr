package Model;

import java.util.ArrayList;
import java.util.List;

public class PedidoModel {

	private ArrayList<PizzaModel> pizzas = new ArrayList<PizzaModel>();
	private ArrayList<BebidaModel> bebidas = new ArrayList<BebidaModel>();
	private ArrayList<SobremesaModel> sobremesas = new ArrayList<SobremesaModel>();
	private ArrayList<Object> todos_itens = new ArrayList<Object>();
	public double preco = 0.0;

	public void Pedido() {
		this.pizzas = null;
		this.bebidas = null;
		this.sobremesas = null;
	}

	/******************************************************************************************************************
	 * 
	 * Metodos Auxiliares
	 * 
	 *******************************************************************************************************************/

	public void AddPizza(PizzaModel pizza) {
		this.pizzas.add(pizza);
		this.todos_itens.add(pizza);
		//this.preco += pizza.getPreco();
	}

	public void AddBebida(BebidaModel bebida) {
		this.bebidas.add(bebida);
		this.todos_itens.add(bebida);
		//this.preco += bebida.getPreco() * bebida.getQtd();
	}

	public void AddSobreMesa(SobremesaModel sobremesa) {
		this.sobremesas.add(sobremesa);
		this.todos_itens.add(sobremesa);
		//this.preco += sobremesa.getPreco();
	}

	public void RemovePizza(PizzaModel pizza) {
		this.pizzas.remove(pizza);
		this.todos_itens.remove(pizza);
		//this.preco -= pizza.getPreco();
	}

	public void RemoveBebida(BebidaModel bebida) {
		this.bebidas.remove(bebida);
		this.todos_itens.remove(bebida);
		//this.preco -= bebida.getPreco() * bebida.getQtd();
	}

	public void RemoveSobremesa(SobremesaModel sobremesa) {
		this.sobremesas.remove(sobremesa);
		this.todos_itens.remove(sobremesa);
		//this.preco -= sobremesa.getPreco();
	}

	public PizzaModel GetPizzaPedido(int position) {
		if (position < pizzas.size())
			return pizzas.get(position);

		return null;
	}

	public void SetPizzaPedido(int position, PizzaModel pizza) {
		if (this.pizzas.size() != 0) {
			this.pizzas.remove(position);
			this.pizzas.add(position, pizza);
		} else {
			this.pizzas.add(pizza);
		}

	}

	public String calcularPrecoPedido(){
		this.preco = 0;
		for(PizzaModel aux : this.pizzas){
			this.preco += aux.getPreco();
		}
		for(BebidaModel aux : this.bebidas){
			this.preco += aux.getPreco();
		}
		for(SobremesaModel aux : this.sobremesas){
			this.preco += aux.getPreco();
		}
		
		return String.valueOf(this.preco);
	}
	
	/******************************************************************************************************************
	 * 
	 * GETTERS SETTERS
	 * 
	 *******************************************************************************************************************/

	public List<PizzaModel> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<PizzaModel> pizzas) {
		this.pizzas = (ArrayList<PizzaModel>) pizzas;
	}

	public List<BebidaModel> getBebidas() {
		return bebidas;
	}

	public void setBebidas(List<BebidaModel> bebidas) {
		this.bebidas = (ArrayList<BebidaModel>) bebidas;
	}

	public List<SobremesaModel> getSobremesas() {
		return sobremesas;
	}

	public void setSobremesas(List<SobremesaModel> sobremesas) {
		this.sobremesas = (ArrayList<SobremesaModel>) sobremesas;
	}

	public List<Object> getTodosItens() {
		return todos_itens;
	}

	public void setTodosItens(List<Object> data) {
		this.todos_itens = (ArrayList<Object>) data;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	

}
