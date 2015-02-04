package Model;

import java.util.ArrayList;
import java.util.List;

public class SaborModel {

	private String nome;
	private String categoria;
	private List<IngredienteModel> ingredientes;
	private ArrayList<IngredienteModel> ingredientesRetirados;
	private ArrayList<IngredienteModel> ingredientesAdicionados;
	private double preco;
	
	public SaborModel(){
		this.nome = null;
		this.categoria = null;
		this.ingredientes = null;
		this.ingredientesRetirados = null;
		this.ingredientesAdicionados = null;
		this.preco = 0.0;
	}
	
	public SaborModel(String nome, String categoria, List<IngredienteModel> ingredientes, double preco){
		this.nome = nome;
		this.categoria = categoria;
		this.ingredientes =  ingredientes;
		this.preco = preco;
		this.ingredientesRetirados = new ArrayList<IngredienteModel>();
		this.ingredientesAdicionados = new ArrayList<IngredienteModel>();
		
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
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<IngredienteModel> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteModel> ingredientes) {
		this.ingredientes = (ArrayList<IngredienteModel>) ingredientes;
	}

	public List<IngredienteModel> getIngredientesRetirados() {
		return ingredientesRetirados;
	}

	public void setIngredientesRetirados(List<IngredienteModel> ingredientesRetirados) {
		this.ingredientesRetirados = (ArrayList<IngredienteModel>) ingredientesRetirados;
	}

	public List<IngredienteModel> getIngredientesAdicionados() {
		return ingredientesAdicionados;
	}

	public void setIngredientesAdicionados(	List<IngredienteModel> ingredientesAdicionados) {
		this.ingredientesAdicionados = (ArrayList<IngredienteModel>) ingredientesAdicionados;
	}

	public double getPreco(){
		return preco;
	}
	
	public void setPreco(double preco){
		this.preco = preco;
	}
}
