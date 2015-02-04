package pedidos;

import com.beta.pimpmypizza_beta.R;














import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Configurar_Pedido extends FragmentActivity implements TabListener {

	ViewPager viewPager;
	ActionBar actionbar;
	private String[] tabNames = {"Sabores", "Preferências"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configurar__pedido);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), getIntent()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.show();
		
		for (int i = 0; i < tabNames.length; i++) {
			actionbar.addTab(actionbar.newTab().setText(tabNames[i]).setTabListener(this));
					

		}
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
		Log.d("TOTH", "position" + tab.getPosition());
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}

class FragmentAdapter extends FragmentPagerAdapter{

	Context context;
	Intent intent;
	public FragmentAdapter(FragmentManager fm, Context context, Intent intent) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.intent = intent;
		this.context = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		 if(arg0 == 0){
			 fragment = new Configurar_Pedido_Sabores(context, intent);
		 }
		 else if( arg0 == 1){
			 fragment = new Configurar_Pedido_Preferencias();
		 }
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
}
