package org.cge.engine.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * A functional facade for java Swing.
 */
class SwingFunctionalFacade {

    public interface Frame {
        /**
         * Sets the window size.
         * @param width the width of the window.
         * @param height the height of the window.
         * @return the updated frame.
         */
        Frame setSize(int width, int height);

        /**
         * Adds a panel to the frame.
         * @param panelName the name of the panel to be added.
         * @param x the x coordinate of the panel.
         * @param y the y coordinate of the panel.
         * @param width the width of the panel.
         * @param height the height of the panel.
         * @return the updated frame.
         */
        Frame addPanel(String panelName, int x, int y, int width, int height);

        /**
         * Sets a Grid Layout for the given panel.
         * @param panelName the name of the panel the layout should be added to.
         * @param orientation if the orientation should be orizontal or vertical.
         * @return the updated frame.
         */
        Frame addGridLayout(String panelName, boolean orientation);

        /**
         * Adds a title to the panel.
         * @param panelName the name of the panel the title should be added to.
         * @param title the panel title.
         * @return the updated frame.
         */
        Frame addPanelTitle(String panelName, String title);

        /**
         * Adds a component to a panel.
         * @param panelName the name of the panel the component should be added to.
         * @param component the component to be added.
         * @return the updated frame.
         */
        Frame addComponentToPanel(String panelName, Component component);

        /**
         * Removes a component from a panel.
         * @param panelName the name of the panel the component should be removed from.
         * @param component the component to be removed.
         * @return the updated frame.
         */
        Frame removeComponentFromPanel(String panelName, Component component);

        /**
         * Adds a component to the frame.
         * @param name the name of the component to be added.
         * @param component the component to be added.
         * @return the updated frame.
         */
        Frame addComponent(String name, Component component);
    
        /**
         * Sets the bounds of a given component.
         * @param name the name of the component.
         * @param x the new x coordinate of the component.
         * @param y the new y coordinate of the component.
         * @param width the width of the component.
         * @param height the height of the component.
         * @return the updated frame.
         */
        Frame setComponentBounds(String name, int x, int y, int width, int height);

        /**
         * Removes a component from the frame.
         * @param name the name of the component to be removed.
         * @return the updated frame.
         */
        Frame removeComponent(String name);

        /**
         * Adds a button to the frame.
         * @param jb the button to be added.
         * @param eventName the name of the event to link to the button click.
         * @return the updated frame.
         */
        Frame addButton(JButton jb, String eventName);
    
        /**
         * Removes a button from the frame.
         * @param jb the button to be removed.
         * @return the updated frame.
         */
        Frame removeButton(JButton jb);
        
        /**
         * Adds a label to the frame.
         * @param labelText the label text.
         * @param x the x coordinate of the label.
         * @param y the y coordinate of the label.
         * @param width the width of the label.
         * @param height the height of the label.
         * @return the updated frame.
         */
        Frame addLabel(String labelText, int x, int y, int width, int height);
    
        /**
         * Makes the frame visible.
         * @return the updated frame.
         */
        Frame show();

        /**
         * Gets the event supplier.
         * @return the event supplier
         */
        Supplier<String> events();
    }

    /**
     * @return the created frame.
     */
    public static Frame createFrame() {
        return new FrameImpl();
    }

    /**
     * Implementation of the Frame interface.
     */
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

        /**
         * Constructor. Sets the default close operation.
         */
        public FrameImpl() {
            this.jframe.setLayout(null);
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
            panel.setLayout(null);
            panel.setBounds(x, y, width, height);
            this.panels.put(panelName, panel);
            this.jframe.getContentPane().add(panel);
            return this;
        }

        @Override
        public Frame addGridLayout(String panelName, boolean orientation) {
            var playerPanel =  this.panels.get(panelName);
            var rows = orientation ? 0 : 1;
            var columns = orientation ? 1 : 0;
            playerPanel.setLayout(new GridLayout(rows, columns));
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
        public Frame removeComponentFromPanel(String panelName, Component component) {
            JPanel panel = this.panels.get(panelName);
            if (panel != null) {
                panel.remove(component);
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
            this.repaint();
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
            this.repaint();
            return this;
        }

        @Override
        public Frame removeButton(JButton jb) {
            for (ActionListener al : jb.getActionListeners()) {
                jb.removeActionListener(al);
            }
            this.jframe.getContentPane().remove(jb);
            this.repaint();
            return this;
        }


        @Override
        public Frame addLabel(String labelText, int x, int y, int width, int height) {
            final JLabel jl = new JLabel(labelText);
            jl.setFont(new Font("Arial", Font.BOLD, 24));
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setBounds(x, y, width, height);
            this.jframe.getContentPane().add(jl);
            this.repaint();
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

        private Frame repaint() {
            this.jframe.repaint();
            return this;
        }
    }
}
