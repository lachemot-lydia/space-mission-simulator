package gui.elements;

import config.SimulationConfig;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * Provides contextual help dialogs for the simulation application.
 */
public class HelpGUI  {

    public static final  Font LABEL_FONT = new Font(Font.MONOSPACED, Font.BOLD, 17);

    public HelpGUI() {}

    public void help(String page) {
        String content;
        String title;
        
        if (page.equals("settings")) {
            content=
            "1. Choose a mission type: \n"+
            		"\t- LEO: Low Earth Orbit at 200km\n" +
            		"\t- GEO: Geostationary Orbit at 35786km\n" +
            		"\t- Interplanetary: Mission to Moon, Jupiter, Mercury or Mars\n\n" +
            "2. Choose a destination planet\n\n" +
            "3. Enter a mission name and description\n\n" +
            "4. Propulsion Configuration:\n" +
            		"\t- Thrust (N)\n" +
            		" \t- Propellant mass (kg)\n\n" +
            "5. Launch Simulation : begin the space simulation\n";
            title="Help Settings";
        } else {
            content=
            "1. Simulation Controls :\n" +
            		"\t-Launch : to begin the simulation\n" +
            		"\t-Follow/Unfollow : to track the current spacecraft\n" +
            		"\t-Pause/Continue : to stop the simulation\n" +
            		"\t-Speed Up and Speed Down : to manage the frame rate\n\n" +
            "2. Space Objects Controls :\n" +
            		"\t-Zoom In and Zoom out : to manage the width of the display\n" +
            		"\t-In Rocket and Out Rocket : to manage the width of the spacecraft\n" +
            		"\t-Departure planet : to locate the simulation display on the departure planet\n" +
            		"\t-Target planet : if interplanetary choosen,\n\t\tit locates the simulation display on the arrival planet\n\n" +
            "3. Information :\n" +
            		"\t-Rocket State : it shows how the rocket is going\n" +
            		"\t-Stages left : the number of stages stick to the satelltite or probe\n" +
            		"\t-Mission duration : time spent since the launch of the rocket\n\n" +
            "4. Mission Phases :\n" +
            		"\t-Shows every curent state of the simulation\n\n" +
            "5. Charts :\n" +
            		"\t-Velocity : the velocity value in meter per second of the spacecraft\n" +
            		"\t-Propellant : the propellant value left of every stages\n" +
            		"\t-Altitude : the altitude value in meter of the spacecraft\n\n" +
            "6. Menu :\n" +
            		"\t-Mission Settings : go back to the first setting page\n" +
            		"\t-Sounds On/Sounds Off : to turn on and off the sound";
            title = "Help Simulation";
        }
        
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setFont(LABEL_FONT);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        textArea.setBackground(SimulationConfig.PANEL_COLOR);

        JScrollPane scrollPane=new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null,scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}