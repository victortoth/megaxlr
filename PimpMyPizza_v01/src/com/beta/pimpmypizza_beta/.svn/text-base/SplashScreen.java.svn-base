package com.beta.pimpmypizza_beta;

import java.util.concurrent.ExecutionException;

import pedidos.PimpController;
import utils.Helper;
import utils.JSonRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashScreen extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 7000;
	static JSonRequest jsonrequest = null;// Recebe dados do servidor

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splash_screen);
				jsonrequest = new JSonRequest(getApplicationContext());
		if (Helper.isConnectedtoInternet(getApplicationContext())) {
			System.out.println("ta conectado");
			try {
				jsonrequest.execute();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("erro: +" +e.getMessage());
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				System.out.println("erro: +" +e.getMessage());
				Log.d("JSONREQUEST", e.getMessage());
			}

			// popular todos os arrays que pegam dados do servidor
		} else {
			Intent mainIntent = new Intent(SplashScreen.this, ConexaoRuim.class);
			mainIntent.putExtra("message",
					"O seu aparelho não está conectado, tente novamente");
			SplashScreen.this.startActivity(mainIntent);
			SplashScreen.this.finish();
		}

		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				// verificar se as listas estão preenchidas
				if (PimpController.categorias.size() == 0) {
					Intent mainIntent = new Intent(SplashScreen.this,
							ConexaoRuim.class);
					mainIntent
							.putExtra("message",
									"O servidor está com problemas, tente novamente mais tarde");
					
						SplashScreen.this.startActivity(mainIntent);
						SplashScreen.this.finish();
					

				} else {

					/* Create an Intent that will start the Menu-Activity. */
					Intent mainIntent = new Intent(SplashScreen.this,
							MainActivity.class);
						mainIntent.putExtra("sucess", true);
						SplashScreen.this.startActivity(mainIntent);
						SplashScreen.this.finish();
					
				}

			}
		}, SPLASH_DISPLAY_LENGHT);
	}
}
