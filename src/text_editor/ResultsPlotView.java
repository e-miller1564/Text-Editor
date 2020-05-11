package text_editor;

import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;

public class ResultsPlotView extends FlowPane {
	
	public Group resultsGroup;
	public NumberAxis xAxis;
	public NumberAxis yAxis;
	public LineChart lineChart;
	public XYChart.Series singleLoopSeries;
	public XYChart.Series tripleLoopSeries;
	
	public ResultsPlotView() {
		xAxis = new NumberAxis(0, 105, 5);
		xAxis.setLabel("% of Original File");
		
		yAxis = new NumberAxis();
		yAxis.setLabel("Running Time (ms)");
		
		lineChart = new LineChart(xAxis, yAxis);
		
		singleLoopSeries = new XYChart.Series();
		singleLoopSeries.setName("Single Loop Algorithm");
		
		tripleLoopSeries = new XYChart.Series();
		tripleLoopSeries.setName("Triple Loop Algorithm");
		
		lineChart.getData().addAll(singleLoopSeries, tripleLoopSeries);

		resultsGroup = new Group(lineChart);
		this.getChildren().add(resultsGroup);
	}
}
