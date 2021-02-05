
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TirAlien extends Tir{

    private int speed=30;

    public TirAlien(int posX, int posY) {
        super(posX, posY);
    }
    public void affiche(GraphicsContext gc){
        gc.setFill(Color.rgb(0,255,255));
        gc.fillOval(posX,posY,size/(size/4),3*size);
    }
    public void update(){
        if(this.posY<=SpaceInvaders.HEIGHT){
            this.posY=this.posY+speed;
        }
    }
}
