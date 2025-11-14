package com.reza.orderkiosk.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private JList<String> itemsList;
    private DefaultListModel<String> itemsModel;
    private JSpinner qtySpinner;
    private JButton addBtn;
    private JTable cartTable;
    private JLabel subtotalLbl;
    private JLabel taxLbl;
    private JLabel totalLbl;

    public MainFrame() {
        super("Cafe Order Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 640);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        add(buildLeftPanel(), BorderLayout.WEST);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildRightPanel(), BorderLayout.EAST);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    private JComponent buildLeftPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        var drinksBtn = new JButton("Drinks");
        var bakeryBtn = new JButton("Bakery");

        panel.add(new JLabel("Categories"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(drinksBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(bakeryBtn);

        panel.setPreferredSize(new Dimension(180, 10));
        return panel;
    }

    private JComponent buildCenterPanel() {
        var root = new JPanel(new BorderLayout(6, 6));
        root.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        root.add(new JLabel("Items"), BorderLayout.NORTH);

        itemsModel = new DefaultListModel<>();
        itemsModel.addElement("Coffee - 3.00");
        itemsModel.addElement("Tea - 2.50");
        itemsList = new JList<>(itemsModel);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        root.add(new JScrollPane(itemsList), BorderLayout.CENTER);

        var foot = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foot.add(new JLabel("Qty:"));
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        foot.add(qtySpinner);

        addBtn = new JButton("Add to Cart");
        addBtn.setEnabled(false);
        foot.add(addBtn);

        root.add(foot, BorderLayout.SOUTH);
        return root;
    }

    private JComponent buildRightPanel() {
        var panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        panel.add(new JLabel("Cart"), BorderLayout.NORTH);

        cartTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Item", "Qty", "Price", "Line Total"}
        ));
        cartTable.setFillsViewportHeight(true);
        panel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        var btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.add(new JButton("Remove"));
        btns.add(new JButton("Clear"));
        panel.add(btns, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(420, 10));
        return panel;
    }

    private JComponent buildBottomBar() {
        var panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subtotalLbl = new JLabel("Subtotal: 0.00");
        taxLbl = new JLabel("Tax: 0.00");
        totalLbl = new JLabel("Total: 0.00");

        var checkoutBtn = new JButton("Checkout…");

        panel.add(subtotalLbl);
        panel.add(new JLabel("   "));
        panel.add(taxLbl);
        panel.add(new JLabel("   "));
        panel.add(totalLbl);
        panel.add(new JLabel("   "));
        panel.add(checkoutBtn);
        return panel;
    }
}
