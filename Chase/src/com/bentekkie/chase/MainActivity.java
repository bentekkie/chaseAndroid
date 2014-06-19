package com.bentekkie.chase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class MainActivity
extends Activity
implements View.OnClickListener {

	public static List<Button> buttons;
	public TableLayout layout;
	Timer T=new Timer();
	SoundPool soundPool;
	Boolean firstRun;
	int sound;
	Boolean isPaused;
	int progress;
	int ticks;
	String startButtonText;
	String resetButtonText;
	String timeTitleText;
	String redButtonTag;
	String greyButtonTag;
	String startButtonTag;
	String restartButtonTag;
	String shareButtonTag;
	float stext;
	float ltext;
	float eltext;
	int twidth;
	int tlength;
	int wincriteria;
    Button start;
	DisplayMetrics metrics;
	LayoutParams startButtonLayout;
    LayoutParams resetButtonLayout;
	LayoutParams timeTextLayout;
    LayoutParams endTitleLayout;
    LayoutParams shareButtonLayout;
	FrameLayout frame;
    ImageView backround;
    ImageView backroundend;
	FrameLayout end;
    TextView timed;
    Button reset;
    Button share;
    TextView stitle;
    Typeface type;
    File imagePath;
    Bitmap bitmap;
    int[] generalButtonDimention;
    int[] generalTextDimention;
    int[] shareButtonDimention;
    
    
	public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupVars();
    setupStartScreen();
    setupGameBoard();
    setupEndScreen();
    
    super.setContentView(frame);
} // ()
	
public void setupVars(){
	type = Typeface.createFromAsset(getAssets(),"fonts/PlayfairDisplay-Black.ttf"); 
	buttons = new ArrayList<Button>();;
	layout = new TableLayout (this);
	T=new Timer();
	firstRun = true;
	soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	isPaused = false;
	startButtonText = "Start";
	resetButtonText = "Restart";
	timeTitleText = "Time";
	redButtonTag = "1";
	greyButtonTag = "0";
	startButtonTag = "2";
	restartButtonTag = "3";
	shareButtonTag = "4";
	stext = 30.0f;
	ltext = 60.0f;
	eltext = 100.0f;
	twidth = 4;
	tlength = 5;
	wincriteria = 20;
    start = new Button(this);
	metrics = this.getResources().getDisplayMetrics();
    generalButtonDimention = new int[]{(metrics.widthPixels/twidth),((metrics.heightPixels)/(tlength+1))};
    generalTextDimention = new int[]{(metrics.widthPixels),((metrics.heightPixels)/(tlength+1))};
    shareButtonDimention = new int[]{(metrics.widthPixels/10),((metrics.widthPixels)/10)};
	startButtonLayout = new LayoutParams(generalButtonDimention[0],generalButtonDimention[1], Gravity.CENTER);
    resetButtonLayout = new LayoutParams(generalButtonDimention[0],generalButtonDimention[1], Gravity.CENTER | Gravity.BOTTOM);
	timeTextLayout = new LayoutParams(generalTextDimention[0],generalTextDimention[1], Gravity.CENTER);
	endTitleLayout = new LayoutParams(generalTextDimention[0],generalTextDimention[1], Gravity.CENTER | Gravity.TOP);
    shareButtonLayout = new LayoutParams(shareButtonDimention[0],shareButtonDimention[1], Gravity.RIGHT | Gravity.BOTTOM);
	frame = new FrameLayout(this);
    backround = new ImageView(this);
    backroundend = new ImageView(this);
	end = new FrameLayout(this);
    timed = new TextView(this);
    reset = new Button(this);
    stitle = new TextView(this);
    share = new Button(this);
    imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
}
	
public void setupStartScreen(){

    backround.setBackgroundColor(Color.RED);
    frame.addView(backround);
    start.setTag(startButtonTag);
    start.setTypeface(type);
    start.setText(startButtonText);
    start.setTextColor(Color.WHITE);
    start.setBackgroundColor(Color.BLUE);
    start.setTextSize(TypedValue.COMPLEX_UNIT_PX,generalButtonDimention[1]/5);
    start.setOnClickListener(this);
    start.setGravity(Gravity.CENTER);
    frame.addView(start, startButtonLayout);
}

public void setupGameBoard(){
	layout.setLayoutParams( new TableLayout.LayoutParams(twidth,tlength) );
    layout.setPadding(1,1,1,1);
	sound = soundPool.load(this, R.raw.mario, 1);
	
    for (int f=0; f<=tlength; f++) {
        TableRow tr = new TableRow(this);
        
        for (int c=0; c<=(twidth-1); c++) {
            tr.addView(generateButton(), generalButtonDimention[0],generalButtonDimention[1]);
        }
        
        layout.addView(tr);
    } // for
    setToRed(randomInt());
}

