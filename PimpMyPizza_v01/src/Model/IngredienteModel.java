package Model;

public class IngredienteModel {

	private String nome;
	private boolean selecionado;
	private double preco;

	public IngredienteModel(String nome, double preco, boolean selecionado){
		this.nome = nome;
		this.preco = preco;
		this.selecionado = selecionado;
	}
	

/****************************************************************************************************************
* 
* 											GETTERS / SETTERS
* 
******************************************************************************************************************/

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

}
