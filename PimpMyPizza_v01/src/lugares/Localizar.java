package lugares;

import java.io.IOException;
import java.util.List;

import pedidos.ConfigurarPedido;
import pedidos.PimpController;
import shared_preferences.Keys;
import shared_preferences.SharedPreferencesController;
import utils.GPSService;
import utils.Helper;
import Model.UnidadeModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.pimpmypizza_beta.R;

public class Localizar extends Fragment {

	private Context context;
	TextView txtEndereco;

	private GPSService gps;
	private UnidadeModel unidade = null;

	private SharedPreferences sharedPreferences;

	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = inflater.inflate(R.layout.lugares, container, false);
		this.context = view.getContext();

		this.sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		final Geocoder geocoder = new Geocoder(view.getContext());

		txtEndereco = (TextView) view.findViewById(R.id.txtEndereco);
		gps = new GPSService(context);
		if (gps.canGetLocation()) {
			Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show();
		}
		Button btnLocalizar = (Button) view.findViewById(R.id.btnLocalizar);
		final Button btnEscolher = (Button) view.findViewById(R.id.btnEscolher);
		final Button btnVerMapa = (Button) view.findViewById(R.id.btnVerMapa);
		
		btnLocalizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Helper.isConnectedtoInternet(context)) {
					if (gps.canGetLocation()) 	{
						List<Address> addresses = null;
						try {
							try {
								addresses = geocoder.getFromLocation(
										gps.getLatitude(), gps.getLongitude(),
										1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							unidade = retornarUnidadeProxima();
							txtEndereco.setText("Unidade Mais Próxima: "
									+ unidade.getNomeUnidade());
							
							btnEscolher.setVisibility(View.VISIBLE);
							btnVerMapa.setVisibility(View.VISIBLE);
							//System.out.println("Latitude: " + gps.getLatitude() + "\n Longitude: " + gps.getLongitude());
						} catch (IndexOutOfBoundsException e) {
							Toast.makeText(context,
									"Ocorreu um erro com seu GPS",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(context, "O GPS deve estar ligado",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(context,
							"O aparelho deve estar conectado a Internet",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		btnEscolher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Unidade escolhida com sucesso!",
						Toast.LENGTH_LONG).show();
				SharedPreferencesController.save(Keys.UnidadeEntrega,
						unidade.getNomeUnidade(), sharedPreferences);
				Intent intent = new Intent();
				intent.setClass(getActivity(), ConfigurarPedido.class);
				startActivityForResult(intent, 100);
			}
		});

		btnVerMapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse("geo:" + unidade.getLatitude() + ","
						+ unidade.getLongitude()));
				Intent chooser = Intent.createChooser(intent, "Launch Maps");
				startActivity(chooser);

			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onDetach() {
		super.onDetach();

	}

	public UnidadeModel retornarUnidadeProxima() {
		UnidadeModel unidade = new UnidadeModel();
		double distancia = 999999;
		for (UnidadeModel u : Lugares.unidades) {
			if (distancia > Math.sqrt(Math.pow(
					(gps.getLatitude() - u.getLatitude()), 2)
					+ Math.pow(gps.getLongitude() - u.getLongitude(), 2))) {
				distancia = Math.sqrt(Math.pow(
						(gps.getLatitude() - u.getLatitude()), 2)
						+ Math.pow(gps.getLongitude() - u.getLongitude(), 2));
				unidade = u;
			}
		}
		return unidade;
	}
}
