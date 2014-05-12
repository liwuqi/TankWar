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

public class TankNewMsg implements Message{
	Tank t;
	TankClient tc;
	int MSG_TYPE =Message.TANK_NEW_MSG;
	public TankNewMsg(Tank t) {
		this.t = t;
	}
	
	public TankNewMsg(TankClient tc) {
		this.tc = tc;
	}

	public void send(DatagramSocket ds,String IP,int port){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(t.ID);
//			byte[] str=t.getMyName().getBytes();
//			dos.write(str);
//			System.out.println("t.getMyName="+t.getMyName());
			dos.writeUTF(t.getMyName());
//			dos.writeBytes(t.getMyName());
			dos.writeInt(t.getX());
			dos.writeInt(t.getY());
			dos.writeInt(t.dir.ordinal());
			dos.writeBoolean(t.isGood());
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
//			System.out.println("new tank's id="+id);
			
			if(tc.tankhero.ID == id)return;
			
			String name00=dis.readUTF();
//			System.out.println("name00="+name00);
			
			int x = dis.readInt();
			int y = dis.readInt();
			int dir = dis.readInt();
			System.out.println("dir="+dir);
			
			boolean good = dis.readBoolean();
			Direction dirr = Direction.values()[dir];
			boolean exist = false;
			for(int i=0;i<tc.mytanks.size();i++)
			{
				if(tc.mytanks.get(i).ID == id)
				{
					exist = true;
					break;
				}
			}
			if(!exist){
				TankNewMsg tnm = new TankNewMsg(tc.tankhero);
				tc.nc.send(tnm);
				Tank tna = new Tank(x,y,false,dirr,tc);
				tna.ID = id;
				tna.setMyName(name00);
				tc.mytanks.add(tna);
			}
			System.out.println("id: "+id+"--x:"+x+"--y:"+y+"--dir:"+dir+"--good:"+good);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
