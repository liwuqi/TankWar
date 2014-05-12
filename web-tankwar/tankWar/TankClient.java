package tankWar;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.*;

import javax.swing.*;




public class TankClient extends JFrame// implements ActionListener
{
//	Container container=this.getContentPane();/*produce the container*/
//	JPanel pane0=new JPanel();/*panel is used for the selection*/
//	JPanel pane1;
//	final JLabel tablabel0=new JLabel("帮助");
	JMenuBar menubar=new JMenuBar();  //build the menu class
	JMenu menu0=new JMenu(" 游戏 ");
	JMenu menu1=new JMenu("帮助 ");

	JMenuItem menuitem0=new JMenuItem("单机模式");
	JMenuItem menuitem1=new JMenuItem("网络对战模式");
	JMenuItem menuitem2=new JMenuItem("方向：WASD");
	JMenuItem menuitem3=new JMenuItem("发射子弹：SPACE");
	
	Wall wall=new Wall(100,200,this);
	
	private static String str0=null;
	//private static String str1=null;
	public static int flag=0;
	public static boolean flagconn=false;
	
	
	boolean isclick_enter = false;
	
	/*联网对战中的变量*/
	public static int P_B;
	public static String name=null;
	
	ClinetMgr nc = new ClinetMgr(this);
	public String Server_Ip = nc.getServer_Ip(); 
	
	Blood b = new Blood();
	private Random ran = new Random();
	/*单机模式下的敌我双方*/
	Tank tankhero=new Tank(ran.nextInt(700),ran.nextInt(600),true,Direction.STOP,this);
	List<Tank>tankenemies=new ArrayList<Tank>();
	/*两种模式下都要用到的*/
	List<Explode>explodes=new ArrayList<Explode>();
	List<Missile>missiles=new ArrayList<Missile>();
	/*网络对战模式下的敌我双方*/
	List<Tank>challengers0=new ArrayList<Tank>();
	List<Tank>challengers1=new ArrayList<Tank>();
//	List<Tank>mytanks;
	public static List<Tank>mytanks=new ArrayList<Tank>();
	List<Tank>myenemies;
	
	Image offscreenimage=null;
	
//	String str=new String("用户登陆游戏客户端选择游戏模式，可分为单机模式和网络对战模式，选择单机模式可以直接进入游戏状态；选择网络对战模式，"
//			+ "在启动服务器后，玩家需要选择自己的角色，用户名和ID等玩家相关资料，"
//			+ "成功后使用其账号进行登录游戏（只有成功登录的玩家才能参与网络对战游戏功能）；"
//			+ "登陆玩家就可以通过控制键盘操作，加入对战游戏。"+"\nWASD---控制方向"+"\nSPACE---发射子弹");

	private static Toolkit tk = Toolkit.getDefaultToolkit();/*in order to get images or get images' measure.*/
//	private static Image image0 = tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankview0.jpg");
	private static Image image0 = tk.getImage(Explode.class.getClassLoader().getResource("images/tankview0.jpg"));
	
//	private static Image image1 = tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\background1.png");
	private static Image image1 = tk.getImage(Explode.class.getClassLoader().getResource("images/background1.png"));
	/*the screen's window size*/
	private static final int WIDTH=786;
	private static final int HEIGTH=665;
	AudioClip audio;  /*in order to play music*/
	
	MyStartPanel msp=null;
	MySecondPanel mp=null;
	MyThirdPanel mtp=null;
	
//	Thread thread0;
//	Thread thread1;
//	Thread thread2;
	
	
	
	public static int getwidth()
	{
		return WIDTH;
	}
	public static int getheigth()
	{
		return HEIGTH;
	}
	
