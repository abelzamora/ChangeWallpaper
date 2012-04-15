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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
		try {
			// Cargamos las imagenes desde la sdCard
			WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();
			Bundle bundle = getIntent().getExtras();
			paquete = bundle.getString("paquete");
			lPhotos = cwg.readSDCard(paquete);

			//Creamos el adaptador
			final ImageAdapter ia = new ImageAdapter(this, lPhotos);
			
			// Cargamos el checkBox
			final WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
			final CheckBox checkBox = (CheckBox) findViewById(R.id.ChkPaquete);
			// Obtenemos el paquete por defecto
			String paqueteXml = wdc.getPaquete();
			if (paqueteXml.equals(paquete)) {
				// El paquete es el seleccionado
				checkBox.setChecked(true);
				checkBox.setEnabled(false);
			}

			

			iButton = (ImageButton) findViewById(R.id.imagenBoton);

			gallery = (Gallery) findViewById(R.id.gallery);
			
			gallery.setAdapter(ia);

			// Pintamos la primera imagen al comienzo
			ia.setSelectImage(PRIMERA_POSICION);
			
			
			//Una vez cargada la galería, cargamos el checkbox
			checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					WallpaperXml wpx = new WallpaperXml();
					if (isChecked) {
						// Paquete seleccionado

						try {
							Toast.makeText(WallpaperSelect.this, "Establecemos: " + paquete, Toast.LENGTH_SHORT).show();
							wpx.writeXml(wdc.getActivado(), paquete, wdc.getFirsTime());
							ia.setWallpaper(0);
							checkBox.setText("Paquete wallpaper seleccionado");
						} catch (IOException e) {
							// Recogemos la excepcion
							e.printStackTrace();
						}

					} else {
						// Paquete no seleccionado
						try {
							Toast.makeText(WallpaperSelect.this, "Establecemos: puentes", Toast.LENGTH_SHORT).show();
							wpx.writeXml(wdc.getActivado(), "puentes", wdc.getFirsTime());
							checkBox.setText("Paquete wallpaper no seleccionado");
						} catch (IOException e) {
							// Recogemos la excepcion
							e.printStackTrace();
						}

					}
				}
			});

			gallery.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView parent, View v, int position, long id) {
					// Funcion para cambiar el fondo de pantalla
					ia.setSelectImage(position);
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		public void setWallpaper(int position) throws IOException {

			Bitmap bm = BitmapFactory.decodeFile(FileList.get(position).toString());
			miGestorDeFondos = WallpaperManager.getInstance(getApplicationContext());
			bm.prepareToDraw();
			bm.setDensity(BIND_AUTO_CREATE);
			miGestorDeFondos.setBitmap(bm);
		}

		public void setSelectImage(int position) {
			Bitmap bm = BitmapFactory.decodeFile(FileList.get(position).toString());
			Drawable d = new BitmapDrawable(bm);
			iButton.setBackgroundDrawable(d);
		}

	}

}
