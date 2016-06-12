package jp.ac.dendai.c.jtp.testlanding;

import android.graphics.PixelFormat;
import android.graphics.Point;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public float disp_w,disp_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display disp= getWindow().getWindowManager().getDefaultDisplay();
        Point size=new Point();
        disp.getSize(size);
        disp_w=size.x;
        disp_h=size.y;
        setContentView(new GameView(this));
    }
}
