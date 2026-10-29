package gui.elements;

import config.EngineConfig;
import config.SimulationConfig;
import engine.mobile.Probe;
import engine.mobile.Rocket;
import engine.mobile.Satellite;
import engine.mobile.Stage;
import engine.process.managers.SimulationManager;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * A panel that renders the current mission phase as a horizontal progress timeline.
 **/
public class MissionPhasePanel extends JPanel {

	private static final long serialVersionUID=1L;
   
    private static final int STEP=80;
    private static final int RADIUS=10;

    private SimulationManager simulationManager;
   

    public MissionPhasePanel(SimulationManager simulationManager) {
        this.simulationManager=simulationManager;
        setBackground(SimulationConfig.PANEL_COLOR);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;

        Rocket rocket=simulationManager.getMobileManager().getRocket();
        String state=rocket.getRocketState();
        if (state==null)state="";

        String[] phases=getPhases();
        int currentPhase=getCurrentPhase(state);

        printLines(g2,phases,currentPhase);
        printCircles(g2,phases,currentPhase);
        printLabels(g2,phases,currentPhase);
    }

    private void printLines(Graphics2D g2,String[] phases,int currentPhase) {
        g2.setStroke(new BasicStroke(2));
        int startY =getHeight()/2;
    	int startX=(getWidth()-(phases.length-1)*STEP)/2;
        
        for (int i=0;i<phases.length-1;i++) {
        	
        	
            if (i<currentPhase) {
                g2.setColor(new Color(29,158,117));
            } else {
                g2.setColor(Color.GRAY);
            }
            int startPoint=startX+i*STEP;
            int endPoint=startX+(i+1)*STEP;
            g2.drawLine(startPoint+RADIUS,startY,endPoint-RADIUS,startY);
        
        }
    }

    private void printCircles(Graphics2D g2,String[]phases,int currentPhase) {
    	int startY =getHeight()/2;
    	int startX=(getWidth()-(phases.length-1)*STEP)/2;
        
    	for (int i=0; i<phases.length;i++) {
    		int startPoint=startX+i*STEP;            
            if (i<currentPhase) {
                g2.setColor(new Color(29,158,117));
                g2.fillOval(startPoint-RADIUS,startY-RADIUS,RADIUS*2,RADIUS*2);
            } else if (i==currentPhase) {
            	
            	    g2.setColor(new Color(239,159,39));
            	
                g2.fillOval(startPoint-RADIUS,startY-RADIUS,RADIUS*2,RADIUS*2);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawOval(startPoint-RADIUS,startY-RADIUS,RADIUS*2,RADIUS*2);
            }
        }
    }

    private void printLabels(Graphics2D g2,String[] phases,int currentPhase) {
        
    	g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
    	int startY =getHeight()/2;
    	int startX=(getWidth()-(phases.length-1)*STEP)/2;
        
    	for (int i=0;i<phases.length;i++) {
            int startPoint=startX+i*STEP;
            FontMetrics fontMetrics=g2.getFontMetrics();
            if (i<=currentPhase) {
                g2.setColor(Color.DARK_GRAY);
            } else {
                g2.setColor(Color.GRAY);
            }
            g2.drawString(phases[i],startPoint-fontMetrics.stringWidth(phases[i])/2,startY+RADIUS+16);
        }
    }

    private String[] getPhases() {
        if (EngineConfig.isInterplanetary) {
            return new String[]{"Launch","Sep.1","Sep.2","Sep.3","LEO","TLI","Orbit","Landed"};
        } else if (EngineConfig.isGEO) {
            return new String[]{"Launch","Sep.1","Sep.2","Transfer","GEO"};
        } else {
            return new String[]{"Launch","Sep.1","Sep.2","LEO"};
        }
    }

    private int getCurrentPhase(String state) {
    	if (state.contains("FAILURE"))return -1;
    	if (state.contains("Landed"))return 7;
    	if (state.contains("In orbit"))return 6;
    	if (state.contains("orbit around"))return 6;
    	if (state.contains("lunar"))return 6;
        if (state.contains("Trans"))return 5;
        if (state.contains("GEO"))return 4; 
        if (state.contains("transfer"))return 3;
        if (state.contains("LEO")) return 4;
        if (state.contains("Payload"))return 3;
        if (state.contains("two")) return 2;
        if (state.contains("one"))  return 1;
        return 0;
    }

    

    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager=simulationManager;
    }
}
