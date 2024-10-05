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
        Frame addPlayer(String playerName);
        Frame addCardToPlayer(String playerName, String cardValue, String cardSuit);
        Frame show();
        Supplier<String> events();
        Frame displayWinner(String winner);  // New method
        Frame gameOver();                    // New method
    }

    public static Frame createFrame() {
        return new FrameImpl();
    }

    private static class FrameImpl implements Frame {
        private final JFrame jframe = new JFrame();
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

        private void resizeCards() {
            int width = jframe.getWidth();
            int height = jframe.getHeight();
            int cardWidth = width / 10;
            int cardHeight = height / 10;

            for (String player : playerCards.keySet()) {
                List<JPanel> cards = playerCards.get(player);
                for (int i = 0; i < cards.size(); i++) {
                    JPanel card = cards.get(i);
                    int x = 0, y = 0;

                    switch (player) {
                        case "Player 1":
                            x = 50 + i * (cardWidth + 10);
                            y = 50;
                            break;
                        case "Player 2":
                            x = width - 50 - (cards.size() - i) * (cardWidth + 10);
                            y = 50;
                            break;
                        case "Player 3":
                            x = 50 + i * (cardWidth + 10);
                            y = height - cardHeight - 50;
                            break;
                        case "Player 4":
                            x = width - 50 - (cards.size() - i) * (cardWidth + 10);
                            y = height - cardHeight - 50;
                            break;
                    }
                    card.setBounds(x, y, cardWidth, cardHeight);
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
            winnerLabel.setBounds((jframe.getWidth() / 2) - width/2, (jframe.getHeight() / 2) - height/2, width, height);
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
