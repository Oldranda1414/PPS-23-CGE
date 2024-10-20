package org.cge.engine.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

class SwingFunctionalFacade {

    public interface Frame {
        Frame setSize(int width, int height);
        Frame addPanel(String panelName, int x, int y, int width, int height);
        Frame addBoxLayout(String panelName);
        Frame addPanelTitle(String panelName, String title);
        Frame addComponentToPanel(String panelName, Component component);
        Frame addButton(JButton jb, String eventName);
        Frame addLabel(String labelText, int x, int y, int width, int height);
        Frame show();
        Supplier<String> events();
        Frame addComponent(String name, Component component);
        Frame removeComponent(String name);
        Frame setComponentBounds(String name, int x, int y, int width, int height);
        Frame repaint();
        Frame dispose();
    }

    public static Frame createFrame() {
        return new FrameImpl();
    }

    private static class FrameImpl implements Frame {
        private final JFrame jframe = new JFrame();
        private final Map<String, JPanel> panels = new HashMap<>();
        private final Map<String, Component> components = new HashMap<>();
        private final LinkedBlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
        private final Supplier<String> events = () -> {
            try {
                return eventQueue.take();
            } catch (InterruptedException e) {
                return "";
            }
        };

        public FrameImpl() {
            this.jframe.setLayout(null); // Allow manual component positioning
            this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        @Override
        public Frame setSize(int width, int height) {
            this.jframe.setSize(width, height);
            return this;
        }

        @Override
        public Frame addPanel(String panelName, int x, int y, int width, int height) {
            JPanel panel = new JPanel();
            panel.setLayout(null); // Allow manual layout for components
            panel.setBounds(x, y, width, height);
            this.panels.put(panelName, panel);
            this.jframe.getContentPane().add(panel);
            return this;
        }

        @Override
        public Frame addBoxLayout(String panelName) {
            var playerPanel =  this.panels.get(panelName);
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            return this;
        }

        @Override
        public Frame addPanelTitle(String panelName, String title) {
            this.panels.get(panelName).setBorder(BorderFactory.createTitledBorder(title));
            return this;
        }

        @Override
        public Frame addComponentToPanel(String panelName, Component component) {
            JPanel panel = this.panels.get(panelName);
            if (panel != null) {
                panel.add(component);
            }
            this.repaint();
            return this;
        }

        @Override
        public Frame addComponent(String name, Component component) {
            this.components.put(name, component);
            this.jframe.getContentPane().add(component);
            this.repaint();
            return this;
        }

        @Override
        public Frame setComponentBounds(String name, int x, int y, int width, int height) {
            Component component = this.components.get(name);
            if (component != null) {
                component.setBounds(x, y, width, height);
            }
            return this;
        }

        @Override
        public Frame removeComponent(String name) {
            Component component = this.components.get(name);
            if (component != null) {
                this.jframe.getContentPane().remove(component);
                this.components.remove(name);
            }
            this.repaint();
            return this;
        }

        @Override
        public Frame addButton(JButton jb, String eventName) {
            jb.addActionListener(e -> {
                try {
                    eventQueue.put(eventName);
                } catch (InterruptedException ex) {}
            });
            this.jframe.getContentPane().add(jb);
            return this;
        }


        @Override
        public Frame addLabel(String labelText, int x, int y, int width, int height) {
            final JLabel jl = new JLabel(labelText);
            jl.setFont(new Font("Arial", Font.BOLD, 24));
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setBounds(x, y, width, height);
            this.jframe.getContentPane().add(jl);
            this.jframe.repaint();
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
        public Frame repaint() {
            this.jframe.repaint();
            return this;
        }

        @Override
        public Frame dispose() {
            this.jframe.dispose();
            return this;
        }
    }
}