	/*the Construction function*/
	public TankClient()
	{
		//this.setJMenuBar(menubar);//put the above menu class into window-menu
				menubar.setBorderPainted(false);				

				menuitem0.addActionListener(new PaneControl(this));
				menuitem1.addActionListener(new PaneControl(this));
				
				menu0.add(menuitem0);
				menu0.add(menuitem1);
				
				menu1.add(menuitem2);
				menu1.add(menuitem3);
				
				menubar.add(menu0);
				menubar.add(menu1);
				this.setJMenuBar(menubar);
//				setContentPane(pane1);
				
				try{
//				audio=Applet.newAudioClip(new URL("file:\\H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\video\\crime_punishment.wav"));
				audio=Applet.newAudioClip(new URL(""+Explode.class.getClassLoader().getResource("video/crime_punishment.wav")));
//				URL url=new File(""+Explode.class.getClassLoader().getResource("images/crime_punishment.wav")).toURI().toURL();
//				String str=""+Explode.class.getClassLoader().getResource("video/crime_punishment.wav");
//				System.out.println(str);
				//URL url=new File("file://E:/MyEclipse/workspace/MyTank/bin/video/crime_punishment.wav").toURI().toURL();
				//audio=Applet.newAudioClip(url);
				}catch(Exception e){
					e.printStackTrace();
				}
				audio.loop();
			
				msp=new MyStartPanel(this);
				Thread startpane=new Thread(msp);
				startpane.start();
				this.add(msp);
				
			
				this.setSize(WIDTH,HEIGTH);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.setTitle("坦克大战");
				//pack();
				this.setVisible(true);
				this.setResizable(false);	
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
	}
	
	private class PaneControl implements ActionListener{
		TankClient t;
		public PaneControl(TankClient t)
		{
			this.t=t;
		}
		public void actionPerformed(ActionEvent e)
		{
			JMenuItem meitem=(JMenuItem)e.getSource();
			str0=meitem.getText();
			//System.out.println(str0);
			if(str0.equals(new String("单机模式")))
			{
				flag=1;
				this.t.remove(msp);
				mp=new MySecondPanel(this.t);
				Thread mt =new Thread(mp);
				mt.start();
				this.t.add(mp);
				this.t.addKeyListener(new KeyMonitor());
				this.t.setVisible(true);
			}	
			else if(str0.equals(new String("网络对战模式")))
			{
				flag=2;
				new ConnDialog(this.t).connect();
			
				//this.remove(msp);
				//while(flag!=3);
				mtp=new MyThirdPanel(this.t);
				Thread mt =new Thread(mtp);

				mt.start();
				this.t.add(mtp);
				this.t.addKeyListener(new KeyMonitor());
				this.t.setVisible(true);
			
			
			
		}
	}
	}
	 
	
	
	
	/*need more attention to understand?????????????????????????????????????????????????????????????????????*/	
//	public void update(Graphics g) {
//		if(offscreenimage == null) {
//			offscreenimage = this.createImage(WIDTH, HEIGTH);
//		}
//		Graphics gOffScreen = offscreenimage.getGraphics();
//		Color c = gOffScreen.getColor();
//		gOffScreen.setColor(Color.CYAN);
//		gOffScreen.fillRect(0, 0, WIDTH, HEIGTH);
//		//gOffScreen.drawImage(image, 0, 0, null);
//		gOffScreen.setColor(c);
//		paint(gOffScreen);
//		g.drawImage(offscreenimage, 0, 0, null);
//	}
	

	/**
	 * 本方法显示坦克主窗口
	 *
	 */
	public static void main(String args[])
	{
		TankClient tankwar=new TankClient();
	
	}
	
	
	
	
	
	/*all kinds of inner classes*/
	
