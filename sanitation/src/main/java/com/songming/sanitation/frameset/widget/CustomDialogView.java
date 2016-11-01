package com.songming.sanitation.frameset.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.songming.sanitation.R;

public class CustomDialogView extends View{
	 // 加载图片资源id，存入缓存数�? 
    /*private final int[] ids = new int[] { R.drawable.loading01,  
                R.drawable.loading02, R.drawable.loading03, R.drawable.loading04 ,  
                R.drawable.loading05, R.drawable.loading06, R.drawable.loading07 ,  
                R.drawable.loading08, R.drawable.loading09, R.drawable.loading10 ,  
                R.drawable.loading11, R.drawable.loading12 }; */ 
    private final int[] ids = new int[] { R.drawable.loading0001,  
    		R.drawable.loading0002, R.drawable.loading0003 };  

    private Bitmap[] loadingImgs ;  

    private Paint loadingImagePaint ;  

    private int currentIdsIndex = 0;  

    public CustomDialogView(Context context, AttributeSet attrs) {  
          super(context, attrs);  
         init();  
   }  

    public CustomDialogView(Context context) {  
          super(context);  
         init();  
   }  

    private void init() {  
           
          // 实例化画�? 
          loadingImagePaint = new Paint();  
          // 设置抗锯�? 
          loadingImagePaint.setAntiAlias(true);  

          // �?��性放进缓存数组中  
          /*loadingImgs = new Bitmap[] {  
                       BitmapFactory. decodeResource(getResources(), ids[0]),  
                       BitmapFactory. decodeResource(getResources(), ids[1]),  
                       BitmapFactory. decodeResource(getResources(), ids[2]),  
                       BitmapFactory. decodeResource(getResources(), ids[3]),  
                       BitmapFactory. decodeResource(getResources(), ids[4]),  
                       BitmapFactory. decodeResource(getResources(), ids[5]),  
                       BitmapFactory. decodeResource(getResources(), ids[6]),  
                       BitmapFactory. decodeResource(getResources(), ids[7]),  
                       BitmapFactory. decodeResource(getResources(), ids[8]),  
                       BitmapFactory. decodeResource(getResources(), ids[9]),  
                       BitmapFactory. decodeResource(getResources(), ids[10]),  
                       BitmapFactory. decodeResource(getResources(), ids[11]) }; */ 
          loadingImgs = new Bitmap[] {  
        		  BitmapFactory. decodeResource(getResources(), ids[0]),  
        		  BitmapFactory. decodeResource(getResources(), ids[1]),  
        		  BitmapFactory. decodeResource(getResources(), ids[2]) };  
   }  

    @Override  
    protected void onDraw(Canvas canvas) {  

          // 循环控制每一张图片的绘制顺序，让看起来像是播放动�? 
          if (currentIdsIndex > (ids .length - 1)) {  
                 currentIdsIndex = 0;  
         }  

         Bitmap currentLoadingBitmap = loadingImgs[currentIdsIndex ];  
          // 绘制图片，显示在屏幕正中  
         canvas.drawBitmap(currentLoadingBitmap, (getWidth() - currentLoadingBitmap.getWidth())/2,  
        		 (getHeight() - currentLoadingBitmap.getHeight())/2, loadingImagePaint );  

          currentIdsIndex++;  

          super.onDraw(canvas);  
   }  

}
