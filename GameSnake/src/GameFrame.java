import javax.swing.*;

public class GameFrame extends JFrame {
    ImageIcon imageIcon = new ImageIcon("snake.jpg");


    GameFrame(){
        this.setIconImage(imageIcon.getImage());
        this.add(new GamePanel());
        this.setTitle("snakeGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }



}
