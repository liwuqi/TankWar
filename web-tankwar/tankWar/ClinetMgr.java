package tankWar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;

public class ClinetMgr {
	private static int UDP_PORT = 2223;
	public static final String SERVER_IP = "127.0.0.1";
	private  String Server_Ip = "10.214.12.34";
	private int udp_port;
	TankClient tc;
	DatagramSocket  ds = null;
	
	public ClinetMgr(TankClient tc){
		udp_port = UDP_PORT++;
		this.tc = tc;
	}
	
	public String getServer_Ip() {
		return Server_Ip;
	}

	public void setServer_Ip(String server_Ip) {
		Server_Ip = server_Ip;
	}

	public int getUdp_port() {
		return udp_port;
	}

	public void setUdp_port(int udp_port) {
		this.udp_port = udp_port;
	}
	
	public void connect(String Ip,int port){
		try {
			ds = new DatagramSocket(udp_port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket s = null;
		try {
			s = new Socket(Ip,port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udp_port);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int ID = dis.readInt();
			tc.tankhero.ID = ID;
			System.out.println("the ID is from Server :"+ID);
			System.out.println("connect!");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(s!=null)s.close();
				s = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		TankNewMsg tm = new TankNewMsg(tc.tankhero);
		send(tm);
		new Thread(new UdpThread()).start();
		Timer timer = new Timer();
		timer.schedule(new MyTask(tc,this), 1000, 1000);
	}
	
	

	public void send(Message tm) {
		tm.send(ds,Server_Ip,TankServer.UDP_PORT);
	}
	
	
	private class UdpThread implements Runnable{
		byte[] buf = new byte[1024];
		@Override
		public void run() {
			while(ds!=null){
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				try {
					ds.receive(dp);
					parse(dp);
//					System.out.println("A package receive !");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		private void parse(DatagramPacket dp) {
			ByteArrayInputStream bais = new ByteArrayInputStream(buf,0,dp.getLength());
			DataInputStream dis = new DataInputStream(bais);
			int msg_type = 0;
			try {
				msg_type = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Message msg = null;
			switch(msg_type){
			case Message.TANK_NEW_MSG:
				msg = new TankNewMsg(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
			case Message.TANK_MOV_MSG:
				msg = new TankMovMsg(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
			case Message.MISSILE_MSG:
				msg = new MissileMsg(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
			case Message.TANK_REVIVAL_MSG:
				msg = new TankRevivalMsg(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
			case Message.TANKPOSITION:
				msg = new TankPosition(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
//			case Message.SUPERFIREMSG:
//				msg = new SuperFireMsg(ClinetMgr.this.tc);
//				msg.parse(dis);
//				break;
			case Message.TANK_DIE_MSG:
				msg = new TankPosition(ClinetMgr.this.tc);
				msg.parse(dis);
				break;
			}
		}
	}
}
