package com.bentekkie.chase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;



public class FirstActivity
extends Activity
implements View.OnClickListener {

	public static List<Button> buttons;
	public TableLayout layout;
	final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	int sound;

@SuppressWarnings("deprecation")
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    layout = new TableLayout (this);
    layout.setLayoutParams( new TableLayout.LayoutParams(4,5) );
    buttons = new ArrayList<Button>();
    layout.setPadding(1,1,1,1);
    FrameLayout frame = new FrameLayout(this);
    ImageView backround = new ImageView(this);
    backround.setBackgroundColor(Color.RED);
    frame.addView(backround);
    Button start = new Button(this);
    start.setTag("2");
    start.setText("START");
    start.setTextColor(Color.WHITE);
    start.setOnClickListener(this);
    start.setGravity(Gravity.CENTER);
	DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    frame.addView(start, (metrics.widthPixels/4),((metrics.heightPixels)/6));
	sound = soundPool.load(this, R.raw.mario, 1);

    for (int f=0; f<=5; f++) {
        TableRow tr = new TableRow(this);
        for (int c=0; c<=3; c++) {
        	final int c1 = c;
        	final int f1 = f;
        	final Button button = new Button (this);
        	button.setBackgroundColor(Color.rgb( 128, 128, 128));
        	button.setTag("0");
        	button.setOnClickListener(this);
        	metrics = this.getResources().getDisplayMetrics();
            tr.addView(button, (metrics.widthPixels/4),((metrics.heightPixels)/6));
            buttons.add(button);
        }
        layout.addView(tr);
    } // for
    ((Button) buttons.get(1)).setTag("1");
    ((Button) buttons.get(1)).setBackgroundColor(Color.rgb( 255, 0, 0));

    super.setContentView(frame);
} // ()

public void onClick(View view) {
	if(view.getTag() == "1"){
	((Button) view).setBackgroundColor(Color.rgb( 128, 128, 128));
	soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f);
	
	Random r = new Random();
	int b = r.nextInt(24);
	while((Button) FirstActivity.buttons.get(b) == (Button) view){
		b = r.nextInt(24);
	}
	((Button) FirstActivity.buttons.get(b)).setTag("1");
	((Button) FirstActivity.buttons.get(b)).setBackgroundColor(Color.rgb( 255, 0, 0));

	}else if(view.getTag() == "0"){
    ((Button) view).setBackgroundColor(Color.rgb( 128, 128, 128));
    view.setTag("0");
	}else if(view.getTag() =="2"){

	    super.setContentView(layout);
	}
	
}
}