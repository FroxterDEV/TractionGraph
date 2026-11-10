package com.weirdfox.tractiongraph.Forms;

import com.weirdfox.tractiongraph.Forms.Components.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FrmMain extends JFrame {

    //Vars
    private final int HEIGHT = 480;
    private final int WIDTH = HEIGHT * (4/3);

    public MainToolbar mainToolbar = new MainToolbar(this);
    public GraphicsCanvas graphicsCanvas = new GraphicsCanvas(this);

    public FrmMain(){
        //Form settings
        setTitle("TractionGraph");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try{
            URL programIcon = getClass().getResource("/res/ProgramLogoIcon.png");
            setIconImage(ImageIO.read(programIcon));
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        getContentPane().setLayout(new BorderLayout());

        //Adding components
        add(mainToolbar, BorderLayout.NORTH); //Toolbar
        add(graphicsCanvas, BorderLayout.CENTER); //Graphic canvas
    }

    public void display(){
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
    }
}
