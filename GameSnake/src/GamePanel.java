
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 30;

    static final int DELAY = 80;
    final int[] x = new int[UNIT_SIZE];
    final int[] y = new int[UNIT_SIZE];
    int bodyparts = 5;  // initial part
    int applesEaten;
    int appleX;
    int appleY;
    char diraction = 'R';
    boolean running = false;
    Timer timer;
    Random random;



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new TAdapter());
        startGame();
    }

    public void startGame() {
        // that means when press run program will generate new apple and start move the object
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g ){

        if(running){
            /*            // just to show the lines of frame and what the place of random apples
            for(int i = 0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
             */
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            for(int i = 0;i<bodyparts;i++){

                // this if statement to show the head of object
                if(i == 0 ){
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    // here to add new tail for my object
                    g.setColor(new Color(194, 253, 193));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            // this part for showing  the score above the screen while game is running
            g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten ,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else {     //    that means running is false
            gameOver(g);
        }

    }
    public void newApple(){
        // generate random pace for the nue apple without go outside of frame
        appleX = random.nextInt( SCREEN_WIDTH /UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt( SCREEN_HEIGHT /UNIT_SIZE)*UNIT_SIZE;


    }
    public void move(){
        for(int i = bodyparts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch(diraction){
            // this cases for start  direction I need
            case'U' :
                y[0] = y[0] - UNIT_SIZE;
                break;
            case'D' :
                y[0] = y[0] + UNIT_SIZE;
                break;
            case'L' :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case'R' :
                x[0] = x[0] + UNIT_SIZE;
                break;

        }

    }
    public void checkApple(){
        // for eaten the apple..... will run this when  the head equal the place of apple
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyparts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkcollisions(){
        for(int i = bodyparts;i>0;i--){
            // here if I touch  my body the game will be stopped
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        // here if I go outside from left  the game will be stopped
        if(x[0]<0 ){
            running = false;

        }
        // here if I go outside from right  the game will be stopped
        if(x[0]> SCREEN_WIDTH){
            running = false;

        }
        // here if I go outside from up  the game will be stopped
        if(y[0]< 0 ){
            running = false;

        }
        // here if I go outside from down  the game will be stopped
        if(y[0]>SCREEN_HEIGHT ){
            running = false;

        }
        // of anything do about above  the game will be stopped
        if(!running){
            timer.stop();

        }
    }

    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten ,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());

        //GameOver

        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GameOver ",(SCREEN_WIDTH - metrics2.stringWidth("GameOver"))/2,SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // after any click I do the program will check about these
        if(running){
            move();
            checkApple();
            checkcollisions();
        }
        repaint();
    }

    public class TAdapter extends KeyAdapter{

        public void	keyPressed(KeyEvent e){
            // here to i can move the direction and not allowed to change the opposite direction.
            switch (e.getKeyCode()){
                case   KeyEvent.VK_LEFT:
                    if ( diraction != 'R'){
                        diraction = 'L';
                    }
                    break;
                case   KeyEvent.VK_RIGHT:
                    if ( diraction!= 'L'){
                        diraction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if ( diraction != 'D'){
                        diraction= 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (  diraction!= 'U'){
                        diraction= 'D';
                    }
                    break;
            }
        }
    }
}
