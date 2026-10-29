package gui;

import config.EngineConfig;
import config.SimulationConfig;
import engine.data.Mission;
import engine.data.Propulsion;
import engine.process.builders.MissionBuilder;
import engine.process.builders.PropulsionBuilder;
import engine.process.builders.SimulationBuilder;
import engine.process.managers.SimulationManager;
import gui.elements.HelpGUI;
import gui.elements.PropulsionPanel;
import gui.elements.SchemaPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * <p>A class that permits the proper functioning of the simulation</p>
 * <p>It retrieves data from the graphical interface writed by the user</p>
 * <p>Finally it launches the mission with the custom settings or default settings</p>
 * @version 2.0
 * @since 2026-03-15
 */
public class EntryGUI extends JFrame {
	private static final long serialVersionUID = 1L;

    private final static Dimension IDEAL_MAIN_DIMENSION = new Dimension(SimulationConfig.WINDOW_WIDTH, SimulationConfig.WINDOW_HEIGHT);
    private final static Dimension IDEAL_PANEL_DIMENSION=new Dimension(700,SimulationConfig.WINDOW_HEIGHT-150);
    private final static Dimension IDEA_COMBOX_DIMENSION=new Dimension(150,75);
    
    
    //help
    HelpGUI helpGUI = new HelpGUI();

    //MISSION
    private JLabel missionNameLabel=new JLabel("Name:");
    private JLabel missionDescriptionLabel=new JLabel("Description:");
    private JLabel missionTypeLabel=new JLabel("Type:");
    private JLabel missionDestinationLabel=new JLabel("Destination:");
    private JButton  exportMissionButton=new JButton("Export mission");
    private JTextField missionNameTextField=new JTextField();
    private JTextField missionDescriptionTextField=new JTextField();
    private JComboBox<String> missionTypeComboBox;
    private JComboBox<String> missionDestinationComboBox;
    
    //BUTTON
    private JButton  launchButton=new JButton("Launch Simulation");

  //PANELS
    private JPanel buttonsPanel=new JPanel();
    private JPanel parametersPanel=new JPanel();
    private PropulsionPanel propulsionPanel=new PropulsionPanel();
    private JPanel missionPanel=new JPanel();
    private SchemaPanel entryDashboard;

    //MENU
    JMenuBar menuBar=new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem helpSimulation = new JMenuItem("settings");
    SimulationManager simulationManager;

    private EntryGUI instance = this;
    
    public  Mission mission;
    public  ArrayList<Propulsion> propulsions=new ArrayList<Propulsion>();
    
    /**
     * Constructs the main entry GUI window and fully initializes its components.
     * @param title the window title displayed in the title bar
     */
    public EntryGUI(String title) {
        super(title);
        initLayout();
    	initMenuBar();
        initStyle();
        initActions();
        initDefaultMissionDisplay();
    }

    private void initLayout() {
        Container contentPane =getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(launchButton);
        contentPane.add(buttonsPanel,BorderLayout.NORTH);

        String[] types={EngineConfig.LEOName,EngineConfig.GEOName,EngineConfig.InterplaneteryName};
        missionTypeComboBox=new JComboBox<>(types);
        String[] destinations= {EngineConfig.earthName,EngineConfig.moonName,EngineConfig.jupiterName,
        		EngineConfig.mercuryName,EngineConfig.marsName,EngineConfig.moonDName,EngineConfig.mercuryDName};
        missionDestinationComboBox=new JComboBox<>(destinations);
        missionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        missionPanel.add(missionTypeLabel);
        missionPanel.add(missionTypeComboBox);
        missionPanel.add(missionDestinationLabel);
        missionPanel.add(missionDestinationComboBox);
        missionPanel.add(missionNameLabel);
        missionPanel.add(missionNameTextField);
        missionPanel.add(missionDescriptionLabel);
        missionPanel.add(missionDescriptionTextField);
   
        contentPane.add(missionPanel,BorderLayout.SOUTH);
 
        parametersPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcParameters=new GridBagConstraints();
        gbcParameters.insets=new Insets(10,10,10,10);
        gbcParameters.fill=GridBagConstraints.HORIZONTAL;
        
        gbcParameters.gridx=0;
        gbcParameters.gridy=2;
        parametersPanel.add(propulsionPanel,gbcParameters);

        contentPane.add(parametersPanel,BorderLayout.EAST);
        
        entryDashboard=new SchemaPanel("src/images/rocketSchema.png");
        contentPane.add(entryDashboard,BorderLayout.CENTER);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(IDEAL_MAIN_DIMENSION);
        setResizable(false);
        pack();
        setVisible(true);

    }
    
