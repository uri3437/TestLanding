package jp.ac.dendai.c.jtp.testlanding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mHolder;
    private Thread mThread;
    private Canvas mCanvas;
    private Paint mPaint;
    private MainActivity MA;

    private float disp_w,disp_h;
    private int jiki_w,jiki_h;
    private int car_w,car_h;

    private Bitmap BitJiki;
    private Bitmap BitCar;

    private Jiki jiki;
    private Carrier car;
    private boolean clear = false;
    private boolean miss= false;

    private static final long FPS=20;
    private static final long PERIOD=1000/FPS;
    private long mLastTime;
    private long sleepTime;

    //コンストラクタ
    public GameView(Context context){
        super(context);
        init(context);
    }
    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    //初期化メソッド
    public void init(Context context){
        mHolder=getHolder();
        mHolder.addCallback(this);
        mHolder.setFixedSize(getWidth(), getHeight());

        MA=(MainActivity)context;
        disp_w=MA.disp_w;
        disp_h=MA.disp_h;

        Resources res=context.getResources();
        BitJiki= BitmapFactory.decodeResource(res,R.drawable.jiki);
        jiki_w=BitJiki.getWidth();
        jiki_h=BitJiki.getHeight();
        BitCar=BitmapFactory.decodeResource(res,R.drawable.car);
        car_w=BitCar.getWidth();
        car_h=BitCar.getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);

        jiki=new Jiki(((int)disp_w-jiki_w)/2,(int)disp_h-jiki_h,jiki_w,jiki_h);
        car=new Carrier(((int)disp_w-car_w)/2,-car_h,car_w,car_h);

        mThread=new Thread(this);
        mThread.start();
    }
    @Override
    public void run() {
        while(mThread !=null) {
            drawBoard();
            upDate();
            sleepThread();
        }
    }

    //キャンバスへの描画
    public void drawBoard(){
        mCanvas=mHolder.lockCanvas();
        mCanvas.drawColor(Color.CYAN);
        mCanvas.drawBitmap(BitCar,car.getX(),car.getY(),null);
        mCanvas.drawBitmap(BitJiki,jiki.getX(),jiki.getY(),null);

        if(clear) mCanvas.drawText("着陸しました",disp_w/2,disp_h/2,mPaint);
        else if (miss) mCanvas.drawText("失敗です",disp_w/2,disp_h/2,mPaint);

        mHolder.unlockCanvasAndPost(mCanvas);
    }
    //内部処理
    public void upDate(){
        if(!clear&&!miss) {
            car.move(0,20);
            if(car.getY()>disp_h) miss=true;
        }
    }
    //FPSに合うようスレッドスリープ
    public void sleepThread(){
        sleepTime=PERIOD-(SystemClock.uptimeMillis()-mLastTime);
        if(sleepTime>=0) {
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
            }
        }
        mLastTime=SystemClock.uptimeMillis();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action= event.getAction();
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if(jiki.getCX()>car.getX() && jiki.getCX()<car.getX()+BitCar.getWidth()
                        && car.getY()+BitCar.getHeight()>jiki.getY() && !miss)
                    clear=true;
            case MotionEvent.ACTION_MOVE:
                if(jiki.getX()<x && jiki.getX()+BitJiki.getWidth()>x && y>jiki.getY()
                    && !clear && !miss)
                    jiki.move(x-jiki.getCX(),0);
                break;
        }
        return true;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread=null;
    }
}
