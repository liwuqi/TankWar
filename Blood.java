package tankWar;
import java.awt.*;
import java.util.*;

public class Blood {
	int x, y, w, h;
	TankClient tc; 
	
	Random rand=new Random();
	
	//int step = 0;
	private boolean live = true;
	private boolean reliveflag=false;
	
	
	boolean mark[]=new boolean[800];
	
	/* the Blood class's Construction function*/
	public Blood() {
		
		for(int i=0;i<800;i++)
			mark[i]=false;
		x = ranXY(739);
		y = ranXY(618);
		h = 15;
		w = 47;

	}
	
	/* showing Blood function*/
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		//move();
	}
	
	/* Blood move function*/
//	private void move() {
//		if(live==true)
//			return ;
//		if(reliveflag==true)
//		{
//			x = ranXY(786);
//			y = ranXY(665);
//			live=true;
//		}
//	}
//	
	/*Blood's rectangle function in order to judge whether collide*/
	public Rectangle getRect() {
		return new Rectangle(x, y, w , h);
	}

	public boolean isLive() {
		return live;
	}

	private int ranXY(int n)
	{
		int i;
		do{
			i=rand.nextInt(n);
			
		}while(mark[i]);
		mark[i]=true;
		return i;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean getReliveflag() 
	{
		return this.reliveflag;
	}
	public void setReliveflag(boolean live) {
			this.reliveflag = live;
			if(this.reliveflag==true)
			{
				x = ranXY(739);
				y = ranXY(618);
				this.live=true;
			}
	} 
	
}
