package WS18aufgabenblatt09;


/**
 * MIT License
 *
 * Copyright (c) 2017 Arnulph Fuhrmann (TH Koeln)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Draw version 0.17 
 */
public class Draw {
    
    public static char CHAR_UNDEFINED = KeyEvent.CHAR_UNDEFINED;

    private static Color       backGroundColor = new Color(228, 228, 228);
    private static Color       defaultColor = new Color(0, 0, 0);

    private static boolean     useDoubleBuffering = false;
    private static boolean     useAntiAliasing = true;
    //private static boolean   useXorMode = false;
    
    private static boolean     mouseButton1Pressed = false;
    
    private static SwingCanvas drawingCanvas;

    @SuppressWarnings("serial")
    private static class SwingCanvas extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

        private int     width, height;

        private boolean showFPS = false;
        private int     fps = 60;        
        private double  fpsLastTime = System.nanoTime();        
        private LinkedList<Double> fpsGraph = new LinkedList<Double>();
        private Color   fpsColor = new Color(0, 0, 0);

        private BufferedImage frontBuffer, backBuffer;
        private Graphics frontBufferGraphics, backBufferGraphics;
        
        private int mouseX, mouseY;

        private boolean hideMouseCursor = false;

        private boolean backBufferReady;

        private double fpsDiff;

        private boolean fullScreen = false;

        public SwingCanvas(int width, int height) {
            this.width = width;
            this.height = height;

            frontBuffer = createBuffer(width, height);
            backBuffer = createBuffer(width, height);
            
            // Create and set up the window.
            JFrame frame = new JFrame("Mini Draw");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(this);

            // Exit on escape
            String key = "ESCAPE";
            KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(key);
            Action escapeAction = new AbstractAction() {

                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };
            frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, key);
            frame.getRootPane().getActionMap().put(key, escapeAction);

            // Listeners
            frame.addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            
            setDoubleBuffered(true);
                        
            if(fullScreen) {
                frame.setUndecorated(true);
                frame.setIgnoreRepaint(true);
            }
            
            // Display the window.
            frame.pack();
            frame.setVisible(true);
            
            // center frame
            frame.setLocationRelativeTo(null);
            
