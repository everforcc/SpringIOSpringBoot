
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MdConvertDialog extends JDialog {
    private JTextArea inputArea = new JTextArea();
    private JTextArea outputArea = new JTextArea();

    public MdConvertDialog() {
        // 初始化基础组件
        inputArea.setLineWrap(false);
        outputArea.setEditable(false);
        outputArea.setLineWrap(false);

        // 创建转换按钮
        JButton convertButton = new JButton("转换");
        convertButton.addActionListener(e -> performConversion());

        // 创建文件选择按钮
        JButton fileButton = new JButton("选择文件");
        fileButton.addActionListener(e -> selectFile());

        // 按钮面板布局
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(fileButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(convertButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        // 滚动面板配置
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // 弹性布局容器
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // 输入面板
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(inputScroll, gbc);

        // 按钮面板
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        contentPanel.add(buttonPanel, gbc);

        // 输出面板
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        contentPanel.add(outputScroll, gbc);

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        
        setTitle("MD文件转换工具");
        setMinimumSize(new Dimension(900, 500));
        setPreferredSize(new Dimension(1200, 600));
        pack();
    }

    private void performConversion() {
        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(inputArea.getText());
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            outputArea.setText(renderer.render(document));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "转换错误: " + ex.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Markdown Files", "md"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String content = new String(Files.readAllBytes(chooser.getSelectedFile().toPath()));
                inputArea.setText(content);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "文件读取失败: " + ex.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}