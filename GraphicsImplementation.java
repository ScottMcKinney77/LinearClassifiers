import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphicsImplementation extends JPanel{
	
	protected ArrayList<Double> trainingReport;
	
	public GraphicsImplementation (ArrayList<Double> trainingReport) {
		this.trainingReport = trainingReport;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Rectangle r = this.getBounds();
		int vh = r.height;
		int vw = r.width;
		int wallBuffer = 80;
		
		
		int oldX = 0 + wallBuffer/2;
		double oldY = trainingReport.get(0)*(vh-wallBuffer);
		int x = 0;
		double y = 0;
		
		double lateralSpacing = ((double)vw-wallBuffer)/trainingReport.size();
		//System.out.println(lateralSpacing);
		
		g.setColor(Color.BLACK);
		for (int i = 0; i < trainingReport.size(); i++) {
			x = wallBuffer/2 + (int) (i*lateralSpacing);
			y = trainingReport.get(i)*(vh-wallBuffer);
			g.drawLine(oldX, (vh-wallBuffer)-(int)oldY, x, (vh-wallBuffer)-(int)y);
			oldX = x;
			oldY = y;
		}
		
		
		
	}
}
