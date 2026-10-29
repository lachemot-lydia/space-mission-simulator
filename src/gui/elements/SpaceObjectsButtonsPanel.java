package gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.EngineConfig;
import config.SimulationConfig;
import engine.process.managers.SimulationManager;

/**
 * A panel providing space view control buttons arranged in a 2×3 grid.
 */
public class SpaceObjectsButtonsPanel extends JPanel {

	private static final long serialVersionUID = 1L;


	private JButton zoomInButton=new JButton("Zoom in");
	private JButton zoomOutButton=new JButton("Zoom out");
	private JButton zoomInRocket=new JButton("In Rocket");
	private JButton zoomOutRocket=new JButton("Out Rocket");
	private JButton targetPlanetButton=new JButton("Target Planet");
	private JButton departurePlanetButton=new JButton("Departure Planet");

	 public SpaceObjectsButtonsPanel(SimulationManager simulationManager) {
		initLayout(simulationManager);
		initStyle();
	}
	
	public void initLayout(SimulationManager simulationManager) {

		//LAYOUT
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints =new GridBagConstraints();
		gridBagConstraints.insets=new Insets(5, 5, 1, 5);
		gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
		
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=0;
		add(zoomInButton,gridBagConstraints);
		
		gridBagConstraints.gridx=1;
		gridBagConstraints.gridy=0;
		add(zoomOutButton,gridBagConstraints);
		
	
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=1;
		add(zoomInRocket,gridBagConstraints);
		
		gridBagConstraints.gridx=1;
		gridBagConstraints.gridy=1;
		add(zoomOutRocket,gridBagConstraints);
		
	
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=2;
		add(departurePlanetButton,gridBagConstraints);
			
		if(EngineConfig.isInterplanetary) {	
			gridBagConstraints.gridx=1;
			gridBagConstraints.gridy=2;
			add(targetPlanetButton,gridBagConstraints);
		}
			
		
		
		
	}
	



	public void initStyle() {

    	//STYLE
    	
        zoomInButton.setFont(SimulationConfig.BUTTON_FONT);
        zoomInButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        zoomInButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        zoomOutButton.setFont(SimulationConfig.BUTTON_FONT);
        zoomOutButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        zoomOutButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
       
        zoomInRocket.setFont(SimulationConfig.BUTTON_FONT);
        zoomInRocket.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        zoomInRocket.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        zoomOutRocket.setFont(SimulationConfig.BUTTON_FONT);
        zoomOutRocket.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        zoomOutRocket.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        targetPlanetButton.setFont(SimulationConfig.BUTTON_FONT);
        targetPlanetButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        targetPlanetButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        departurePlanetButton.setFont(SimulationConfig.BUTTON_FONT);
        departurePlanetButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        departurePlanetButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
     
	}
	
	
	public JButton getZoomInButton() {
		return zoomInButton;
	}

	public JButton getZoomOutButton() {
		return zoomOutButton;
	}

	
	public JButton getZoomInRocket() {
		return zoomInRocket;
	}

	public JButton getZoomOutRocket() {
		return zoomOutRocket;
	}

	
	public JButton getTargetPlanet() {
		return targetPlanetButton;
	}

	public JButton getDeparturePlanetButton() {
		return departurePlanetButton;
	}



	

}

		

	
	

