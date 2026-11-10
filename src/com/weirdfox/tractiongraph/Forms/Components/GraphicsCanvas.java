package com.weirdfox.tractiongraph.Forms.Components;

import com.weirdfox.tractiongraph.Forms.FrmMain;
import com.weirdfox.tractiongraph.Logic.MainLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicsCanvas extends JPanel {
    private JLabel lblEmpty;

    public GraphicsCanvas(FrmMain frmMain) {
        setLayout(new BorderLayout());

        lblEmpty = new JLabel("<html><h1>Please import a table...</h1></html>");
        lblEmpty.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmpty.setVerticalAlignment(SwingConstants.CENTER);

        add(lblEmpty, BorderLayout.CENTER);
    }
}