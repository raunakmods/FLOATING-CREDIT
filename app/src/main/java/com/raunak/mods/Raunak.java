package com.raunak.mods;
 
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.provider.Settings;
import android.os.Build;
import android.widget.Toast;
import android.os.Handler;
import android.net.Uri;

public class Raunak extends Activity { 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, RaunakMods.class));
		Start(this);
        setContentView(R.layout.activity_main);
    }
	

    public static void Start(final Context context) {
        //Check if overlay permission is enabled or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            Toast.makeText(context.getApplicationContext(), "Overlay permission is required in order to show mod menu. Restart the game after you allow permission", Toast.LENGTH_LONG).show();
            Toast.makeText(context.getApplicationContext(), "Overlay permission is required in order to show mod menu. Restart the game after you allow permission", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
                                             Uri.parse("package:" + context.getPackageName())));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(1);
                    }
                }, 30);
            return;
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.startService(new Intent(context, RaunakMods.class));
                    }
                }, 50);
        }
    }
}

