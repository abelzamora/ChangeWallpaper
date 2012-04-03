package abel.wallpaper.menu;

import java.io.IOException;
import java.util.List;

import abel.wallpaper.change.R;
import abel.wallpaper.global.WallpaperDataConfig;
import abel.wallpaper.global.WallpaperDataName;
import abel.wallpaper.global.WallpaperGlobales;
import abel.wallpaper.global.WallpaperXml;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class WallpaperSelect<E> extends Activity {
	private static final int PRIMERA_POSICION = 0;
	private WallpaperManager miGestorDeFondos;
	private Gallery gallery;
	private ImageButton iButton;
	private List<String> lPhotos;
	private String paquete;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		// Cargamos las imagenes desde la sdCard
		WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();
		Bundle bundle = getIntent().getExtras();
		paquete = bundle.getString("paquete");
		lPhotos = cwg.readSDCard(paquete);

		iButton = (ImageButton) findViewById(R.id.imagenBoton);

		gallery = (Gallery) findViewById(R.id.gallery);
		final ImageAdapter ia = new ImageAdapter(this, lPhotos);
		gallery.setAdapter(ia);

		// Pintamos la primera imagen al comienzo
		ia.setSelectImage(PRIMERA_POSICION);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				// Pinta mensaje con la posicion
				Toast.makeText(WallpaperSelect.this, "" + position, Toast.LENGTH_SHORT).show();

				// Funcion para cambiar el fondo de pantalla
				ia.setSelectImage(position);
			}
		});

	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private List<String> FileList;

		public ImageAdapter(Context c, List<String> fList) {
			mContext = c;
			FileList = fList;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return FileList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);

			Bitmap bm = BitmapFactory.decodeFile(FileList.get(position).toString());
			imageView.setImageBitmap(bm);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setBackgroundResource(mGalleryItemBackground);

			return imageView;
		}

		public void setWallpaper(Bitmap wallpaper) throws IOException {

			miGestorDeFondos = WallpaperManager.getInstance(getApplicationContext());
			miGestorDeFondos.setBitmap(wallpaper);
		}

		public void setSelectImage(int position) {
			Bitmap bm = BitmapFactory.decodeFile(FileList.get(position).toString());
			Drawable d = new BitmapDrawable(bm);
			iButton.setBackgroundDrawable(d);
		}

		/*
		 * Menu para añadir el token del usuario
		 * 
		 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
		 */
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.usermenu, menu);
			return true;
		}

		// This method is called once the menu is selected
		public boolean onOptionsItemSelected(MenuItem item) throws IllegalArgumentException, IllegalStateException, IOException {
			
			switch (item.getItemId()) {
			// We have only one menu option
			
			case R.id.seleccionar:
				// Seleecionamos la opcion del usuario
				WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
				//Establecemos el paquete que ha seleccionado el usuario
				
				
				WallpaperXml wpx = new WallpaperXml();
				wpx.writeXml(wdc.getActivado(), paquete, wdc.getFirsTime());
				
				break;

			}
			return true;
		}

	}

}
