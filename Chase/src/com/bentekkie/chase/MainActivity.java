package com.bentekkie.chase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
	float stext;
	float ltext;
	int twidth;
	int tlength;
	int wincriteria;
    Button start;
	DisplayMetrics metrics;
	LayoutParams lp;
    LayoutParams lb;
	LayoutParams la;
    LayoutParams lc;
	FrameLayout frame;
    ImageView backround;
    ImageView backroundend;
	FrameLayout end;
    TextView timed;
    Button reset;
    TextView stitle;
	
	
	public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupVars();
    setupStartScreen();
    setupGameBoard();
    setupEndScreen();
    
    super.setContentView(frame);
} // ()
	
public void setupVars(){
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
	stext = 30.0f;
	ltext = 60.0f;
	twidth = 4;
	tlength = 5;
	wincriteria = 20;
    start = new Button(this);
	metrics = this.getResources().getDisplayMetrics();
	lp = new LayoutParams((metrics.widthPixels/4),((metrics.heightPixels)/6), Gravity.CENTER);
    lb = new LayoutParams((metrics.widthPixels/twidth),((metrics.heightPixels)/(tlength+1)), Gravity.CENTER | Gravity.BOTTOM);
	la = new LayoutParams((metrics.widthPixels),((metrics.heightPixels)/6), Gravity.CENTER);
    lc = new LayoutParams((metrics.widthPixels/twidth),((metrics.heightPixels)/(tlength+1)), Gravity.CENTER | Gravity.TOP);
	frame = new FrameLayout(this);
    backround = new ImageView(this);
    backroundend = new ImageView(this);
	end = new FrameLayout(this);
    timed = new TextView(this);
    reset = new Button(this);
    stitle = new TextView(this);
}
	
public void setupStartScreen(){

    backround.setBackgroundColor(Color.RED);
    frame.addView(backround);
    start.setTag(startButtonTag);
    start.setText(startButtonText);
    start.setTextColor(Color.WHITE);
    start.setBackgroundColor(Color.BLUE);
    start.setTextSize(stext);
    start.setOnClickListener(this);
    start.setGravity(Gravity.CENTER);
    frame.addView(start, lp);
}

public void setupGameBoard(){
	layout.setLayoutParams( new TableLayout.LayoutParams(twidth,tlength) );
    layout.setPadding(1,1,1,1);
	sound = soundPool.load(this, R.raw.mario, 1);
	
    for (int f=0; f<=tlength; f++) {
        TableRow tr = new TableRow(this);
        
        for (int c=0; c<=(twidth-1); c++) {
            tr.addView(generateButton(), (metrics.widthPixels/twidth),((metrics.heightPixels)/(tlength+1)));
        }
        
        layout.addView(tr);
    } // for
    setToRed(randomInt());
}

public Button generateButton(){
	
	Button button = new Button (this);
	button.setBackgroundColor(Color.GRAY);
	button.setTag(greyButtonTag);
	button.setOnClickListener(this);
    button.setText("");
    button.setTextSize(stext);
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
    ((Button) buttons.get(a)).setTextSize(stext);
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
    timed.setText(Integer.toString(ticks)+"ms");
    timed.setTextColor(Color.WHITE);
    timed.setTextSize(stext);
    timed.setGravity(Gravity.CENTER);
    reset.setText(resetButtonText);
    reset.setTextColor(Color.WHITE);
    reset.setTag(restartButtonTag);
    reset.setOnClickListener(this);
    reset.setBackgroundColor(Color.BLUE);
    reset.setTextSize(stext);
    stitle.setText(timeTitleText);
    stitle.setTextColor(Color.WHITE);
    stitle.setTextSize(stext);
    stitle.setGravity(Gravity.CENTER);
    end.addView(timed, la);
    end.addView(reset, lb);
    end.addView(stitle, lc);
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

	    timed.setText(Integer.toString(ticks)+"ms");
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
	}
}
}