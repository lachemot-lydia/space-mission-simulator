package gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import config.SimulationConfig;
import gui.management.ChartManager;

/**
 * A panel displaying three real time mission charts side by side:
 * velocity, propellant consumption, and altitude.
 * */
public class ChartsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private ChartManager chartManager = new ChartManager();
	private GridBagConstraints gbcChart=new GridBagConstraints();
	private ChartPanel velocityChartPanel;
	private ChartPanel altitudeChartPanel;
	private ChartPanel propellantChartPanel;
	
	
	public ChartsPanel() {
		setLayout(new GridBagLayout());
		gbcChart.fill=GridBagConstraints.BOTH;
        gbcChart.weightx=1.0;
        gbcChart.weighty=1.0;
		initLayout();
		initStyle();
	
	}
	
	public void initLayout() {
		
		gbcChart.insets = new Insets(20,50, 20, 50);
		gbcChart.gridx=0;
		gbcChart.gridy=0;
		velocityChartPanel=new ChartPanel(chartManager.getVelocityChart());
		add(velocityChartPanel,gbcChart);
		
		gbcChart.gridx=1;
		gbcChart.gridy=0;
		propellantChartPanel=new ChartPanel(chartManager.getPropellantChart());
		add(propellantChartPanel,gbcChart);
		
		gbcChart.gridx=2;
		gbcChart.gridy=0;
		altitudeChartPanel=new ChartPanel(chartManager.getAltitudeChart());
		add(altitudeChartPanel,gbcChart);
	}
	
	
	public void updateCharts(double velocity,double altitude,double propellant,int stageId) {
		chartManager.registerVelocity(velocity);
		chartManager.registerAltitude(altitude);
		chartManager.registerPropellant(propellant,stageId);
	}
	
	public void resetChart() {
		chartManager.reset();
	}
	
	public void initStyle() {
		setBackground(SimulationConfig.PANEL_COLOR);
		styleChartPanel(velocityChartPanel);
		styleChartPanel(propellantChartPanel);
		styleChartPanel(altitudeChartPanel);
	}
	
	private void styleChartPanel(ChartPanel chartPanel) {
		chartPanel.setBackground(SimulationConfig.PANEL_COLOR);
		chartPanel.setPreferredSize(new Dimension(420, 220));
		chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(SimulationConfig.BUTTON_COLOR_BACK, 2),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
	}
}
