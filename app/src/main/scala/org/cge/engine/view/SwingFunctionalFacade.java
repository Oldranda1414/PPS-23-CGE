package org.cge.engine.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

class SwingFunctionalFacade {

    public static interface Frame {
        Frame setSize(int width, int height);
        Frame addPlayer(String playerName);
        Frame addCardToPlayer(String playerName, String cardValue, String cardSuit);
        Frame show();
        Supplier<String> events();
    }

    public static Frame createFrame(){
        return new FrameImpl();
    }

    private static class FrameImpl implements Frame {
        private final JFrame jframe = new JFrame();
        private final Map<String, JPanel> playerPanels = new HashMap<>();
        private final LinkedBlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
        private final Supplier<String> events = () -> {
            try{
                return eventQueue.take();
            } catch (InterruptedException e){
                return "";
            }
        };

        // Position map for placing player hands around the frame
        private final Map<String, Point> playerPositions = Map.of(
            "Player 1", new Point(50, 50),
            "Player 2", new Point(500, 50),
            "Player 3", new Point(50, 400),
            "Player 4", new Point(500, 400)
        );

        public FrameImpl() {
            this.jframe.setLayout(null); // Absolute positioning
        }

        @Override
        public Frame setSize(int width, int height) {
            this.jframe.setSize(width, height);
            return this;
        }

        @Override
        public Frame addPlayer(String playerName) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            playerPanel.setBorder(BorderFactory.createTitledBorder(playerName)); // Show player name as a border title

            // Set player panel location
            Point position = playerPositions.get(playerName);
            if (position != null) {
                playerPanel.setBounds(position.x, position.y, 200, 100); // Set size of player hand area
            }

            this.playerPanels.put(playerName, playerPanel);
            this.jframe.getContentPane().add(playerPanel);
            return this;
        }

        @Override
        public Frame addCardToPlayer(String playerName, String cardValue, String cardSuit) {
            JPanel cardPanel = new JPanel();
            cardPanel.setPreferredSize(new Dimension(50, 70)); // Card size
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            JLabel cardLabel = new JLabel(cardValue + " of " + cardSuit);
            cardPanel.add(cardLabel);

            JPanel playerPanel = this.playerPanels.get(playerName);
            if (playerPanel != null) {
                playerPanel.add(cardPanel);
                playerPanel.revalidate(); // Refresh the panel to display new card
                playerPanel.repaint();
            }

            return this;
        }

        @Override
        public Frame show() {
            this.jframe.setVisible(true);
            return this;
        }

        @Override
        public Supplier<String> events() {
            return events;
        }
    }
}
