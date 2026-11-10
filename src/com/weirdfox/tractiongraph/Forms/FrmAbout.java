package com.weirdfox.tractiongraph.Forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.URL;

public class FrmAbout extends JDialog {
    public FrmAbout(FrmMain parent){
        //Form settings
        setTitle("About TractionGraph");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(new Dimension(320, 320));
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        try{
            URL programLogo = getClass().getResource("/res/ProgramLogoIcon.png");
            setIconImage(ImageIO.read(programLogo));
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        //Setting layout
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        /* Component creation */

        //Components
        URL programLogo = getClass().getResource("/res/ProgramLogo.png");
        JLabel imgProgramIcon = new JLabel(new ImageIcon(programLogo));
        imgProgramIcon.setBorder(BorderFactory.createLoweredBevelBorder());

        String htmlDescription = "<html>" +
                "<h1>TractionGraph</h1>" +
                "<h4>By Froxter Bergec</h4>" +
                "<br>" +
                "<p>This is a school project that my teacher recommended to my class to do, I wanted to better try with Java because I feel better here than in Python :)</p>" +
                "</html>";
        JLabel lblInfo = new JLabel(htmlDescription);

        //Adding components
        gbc.gridx = 0; gbc.weightx = 0.3;
        getContentPane().add(imgProgramIcon, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        getContentPane().add(lblInfo, gbc);

    }

    public void display(){
        setVisible(true);
    }
}
