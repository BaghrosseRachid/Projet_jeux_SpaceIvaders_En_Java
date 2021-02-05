
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Fusee  {

    protected int posX;
    protected int posY;
    protected boolean detruit,explosion;
    protected Image img;
    protected int size;

    public Fusee(int posX, int posY, int size, Image img){
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }
    public Tir tir(){
        return new Tir(posX+10,posY-10);
    }
    public void affiche(GraphicsContext gc){
        gc.drawImage(img, posX, posY,size,size);
    }
    public void moveRight(){
        if(this.posX<=(SpaceInvaders.WIDTH-SpaceInvaders.TAILLE_JOUEUR)){
            this.posX=this.posX+20;
        }
    }
    public void moveLeft(){
        if(this.posX>=0){
            this.posX=this.posX-20;
        }
    }
    public boolean collision(Tir tir){
        if((tir.posX>=this.posX && tir.posX<=this.posX+30)&&(this.posY<=tir.posY && tir.posY<=tir.posY+5)){
           return true;
        }else{
            return false;
        }
    }
}
