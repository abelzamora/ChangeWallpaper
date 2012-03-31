package abel.wallpaper.menu;

import java.io.IOException;

import abel.wallpaper.change.R;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class WallpaperSelect extends Activity {
	private WallpaperManager miGestorDeFondos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		final ImageAdapter ia = new ImageAdapter(this);
		gallery.setAdapter(ia);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Toast.makeText(WallpaperSelect.this, "" + position, Toast.LENGTH_SHORT).show();
				try {
					ia.setWallpaper(position);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;

		private Integer[] mImageIds = { 
				R.drawable.sample_1, 
				R.drawable.sample_2, 
				R.drawable.sample_3, 
				R.drawable.sample_4, 
				R.drawable.sample_5, 
				R.drawable.sample_6, 
				R.drawable.sample_7 
				};

		public ImageAdapter(Context c) {
			mContext = c;
			TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0);
			attr.recycle();
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);

			imageView.setImageResource(mImageIds[position]);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setBackgroundResource(mGalleryItemBackground);

			return imageView;
		}
		
		public void setWallpaper(int position) throws IOException{
			
			miGestorDeFondos= WallpaperManager.getInstance(getApplicationContext());   
			miGestorDeFondos.setResource(mImageIds[position]);
		}
	}

}
