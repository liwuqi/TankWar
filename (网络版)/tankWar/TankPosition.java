
package tankWar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TankPosition implements Message{
	Tank t;
	TankClient tc;
	int MSG_TYPE =Message.TANKPOSITION;
	public TankPosition(Tank t) {
		this.t = t;
	}
	
	public TankPosition(TankClient tc) {
		this.tc = tc;
	}

	public void send(DatagramSocket ds,String IP,int port){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(t.ID);
			dos.writeInt(t.getX());
			dos.writeInt(t.getY());
			dos.writeInt(t.getLife());
			dos.writeInt(t.dir.ordinal());
			dos.writeBoolean(t.isLive());
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

	public void parse(DataInputStream dis) {
		
		try {
			//int msg_type = dis.readInt();
			int id = dis.readInt();
			if(tc.tankhero.ID == id)return;
			int x = dis.readInt();
			int y = dis.readInt();
			int life = dis.readInt();
			int dir = dis.readInt();    
			boolean live = dis.readBoolean();
			Direction dirr = Direction.values()[dir];
			for(int i=0;i<tc.mytanks.size();i++)
			{
				Tank tn = tc.mytanks.get(i);
				if(tn.ID==id)
				{
					tn.setLife(life);
					tn.setLive(live);
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

