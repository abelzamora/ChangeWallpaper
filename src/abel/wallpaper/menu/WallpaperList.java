package abel.wallpaper.menu;

import abel.wallpaper.change.ChangeWallpaperGlobales;
import abel.wallpaper.change.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WallpaperList<E> extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ChangeWallpaperGlobales<E> cwg = new ChangeWallpaperGlobales<E>();
		
		String[] listSdCard = cwg.listFile();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, listSdCard));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Selecciona una opción del menú
				startActivity(new Intent(WallpaperList.this, WallpaperSelect.class));
				
			}
		});
	}
}