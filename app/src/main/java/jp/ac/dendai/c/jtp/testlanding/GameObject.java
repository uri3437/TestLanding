package jp.ac.dendai.c.jtp.testlanding;

public abstract class GameObject {
    private int x,y,cx,cy;
    private int width,height;

    public GameObject(int x, int y,int width,int height){
        setLocate(x,y);
        this.width=width;
        this.height=height;
        setCenter(this.x,this.y);

    }
    //座標設定
    public void setLocate(int x, int y){
        this.x=x;
        this.y=y;
    }
    //中心座標設定
    public void setCenter(int x, int y){
        this.cx=x+width/2;
        this.cy=y+height/2;
    }
    //移動
    public void move(int x, int y){
        this.x=this.x+x;
        this.y=this.y+y;
        setCenter(this.x,this.y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getCX(){
        return cx;
    }
    public int getCY(){
        return cy;
    }

}
