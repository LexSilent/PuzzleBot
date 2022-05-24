package com.example.puzzlebot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.util.Objects;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class HelloController {
    @FXML private TextField tfComandos;
    @FXML private VBox contenedor;
    public static int playerXInitial, playerYInitial, starrXInitial, starYInitial;
    MediaPlayer mediaPlayer,mediaPlayer1;
    int margenX=8,margenY=5;

    public static VBox contenedorMismo;

    Image imagenCuadro, imagenRobot, imagenStar;

    @FXML public void initialize() {

        contenedorMismo = contenedor;

        Media sound = new Media(new File("src/main/resources/audio/fondo.mp3").toURI().toString());
        mediaPlayer1=new MediaPlayer(sound);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.play();

        Random random=new Random();
        playerXInitial= random.nextInt(8);
        playerYInitial= random.nextInt(5);
        starrXInitial= random.nextInt(8);
        starYInitial= random.nextInt(5);

        imagenCuadro = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/cuadro.png")));
        imagenRobot = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/robot.png")));
        imagenStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/Star.png")));

        crearCuadros(playerXInitial,playerYInitial,starrXInitial,starYInitial);

    }

    public void crearCuadros(int playerXInitial, int playerYInitial, int starrXInitial, int starYInitial){

        contenedor.getChildren().clear();

        for (int y=0;y<=margenY;y++){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);
            hBox.getChildren().clear();
            contenedor.getChildren().add(hBox);
            for (int x=0;x<=margenX;x++){

                Image image;
                if (x==playerXInitial&&y==playerYInitial) image = imagenRobot;
                else if(x==starrXInitial&&y==starYInitial) image = imagenStar;
                else image = imagenCuadro;

                ImageView imageView = new ImageView(image);
                imageView.setId("imageView"+x+y);
                imageView.setFitWidth(70);
                imageView.setFitHeight(70);
                hBox.getChildren().add(imageView);

            }
        }//for

    }

    @FXML void btnDown() {
        tfComandos.setText(tfComandos.getText()+"D");
        Media sound = new Media(new File("src/main/resources/audio/movimientoAbajo.mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML void btnLeft() {
        tfComandos.setText(tfComandos.getText()+"L");
        Media sound = new Media(new File("src/main/resources/audio/movimientoIzquierda.mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML void btnRight() {
        tfComandos.setText(tfComandos.getText()+"R");
        Media sound = new Media(new File("src/main/resources/audio/movimientoDerecha.mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML void btnUp() {
        tfComandos.setText(tfComandos.getText()+"U");
        Media sound = new Media(new File("src/main/resources/audio/movimientoArriba.mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML void btnBorrar() {
        String palabraInicial= tfComandos.getText();
        String palabraFinal="";
        char arreglo[];
        arreglo=palabraInicial.toCharArray();
        for (int i=0;i<palabraInicial.length()-1;i++){
            palabraFinal=palabraFinal+arreglo[i];
        }
        tfComandos.setText(palabraFinal);
    }

    int movidas = 0;
    String comandos;

    Timeline tl1,tl2,tl3;

    @FXML void btnIniciar(){

        comandos = tfComandos.getText();

        ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(HelloController.playerYInitial)).getChildren().get(HelloController.playerXInitial)).setImage(imagenCuadro);

        if (comandos.toCharArray()[movidas]=='U'&&HelloController.playerYInitial-1>=0) HelloController.playerYInitial=HelloController.playerYInitial-1;
        else if (comandos.toCharArray()[movidas]=='D'&&HelloController.playerYInitial+1<=margenY) HelloController.playerYInitial=HelloController.playerYInitial+1;
        else if(comandos.toCharArray()[movidas]=='L'&&HelloController.playerXInitial-1>=0) HelloController.playerXInitial=HelloController.playerXInitial-1;
        else if(comandos.toCharArray()[movidas]=='R'&&HelloController.playerXInitial+1<=margenX) HelloController.playerXInitial=HelloController.playerXInitial+1;

        tl1 = new Timeline();
        tl1.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25),actionEvent -> ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(HelloController.playerYInitial)).getChildren().get(HelloController.playerXInitial)).setImage(imagenRobot)));
        tl1.play();

        System.out.println(movidas);
        movidas++;

        if(movidas == comandos.length()) {
            if(HelloController.playerXInitial==HelloController.starrXInitial&&HelloController.playerYInitial==HelloController.starYInitial) reiniciar();
            else System.out.println("perdiste "+HelloController.starYInitial+" "+HelloController.playerYInitial+" "+HelloController.starrXInitial+" "+HelloController.playerXInitial);
            tfComandos.setText("");
            movidas = 0;
        }//if
        else {
            tl2 = new Timeline();
            tl2.getKeyFrames().add(new KeyFrame(Duration.seconds(.4), actionEvent -> btnIniciar()));
            tl2.play();
        }//else

    }//btnIniciar

    Random random;

    public void reiniciar(){

        tl3 = new Timeline();

        tl3.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent -> {

            ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(playerYInitial)).getChildren().get(playerXInitial)).setImage(imagenCuadro);
            ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(starYInitial)).getChildren().get(starrXInitial)).setImage(imagenCuadro);

            System.out.println("ganaste"+starYInitial+" "+playerYInitial+" "+starrXInitial+" "+playerXInitial);
            random = new Random();
            playerXInitial = random.nextInt(margenX);
            playerYInitial = random.nextInt(margenY);
            starrXInitial = random.nextInt(margenX);
            starYInitial = random.nextInt(margenY);

            ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(playerYInitial)).getChildren().get(playerXInitial)).setImage(imagenRobot);
            ((ImageView)((HBox)HelloController.contenedorMismo.getChildren().get(starYInitial)).getChildren().get(starrXInitial)).setImage(imagenStar);

        }));

        tl3.play();

    }

}