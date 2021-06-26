package PIS_HU1;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import java.util.Arrays;

public class Draw extends PApplet {
    GameEngine game = new GameEngine();
    boolean bot = false;
    boolean drawGame=false;
    public static void main(String[] args) {
        PApplet.runSketch(new String[]{""}, new Draw());
    }

    public void settings() {

        size(650, 680);

    }

    public void setup() {
        PImage icon = loadImage("C:\\Users\\Mehmet\\IdeaProjects\\PIS_HU1\\app\\Pictures\\connect-four.png");
        game.reset();
        mainMenu();
        surface.setTitle("Connect Four!");
        surface.setIcon(icon);

    }

    public void draw() {
        if(drawGame) {
            background(50,96,114);
            drawBoard();
        }
    }

    public void drawBoard() {
        for (int i = 1; i < 8; i++) {
            textSize(30);
            fill(255);
            text(i, (width/8) * i-10, 40);
        }
        line(0, 60, 800, 60);
        int x = width/8;
        int y = 120;
        for (int h = 5; h >= 0; h--) {
            int w = h;
            int i = 1;
            while (w < 49) {
                long mask = 1L << w;
                if ((game.playerBoard[0] & mask) != 0L) {
                    fill(252, 215, 3);
                    ellipse(x * i, y, 75, 75);

                } else if ((game.playerBoard[1] & mask) != 0L) {
                    fill(252, 44, 3);
                    ellipse(x * i, y, 75, 75);

                } else {
                    fill(255);
                    ellipse(x * i, y, 75, 75);
                }
                i++;
                w += 7;
            }
            y += 100;
        }
        if (game.isWin(game.playerBoard[0])) {
            background(3, 90, 252);
            textSize(60);
            fill(255);
            text("Player 2 WON",100,100);
            textSize(30);
            text("Press R for restart",100,230);
        } else if (game.isWin(game.playerBoard[1])) {
            background(3, 90, 252);
            textSize(60);
            fill(255);
            if(bot) text("Bot WON", 100, 100);
            else text("Player 2 WON", 100,100);
            textSize(30);
            text("Press R for restart",100,230);
        }
    }

    public void mainMenu(){
        PImage start = loadImage("app/Pictures/PinClipart.com_tired-parents-clip-art_1966405.png");
        background(50,96,114);
        textSize(60);
        fill(255);
        text("Connect Four",100,100);
        textSize(30);
        text("Press P to play against a Player",100,230);
        text("Press B to play against a Bot",100,280);
        start.resize(275,275);
        image(start,175,350);
    }

    @Override
    public void keyPressed() {
        if (key == '0') {
            System.out.println(game.toString());
        }
        if (key == '1') {
            game.makeMove(0);
            if(bot) bot();
        }
        if (key == '2') {
            game.makeMove(1);
            if(bot) bot();
        }
        if (key == '3') {
            game.makeMove(2);
            if(bot) bot();
        }
        if (key == '4') {
            game.makeMove(3);
            if(bot) bot();
        }
        if (key == '5') {
            game.makeMove(4);
            if(bot) bot();
        }
        if (key == '6') {
            game.makeMove(5);
            if(bot) bot();
        }
        if (key == '7') {
            game.makeMove(6);
            if(bot) bot();
        }
        if (key == 'r') {
            background(3, 90, 252);
            game.reset();
            mainMenu();
            bot=false;
            drawGame=false;
            mainMenu();
            if(drawGame) drawBoard();
        }
        if (key == 'u') {
            game.undoMove();
        }
        if(key=='p'){
            drawGame=true;
        }
        if(key=='b'){
            bot=true;
            drawGame=true;
        }
    }

    public void bot(){
        GameEngine test = new GameEngine();
        test.playerBoard = Arrays.copyOf(game.playerBoard, game.playerBoard.length);
        test.move = Arrays.copyOf(game.move, game.move.length);
        test.heightCol = Arrays.copyOf(game.heightCol, game.heightCol.length);
        test.count = game.count;
        game.makeMove(test.chooseBestMove(test, 100));
    }
}




