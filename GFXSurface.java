package com.example.my;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener{

	MyBringBackSurface oursurface;
	float x,y,sx,sy,fx,fy,dx,dy,anix,aniy,scaledx,scaledy;
	Bitmap test,plus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		oursurface= new MyBringBackSurface(this);
		oursurface.setOnTouchListener(this);
		setContentView(oursurface);
		x=0;
		y=0;
		sx=0;
		sy=0;
		fx=0;fy=0;
		dx=dy=anix=aniy=scaledx=scaledy=0;
		 test = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
		 plus = BitmapFactory.decodeResource(getResources(),R.drawable.plus);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		oursurface.pause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		oursurface.resume();
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = event.getX();
		y=event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			sx=event.getX();
			sy=event.getY();
			dx=dy=anix=aniy=scaledx=scaledy=fx=fy=0; 
			break;
		case MotionEvent.ACTION_UP:
			fx=event.getX();
			fy=event.getY();
			dx=fx-sx;
			dy=fy-sy;
			scaledx=dx/30;	
			scaledy=dy/30;
			x=y=0;
			
			break;
			
		}
		return true;
	}

	public class MyBringBackSurface extends SurfaceView implements Runnable{

		SurfaceHolder ourholder;	
		Thread mythread = null;
		boolean isrunning = false;
		public MyBringBackSurface(Context context) {
			// TODO Auto-generated constructor stub
			super(context);
			ourholder=getHolder();
					
		}
 
		public void pause()
		{
			isrunning = false;
			while(true)
			{
				try {
					mythread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			}
			mythread=null;
		}
		public void resume()
		{
			isrunning = true;
			mythread=new Thread(this);
			mythread.start();
		}
		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			while(isrunning)
			{
				if(!ourholder.getSurface().isValid())
				{
					continue;
				}
				Canvas canvas = ourholder.lockCanvas();
				canvas.drawRGB( 5, 1, 200);
				if(x!=0 && y!=0)
				{
					
					canvas.drawBitmap(test,x-test.getWidth()/2,y-test.getHeight()/2,null);
				}
				if(sx!=0 && sy!=0)
				{
					
					canvas.drawBitmap(plus,sx-plus.getWidth()/2,sy-plus.getHeight()/2,null);
				}
				
				if(fx!=0 && fy!=0)
				{
					canvas.drawBitmap(test,fx-test.getWidth()/2-anix,fy-test.getHeight()/2-aniy,null);
					canvas.drawBitmap(plus,sx-plus.getWidth()/2,sy-plus.getHeight()/2,null);
				}
				anix=anix+scaledx;
				aniy=aniy+scaledy;
				
				ourholder.unlockCanvasAndPost(canvas);
			}
		}

	}

}
