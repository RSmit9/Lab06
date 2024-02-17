        import javax.swing.*;
        import javax.swing.border.TitledBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.text.DecimalFormat;
public class PizzaGUIFrame extends JFrame
    {
        private JRadioButton thinCrustRadioButton,
                            regularCrustRadioButton,
                            deepDishCrustRadioButton;
        private JComboBox<String> sizeComboBox;
        private JCheckBox[] toppingsCheckBoxes;
        private JTextArea orderTextArea;
        private final double[] toppingPrices = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
            public PizzaGUIFrame() {
                setTitle("Pizza Order Form");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new BorderLayout());

                JPanel crustPanel = createTitledPanel("Crust Type", createCrustPanel());
                JPanel sizePanel = createTitledPanel("Size", createSizePanel());
                JPanel toppingPanel = createTitledPanel("Toppings", createToppingPanel());
                JPanel orderPanel = createTitledPanel("Order", createOrderPanel());
                JPanel buttonPanel = createButtonPanel();

              add(crustPanel, BorderLayout.NORTH);
                add(sizePanel, BorderLayout.WEST);
                add(toppingPanel, BorderLayout.CENTER);
                add(orderPanel, BorderLayout.EAST);
                add(buttonPanel, BorderLayout.SOUTH);

                pack();
    }
        private JPanel createTitledPanel(String title, JPanel panel) {
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.CENTER, TitledBorder.TOP));
            return panel;
    }
        private JPanel createCrustPanel() {
            JPanel crustPanel = new JPanel(new GridLayout(3, 1));
            ButtonGroup crustGroup = new ButtonGroup();
                thinCrustRadioButton = new JRadioButton("Thin");
                regularCrustRadioButton = new JRadioButton("Regular");
                deepDishCrustRadioButton = new JRadioButton("Deep-dish");
            crustGroup.add(thinCrustRadioButton);
            crustGroup.add(regularCrustRadioButton);
            crustGroup.add(deepDishCrustRadioButton);
            crustPanel.add(thinCrustRadioButton);
            crustPanel.add(regularCrustRadioButton);
            crustPanel.add(deepDishCrustRadioButton);
            return crustPanel;
    }
        private JPanel createSizePanel() {
            JPanel sizePanel = new JPanel();
            String[] sizes = {"Small", "Medium", "Large", "SUper"};
            sizeComboBox = new JComboBox<>(sizes);
            sizePanel.add(sizeComboBox);
            return sizePanel;
    }
    private JPanel createToppingPanel()
    {
            JPanel toppingPanel = new JPanel(new GridLayout(0, 1));
            String[] toppings = {"Pepperoni", "Mushrooms", "Pineapple", "Anchovies", "Bacon", "Sausage"};
            toppingsCheckBoxes = new JCheckBox[toppings.length];
            for (int i = 0; i < toppings.length; i++)

                {
                    toppingsCheckBoxes[i] = new JCheckBox(toppings[i]);
                    toppingPanel.add(toppingsCheckBoxes[i]);
                }
                    return toppingPanel;
    }
    private JPanel createOrderPanel()
        {
            JPanel orderPanel = new JPanel(new BorderLayout());
            orderTextArea = new JTextArea(10, 20);
            orderTextArea.setEditable(false);
            orderPanel.add(new JScrollPane(orderTextArea), BorderLayout.CENTER);
            return orderPanel;
        }
    private JPanel createButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("quit");
        orderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
                {
                    orderPizza();
                }
        });
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
                {
                    clearForm();
                }
        });
        quitButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                    {
                        quitApplication();
                    }
            });
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        return buttonPanel;
    }
    private void orderPizza()
    {
        StringBuilder orderBuilder = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");

        if (thinCrustRadioButton.isSelected() || regularCrustRadioButton.isSelected() || deepDishCrustRadioButton.isSelected())
        {
            if (sizeComboBox.getSelectedIndex() != -1)
            {
                orderBuilder.append("=========================================\n");
                orderBuilder.append(" Crust and Size\t\tPrice\n");
                orderBuilder.append("=========================================\n");

                double basePrice = 8.0 * (sizeComboBox.getSelectedIndex() + 1);

                String crustType = thinCrustRadioButton.isSelected() ? "Thin" :
                        regularCrustRadioButton.isSelected() ? "Regular" :
                                deepDishCrustRadioButton.isSelected() ? "Deep-dish" : "";
                orderBuilder.append(crustType).append(" Crust - ").append(sizeComboBox.getSelectedItem()).append("\t$").append(df.format(basePrice)).append("\n");

                double totalToppingPrice = 0.0;
                for (int i = 0; i < toppingsCheckBoxes.length; i++)
                {
                    if (toppingsCheckBoxes[i].isSelected())
                        {
                            orderBuilder.append(toppingsCheckBoxes[i].getText()).append("\t$").append(df.format(toppingPrices[i])).append("\n");
                            totalToppingPrice += toppingPrices[i];
                        }
                }

                double subTotal = basePrice + totalToppingPrice;
                double tax = 0.07 * subTotal;
                double total = subTotal + tax;
                    orderBuilder.append("-----------------------------------------\n");
                    orderBuilder.append("Sub-total:\t\t$").append(df.format(subTotal)).append("\n");
                    orderBuilder.append("Tax:\t\t\t$").append(df.format(tax)).append("\n");
                    orderBuilder.append("-----------------------------------------\n");
                    orderBuilder.append("Total:\t\t\t$").append(df.format(total)).append("\n");
                    orderBuilder.append("=========================================\n");

                orderTextArea.setText(orderBuilder.toString());
            } else
                {
                    JOptionPane.showMessageDialog(this, "select pizza size.", "Error", JOptionPane.ERROR_MESSAGE);
                }
        } else
            {
                JOptionPane.showMessageDialog(this, "select crust type.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    private void clearForm()
        {
            thinCrustRadioButton.setSelected(false);
            regularCrustRadioButton.setSelected(false);
            deepDishCrustRadioButton.setSelected(false);
            sizeComboBox.setSelectedIndex(-1);
            for (JCheckBox checkBox : toppingsCheckBoxes)
                {
                    checkBox.setSelected(false);
                }
            orderTextArea.setText("");
        }
    private void quitApplication()
        {
            int choice = JOptionPane.showConfirmDialog(this, "quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
        }
}
