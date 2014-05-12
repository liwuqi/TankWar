package tankWar;

import java.util.TimerTask;

public class MyTask extends TimerTask{
	TankClient tc;
	ClinetMgr cline;
	public MyTask(TankClient tc,ClinetMgr cline){
		this.cline = cline;
		this.tc = tc;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TankPosition pmsg = new TankPosition(tc.tankhero);
		cline.send(pmsg);
	}
}
