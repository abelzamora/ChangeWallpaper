package abel.wallpaper.menu;

import abel.wallpaper.change.R;
import abel.wallpaper.global.WallpaperDataName;
import abel.wallpaper.global.WallpaperGlobales;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WallpaperList<E, T> extends ListActivity {
	private ArrayAdapter<T> adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();
		
		final String[] listSdCard = cwg.listFile();
		adapter = new WallpaperListAdapter(this, R.layout.list_pqt, listSdCard);
		setListAdapter(adapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Selecciona una opción del menú
				// Esto para realizar la llamada
				Intent i=new Intent(WallpaperList.this, WallpaperSelect.class);
				i.putExtra("paquete", listSdCard[position]);
				startActivity(i); 
				
			}
		});
		
	}
	
	@Override
	public void onResume()
	{
	         super.onResume();
	         adapter.notifyDataSetChanged();
	 }
	
}