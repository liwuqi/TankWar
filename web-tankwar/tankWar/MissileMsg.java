package tankWar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class MissileMsg implements Message{
	int MSG_TYPE =Message.MISSILE_MSG;
	int ID;
	boolean isfire;
	TankClient tc;
	
	public MissileMsg(int iD, boolean isfire) {
		super();
		ID = iD;
		this.isfire = isfire;
	}
	
	public MissileMsg(TankClient tc)
	{
		this.tc = tc;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int port) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(ID);
			dos.writeBoolean(isfire);
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
			boolean isfire = dis.readBoolean();
			//System.out.println(dir);
			for(int i=0;i<tc.mytanks.size();i++)
			{
				Tank tn = tc.mytanks.get(i);
				if(tn.ID==id)
				{
					tn.isfire = true;
					break;
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
