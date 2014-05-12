package tankWar;

import java.io.DataInputStream;
import java.net.DatagramSocket;

public interface Message {
	public static final int TANK_NEW_MSG = 1;
	public static final int TANK_MOV_MSG = 2;
	public static final int MISSILE_MSG = 3;
	public static final int TANK_REVIVAL_MSG = 4;
	public static final int TANKPOSITION = 5;
	public static final int SUPERFIREMSG = 6;
	public static final int TANK_DIE_MSG = 7;
	public void send(DatagramSocket ds,String IP,int port);
	public void parse(DataInputStream dis); 
}
