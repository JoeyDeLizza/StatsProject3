// Joint Effort by Joe DeLizza and Joshua Distefano

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.math3.stat.StatUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class Plotter {
	
	
	public ArrayList<Double> grabYPointsFromFile() throws IOException {
        String line;
 
        FileReader fr=null;
        BufferedReader br;
        try
        {
            fr = new FileReader("SaltedPoints.csv");
        }
        catch (FileNotFoundException fe)
        {
            System.out.println("File not found");
        }
        br = new BufferedReader(fr);
        // read first line in Points
        line = br.readLine();
        // Loop to read entire file
        ArrayList<Double> yCords = new ArrayList<>();
        while (line != null) {
        	// split the line to store points in array
        		String[] str = line.split(",");
        		//System.out.println(str);
        		// get y cord
        		double yNum = Double.parseDouble(str[1]);
        		yCords.add(yNum);
        		line = br.readLine();
        }
        return yCords;
	}
	// Takes the array of salted Y points and for each point finds the average of the points in the window
	public double[] smoothYPoints(int windowSize) throws IOException {
		ArrayList<Double> yCords = this.grabYPointsFromFile();
		// Stores the yCords in a double array
		double[] yPoints = new double[yCords.size()];
		for(int i = 0; i < yPoints.length; i++) {
			yPoints[i] = yCords.get(i);
		}
		// Smooths each point
		double[] smoothedPoints = new double[yCords.size()];
		for(int i = 0; i < smoothedPoints.length-1; i++) {
			smoothedPoints[i] = StatUtils.mean(yPoints, i, windowSize);
			if(i+windowSize > smoothedPoints.length-1) {
				windowSize--;
			}
		}
		return smoothedPoints;
	}
	
	public double[] betterSmoothYPoints(int windowSize) throws IOException {
		ArrayList<Double> yCords = this.grabYPointsFromFile();
		// Stores the yCords in a double array
		double[] yPoints = new double[yCords.size()];
		for(int i = 0; i < yPoints.length; i++) {
			yPoints[i] = yCords.get(i);
		}
		// Smooths each point
		double[] smoothedPoints = new double[yCords.size()];
		for(int i = 0; i < smoothedPoints.length-1; i++) {
			int tmpWinSize = windowSize;
			int rightWindow = windowSize;
			if(i==0) {
				smoothedPoints[i] = yPoints[i];
				continue;
			}
			if(i < windowSize) {
				rightWindow = windowSize - i;
				windowSize = i;
				
			}
			smoothedPoints[i] = (StatUtils.mean(yPoints, i, rightWindow) + meanLeftOfIndex(yPoints, i-windowSize, windowSize))/2;
			if(i+windowSize > smoothedPoints.length-1) {
				windowSize--;
				tmpWinSize=windowSize;
			}
			windowSize = tmpWinSize;
		}
		return smoothedPoints;
	}
	
	private double meanLeftOfIndex(double[] yP, int i, int windowSize) {
		return StatUtils.mean(yP, i, windowSize);
	}
	
	public void test(int windowSize) throws IOException {
		 Graph chart = new Graph(
		         "Salted and Smoothed Graphs" ,
		         "Salted y=x and Smoothed y=x", windowSize);

		      chart.pack( );
		      //RefineryUtilities.centerFrameOnScreen( chart );
		      chart.setVisible( true );
	}
	

	}
// Inline class for displaying the data in a chart
	 class Graph extends ApplicationFrame {
		 // plot data
		 Plotter plot = new Plotter();
		 // Creates LineChart using data from ploter
	 Graph( String applicationTitle , String chartTitle, int windowSize) throws IOException {
	super(applicationTitle);
	JFreeChart lineChart = ChartFactory.createLineChart(
	   chartTitle,
	   "X","Y",
	   createDataset(windowSize),
	   PlotOrientation.VERTICAL,
	   true,true,false);
	   
	ChartPanel chartPanel = new ChartPanel( lineChart );
	chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	setContentPane( chartPanel );
	}
	
	 // Creates DataSet from the plot data for JFreeChart to interpret
	private DefaultCategoryDataset createDataset(int windowSize) throws IOException {
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	// Add salted points to data set
	ArrayList<Double> yCords = plot.grabYPointsFromFile();
	for(int i = 0; i < yCords.size()-1; i++) {
		dataset.addValue(yCords.get(i), "SaltedPoints", ""+i);
	}
	// Add smoothed points to data set
	double[] smoothYPoints = plot.betterSmoothYPoints(windowSize).clone();
	for(int i = 0; i < smoothYPoints.length-1; i++) {
		dataset.addValue(smoothYPoints[i], "SmoothedPoints", ""+i);
		if(i+windowSize > smoothYPoints.length) {
			windowSize--;
		}
	}
	

	return dataset;
	
	
	}
}
