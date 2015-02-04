package com.beta.pimpmypizza_beta;

import java.util.Calendar;

import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;

import org.json.JSONObject;

import pedidos.Configurar_Pedido;
import pedidos.PimpController;
import shared_preferences.SharedPreferencesController;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConexaoRuim extends Activity {

	TextView tvMensagem;
	Button btnDenovo, btnVerCardapio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conexao_ruim);
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// String result =
		// "{\"Cardapio\": {\"Categorias\": [\"Salgada\", \"Doce\"], \"Sabores\": [{\"Categoria\": \"Salgada\", \"Nome\": \"Mussarela\", \"Preco\": \"30.00\", \"Ingredientes\": [\"Molho\", \"Mussarela\", \"Azeitona\"]}, {\"Categoria\": \"Salgada\", \"Nome\": \"Calabresa\", \"Preco\": \"30.00\", \"Ingredientes\": [\"Molho\", \"Calabresa\", \"Cebola\", \"Azeitona\"]}, {\"Categoria\": \"Doce\", \"Nome\": \"Chocolate\", \"Preco\": \"34.00\", \"Ingredientes\": [\"Chocolate Derretido\"]}], \"Bebidas\": [{\"Nome\": \"Coca Cola\", \"Tamanho\": \"1l\", \"Preco\": \"10.00\"}, {\"Nome\": \"Doly\", \"Tamanho\": \"3l\", \"Preco\": \"9.00\"}], \"Ingredientes\": [{\"Categoria\": \"queijo\", \"Nome\": \"Mussarela\", \"Preco\": \"2.50\"}, {\"Categoria\": \"padrao\", \"Nome\": \"Molho\", \"Preco\": \"2.50\"}, {\"Categoria\": \"carne\", \"Nome\": \"Azeitona\", \"Preco\": \"2.50\"}, {\"Categoria\": \"carne\", \"Nome\": \"Calabresa\", \"Preco\": \"2.50\"}, {\"Categoria\": \"carne\", \"Nome\": \"Cebola\", \"Preco\": \"2.50\"}, {\"Categoria\": \"carne\", \"Nome\": \"Chocolate Derretido\", \"Preco\": \"2.50\"}]}, \"Pizzaria\": {\"Horario_de_funcionamento\": \"8 da manha ao infinito\", \"Nome\": \"Pizzaria do Joao\", \"Localizacao\": \"Quebradas da ZL\", \"Descricao\": \"Pizza todo dia\"}}";

		// String result =
		// "{\"Pizzaria\": {\"Descricao\": \"Pizza todo dia\", \"Nome\": \"Pizzaria do Joao\", \"Localizacao\": \"Quebradas da ZL\", \"Horario_de_funcionamento\": \"8 da manha ao infinito\"},\"Cardapio\": {\"Categorias\": [\"mussarela\", \"calabresa\", \"Doce\"], \"Bebidas\": [{\"Tamanho\": \"1l\", \"Nome\": \"Coca Cola\", \"Preco\": \"10.00\"},{\"Tamanho\": \"3l\", \"Nome\": \"Doly\", \"Preco\": \"9.00\"}], \"Sabores\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Mussarela de Bufalo\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Catupiry\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Margherita\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana especial\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Dois Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Tres Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Quatro Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 1\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 2\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Toscana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Baiana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Banana\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Banana 1\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Brigadeiro\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"California\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Casadinha\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Romeu e Julieta\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"}],\"Ingredientes\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"queijo\", \"Preco\": \"2.50\"},{\"Nome\": \"Molho\", \"Categoria\": \"padrao\", \"Preco\": \"2.50\"}, {\"Nome\": \"Azeitona\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}, {\"Nome\": \"Cebola\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Chocolate Derretido\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}]}}";
		String result = "{'Pizzaria': {'Horario_de_funcionamento': '8 da manha ao infinito', 'Descricao': 'Pizza todo dia', 'Nome': 'Pizzaria do Joao', 'Localizacao': 'Quebradas da ZL'}, 'Cardapio': {'Ingredientes': [{'Nome': 'Mussarela', 'Preco': '2.50'}, {'Nome': 'Molho', 'Preco': '2.50'}, {'Nome': 'Azeitona', 'Preco': '2.50'}, {'Nome': 'Calabresa', 'Preco': '2.50'}, {'Nome': 'Cebola', 'Preco': '2.50'}, {'Nome': 'Chocolate Derretido', 'Preco': '2.50'}, {'Nome': 'Mussarela', 'Preco': '2.5'}, {'Nome': 'Calabresa', 'Preco': '2.5'}, {'Nome': 'Azeitona', 'Preco': '2.5'}, {'Nome': 'Parmesao', 'Preco': '2.0'}, {'Nome': 'Bacon', 'Preco': '2.0'}, {'Nome': 'Cebola', 'Preco': '1.0'}, {'Nome': 'Atum', 'Preco': '2.0'}, {'Nome': 'Beringela', 'Preco': '2.0'}, {'Nome': 'Brocolis', 'Preco': '2.0'}, {'Nome': 'Frutas', 'Preco': '3.0'}, {'Nome': 'Acucar', 'Preco': '2.0'}, {'Nome': 'Canela', 'Preco': '2.0'}, {'Nome': 'Pimenta', 'Preco': '1.0'}, {'Nome': 'Tomate', 'Preco': '2.0'}, {'Nome': 'Presunto', 'Preco': '2.0'}, {'Nome': 'Lombo', 'Preco': '3.0'}, {'Nome': 'Banana', 'Preco': '3.0'}, {'Nome': 'Leite Condensado', 'Preco': '3.0'}, {'Nome': 'Granulado', 'Preco': '3.0'}, {'Nome': 'Coco Ralado', 'Preco': '4.0'}, {'Nome': 'Frango', 'Preco': '3.0'}, {'Nome': 'Ervilha', 'Preco': '1.0'}, {'Nome': 'Milho', 'Preco': '1.0'}, {'Nome': 'Gorgonzola', 'Preco': '4.0'}, {'Nome': 'Catupiri', 'Preco': '4.0'}, {'Nome': 'Provolone', 'Preco': '4.0'}, {'Nome': 'Ovo', 'Preco': '3.0'}], 'Categorias': ['Calabresa', 'Atum', 'Mussarela', 'Vegetal', 'Doce', 'Frango'], 'Outros': [], 'Bebidas': [{'Nome': 'Coca Cola', 'Preco': '10.00', 'Tamanho': '1 Litro(s)'}, {'Nome': 'Doly', 'Preco': '9.00', 'Tamanho': '3 Litro(s)'}, {'Nome': 'Guarana', 'Preco': '4.0', 'Tamanho': '200 Mililitros'}, {'Nome': 'Skol - Lata', 'Preco': '3.0', 'Tamanho': '350 Mililitros'}, {'Nome': 'Suco de Laranja', 'Preco': '3.0', 'Tamanho': '200 Mililitros'}, {'Nome': 'Suco de Limao', 'Preco': '3.0', 'Tamanho': '200 Mililitros'}], 'Sabores': [{'Ingredientes': 'Calabresa, Mussarela', 'Categoria': 'Calabresa', 'Nome': 'Da Casa', 'Preco': '40.0'}, {'Ingredientes': 'Atum, Cebola', 'Categoria': 'Atum', 'Nome': 'Atum I', 'Preco': '25.0'}, {'Ingredientes': 'Atum, Cebola, Parmesao', 'Categoria': 'Atum', 'Nome': 'Atum II', 'Preco': '30.0'}, {'Ingredientes': 'Bacon, Mussarela', 'Categoria': 'Calabresa', 'Nome': 'Bacon', 'Preco': '25.0'}, {'Ingredientes': 'Calabresa, Pimenta, Parmesao, Tomate', 'Categoria': 'Calabresa', 'Nome': 'Baiana', 'Preco': '30.0'}, {'Ingredientes': 'Presunto, Tomate, Mussarela', 'Categoria': 'Mussarela', 'Nome': 'Bauru', 'Preco': '25.0'}, {'Ingredientes': 'Beringela, Molho, Mussarela', 'Categoria': 'Vegetal', 'Nome': 'Beringela', 'Preco': '30.0'}, {'Ingredientes': 'Brocolis, Mussarela', 'Categoria': 'Vegetal', 'Nome': 'Brocolis', 'Preco': '25.0'}, {'Ingredientes': 'Calabresa, Cebola', 'Categoria': 'Calabresa', 'Nome': 'Calabresa I', 'Preco': '25.0'}, {'Ingredientes': 'Calabresa, Parmesao, Mussarela', 'Categoria': 'Calabresa', 'Nome': 'Calabresa II', 'Preco': '25.0'}, {'Ingredientes': 'Lombo, Cebola, Mussarela', 'Categoria': 'Mussarela', 'Nome': 'Canadense', 'Preco': '30.0'}, {'Ingredientes': 'Presunto, Frutas, Mussarela', 'Categoria': 'Doce', 'Nome': 'California', 'Preco': '25.0'}, {'Ingredientes': 'Banana, Acucar, Canela, Leite Condensado', 'Categoria': 'Doce', 'Nome': 'Banana', 'Preco': '25.0'}, {'Ingredientes': 'Chocolate Derretido, Granulado', 'Categoria': 'Doce', 'Nome': 'Brigadeiro', 'Preco': '30.0'}, {'Ingredientes': 'Chocolate Derretido, Coco Ralado', 'Categoria': 'Doce', 'Nome': 'Prestigio', 'Preco': '30.0'}, {'Ingredientes': 'Frango, Ervilha, Milho, Mussarela', 'Categoria': 'Frango', 'Nome': 'Caipira', 'Preco': '30.0'}, {'Ingredientes': 'Gorgonzola, Catupiri, Provolone, Mussarela, Parmesao', 'Categoria': 'Mussarela', 'Nome': 'Cinco Queijos', 'Preco': '30.0'}, {'Ingredientes': 'Mussarela, Catupiri', 'Categoria': 'Mussarela', 'Nome': 'Dois Queijos', 'Preco': '22.0'}, {'Ingredientes': 'Parmesao, Provolone, Catupiri, Mussarela', 'Categoria': 'Mussarela', 'Nome': 'Quatro Queijos', 'Preco': '25.0'}, {'Ingredientes': 'Provolone, Catupiri, Parmesao', 'Categoria': 'Mussarela', 'Nome': 'Gratinada', 'Preco': '23.0'}, {'Ingredientes': ['Calabresa', 'Ovo', 'Mussarela'], 'Categoria': 'Calabresa', 'Nome': 'Toscana', 'Preco': '23.0'}]}}";
		SharedPreferencesController.save(Keys.Cardapio, result,

		// String result =
		// "{\"Pizzaria\": {\"Descricao\": \"Pizza todo dia\", \"Nome\": \"Pizzaria do Joao\", \"Localizacao\": \"Quebradas da ZL\", \"Horario_de_funcionamento\": \"8 da manha ao infinito\"},\"Cardapio\": {\"Categorias\": [\"mussarela\", \"calabresa\", \"Doce\"], \"Bebidas\": [{\"Tamanho\": \"1l\", \"Nome\": \"Coca Cola\", \"Preco\": \"10.00\"},{\"Tamanho\": \"3l\", \"Nome\": \"Doly\", \"Preco\": \"9.00\"}], \"Sabores\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Mussarela de Bufalo\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Catupiry\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Margherita\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Napolitana especial\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Dois Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Tres Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"}, {\"Nome\": \"Quatro Queijos\", \"Categoria\": \"mussarela\", \"Ingredientes\": \"Molho , Mussarela ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 1\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Calabresa 2\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Toscana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Baiana\", \"Categoria\": \"calabresa\", \"Ingredientes\": \"Molho , Calabresa, Cebola ,Azeitona\", \"Preco\": \"30.00\"},{\"Nome\": \"Banana\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Banana 1\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Brigadeiro\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"California\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Casadinha\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"},{\"Nome\": \"Romeu e Julieta\", \"Categoria\": \"Doce\", \"Ingredientes\": \"Chocolate Derretido\", \"Preco\": \"34.00\"}],\"Ingredientes\": [{\"Nome\": \"Mussarela\", \"Categoria\": \"queijo\", \"Preco\": \"2.50\"},{\"Nome\": \"Molho\", \"Categoria\": \"padrao\", \"Preco\": \"2.50\"}, {\"Nome\": \"Azeitona\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Calabresa\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}, {\"Nome\": \"Cebola\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"},{\"Nome\": \"Chocolate Derretido\", \"Categoria\": \"carne\", \"Preco\": \"2.50\"}]}}";

				sharedPreferences);
		if (SharedPreferencesController.load(Keys.Cardapio, sharedPreferences) == "") {
			System.out.println("Salvar no SP");
			SharedPreferencesController.save(Keys.Cardapio, result,
					sharedPreferences);
		}

		tvMensagem = (TextView) findViewById(R.id.tvRuim);
		btnDenovo = (Button) findViewById(R.id.btnTenteNovamente);
		Calendar c = Calendar.getInstance();
		tvMensagem.setText(getIntent().getStringExtra("message"));

	}
	
	public void Recarregar(View view) {
		/*
		 * Intent intent = new Intent(this, SplashScreen.class);
		 * startActivity(intent); this.finish();
		 */
		
		Intent intent = new Intent(this, Configurar_Pedido.class);
		startActivity(intent);
		this.finish();
	}

	public void verCardapioClicked(View view) {
		PimpController.isServerOff = true;
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("sucess", false);
		startActivity(intent);
		this.finish();
	}
}
