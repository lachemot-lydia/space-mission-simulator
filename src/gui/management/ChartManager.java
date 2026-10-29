package gui.management;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import config.SimulationConfig;

/**
* Manages the three real time mission charts: velocity, altitude, and propellant.
* @version 3.0
**/
public class ChartManager {
	private static final Color CHART_BACKGROUND=SimulationConfig.TEXTFIELD_COLOR_BACK;
	private static final Color VELOCITY_COLOR=SimulationConfig.BUTTON_COLOR_BACK;
	private static final Color ALTITUDE_COLOR=new Color(70, 130, 180);
	private static final Color REMAINING_COLOR=new Color(60, 140, 90);
	private static final Color CONSUMED_COLOR=new Color(180, 70, 70);
	
	private XYSeries velocitySerie =new XYSeries("Velocity");
	private XYSeries altitudeSerie= new XYSeries("Altitude");
	private DefaultPieDataset pieDataset; 
	
	private JFreeChart velocityChart;
	private JFreeChart altitudeChart;
	private JFreeChart propellantChart;
	
	
	private double initialPropellant=-1;
	private int currentStageId=-1;
	
	public ChartManager() {
		
		velocityChart=buildChart("Velocity (m/s)",velocitySerie);
		altitudeChart=buildChart("Altitude (m)",altitudeSerie);
		propellantChart=buildChartPropellant();
		
	}
	
	
	public void registerVelocity(double velocity) {
		velocitySerie.add(velocitySerie.getItemCount(),velocity);
	}
	public void registerAltitude(double altitude) {
		altitudeSerie.add(altitudeSerie.getItemCount(),altitude);
	}
	public void registerPropellant(double propellant,int stageId) {
		
		if(currentStageId!=stageId) {
			currentStageId=stageId;
			initialPropellant=propellant;
		}
		updatePropellantChartTitle(stageId);
		if(initialPropellant<0) {
			initialPropellant=propellant;
		}
		double consumed=initialPropellant-propellant;
		pieDataset.setValue("Remaining Propellant", propellant);
		pieDataset.setValue("Consumed Propellant", consumed);
	}
	
	
	public JFreeChart buildChart(String yLabel,XYSeries serie ) {
		XYSeriesCollection dataset= new XYSeriesCollection();
		dataset.addSeries(serie);
		JFreeChart chart = ChartFactory.createXYLineChart("", "Time step (s)",yLabel, dataset, PlotOrientation.VERTICAL, false, true, false);
		Color color=serie==velocitySerie?VELOCITY_COLOR:ALTITUDE_COLOR;
		styleLineChart(chart, color);
		return chart;
	}
	
	public JFreeChart buildChartPropellant() {
		pieDataset= new DefaultPieDataset();
		pieDataset.setValue("Remaining Propellant", 100);
		pieDataset.setValue("Consumed Propellant", 0);
		JFreeChart chart = ChartFactory.createPieChart("Propellant", pieDataset,true,true,false);
		stylePieChart(chart);
		return chart;
		
	}
	
	public void updatePropellantChartTitle(int stageId) {
		if(propellantChart==null) return;
		if(stageId>=0) {
			propellantChart.setTitle("Propellant of Stage " + (stageId+1));
		}else {
			propellantChart.setTitle("Propellant");
		}
	}
	
	
	public void reset() {
		velocitySerie.clear();
		altitudeSerie.clear();
		pieDataset.setValue("Remaining Propellant", 100);
		pieDataset.setValue("Consumed Propellant", 0);
		initialPropellant=-1;
		currentStageId=-1;
		updatePropellantChartTitle(-1);
	}
	
	public JFreeChart getVelocityChart() {
		return velocityChart;
	}

	public JFreeChart getAltitudeChart() {
		return altitudeChart;
	}

	public JFreeChart getPropellantChart() {
		return propellantChart;
	}
	
	private void styleLineChart(JFreeChart chart, Color seriesColor) {
		
		chart.setBackgroundPaint(SimulationConfig.PANEL_COLOR);
		XYPlot plot=chart.getXYPlot();
		plot.setBackgroundPaint(CHART_BACKGROUND);
		plot.setOutlinePaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(SimulationConfig.BUTTON_COLOR_BACK);  
	    plot.setDomainGridlinePaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.getDomainAxis().setLabelPaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.getRangeAxis().setLabelPaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.getDomainAxis().setTickLabelPaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.getRangeAxis().setTickLabelPaint(SimulationConfig.BUTTON_COLOR_BACK);
	}

	private void stylePieChart(JFreeChart chart) {
		
		chart.setBackgroundPaint(SimulationConfig.PANEL_COLOR);
		PiePlot plot=(PiePlot) chart.getPlot();
		plot.setBackgroundPaint(CHART_BACKGROUND);
		plot.setOutlinePaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.setLabelBackgroundPaint(SimulationConfig.TEXTFIELD_COLOR_BACK);
		plot.setLabelOutlinePaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.setLabelPaint(SimulationConfig.BUTTON_COLOR_BACK);
		plot.setSectionPaint("Remaining Propellant", REMAINING_COLOR);
		plot.setSectionPaint("Consumed Propellant", CONSUMED_COLOR);
		plot.setShadowPaint(null);
	}
	
}