            frontBufferGraphics = frontBuffer.getGraphics();            
            backBufferGraphics = backBuffer.getGraphics();
            initFrontBuffer();
        }

        private void initFrontBuffer() {
            frontBufferGraphics.setColor(backBufferGraphics.getColor());            
            frontBufferPixels = ((DataBufferInt) frontBuffer.getRaster().getDataBuffer()).getData();
            frontBufferWidth = frontBuffer.getWidth();
        }

        private BufferedImage createBuffer(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        private void measureFPS() {
            double time = System.nanoTime();
            fpsDiff = time - fpsLastTime;
            fpsLastTime = time;
        }
        
        private void renderFPS() {
            
            if(showFPS) {
                Graphics g = getFronfBufferGraphics();
                double fps = (double)((int)(1.0 / (fpsDiff / 1.0E9 ) * 10.0)) / 10;
                Color color = g.getColor();
                g.setColor(fpsColor);
                fpsGraph.add(fps);
                
                text(10,20, ""+fps);
                
                if(fpsGraph.size() > width) {
                    fpsGraph.removeFirst();
                }
                int x = 0;
                for(double value : fpsGraph) {
                    g.drawLine(x, height-1, x, height-(int)value);
                    x++;
                }                
                g.setColor(color);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            synchronized(keyMonitor)
            {
                keyMonitor.notifyAll();
                lastPressedKey = e.getKeyCode();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            synchronized(keyMonitor)
            {
                if(lastPressedKey == e.getKeyCode())
                {
                    lastPressedKey = -1;                    
                }
                lastTypedKey = e.getKeyCode();
                lastTypedKeyChar = e.getKeyChar();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                mouseButton1Pressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                mouseButton1Pressed = false;                
            }        
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(hideMouseCursor) {
                drawingCanvas.setCursor(drawingCanvas.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), 
                                                                                      new Point(0, 0), "null"));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        public Graphics getFronfBufferGraphics() {
            return frontBufferGraphics;
        }

    
        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
        
        public void swapBuffers() {           
            if(useDoubleBuffering) {
                synchronized(drawingCanvas) {
                    backBufferReady = true;

                    BufferedImage tmp = backBuffer;
                    backBuffer = frontBuffer;
                    frontBuffer = tmp;

                    Graphics gTmp = backBufferGraphics;
                    backBufferGraphics = frontBufferGraphics;
                    frontBufferGraphics = gTmp;
                    
                    initFrontBuffer();
                }
            }
        }
        
        int[] frontBufferPixels;
        int frontBufferWidth;
        
        
        @Override
        protected void paintComponent(Graphics g) {
            if(fullScreen || drawingCanvas == null ) return;                
            synchronized(drawingCanvas)
            {                
                if(useDoubleBuffering) {
                    if(backBufferReady) {
                        g.drawImage(backBuffer, 0, 0, null);
                        measureFPS();
                        backBufferReady = false;
                    }
                }
                else {
                    g.drawImage(frontBuffer, 0, 0, null);
                }
            }
        }

    }
    
    public static void setFpsColor(int r, int g, int b) {
        drawingCanvas.fpsColor = new Color(r, g, b);
    }
    
    public static void clearScreen() {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        Color color = g.getColor();
        g.setColor(backGroundColor);
        g.fillRect(0, 0, drawingCanvas.width, drawingCanvas.height);
        g.setColor(color);
    }

    private static double lastTime = System.nanoTime();
    
    public static void syncToFrameRate() {
        
        if(drawingCanvas.showFPS) drawingCanvas.renderFPS();
            
        syncWithLongSleep(drawingCanvas.fps, 4);
        lastTime = System.nanoTime();
    
        drawingCanvas.swapBuffers();                    
        drawingCanvas.repaint();
    }
    
    /**
     * Checks how many nanoseconds are left until the next frame must be displayed.
     * Then the thread is set to sleep. Since the Java sleep mechanism has a large 
     * variance the thread is awakened burnMillis earlier. The remaining time is spent
     * by active waiting which causes a precise frame time at the cost of some lost CPU time.   
     * @param fps the targeted frames per second 
     * @param burnMillis the number of milliseconds which are spent in active waiting.
     */
    private static void syncWithLongSleep(int fps, int burnMillis) {

        boolean sleepOnce = true;
        while(true) {
            
            double currentTime = System.nanoTime();
            double deltaTime = currentTime - lastTime;
            double nanosPerFrame = 1d/fps * 1E9; 
            
            if( deltaTime > nanosPerFrame)
                return;
            
            if(sleepOnce) {
                
                sleepOnce = false;                
                int sleepTime = (int)((nanosPerFrame - deltaTime) / 1E6) - burnMillis;                
                if(sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }        
    }
    
    public static void init(int width, int height) {
        if (drawingCanvas == null) {
            drawingCanvas = new SwingCanvas(width, height);
            clearScreen();
            enableAntiAliasing(useAntiAliasing);            
        }
        else {
            synchronized (drawingCanvas) {
                drawingCanvas.width = width;
                drawingCanvas.height = height;
            }
        }
    }

    public static void setFps(int fps) {
        drawingCanvas.fps = fps;
    }
    
    public static void setColor(int red, int green, int blue) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.setColor(new Color(red, green, blue));                
    }
    
    public static void setColor(int red, int green, int blue, int alpha) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.setColor(new Color(red, green, blue, alpha));
    }
    
    public static void setBackgroundColor(int red, int green, int blue) {
        backGroundColor = new Color(red, green, blue);
    }
    
    /**
     * Draws a filled rectangular shape.
     * @param x x-coordinate of the upper left corner. 
     * @param y y-coordinate of the upper left corner.
     * @param width width of the rectangle.
     * @param height height of the rectangle.
     */
    public static void filledRect(int x, int y, int width, int height) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.fillRect(x, y, width, height);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void filledEllipse(int x, int y, int width, int height) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.fillOval(x, y, width, height);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void ellipse(int x, int y, int width, int height) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.drawOval(x, y, width, height);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void polyLine(int[] xCoords, int[] yCoords) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.drawPolyline(xCoords, yCoords, xCoords.length);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void polygon(int[] xCoords, int[] yCoords) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.drawPolygon(xCoords, yCoords, xCoords.length);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void filledPolygon(int[] xCoords, int[] yCoords) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.fillPolygon(xCoords, yCoords, xCoords.length);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }

    /**
     * Draws a rectangular shape.
     * @param x x-coordinate of the upper left corner. 
     * @param y y-coordinate of the upper left corner.
     * @param width width of the rectangle.
     * @param height height of the rectangle.
     */
    public static void rect(int x, int y, int width, int height) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.drawRect(x, y, width, height);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void line(int x1, int y1, int x2, int y2) {
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        g.drawLine(x1, y1, x2, y2);
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
        
    public static void text(int x, int y, String text) {
        text(x, y, text, 12);        
    }
    
    public static void text(int x, int y, char text) {
        text(x, y, ""+text);       
    }
    
    public static void text(int x, int y, String text, int size) {
        text(x, y, text, size, -1);        
    }
    
    public static void text(int x, int y, String text, int size, int width) {        
        Graphics g = drawingCanvas.getFronfBufferGraphics();
        
        //if(useXorMode) 
            //g.setXORMode(new Color( 0xff ^ color.getRed(), 0xff ^ color.getGreen(),  0xff ^ color.getBlue()));
        
        Font currentFont = g.getFont();
        g.setFont( new Font(currentFont.getFontName(), currentFont.getStyle(), size) );
        
        if(width > 0) {
            int stringWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, x + (width - stringWidth)/2, y);
        }
        else {
            g.drawString(text, x, y);
        }
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void setPixel(int x, int y) {
        // drawingCanvas.frontBuffer.setRGB() is much slower due to checks for color space conversion      
        drawingCanvas.frontBufferPixels[y*drawingCanvas.frontBufferWidth + x] = drawingCanvas.getFronfBufferGraphics().getColor().getRGB();
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void setPixel(int x, int y, int col) {
        drawingCanvas.frontBufferPixels[y*drawingCanvas.frontBufferWidth + x] = col;
        if(!useDoubleBuffering) drawingCanvas.repaint();
    }
    
    public static void setPixelUsingAlpha(int x, int y, int col) {
        int index = y*drawingCanvas.frontBufferWidth + x;
        int currentCol = drawingCanvas.frontBufferPixels[index];
        drawingCanvas.frontBufferPixels[index] = applyAlphaBlending(currentCol, col);
    }

    public static void setPixelUsingAlpha(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize)  {

        int[] frontBufferPixel = ((DataBufferInt) drawingCanvas.frontBuffer.getRaster().getDataBuffer()).getData();
        int width = drawingCanvas.frontBuffer.getWidth();
        int yOff  = offset;
        for (int y = startY; y < startY+h; y++, yOff+=scanSize) {
            
            int off = yOff;
            for (int x = startX; x < startX+w; x++) {
                
                int newColor = rgbArray[off++];
                int oldColor = frontBufferPixel[y*width + x];
                frontBufferPixel[y*width + x] = applyAlphaBlending(oldColor, newColor);
            }
        }
    }
    
    private static int applyAlphaBlending(int oldColor, int newColor) {
        
        int alpha = newColor>>>24;
    
        // early outs for two important and frequent cases:
        if(alpha == 255) {
            return newColor;
        }
        else if(alpha == 0) {
            return oldColor;
        }

        // ok, now we have to do the actual blending
        int r_old = oldColor >> 16 & 0xff;
        int g_old = oldColor >> 8  & 0xff;
        int b_old = oldColor       & 0xff;
        
        long r = newColor >> 16 & 0xff;
        long g = newColor >> 8  & 0xff;
        long b = newColor       & 0xff;

        int oneMinusAlpha = 255 - alpha;        
        r = r * alpha + r_old * oneMinusAlpha;
        g = g * alpha + g_old * oneMinusAlpha;
        b = b * alpha + b_old * oneMinusAlpha;
        
        r = r / 255;
        g = g / 255;
        b = b / 255;
        
        return (int)(0xff000000 | (r << 16) | (g << 8) | ( b ));
    }
    
    
    public static void setPixel(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize)  {

        int[] frontBufferPixel = drawingCanvas.frontBufferPixels;
        int width = drawingCanvas.frontBuffer.getWidth();
        int yOff  = offset;
        for (int y = startY; y < startY+h; y++, yOff+=scanSize) {
            
            int off = yOff;
            for (int x = startX; x < startX+w; x++) {
                frontBufferPixel[y*width + x] = rgbArray[off++];
            }
        }
    }
    
    public static int getPixel(int x, int y) {
        return drawingCanvas.frontBuffer.getRGB(x, y);
    }

    /**
     * When enabled the shapes are drawn into the frontBuffer and a call to syncToFrameRate 
     * will swap the buffers. Otherwise all shapes are directly rendered into the frontBuffer
     * and a call to syncToFrameRate is not necessary. 
     */
    public static void enableDoubleBuffering(boolean enabled) {
        synchronized (drawingCanvas) {
            useDoubleBuffering = enabled;           
        }        
    }
    
    public static void enableAntiAliasing(boolean enabled) {
        synchronized (drawingCanvas) {
            useAntiAliasing = enabled;
            Object doAA = useAntiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF; 
            ((Graphics2D)drawingCanvas.frontBufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, doAA);
            ((Graphics2D)drawingCanvas.backBufferGraphics ).setRenderingHint(RenderingHints.KEY_ANTIALIASING, doAA);
        }
    }
    
    public static void enableShowFPS(boolean enabled) {
        synchronized (drawingCanvas) {
            drawingCanvas.showFPS = enabled;
        }        
    }
    
    public static void hideMouseCursor(boolean hidden) {
        drawingCanvas.hideMouseCursor = hidden;
    }
    
    private static int lastPressedKey = -1;
    private static int lastTypedKey = -1;
    private static char lastTypedKeyChar = KeyEvent.CHAR_UNDEFINED;
    
    private static Boolean keyMonitor = false;
    
    public static int waitForKeyboard() {   
        drawingCanvas.repaint();
        
        synchronized(keyMonitor) {
            try {
                keyMonitor.wait();                
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
        return lastPressedKey;
    }
    
    public static int getLastPressedKey() {
        return lastPressedKey;
    }
    
    public static int getLastTypedKeyCode() {
        int tmp = lastTypedKey;
        lastTypedKey = -1;
        return tmp;
    }
    
    public static char getLastTypedKeyChar() {
        char tmp = lastTypedKeyChar;
        lastTypedKeyChar = KeyEvent.CHAR_UNDEFINED;
        return tmp;
    }
    
    public static int getMouseX() {
        return drawingCanvas.mouseX;
    }

    public static int getMouseY() {
        return drawingCanvas.mouseY;
    }
    
    public static boolean isMouseButton1Pressed() {
        return mouseButton1Pressed;
    }
    
    // ------------------------------------------------------------------------------------
    // - UI element button                                                                -
    // - ----------------------------------------------------------------------------------
    private static int buttonWidth = 100;
    private static int buttonHeight = 25;
    
    public static void setButtonWidth(int width) {
        buttonWidth = width;
    }
    
    public static void setButtonHeight(int height) {
        buttonWidth = height;
    }
    
    /**
     * Draws a button with default width and height.
     * 
     * @param name centered label of the button
     * @param x x-coordinate of upper left corner
     * @param y y-coordinate of upper left corner
     * @return true, if the button is pressed
     */
    public static boolean button(String name, int x, int y) {
        
        int width  = buttonWidth;
        int height = buttonHeight;
        int mouseX = drawingCanvas.mouseX;
        int mouseY = drawingCanvas.mouseY;
        
        boolean doHighlight = false;
        boolean pressed     = false;
        
        if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y+height) {        
            doHighlight = true;            
            if(mouseButton1Pressed) {
                pressed = true;
            }
        }

        if(doHighlight && !pressed) {
            setColor(150, 150, 255);
        }
        else if(pressed) {
            setColor(120, 120, 255);
        }            
        else {
            setColor(190, 190, 190);
        }
        filledRect(x, y, width, height);
        
        setColor(30, 30, 30);
        text(x, y + 17, name, 12, buttonWidth);
        
        return pressed;
    }
    
    private static boolean sliderDragged = false;
    
    public static int slider(int x, int y, int value) {
        
        int width  = buttonWidth;
        int height = buttonHeight;
        int mouseX = drawingCanvas.mouseX;
        int mouseY = drawingCanvas.mouseY;
        
        boolean doHighlight = false;
        boolean pressed     = false;
        
        if(sliderDragged) {
            if(mouseButton1Pressed) {
                pressed = true;
            }
            else {
                sliderDragged = false;
            }
        }
        
        if(mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y+height) {        
            doHighlight = true;            
            if(mouseButton1Pressed) {
                pressed = true;
                sliderDragged = true;
            }
        }
        
        if(doHighlight && !pressed) {
            setColor(150, 150, 255);
        }
        else if(pressed) {
            setColor(120, 120, 255);            
        }            
        else {
            setColor(190, 190, 190);
        }
        filledRect(x, y, width, height);
        
        int sliderPos;
        
        if(pressed) {
            if(mouseX < x) mouseX = x;
            if(mouseX > x+width) mouseX = x+ width;
            sliderPos = mouseX - x;
            value = sliderPos/10;            
        } 
        else {
            sliderPos = 10*value;
        }
        setColor(60, 60, 160);
        filledRect(x + sliderPos - 3, y + 2, 7, buttonHeight-4);
        return value;
    }
    
    public static int[][] loadImage(String fileName) {
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
        int image[][] = new int[img.getHeight()][img.getWidth()];
        
        for(int y=0; y < image.length; y++) {
            for(int x=0; x < image[0].length; x++) {
                image[y][x] = img.getRGB(x, y);
            }
        }
        return image;        
    }
    
    public static Clip loadSound(final String fileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
            clip.open(audioInputStream);
            {
                // dummy calls to avoid lag when played for the first time
                clip.setFramePosition(clip.getFrameLength() - 1);
                clip.start();
            }
            return clip;
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("File: " + fileName);
            e.printStackTrace();
        }
        return null;
    }
    
    public static int[] hsvToRgb(float hue, float saturation, float v) {
        
        float r=0, g=0, b=0;        
        
        float huePrime = hue / 60.0f;
        int   integerPart = (int)huePrime;
        float fractionalPart = huePrime - integerPart;
        
        float tmp = - v * saturation;
        float a0 = tmp;
        float a1 = tmp * fractionalPart;
        float a2 = tmp * (1 - fractionalPart);
        
        switch(integerPart) {
            case 0:
                        g = a2; b = a0;
                break;
            case 1:
                r = a1;         b = a0;
                break;
            case 2:
                r = a0;         b = a2;
                break;
            case 3:
                r = a0; g = a1; 
                break;
            case 4:
                r = a2; g = a0; 
                break;
            case 5:
                        g = a0; b = a1;
                break;
            default:
                System.out.println("Hue out of range. Hue = " + hue);
                break;
        }
        
        int[] colors = new int[3];        
        colors[0] = (int)(255*(r + v) + 0.5);
        colors[1] = (int)(255*(g + v) + 0.5);
        colors[2] = (int)(255*(b + v) + 0.5);
        return colors;
    }

    /**
     * Unpacks the parameter color in the format ARGB into an array of integers 
     * containing r, g, b. Each returned value will be in the range [0,255].
     */
    public static int[] intToRgb(int color) {

        int[] rgb = new int[3];
        rgb[0] = color >> 16 & 0xff;
        rgb[1] = color >> 8  & 0xff;
        rgb[2] = color       & 0xff;
        return rgb;
    }    

    /**
     * Packs the parameters r, g, b into a single integer in the format ARGB.
     * Each parameter may be in the range [0,255].
     */
    public static int rgbToInt(int r, int g, int b) {
        return (int)(0xff000000 | (r << 16) | (g << 8) | ( b ));
    }

    /**
     * Unpacks the parameter color in the format ARGB into an array of integers 
     * containing r, g, b, a. Each returned value will be in the range [0,255].
     */
    public static int[] intToRgba(int color) {

        int[] rgba = new int[4];        
        rgba[0] = color >>  16 & 0xff;
        rgba[1] = color >>   8 & 0xff;
        rgba[2] = color        & 0xff;
        rgba[3] = color >>> 24;        
        return rgba;
    }    
        
    /**
     * Packs the parameters r, g, b, a into a single integer in the format ARGB.
     * Each parameter may be in the range [0,255].
     */
    public static int rgbaToInt(int r, int g, int b, int a) {        
        return (int)( a << 24 | (r << 16) | (g << 8) | ( b ));
    }


}