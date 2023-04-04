package com.example.numpiy;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PartSnake {
    private Bitmap bm;
    private int x,y;
    private Rect Body,top,bottom,left,right;

    public PartSnake(Bitmap bm, int x, int y) {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getBody() {
        return new Rect(this.x,this.y,this.x+GameView.map_size,this.y+GameView.map_size);
    }

    public void setBody(Rect body) {
        Body = body;
    }

    public Rect getTop() {
        return new Rect(this.x,this.y-10*Constrants.SCREEN_HEIGHT/1920,this.x+GameView.map_size,this.y);
    }

    public void setTop(Rect top) {
        this.top = top;
    }

    public Rect getBottom() {
        return new Rect(this.x,this.y+GameView.map_size,this.x+GameView.map_size,this.y+GameView.map_size+10*Constrants.SCREEN_HEIGHT/1920);
    }

    public void setBottom(Rect bottom) {
        this.bottom = bottom;
    }

    public Rect getLeft() {
        return new Rect(this.x-10*Constrants.SCREEN_WIDTH/1080,this.y,this.x,this.y+GameView.map_size);
    }

    public void setLeft(Rect left) {
        this.left = left;
    }

    public Rect getRight() {
        return new Rect(this.x+GameView.map_size,this.y,this.x+GameView.map_size+Constrants.SCREEN_WIDTH/1080,this.y+GameView.map_size);
    }

    public void setRight(Rect right) {
        this.right = right;
    }
}
