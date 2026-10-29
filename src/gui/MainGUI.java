package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import javax.xml.crypto.Data;

import config.EngineConfig;
import config.SimulationConfig;
import engine.data.Mission;
import engine.data.Propulsion;
import engine.data.RocketEngine;
import engine.data.SolarSystem;
import engine.data.TankPropellant;
import engine.mobile.Orbit;
import engine.mobile.Planet;
import engine.mobile.Rocket;
import engine.mobile.Satellite;
import engine.process.Music;
import engine.process.builders.MissionBuilder;
import engine.process.builders.SimulationBuilder;
import engine.process.managers.MobileElementManager;
import engine.process.managers.SimulationManager;
import gui.elements.ChartsPanel;
import gui.elements.ControlsButtonsPanel;
import gui.elements.HelpGUI;
import gui.elements.MissionPhasePanel;
import gui.elements.SpaceObjectsButtonsPanel;
import gui.management.ChartManager;
import gui.management.ChartsData;
import gui.management.FocusManager;
import gui.management.SimulationDisplay;
import log.LoggerUtility;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;


/**
 * <p>The main graphical class of the simulation</p>
 * <p>It is used to show the page of the simulation launching of the rocket </p>
 * <p>And to interact with it using buttons to change the simulation's settings </p>
 *@version 2.0
 *@since 2026-03-15
 */

