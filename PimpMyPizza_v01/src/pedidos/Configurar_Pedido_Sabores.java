package pedidos;

import com.beta.pimpmypizza_beta.R;
import com.beta.pimpmypizza_beta.R.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class Configurar_Pedido_Sabores extends Fragment implements
		View.OnClickListener {

	Context context;
	Intent intent_Pedido;

	public Configurar_Pedido_Sabores(Context context, Intent intent) {
		// Required empty public constructor
		this.context = context;
		this.intent_Pedido = intent;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater
				.inflate(R.layout.fragment_configurar__pedido__sabores,
						container, false);

		Button btnSabor1 = (Button) v.findViewById(R.id.btnSabor1);
		Button btnSabor2 = (Button) v.findViewById(R.id.btnSabor2);
		Button btnSabor3 = (Button) v.findViewById(R.id.btnSabor3);
		Button btnSabor4 = (Button) v.findViewById(R.id.btnSabor4);
		Button btnProximoPasso = (Button) v.findViewById(R.id.btnProximo);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void btnProximoClicked(View view) {
		Intent intent = new Intent();
		intent.setClass(context, ConfigurarPedido.class);
		if (intent_Pedido.getIntExtra("acao", -1) > 0) {
			intent.putExtra("acao", 1);
		}
		startActivity(intent);

	}

}
