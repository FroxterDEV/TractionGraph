package com.weirdfox.tractiongraph.Logic;

import com.weirdfox.tractiongraph.Forms.Components.GraphicsCanvas;
import com.weirdfox.tractiongraph.Forms.FrmMain;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainLogic {
    //Variables
    private ChartPanel currentChartPanel = null;

    //Actions
    public void importTable(FrmMain parent, GraphicsCanvas target) {
        //Configure file chooser
        JFileChooser csvImporter = new JFileChooser();

        csvImporter.setAcceptAllFileFilterUsed(false);

        csvImporter.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()){
                    return true;
                }else{
                    return f.getName().toLowerCase().endsWith(".csv");
                }
            }

            @Override
            public String getDescription() {
                return "CSV Files (*.csv)";
            }
        });

        //Show selection dialog
        int result = csvImporter.showOpenDialog(parent);

        //Get file and process
        String filepath = "";

        if (result == JFileChooser.APPROVE_OPTION){
            filepath = csvImporter.getSelectedFile().getPath();

        }

        csvProcess(parent, filepath, target);
    }

    private void csvProcess(FrmMain parent, String filepath, GraphicsCanvas target){
        //Read file
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))){
            String line;

            double length = 0;
            double surface = 0;
            List<Double> enlargement  = new ArrayList<>();
            List<Double> strength = new ArrayList<>();
            //Getting length and surface
            try {
                length = Double.parseDouble(br.readLine().split(";")[1].replace(",","."));  //DO NOT DELETE THIS IN YOUR LIFE!!!
                surface = Double.parseDouble(br.readLine().split(";")[1].replace(",",".")); //DO NOT DELETE THIS IN YOUR LIFE!!!

                //Converting from mm to m
                length = length/1000;
                surface = surface/1_000_000.0;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            br.readLine();
            while ((line = br.readLine()) != null){
                try {
                    enlargement.add(Double.parseDouble(line.split(";")[0].replace(",",".")));
                    strength.add(Double.parseDouble(line.split(";")[1].replace(",",".")));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            //Create graphic
            if (!enlargement.isEmpty() && !strength.isEmpty()){
                int n = enlargement.size();

                //Getting Young modulus
                double sumEps = 0, sumSig = 0, sumEpsSq = 0, sumEpsSig = 0;
                int linePoints = Math.min(10, n);

                for (int i = 0; i < linePoints; i++) {
                    double strain = enlargement.get(i) / length;
                    double stress = (strength.get(i) * 1000.0) / (surface * 1_000_000.0);

                    sumEps += strain;
                    sumSig += stress;
                    sumEpsSq += (strain * strain);
                    sumEpsSig += (strain * stress);
                }

                double youngModulus = (linePoints * sumEpsSig - sumEps * sumSig) / (linePoints * sumEpsSq - sumEps * sumEps);

                //Processing enlargement and strength
                double[][] essayValues = new double[2][n];
                for(int i = 0; i < n; i++){
                    essayValues[0][i] = enlargement.get(i); //X Axis
                    essayValues[1][i] = strength.get(i);    //Y Axis
                }

                double xMaxLine = enlargement.get(linePoints);
                double yMaxLine = (youngModulus * (xMaxLine / length) * surface * 1_000_000.0) / 1000.0;

                double[][] youngLineData = {
                        {0.0, xMaxLine},
                        {0.0, yMaxLine}
                };

                DefaultXYDataset essayData = new DefaultXYDataset();
                essayData.addSeries("Traction Essays", essayValues);
                essayData.addSeries("Young modulus line (" + String.format("%.2f", youngModulus) + " MPa)", youngLineData);

                JFreeChart chart = ChartFactory.createXYLineChart("Curve effort-deform", "Enlargement", "Stength", essayData);

                XYPlot xyPlot = chart.getXYPlot();
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();

                float[] dashPattern = {6.0f, 4.0f};
                Stroke dashedStroke = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f);

                renderer.setSeriesStroke(1, dashedStroke);
                renderer.setSeriesPaint(1, Color.BLUE);

                currentChartPanel = new ChartPanel(chart);

                target.removeAll();
                target.setLayout(new java.awt.BorderLayout());
                target.add(currentChartPanel, java.awt.BorderLayout.CENTER);
                target.revalidate();
                target.repaint();
            }

        }catch (IOException e){
            JOptionPane.showMessageDialog(parent, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exportGraph(FrmMain parent) {
        if (currentChartPanel == null || currentChartPanel.getChart() == null) {
            JOptionPane.showMessageDialog(parent, "There is not graphic to export", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser pathChooser = new JFileChooser();
        pathChooser.setDialogTitle("Save chart as PNG");

        pathChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()){
                    return true;
                }else{
                    return f.getName().toLowerCase().endsWith(".png");
                }
            }

            @Override
            public String getDescription() {
                return "Portable Network Graphic Image (*.png)";
            }
        });

        int result = pathChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION){
            File fileToSave = pathChooser.getSelectedFile();

            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")){
                fileToSave = new File(filePath + ".png");
            }

            try {
                ChartUtils.saveChartAsPNG(fileToSave, currentChartPanel.getChart(), 1024, 768);
                JOptionPane.showMessageDialog(parent, "Image saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Unable to save image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}