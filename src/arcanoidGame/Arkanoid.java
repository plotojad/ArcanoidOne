package arcanoidGame;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Arkanoid {

    public static Arkanoid game;
    private int width;
    private int height;
    private Ball ball;
    private Stand stand;
    private List<Brick> bricks = new ArrayList<>();
    private boolean isGameOver = false;

    public static void main(String[] args) {
        game = new Arkanoid(20, 30);

        Ball ball = new Ball(10, 29, 2, 95);
        game.setBall(ball);

        Stand stand = new Stand(10, 30);
        game.setStand(stand);

        game.getBricks().add(new Brick(3, 3));
        game.getBricks().add(new Brick(7, 5));
        game.getBricks().add(new Brick(12, 5));
        game.getBricks().add(new Brick(16, 3));

        try {
            game.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Arkanoid(int width, int height) {
        this.width = width;
        this.height = height;
    }



    public Ball getBall() {
        return ball;
    }

    public Stand getStand() {
        return stand;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public void run() throws InterruptedException {
        Canvas canvas = new Canvas(width, height);
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (!isGameOver){
            if (keyboardObserver.hasKeyEvenrs()){
                KeyEvent event = keyboardObserver.getEventFromTop();

                switch (event.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        stand.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        stand.moveRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        ball.start();
                        break;
                }
            }

            move();

            checkBRicksBump();
            checkStandBump();
            checkEndGame();

            canvas.clear();
            draw(canvas);
            canvas.print();

            Thread.sleep(300);
        }
        System.out.println("Game Over!");
    }

    private void move() {
        ball.move();
        stand.move();
    }

    private void draw(Canvas canvas) {
        drawBorders(canvas);
        for (Brick current : bricks){
            current.draw(canvas);
        }
        ball.draw(canvas);
        stand.draw(canvas);
    }

    private void drawBorders(Canvas canvas) {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }
    }

    private void checkBRicksBump(){
        for (Brick currentBrick : bricks){
            if (ball.isIntersec(currentBrick)){
                double angel = Math.random() * 360;
                ball.setDirection(angel);
                bricks.remove(currentBrick);
                break;
            }
        }
    }

    private void checkStandBump(){
        if (ball.isIntersec(stand)){
            double angel = 80 + Math.random() * 20;
            ball.setDirection(angel);
        }
    }

    private void checkEndGame(){
        if (ball.getY() >= height){
            isGameOver = true;
        }
    }




}
