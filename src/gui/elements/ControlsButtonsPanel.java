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
 * A control panel providing simulation action buttons arranged in a 2×3 grid.
 **/
public class ControlsButtonsPanel extends JPanel {
	
private static final long serialVersionUID = 1L;
	//Button
	private JButton  pauseButton=new JButton("Pause");
	private JButton  speedUpButton=new JButton("Speed Up");
	private JButton  speedDownButton=new JButton("Speed Down");
	private JButton  resetButton=new JButton("Reset");
	private JButton launchButton=new JButton("Launch");
	private JButton followRocket=new JButton("Follow");
    private final static Dimension SIMULATION_BUTTON_DIMENSION=new Dimension(160,50);

	public ControlsButtonsPanel(SimulationManager simulationManager) {
	
		
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
		add(launchButton,gridBagConstraints);
		
		gridBagConstraints.gridx=1;
		gridBagConstraints.gridy=0;
		add(followRocket,gridBagConstraints);
		
		
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=1;
		add(pauseButton,gridBagConstraints);
		
		gridBagConstraints.gridx=1;
		gridBagConstraints.gridy=1;
		add(resetButton,gridBagConstraints);
		
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=2;
		add(speedUpButton,gridBagConstraints);
		
		gridBagConstraints.gridx=1;
		gridBagConstraints.gridy=2;
		add(speedDownButton,gridBagConstraints);
		
	}
	



	public void initStyle() {

    	//STYLE
    	pauseButton.setFont(SimulationConfig.BUTTON_FONT);
        pauseButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        pauseButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        resetButton.setFont(SimulationConfig.BUTTON_FONT);
        resetButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        resetButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
 
        speedDownButton.setFont(SimulationConfig.BUTTON_FONT);
        speedDownButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        speedDownButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        speedUpButton.setFont(SimulationConfig.BUTTON_FONT);
        speedUpButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        speedUpButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        
        launchButton.setFont(SimulationConfig.BUTTON_FONT);
        launchButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        launchButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        followRocket.setFont(SimulationConfig.BUTTON_FONT);
        followRocket.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        followRocket.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        
        //DIMENSION
       
        pauseButton.setPreferredSize(SIMULATION_BUTTON_DIMENSION);
        resetButton.setPreferredSize(SIMULATION_BUTTON_DIMENSION);
        speedDownButton.setPreferredSize(SIMULATION_BUTTON_DIMENSION);
        speedUpButton.setPreferredSize(SIMULATION_BUTTON_DIMENSION);
        
        launchButton.setPreferredSize(SIMULATION_BUTTON_DIMENSION);
        followRocket.setPreferredSize(SIMULATION_BUTTON_DIMENSION);

       
	}
	
	
	public JButton getPauseButton() {
		return pauseButton;
	}

	public JButton getSpeedUpButton() {
		return speedUpButton;
	}

	public JButton getSpeedDownButton() {
		return speedDownButton;
	}



	public JButton getResetButton() {
		return resetButton;
	}

	

	public JButton getLaunchButton() {
		return launchButton;
	}

	public JButton getFollowRocket() {
		return followRocket;
	}

	



	

}
