package com.weirdfox.tractiongraph;

import com.weirdfox.tractiongraph.Forms.*;
import org.jfree.data.general.Dataset;

import javax.swing.*;

public class Init {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            //Setting looknfeel
            try{
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            }catch (UnsupportedLookAndFeelException |
                    ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException e){
                e.printStackTrace();
            }

            //Call the main form to display
            new FrmMain().display();
        });

    }
}
