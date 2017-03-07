import java.awt.*;
import java.util.Random;

/**
 * Created by 桢 on 2017/2/21.
 */
public class Egg {

    public static int row, col;
    Random r=new Random();

    public Egg() {
        location();
    }

    public static void main(String[] args){
    }

    public void location(){
        this.row=r.nextInt(Yard.ROWS-3)+2;
        this.col=r.nextInt(Yard.COLS-2)+1;
    }
    public void draw(Graphics g){
        Color c=g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(col*Yard.WIDTH,row*Yard.WIDTH,Yard.WIDTH,Yard.WIDTH);
        g.setColor(c);
    }
}
