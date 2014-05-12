package tankWar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TankDieMsg implements Message{
	int MSG_TYPE =Message.TANK_DIE_MSG;
	int ID;
	boolean islive;
	TankClient tc;
	Tank t ;
	
	public TankDieMsg(int iD, boolean islive) {
		ID = iD;
		this.islive = islive;
	}
	
	public TankDieMsg(Tank t) {
		this.t = t;
	}
	
	public TankDieMsg(TankClient tc)
	{
		this.tc = tc;
	}
	
	public void send(DatagramSocket ds, String IP, int port) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(t.ID);
			dos.writeBoolean(false);
			dos.writeInt(t.getX());
			dos.writeInt(t.getY());
			dos.writeInt(t.dir.ordinal());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf,buf.length,new  InetSocketAddress(IP,port));
			try {
				ds.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void parse(DataInputStream dis) {
		try {
			//int msg_type = dis.readInt();
			int id = dis.readInt();
			if(tc.tankhero.ID == id)return;
			int ilive = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			int dir = dis.readInt();
			Direction dirr = Direction.values()[dir];
			boolean islive = dis.readBoolean();
			//System.out.println(dir);
			for(int i=0;i<tc.mytanks.size();i++)
			{
				Tank tn = tc.mytanks.get(i);
				if(tn.ID==id)
				{
					tn.setLive(false);
					//tn.setLife(100);
					tn.setX(x);
					tn.setY(y);
					tn.dir = dirr;
					break;
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
