package com.weirdfox.tractiongraph.Logic.IconProcess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class IconExtractor {
    private BufferedImage iconSheet;
    private int w, h;

    public IconExtractor(int w, int h){
        try {
            URL iconsUrl = getClass().getResource("/res/TBIcons.png");
            this.iconSheet = ImageIO.read(iconsUrl);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.w = w;
        this.h = h;
    }

    public BufferedImage getIcon(int col, int row){
        int x = col * w;
        int y = row * h;

        //Limits
        if (x + w > iconSheet.getWidth() || y + h > iconSheet.getHeight() || x < 0 || y < 0) {
            throw new IllegalArgumentException("Tile out of limits!");
        }

        return iconSheet.getSubimage(x, y, w, h);
    }
}
