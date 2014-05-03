package tankWar;
import java.awt.*;

public class Explode {
	int x, y;
	private boolean live = true;
	
	private TankClient tc ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] image = {
//			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
//			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif"))
		
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\0.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\1.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\2.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\3.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\4.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\5.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\6.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\7.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\8.gif"),
		tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\8.gif")
	};
	int step = 0;//just for expressing the explode's progress.

	
	/* the Explode class Construction function*/
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	/* showing explode-phenomenon */
	public void draw(Graphics g) {
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == image.length) {
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.drawImage(image[step], x, y, null);
		g.setColor(c);
		
		step ++;
	}
}
