package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beta.pimpmypizza_beta.ConexaoRuim;
import com.beta.pimpmypizza_beta.MainActivity;
import com.beta.pimpmypizza_beta.SplashScreen;

import pedidos.PimpController;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import Model.IngredienteModel;
import Model.SaborModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.util.Log;

public class JSonRequest {

	private String url01 = "http://api.androidhive.info/contacts/";


	 //private String url02 = "http://10.0.0.105:8888/cardapio";
	private String url02 = "http://192.168.1.104:8888/cardapio";


	private ArrayList<String> teste = new ArrayList<String>();
	private String result = "";
	HttpAsyncTask at;
	private SharedPreferences sharedPreferences;
	private Context context;
	private boolean status = false;

	public JSonRequest(Context context) {
		this.context = context;
		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this.context);
	}

	public void execute() throws InterruptedException, ExecutionException {
		at = new HttpAsyncTask();
		at.execute(url02);

	}

	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			
			// convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			System.out.println("InputStream: " + e.getLocalizedMessage());
		}

		return result;
	}

	public static String GET02(String end) {
		// InputStream inputStream = null;
		String result = "";
		try {

			URL url = new URL(end);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			
			try {
				InputStream in = new BufferedInputStream(
						urlConnection.getInputStream());
				if (in != null){
					result = convertInputStreamToString(in);
				System.out.println("Result " + result);
				}
				else
					result = "Did not work!";

			} catch (Exception e) {
				System.out.println("ERRO GET02: " + e.getLocalizedMessage());
			} finally {
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			System.out.println("InputStrem: " + e.getMessage());
		}

		return result;
	}

	// convert inputstream to String
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

	/***********************************************************************************************************************
	 * 
	 * Metodos Auxiliares
	 * 
	 ***********************************************************************************************************************/

	public boolean getStatus() {
		return this.status;
	}

	public void asyncTaskFinished() {
		this.status = true;
	}

	/*********************************************************************************************************************
	 * 
	 * Async Class
	 * 
	 **********************************************************************************************************************/
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			return GET02(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// chamar os metodos de tratamento
			Helper.getBebidas(result);
			Helper.getCategorias(result);
			Helper.getIngredientes(result);
			Helper.getSabores(result);
			if (result != "" && result != SharedPreferencesController.load(Keys.Cardapio, sharedPreferences)) {
				SharedPreferencesController.save(Keys.Cardapio, result,
						sharedPreferences);
				
			}
		}
	}

	/***********************************************************************************************************************
	 * 
	 * Getters/Setters
	 * 
	 ***********************************************************************************************************************/
	public List<String> getTeste() {
		return this.teste;
	}

}
