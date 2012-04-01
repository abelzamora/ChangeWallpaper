package abel.wallpaper.menu;

import abel.wallpaper.change.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WallpaperActive extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);

		final CharSequence[] items = { "Activar", "Desactivar" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Activar/Desactivar");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int item) {
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				startActivity(new Intent(WallpaperActive.this, WallpaperMenu.class));
				dialogInterface.dismiss();
			}
		});
		builder.create().show();
	}
}
