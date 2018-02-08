package org.dezzPro.app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class MainFrame {

    public static int     WIDTH;
    public static int     HEIGHT;
    public static boolean isCreated;

    private JFrame         frame;
    private Canvas         content;
    private BufferedImage  bufferedImage;
    private BufferStrategy bufferStrategy;
    private int[]          bufferedData;
    private Graphics2D     graphics2D;
    private String         title;

    public MainFrame(int width, int height, String title)
    {
        if (isCreated) {
            return;
        }

        WIDTH = width;
        HEIGHT = height;
        isCreated = true;

        this.title = title;
        this.frame = new JFrame(this.title);
        this.content = new Canvas();
        this.content.setSize(new Dimension(WIDTH, HEIGHT));

        this.frame.setResizable(false);
        this.frame.getContentPane().add(this.content);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        this.bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.bufferedData = ((DataBufferInt) this.bufferedImage.getRaster().getDataBuffer()).getData();
        this.graphics2D = (Graphics2D) this.bufferedImage.getGraphics();

        this.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.content.createBufferStrategy(3);
        this.bufferStrategy = this.content.getBufferStrategy();
    }

    public void swapBuffer()
    {
        this.bufferStrategy.getDrawGraphics().drawImage(this.bufferedImage, 0, 0, null);
        this.bufferStrategy.show();
    }

    public void setTitle(String title)
    {
        this.frame.setTitle(this.title + title);
    }

    public void clearFrame()
    {
        Arrays.fill(this.bufferedData, 0xff000000);
    }

    public String getTitle()
    {
        return title;
    }

    public Graphics2D getGraphics2D()
    {
        return graphics2D;
    }

    public int[] getBufferedData()
    {
        return bufferedData;
    }

    public BufferStrategy getBufferStrategy()
    {
        return bufferStrategy;
    }

    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }

    public Canvas getContent()
    {
        return content;
    }

    public JFrame getFrame()
    {
        return frame;
    }
}
