package Model;

public class BebidaModel {

	private String nome;
	private String tamanho;
	private int Qtd;
	private double preco;
	
	public BebidaModel(){
		this.nome = "";
		this.tamanho = "";
		this.Qtd = 1;
	}
	
	public BebidaModel(String nome, String tamanho, double preco){
		this.nome = nome;
		this.tamanho = tamanho;
		this.preco = preco;
		this.Qtd = 1;
	}
	
	public BebidaModel (String nome, String tamanho, int qtd){
		this.nome = nome;
		this.tamanho = tamanho;
		if(qtd == 0)
			this.Qtd = 1;
		else
			this.Qtd = qtd;
	}
	
/********************************************************************************************
 * 
 * 									GETTERS / SETTERS
 * 	
 ********************************************************************************************/
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public int getQtd() {
		return Qtd;
	}
	public void setQtd(int qtd) {
		this.Qtd = qtd;
	}

	public double getPreco() {
		return preco * this.Qtd;	
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
}
