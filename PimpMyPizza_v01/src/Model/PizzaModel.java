package Model;

import java.util.ArrayList;
import java.util.List;

import pedidos.PimpController;

public class PizzaModel {

	private ArrayList<SaborModel> sabores;
	private int nPedacos;
	private String tamanho;
	private String borda;
	private int qtd;
	private double preco;

	
	public PizzaModel() {
		this.sabores = new ArrayList<SaborModel>();
		this.nPedacos = 0;
		this.tamanho = "";
		this.borda = "";
		this.qtd = 1;
		this.preco = 00.00;
	}

	public PizzaModel(SaborModel sabor) {
		this.sabores.add(sabor);
		this.calcularPreco();
	}

	/********************************************************************************************************************
	 * 
	 * Metodos Auxiliares
	 * 
	 ********************************************************************************************************************/

	public void AddSabor(SaborModel sabor) {
		this.sabores.add(sabor);
		this.calcularPreco();
		System.out.println("Pizza Model Pre�o: " + preco);
	}

	public String getPedidoDesc() {
		String result = "";

		result = "UNIDADES: " + qtd + "\nTAMANHO: " + tamanho + "\nPEDA�OS: "
				+ nPedacos + "\nBORDA: " + borda;

		System.out.println(result);
		return result;
	}

	public void calcularPreco() {
		this.preco = 0;
		double pAux = 0.0;

		
			for (SaborModel aux : sabores) {
				if (aux.getPreco() > pAux) {
					pAux = aux.getPreco();
				}
			}
				this.preco = pAux;			
		
		
		for(SaborModel aux : sabores){
			
			for(IngredienteModel aux2: aux.getIngredientesAdicionados()){
				this.preco += aux2.getPreco();
			}
			
		}
		
		this.preco = this.preco * this.getQtd();

	}

	/********************************************************************************************************************
	 * 
	 * GETTERS/SETTERS
	 * 
	 ********************************************************************************************************************/

	public List<SaborModel> getSabores() {
		return sabores;
	}

	public void setSabores(List<SaborModel> sabores) {
		this.sabores = (ArrayList<SaborModel>) sabores;
	}

	public int getnPedacos() {
		return nPedacos;
	}

	public void setnPedacos(int nPedacos) {
		this.nPedacos = nPedacos;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(CharSequence charSequence) {
		this.tamanho = (String) charSequence;
	}

	public String getBorda() {
		return borda;
	}

	public void setBorda(String borda) {
		this.borda = borda;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
		this.preco *= qtd;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getNomePedido() {
		String result = "|";

		if (sabores.size() == 1) {
			result += sabores.get(0).getNome() + "|";
		} else {
			for (int i = 0; i < sabores.size(); i++) {
				result += sabores.get(i).getNome() + "|";
			}
		}

		return result;
	}

	

}
