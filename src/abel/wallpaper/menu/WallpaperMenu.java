package abel.wallpaper.menu;

import abel.wallpaper.change.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WallpaperMenu extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] menuPrincipal = getResources().getStringArray(R.array.menu_princial);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menuPrincipal));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Selecciona una opción del menú
				selectItem(position);
				
			}
		});
	}
	
	/*
	 * Permite conocer que opción de la lista se ha seleccionado
	 */
	private void selectItem(int position)
	{
		switch(position)
		{
		case 0:
			//Mostramos ventana emergente, para Activar/Desactivar wallpaper
			startActivity(new Intent(WallpaperMenu.this, WallpaperActive.class));
			break;
			
		case 1:
			//Redirige al menu para obtener más wallpapers
			startActivity(new Intent(WallpaperMenu.this, WallpaperList.class));
			break;
		
		case 2:
			//Redirige al menu para seleccionar el origen del wallpaper
			startActivity(new Intent(WallpaperMenu.this, WallpaperSelectOrigin.class));
			break;
		
		default:
			Toast.makeText(getApplicationContext(), "Opción no válida", Toast.LENGTH_SHORT).show();
				
		}
		
	}
	
}