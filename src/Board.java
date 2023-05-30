import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    int B_width=400;
    int B_height=400;
    int MaxDots=1600;

    int[] x=new int[MaxDots];
    int[] y=new int[MaxDots];
    int dots;
    int dotSize=10;
    int apple_x;
    int apple_y;

    Image body,head,apple;
    Timer timer;
    int Delay=300;

    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;

    boolean inGame=true;

    Board(){
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_width,B_height));
        setBackground(Color.black);
        intGame();
        loadImage();
    }

    public void intGame(){
        dots=3;
        //Initialize snake position
        x[0]=50;
        y[0]=50;
        for(int i=1;i<dots;i++)
        {
            x[i]=x[0]+dotSize*i;
            y[i]=y[0];
        }
        // Initialize apple position
        apple_x=150;
        apple_y=150;
        timer=new Timer(Delay,this);
        timer.start();
    }
    //load images from resource folder to image folder
    public void loadImage(){
        ImageIcon bodyicon=new ImageIcon("src/resources/dot.png");
        body=bodyicon.getImage();
        ImageIcon headicon=new ImageIcon("src/resources/head.png");
        head=headicon.getImage();
        ImageIcon appleicon=new ImageIcon("src/resources/apple.png");
        apple=appleicon.getImage();

    }

    public void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]-=dotSize;
        }
        if(rightDirection)
        {
            x[0]+=dotSize;
        }
        if(upDirection)
        {
            y[0]-=dotSize;
        }
        if(downDirection)
        {
            y[0]+=dotSize;
        }
    }
    public void checkApple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            dots++;
            locateApple();
        }
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }
    //Draw image
    public void doDrawing(Graphics g)
    {
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }else
            gameOver(g);
    }
    //Randomize apple position
    public void locateApple(){
        apple_x=((int)(Math.random()*39))*dotSize;
        apple_y=((int)(Math.random()*39))*dotSize;
    }


    public void checkCollision(){
        for(int i=1;i<dots;i++){
            if((i>4) && x[0]==x[i] && y[0]==y[i])
            {
                inGame=false;
            }
        }
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>B_width)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(y[0]>B_height)
        {
            inGame=false;
        }
    }
    //Display Game Over
    public void gameOver(Graphics g)
    {
        String msg="Game Over";
        int score=(dots-3)*100;
        String scoremsg="Score:-"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_width-fontMetrics.stringWidth(msg))/2,B_height/4);
        g.drawString(scoremsg,(B_width-fontMetrics.stringWidth(msg))/2,3*B_height/4);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame) {
            checkApple();
            move();
            checkCollision();
        }
        repaint();
    }

    //make snake move


    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && !downDirection){
                upDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection)
            {
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
        }
    }





}
