	//????????????????????????????????????????????????????????????????????need fixing so that it will be suit for webbing mode.
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			tankhero.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
//			if(key == KeyEvent.VK_ENTER){??????????????????????????????????????????????????????????????
//				ConDialog cd = new ConDialog();
//			}
//			else{
//				tankhero.keyPressed(e);
//			}
			tankhero.keyPressed(e);
		}
		
	}
	
	private class MyStartPanel extends JPanel implements Runnable
	{
		TankClient tc;
		public MyStartPanel(TankClient tc)
		{
			this.tc=tc;
		}
		public void paint(Graphics g)
		{
			super.paint(g);
			g.drawImage(image0, 0,0,780,600,tc);
			this.repaint();
		}
		public void run()
		{
			while(true)
			{
				try{
					Thread.sleep(300);
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	private class MySecondPanel extends JPanel implements Runnable
	{
		TankClient tc;
		
		private int counts0=0;
		private int counts1=0;
		private int flaginner0=0;
		
		public MySecondPanel(TankClient tc)
		{
			this.tc=tc;
		}
		public void paint(Graphics g)
		{
			super.paint(g);
			/*
			 * 指明子弹-爆炸-坦克的数量
			 * 以及坦克的生命值
			 */
			g.drawImage(image1, 0, 0, null);
			//g.setFont(font);
			g.setColor(Color.YELLOW);
			g.drawString("missiles  count:   " + missiles.size(), 5, 20);
			g.drawString("explodes count:   " + explodes.size(), 5, 40);
			g.drawString("tanks        count:   " + tankenemies.size(), 5, 60);
			g.drawString("tanks             life:   " + tankhero.getLife(), 5, 80);
			//int reProduceTankCount = Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount"));//????????????????????????????????????????????????
			if(tankenemies.size() <= 0) {
				//for(int i=0; i<reProduceTankCount; i++) {
				for(int i=0; i<11; i++) {
//					tanks.add(new Tank(50 + 40*(i+1), 50, false, Direction.D, this));
					tankenemies.add(new Tank(ran.nextInt(786), ran.nextInt(665), false,Direction.D,tc));
				}
			}
			
			for(int i=0; i<missiles.size(); i++) 
			{
				Missile m = missiles.get(i);
				m.hitMissiles(missiles);
				m.hitTanks(tankenemies);
				m.hitTank(tankhero);
				//m.hitTanks(myTanks);
//				m.hitWall(w1);
//				m.hitWall(w2);
				m.hitWall(wall);
				m.draw(g);
				//if(!m.isLive()) missiles.remove(m);
				//else m.draw(g);
			}
			
			
			
			for(int i=0; i<explodes.size(); i++) {
				Explode e = explodes.get(i);
				e.draw(g);
			}
			
			for(int i=0; i<tankenemies.size(); i++) {
				Tank t = tankenemies.get(i);
//				t.collidesWithWall(w1);
//				t.collidesWithWall(w2);
				t.collidesWithWall(wall);
				t.collidesWithTanks(tankenemies);
				//t.collidesWithTanks(myTanks);
				t.draw(g);
				t.collidesWithTank(tankhero);
				tankhero.collidesWithTank(t);
			}
			
			tankhero.draw(g);
			tankhero.eat(b);
			//g.setColor(Color.gray);
//			w1.draw(g);
//			w2.draw(g);
			if(counts0%500==0 && counts0>300)
			{
				//System.out.println("Blood Bar");
				b.setReliveflag(true);
				//b.draw(g);
			}
			wall.draw(g);
			if(b.getReliveflag()==true)
			{
				b.draw(g);
				flaginner0=1;
			}
			if(flaginner0==1)
			{
				counts1++;
			}
			if(counts1%200==0)
			{
				b.setLive(false);
				counts1=0;
				flaginner0=0;
			}
		}
		
		public void run()
		{
			while(true)
			{
				try{
					counts0++;
					if(counts0 == 1000000000)
						counts0=0;
						this.repaint();
					Thread.sleep(40);
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	
	public void update(Graphics g) {
		if(offscreenimage == null) {
			offscreenimage = this.createImage(WIDTH, HEIGTH);
		}
		Graphics gOffScreen = offscreenimage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.CYAN);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGTH);
		//gOffScreen.drawImage(image, 0, 0, null);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offscreenimage, 0, 0, null);
	}
	
}
	private class MyThirdPanel extends JPanel implements Runnable{
		TankClient tc;
		/*the following avariables are to control the blood-bar*/
		private int counts0=0;
		private int counts1=0;
		private int flaginner1=0;
		
		public MyThirdPanel(TankClient tc)
		{
			this.tc=tc;
			//new ConnDialog(this.tc).connect();
			//this.tc.remove(msp);
			
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			/*
			 * 指明子弹-爆炸-坦克的数量
			 * 以及坦克的生命值
			 */
			if(TankClient.flagconn!=true)
				g.drawImage(image0, 0,0,780,600,this.tc);
			else
			{
			g.drawImage(image1, 0, 0, null);
			//g.setFont(font);
			g.setColor(Color.YELLOW);
//			g.drawString("Tank                id:   " + ID, 5, 20);
			g.drawString("Game's Name:   " + name, 5, 40);
//			g.drawString("missiles  count:   " + missiles.size(), 5, 40);
//			g.drawString("explodes count:   " + explodes.size(), 5, 60);
			//g.drawString("tanks        count:   " + mytanks.size(), 5, 80);
			g.drawString("tanks             life:   " + tankhero.getLife(), 5, 60);
			//int reProduceTankCount = Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount"));//????????????????????????????????????????????????
//			if(challengers0.size() <= 0) {
//				//for(int i=0; i<reProduceTankCount; i++) {
//				for(int i=0; i<3; i++) {
////					tanks.add(new Tank(50 + 40*(i+1), 50, false, Direction.D, this));
//					challengers0.add(new Tank(0+10*i, 0, false,Direction.D,tc));
//				}
//			}
//			if(challengers1.size()<=0) {
//				for(int i=0;i<3;i++){
//					challengers1.add(new Tank(780-20*i, 590,false,Direction.U,tc));
//				}
//			}
			
//			if(P_B==0)
//			{
//				challengers0.add(tankhero);
//				mytanks=challengers0;
//				myenemies=challengers1;
//			}
//			else
//			{
//				challengers1.add(tankhero);
//				mytanks=challengers1;
//				myenemies=challengers0;
//			}
			
			for(int i=0; i<missiles.size(); i++) 
			{
				Missile m = missiles.get(i);
				m.hitMissiles(missiles);
//				if(flag==0)
//					m.hitTanks(challengers1);
//				else
//					m.hitTanks(challengers0);
				m.hit_web_tanks(mytanks);
				m.hit_web_tank(tankhero);
				//m.hit_web_tanks(myenemies);
				//m.hitTanks(myTanks);
//				m.hitWall(w1);
//				m.hitWall(w2);
				m.hitWall(wall);
				m.draw(g);
				//if(!m.isLive()) missiles.remove(m);
				//else m.draw(g);
			}
			
			for(int i=0; i<explodes.size(); i++) {
				Explode e = explodes.get(i);
				e.draw(g);
			}
			
//			for(int i=0; i<myenemies.size(); i++) {
//				Tank t = myenemies.get(i);
////				t.collidesWithWall(w1);
////				t.collidesWithWall(w2);
//				t.collidesWithWall(wall);
//				t.collidesWithTanks(myenemies);
//				t.collidesWithTanks(mytanks);
//				t.draw(g);
//				t.collidesWithTank(tankhero);
//				tankhero.collidesWithTank(t);
//			//	t.eat(b);
//			}
			
//			System.out.println("mytanks.size="+mytanks.size());
			
			for(int i=0; i<mytanks.size(); i++) {
				Tank t = mytanks.get(i);
//				System.out.println("mytanks.name="+t.getMyName());
//				t.collidesWithWall(w1);
//				t.collidesWithWall(w2);
				t.collidesWithWall(wall);
				//t.collidesWithTanks(myenemies);
				t.collidesWithTanks(mytanks);
				t.draw(g);
				t.collidesWithTank(tankhero);
				tankhero.collidesWithTank(t);
			//	t.eat(b);
			}
//			if(flag==0)
//			{
//				for(int i=0; i<challengers1.size(); i++) {
//					Tank t = challengers1.get(i);
////					t.collidesWithWall(w1);
////					t.collidesWithWall(w2);
//					t.collidesWithWall(wall);
//					t.collidesWithTanks(challengers1);
//				//	t.collidesWithTanks(myTanks);
//					t.draw(g);
//					t.collidesWithTank(tankhero);
//					tankhero.collidesWithTank(t);
//				//	t.eat(b);
//				}
//			}
//			else
//			{
//				for(int i=0; i<challengers0.size(); i++) {
//					Tank t = challengers0.get(i);
////					t.collidesWithWall(w1);
////					t.collidesWithWall(w2);
//					t.collidesWithWall(wall);
//					t.collidesWithTanks(challengers0);
//				//	t.collidesWithTanks(myTanks);
//					t.draw(g);
//					t.collidesWithTank(tankhero);
//					tankhero.collidesWithTank(t);
//				//	t.eat(b);
//				}
//			}
			
			tankhero.draw(g);
			//tankhero.eat(b);
			wall.draw(g);
//			if(counts0%500==0 && counts0>300)
//			{
//				//System.out.println("Blood Bar");
//				b.setReliveflag(true);
//				//b.draw(g);
//			}
//			wall.draw(g);
//			if(b.getReliveflag()==true)
//			{
//				b.draw(g);
//				flaginner1=1;
//			}
//			if(flaginner1==1)
//			{
//				counts1++;
//			}
//			if(counts1%200==0)
//			{
//				b.setLive(false);
//				counts1=0;
//				flaginner1=0;
//			}
		}
		}
		
		public void run()
		{
			while(true)
			{
				try{
//					counts0++;
//					if(counts0 == 1000000000)
//						counts0=0;
						this.repaint();
					Thread.sleep(37);
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	
	public void update(Graphics g) {
		if(offscreenimage == null) {
			offscreenimage = this.createImage(WIDTH, HEIGTH);
		}
		Graphics gOffScreen = offscreenimage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.CYAN);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGTH);
		//gOffScreen.drawImage(image, 0, 0, null);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offscreenimage, 0, 0, null);
	}
		
		
	}
	
	
	
	

	
	// 设置网络参数的对话框
	public class ConnDialog extends JDialog {
		JTextField IP = new JTextField("210.32.187.223", 10);
		JTextField Port = new JTextField("" + TankServer.TCP_PORT, 4);
		//JTextField Port = new JTextField("" + 9999, 4);
		JTextField MyUDPPort = new JTextField("2223", 4);
		JTextField PB = new JTextField("1",5); 
		JTextField Name = new JTextField("",7);
		JButton button = new JButton("确定");
		
		TankClient tcl0;

		public ConnDialog(TankClient tc0) {
			
			super(tc0);//Don't repair the super(Frame frame,boolean mode) arbitrarily,because the "boolean mode" will make us want to die.
			this.tcl0=tc0;
		}
		public void connect(){
			//super(this.tcl0,true);
			//this.tcl0=tc0;			
			this.setLayout(new FlowLayout());
			this.add(new JLabel("IP: "));
			this.add(IP);
			this.add(new JLabel("Port: "));
			this.add(Port);
			this.add(new JLabel("My UDP Port: "));
			this.add(MyUDPPort);
			this.add(new JLabel("P/B: "));
			this.add(PB);
			this.add(new JLabel("Name: "));
			this.add(Name);
			button.setEnabled(true);
			this.add(button);
			this.setLocation(350, 300);
			this.pack();
			this.setVisible(true);
			
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					setVisible(false);
				}
			}
			);
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{	
					//tc0.requestFocus();//点击按钮后获取焦点
//					System.out.println("Hello World");
					//setVisible(false);
					String ip = IP.getText().trim(); // 服务器IP
					int port = Integer.parseInt(Port.getText().trim()); // 服务器端口
					int myUdpPort = Integer.parseInt(MyUDPPort.getText().trim());
					P_B=Integer.parseInt(PB.getText().trim());
					
//					System.out.println("p_B="+P_B);
					
					name=Name.getText();
					
					tankhero.setMyName(name);
					
					nc.setServer_Ip(ip);
					nc.setUdp_port(myUdpPort);
					nc.connect(ip, port);
					isclick_enter = true;//This variable is also as the signature of the web_tank_war, and send the datagrampacket to the server .*/
					
					setVisible(false);
					TankClient.flagconn=true;
					tcl0.remove(msp);
					
				}
			});
			
			
		}

	}
	
	
}