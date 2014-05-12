package tankWar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TankMovMsg implements Message{
	int ID;
	Direction dir;
	int MSG_TYPE =Message.TANK_MOV_MSG;
	Tank t;
	TankClient tc;
	public TankMovMsg(int iD, Direction dir) {
		this.ID = iD;
		this.dir = dir;
	}
	
	public TankMovMsg(Tank t) {
		this.t = t;
	}
	
	public TankMovMsg(TankClient tc) {
		this.tc = tc;
	}
	
	public TankMovMsg() {
	}
	
	public void send(DatagramSocket ds,String IP,int port){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(ID);
			dos.writeInt(dir.ordinal());
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
			int dir = dis.readInt();
			//System.out.println(dir);
			Direction dirr = Direction.values()[dir];
			for(int i=0;i<tc.mytanks.size();i++)
			{
				Tank tn = tc.mytanks.get(i);
				if(tn.ID==id)
				{
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
