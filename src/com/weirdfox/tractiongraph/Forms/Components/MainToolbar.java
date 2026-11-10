package com.weirdfox.tractiongraph.Forms.Components;

import com.weirdfox.tractiongraph.Forms.FrmAbout;
import com.weirdfox.tractiongraph.Forms.FrmMain;
import com.weirdfox.tractiongraph.Logic.IconProcess.IconExtractor;
import com.weirdfox.tractiongraph.Logic.MainLogic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainToolbar extends JToolBar {
    //Importing useful scripts
    MainLogic mainLogic = new MainLogic();
    IconExtractor iconExtractor = new IconExtractor(32, 32);

    public MainToolbar(FrmMain parent) {
        //Toolbar settings
        setFloatable(false);

        /* Creating toolbar buttons */

        //Import button
        JButton btnImport = new JButton();
        btnImport.setToolTipText("Import a CSV table");
        btnImport.setIcon(new ImageIcon(iconExtractor.getIcon(0,0)));
        btnImport.addActionListener(e -> { mainLogic.importTable(parent, parent.graphicsCanvas); });

        //Save button
        JButton btnSave = new JButton();
        btnSave.setToolTipText("Export graph as PNG image");
        btnSave.setIcon(new ImageIcon(iconExtractor.getIcon(1,0)));
        btnSave.addActionListener(e -> { mainLogic.exportGraph(parent); });

        //About button
        JButton btnAbout = new JButton();
        btnAbout.setToolTipText("Shows program's details");
        btnAbout.setIcon(new ImageIcon(iconExtractor.getIcon(2,0)));
        btnAbout.addActionListener(e -> { new FrmAbout(parent).display(); });

        //Adding buttons to toolbar
        add(btnImport);
        add(btnSave);
        addSeparator();
        add(btnAbout);
    }
}