public Button generateButton(){
	
	Button button = new Button (this);
	button.setTypeface(type);
	button.setBackgroundColor(Color.GRAY);
	button.setTag(greyButtonTag);
	button.setOnClickListener(this);
    button.setText("");
    button.setGravity(Gravity.CENTER);
    button.setTextSize(TypedValue.COMPLEX_UNIT_PX,generalButtonDimention[1]/2);
    buttons.add(button);
    return button;
}

public int randomInt(){
	Random r = new Random();
	int b = r.nextInt(buttons.size()-1);
	return b;
}

public void setToGrey(View view){

	((Button) view).setBackgroundColor(Color.GRAY);
	((Button) view).setText("");
	((Button) view).setTag(greyButtonTag);
}

public void setToRed(int a){
    ((Button) buttons.get(a)).setTag(redButtonTag);
    ((Button) buttons.get(a)).setBackgroundColor(Color.RED);
    ((Button) buttons.get(a)).setText(Integer.toString(wincriteria-progress));
    ((Button) buttons.get(a)).setTextColor(Color.WHITE);
    ((Button) buttons.get(a)).setGravity(Gravity.CENTER);
    ((Button) buttons.get(a)).setTextSize(TypedValue.COMPLEX_UNIT_PX,generalButtonDimention[1]/2);
    if(wincriteria-progress == 0){
		((Button) buttons.get(a)).setText(Integer.toString(wincriteria));
	}else{
	((Button) buttons.get(a)).setText(Integer.toString(wincriteria-progress));
	}
}

public void setupEndScreen(){
	
    backroundend.setBackgroundColor(Color.RED);
    end.addView(backroundend);
    isPaused = true;
    timed.setTypeface(type);
    timed.setText(0+"ms");
    timed.setTextColor(Color.WHITE);
    timed.setTextSize(TypedValue.COMPLEX_UNIT_PX,generalTextDimention[1]/3);
    timed.setGravity(Gravity.CENTER);
    reset.setTypeface(type);
    reset.setText(resetButtonText);
    reset.setTextColor(Color.WHITE);
    reset.setTag(restartButtonTag);
    reset.setOnClickListener(this);
    reset.setBackgroundColor(Color.BLUE);
    reset.setTextSize(TypedValue.COMPLEX_UNIT_PX,generalButtonDimention[1]/5);
    stitle.setTypeface(type);
    stitle.setText(timeTitleText);
    stitle.setTextColor(Color.WHITE);
    stitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,generalTextDimention[1]-5);
    stitle.setGravity(Gravity.CENTER);
    share.setGravity(Gravity.CENTER);
    share.setBackgroundColor(Color.GREEN);
    share.setTag(shareButtonTag);
    share.setOnClickListener(this);
    share.setBackgroundResource(drawable.ic_menu_share);
    end.addView(share,shareButtonLayout);
    end.addView(timed, timeTextLayout);
    end.addView(reset, resetButtonLayout);
    end.addView(stitle, endTitleLayout);
    isPaused = true;
    
}

public void setupTimer(){
	T=new Timer();
    T.scheduleAtFixedRate(new TimerTask() {         
        @Override
        public void run() {
                ticks++;     
        }   
    }, 1, 1);
}

public void onClick(View view) {
	if(view.getTag() == redButtonTag){
	soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f);
	progress++;
	if(progress == wincriteria){
		String time = Integer.toString(ticks);
		String timee = time.substring((time.length()-3));
		String times = time.substring(0, (time.length()-3));
				
	    timed.setText(times+"."+timee+"\n seconds");
		 super.setContentView(end);
	}
	int b = randomInt();
	while((Button) MainActivity.buttons.get(b) == (Button) view){
		b = randomInt();
	}
	setToRed(b);
	setToGrey(view);
	}else if(view.getTag() == greyButtonTag){
	setToGrey(view);
	}else if(view.getTag() ==startButtonTag){
		isPaused = false;
	    super.setContentView(layout);
                                                                                                                                
	    setupTimer();
	}else if(view.getTag() == restartButtonTag){
		T.cancel();
		ticks = 0;
		setupTimer();
		progress = 0;
	    super.setContentView(layout);	
	}else if(view.getTag() == shareButtonTag){
		bitmap = takeScreenshot();
		saveBitmap(bitmap);
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
	    sharingIntent.setType("image/jpeg");
	    String shareBody = "Here is the share content body";
	    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My Score is "+timed.getText()+" !");
	    sharingIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Share your score!");
	    sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(imagePath));
	startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
}
public Bitmap takeScreenshot() {
	   View rootView = findViewById(android.R.id.content).getRootView();
	   rootView.setDrawingCacheEnabled(true);
	   return rootView.getDrawingCache();
	}
public void saveBitmap(Bitmap bitmap) {

    FileOutputStream fos;
    try {
        fos = new FileOutputStream(imagePath);
        bitmap.compress(CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    } catch (FileNotFoundException e) {
        Log.e("GREC", e.getMessage(), e);
    } catch (IOException e) {
        Log.e("GREC", e.getMessage(), e);
    }
}
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
       // setContentView();

    } else {
      //  setContentView(R.layout.portraitView);
    }
}
}