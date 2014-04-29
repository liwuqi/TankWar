package tankWar;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;



public class TankClient extends JFrame
{
	Container container=this.getContentPane();
	JPanel pane0=new JPanel();
	JPanel pane1;
	JLabel jlabel0=new JLabel(" 游戏   ");
	JLabel jlabel1=new JLabel("帮助");
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image image0 = tk.getImage("H:\\eclipse-standard-kepler-SR2-win32\\MyTank\\image\\tankview0.jpg");
	private int WIDTH=786;
	private int HEIGTH=655;
	AudioClip audio;
	Graphics g;
	
//	JComboBox<String>jc=new JComboBox<>(new Mycombobox());
//	JList<String>jlist=new JList<>(new Mylistmodel());
	
	public TankClient(){
		pane0.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane0.add(jlabel0);
		pane0.add(jlabel1);

		pane1=new MyPane(image0);
		
		
		try{
		audio=Applet.newAudioClip(new URL("file:\\C:\\Users\\wenbin\\Desktop\\TankWarNet1.4\\src\\video\\crime_punishment.wav"));
		}catch(Exception e){
			e.printStackTrace();
		}
		audio.loop();
		
		container.setLayout(new BorderLayout());
		container.add(pane0,BorderLayout.NORTH);
		container.add(pane1,BorderLayout.CENTER);
		
		this.setSize(WIDTH,HEIGTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("坦克大战");
		//pack();
		this.setVisible(true);
		this.setResizable(false);
		
//		new Thread(new PaintThread()).start();
	}
	
//	public void paint(Graphics g)
//	{
//		setContentPane(pane1);
////		g.drawImage(image0, 0, 0,null);
////		
//	}
	
//	class Mycombobox extends AbstractListModel<String>implements ComboBoxModel<String>{
//		String selecteditem="游戏";
//		String str[]={"单机模式","网络对战模式"};
//		public String getElementAt(int index){
//			return str[index];
//		}
//		public int getSize(){
//			return str.length;
//		}
//		public void setSelectedItem(Object item){
//			selecteditem=(String)item;
//		}
//		public Object getSelectedItem(){
//			return selecteditem;
//		}
//	}
//	class Mylistmodel extends AbstractListModel<String>{
//		private String[] contents={"方向：WASD","发射子弹：空格"};
//		public String getElementAt(int x){
//			if(x<contents.length)
//			{
//				return contents[x++];
//			}
//			else
//				return null;
//		}
//		public int getSize()
//		{
//			return contents.length;
//		}
//	}
	
	
	
	
	public static void main(String args[])
	{
		TankClient tank=new TankClient();

	}
	
	private class MyPane extends JPanel
	{
		Image img;
		public MyPane(Image image)
		{
			this.img=image;
		}
		public void paint(Graphics g)
		{
			super.paint(g);
			g.drawImage(img,0,0,780,600,this);
		}
	
	}
//	
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