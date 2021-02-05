
import javafx.scene.image.Image;

public class Alien extends Fusee {

    public static int vitesse=10;
    public static int direction=1;
    public static boolean gauche=false;
    public static boolean droite=true;

    public Alien(int posX, int posY, int size, Image img) {
        super(posX, posY, size, img);
    }
    public TirAlien tir(){
        return new TirAlien(posX+10,posY+10);
    }
    public void update(){

        if(this.posX==0){
            direction=1;
        }else{
            if(this.posX==SpaceInvaders.WIDTH-SpaceInvaders.TAILLE_ALIEN){
                direction=0;
            }
        }
        if(this.direction==0){
            if(droite==false){
                moveRight();
                SpaceInvaders.descendre();
                for(int i=0; i<SpaceInvaders.aliens.size(); i++){
                    for(int j=0; j<SpaceInvaders.aliens.get(i).size();j++){
                        if(i==0){
                            SpaceInvaders.tirsAlien.add(SpaceInvaders.aliens.get(i).get(j).tir());
                        }
                    }
                }
            }
            moveLeft();
            gauche=false;
            droite=true;
        }else{
            if(this.direction==1){
                if(gauche==false){
                    moveLeft();
                    SpaceInvaders.descendre();
                    for(int i=0; i<SpaceInvaders.aliens.size(); i++){
                        for(int j=0; j<SpaceInvaders.aliens.get(i).size();j++){
                            if(i==0){
                                SpaceInvaders.tirsAlien.add(SpaceInvaders.aliens.get(i).get(j).tir());
                            }
                        }
                    }
                }
                moveRight();
                gauche=true;
                droite=false;
            }
        }
    }
    public void moveRight(){
        if(this.posX<=(SpaceInvaders.WIDTH-SpaceInvaders.TAILLE_JOUEUR)){
            this.posX=this.posX+5;
        }

    }
    public void moveLeft(){
        if(this.posX>=0){
            this.posX=this.posX-5;
        }
    }
}
