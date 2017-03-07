import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by 桢 on 2017/2/21.
 */
public class Snake {
    static int size=1 ;
    public int score=0;
    Node n1=new Node(Yard.ROWS / 2, Yard.COLS / 2, 1);
    Node head = n1;
    Node tail = n1;



    public Snake() {
        head=n1;
        tail=n1;
        size=1;
    }

    public void addToHead() {
        Node n = new Node(head.row, head.col, head.dir);
        switch (n.dir) {
            case 1:
                n = new Node(head.row - 1, head.col, head.dir);
                break;
            case 2:
                n = new Node(head.row + 1, head.col, head.dir);
                break;
            case 3:
                n = new Node(head.row, head.col - 1, head.dir);
                break;
            case 4:
                n = new Node(head.row, head.col + 1, head.dir);
                break;
        }
        n.next = head;
        head.prev = n;
        head = n;
    }

    public void deleteFromTail() {
        if(tail==null) return;
            tail= tail.prev;
            tail.next = null;
    }

    private void move() {
        addToHead();
        deleteFromTail();
    }

    private void eat(){
        if(head.row==Egg.row&&head.col==Egg.col){
            System.out.println("Eat");
            Egg egg=new Egg();
            addToHead();
            score=size+(200-Yard.sleeptime)/40;
            size++;

        }
    }

    public boolean isAlive(){
        boolean flag=true;
        if(head.row<1||head.row>24||head.col<0||head.col>24){
            flag=false;
            System.out.println("aaa");
        }
        if(size>4){
        for(Node n=head.next;n!=null;n=n.next){
            if(head.row==n.row&&head.col==n.col){
                flag=false;
                System.out.println("bbb");
            }
        }}
        return flag;
    }

    public void draw(Graphics g) {
        if(size<=0) return;
        move();
        eat();
        for (Node node2 = head; node2 != null; node2 = node2.next) {
            node2.draw(g);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                if (head.dir != 2) { //Prevent the snake which is bigger than 2 nodes to go along the direction against its head.
                    if(size>1&&head.col!=head.next.col){ //Prevent the snake to go along the direction against its head when user press too fast.
                        head.dir = 1;
                        break;
                    }else if(size==1){
                    head.dir = 1;}
                }
                break;
            case KeyEvent.VK_S:
                if (head.dir != 1) {
                    if(size>1&&head.col!=head.next.col){
                        head.dir = 2;
                        break;
                    }else if(size==1){
                    head.dir = 2;}
                }
                break;
            case KeyEvent.VK_A:
                if (head.dir != 4) {
                    if(size>1&&head.row!=head.next.row){
                        head.dir = 3;
                        break;
                    }else if(size==1){
                    head.dir = 3;}
                }
                break;
            case KeyEvent.VK_D:
                if (head.dir != 3) {
                    if(size>1&&head.row!=head.next.row){
                        head.dir = 4;
                        break;
                    }else if(size==1){
                    head.dir = 4;}
                }
                break;
        }
    }

    public class Node {
        int row, col, dir;
        Node next = null;
        Node prev = null;

        public Node(int nrow, int ncol, int ndir) {
            this.row = nrow;
            this.col = ncol;
            this.dir = ndir;
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(col * Yard.WIDTH, row * Yard.WIDTH, Yard.WIDTH, Yard.WIDTH);
            g.setColor(c);
        }
    }
}
