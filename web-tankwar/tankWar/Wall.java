package tankWar;

import java.awt.*;

import javax.swing.*;

public class Wall {
	int x, y;
	private static final int WALL_W=200;
	private static final int WALL_H=20;
	TankClient tc ;
	private static Toolkit tk0 = Toolkit.getDefaultToolkit();/*in order to get images or get images' measure.*/
	private static Image image2 = tk0.getImage(Explode.class.getClassLoader().getResource("images/wall0.jpg"));
	private static Image image3 = tk0.getImage(Explode.class.getClassLoader().getResource("images/wall1.jpg"));
	
	public Wall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image2, x, y, tc);
		//g.fillRect(x, y, w, h);
		g.drawImage(image3,300, 120, tc);
	}
	
	public Rectangle getRect0() {
		return new Rectangle(x, y, WALL_H, WALL_W);
	}
	public Rectangle getRect1(){
		return new Rectangle(300, 120, WALL_W,WALL_H);
	}
}
