
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;


public class SpaceInvaders extends Application {
	private GraphicsContext gc;
    private Canvas canvas;
    private Fusee joueur;
    private Pane root;
    private Scene scene;
    private static Image JOUEUR_IMG;
    private static Image ALIEN_IMG;
    private static Image EXPLOSION_IMG;
    public static ArrayList<ArrayList<Alien>> aliens = new ArrayList<ArrayList<Alien>>();
    public static ArrayList<Tir> tirs = new ArrayList<>();
    public static ArrayList<TirAlien> tirsAlien = new ArrayList<>();
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    public static final int TAILLE_JOUEUR = 30;
    public static final int TAILLE_ALIEN = 30;
    public static final int NBR_ALIEN_LIGNE=6;
    private final String CHEMIN_JOUEUR_IMG = "../Images/joueur.png";
    private final String CHEMIN_ALIEN_IMG = "../Images/alien.png";
    private final String CHEMIN_EXPLOSION_IMG = "../Images/boom.png";
    public static int level=1;
    private int score=0;
    private Timeline timeline;


    public static void main(String args[]){
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        creerContenu();
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
    public void creerContenu() throws FileNotFoundException{

        timeline = new Timeline(new KeyFrame(Duration.millis(100),e->{
        	run();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        JOUEUR_IMG = new Image(new FileInputStream (CHEMIN_JOUEUR_IMG));
        ALIEN_IMG = new Image(new FileInputStream (CHEMIN_ALIEN_IMG));
		EXPLOSION_IMG = new Image(new FileInputStream (CHEMIN_EXPLOSION_IMG));

        canvas = new Canvas(WIDTH,HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Pane(canvas);
        root.setPrefSize(WIDTH,HEIGHT);
        scene = new Scene(root);
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0,0,WIDTH,HEIGHT);

        joueur = new Fusee(WIDTH/2,HEIGHT-TAILLE_JOUEUR-10,TAILLE_JOUEUR,JOUEUR_IMG);
        joueur.affiche(gc);

        ajouterAlien(level);
        for(int i=0; i<aliens.size(); i++){
            aliens.get(i).get(i).affiche(gc);
        }

        scene.setOnKeyPressed( e->{
            switch(e.getCode()){
                case LEFT:
                    joueur.moveLeft();
                break;
                case RIGHT:
                    joueur.moveRight();
                break;
                case SPACE:
                	tirs.add(joueur.tir());
                break;
            }
        });
    }
    public static int distance(int x1, int y1, int x2, int y2){
    	return (int) Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static void ajouterAlien(int level){
		int hauteur=200;
    	for(int i=0;i<level;i++){
            int ecart=0;
            aliens.add(new ArrayList<>());
            for(int j=0;j<NBR_ALIEN_LIGNE;j++){
                aliens.get(i).add(new Alien(ecart,hauteur,TAILLE_ALIEN,ALIEN_IMG));
                ecart = ecart+120;
            }
            hauteur=hauteur+TAILLE_ALIEN+5;
        }
    }

    public static void descendre(){
        for(int i=0; i<aliens.size(); i++){
            for(int j=0; j<aliens.get(i).size(); j++){
            	aliens.get(i).get(j).posY=aliens.get(i).get(j).posY+Alien.vitesse;
            }
        }
    }

    private void run(){

        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0,0,WIDTH,HEIGHT);
        gc.setFill(Color.rgb(255,0,255));
        gc.fillText("Score  : "+score,45,25);
        gc.setFill(Color.rgb(255,255,0));
        gc.fillText("Niveau : "+level,45,50);


        for(int i=0;i<tirs.size();i++){
            if(tirs.get(i).posY>0){
                tirs.get(i).affiche(gc);
                tirs.get(i).update();
            }else{
                tirs.remove(tirs.get(i));
            }
        }

        for(int i=0; i<tirsAlien.size();i++){
        	if(tirsAlien.get(i).posY<HEIGHT){
        		tirsAlien.get(i).affiche(gc);
        		tirsAlien.get(i).update();
        	}else{
        		tirsAlien.remove(tirsAlien.get(i));
        	}
        }

        for(int i=0;i<tirs.size();i++){
            for(int j=0;j<aliens.size(); j++){
        		for(int k=0; k<aliens.get(j).size(); k++){
                    if(tirs.get(i).collision(aliens.get(j).get(k))){
						gc.drawImage(EXPLOSION_IMG,aliens.get(j).get(k).posX,aliens.get(j).get(k).posY,50,50);
                        aliens.get(j).remove(aliens.get(j).get(k));
                        score=score+10;
                    }
                }
            }
        }

        for(int i=0;i<aliens.size();i++){
            for(int j=0; j<aliens.get(i).size(); j++){
                aliens.get(i).get(j).update();
            	aliens.get(i).get(j).affiche(gc);
            }
        }

        for(int i=0;i<aliens.size();i++){
            for(int j=0; j<aliens.get(i).size(); j++){
                if(aliens.get(i).get(j).posY==joueur.posY){
                    gc.setFill(Color.grayRgb(20));
                    gc.fillRect(0,0,WIDTH,HEIGHT);
                    gc.setFill(Color.rgb(255,255,255));
                    gc.fillText("GAME OVER - LA PARTIE EST TERMINEE",WIDTH/2-100,HEIGHT/2);
                    timeline.stop();
                }
            }
        }
        for(int i=0; i<tirsAlien.size();i++){
        	if(joueur.collision(tirsAlien.get(i))){
                gc.setFill(Color.rgb(255,255,255));
                gc.fillText("GAME OVER - LA PARTIE EST TERMINEE",WIDTH/2-100,HEIGHT/2);
                gc.setFill(Color.rgb(255,0,0));
                timeline.stop();
        	}
        }
        joueur.affiche(gc);
        int k=0;
        for(int i=0; i<aliens.size(); i++){
            if(aliens.get(i).isEmpty()){
                k++;
            }
        }
        if(k==aliens.size()){
            level++;
            score=score+1000;
            ajouterAlien(level);
        }

    }
}

