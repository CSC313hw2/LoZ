import java.awt.*;
import java.util.Vector;
import java.util.Random;
import java.time.LocalTime;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Zelda {
    public Zelda() {
        setup();
    }

    public static void setup() {
        appFrame = new JFrame("The Legend of Zelda: Link's Awakening");
        XOFFSET = 0;
        YOFFSET = 40;
        WINWIDTH = 338;
        WINHEIGHT = 271;
        pi = 3.14159265358979;
        quarterPi = 0.25 * pi;
        halfPi = 0.5 * pi;
        threequartersPi = 0.75 * pi;
        fivequartersPi = 1.25 * pi;
        threehalvesPi = 1.5 * pi;
        sevenquartersPi = 1.75 * pi;
        twoPi = 2.0 * pi;
        endgame = false;
        p1width = 20; //18.5;
        p1height = 20; //25;
        p1originalX = (double) XOFFSET + ((double) WINWIDTH / 2.0) - (p1width / 2.0) - 20;
        p1originalY = (double) YOFFSET + ((double) WINHEIGHT / 2.0) - (p1height / 2.0) + 10; // + 10
        level = 3;
        audiolifetime = new Long(78000); // 78 seconds for KI.wav
        dropLifeLifetime = new Long(1000); // 1 second
        try {
            // setting up the Koholint Island images
            xdimKI = 16;
            ydimKI = 16;
            backgroundKI = new Vector<>();
            for (int i = 0; i < ydimKI; i++) {
                Vector<BufferedImage> temp = new Vector<>();
                for (int j = 0; j < xdimKI; j++) {
                    BufferedImage tempImg = ImageIO.read(new File("blank.png"));
                    temp.addElement(tempImg);
                }
                backgroundKI.addElement(temp);
            }
            for (int i = 0; i < backgroundKI.size(); i++) {
                for (int j = 0; j < backgroundKI.elementAt(i).size(); j++) {
                    // TODO swap j and i
                    if ((j == 5 && i == 10) || (j == 5 && i == 11)
                            || (j == 6 && i == 10) || (j == 6 && i == 11)
                            || (j == 7 && i == 10) || (j == 7 && i == 11)
                            || (j == 8 && i == 9) || (j == 8 && i == 10)) {
                        String filename = "KI";
                        if (j < 10) {
                            filename = filename + "0";
                        }
                        filename = filename + j;
                        if (i < 10) {
                            filename = filename + "0";
                        }
                        filename = filename + i + ".png";
                        //System.out.println( filename );
                        backgroundKI.elementAt(i).set(j, ImageIO.read(new File(filename)));
                    }
                }
            }

            // setting up the Koholint Island walls
            wallsKI = new Vector<>();
            for (int i = 0; i < ydimKI; i++) {
                Vector<Vector<ImageObject>> temp = new Vector<>();
                for (int j = 0; j < xdimKI; j++) {
                    Vector<ImageObject> tempWalls = new Vector<>();
                    temp.addElement(tempWalls);
                }
                wallsKI.add(temp);
            }

            for (int i = 0; i < wallsKI.size(); i++) {
                for (int j = 0; j < wallsKI.elementAt(i).size(); j++) {
                    if (i == 8 && j == 9) { //TODO: change i and j if grid images are renamed
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(65, 0, 100, 175, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(165, 0, 35, 120, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(200, 0, 100, 175, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 65, 320, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(300, 0, 30, 320, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(65, 265, 100, 40, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(200, 265, 100, 40, 0.0));
                    }
                    if (i == 8 && j == 10) {
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 35, 320, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 35, 172, 35, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(210, 35, 125, 35, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 265, 340, 40, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(207, 70, 32, 70, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(107, 103, 103, 70, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(107, 169, 30, 70, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(139, 173, 36, 40, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(240, 167, 100, 65, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(240, 233, 36, 40, 0.0));
                    }
                    if (i == 7 && j == 10) {
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(203, 133, 130, 73, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(203, 143, 30, 135, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 33, 32, 35, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 167, 130, 70, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 265, 340, 40, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(238, 0, 100, 70, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(130, 98, 38, 45, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(101, 0, 30, 240, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(325, 70, 15, 72, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(325, 206, 15, 70, 0.0));
                    }
                    if (i == 7 && j == 11) {
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 340, 108, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 108, 32, 204, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(102, 133, 32, 180, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(170, 108, 30, 130, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(237, 133, 30, 180, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(237, 268, 100, 44, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(304, 167, 32, 73, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(325, 108, 30, 130, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(325, 240, 30, 37, 0.0));
                    }
                    if (i == 6 && j == 11) {
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 340, 108, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 168, 32, 73, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 267, 340, 50, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(70, 132, 30, 42, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(270, 132, 30, 42, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(70, 200, 30, 42, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(270, 200, 30, 42, 0.0));
                        wallsKI.elementAt(i).elementAt(j).addElement(
                                new ImageObject(170, 107, 32, 170, 0.0));
                    }
                }
            }
            // setting up the Tail Cave images
            xdimTC = 9;//7; // TODO: need to be able to just use 7 and 6, not 9 and 8.
            ydimTC = 8;//6;
            backgroundTC = new Vector<>();
            for (int i = 0; i < ydimTC; i++) {
                Vector<BufferedImage> temp = new Vector<>();
                for (int j = 0; j < xdimTC; j++) {
                    BufferedImage tempImg = ImageIO.read(new File("blank.png"));
                    temp.addElement(tempImg);
                }
                backgroundTC.addElement(temp);
            }
            for (int i = 0; i < backgroundTC.size(); i++) {
                for (int j = 0; j < backgroundTC.elementAt(i).size(); j++) {
                    if ((j == 0 && i == 2) || (j == 0 && i == 3) || (j == 0 && i == 4)
                            || (j == 1 && i == 1) || (j == 1 && i == 3) || (j == 1 && i == 5)
                            || (j == 2 && i == 1) || (j == 2 && i == 2) || (j == 2 && i == 3)
                            || (j == 2 && i == 4) || (j == 2 && i == 5) || (j == 2 && i == 6)
                            || (j == 3 && i == 1) || (j == 3 && i == 2) || (j == 3 && i == 3)
                            || (j == 3 && i == 4) || (j == 3 && i == 5) || (j == 4 && i == 2)
                            || (j == 4 && i == 3) || (j == 4 && i == 4) || (j == 5 && i == 2)
                            || (j == 5 && i == 3) || (j == 6 && i == 0) || (j == 6 && i == 1)
                            || (j == 6 && i == 2) || (j == 6 && i == 3)) {
                        String filename = "TC";
                        if (j < 10) {
                            filename = filename + "0";
                        }
                        filename = filename + j;
                        if (i < 10) {
                            filename = filename + "0";
                        }
                        filename = filename + i + ".png";
                        //System.out.println( filename );
                        backgroundTC.elementAt(i).set(j, ImageIO.read(new File(filename)));
                    }
                }
            }

            // setting up the Tail Cave walls
            wallsTC = new Vector<>();
            for (int i = 0; i < ydimTC; i++) {
                Vector<Vector<ImageObject>> temp = new Vector<>();
                for (int j = 0; j < xdimTC; j++) {
                    Vector<ImageObject> tempWalls = new Vector<>();
                    temp.addElement(tempWalls);
                }
                wallsTC.add(temp);
            }

            for (int i = 0; i < wallsTC.size(); i++) {
                for (int j = 0; j < wallsTC.elementAt(i).size(); j++) {
                    if (i == 3 && j == 5) { //TODO: change i and j if grid images are renamed
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 340, 72, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 268, 120, 45, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(216, 268, 120, 45, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 40, 30, 100, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(302, 40, 35, 100, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 200, 30, 100, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(302, 200, 35, 100, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(35, 75, 30, 30, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(35, 230, 30, 30, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(270, 75, 30, 30, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(270, 230, 30, 30, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(120, 250, 30, 45, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(187, 250, 28, 45, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 130, 4, 70, 0.0));
                    }
                    if (i == 3 && j == 4) {
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 100, 140, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(100, 0, 40, 70, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(140, 0, 60, 140, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(200, 0, 40, 70, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(240, 0, 100, 140, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 200, 100, 100, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(100, 265, 140, 30, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(240, 200, 100, 100, 0.0));
                    }
                    if (i == 3 && j == 3) {
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 340, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 137, 110, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(204, 0, 135, 110, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 110, 35, 200, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(305, 110, 35, 79, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(305, 219, 35, 190, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 267, 153, 35, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(186, 267, 153, 35, 0.0));
                    }
                    if (i == 3 && j == 2) {
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 153, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(187, 0, 140, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 35, 140, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(305, 0, 35, 310, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 200, 35, 110, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 265, 340, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(37, 230, 33, 35, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(271, 230, 33, 35, 0.0));
                    }
                    if (i == 2 && j == 6) {
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 0, 251, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(285, 0, 45, 75, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 75, 100, 35, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 110, 33, 80, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 218, 33, 80, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(0, 268, 118, 35, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(150, 268, 170, 35, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(235, 167, 90, 130, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(302, 0, 35, 360, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(118, 292, 42, 5, 0.0));
                        wallsTC.elementAt(i).elementAt(j).addElement(
                                new ImageObject(250, 0, 35, 45, 0.0));
                    }
                }
            }


            player = ImageIO.read(new File("link00.png"));
            // Link's images
            link = new Vector<>();
            for (int i = 0; i < 72; i++) {
                if (i < 10) {
                    String filename = "link0" + i + ".png";
                    link.addElement(ImageIO.read(new File(filename)));
                } else {
                    String filename = "link" + i + ".png";
                    link.addElement(ImageIO.read(new File(filename)));
                }
            }
            //rupee images
            rupees = new Vector<>();
            rupee = new Vector<>();
            rupee.addElement(ImageIO.read(new File("rupee.png")));
            // BluePig Enemy's images
            bluepigEnemies = new Vector<>();
            bluepigEnemy = new Vector<>();
            bluepigEnemy.addElement(ImageIO.read(new File("BPB1.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPB2.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPF1.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPF2.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPL1.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPL2.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPR1.png")));
            bluepigEnemy.addElement(ImageIO.read(new File("BPR2.png")));
            // BubbleBoss Enemies
            bubblebossEnemies = new Vector<>();
            // Health images
            leftHeartOutline = ImageIO.read(new File("heartOutlineLeft.png"));
            rightHeartOutline = ImageIO.read(new File("heartOutlineRight.png"));
            leftHeart = ImageIO.read(new File("heartLeft.png"));
            rightHeart = ImageIO.read(new File("heartRight.png"));
        } catch (IOException ioe) {
        }
    }

    private static class Animate implements Runnable {
        public void run() {
            while (endgame == false) {
                backgroundDraw();
                //testDraw();
                enemiesDraw();
                rupeesDraw();
                playerDraw();
                healthDraw();
                rupeeCount();
                try {
                    Thread.sleep(32);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    //for debugging
    private static void testDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.green);
        //wall
        g2D.fillRect(0, 0, 340, 72);
        g2D.fillRect(0, 278, 120, 45);
        g2D.fillRect(216, 278, 120, 45);
        g2D.fillRect(0, 40, 30, 100);
        g2D.fillRect(302, 40, 35, 100);
        g2D.fillRect(0, 200, 30, 100);
        g2D.fillRect(302, 200, 35, 100);

    }

    private static class AudioLooper implements Runnable {
        public void run() {
            while (endgame == false) {
                Long curTime = new Long(System.currentTimeMillis());
                if (curTime - lastAudioStart > audiolifetime) {
                    playAudio(backgroundState);
                }
            }
        }
    }

    private static void playAudio(String backgroundState) {
        try {
            clip.stop();
        } catch (Exception e) {
            // NOP
        }
        try {
            if (backgroundState.substring(0, 2).equals("KI")) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File("KI.wav").getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                lastAudioStart = System.currentTimeMillis();
                audiolifetime = new Long(78000);
            } else if (backgroundState.substring(0, 2).equals("TC")) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File("TC.wav").getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                lastAudioStart = System.currentTimeMillis();
                audiolifetime = new Long(191000);
            }
        } catch (Exception e) {
            // NOP
        }
    }

    private static String bgWrap(String input, int wrap) {
        String ret = input;
        if (wrap == 0) {
            // NOP
        } else if (wrap == 1) { //right
            int xcoord = Integer.parseInt(input.substring(2, 4));
            int ycoord = Integer.parseInt(input.substring(4, 6));
            xcoord = xcoord + 1;
            if (xcoord < 10) {
                ret = input.substring(0, 2) + "0" + xcoord;
            } else {
                ret = input.substring(0, 2) + xcoord;
            }
            if (ycoord < 10) {
                ret = ret + "0" + ycoord;
            } else {
                ret = ret + ycoord;
            }
        } else if (wrap == 2) { //left
            int xcoord = Integer.parseInt(input.substring(2, 4));
            int ycoord = Integer.parseInt(input.substring(4, 6));
            xcoord = xcoord - 1;
            if (xcoord < 10) {
                ret = input.substring(0, 2) + "0" + xcoord;
            } else {
                ret = input.substring(0, 2) + xcoord;
            }
            if (ycoord < 10) {
                ret = ret + "0" + ycoord;
            } else {
                ret = ret + ycoord;
            }
        } else if (wrap == 3) { // down
            int xcoord = Integer.parseInt(input.substring(2, 4));
            int ycoord = Integer.parseInt(input.substring(4, 6));
            ycoord = ycoord + 1;
            if (xcoord < 10) {
                ret = input.substring(0, 2) + "0" + xcoord;
            } else {
                ret = input.substring(0, 2) + xcoord;
            }
            if (ycoord < 10) {
                ret = ret + "0" + ycoord;
            } else {
                ret = ret + ycoord;
            }
        } else if (wrap == 4) { // up
            int xcoord = Integer.parseInt(input.substring(2, 4));
            int ycoord = Integer.parseInt(input.substring(4, 6));
            ycoord = ycoord - 1;
            if (xcoord < 10) {
                ret = input.substring(0, 2) + "0" + xcoord;
            } else {
                ret = input.substring(0, 2) + xcoord;
            }
            if (ycoord < 10) {
                ret = ret + "0" + ycoord;
            } else {
                ret = ret + ycoord;
            }
        }
        return ret;
    }

    private static class PlayerMover implements Runnable {
        public PlayerMover() {
            velocitystep = 3;
        }

        public void run() {
            while (endgame == false) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                if (upPressed || downPressed || leftPressed || rightPressed) {
                    p1velocity = velocitystep;
                    if (upPressed) {
                        if (leftPressed) {
                            p1.setInternalAngle(fivequartersPi);
                        } else if (rightPressed) {
                            p1.setInternalAngle(5.49779);
                        } else {
                            p1.setInternalAngle(threehalvesPi);
                        }
                    }
                    if (downPressed) {
                        if (leftPressed) {
                            p1.setInternalAngle(2.35619);
                        } else if (rightPressed) {
                            p1.setInternalAngle(quarterPi);
                        } else {
                            p1.setInternalAngle(halfPi);
                        }
                    }
                    if (leftPressed) {
                        if (upPressed) {
                            p1.setInternalAngle(fivequartersPi);
                        } else if (downPressed) {
                            p1.setInternalAngle(threequartersPi);
                        } else {
                            p1.setInternalAngle(pi);
                        }
                    }
                    if (rightPressed) {
                        if (upPressed) {
                            p1.setInternalAngle(5.49779);
                        } else if (downPressed) {
                            p1.setInternalAngle(quarterPi);
                        } else {
                            p1.setInternalAngle(0.0);
                        }
                    }
                } else {
                    p1velocity = 0.0;
                    p1.setInternalAngle(threehalvesPi);
                }
                p1.updateBounce();
                p1.move(p1velocity * Math.cos(p1.getInternalAngle()), p1velocity * Math.sin(p1.getInternalAngle()));
                int wrap = p1.screenWrap(XOFFSET, XOFFSET + WINWIDTH, YOFFSET,
                        YOFFSET + WINHEIGHT);
                //backgroundState = bgWrap(backgroundState, wrap);
                if (backgroundState.substring(0, 2).equals("KI")) {
                    changeWorldBackground(wrap);
                }
                if (backgroundState.substring(0, 2).equals("TC")) {
                    changeDungeonBackground(wrap);
                }
                if (wrap != 0) {
                    clearEnemies();
                    clearRupees();
                    generateEnemies(backgroundState);
                    generateRupees(backgroundState);
                }
            }
        }
        private double velocitystep;
    }

    private static void changeWorldBackground(int wrap) {
        if (wrap == 0) {
            //NOP
        } else if (backgroundState.substring(0, 6).equals("KI0809")) {
            if (wrap == 3) { //down
                p1.moveto(185, 70);
                backgroundState = "KI0810";
            }
        } else if (backgroundState.substring(0, 6).equals("KI0810")) {
            if (wrap == 1) { //right
                p1.moveto(15, p1.getY());
                backgroundState = "KI0710";
            } else if (wrap == 4) { //up
                p1.moveto(175, 280);
                backgroundState = "KI0809";
            }
        } else if (backgroundState.substring(0, 6).equals("KI0710")) {
            if (wrap == 2) { //left
                p1.moveto(325, p1.getY());
                backgroundState = "KI0810";
            } else if (wrap == 4) { //up
                p1.moveto(p1.getX(), 295);
                backgroundState = "KI0711";
            }
        } else if (backgroundState.substring(0, 6).equals("KI0711")) {
            if (wrap == 3) { //down
                p1.moveto(p1.getX(), 60);
                backgroundState = "KI0710";
            }
        } else if (backgroundState.substring(0, 6).equals("KI0611")) {
            if (wrap == 2) { //left
                p1.move(325, p1.getY());
                backgroundState = "KI0711";
            }
        }
    }

    private static void changeDungeonBackground(int wrap) {
        if (wrap == 0) {
            //NOP
        } else if (backgroundState.substring(0, 6).equals("TC0305")) {
            if (wrap == 1) { //right
                p1.moveto(15, p1.getY());
                backgroundState = "TC0304";
            }
        } else if (backgroundState.substring(0, 6).equals("TC0304")) {
            if (wrap == 1) { //right
                p1.moveto(15, p1.getY());
                backgroundState = "TC0302";
            } else if (wrap == 2) { //left
                p1.moveto(320, p1.getY());
                backgroundState = "TC0305";
            }
        } else if (backgroundState.substring(0, 6).equals("TC0303")) {
            if (wrap == 1) { //right
                p1.moveto(15, p1.getY());
                backgroundState = "TC0206";
            } else if (wrap == 3) { //down
                p1.moveto(p1.getX(), 65);
                backgroundState = "TC0302";
            }
        } else if (backgroundState.substring(0, 6).equals("TC0302")) {
            if (wrap == 2) { //left
                p1.moveto(320, p1.getY());
                backgroundState = "TC0304";
            } else if (wrap == 4) { //up
                p1.moveto(p1.getX(), 285);
                backgroundState = "TC0303";
            }
        } else if (backgroundState.substring(0, 6).equals("TC0206")) {
            if (wrap == 2) { //left
                p1.moveto(310, p1.getY());
                backgroundState = "TC0303";
            }
        }
    }

    private static void clearEnemies() {
        bluepigEnemies.clear();
        bubblebossEnemies.clear();
    }

    private static void clearRupees() {
        rupees.clear();
    }

    private static void generateEnemies(String backgroundState) {
        if (backgroundState.substring(0, 6).equals("KI0809")) {
            bluepigEnemies.addElement(new ImageObject(80, 190, 33, 33, 0.0));
            bluepigEnemies.addElement(new ImageObject(250, 230, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0810")) {
            bluepigEnemies.addElement(new ImageObject(250, 100, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0710")) {
            bluepigEnemies.addElement(new ImageObject(250, 100, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0711")) {
            bluepigEnemies.addElement(new ImageObject(50, 130, 33, 33, 0.0));
        }
        for (int i = 0; i < bluepigEnemies.size(); i++) {
            bluepigEnemies.elementAt(i).setMaxFrames(25);
        }
    }

    private static void generateRupees(String backgroundState) {
        if (backgroundState.substring(0, 6).equals("KI0809")) {
            rupees.addElement(new ImageObject(80, 190, 33, 33, 0.0));
            rupees.addElement(new ImageObject(250, 230, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0810")) {
            rupees.addElement(new ImageObject(250, 100, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0710")) {
            rupees.addElement(new ImageObject(250, 100, 33, 33, 0.0));
        } else if (backgroundState.substring(0, 6).equals("KI0711")) {
            rupees.addElement(new ImageObject(50, 130, 33, 33, 0.0));
        }
        for (int i = 0; i < rupees.size(); i++) {
            rupees.elementAt(i).setMaxFrames(25);
        }
    }

    private static class EnemyMover implements Runnable {
        public EnemyMover() {
            bluepigvelocitystep = 2;
        }

        public void run() {
            Random randomNumbers = new Random(LocalTime.now().getNano());
            while (endgame == false) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // NOP;
                }
                // TODO
                try {
                    for (int i = 0; i < bluepigEnemies.size(); i++) {
                        int state = randomNumbers.nextInt(1000);
                        if (state < 5) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(0);
                        } else if (state < 10) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(halfPi);
                        } else if (state < 15) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(pi);
                        } else if (state < 20) {
                            bluepigvelocity = bluepigvelocitystep;
                            bluepigEnemies.elementAt(i).setInternalAngle(threehalvesPi);
                        } else if (state < 250) {
                            bluepigvelocity = bluepigvelocitystep;
                        } else {
                            bluepigvelocity = 0;
                        }
                        bluepigEnemies.elementAt(i).updateBounce();
                        bluepigEnemies.elementAt(i).move(bluepigvelocity * Math.cos(bluepigEnemies.elementAt(i).getInternalAngle()),
                                bluepigvelocity * Math.sin(bluepigEnemies.elementAt(i).getInternalAngle()));
                    }
                    for (int i = 0; i < bubblebossEnemies.size(); i++) {
                    }
                } catch (java.lang.NullPointerException jlnpe) {
                    // NOP
                }
            }
        }

        private double bluepigvelocitystep;
        private double bluepigvelocity;
    }

    private static class HealthTracker implements Runnable {
        public void run() {
            while (endgame == false) {
                Long curTime = new Long(System.currentTimeMillis());
                if (availableToDropLife && p1.getDropLife() > 0) {
                    int newLife = p1.getLife() - p1.getDropLife();
                    p1.setDropLife(0);
                    availableToDropLife = false;
                    lastDropLife = System.currentTimeMillis();
                    p1.setLife(newLife);

                    try {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("hurt.wav").getAbsoluteFile());
                        Clip hurtclip = AudioSystem.getClip();
                        hurtclip.open(ais);
                        hurtclip.start();
                    } catch (Exception e) {
                    }
                } else {
                    if (curTime - lastDropLife > dropLifeLifetime) {
                        availableToDropLife = true;
                    }
                }
            }
        }
    }

    private static class CollisionChecker implements Runnable {
        public void run() {
            //Random randomNumbers = new Random( LocalTime.now().getNano() );
            while (endgame == false) {
                // check player against doors in given scenes
                if (backgroundState.substring(0, 6).equals("KI0809")) {
                    if (collisionOccurs(p1, doorKItoTC)) {
                        p1.moveto(156, 265);
                        backgroundState = "TC0305";
                        clip.stop();
                        clearEnemies();
                        clearRupees();
                        playAudio(backgroundState);
                        generateEnemies(backgroundState);
                        generateRupees(backgroundState);
                    }
                } else if (backgroundState.substring(0, 6).equals("TC0305")) {
                    if (collisionOccurs(p1, doorTCtoKI)) {
                        p1.moveto(175, 155);
                        backgroundState = "KI0809";
                        clip.stop();
                        clearEnemies();
                        clearRupees();
                        playAudio(backgroundState);
                        generateEnemies(backgroundState);
                        generateRupees(backgroundState);
                    }
                }

                // check player and enemies against walls in overworld
                //TODO: change i and j if grid images are renamed
                if (backgroundState.substring(0, 6).equals("KI0809")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(8).elementAt(9));
                } else if (backgroundState.substring(0, 6).equals("KI0810")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(8).elementAt(10));
                } else if (backgroundState.substring(0, 6).equals("KI0710")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(7).elementAt(10));
                } else if (backgroundState.substring(0, 6).equals("KI0711")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(7).elementAt(11));
                } else if (backgroundState.substring(0, 6).equals("KI0611")) {
                    checkMoversAgainstWalls(wallsKI.elementAt(6).elementAt(11));
                }

                // check player and enemies against walls in dungeon
                if (backgroundState.substring(0, 6).equals("TC0305")) {
                    checkMoversAgainstWalls(wallsTC.elementAt(3).elementAt(5));
                } else if (backgroundState.substring(0, 6).equals("TC0304")) {
                    checkMoversAgainstWalls(wallsTC.elementAt(3).elementAt(4));
                } else if (backgroundState.substring(0, 6).equals("TC0303")) {
                    checkMoversAgainstWalls(wallsTC.elementAt(3).elementAt(3));
                } else if (backgroundState.substring(0, 6).equals("TC0302")) {
                    checkMoversAgainstWalls(wallsTC.elementAt(3).elementAt(2));
                } else if (backgroundState.substring(0, 6).equals("TC0206")) {
                    checkMoversAgainstWalls(wallsTC.elementAt(2).elementAt(6));
                }

                // check player against enemies
                for (int i = 0; i < bluepigEnemies.size(); i++) {
                    if (collisionOccurs(p1, bluepigEnemies.elementAt(i))) {
                        //System.out.println( "Still Colliding: " + i + ", " + System.currentTimeMillis() );
                        p1.setBounce(true);
                        bluepigEnemies.elementAt(i).setBounce(true);
                        if (availableToDropLife) {
                            p1.setDropLife(1);
                        }
                    }
                }

                // check player against rupees
                for (int i = 0; i < rupees.size(); i++) {
                    if (collisionOccurs(p1, rupees.elementAt(i))) {
                        //System.out.println( "Still Colliding: " + i + ", " + System.currentTimeMillis() );
                        moneyCount += 5;
                        rupees.remove(i);
                    }
                }

            }
        }

        private static void checkMoversAgainstWalls(Vector<ImageObject> wallsInput) {
            for (int i = 0; i < wallsInput.size(); i++) {
                if (collisionOccurs(p1, wallsInput.elementAt(i))) {
                    p1.setBounce(true);
                }
                for (int j = 0; j < bluepigEnemies.size(); j++) {
                    if (collisionOccurs(bluepigEnemies.elementAt(j), wallsInput.elementAt(i))) {
                        bluepigEnemies.elementAt(j).setBounce(true);
                    }
                }
            }
        }
    }

    // TODO make one lockrotate function which takes as input objInner, objOuter, and point relative to objInner's x,y that objOuter must rotate around.
    // dist is a distance between the two objects at the bottom of objInner.
    private static void lockrotateObjAroundObjbottom(ImageObject objOuter, ImageObject objInner, double dist) {
        objOuter.moveto(objInner.getX() + (dist + objInner.getWidth() / 2.0)
                        * Math.cos(-objInner.getAngle() + pi / 2.0) + objOuter.getWidth() / 2.0,
                objInner.getY() + (dist + objInner.getHeight() / 2.0)
                        * Math.sin(-objInner.getAngle() + pi / 2.0) + objOuter.getHeight() / 2.0);
        objOuter.setAngle(objInner.getAngle());
    }

    // dist is a distance between the two objects at the top of the inner object.
    private static void lockrotateObjAroundObjtop(ImageObject objOuter, ImageObject objInner, double dist) {
        objOuter.moveto(objInner.getX() + objOuter.getWidth() + (objInner.getWidth() / 2.0 + (dist + objInner.getWidth() / 2.0)
                        * Math.cos(objInner.getAngle() + pi / 2.0)) / 2.0,
                objInner.getY() - objOuter.getHeight() + (dist + objInner.getHeight() / 2.0)
                        * Math.sin(objInner.getAngle() / 2.0));
        objOuter.setAngle(objInner.getAngle());
    }

    private static AffineTransformOp rotateImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getAngle(), obj.getWidth() / 2.0, obj.getHeight() / 2.0);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }

    private static AffineTransformOp spinImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getInternalAngle(),
                obj.getWidth() / 2.0, obj.getHeight() / 2.0);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }

    private static void backgroundDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        if (backgroundState.substring(0, 2).equals("KI")) {
            //KI0809
            //System.out.println("KI");
            int i = Integer.parseInt(backgroundState.substring(4, 6));
            int j = Integer.parseInt(backgroundState.substring(2, 4));
            if (i < backgroundKI.size()) {
                if (j < backgroundKI.elementAt(i).size()) {
                    g2D.drawImage(backgroundKI.elementAt(i).elementAt(j), XOFFSET, YOFFSET, null);
                }
            }
            //g2D.drawImage(backgroundKI);
        }
        if (backgroundState.substring(0, 2).equals("TC")) {
            int i = Integer.parseInt(backgroundState.substring(4, 6));
            int j = Integer.parseInt(backgroundState.substring(2, 4));
            if (i < backgroundTC.size()) {
                if (j < backgroundTC.elementAt(i).size()) {
                    g2D.drawImage(backgroundTC.elementAt(i).elementAt(j), XOFFSET, YOFFSET, null);
                }
            }
        }
    }

    private static void playerDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        if (upPressed || downPressed || leftPressed || rightPressed) {
            if (upPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(4), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.getCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(5), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (downPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(2), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.getCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(3), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (leftPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(0), null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.getCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(1), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
            if (rightPressed == true) {
                if (p1.getCurrentFrame() == 0) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(6), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                } else if (p1.getCurrentFrame() == 1) {
                    g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(7), null),
                            (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
                }
                p1.updateCurrentFrame();
            }
        } else {
            if (Math.abs(lastPressed - 90.0) < 1.0) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(4), null),
                        (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 270.0) < 1.0) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(2), null),
                        (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 0.0) < 1.0) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(6), null),
                        (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
            if (Math.abs(lastPressed - 180.0) < 1.0) {
                g2D.drawImage(rotateImageObject(p1).filter(link.elementAt(0), null),
                        (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
            }
        }
        //g2D.drawImage( rotateImageObject( p1 ).filter(player, null), (int)(p1.getX() + 0.5), (int)(p1.getY() + 0.5), null );
    }

    private static void healthDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        int leftscale = 10;
        int leftoffset = 10;
        int rightoffset = 9;
        int interioroffset = 2;
        int halfinterioroffset = 1;
        for (int i = 0; i < p1.getMaxLife(); i++) {
            if (i % 2 == 0) {
                g2D.drawImage(rotateImageObject(p1).filter(leftHeartOutline, null), leftscale * i + leftoffset + XOFFSET, YOFFSET, null);
            } else {
                g2D.drawImage(rotateImageObject(p1).filter(rightHeartOutline, null),
                        leftscale * i + rightoffset + XOFFSET, YOFFSET, null);
            }
        }
        for (int i = 0; i < p1.getLife(); i++) {
            if (i % 2 == 0) {
                g2D.drawImage(rotateImageObject(p1).filter(leftHeart, null),
                        leftscale * i + leftoffset + interioroffset + XOFFSET,
                        interioroffset + YOFFSET, null);
            } else {
                g2D.drawImage(rotateImageObject(p1).filter(rightHeart, null),
                        leftscale * i + leftoffset - halfinterioroffset + XOFFSET,
                        interioroffset + YOFFSET, null);
            }
        }
    }

    private static void rupeeCount() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(g.getFont().deriveFont(25f));
        g2D.setColor(Color.GREEN);
        g2D.drawImage(rotateImageObject(p1).filter(rupee.elementAt(0), null), XOFFSET + 10, YOFFSET + 15, null);
        g2D.drawString(": " + moneyCount, XOFFSET + 20, YOFFSET + 45);
    }
    private static void rupeesDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        for (int i = 0; i < rupees.size(); i++) {
            g2D.drawImage(rotateImageObject(rupees.elementAt(i)).filter(rupee.elementAt(0), null), (int) (rupees.elementAt(i).getX() + 0.5), (int) (rupees.elementAt(i).getY() + 0.5), null);
        }
    }

    private static void enemiesDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < bluepigEnemies.size(); i++) {
            if (Math.abs(bluepigEnemies.elementAt(i).getInternalAngle() - 0.0) < 1.0) {
                if (bluepigEnemies.elementAt(i).getCurrentFrame() < bluepigEnemies.elementAt(i).getMaxFrames() / 2) {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(
                            bluepigEnemy.elementAt(6), null), (int) (bluepigEnemies.elementAt(i).getX() + 0.5),
                            (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                } else {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(
                            bluepigEnemy.elementAt(7), null), (int) (bluepigEnemies.elementAt(i).getX() + 0.5),
                            (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if (Math.abs(bluepigEnemies.elementAt(i).getInternalAngle() - pi) < 1.0) {
                if (bluepigEnemies.elementAt(i).getCurrentFrame() < bluepigEnemies.elementAt(i).getMaxFrames() / 2) {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(4), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                } else {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(5), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if (Math.abs(bluepigEnemies.elementAt(i).getInternalAngle() - halfPi) < 1.0) {
                if (bluepigEnemies.elementAt(i).getCurrentFrame() < bluepigEnemies.elementAt(i).getMaxFrames() / 2) {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(2), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                } else {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(3), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
            if (Math.abs(bluepigEnemies.elementAt(i).getInternalAngle() - threehalvesPi) < 1.0) {
                if (bluepigEnemies.elementAt(i).getCurrentFrame() < bluepigEnemies.elementAt(i).getMaxFrames() / 2) {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(0), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                } else {
                    g2D.drawImage(rotateImageObject(bluepigEnemies.elementAt(i)).filter(bluepigEnemy.elementAt(1), null),
                            (int) (bluepigEnemies.elementAt(i).getX() + 0.5), (int) (bluepigEnemies.elementAt(i).getY() + 0.5), null);
                }
                bluepigEnemies.elementAt(i).updateCurrentFrame();
            }
        }
    }

    private static class KeyPressed extends AbstractAction {
        public KeyPressed() {
            action = "";
        }

        public KeyPressed(String input) {
            action = input;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals("UP")) {
                upPressed = true;
                lastPressed = 90.0;
            }
            if (action.equals("DOWN")) {
                downPressed = true;
                lastPressed = 270.0;
            }
            if (action.equals("LEFT")) {
                leftPressed = true;
                lastPressed = 180.0;
            }
            if (action.equals("RIGHT")) {
                rightPressed = true;
                lastPressed = 0.0;
            }
            if (action.equals("A")) {
                aPressed = true;
            }
            if (action.equals("X")) {
                xPressed = true;
            }
        }

        private String action;
    }

    private static class KeyReleased extends AbstractAction {
        public KeyReleased() {
            action = "";
        }

        public KeyReleased(String input) {
            action = input;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals("UP")) {
                upPressed = false;
            }
            if (action.equals("DOWN")) {
                downPressed = false;
            }
            if (action.equals("LEFT")) {
                leftPressed = false;
            }
            if (action.equals("RIGHT")) {
                rightPressed = false;
            }
            if (action.equals("A")) {
                aPressed = false;
            }
            if (action.equals("X")) {
                xPressed = false;
            }
        }

        private String action;
    }

    private static class QuitGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
        }
    }

    private static class StartGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
            aPressed = false;
            xPressed = false;
            lastPressed = 90.0;
            backgroundState = "KI0809"; //"KI0809"
            availableToDropLife = true;
            try {
                clearEnemies();
                generateEnemies(backgroundState);
                clearRupees();
                generateRupees(backgroundState);
            } catch (java.lang.NullPointerException jlnpe) {
            }
            p1 = new ImageObject(p1originalX, p1originalY, p1width, p1height, 0.0);
            p1velocity = 0.0;
            p1.setInternalAngle(threehalvesPi); // 270 degrees, in radians
            p1.setMaxFrames(2);
            p1.setlastposx(p1originalX);
            p1.setlastposy(p1originalY);
            p1.setLife(6);
            p1.setMaxLife(6);
            doorKItoTC = new ImageObject(165, 120, 35, 20, 0.0);
            doorTCtoKI = new ImageObject(150, 300, 35, 20, 0.0);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            lastAudioStart = System.currentTimeMillis();
            playAudio(backgroundState);
            endgame = false;
            lastDropLife = System.currentTimeMillis();
            Thread t1 = new Thread(new Animate());
            Thread t2 = new Thread(new PlayerMover());
            Thread t3 = new Thread(new CollisionChecker());
            Thread t4 = new Thread(new AudioLooper());
            Thread t5 = new Thread(new EnemyMover());
            Thread t6 = new Thread(new HealthTracker());
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
        }
    }

    private static class GameLevel implements ActionListener {
        public int decodeLevel(String input) {
            int ret = 3;
            if (input.equals("One")) {
                ret = 1;
            } else if (input.equals("Two")) {
                ret = 2;
            } else if (input.equals("Three")) {
                ret = 3;
            } else if (input.equals("Four")) {
                ret = 4;
            } else if (input.equals("Five")) {
                ret = 5;
            } else if (input.equals("Six")) {
                ret = 6;
            } else if (input.equals("Seven")) {
                ret = 7;
            } else if (input.equals("Eight")) {
                ret = 8;
            } else if (input.equals("Nine")) {
                ret = 9;
            } else if (input.equals("Ten")) {
                ret = 10;
            }
            return ret;
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            String textLevel = (String) cb.getSelectedItem();
            level = decodeLevel(textLevel);
        }
    }

    private static Boolean isInside(double p1x, double p1y, double p2x1, double p2y1, double p2x2, double p2y2) {
        Boolean ret = false;
        if (p1x > p2x1 && p1x < p2x2) {
            if (p1y > p2y1 && p1y < p2y2) {
                ret = true;
            }
            if (p1y > p2y2 && p1y < p2y1) {
                ret = true;
            }
        }
        if (p1x > p2x2 && p1x < p2x1) {
            if (p1y > p2y1 && p1y < p2y2) {
                ret = true;
            }
            if (p1y > p2y2 && p1y < p2y1) {
                ret = true;
            }
        }
        return ret;
    }

    private static Boolean collisionOccursCoordinates(double p1x1, double p1y1, double p1x2, double p1y2,
                                                      double p2x1, double p2y1, double p2x2, double p2y2) {
        Boolean ret = false;
        if (isInside(p1x1, p1y1, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x1, p1y2, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x2, p1y1, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p1x2, p1y2, p2x1, p2y1, p2x2, p2y2) == true) {
            ret = true;
        }
        if (isInside(p2x1, p2y1, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x1, p2y2, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x2, p2y1, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        if (isInside(p2x2, p2y2, p1x1, p1y1, p1x2, p1y2) == true) {
            ret = true;
        }
        return ret;
    }

    private static Boolean collisionOccurs(ImageObject obj1, ImageObject obj2) {
        Boolean ret = false;
        if (collisionOccursCoordinates(obj1.getX(), obj1.getY(), obj1.getX() + obj1.getWidth(),
                obj1.getY() + obj1.getHeight(), obj2.getX(), obj2.getY(), obj2.getX() + obj2.getWidth(),
                obj2.getY() + obj2.getHeight()) == true) {
            ret = true;
        }
        return ret;
    }

    private static class ImageObject {
        public ImageObject() {
            maxFrames = 1;
            currentFrame = 0;
            bounce = false;
            life = 1;
            maxLife = 1;
            dropLife = 0;
        }

        public ImageObject(double xinput, double yinput, double xwidthinput, double yheightinput, double angleinput) {
            this();
            x = xinput;
            y = yinput;
            lastposx = x;
            lastposy = y;
            xwidth = xwidthinput;
            yheight = yheightinput;
            angle = angleinput;
            internalangle = 0.0;
            coords = new Vector<Double>();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getlastposx() {
            return lastposx;
        }

        public double getlastposy() {
            return lastposy;
        }

        public void setlastposx(double input) {
            lastposx = input;
        }

        public void setlastposy(double input) {
            lastposy = input;
        }

        public double getWidth() {
            return xwidth;
        }

        public double getHeight() {
            return yheight;
        }

        public double getAngle() {
            return angle;
        }

        public double getInternalAngle() {
            return internalangle;
        }

        public void setAngle(double angleinput) {
            angle = angleinput;
        }

        public void setInternalAngle(double internalangleinput) {
            internalangle = internalangleinput;
        }

        public Vector<Double> getCoords() {
            return coords;
        }

        public void setCoords(Vector<Double> coordsinput) {
            coords = coordsinput;
            generateTriangles();
            //printTriangles();
        }

        public int getMaxFrames() {
            return maxFrames;
        }

        public void setMaxFrames(int input) {
            maxFrames = input;
        }

        public int getCurrentFrame() {
            return currentFrame;
        }

        public void setCurrentFrame(int input) {
            currentFrame = input;
        }

        public Boolean getBounce() {
            return bounce;
        }

        public void setBounce(Boolean input) {
            bounce = input;
        }

        public int getLife() {
            return life;
        }

        public void setLife(int input) {
            life = input;
        }

        public int getMaxLife() {
            return maxLife;
        }

        public void setMaxLife(int input) {
            maxLife = input;
        }

        public int getDropLife() {
            return dropLife;
        }

        public void setDropLife(int input) {
            dropLife = input;
        }

        public void updateBounce() {
            if (getBounce()) {
                moveto(getlastposx(), getlastposy());
            } else {
                setlastposx(getX());
                setlastposy(getY());
            }
            setBounce(false);
        }

        public void updateCurrentFrame() {
            currentFrame = (currentFrame + 1) % maxFrames;
        }

        public void generateTriangles() {
            triangles = new Vector<Double>();
            // format: (0, 1), (2, 3), (4, 5) is the (x, y) coords of a triangle.
            // get center point of all coordinates.
            comX = getComX();
            comY = getComY();
            for (int i = 0; i < coords.size(); i = i + 2) {
                triangles.addElement(coords.elementAt(i));
                triangles.addElement(coords.elementAt(i + 1));
                triangles.addElement(coords.elementAt((i + 2) % coords.size()));
                triangles.addElement(coords.elementAt((i + 3) % coords.size()));
                triangles.addElement(comX);
                triangles.addElement(comY);
            }
        }

        public void printTriangles() {
            for (int i = 0; i < triangles.size(); i = i + 6) {
                System.out.print("p0x: " + triangles.elementAt(i) + ", p0y: " + triangles.elementAt(i + 1));
                System.out.print(" p1x: " + triangles.elementAt(i + 2) + ", p1y: " + triangles.elementAt(i + 3));
                System.out.println(" p2x: " + triangles.elementAt(i + 4) + ", p2y: " + triangles.elementAt(i + 5));
            }
        }

        public double getComX() {
            double ret = 0;
            if (coords.size() > 0) {
                for (int i = 0; i < coords.size(); i = i + 2) {
                    ret = ret + coords.elementAt(i);
                }
                ret = ret / (coords.size() / 2.0);
            }
            return ret;
        }

        public double getComY() {
            double ret = 0;
            if (coords.size() > 0) {
                for (int i = 1; i < coords.size(); i = i + 2) {
                    ret = ret + coords.elementAt(i);
                }
                ret = ret / (coords.size() / 2.0);
            }
            return ret;
        }

        public void move(double xinput, double yinput) {
            x = x + xinput;
            y = y + yinput;
        }

        public void moveto(double xinput, double yinput) {
            x = xinput;
            y = yinput;
        }

        public int screenWrap(double leftEdge, double rightEdge, double topEdge, double bottomEdge) {
            int ret = 0;
            if (x > rightEdge) {
                moveto(leftEdge, getY());
                ret = 1;
            }
            if (x < leftEdge) {
                moveto(rightEdge, getY());
                ret = 2;
            }
            if (y > bottomEdge) {
                moveto(getX(), topEdge);
                ret = 3;
            }
            if (y < topEdge) {
                moveto(getX(), bottomEdge);
                ret = 4;
            }
            return ret;
        }

        public void rotate(double angleinput) {
            angle = angle + angleinput;
            while (angle > twoPi) {
                angle = angle - twoPi;
            }
            while (angle < 0) {
                angle = angle + twoPi;
            }
        }

        public void spin(double internalangleinput) {
            internalangle = internalangle + internalangleinput;
            while (internalangle > twoPi) {
                internalangle = internalangle - twoPi;
            }
            while (internalangle < 0) {
                internalangle = internalangle + twoPi;
            }
        }

        private double x;
        private double y;
        private double lastposx;
        private double lastposy;
        private double xwidth;
        private double yheight;
        private double angle; // in Radians
        private double internalangle; // in Radians
        private Vector<Double> coords;
        private Vector<Double> triangles;
        private double comX;
        private double comY;
        private int maxFrames;
        private int currentFrame;
        private int life;
        private int maxLife;
        private int dropLife;
        private Boolean bounce;
    }

    private static void bindKey(JPanel myPanel, String input) {
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke("pressed " + input), input + " pressed");
        myPanel.getActionMap().put(input + " pressed", new KeyPressed(input));
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke("released " + input), input + " released");
        myPanel.getActionMap().put(input + " released", new KeyReleased(input));
    }

    public static void main(String[] args) {
        setup();
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setSize(WINWIDTH + 1, WINHEIGHT + 85);
        JPanel myPanel = new JPanel();
        /*
            String[] levels = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
            JComboBox<String> levelMenu = new JComboBox<String>( levels );
            levelMenu.setSelectedIndex(2);
            levelMenu.addActionListener( new GameLevel() );
            myPanel.add( levelMenu );
        */
        JButton quitButton = new JButton("Select");
        quitButton.addActionListener(new QuitGame());
        myPanel.add(quitButton);
        JButton newGameButton = new JButton("Start");
        newGameButton.addActionListener(new StartGame());
        myPanel.add(newGameButton);
        bindKey(myPanel, "UP");
        bindKey(myPanel, "DOWN");
        bindKey(myPanel, "LEFT");
        bindKey(myPanel, "RIGHT");
        bindKey(myPanel, "F");
        appFrame.getContentPane().add(myPanel, "South");
        appFrame.setVisible(true);
    }

    private static Boolean endgame;
    private static Vector<Vector<BufferedImage>> backgroundKI;
    private static Vector<Vector<BufferedImage>> backgroundTC;
    private static Vector<Vector<Vector<ImageObject>>> wallsKI;
    private static Vector<Vector<Vector<ImageObject>>> wallsTC;
    private static int xdimKI;
    private static int ydimKI;
    private static int xdimTC;
    private static int ydimTC;
    private static BufferedImage player;
    private static Vector<BufferedImage> link;
    private static BufferedImage leftHeartOutline;
    private static BufferedImage rightHeartOutline;
    private static BufferedImage leftHeart;
    private static BufferedImage rightHeart;
    private static Vector<BufferedImage> bluepigEnemy;
    private static Vector<BufferedImage> rupee;
    private static Vector<ImageObject> rupees;
    private static Vector<ImageObject> bluepigEnemies;
    private static Vector<ImageObject> bubblebossEnemies;
    private static ImageObject doorKItoTC;
    private static ImageObject doorTCtoKI;
    private static Boolean upPressed;
    private static Boolean downPressed;
    private static Boolean leftPressed;
    private static Boolean rightPressed;
    private static Boolean aPressed;
    private static Boolean xPressed;
    private static double lastPressed;
    private static ImageObject p1;
    private static double p1width;
    private static double p1height;
    private static double p1originalX;
    private static double p1originalY;
    private static double p1velocity;
    private static int level;
    private static Long audiolifetime;
    private static Long lastAudioStart;
    private static Clip clip;
    private static Long dropLifeLifetime;
    private static Long lastDropLife;
    private static int XOFFSET;
    private static int YOFFSET;
    private static int WINWIDTH;
    private static int WINHEIGHT;
    private static int moneyCount;
    private static double pi;
    private static double quarterPi;
    private static double halfPi;
    private static double threequartersPi;
    private static double fivequartersPi;
    private static double threehalvesPi;
    private static double sevenquartersPi;
    private static double twoPi;
    private static JFrame appFrame;
    private static String backgroundState;
    private static Boolean availableToDropLife;
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
}