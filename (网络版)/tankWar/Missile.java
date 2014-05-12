package tankWar;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int XM_SPEED = 5;
	public static final int YM_SPEED = 5;
	
	public static final int MISSILE_WIDTH = 10;
	public static final int MISSILE_HEIGHT = 10;
	
	int x, y;
	Direction dir;
	
	private boolean good;
	private boolean live = true;
	
	private TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] image = null;
	
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	static{
		image = new  Image[]{
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif"))
		
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif"))
		};
		
		imgs.put("D", image[0]);
		imgs.put("L", image[1]);
		imgs.put("LD", image[2]);
		imgs.put("LU", image[3]);
		imgs.put("R", image[4]);
		imgs.put("RD", image[5]);
		imgs.put("RU", image[6]);
		imgs.put("U",  image[7]);
	}
	
	/*the missile class Construction function*/
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	/*the showing-missile function---draw()*/
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		if(good)g.setColor(Color.RED);//?????????????????????????????????????????
		else g.setColor(Color.BLACK);
		//g.fillOval(x, y, WIDTH, HEIGHT);
		
		g.setColor(c);
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
//			System.out.println("This is the \"D\" missile!");
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		default:
			break;
		}
		move();
	}

	private void move() {
		
		
		switch(dir) {
		case L:
			x -= XM_SPEED;
			break;
		case LU:
			x -= XM_SPEED;
			y -= YM_SPEED;
			break;
		case U:
			y -= YM_SPEED;
			break;
		case RU:
			x += XM_SPEED;
			y -= YM_SPEED;
			break;
		case R:
			x += XM_SPEED;
			break;
		case RD:
			x += XM_SPEED;
			y += YM_SPEED;
			break;
		case D:
			y += YM_SPEED;
			break;
		case LD:
			x -= XM_SPEED;
			y += YM_SPEED;
			break;
		case STOP:
			break;
		}
		/*To adjust the missile wrong location*/
		if(x < 0 || y < 0 || x > TankClient.getwidth() || y > TankClient.getheigth()) {
			live = false;
		}		
	}
	/* return missile's live-state*/
	public boolean isLive() {
		return live;
	}
	/* return missile's good-state.*/
	public boolean isGood()
	{
		return good;
	}
	
	/*get the thing's rectangle in order to judge whether collide someting .*/
	public Rectangle getRect() {
		return new Rectangle(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
	}
	
	/*judge whether missile hit our own tank*/
	public boolean hitTank(Tank t) {
		if(this.live && (this.getRect().intersects(t.getRect()) || this.getRect().intersects(t.getRect()))&& t.isLive() && this.good != t.isGood()) {
			if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0)
				{
					t.setLive(false);
				}
			} else {
				t.setLive(false);
			}
			
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	/*judge whether missiles hit or not.*/
	public boolean hitMissile(Missile m) {
		if(this.live && (this.getRect().intersects(m.getRect()) || this.getRect().intersects(m.getRect()))&& m.isLive() && this.good != m.isGood()) {
			
			this.live = false;
			m.live=false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	/* judge whether missile hit otehr missiles.*/
	public boolean hitMissiles(List<Missile> Missiles) {
		for(int i=0; i<Missiles.size(); i++) {
			if(hitMissile(Missiles.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/*judge whether missile hit our own tank*/
	public boolean hit_web_tank(Tank t) {
		if(this.live && (this.getRect().intersects(t.getRect()) || this.getRect().intersects(t.getRect()))&& t.isLive() && this.good != t.isGood()) {
//		if(this.live && (this.getRect().intersects(t.getRect()) || this.getRect().intersects(t.getRect()))&& t.isLive()) {
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) 
				{
//					t.setLive(false);
					if(t.ID==tc.tankhero.ID)
					{
						t.setLive(false);
						TankDieMsg msg = new TankDieMsg(t);
						tc.nc.send(msg);
					}
				}
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	/* judge whether missile hit the enemy tanks*/
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	public boolean hit_web_tanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hit_web_tank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	/*judge whether missile hit the wall*/
	public boolean hitWall(Wall w) {
		if(this.live && (this.getRect().intersects(w.getRect0()) || this.getRect().intersects(w.getRect1()))) {
			this.live = false;
			return true;
		}
		return false;
	}
	
}
