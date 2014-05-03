package tankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	public static final int X_SPEED = 5;
	public static final int Y_SPEED = 5;
	public static int score = 0;
	
	public static final int TANK_WIDTH = 47;
	public static final int TANK_HEIGHT = 47;
	
	private boolean live = true;
	private BloodBar bb = new BloodBar();
	int ID;
	private int life = 100;
	boolean isfire = false;
	boolean isSuperfire = false;
	TankClient tc;
	
	private boolean good;//???????????????????????????????????????????????????????????????
	
	
	private int oldX, oldY;
	
	private static Random r = new Random();
	
	private boolean bL=false, bU=false, bR=false, bD = false;
	
	
	public Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	private int step = r.nextInt(12) + 3;//?????????????????????????????????????????????
	private int count = 0;
	private static Toolkit tk1 = Toolkit.getDefaultToolkit();
	private static Image[] image = null;
	
	public void setDir(Direction dir) {
		this.dir = dir;
	}
//???????????????????????????????????????????????????????????????????
//	public final int ordinal() {
//		return dir.ordinal();
//	}

	private int x, y;
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	/*set the tank-direction with the tank-image*/ 
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	static{
		image = new  Image[]{
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
//		tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif"))
//				
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankdown.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankleft0.png"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankleft_down.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankleft_up.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankright.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankright_down.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankright_up.gif"),
//				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankup.gif")
				
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankD.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankL.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankLD.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankLU.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankR.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankRD.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankRU.gif"),
				tk1.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankU.gif")
				
		};
		
		imgs.put("D", image[0]);
		imgs.put("L", image[1]);
		imgs.put("LD", image[2]);
		imgs.put("LU", image[3]);
		imgs.put("R", image[4]);
		imgs.put("RD", image[5]);
		imgs.put("RU", image[6]);
		imgs.put("U", image[7]);
	}
	
	/* the construction0 of Tank class*/ 
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	/*the construction1 of Tank class*/
	public Tank(int x, int y, boolean good, Direction dir,TankClient tc) {
	//public Tank(int x, int y, boolean good, Direction dir) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	/*in the tankclient,the showing-tank function*/  
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {//???????????????????????????????????????
				score++;
				tc.tankenemies.remove(this);
				//tankenemies.remove(this);
			}
			return;
		}
		
		//Color c = g.getColor();
		
		if(good) 
		{
			bb.draw(g);
			g.drawString("Tank                id:   " + ID, 5, 100);//????I don't understand the meaning of good?????
			g.drawString("Your          Score:   " + score, 5, 120);
		}
	
		switch(ptDir) {/*draw the initial-direction tank when it is beginning */
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
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		default://If you have some time ,you should deal with it.
			break;
		}
		
		move();
	}
	
	void move() {
		
		this.oldX = x;
		this.oldY = y;
		
		switch(dir) {
		case L:
			x -= X_SPEED;
			break;
		case LU:
			x -= X_SPEED;
			y -= Y_SPEED;
			break;
		case U:
			y -= Y_SPEED;
			break;
		case RU:
			x += X_SPEED;
			y -= Y_SPEED;
			break;
		case R:
			x += X_SPEED;
			break;
		case RD:
			x += X_SPEED;
			y += Y_SPEED;
			break;
		case D:
			y += Y_SPEED;
			break;
		case LD:
			x -= X_SPEED;
			y += Y_SPEED;
			break;
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP) {//what's the meanning of these?
			this.ptDir = this.dir;
		}
		
		/*adjust the tank's wrong location */
		if(x < 5) x = 0;
		if(y < 15) y = 15;
		if(x + Tank.TANK_WIDTH > TankClient.getwidth()) x = TankClient.getwidth() - Tank.TANK_WIDTH;
		if(y + Tank.TANK_HEIGHT > TankClient.getheigth()-50) y = TankClient.getheigth() - Tank.TANK_HEIGHT-47;
		
		if(!good) {//???????????I don't understand
			Direction[] dirs = Direction.values();//?????????????????????
			if(step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
				
			}			
			step --;
			
			if(r.nextInt(40) > 38)/*this is the codes of controlling the tank firing-time*/
				this.fire();
			
		}	
		if(isfire)
			fire();
		isfire = false;
		if(isSuperfire)
			superFire();
		isSuperfire = false;
		
	
	}
	
	private void stay() {/*stand on the old ground*/
		x = oldX;
		y = oldY;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F2 ://the begin new life's function-key*/
//			if(!this.live) {//????????????????????????????????????????????????????????????????
//				if(tc.isclick_enter){
//				TankRevivalMsg msg = new TankRevivalMsg(this);//?????????????????????????????????????????
//				tc.netclient.send(msg);
//				}
				this.live = true;
				this.life = 100;
			//}
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		locateDirection();
	}
	
	/*define the tank move-direction*/
	void locateDirection() {
		Direction olddir = dir;
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
//		if((dir!=olddir)&&tc.isclick_enter){
//			TankMovMsg msg = new TankMovMsg(ID,dir);//the same thing :I don't understand!?????????????????????????????????????
//			tc.netclient.send(msg);
//		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_SPACE:
			isfire = true;
//			if(tc.isclick_enter){//??????????????????????????????????????????????????????????????????????????????
//			MissileMsg msg = new MissileMsg(ID,isfire);
//			tc.netclient.send(msg);
//			}
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		case KeyEvent.VK_A :
			isSuperfire = true;
//			if(tc.isclick_enter){
//			SuperFireMsg supermsg = new SuperFireMsg(ID,isSuperfire);
//			tc.netclient.send(supermsg);
//			}
			break;
		}
		locateDirection();		
	}
	
	
	/*the tank fires */
	public Missile fire() {
		if(!live) return null;
		/*This place need to be well dealed with,now what we did is not good*/
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
//		int x = this.x + Tank.WIDTH/2;
//		int y = this.y + Tank.HEIGHT/2;
//		int x = this.x+Tank.WIDTH/2;
//		int y = this.y+Tank.HEIGHT/2;
//		switch(ptDir){
//		case L:
//			y+=Tank.HEIGHT/2-Missile.WIDTH/2;
//			break;
//		case R:
//			x+=Tank.WIDTH;
//			y+=Tank.HEIGHT/2-Missile.WIDTH/2;
//			break;
//		case U:
//			x+=Tank.WIDTH/2-Missile.WIDTH/2;
//			break;
//		case D:
//			x+=Tank.WIDTH/2-Missile.WIDTH/2;
//			y+=Tank.HEIGHT;
//			break;
//		default:
//			break;
//		
//		}
		Missile m = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
//		int x = this.x+Tank.WIDTH/2;
//		int y = this.y+Tank.HEIGHT/2;
//		switch(dir){
//		case L:
//			y+=Tank.HEIGHT/2-Missile.WIDTH/2;
//			break;
//		case R:
//			x+=Tank.WIDTH;
//			y+=Tank.HEIGHT/2-Missile.WIDTH/2;
//			break;
//		case U:
//			x+=Tank.WIDTH/2-Missile.WIDTH/2;
//			break;
//		case D:
//			x+=Tank.WIDTH/2-Missile.WIDTH/2;
//			y+=Tank.HEIGHT;
//			break;
//		default:
//			break;
//		
//		}

		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/*judge wether the two things are collide*/
	public Rectangle getRect() {
		return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}

	/*return the tank's live*/
	public boolean isLive() {
		return live;
	}
	/*set the tank's life*/
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}
	
	/**
	 * 撞墙
	 * @param w 被撞的墙
	 * @return 撞上了返回true，否则false
	 */
	/*rectangle has the function-intersects(),to judge wether the two things is collide*/
	public boolean collidesWithWall(Wall w) {
		if(this.live && (this.getRect().intersects(w.getRect0()) || this.getRect().intersects(w.getRect1()))) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/*rectangle has the function-intersects(),to judge wether collide with enemy tanks*/
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	/*rectangle has the function-intersects(),to judge wether collide with our own tanks---in the web-war circumstance.*/
	public boolean collidesWithTank(Tank tank) {
		if(this.live && tank.isLive() && this.getRect().intersects(tank.getRect())) {
			this.stay();
			tank.stay();
			return true;
		}
		return false;
	}
	
	private void superFire() {
		Direction[] dirs = Direction.values();/*turn the Direction's values into array[]*/
		for(int i=0; i<8; i++) {
			fire(dirs[i]);//???????????????????????
		}
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	/*the inner class---BloodBar*/
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, TANK_WIDTH, 10);
			int w = TANK_WIDTH * life/100 ;
			g.fillRect(x, y-10, w, 10);/*in the head of my own tank,draw the bloodbar*/
			g.setColor(c);
		}
	}
	
	/*eatting the life-blood will make tank's life full*/
	public boolean eat(Blood b) {
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}
}
	
	
	