    public void initStyle(){

    	//BUTTONS
    	launchButton.setFont(SimulationConfig.BUTTON_FONT);
        launchButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        launchButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        exportMissionButton.setFont(SimulationConfig.BUTTON_FONT);
        exportMissionButton.setBackground(SimulationConfig.BUTTON_COLOR_BACK);
        exportMissionButton.setForeground(SimulationConfig.BUTTON_COLOR_FORE);
        
        //LABELS
        missionTypeLabel.setFont(SimulationConfig.LABEL_FONT);
       // missionTypeLabel.setForeground(SimulationConfiguration.LABEL_COLOR_FORE);
        missionNameLabel.setFont(SimulationConfig.LABEL_FONT);
        missionDescriptionLabel.setFont(SimulationConfig.LABEL_FONT);
        missionDestinationLabel.setFont(SimulationConfig.LABEL_FONT);
        
        //TEXT FIELD
        missionNameTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        missionDescriptionTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);

        //COMBOX/SLIDER
        missionTypeComboBox.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        missionDestinationComboBox.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);

        //PANELS
        missionPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder missionBorder=BorderFactory.createTitledBorder("Mission Configuration");
        missionBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        missionBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        missionPanel.setBorder(missionBorder);
        
        parametersPanel.setBackground(SimulationConfig.PANEL_COLOR);

        propulsionPanel.setBackground(SimulationConfig.PANEL_COLOR);
        TitledBorder propulsionBorder=BorderFactory.createTitledBorder("Propulsion Configuration");
        propulsionBorder.setBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK));
        propulsionBorder.setTitleFont(SimulationConfig.LABEL_FONT);
        propulsionPanel.setBorder(propulsionBorder);

        buttonsPanel.setBackground(SimulationConfig.PANEL_COLOR);

        //DIMENSION
        missionPanel.setPreferredSize(new Dimension(SimulationConfig.WINDOW_WIDTH,150));
        
        missionTypeComboBox.setPreferredSize(IDEA_COMBOX_DIMENSION);
        missionDestinationComboBox.setPreferredSize(IDEA_COMBOX_DIMENSION);
        
        missionNameTextField.setPreferredSize(new Dimension(200,75));
        missionDescriptionTextField.setPreferredSize(new Dimension(400,75));
        
        parametersPanel.setPreferredSize(IDEAL_PANEL_DIMENSION);
	
    }
    
    /**
    * This method initialize every button to make the simulation works,
    * launching the simulation, exporting mission's data, reseting simulation
    * and put the simulation to a halt.
    */
    public void initActions(){
    	missionTypeComboBox.addActionListener(new MissionType());
        launchButton.addActionListener(new LaunchSimulation());
        helpSimulation.addActionListener(new HelpSimultation());
      
    }

    private void initDefaultMissionDisplay() {
    	missionTypeComboBox.setSelectedIndex(0);
    	updateMissionTypeDisplay();
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
    		helpGUI.help("settings");
    	}
    }

    /**
    * Action related to the button Launch Simulation,
    * when the user press the button,he is redirected to the simulation display
    * with the paramaters previously retrieved by himself
    */
    private class LaunchSimulation implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	initParameters();
        	simulationManager=SimulationBuilder.buildSimulation(mission, propulsions);
        	//Musique.play();
        	new MainGUI("Simulation",simulationManager,mission,propulsions);
        	instance.dispose();

        }
    }

	/**
     * Initialize the parameters of the mission.
     */
    private void initParameters() {
    	propulsions.clear();
    	mission=initializeMission(mission);
    	propulsions=initializePropulsion(propulsions);        
    	
    }

	/**
     * Initialize the values related to the propulsion of the rocket
	 * such as its thrust and its propellant.
     */
    private ArrayList<Propulsion> initializePropulsion(ArrayList<Propulsion> propulsions) {

        boolean isLG=EngineConfig.isLEO||EngineConfig.isGEO;

        double thrust1=verifyValue(propulsionPanel.getFirstThrustTextField(),"first thrust",isLG?EngineConfig.FIRST_THRUST_LG:EngineConfig.FIRST_THRUST_INTER);
        double thrust2=verifyValue(propulsionPanel.getSecondThrustTextField(),"second thrust",isLG?EngineConfig.SECOND_THRUST_LG:EngineConfig.SECOND_THRUST_INTER);
        double thrust3=verifyValue(propulsionPanel.getThirdThrustTextField(),"third thrust",EngineConfig.THIRD_THRUST_INTER);
        double propellant1=verifyValue(propulsionPanel.getFirstPropellantTextField(),"first propellant",isLG?EngineConfig.FIRST_PROPELLANT_LG :EngineConfig.FIRST_PROPELLANT_INTER);
        double propellant2=verifyValue(propulsionPanel.getSecondPropellantTextField(),"second propellant",isLG?EngineConfig.SECOND_PROPELLANT_LG:EngineConfig.SECOND_PROPELLANT_INTER);
        double propellant3=verifyValue(propulsionPanel.getThirdPropellantTextField(),"third propellant",EngineConfig.THIRD_PROPELLANT_INTER);

        double firstEngineMass=isLG?EngineConfig.FIRST_ENGINE_MASS_LG :EngineConfig.FIRST_ENGINE_MASS_INTER;
        double secondEngineMass=isLG?EngineConfig.SECOND_ENGINE_MASS_LG :EngineConfig.SECOND_ENGINE_MASS_INTER;
        double thirdEngineMass=EngineConfig.THIRD_ENGINE_MASS_INTER;

        propulsions.add(PropulsionBuilder.buildPropulsion(thrust1,propellant1,EngineConfig.FIRST_ISP,firstEngineMass));
        propulsions.add(PropulsionBuilder.buildPropulsion(thrust2,propellant2,EngineConfig.SECOND_ISP,secondEngineMass));
        propulsions.add(PropulsionBuilder.buildPropulsion(thrust3,propellant3,EngineConfig.THIRD_ISP,thirdEngineMass));

        return propulsions;
    }
    
    private double verifyValue(JTextField field,String fieldName,double value) {
    	String text=field.getText();
        if (text==null||text.isEmpty()) return value;
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(instance,"Invalid value for "+fieldName+"! Using default: "+value,"ERROR", JOptionPane.ERROR_MESSAGE);
            return value;
        }
    }

	
	/**
     * Initialize the fields related to the mission
	 * such as its type, its destination, its name and its description.
     */
	private Mission initializeMission(Mission mission) {
    	String missionType=(String)missionTypeComboBox.getSelectedItem();
    	String planetArrival=(String)missionDestinationComboBox.getSelectedItem();
    	String name;
		// check if the user has written the name of the mission
    	if(!missionNameTextField.getText().isEmpty() ) {
    		name=missionNameTextField.getText();
    	}else {//default name
    		name="";
    	}

		// check if the user has written the description of the mission
    	String description;
    	if(!missionDescriptionTextField.getText().isEmpty() ) {// retrieve its description
    		description=missionDescriptionTextField.getText();
    	}else {//default description
    		description="";
    	}
		//after verification of user input, retrieve all value of the mission into its builder variable
    	mission=MissionBuilder.buildMission(missionType, name, description, planetArrival);
    	return mission;
    }
	

	 public void thirdStageVisibility() {
         propulsionPanel.getThirdStage().setVisible(EngineConfig.isInterplanetary);
         propulsionPanel.getThirdThrustLabel().setVisible(EngineConfig.isInterplanetary);
         propulsionPanel.getThirdThrustTextField().setVisible(EngineConfig.isInterplanetary);
         propulsionPanel.getThirdPropellantLabel().setVisible(EngineConfig.isInterplanetary);
         propulsionPanel.getThirdPropellantTextField().setVisible(EngineConfig.isInterplanetary);
	 }


     /**
      * Action related to the combo box of the mission type,
      * <p>the schema of the rocket changes according to the type of the mission chosen by the user<p>
      * <p>the parameters of the third stage are visible only if the user choose the interplanetary mission type<p>
      */
     private class MissionType implements ActionListener{
         @Override
         public void actionPerformed(ActionEvent e) {
         	updateMissionTypeDisplay();
         }
     }

     private void updateMissionTypeDisplay() {
     	int index = missionTypeComboBox.getSelectedIndex();
         String firstThrust=null;
         String firstPropellant=null;
         String secondThrust=null;
         String secondPropellant=null;
         if (index == 0) {
             entryDashboard.setFilePath("src/images/leo.png");
             EngineConfig.isLEO=true;
             EngineConfig.isGEO=false;
             EngineConfig.isInterplanetary=false;
             missionDestinationComboBox.removeAllItems();
             missionDestinationComboBox.addItem(EngineConfig.earthName);
             firstThrust=String.valueOf(EngineConfig.FIRST_THRUST_LG);
             firstPropellant=String.valueOf(EngineConfig.FIRST_PROPELLANT_LG);
             secondThrust=String.valueOf(EngineConfig.SECOND_THRUST_LG);
             secondPropellant=String.valueOf(EngineConfig.SECOND_PROPELLANT_LG);
             propulsionPanel.getThirdThrustTextField().setText("");
             propulsionPanel.getThirdPropellantTextField().setText("");

         } else if (index == 1) {
             entryDashboard.setFilePath("src/images/geo.png");
             EngineConfig.isLEO=false;
             EngineConfig.isGEO=true;
             EngineConfig.isInterplanetary=false;
             missionDestinationComboBox.removeAllItems();
             missionDestinationComboBox.addItem(EngineConfig.earthName);
             firstThrust=String.valueOf(EngineConfig.FIRST_THRUST_LG);
             firstPropellant=String.valueOf(EngineConfig.FIRST_PROPELLANT_LG);
             secondThrust=String.valueOf(EngineConfig.SECOND_THRUST_LG);
             secondPropellant=String.valueOf(EngineConfig.SECOND_PROPELLANT_LG);
             propulsionPanel.getThirdThrustTextField().setText("");
             propulsionPanel.getThirdPropellantTextField().setText("");

         } else if (index == 2) {
             entryDashboard.setFilePath("src/images/saturnV.png");
             EngineConfig.isLEO=false;
             EngineConfig.isGEO=false;
             EngineConfig.isInterplanetary = true;
             missionDestinationComboBox.removeAllItems();
             missionDestinationComboBox.addItem(EngineConfig.moonName);
             missionDestinationComboBox.addItem(EngineConfig.jupiterName);
             missionDestinationComboBox.addItem(EngineConfig.mercuryName);
             missionDestinationComboBox.addItem(EngineConfig.marsName);
             missionDestinationComboBox.addItem(EngineConfig.moonDName);
             missionDestinationComboBox.addItem(EngineConfig.mercuryDName);
             firstThrust=String.valueOf(EngineConfig.FIRST_THRUST_INTER);
             firstPropellant=String.valueOf(EngineConfig.FIRST_PROPELLANT_INTER);
             secondThrust=String.valueOf(EngineConfig.SECOND_THRUST_INTER);
             secondPropellant=String.valueOf(EngineConfig.SECOND_PROPELLANT_INTER);
             propulsionPanel.getThirdThrustTextField().setText(String.valueOf(EngineConfig.THIRD_THRUST_INTER));
             propulsionPanel.getThirdPropellantTextField().setText(String.valueOf(EngineConfig.THIRD_PROPELLANT_INTER));
         }

         propulsionPanel.getFirstThrustTextField().setText(firstThrust);
         propulsionPanel.getFirstPropellantTextField().setText(firstPropellant);
         propulsionPanel.getSecondThrustTextField().setText(secondThrust);
         propulsionPanel.getSecondPropellantTextField().setText(secondPropellant);
         thirdStageVisibility();
         propulsionPanel.revalidate();
         propulsionPanel.repaint();
         entryDashboard.repaint();
     }



}



