package org.cge.engine.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.*;

class SwingFunctionalFacade {

    public static interface Frame {
        Frame setSize(int width, int height);
        Frame addButton(String text, String name);
        Frame addPlayer(String playerName);
        Frame addCardToPlayer(String playerName, String cardValue, String cardSuit);
        Frame show();
        Supplier<String> events();
        Frame displayWinner(String winner);
        Frame gameOver();
    }

    public static Frame createFrame() {
        return new FrameImpl();
    }

    private static class FrameImpl implements Frame {
        private final JFrame jframe = new JFrame();
        private final Map<String, JButton> buttons = new HashMap<>();
        private final Map<String, JPanel> playerPanels = new HashMap<>();
        private final LinkedBlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
        private final Supplier<String> events = () -> {
            try {
                return eventQueue.take();
            } catch (InterruptedException e) {
                return "";
            }
        };

        private final Map<String, List<JPanel>> playerCards = new HashMap<>();

        public FrameImpl() {
            this.jframe.setLayout(null);
            this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.jframe.addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    resizeCards();
                }
            });
        }

        @Override
        public Frame addButton(String text, String name) {
            JButton jb = new JButton(text);
            jb.setBounds(300, 300, 100, 100);
            jb.setActionCommand(name);
            this.buttons.put(name, jb);
            jb.addActionListener(e -> {
                try {
                    eventQueue.put(name);
                } catch (InterruptedException ex) {}
            });
            this.jframe.getContentPane().add(jb);
            return this;
        }

        private void resizeCards() {
            int width = jframe.getWidth();
            int height = jframe.getHeight();

            // Set smaller card dimensions
            int cardWidth = width / 15; // Adjust card width to be smaller
            int cardHeight = height / 15; // Adjust card height to be smaller

            for (String player : playerCards.keySet()) {
                List<JPanel> cards = playerCards.get(player);
                for (int i = 0; i < cards.size(); i++) {
                    JPanel card = cards.get(i);
                    int x = 0, y = 0;

                    switch (player) {
                        case "Player 1": // Left player
                            x = 20; // Fixed distance from the left
                            y = height - cardHeight - 60 - (i * (cardHeight + 5)); // Increased margin to avoid cutting off
                            break;
                        case "Player 2": // Right player
                            x = width - cardWidth - 20; // Fixed distance from the right
                            y = 20 + (i * (cardHeight + 5)); // Stack from the top down
                            break;
                        case "Player 3": // Bottom player
                            x = width - (i + 1) * (cardWidth + 5) - 20; // Stack horizontally from the right
                            y = height - cardHeight - 60; // Fixed distance from the bottom (increased to avoid cutting off)
                            break;
                        case "Player 4": // Top player
                            x = 20 + i * (cardWidth + 5); // Stack horizontally from the left
                            y = 20; // Fixed distance from the top
                            break;
                    }
                    card.setBounds(x, y, cardWidth, cardHeight);
                    adjustCardFont(card, cardWidth, cardHeight); // Adjust font size based on card size
                }
            }
        }


        private void adjustCardFont(JPanel card, int cardWidth, int cardHeight) {
            // Calculate an appropriate font size based on card dimensions
            int fontSize = Math.min(cardWidth / 8, cardHeight / 3); // Adjust this ratio as necessary
            Font newFont = new Font("Arial", Font.PLAIN, fontSize);
            for (Component component : card.getComponents()) {
                if (component instanceof JLabel) {
                    component.setFont(newFont);
                }
            }
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
            playerPanel.setBorder(BorderFactory.createTitledBorder(playerName));

            this.playerPanels.put(playerName, playerPanel);
            this.playerCards.put(playerName, new ArrayList<>());
            this.jframe.getContentPane().add(playerPanel);
            return this;
        }

        @Override
        public Frame addCardToPlayer(String playerName, String cardValue, String cardSuit) {
            JPanel cardPanel = new JPanel();
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel cardLabel = new JLabel(cardValue + " of " + cardSuit);
            cardPanel.add(cardLabel);

            this.playerCards.get(playerName).add(cardPanel);
            this.jframe.getContentPane().add(cardPanel);
            
            resizeCards();
            
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

        @Override
        public Frame displayWinner(String winner) {
            int width = 500;
            int height = 100;
            JLabel winnerLabel = new JLabel("The winner is: " + winner);
            winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            winnerLabel.setBounds((jframe.getWidth() / 2) - width / 2, (jframe.getHeight() / 2) - height / 2, width, height);
            jframe.getContentPane().add(winnerLabel);
            jframe.repaint();
            return this;
        }

        @Override
        public Frame gameOver() {
            jframe.dispose();
            System.exit(0);
            return this;
        }
    }
}
