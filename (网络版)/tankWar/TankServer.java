package tankWar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class TankServer extends Frame{
	private static int ID = 1;
	int count =0;
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	List<Client> clients = new ArrayList<Client>();
	List<DatagramPacket> tanks_all = new ArrayList<DatagramPacket>();
	public void start() {
		new Thread(new UdpThread()).start();
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			Socket s = null;
			try {
				s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String IP = s.getInetAddress().getHostAddress();
				System.out.println("Server IP="+IP);
				int udpPort = dis.readInt();
				if(udpPort >UDP_PORT+10000)continue;
				Client c = new Client(IP, udpPort);
				clients.add(c);
				count++;
				validate();
				repaint();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(ID++);
System.out.println("A Client Connect! Addr- " + s.getInetAddress() + ":" + s.getPort() + "----UDP Port:" + udpPort);
			}
			catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(s != null) {
					try {
						s.close();
						s = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TankServer().launchFrame();
	}
	
	private class Client {
		String IP;
		int udpPort;
		
		public Client(String IP, int udpPort) {
			this.IP = IP;
			this.udpPort = udpPort;
		}
	}
	
	private class UdpThread implements Runnable{
		
		byte[] buf = new byte[1024];
		@Override
		public void run() {
			DatagramSocket  ds = null;
			try {
				ds = new DatagramSocket(UDP_PORT);
				
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("A UDP thread start !");
			while(ds!=null){
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				try {
					ds.receive(dp);
					for(int i=0;i<clients.size();i++)
					{
						dp.setSocketAddress(new InetSocketAddress(clients.get(i).IP,clients.get(i).udpPort));
						ds.send(dp);
//						System.out.println("A package send !");
					}
					//System.out.println("A package receive !");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void launchFrame(){
		this.setLocation(50, 50);
		this.setSize(800, 600);
		this.setBackground(Color.CYAN);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setTitle("Server");
		setVisible(true);
		start();
	}
	
	public void paint(Graphics g) {
	
		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,24));
		g.drawString("Client count:" + count, 10, 50);
		int style = Font.BOLD;

		Font font = new Font ("Garamond", style , 30);
		g.setFont(font);
		g.drawString("Server Start !!!", 350, 300);
		}
}
