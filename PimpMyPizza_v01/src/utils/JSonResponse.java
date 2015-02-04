package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import pedidos.PimpController;

import Model.PedidoModel;
import Model.PizzaModel;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class JSonResponse {

	private static String Url01 = "http://hmkcode.appspot.com/jsonservlet";
	private static String Url02 = "http://192.168.1.103:8887/pedidos/";
	private static PedidoModel pedido;
	private static Context context;

	public JSonResponse(Context context) {
		this.context = context;
		this.Url02 += PimpController.usuario.getTelefone();
	}

	public void EnviarPedido(PedidoModel pedido) {

		JSonResponse.pedido = pedido;
		try {
			new HttpAsyncTask().execute(Url02).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String POST(PedidoModel pedido) {

		HttpURLConnection connection;
		OutputStreamWriter request = null;

		URL url = null;
		String response = "";
		String parameters = Helper.PedidoToJson(pedido,
				PimpController.usuario.getNome(),
				PimpController.usuario.getEnderecoEntrega(),
				PimpController.usuario.getTelefone(),
				PimpController.usuario.getUnidade_entrega(),
				PimpController.usuario.getForma_pagamento(),
				PimpController.usuario.getValor_troco());
		try {
			url = new URL(Url02);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");

			request = new OutputStreamWriter(connection.getOutputStream());
			request.write(parameters);
			request.flush();
			request.close();
			String line = "";
			InputStreamReader isr = new InputStreamReader(
					connection.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			// Response from server after login process will be stored in
			// response variable.
			response = sb.toString();
			// You can perform UI operations here

			isr.close();
			reader.close();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return response;

	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	/************************************************************************************************************************
	 * 
	 * Async Class
	 * 
	 ***********************************************************************************************************************/

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			System.out.println("Init HttpAsyncTask");
			return POST(JSonResponse.pedido);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Pedido enviado");
			Toast.makeText(context, "Pedido enviado com sucesso!",
					Toast.LENGTH_LONG).show();
		}
	}

}
