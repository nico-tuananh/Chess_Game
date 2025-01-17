package main;

import javax.swing.*;
//import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class ChessTimerGUI extends JPanel {
    static int timer1; // time for each player's timer
    static int timer2;
    static int startTime1; // start and end time of each player's turn
    static int endTime1;
    static int startTime2;
    static int endTime2;
    static boolean isTimerRunning; // indicates whether the timer is currently running or not
    static boolean isTimer1Active; // indicates which player's timer is currently active
    static boolean isTimer2Active;
    static JLabel labelTimer1; // JLabel objects to display the timer values for each player
    static JLabel labelTimer2;
    static Timer timer1Obj; // Timer objects to control the timing functionality
    static Timer timer2Obj;

    static Board board;
    static boolean canStart;

    public ChessTimerGUI(Board board) {

        /* Set timer values as game mode
        - Ultrabullet: 15
        - Bullet: 60
        - Blitz: 300
        - Rapid: 900
        - Traditional: 3600
         */

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Which game mode would you like to play? (Ultrabullet, Bullet, Blitz, Rapid, Traditional)");
//        String mode = scanner.next();
//
//        switch (mode) {
//            case "Ultrabullet":
//                timer1 = timer2 = 15;
//                break;
//            case "Bullet":
//                timer1 = timer2 = 60;
//                break;
//            case "Blitz":
//                timer1 = timer2 = 300; +-5
//                break;
//            case "Rapid":
//                timer1 = timer2 = 900;
//                break;
//            case "Traditional":
//                timer1 = timer2 = 3600;
//                break;
//            default:
//                timer1 = scanner.nextInt();
//                timer2 = scanner.nextInt();
//                break;
//        }

        timer1 = 10;
        timer2 = 10;
        this.board = board;

        isTimerRunning = false;
        isTimer1Active = true;
        isTimer2Active = false;
        canStart = false;

        // Set layout for this panel
        setLayout(new BorderLayout());

        // Create and set layout for main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // this (BorderLayout)
        // <- panel (GridBagLayout)
        // <- backgroundPanel
        // <- timersPanel (GridLayout)
        // <- labelTimer1, labelTimer2

        // Create labels for timer display
        labelTimer1 = new JLabel("Player 1: " + formatTime(timer1));
        labelTimer1.setFont(new Font("Arial", Font.BOLD, 30));
        labelTimer1.setOpaque(true);
        labelTimer1.setBackground(Color.BLACK);
        labelTimer1.setForeground(Color.WHITE);

        labelTimer2 = new JLabel("Player 2: " + formatTime(timer2));
        labelTimer2.setFont(new Font("Arial", Font.BOLD, 30));
        labelTimer2.setOpaque(true);
        labelTimer2.setBackground(Color.BLACK);
        labelTimer2.setForeground(Color.WHITE);

        // Create background panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());

        // Create timers panel
        JPanel timersPanel = new JPanel();
        timersPanel.setLayout(new GridLayout(2, 1, 0, 50));
        timersPanel.setBackground(Color.BLACK);
        timersPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        timersPanel.add(labelTimer1);
        timersPanel.add(labelTimer2);

        // Add panels
        backgroundPanel.add(timersPanel, BorderLayout.CENTER);
        panel.add(backgroundPanel);
        add(panel, BorderLayout.CENTER);

        // Set panel properties
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(53, 53, 53));
    }

    // Automatically start Timer2 at the start of the game
    public void startGame() {
        if (canStart) {
            isTimerRunning = true;
            timer1Obj = new Timer(1000, new TimerAction(timer1, labelTimer1));
            startTime1 = (int) System.currentTimeMillis() / 1000;
            timer1Obj.start();
        }
    }

    // Formats the given time in minutes and seconds
    private String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Handles the action performed when a move is made.
    // Starts or stops the timers based on the current player's turn.
    public void changeTimer(boolean changed) {
        // Start or stop timer for current player
        // if (isTimerRunning)
        if (changed) {
            if (isTimer1Active) {
                endTime1 = (int) System.currentTimeMillis() / 1000;
                timer1Obj.stop();
                timer1 = timer1 - (endTime1 - startTime1);
                //System.out.println("Player 1-2 time remaining: " + timer1 + "-" + timer2);
                isTimer1Active = false;
                isTimer2Active = true;
            } else {
                endTime2 = (int) System.currentTimeMillis() / 1000;
                timer2Obj.stop();
                timer2 = timer2 - (endTime2 - startTime2);
                //System.out.println("Player 1-2 time remaining: " + timer1 + "-" + timer2);
                isTimer2Active = false;
                isTimer1Active = true;
            }
            isTimerRunning = false;
        }

        if (isTimer1Active) {
            timer1Obj = new Timer(1000, new TimerAction(timer1, labelTimer1));
            startTime1 = (int) System.currentTimeMillis() / 1000;
            timer1Obj.start();
        } else {
            timer2Obj = new Timer(1000, new TimerAction(timer2, labelTimer2));
            startTime2 = (int) System.currentTimeMillis() / 1000;
            timer2Obj.start();
        }

        isTimerRunning = true;
    }

    // Inner class implementing ActionListener interface
    // Update the timer label and check if player's time has run out
    private class TimerAction implements ActionListener {
        private int time;
        private JLabel label;

        public TimerAction(int time, JLabel label) {
            this.time = time;
            this.label = label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (canStart) {
                time--;
                label.setText(isTimer1Active ? "Player 1: \n" + formatTime(time) : "Player 2: \n" + formatTime(time));

                // Set red background if player has less than 30 seconds less
                if (time < 30) {
                    label.setBackground(new Color(232, 103, 103));
                }

                System.out.println("Time in actionPerformed(): " + time);
                System.out.println("isTimer1Active-isTimer2Active: " + isTimer1Active + "-" + isTimer2Active);
                if (time <= 0) {
                    // Player's time has run out
                    if (isTimer1Active) {
                        timer1Obj.stop();
                    } else {
                        timer2Obj.stop();
                    }
                    isTimerRunning = false;
                    // Display a message dialog when a player's time runs out, indicating the winner of the game.
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), isTimer1Active ? "Player 2 wins!" : "Player 1 wins!");

                }
            }
        }
    }

//    public static void main(String[] args) {
//        new ChessTimerGUI();
//    }

    // setLookAndFeel()
}