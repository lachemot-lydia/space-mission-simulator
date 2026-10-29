package gui.elements;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.SimulationConfig;

/**
 * A form panel for configuring the propulsion parameters of up to three rocket stages.
 */
public class PropulsionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
    private final static Dimension IDEAL_TEXTFIELD_DIMENSION=new Dimension(200,50);

	//ROCKET
    //Stage1
    private JLabel firstStage=new JLabel("Stage 1:");
    private JLabel firstThrustLabel=new JLabel("Thrust (N):");
    private JTextField firstThrustTextField;
    private JLabel firstPropellantLabel=new JLabel("Propellant (Kg):");
    private JTextField firstPropellantTextField;
    //Stage2
    private JLabel secondStage=new JLabel("Stage 2:");
    private JLabel secondThrustLabel=new JLabel("Thrust (N):");
    private JTextField secondThrustTextField;
    private JLabel secondPropellantLabel=new JLabel("Propellant (Kg):");
    private JTextField secondPropellantTextField;
    //Stage3
    private JLabel thirdStage=new JLabel("Stage 3: (Interplanetary)");
    private JLabel thirdThrustLabel=new JLabel("Thrust (N):");
    private JTextField thirdThrustTextField;
    private JLabel thirdPropellantLabel=new JLabel("Propellant (Kg):");
    private JTextField thirdPropellantTextField;
    
	public PropulsionPanel() {
		firstThrustTextField=new JTextField();
		firstPropellantTextField=new JTextField();
		secondThrustTextField=new JTextField();
		secondPropellantTextField=new JTextField();
		thirdThrustTextField=new JTextField();
		thirdPropellantTextField=new JTextField();
		
		initLayout();
		initStyle();		
	}
	
	public void initLayout() {
		
		setLayout(new GridBagLayout());
        GridBagConstraints gbcPropulsion=new GridBagConstraints();
        gbcPropulsion.insets=new Insets(5, 5, 1, 5);
        gbcPropulsion.fill=GridBagConstraints.HORIZONTAL;
        //FIRST STAGE
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=0;
        add(firstStage,gbcPropulsion);

        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=1;
        
        add(firstThrustLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=1;
        
        add(firstThrustTextField,gbcPropulsion);
        
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=2;
        add(firstPropellantLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=2;
        add(firstPropellantTextField,gbcPropulsion);
        
        //SECOND STAGE
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=3;
        add(secondStage,gbcPropulsion);
        
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=4;
        add(secondThrustLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=4;
        add(secondThrustTextField,gbcPropulsion);
        
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=5;
        add(secondPropellantLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=5;
        add(secondPropellantTextField,gbcPropulsion);
        
        //THIRD STAGE
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=6;
        add(thirdStage,gbcPropulsion);
        
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=7;
        add(thirdThrustLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=7;
        add(thirdThrustTextField,gbcPropulsion);
        
        gbcPropulsion.gridx=0;
        gbcPropulsion.gridy=8;
        add(thirdPropellantLabel,gbcPropulsion);
        gbcPropulsion.gridx=1;
        gbcPropulsion.gridy=8;
        add(thirdPropellantTextField,gbcPropulsion);

	}

	public void initStyle() {
		
		firstStage.setFont(SimulationConfig.LABEL_FONT);
        firstThrustLabel.setFont(SimulationConfig.LABEL_FONT);
        firstPropellantLabel.setFont(SimulationConfig.LABEL_FONT);
        
        secondStage.setFont(SimulationConfig.LABEL_FONT);
        secondPropellantLabel.setFont(SimulationConfig.LABEL_FONT);
        secondThrustLabel.setFont(SimulationConfig.LABEL_FONT);
        
        thirdStage.setFont(SimulationConfig.LABEL_FONT);
        thirdPropellantLabel.setFont(SimulationConfig.LABEL_FONT);
        thirdThrustLabel.setFont(SimulationConfig.LABEL_FONT);
        
        firstThrustTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        firstThrustTextField.setFont(SimulationConfig.TEXTFIELD_FONT);
        firstPropellantTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        firstPropellantTextField.setFont(SimulationConfig.TEXTFIELD_FONT);

        secondThrustTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        secondThrustTextField.setFont(SimulationConfig.TEXTFIELD_FONT);
        secondPropellantTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        secondPropellantTextField.setFont(SimulationConfig.TEXTFIELD_FONT);

        thirdThrustTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        thirdThrustTextField.setFont(SimulationConfig.TEXTFIELD_FONT);
        thirdPropellantTextField.setBackground(SimulationConfig.TEXTFIELD_COLOR_BACK);
        thirdPropellantTextField.setFont(SimulationConfig.TEXTFIELD_FONT);
        
        firstThrustTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        secondThrustTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        thirdThrustTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        firstPropellantTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        secondPropellantTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
        thirdPropellantTextField.setPreferredSize(IDEAL_TEXTFIELD_DIMENSION);
		
	}

	public JTextField getFirstThrustTextField() {
		return firstThrustTextField;
	}

	public JTextField getFirstPropellantTextField() {
		return firstPropellantTextField;
	}

	public JTextField getSecondThrustTextField() {
		return secondThrustTextField;
	}

	public JTextField getSecondPropellantTextField() {
		return secondPropellantTextField;
	}

	public JTextField getThirdThrustTextField() {
		return thirdThrustTextField;
	}

	public JTextField getThirdPropellantTextField() {
		return thirdPropellantTextField;
	}

	public JLabel getThirdStage() {
		return thirdStage;
	}

	public JLabel getThirdThrustLabel() {
		return thirdThrustLabel;
	}

	public JLabel getThirdPropellantLabel() {
		return thirdPropellantLabel;
	}
	
}
