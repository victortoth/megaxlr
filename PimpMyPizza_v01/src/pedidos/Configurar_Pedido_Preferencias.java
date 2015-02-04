package pedidos;

import com.beta.pimpmypizza_beta.R;
import com.beta.pimpmypizza_beta.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class Configurar_Pedido_Preferencias extends Fragment {

	public Configurar_Pedido_Preferencias() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(
				R.layout.fragment_configurar__pedido__preferencias, container,
				false);
	}

}
