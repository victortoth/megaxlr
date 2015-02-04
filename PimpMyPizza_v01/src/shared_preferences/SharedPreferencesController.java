package shared_preferences;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import java.util.StringTokenizer;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesController {

	// -----------------------------------------------
	// Shared Preferences
	// -----------------------------------------------

	public static void save(String key, String value,
			SharedPreferences sharedPreferences) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();

	}

	public static void save(String key, List<String> values,
			SharedPreferences sharedPreferences) {
		Editor editor = sharedPreferences.edit();
		StringBuilder str = new StringBuilder();
		for (String value : values) {
			str.append(value).append(",");
		}
		System.out.println("String salva: " + str.toString());
		editor.putString(key, str.toString());
		editor.commit();
	}

	public static void save(String key, boolean value,
			SharedPreferences sharedPreferences) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();

	}
	public static String load(String key, SharedPreferences sharedPreferences) {
		try {
			return sharedPreferences.getString(key, "");
		} catch (Exception e) {
			return "";
		}
	}

	public static List<String> loadList(String key,
			SharedPreferences sharedPreferences) {
		List<String> list = new ArrayList<String>();
		try {
			String savedString = sharedPreferences.getString(key, "");
			StringTokenizer st = new StringTokenizer(savedString, ",");
			while (st.hasMoreTokens()){
				list.add(st.nextToken());
			}
			return list;
		} catch (Exception e) {
			return list;
		}
	}
}
