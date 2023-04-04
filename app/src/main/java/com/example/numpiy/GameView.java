package com.example.numpiy;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {
    private Bitmap Grass1, Grass2, bmSnake,bmPrey;
    public static int map_size = 75 * Constrants.SCREEN_WIDTH / 1000;
    private int h = 21, w = 12;
    private SnakeFull snakeFull;
    private boolean move = false;
    private float mx, my;
    private android.os.Handler handler;
    private Runnable r;
    private Prey prey;
    private ArrayList<Grass> arr_Grass = new ArrayList<>();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Grass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        Grass1 = Bitmap.createScaledBitmap(Grass1, map_size, map_size, true);
        Grass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        Grass2 = Bitmap.createScaledBitmap(Grass2, map_size, map_size, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * map_size, map_size, true);
        bmPrey = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmPrey = Bitmap.createScaledBitmap(bmPrey, map_size, map_size, true);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((i + j) % 2 == 0) {
                    arr_Grass.add(new Grass(Grass1, j * map_size + Constrants.SCREEN_WIDTH / 2 - (w / 2) * map_size,
                            i * map_size + 100 * Constrants.SCREEN_HEIGHT / 1920, map_size, map_size));
                } else {
                    arr_Grass.add(new Grass(Grass2, j * map_size + Constrants.SCREEN_WIDTH / 2 - (w / 2) * map_size,
                            i * map_size + 100 * Constrants.SCREEN_HEIGHT / 1920, map_size, map_size));
                }
            }

        }
        snakeFull = new SnakeFull(bmSnake, arr_Grass.get(126).getX(), arr_Grass.get(126).getY(), 4);
        prey = new Prey(bmPrey,arr_Grass.get(randomPrey()[0]).getX(),arr_Grass.get(randomPrey()[1]).getY());
        handler= new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a) {
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }
                else {
                    if (mx-event.getX()> 100 * Constrants.SCREEN_WIDTH / 1080 && !snakeFull.isDir_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snakeFull.setDir_left(true);
                    }
                    else if (event.getX()-mx> 100 * Constrants.SCREEN_WIDTH / 1080 && !snakeFull.isDir_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snakeFull.setDir_right(true);
                    } else if (my - event.getY() > 100 * Constrants.SCREEN_WIDTH / 1080 && !snakeFull.isDir_bottom()) {
                        mx = event.getX();
                        my = event.getY();
                        snakeFull.setDir_top(true);
                    }else if (event.getY()-my > 100 * Constrants.SCREEN_WIDTH / 1080 && !snakeFull.isDir_top()) {
                        mx = event.getX();
                        my = event.getY();
                        snakeFull.setDir_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx=0;
                my=0;
                move=false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(0xFF1A6100);
        for (int i=0;i<arr_Grass.size();i++){
            canvas.drawBitmap(arr_Grass.get(i).getBm(), arr_Grass.get(i).getX(),arr_Grass.get(i).getY(),null);
        }
        snakeFull.update();
        snakeFull.draw(canvas);
        prey.draw(canvas);
        if (snakeFull.getArrPartSnake().get(0).getBody().intersect(prey.getR())){
            randomPrey();
            prey.reset(arr_Grass.get(randomPrey()[0]).getX(),arr_Grass.get(randomPrey()[1]).getY());
            snakeFull.partadd();
        }
        handler.postDelayed(r,100);
    }
    public int[] randomPrey(){
        int []xy=new int[2];
        Random r=new Random();
        xy[0]=r.nextInt(arr_Grass.size()-1);
        xy[1]=r.nextInt(arr_Grass.size()-1);
        Rect rect=new Rect(arr_Grass.get(xy[0]).getX(),arr_Grass.get(xy[1]).getY(),arr_Grass.get(xy[0]).getX()+map_size,arr_Grass.get(xy[1]).getY()+map_size);
        boolean check=true;
        while (check){
            check=false;
            for (int i=0;i<snakeFull.getArrPartSnake().size();i++){
                if (rect.intersect(snakeFull.getArrPartSnake().get(i).getBody())){
                    check=true;
                    xy[0]=r.nextInt(arr_Grass.size()-1);
                    xy[1]=r.nextInt(arr_Grass.size()-1);
                    rect=new Rect(arr_Grass.get(xy[0]).getX(),arr_Grass.get(xy[1]).getY(),arr_Grass.get(xy[0]).getX()+map_size,arr_Grass.get(xy[1]).getY()+map_size);
                }
            }
        }
        return xy;

    }
}
