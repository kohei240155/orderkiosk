package com.reza.orderkiosk.ui;

import com.reza.orderkiosk.model.Order;

import javax.swing.*;
import java.awt.*;

public class ReceiptDialog extends JDialog {
    public ReceiptDialog(Window owner, Order order, long receiptId) {
        super(owner, "Receipt", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(owner);

        var textArea = new JTextArea(ReceiptFormatter.format(order));
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        textArea.setCaretPosition(0);

        var closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        var south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(new JLabel("Receipt ID: " + receiptId));
        south.add(closeBtn);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
    }
}
