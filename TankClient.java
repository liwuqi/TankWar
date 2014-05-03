package tankWar;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;


public class TankClient extends JFrame implements ActionListener
{
//	Container container=this.getContentPane();/*produce the container*/
//	JPanel pane0=new JPanel();/*panel is used for the selection*/
//	JPanel pane1;
//	final JLabel tablabel0=new JLabel("����");
	JMenuBar menubar=new JMenuBar();  //build the menu class
	JMenu menu0=new JMenu(" ��Ϸ ");
	JMenu menu1=new JMenu("���� ");

	JMenuItem menuitem0=new JMenuItem("����ģʽ");
	JMenuItem menuitem1=new JMenuItem("�����սģʽ");
	JMenuItem menuitem2=new JMenuItem("����WASD");
	JMenuItem menuitem3=new JMenuItem("�����ӵ���SPACE");
	
	Wall wall=new Wall(100,200,this);
	
	private static String str0=null;
	//private static String str1=null;
	
	Blood b = new Blood();
	private Random ran = new Random();
	Tank tankhero=new Tank(500,500,true,Direction.STOP,this);
	List<Tank>tankenemies=new ArrayList<Tank>();
	List<Explode>explodes=new ArrayList<Explode>();
	List<Missile>missiles=new ArrayList<Missile>();
	
	Image offscreenimage=null;
	
//	String str=new String("�û���½��Ϸ�ͻ���ѡ����Ϸģʽ���ɷ�Ϊ����ģʽ�������սģʽ��ѡ�񵥻�ģʽ����ֱ�ӽ�����Ϸ״̬��ѡ�������սģʽ��"
//			+ "�������������������Ҫѡ���Լ��Ľ�ɫ���û�����ID�����������ϣ�"
//			+ "�ɹ���ʹ�����˺Ž��е�¼��Ϸ��ֻ�гɹ���¼����Ҳ��ܲ��������ս��Ϸ���ܣ���"
//			+ "��½��ҾͿ���ͨ�����Ƽ��̲����������ս��Ϸ��"+"\nWASD---���Ʒ���"+"\nSPACE---�����ӵ�");

	private static Toolkit tk = Toolkit.getDefaultToolkit();/*in order to get images or get images' measure.*/
	private static Image image0 = tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankview0.jpg");
	private static Image image1 = tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\background1.png");
		
	/*the screen's window size*/
	private static final int WIDTH=786;
	private static final int HEIGTH=665;
	AudioClip audio;  /*in order to play music*/
	
	MyStartPanel msp=null;
	
	
	
	MySecondPanel mp=null;
	
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

				menuitem0.addActionListener(this);
				menuitem1.addActionListener(this);
				
				menu0.add(menuitem0);
				menu0.add(menuitem1);
				
				menu1.add(menuitem2);
				menu1.add(menuitem3);
				
				menubar.add(menu0);
				menubar.add(menu1);
				this.setJMenuBar(menubar);
//				setContentPane(pane1);
				
				try{
				audio=Applet.newAudioClip(new URL("file:\\C:\\Users\\wenbin\\Desktop\\TankWarNet1.4\\src\\video\\crime_punishment.wav"));
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
				this.setTitle("̹�˴�ս");
				//pack();
				this.setVisible(true);
				this.setResizable(false);	
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem meitem=(JMenuItem)e.getSource();
		str0=meitem.getText();
		//System.out.println(str0);
		if(str0.equals(new String("����ģʽ")))
		{
			this.remove(msp);
			mp=new MySecondPanel(this);
			Thread mt =new Thread(mp);
			mt.start();
			this.add(mp);
			this.addKeyListener(new KeyMonitor());
			this.setVisible(true);
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
	 * ��������ʾ̹��������
	 *
	 */
	public static void main(String args[])
	{
		TankClient tankwar=new TankClient();
	
	}
	
	
	
	
	
	/*all kinds of inner classes*/
	
	
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
		private int flag=0;
		
		public MySecondPanel(TankClient tc)
		{
			this.tc=tc;
		}
		public void paint(Graphics g)
		{
			super.paint(g);
			/*
			 * ָ���ӵ�-��ը-̹�˵�����
			 * �Լ�̹�˵�����ֵ
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
				flag=1;
			}
			if(flag==1)
			{
				counts1++;
			}
			if(counts1%200==0)
			{
				b.setLive(false);
				counts1=0;
				flag=0;
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
	
//	private class PaintThread implements Runnable {
//
//		public void run() {
//			while(true) {
//				repaint();
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}	



}