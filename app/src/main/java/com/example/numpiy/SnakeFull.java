package com.example.numpiy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class SnakeFull {
    private boolean dir_left,dir_right,dir_top,dir_bottom;
    private Bitmap bm,body_bottom_left,body_bottom_right,body_horizontal,body_top_left,body_top_right,
            body_vertical,head_down,head_left,head_right,head_up,tail_up,tail_right,
            tail_left,tail_down;
    private int x,y,len;
    private ArrayList<PartSnake> arrPartSnake=new ArrayList<>();
    public SnakeFull(Bitmap bm, int x, int y, int len) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.len = len;
        body_bottom_left = Bitmap.createBitmap(bm, 0, 0, GameView.map_size, GameView.map_size);
        body_bottom_right = Bitmap.createBitmap(bm, GameView.map_size, 0, GameView.map_size, GameView.map_size);
        body_horizontal = Bitmap.createBitmap(bm, 2 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        body_top_left = Bitmap.createBitmap(bm, 3 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        body_top_right = Bitmap.createBitmap(bm, 4 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        body_vertical = Bitmap.createBitmap(bm, 5 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        head_down = Bitmap.createBitmap(bm, 6 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        head_left = Bitmap.createBitmap(bm, 7 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        head_right = Bitmap.createBitmap(bm, 8 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        head_up = Bitmap.createBitmap(bm, 9 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        tail_up = Bitmap.createBitmap(bm, 10 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        tail_right = Bitmap.createBitmap(bm, 11 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        tail_left = Bitmap.createBitmap(bm, 12 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        tail_down = Bitmap.createBitmap(bm, 13 * GameView.map_size, 0, GameView.map_size, GameView.map_size);
        arrPartSnake.add(new PartSnake(head_right, x, y));
        for (int i = 1; i < len - 1; i++) {
            arrPartSnake.add(new PartSnake(body_horizontal, arrPartSnake.get(i - 1).getX() - GameView.map_size, y));
        }
        arrPartSnake.add(new PartSnake(tail_right, arrPartSnake.get(len - 2).getX() - GameView.map_size, y));
        setDir_right(true);
    }
    public void update(){
        for(int i=len-1;i>0;i--){
            arrPartSnake.get(i).setX(arrPartSnake.get(i-1).getX());
            arrPartSnake.get(i).setY(arrPartSnake.get(i-1).getY());
        }
        if(dir_right){
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX()+GameView.map_size);
            arrPartSnake.get(0).setBm(head_right);
        }
        else if(dir_left){
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX()-GameView.map_size);
            arrPartSnake.get(0).setBm(head_left);
        }
        else if(dir_top){
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY()-GameView.map_size);
            arrPartSnake.get(0).setBm(head_up);
        }
        else if(dir_bottom){
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY()+GameView.map_size);
            arrPartSnake.get(0).setBm(head_down);
        }
        for (int i=1;i<len-1;i++){
            if(arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_bottom_left);
            }
            else if(arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_bottom_right);
            }
            else if(arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_top_left);
            }
            else if(arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_top_right);
            }
            else if(arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getTop().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getBottom().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_vertical);
            }
            else if(arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i+1).getBody())
                    && arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i-1).getBody())
                    || arrPartSnake.get(i).getLeft().intersect(arrPartSnake.get(i-1).getBody())
                    &&arrPartSnake.get(i).getRight().intersect(arrPartSnake.get(i+1).getBody())){
                arrPartSnake.get(i).setBm(body_horizontal);
            }
        }
        if(arrPartSnake.get(len-1).getRight().intersect(arrPartSnake.get(len-2).getBody())){
            arrPartSnake.get(len-1).setBm(tail_right);

        }
        else if(arrPartSnake.get(len-1).getLeft().intersect(arrPartSnake.get(len-2).getBody())){
            arrPartSnake.get(len-1).setBm(tail_left);

        }
        else if(arrPartSnake.get(len-1).getTop().intersect(arrPartSnake.get(len-2).getBody())){
            arrPartSnake.get(len-1).setBm(tail_up);

        }
        else if(arrPartSnake.get(len-1).getBottom().intersect(arrPartSnake.get(len-2).getBody())){
            arrPartSnake.get(len-1).setBm(tail_down);

        }

    }

    public void draw(Canvas canvas){
        for (int i=0;i<len;i++){
            canvas.drawBitmap(arrPartSnake.get(i).getBm(),arrPartSnake.get(i).getX(),arrPartSnake.get(i).getY(),null);
        }
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBody_bottom_left() {
        return body_bottom_left;
    }

    public void setBody_bottom_left(Bitmap body_bottom_left) {
        this.body_bottom_left = body_bottom_left;
    }

    public Bitmap getBody_bottom_right() {
        return body_bottom_right;
    }

    public void setBody_bottom_right(Bitmap body_bottom_right) {
        this.body_bottom_right = body_bottom_right;
    }

    public Bitmap getBody_horizontal() {
        return body_horizontal;
    }

    public void setBody_horizontal(Bitmap body_horizontal) {
        this.body_horizontal = body_horizontal;
    }

    public Bitmap getBody_top_left() {
        return body_top_left;
    }

    public void setBody_top_left(Bitmap body_top_left) {
        this.body_top_left = body_top_left;
    }

    public Bitmap getBody_top_right() {
        return body_top_right;
    }

    public void setBody_top_right(Bitmap body_top_right) {
        this.body_top_right = body_top_right;
    }

    public Bitmap getBody_vertical() {
        return body_vertical;
    }

    public void setBody_vertical(Bitmap body_vertical) {
        this.body_vertical = body_vertical;
    }

    public Bitmap getHead_down() {
        return head_down;
    }

    public void setHead_down(Bitmap head_down) {
        this.head_down = head_down;
    }

    public Bitmap getHead_left() {
        return head_left;
    }

    public void setHead_left(Bitmap head_left) {
        this.head_left = head_left;
    }

    public Bitmap getHead_right() {
        return head_right;
    }

    public void setHead_right(Bitmap head_right) {
        this.head_right = head_right;
    }

    public Bitmap getHead_up() {
        return head_up;
    }

    public void setHead_up(Bitmap head_up) {
        this.head_up = head_up;
    }

    public Bitmap getTail_up() {
        return tail_up;
    }

    public void setTail_up(Bitmap tail_up) {
        this.tail_up = tail_up;
    }

    public Bitmap getTail_right() {
        return tail_right;
    }

    public void setTail_right(Bitmap tail_right) {
        this.tail_right = tail_right;
    }

    public Bitmap getTail_left() {
        return tail_left;
    }

    public void setTail_left(Bitmap tail_left) {
        this.tail_left = tail_left;
    }

    public Bitmap getTail_down() {
        return tail_down;
    }

    public void setTail_down(Bitmap tail_down) {
        this.tail_down = tail_down;
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

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public ArrayList<PartSnake> getArrPartSnake() {
        return arrPartSnake;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arrPartSnake = arrPartSnake;
    }

    public boolean isDir_left() {
        return dir_left;
    }

    public void setDir_left(boolean dir_left) {
        s();
        this.dir_left = dir_left;
    }

    public boolean isDir_right() {
        return dir_right;
    }

    public void setDir_right(boolean dir_right) {
        s();
        this.dir_right = dir_right;
    }

    public boolean isDir_top() {
        return dir_top;
    }

    public void setDir_top(boolean dir_top) {
        s();
        this.dir_top = dir_top;
    }

    public boolean isDir_bottom() {
        return dir_bottom;
    }

    public void setDir_bottom(boolean dir_bottom) {
        s();
        this.dir_bottom = dir_bottom;
    }
    public void s(){
        this.dir_left=false;
        this.dir_right=false;
        this.dir_bottom=false;
        this.dir_top=false;
    }

    public void partadd() {
        PartSnake p=this.arrPartSnake.get(len-1);
        this.len+=1;
        if (p.getBm()==tail_right){
            this.arrPartSnake.add(new PartSnake(tail_right, p.getX()-GameView.map_size, p.getY()));
        }
        else if (p.getBm()==tail_left){
            this.arrPartSnake.add(new PartSnake(tail_left, p.getX()+GameView.map_size, p.getY()));
        }
        else if (p.getBm()==tail_up){
            this.arrPartSnake.add(new PartSnake(tail_up, p.getX(), p.getY()+GameView.map_size));
        }
        else if (p.getBm()==tail_down){
            this.arrPartSnake.add(new PartSnake(tail_down, p.getX(), p.getY()-GameView.map_size));
        }
    }
}
