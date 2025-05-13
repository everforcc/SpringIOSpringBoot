import javax.swing.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.awt.*;

public class JsonFormatDialog extends JDialog {
    private JTextArea inputArea = new JTextArea();
    private JTextArea outputArea = new JTextArea();

    public JsonFormatDialog() {
        // 初始化文本区域
        inputArea.setLineWrap(false); // 关闭自动换行以支持水平扩展
        outputArea.setEditable(false);
        outputArea.setLineWrap(false); // 关闭自动换行以支持水平扩展

        // 添加格式化按钮事件
        JButton formatButton = new JButton("格式化");
        formatButton.addActionListener(e -> {
            try {
                String input = inputArea.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "请输入JSON内容", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // json格式化

                JSONObject jsonObject = JSONObject.parseObject(input);
                String formatStr = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                outputArea.setText(formatStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "JSON格式错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(formatButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        // 设置滚动面板尺寸策略
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // 添加水平滚动条
        inputScroll.setPreferredSize(new Dimension(300, 400));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // 添加水平滚动条
        outputScroll.setPreferredSize(new Dimension(300, 400));

        // 创建弹性布局容器
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;  // 水平权重保持1
        gbc.weighty = 1.0;  // 垂直权重保持1

        // 输入面板约束调整
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;  // 明确指定列跨度
        contentPanel.add(inputScroll, gbc);

        // 按钮面板约束调整
        gbc.gridx = 1;
        gbc.gridy = 0;  // 确保按钮位于同一行
        gbc.weightx = 0.0; // 按钮列不参与水平扩展
        gbc.anchor = GridBagConstraints.CENTER; // 添加居中约束
        contentPanel.add(buttonPanel, gbc);

        // 输出面板约束调整
        gbc.gridx = 2;
        gbc.weightx = 1.0; // 恢复水平扩展权重
        contentPanel.add(outputScroll, gbc);

        // 设置布局
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(contentPanel, BorderLayout.CENTER);
        add(panel);
        
        // 修改：设置容器布局为BorderLayout并添加滚动条支持
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);  // 直接添加弹性布局容器

        // 设置对话框属性
        setTitle("JSON格式化工具");
        setMinimumSize(new Dimension(900, 500)); // 添加最小尺寸限制
        setPreferredSize(new Dimension(1200, 600)); // 设置首选尺寸
        pack();
    }
}