package cn.cc.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FunctionPanel extends JPanel {
    private JButton jsonFormatButton;
    private JButton mdConvertButton;
    private JButton readButton;
    private JButton downloadButton;

    public FunctionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 3, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        jsonFormatButton = new JButton("JSON格式化");
        jsonFormatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jsonFormatButton.setMaximumSize(new Dimension(150, 30));
        jsonFormatButton.addActionListener(e -> {
            JsonFormatDialog dialog = new JsonFormatDialog();
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        mdConvertButton = new JButton("MD转换");
        mdConvertButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdConvertButton.setMaximumSize(new Dimension(150, 30));
        mdConvertButton.addActionListener(e -> {
            MdConvertDialog dialog = new MdConvertDialog();
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        readButton = new JButton("阅读文档");
        readButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        readButton.setMaximumSize(new Dimension(150, 30));

        downloadButton = new JButton("下载结果");
        downloadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        downloadButton.setMaximumSize(new Dimension(150, 30));

        buttonPanel.add(jsonFormatButton);
        buttonPanel.add(mdConvertButton);
        buttonPanel.add(readButton);
        buttonPanel.add(downloadButton);

        add(Box.createVerticalStrut(20));
        add(buttonPanel);
        add(Box.createVerticalGlue());
    }
}