public class MainGUI extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerUtility.getLogger(MainGUI.class, "html");

    private final static Dimension IDEAL_MAIN_DIMENSION = new Dimension(SimulationConfig.WINDOW_WIDTH, SimulationConfig.WINDOW_HEIGHT);
    private final static Dimension IDEAL_PANEL_DIMENSION=new Dimension(620,SimulationConfig.WINDOW_HEIGHT-150);
    private final static Dimension IDEAL_TEXTFIELD_DIMENSION=new Dimension(280,55);

    

    
  //Button
  	private JButton  pauseButton;
  	private JButton  speedUpButton;
  	private JButton  speedDownButton;
  	private JButton zoomInButton;
  	private JButton zoomOutButton;
  	private JButton  resetButton;
  	private JButton zoomInRocket;
  	private JButton zoomOutRocket;
  	private JButton launchButton;
  	private JButton followRocket;
  	private JButton targetPlanetButton;
  	private JButton departurePlanetButton;
  	
  	private JButton missionSittingButton=new JButton("Mission Settings");
  	private JButton soundButton=new JButton("Sound Off");

    //LABEL 
    private JLabel altitudeLabel=new JLabel("Altitude:");
    private JLabel stagesLeftLabel= new JLabel("Stages left:");
    private JLabel rocketStateLabel=new JLabel("Rocket State: ");
    private JLabel missionDurationLabel=new JLabel("Mission duration:");
    private JLabel velocityLabel=new JLabel("Velocity: ");
    
    //TEXTFIELD
    private JTextField altitudeField=new JTextField();
    private JTextField stagesLeftField=new JTextField();
    private JTextField rocketStateField=new JTextField();
    private JTextField missionDurationField=new JTextField();
    private JTextField velocityField=new JTextField();
    
    
    //PANELS
    private JPanel buttonsPanel=new JPanel();
    private JPanel eastPanel=new JPanel();
    private ControlsButtonsPanel simulationButtonsPanel;
    private SpaceObjectsButtonsPanel spaceObjectsPanel;
    private JPanel stateRocketPanel=new JPanel();
    private MissionPhasePanel missionPhasePanel;
    private JPanel centerPanel=new JPanel();
    private ChartsPanel chartsPanel= new ChartsPanel();

    //MENU
    private JMenuBar menuBar=new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem helpSimulation = new JMenuItem("simulation");
    
    private SimulationDisplay dashboard;
    private SimulationManager simulationManager;
    
    private boolean stop=false;
    private MainGUI instance=this;
    private boolean launchDone;
    private boolean soundOn=true;
    private boolean missionSaved=false;
    private Mission mission;
    private ArrayList<Propulsion> propulsions;
    private ArrayList<Propulsion> initialPropulsions;
    private ChartsData chartsData;
    private FocusManager focusManager;
    
    private Music music;

  //help
    HelpGUI helpGUI = new HelpGUI();
	
    public MainGUI(String title,SimulationManager simulationManager,Mission mission,ArrayList<Propulsion> propulsions) {
    	super(title);
    	this.mission=mission;
    	this.simulationManager=simulationManager;
    	this.initialPropulsions=initialPropulsion(propulsions);
    	this.propulsions=initialPropulsion(this.initialPropulsions);
    	initMenuBar();
    	initLayout();
    	initStyle();
    	initActions();
    	music=new Music();
        music.play();
    	
    }
    
    public void initLayout() {
    	
    	Container contentPane =getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        buttonsPanel.add(missionSittingButton);
        buttonsPanel.add(soundButton);
    	contentPane.add(buttonsPanel,BorderLayout.NORTH);
    	
    	simulationButtonsPanel=new ControlsButtonsPanel(simulationManager);
    	spaceObjectsPanel=new SpaceObjectsButtonsPanel(simulationManager);
    	
    	launchButton=simulationButtonsPanel.getLaunchButton();
    	pauseButton=simulationButtonsPanel.getPauseButton();
    	resetButton=simulationButtonsPanel.getResetButton();
    	zoomInButton=spaceObjectsPanel.getZoomInButton();
    	zoomOutButton=spaceObjectsPanel.getZoomOutButton();
    	zoomInRocket=spaceObjectsPanel.getZoomInRocket();
    	zoomOutRocket=spaceObjectsPanel.getZoomOutRocket();
    	speedUpButton=simulationButtonsPanel.getSpeedUpButton();
    	speedDownButton=simulationButtonsPanel.getSpeedDownButton();
    	followRocket= simulationButtonsPanel.getFollowRocket();
    	targetPlanetButton=spaceObjectsPanel.getTargetPlanet();
    		

    	departurePlanetButton=spaceObjectsPanel.getDeparturePlanetButton();
    	
    	stateRocketPanel.setLayout(new GridBagLayout());
    	GridBagConstraints gbcState=new GridBagConstraints();
    	gbcState.insets=new Insets(8, 12, 8, 8);
    	gbcState.fill=GridBagConstraints.HORIZONTAL;
    	
    	gbcState.gridx=0;
    	gbcState.gridy=0;
    	gbcState.weightx=0;
    	stateRocketPanel.add(rocketStateLabel,gbcState);
    	gbcState.gridx=1;
    	gbcState.gridy=0;
    	gbcState.weightx=1.0;
    	rocketStateField.setEditable(false);
    	stateRocketPanel.add(rocketStateField,gbcState);
    	
    	gbcState.gridx=0;
    	gbcState.gridy=1;
    	gbcState.weightx=0;
    	stateRocketPanel.add(stagesLeftLabel,gbcState);
    	stagesLeftField.setEditable(false);
    	gbcState.gridx=1;
    	gbcState.gridy=1;
    	gbcState.weightx=1.0;
    	stateRocketPanel.add(stagesLeftField,gbcState);
    	
    	gbcState.gridx=0;
    	gbcState.gridy=2;
    	gbcState.weightx=0;
    	stateRocketPanel.add(missionDurationLabel,gbcState);
    	gbcState.gridx=1;
    	gbcState.gridy=2;
    	gbcState.weightx=1.0;
    	missionDurationField.setEditable(false);
    	stateRocketPanel.add(missionDurationField,gbcState);
    	

    	eastPanel.setLayout(new GridBagLayout());
    	GridBagConstraints gbcEast=new GridBagConstraints();
    	gbcEast.insets=new Insets(5, 5, 1, 5);
    	gbcEast.fill=GridBagConstraints.HORIZONTAL;
    	
    	gbcEast.gridx=0;
    	gbcEast.gridy=0;
    	gbcEast.weighty=0.1;
    	eastPanel.add(simulationButtonsPanel,gbcEast);
    	gbcEast.gridx=0;
    	gbcEast.gridy=1;
    	gbcEast.weighty=0.1;
    	eastPanel.add(spaceObjectsPanel,gbcEast);
    	gbcEast.gridx=0;
    	gbcEast.gridy=2;
    	gbcEast.weighty=0.1;
    	eastPanel.add(stateRocketPanel,gbcEast);
    	gbcEast.gridx=0;
    	gbcEast.gridy=3;
    	
    	gbcEast.weighty=0.5;
    	gbcEast.weightx = 1;
    	gbcEast.fill = GridBagConstraints.BOTH;
    	missionPhasePanel=new MissionPhasePanel(simulationManager);
    	eastPanel.add(missionPhasePanel,gbcEast);
    	
    	
    	contentPane.add(eastPanel,BorderLayout.EAST);
    	
        dashboard=new SimulationDisplay(simulationManager);
        
        MouseInputAdapter mouseInput = new MouseInput();
		dashboard.addMouseListener(mouseInput);
		dashboard.addMouseMotionListener(mouseInput);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(chartsPanel,BorderLayout.SOUTH);
        centerPanel.add(dashboard,BorderLayout.CENTER);
        
        contentPane.add(centerPanel,BorderLayout.CENTER);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(IDEAL_MAIN_DIMENSION);
        setResizable(false);
        pack();
        setVisible(true);
    	

    }
    
    
    public void initStyle() {
    	
        missionSittingButton.setFont(SimulationConfig.BUTTON_FONT);
        missionSittingButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        missionSittingButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        
        soundButton.setFont(SimulationConfig.BUTTON_FONT);
        soundButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        soundButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE); 
        
        //LABELS
       // countDownLabel.setFont(SimulationConfig.LABEL_FONT);
        altitudeLabel.setFont(SimulationConfig.LABEL_FONT);
        stagesLeftLabel.setFont(SimulationConfig.LABEL_FONT);
        rocketStateLabel.setFont(SimulationConfig.LABEL_FONT);
        missionDurationLabel.setFont(SimulationConfig.LABEL_FONT);
        velocityLabel.setFont(SimulationConfig.LABEL_FONT);

        //FIELD
        altitudeField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        altitudeField.setFont(SimulationConfig.TEXTFIELD_FONT);
        stagesLeftField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        stagesLeftField.setFont(SimulationConfig.TEXTFIELD_FONT);
        
        altitudeField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        stagesLeftField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        
        rocketStateField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        rocketStateField.setFont(SimulationConfig.TEXTFIELD_FONT);
        rocketStateField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        missionDurationField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        missionDurationField.setFont(SimulationConfig.TEXTFIELD_FONT);
        missionDurationField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        velocityField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        velocityField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        velocityField.setFont(SimulationConfig.TEXTFIELD_FONT);

        //PANELS
        buttonsPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder buttonsBorder= new TitledBorder("Simulation Controls");
        buttonsBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        buttonsBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        buttonsBorder.setTitleColor(SimulationConfig.BUTTON_COLOR_BACK);
        simulationButtonsPanel.setBorder(buttonsBorder);
        simulationButtonsPanel.setBackground(SimulationConfig.PANEL_COLOR);
        
        TitledBorder objectsButtonsBorder= new TitledBorder("Space Objects Controls");
        objectsButtonsBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        objectsButtonsBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        objectsButtonsBorder.setTitleColor(SimulationConfig.BUTTON_COLOR_BACK);
        spaceObjectsPanel.setBorder(objectsButtonsBorder);
        spaceObjectsPanel.setBackground(SimulationConfig.PANEL_COLOR);

        stateRocketPanel.setBackground(SimulationConfig.PANEL_COLOR);
        stateRocketPanel.setPreferredSize(new Dimension(580, 220));
        TitledBorder rocketBorder=new TitledBorder("Information");
        rocketBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        rocketBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        stateRocketPanel.setBorder(rocketBorder);
        
        
        missionPhasePanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder stageBorder=new TitledBorder("Mission Phases");
        stageBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        stageBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        missionPhasePanel.setBorder(stageBorder);
        
        chartsPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder chartsBorder= new TitledBorder("");
        chartsBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        chartsPanel.setBorder(chartsBorder);
        
        centerPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder centerBorder= new TitledBorder("");
        centerBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        centerPanel.setBorder(centerBorder);

        eastPanel.setPreferredSize(IDEAL_PANEL_DIMENSION);
        eastPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder eastBorder= new TitledBorder("");
        eastBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        eastPanel.setBorder(eastBorder);
        
        chartsPanel.setPreferredSize(new Dimension((int)SimulationConfig.DASHBOARD_WIDTH,300));
        missionPhasePanel.setBackground(SimulationConfig.PANEL_COLOR);

        
    }
    
    public void initActions() {
    	
    	resetButton.addActionListener(new ResetAction());
        pauseButton.addActionListener(new StartStopAction());
        zoomInButton.addActionListener(new ZoomInAction());
        zoomOutButton.addActionListener(new ZoomOutAction());
        missionSittingButton.addActionListener(new Settings());
        zoomInRocket.addActionListener(new ZoomInRocket());
        zoomOutRocket.addActionListener(new ZoomOutRocket());
        launchButton.addActionListener(new LaunchRocket());
        speedUpButton.addActionListener(new SpeedUp());
        speedDownButton.addActionListener(new SpeedDown());
        followRocket.addActionListener(new FollowRocket());
        targetPlanetButton.addActionListener(new FocusInTargetPlanet());
        departurePlanetButton.addActionListener(new FocusInDeparturePlanet());
        soundButton.addActionListener(new SoundOff());
        helpSimulation.addActionListener(new HelpSimultation());
    	
    }
    
    
    
    /**
     * Action related to the button "Launch"
     * By pressing it, the user will launch the simulation
	 * The display animation will start
     * @see Thread
     */
    private class LaunchRocket implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    	    if(!launchDone) {
    	    	launchDone=true;
    	    	stop=false;
	    		simulationManager.getMobileManager().rocketLaunch();
	    		Rocket rocket=simulationManager.getMobileManager().getRocket();
	    		chartsData=new ChartsData(rocket);
	    		focusManager=new FocusManager();
	    		
	    		new Thread(instance).start();
    	    	
    	    }
    		
    	}
    }
    
    /**
	    * This method continuously updates the simulation as long as the
	    * {@code stop} flag remains false, at each iteration the display is repainted,
	    * and refresh GUI labels to reflect the current values.
	    */
	 @Override
	    public void run() {
		
	        while (!stop) {
	            try {
	                Thread.sleep((int)SimulationConfig.SIMULATION_SPEED);
	            } catch (InterruptedException e) {
	                System.out.println(e.getMessage());
	            }
	            	simulationManager.nextRound(); 
	                Rocket  rocket=simulationManager.getMobileManager().getRocket();

	                if (!missionSaved && isMissionComplete(rocket)) {
	                    missionSaved=true;
	                    missionSave();
	                }
	                
	                if(chartsData !=null) {
		                double[] data =chartsData.updateChartsData();
		                double propellant=chartsData.updatePropellantChart();
		                int currentStageId=chartsData.getCurrentStageId();
		                chartsPanel.updateCharts(data[0],data[1],propellant,currentStageId);
		             
	                }
	                
	                if(SimulationConfig.follow && focusManager != null) {
	                	focusManager.updateFocus(rocket);
	                }
	                
		            double numberStagesLeft=rocket.getStageNumber();
		            stagesLeftField.setText(String.valueOf(numberStagesLeft));
		            String rocketState=rocket.getRocketState();
		            
		            if(rocketState.equals("FAILURE")) {
		            	rocketStateField.setBackground(Color.red);
		            }
		            rocketStateField.setText(rocketState);
		            
		            //REPAINT
		            missionPhasePanel.repaint();
		            chartsPanel.repaint();
		            dashboard.repaint();
		            
		            if (!stop){
		                updatesValues();
		            }
	            }
 
	        
	    }
   
    /**
	 * Action related to the button "Mission Settings"
	 * By clicking on it, the user will be redirected to the mission settings
	 * Where he will be able to begin a new mission with new parameters
	 */
    private class Settings implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		reset();
    		if (music!=null) music.stop();
        	new EntryGUI("Settings");
        	instance.dispose();
        	

        }
    	
    }
    
	        /**
			 * Action related to the button "Zoom In"
	         * Changing the scale to zoom in the dashboard
	         */
	        private class ZoomInAction implements ActionListener {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if(SimulationConfig.SCALE<=2.27E-4) {
	            		SimulationConfig.SCALE*=1.5;	                
	            		dashboard.repaint();
	            	}
	            }
	        }
	        /**
			 * Action related to the button "Zoom Out"
	         * Changing the scale to zoom out the dashboard
	         */
	        private class ZoomOutAction implements ActionListener {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            if(SimulationConfig.SCALE>=1.20E-6) {
	                SimulationConfig.SCALE/=1.5;
	                dashboard.repaint();
	            }
	            }
	        }

	        /**
	         * Initialize the help menu in the menu bar.
	         */
	        private void initMenuBar() {
	        	helpMenu.add(helpSimulation);
	            menuBar.add(helpMenu);
	            instance.setJMenuBar(menuBar);
	        }

	         
	        private class HelpSimultation implements ActionListener{
	        	public void actionPerformed(ActionEvent e) {
	        		helpGUI.help("simulation");
	        	}
	        }
	         
	         private class MouseInput extends MouseInputAdapter{
	        	 int x;
	        	 int y;
	     		@Override
	     		public void mousePressed(MouseEvent e) {
	     			x = e.getX();
	     			y = e.getY();
	     		}

	     		@Override
	     		public void mouseDragged(MouseEvent e) {
	     			int deltaX=(int) (e.getX()-x);
	     			int deltaY=(int) (e.getY()-y);
	     			
	     			SimulationConfig.dASHBOARD_CENTERX+=deltaX;
	     			SimulationConfig.dASHBOARD_CENTERY+=deltaY;
	     			
	     			x=e.getX();
	     			y=e.getY();
	     			
	     			dashboard.repaint();
	     		
	     		}
	     		
	     		
	     	}
	     
	        
	     	/**
			 * Action related to the button "In Rocket"
	         * Widering the scale to better see the rocket
	         */
	        private class ZoomInRocket implements ActionListener {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SimulationConfig.scale_ROCKET*=2;
	                
	                dashboard.repaint();
	            }
	        }
	        
	        /**
			 * Action related to the button "Out Rocket"
	         * Shrinking the scale to lesser see the rocket
	         */
	        private class ZoomOutRocket implements ActionListener {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               SimulationConfig.scale_ROCKET/=2;
	                
	                dashboard.repaint();
	            }
	        }

			 /**
			 * Action related to the button "Speed Up"
	         * To upper the display speed
	         */
	        private class SpeedUp implements ActionListener{
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		if (SimulationConfig.SIMULATION_SPEED/2>=SimulationConfig.MIN_SIMULATION_SPEED) {
	                    SimulationConfig.SIMULATION_SPEED/=2;
	                }	        	}
	        }

			/**
			 * Action related to the button "Speed Down"
	         * To lower the display speed
	         */
	        private class SpeedDown implements ActionListener{
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		 if (SimulationConfig.SIMULATION_SPEED*2<=SimulationConfig.MAX_SIMULATION_SPEED) {
	        	            SimulationConfig.SIMULATION_SPEED*=2;
	        	        }
	        	}
	        }

	 	    
	 	    /**
	 	     * Action related to the button "Pause",
	 	     * When the user presses the button, it stops the simulation
	 	     * until he presses again the same button,
	 	     * The text of the button changes when he presses it from "Pause" to "Continue"
	 	     * @see Thread
	 	     */
	 	     private class StartStopAction implements ActionListener {
	 	         public void actionPerformed(ActionEvent e) {
	 	             //To avoid starting from pause button!
	 	             if (!launchDone) {
	 	                 return;
	 	             }
	 	             if (!stop) {
	 	                 stop = true;
	 	                 pauseButton.setText("Continue ");

	 	             } else {
	 	                 stop = false;
	 	                 pauseButton.setText(" Pause ");
	 	               
	 	                 Thread launchSimulation = new Thread(instance);
	 	                 launchSimulation.start();
	 	             }
	 	         }
	 	     }
	 	     
	 	     /**
	 	      * This method interact with the count down display,
	 	      * This changes the text display to update the new counter.
	 	      */
	 	      private void updatesValues(){
	 	          double missionDurationSeconds=simulationManager.getMission().getDuration()*86400.0;
                  missionDurationField.setText(formatTime(missionDurationSeconds));

	 	      }
	 	      
	 	       /**
	 	        * Action listener related to the button "Reset",
	 	        * It is responsible for resting the simulation,
	 	        * When you press the button, it repaints the graphic interface.
	 	        * @see SimulationBuilder
	 	        */
	 	       private class ResetAction implements ActionListener {
	 	            @Override
	 	            public void actionPerformed(ActionEvent e) {
	 	                reset();
	 	            }

	 	        }
	 	       
	 	       public void reset() {
	 	    	  
	 	    	    pauseButton.setText("Pause");
	                stop = true;
	                launchDone=false;
	                missionSaved = true;
	                cleanField();
	                chartsPanel.resetChart();
	                chartsPanel.repaint();
	                mission.setDuration(0);
	                propulsions=initialPropulsion(initialPropulsions);
	                
	                EngineConfig.isLEO=mission.getMissionType().equals(EngineConfig.LEOName);
	                EngineConfig.isGEO=mission.getMissionType().equals(EngineConfig.GEOName);
	                EngineConfig.isInterplanetary=mission.getMissionType().equals(EngineConfig.InterplaneteryName);

	                simulationManager=SimulationBuilder.buildSimulation(mission,propulsions);
	                chartsData = null;
		    		focusManager=null;

	                dashboard.setSimulationManager(simulationManager);
	                missionPhasePanel.setSimulationManager(simulationManager);
	                
	                rocketStateField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
	                SimulationConfig.follow=false;
	                SimulationConfig.scale_ROCKET=3000;
	                SimulationConfig.SCALE=1.0/30000.0;
	                SimulationConfig.scale_SATELLITE=450;
	                SimulationConfig.SIMULATION_SPEED=SimulationConfig.DEFAULT_SIMULATION_SPEED;
	                SimulationConfig.dASHBOARD_CENTERX = SimulationConfig.DASHBOARD_WIDTH / 2;
	                SimulationConfig.dASHBOARD_CENTERY = SimulationConfig.DASHBOARD_HEIGHT / 2;
	                followRocket.setText("Follow");
	                
	                missionSaved=false;

	                missionPhasePanel.revalidate();
	                missionPhasePanel.repaint();
	                dashboard.repaint();
	 	       }
	
	 	       /**
	 	        * Method that reinitialize every dynamic text fields
				* Related to the estate of the rocket
	 	        */
	 	  public void cleanField() {
	 	     stagesLeftField.setText("");
	 	     rocketStateField.setText("");
	 	     missionDurationField.setText("");
	 	     altitudeField.setText("");
	 	     velocityField.setText("");
	 	    }
	 	       
	 	       /**
	 	        * Creates a copy of the propulsions to avoid using last values after a reset 
	 	        * @param propulsions
	 	        * @return
	 	        */
	 	public ArrayList<Propulsion> initialPropulsion(ArrayList<Propulsion> propulsions) {
	 	    ArrayList<Propulsion> initialPropulsions= new ArrayList<Propulsion>();
	 	    for (Propulsion propulsion: propulsions) {
	 	    		   TankPropellant tankPropellant=new TankPropellant(propulsion.getTankPropellant().getPropellantMass());
	 	    		   RocketEngine rocketEngine=new RocketEngine(propulsion.getRocketEngine().getThrust(), propulsion.getRocketEngine().getSpecificImpulse(),
	 	    				   propulsion.getRocketEngine().getMass());
	 	    		   initialPropulsions.add(new Propulsion(rocketEngine, tankPropellant));
	 	    	   }
	 	    		   return initialPropulsions;
	 	       }
	 	       
	 	private class FollowRocket implements ActionListener{
	 		@Override
	 		public void actionPerformed(ActionEvent e) {
	 		if(launchDone) {
	 			SimulationConfig.follow=!SimulationConfig.follow;
	 			if(SimulationConfig.follow) {
	 				followRocket.setText("Unfollow");
	 				SimulationConfig.scale_SATELLITE=150;
	 				SimulationConfig.SCALE=1.0/2000.0;
	 			}else {
	 				followRocket.setText("Follow");
	 				SimulationConfig.SCALE=1.0/50000.0;
	 				SimulationConfig.dASHBOARD_CENTERX=SimulationConfig.DASHBOARD_WIDTH / 2;
	 	            SimulationConfig.dASHBOARD_CENTERY=SimulationConfig.DASHBOARD_HEIGHT / 2;
	 	            dashboard.repaint();
	 			}
	 		}
	 		}
	 	}
	 	
	 	private class FocusInTargetPlanet implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Planet targetPlanet=SolarSystem.getInstance().getPlanets().get(mission.getPlanetArrival());
                if(targetPlanet==null) return;
               SimulationConfig.dASHBOARD_CENTERX = SimulationConfig.DASHBOARD_WIDTH / 2 -targetPlanet.getCurrentPosition().getX() * SimulationConfig.SCALE
            		   +SimulationConfig.INITIAL_REFERENCE_X*SimulationConfig.SCALE;
               SimulationConfig.dASHBOARD_CENTERY = SimulationConfig.DASHBOARD_HEIGHT / 2 -targetPlanet.getCurrentPosition().getY() * SimulationConfig.SCALE
                       +SimulationConfig.INITIAL_REFERENCE_Y*SimulationConfig.SCALE;
                dashboard.repaint();
            }
        }
	 	
	 	private class FocusInDeparturePlanet implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Planet targetPlanet=null;

            	targetPlanet=SolarSystem.getInstance().getPlanets().get(EngineConfig.earthName);

                SimulationConfig.dASHBOARD_CENTERX=(SimulationConfig.DASHBOARD_WIDTH/2)-targetPlanet.getCurrentPosition().getX()*SimulationConfig.SCALE
                		+SimulationConfig.INITIAL_REFERENCE_X*SimulationConfig.SCALE;
                SimulationConfig.dASHBOARD_CENTERY=(SimulationConfig.DASHBOARD_HEIGHT/2)-targetPlanet.getCurrentPosition().getY()*SimulationConfig.SCALE
                		+SimulationConfig.INITIAL_REFERENCE_Y*SimulationConfig.SCALE;
                dashboard.repaint();
            }
        }
	 	
	 	private class SoundOff implements ActionListener{
	 		@Override
	 		public void actionPerformed(ActionEvent e) {
	 			
	 			if (soundOn) {
	 				if (music!=null) music.stop();
	 	            soundOn=false;
	 	            soundButton.setText("Sound On");
	 	        } else {
	 	        	music=new Music();
	 	            music.play();
	 	            soundOn=true;
	 	            soundButton.setText("Sound Off");
	 	        }
            }
	 		
	 	}
	 	
	 	private void missionSave() {
	 		Object[] options = {"Yes", "No"};
	 		String message=null;
	 		if(EngineConfig.isLEO || EngineConfig.isGEO) {
	 			message="The satellite has successfully reached the orbit!";
	 		}else {
	 			message="The probe has successfully landed!";
	 		}
	 	    int choice=JOptionPane.showOptionDialog(instance,message+"\n\nWould you like to save the mission report?","Mission Complete",JOptionPane.YES_NO_OPTION,
	 	        JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
	 	    if (choice==JOptionPane.YES_OPTION) {
	 	        saveMissionReport();
	 	    }
	 	}
	 	
	 	private void saveMissionReport() {
	 	    JFileChooser fileChooser=new JFileChooser();
	 	    fileChooser.setDialogTitle("Save Mission Report");

	 	    int result=fileChooser.showSaveDialog(instance);
	 	    if (result!=JFileChooser.APPROVE_OPTION) return;

	 	    File file=fileChooser.getSelectedFile();
	 	    
	 	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	 	        writer.write("******MISSION REPORT*****");writer.newLine();
	 	        writer.write("Name : " +mission.getName());writer.newLine();
	 	        writer.write("Description : " +mission.getDescription());writer.newLine();
	 	        writer.write("Type : " + mission.getMissionType());writer.newLine();
	 	        writer.write("Departure : " +mission.getPlanetDeparture());writer.newLine();
	 	        writer.write("Destination : " +(mission.getPlanetArrival()!=null?mission.getPlanetArrival():"earth")); writer.newLine();	 
	 	        writer.write("Duration    : " +formatTime(simulationManager.getMission().getDuration() * 86400.0)); writer.newLine();	 	        
	 	        writer.write("Rocket state : " +simulationManager.getMobileManager().getRocket().getRocketState());writer.newLine();
	 	        writer.write("Generated: " + new Date());writer.newLine();
	 	        writer.write("***************************");
	 	        JOptionPane.showMessageDialog(instance,"Report saved!","Save Successful",JOptionPane.INFORMATION_MESSAGE);
	 	    } catch (IOException e) {
	 	        JOptionPane.showMessageDialog(instance,"Error: " +e.getMessage(),"Save Error",JOptionPane.ERROR_MESSAGE);
	 	    }
	 	}
	 	
	 	private boolean isMissionComplete(Rocket rocket) {
	 		if (EngineConfig.isInterplanetary) {
	 	        return rocket.getSaveProbe()!=null&&rocket.getSaveProbe().isLanded();
	 	    }
	 	    if (EngineConfig.isGEO || EngineConfig.isLEO) {
	 	        Satellite satellite=rocket.getSaveSatellites().isEmpty() ?null:rocket.getSaveSatellites().get(0);
	 	        return satellite!=null && satellite.isInOrbit();
	 	    }
	 	    return false;
	 	}
	 	/**
	       * This method changes an integer into a string of characters,
	       * to display a duration in hour minute second format.
	       * @param seconds
	       * @return formatted duration
	       */
	       private static String formatTime(double seconds) {
	           int totalSeconds=(int)Math.round(seconds);
	           int hours=totalSeconds/3600;
	           int minutes=(totalSeconds % 3600)/60;
	           int remainingSeconds=totalSeconds%60;
	           return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
	       }


}
