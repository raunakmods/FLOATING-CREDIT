package com.raunak.mods;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import android.view.animation.Animation;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.app.Service;
import android.widget.LinearLayout;
import android.content.Intent;
import android.os.IBinder;
import android.content.res.AssetManager;
import android.view.Gravity;
import android.graphics.Color;
import android.widget.ScrollView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import android.graphics.PixelFormat;
import android.view.MotionEvent;

public class RaunakMods extends Service {
    Drawable icon;
    private WindowManager mWindowManager;
    private View mFloatingView;
    private FrameLayout rootFrame;
    private RelativeLayout mRootContainer, mCollapsed;
    private LinearLayout patches, view1, view2, mExpandet, settings, mButtonPanel;
    private WindowManager.LayoutParams params;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initFloating();
    }

    private void initFloating() {

        AssetManager assetManager = getAssets();
        rootFrame = new FrameLayout(getBaseContext());
        mRootContainer = new RelativeLayout(getBaseContext());
        mCollapsed = new RelativeLayout(getBaseContext());
        mExpandet = new LinearLayout(getBaseContext());
        view1 = new LinearLayout(getBaseContext());
        patches = new LinearLayout(getBaseContext());
        view2 = new LinearLayout(getBaseContext());
        mButtonPanel = new LinearLayout(getBaseContext());

        FrameLayout.LayoutParams flayoutParams = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        rootFrame.setLayoutParams(flayoutParams);
        mRootContainer.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        mCollapsed.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        mCollapsed.setVisibility(View.VISIBLE);
        mCollapsed.setGravity(Gravity.CENTER);


        mExpandet.setVisibility(View.GONE);
        mExpandet.setBackgroundColor(Color.parseColor("#e90909"));
        mExpandet.setGravity(Gravity.CENTER);
        mExpandet.setOrientation(LinearLayout.VERTICAL);
        //mExpandet.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params_mExpandet = new LinearLayout.LayoutParams(350, 500);
        mExpandet.setLayoutParams(params_mExpandet);

        ScrollView scrollView = new ScrollView(getBaseContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 340));
        scrollView.setBackgroundColor(Color.parseColor("#FF000000"));

        view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 5));
        view1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        patches.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        patches.setOrientation(LinearLayout.VERTICAL);

        view2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 5));
        view2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        mButtonPanel.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        
        
        final TextView title_text = new TextView(this);
        title_text.setPadding(10, 5, 10, 5);
        title_text.setTextSize(15.0f);
        title_text.setText("𝙍𝘼𝙐𝙉𝘼𝙆 𝙈𝙊𝘿𝙨");
        title_text.setTextColor(Color.GREEN);
       
        title_text.setGravity(Gravity.TOP);
        GradientDrawable textBody = new GradientDrawable();
        textBody.setCornerRadius(0); //Set corner
        textBody.setStroke(4,(Color.RED));
        title_text.setBackground(textBody);


                     
    
        LinearLayout.LayoutParams title_Layout = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        title_Layout.gravity = Gravity.CENTER;
        title_text.setLayoutParams(title_Layout);
        
        rootFrame.addView(mRootContainer);
        mRootContainer.addView(mCollapsed);
        mCollapsed.addView(title_text);
        mFloatingView = rootFrame;

        //Add the view to the window.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        } else {
            params = new WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        }

        params.gravity = Gravity.CENTER | Gravity.TOP;
        params.x = 0;
        params.y = 50;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
        
        mFloatingView.setOnTouchListener(onTouchListener());
    }


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            final View collapsedView = mCollapsed;

            final View expandedView = mExpandet;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        initialX = params.x;
                        initialY = params.y;


                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        
                    case MotionEvent.ACTION_MOVE:

                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        return true;
                }
                return false;
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null)
            mWindowManager.removeView(mFloatingView);
    }
}

