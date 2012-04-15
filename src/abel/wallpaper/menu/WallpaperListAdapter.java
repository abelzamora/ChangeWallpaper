package abel.wallpaper.menu;

import java.util.Locale;

import abel.wallpaper.change.R;
import abel.wallpaper.global.WallpaperXml;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WallpaperListAdapter extends ArrayAdapter {

	private String[] listSdCard;
	
	@SuppressWarnings("unchecked")
	public WallpaperListAdapter(Context context, int textViewResourceId, String[] listSdCard) {
		super(context, textViewResourceId, listSdCard);
		this.listSdCard = listSdCard;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_pqt, null);
		}

		String lsc = listSdCard[position];
		String paquete =  new WallpaperXml().getDataConfig().getPaquete();

		if (lsc != null) {
			TextView art = (TextView) v.findViewById(R.id.label);
			ImageView img = (ImageView) v.findViewById(R.id.icon);
			if (art != null) {
				art.setText(lsc.toUpperCase(Locale.ENGLISH));
			}
			if (img != null) {
				if(paquete.equals(lsc))
				{
					//Paquete seleccionado
					img.setImageResource(R.drawable.ok);
				}
				else
				{
					//No seleccionado
					img.setImageResource(R.drawable.no);
				}
				
			}

		}

		return v;

	}

}
