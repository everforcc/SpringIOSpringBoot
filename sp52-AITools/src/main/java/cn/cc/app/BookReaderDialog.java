package cn.cc.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookReaderDialog extends JDialog {
    private final List<String> chapters = new ArrayList<>();
    private List<String> chapterTitles = new ArrayList<>();  // 新增章节标题列表
    private int currentPage = 0;
    private static final int PAGE_SIZE = 20;
    private JTextArea contentArea;  
    private JTextField pageInputField;
    private JPanel fontControlPanel; // 新增成员变量声明

    // 新增配置常量
    private static final String CONFIG_PATH = System.getProperty("user.home") + "/.book_reader_config.properties";
    private int fontSize = 14; // 默认字体大小

    public BookReaderDialog(File bookFile) {
        setTitle("书籍阅读器");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // 解析章节内容
        try {
            String content = new String(Files.readAllBytes(bookFile.toPath()), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("第[\\u4e00-\\u9fa5]+章\\s*.*");
            Matcher matcher = pattern.matcher(content);
            int lastStart = 0;
            
            // 存储完整章节内容（包含标题）
            while (matcher.find()) {
                if (lastStart != 0) {
                    chapters.add(content.substring(lastStart, matcher.start()));
                }
                lastStart = matcher.start();
            }
            if (lastStart != 0) {
                chapters.add(content.substring(lastStart));
            }
            
            // 提取章节标题（首行内容）
            for (String chapter : chapters) {
                int endOfLine = chapter.indexOf('\n');
                String title = (endOfLine == -1) ? chapter : chapter.substring(0, endOfLine).trim();
                chapterTitles.add(title);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "文件读取失败", "错误", JOptionPane.ERROR_MESSAGE);
        }

        // 创建左右分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        
        // 章节列表
        JList<String> chapterList = new JList<>(getPageChapters());
        chapterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chapterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showChapterContent(chapterList.getSelectedIndex() + currentPage * PAGE_SIZE);
            }
        });
        
        // 分页控件
        JPanel paginationPanel = new JPanel();
        JButton prevButton = new JButton("上一页");
        JButton nextButton = new JButton("下一页");
        JLabel pageLabel = new JLabel("第 " + (currentPage + 1) + " 页");
        
        // 修改：恢复输入框原始尺寸
        pageInputField = new JTextField(8); // 恢复为默认宽度
        pageInputField.setPreferredSize(new Dimension(40, 20)); // 新增：设置固定尺寸
        pageInputField.setMinimumSize(new Dimension(40, 20)); // 新增：设置最小尺寸
        pageInputField.setMaximumSize(new Dimension(40, 20)); // 新增：设置最大尺寸
        JButton gotoButton = new JButton("跳转");
        gotoButton.addActionListener(e -> {
            try {
                int targetPage = Integer.parseInt(pageInputField.getText()) - 1;
                updatePage(targetPage, chapterList, pageLabel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效页码", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 调整分页面板布局
        paginationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 5);

        // 添加组件到分页面板
        gbc.gridx = 0;
        paginationPanel.add(prevButton, gbc);

        gbc.gridx = 1;
        paginationPanel.add(pageLabel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0; // 禁止水平扩展
        paginationPanel.add(pageInputField, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0; // 按钮不扩展
        paginationPanel.add(gotoButton, gbc);

//        gbc.gridx = 4;
//        paginationPanel.add(Box.createHorizontalStrut(20), gbc);

        gbc.gridx = 4;
        paginationPanel.add(nextButton, gbc);

        prevButton.addActionListener(e -> updatePage(currentPage - 1, chapterList, pageLabel));
        nextButton.addActionListener(e -> updatePage(currentPage + 1, chapterList, pageLabel));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(chapterList), BorderLayout.CENTER);
        leftPanel.add(paginationPanel, BorderLayout.SOUTH);
        
        // 内容显示区域
        loadFontSizeFromConfig(); // 新增：加载字体配置
        contentArea = new JTextArea();
        contentArea.setFont(new Font("宋体", Font.PLAIN, fontSize)); // 应用字体大小
        contentArea.setLineWrap(true); // 新增：启用自动换行
        contentArea.setWrapStyleWord(true); // 新增：在单词边界处换行
        
        // 在右侧面板添加字体控制组件
        fontControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 删除JPanel类型声明
        JComboBox<Integer> fontSizeCombo = new JComboBox<>(new Integer[]{12, 14, 16, 18, 20});
        fontSizeCombo.setSelectedItem(fontSize);
        fontSizeCombo.addActionListener(e -> {
            fontSize = (Integer) fontSizeCombo.getSelectedItem();
            contentArea.setFont(new Font("宋体", Font.PLAIN, fontSize));
            saveFontSizeToConfig();
        });

        // 调整字体控制面板布局
        fontControlPanel.add(new JLabel("字体大小:"));
        fontSizeCombo.setPreferredSize(new Dimension(80, 30)); // 增大下拉框尺寸
        fontControlPanel.add(fontSizeCombo);
        
        // 修改右侧面板布局（仅保留内容区域）
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        
        // 修复分页按钮布局问题

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane);
    }

    // 修改分页获取方法为获取标题
    private String[] getPageChapters() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, chapterTitles.size());
        return chapterTitles.subList(start, end).toArray(new String[0]);
    }

    private void updatePage(int newPage, JList<String> list, JLabel label) {
        int maxPage = (int) Math.ceil((double) chapterTitles.size() / PAGE_SIZE);
        if (newPage >= 0 && newPage < maxPage) {
            currentPage = newPage;
            list.setListData(getPageChapters());
            label.setText("第 " + (currentPage + 1) + " 页");
            pageInputField.setText(""); // 清空输入框
        } else {
            JOptionPane.showMessageDialog(this, "页码超出范围", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 修改说明：添加内容滚动到顶部的功能
    private void showChapterContent(int index) {
        if (index >= 0 && index < chapters.size()) {
            contentArea.setText(chapters.get(index));
            contentArea.setCaretPosition(0); // 新增：设置光标在起始位置以实现内容置顶

            // 添加章节跳转控件
            JPanel navPanel = new JPanel(new GridLayout(1, 5, 10, 0));
            JButton prevChapter = new JButton("上一章");
            JButton nextChapter = new JButton("下一章");
            JLabel chapterLabel = new JLabel("当前章节: " + chapterTitles.get(index));
            
            // 新增章节选择下拉框
            JComboBox<String> chapterSelector = new JComboBox<>(chapterTitles.toArray(new String[0]));
            chapterSelector.setPrototypeDisplayValue("第XXX章 这是一个非常长的章节标题示例"); // 设置原型显示值
            chapterSelector.setPreferredSize(new Dimension(300, 30)); // 设置固定尺寸
            chapterSelector.setSelectedIndex(index);
            chapterSelector.addActionListener(e -> {
                int selected = chapterSelector.getSelectedIndex();
                showChapterContent(selected);
            });
            
            // 新增快速跳转按钮
            JButton firstChapterBtn = new JButton("第一章");
            JButton lastChapterBtn = new JButton("最末章");
            firstChapterBtn.addActionListener(e -> showChapterContent(0));
            lastChapterBtn.addActionListener(e -> showChapterContent(chapters.size() - 1));

            prevChapter.setEnabled(index > 0);
            nextChapter.setEnabled(index < chapters.size() - 1);
            firstChapterBtn.setEnabled(index > 0);
            lastChapterBtn.setEnabled(index < chapters.size() - 1);
            
            prevChapter.addActionListener(e -> showChapterContent(index - 1));
            nextChapter.addActionListener(e -> showChapterContent(index + 1));
            
            // 调整导航面板布局
            navPanel.add(firstChapterBtn);
            navPanel.add(prevChapter);
            navPanel.add(chapterSelector);
            navPanel.add(nextChapter);
            navPanel.add(lastChapterBtn);
            
            // 修改右侧面板布局（将字体控制面板添加到章节内容下方）
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(navPanel, BorderLayout.NORTH);
            rightPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
            rightPanel.add(fontControlPanel, BorderLayout.SOUTH);  // 调整布局位置

            ((JSplitPane)getContentPane().getComponent(0)).setRightComponent(rightPanel);
        }
    }

    // 新增字体配置保存方法
    private void saveFontSizeToConfig() {
        try (OutputStream out = Files.newOutputStream(Paths.get(CONFIG_PATH))) {
            Properties props = new Properties();
            props.setProperty("fontSize", String.valueOf(fontSize));
            props.store(out, "Book Reader Config");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 新增字体配置加载方法
    private void loadFontSizeFromConfig() {
        if (!Files.exists(Paths.get(CONFIG_PATH))) {
            return;
        }
        
        try (InputStream in = Files.newInputStream(Paths.get(CONFIG_PATH))) {
            Properties props = new Properties();
            props.load(in);
            String size = props.getProperty("fontSize");
            if (size != null) {
                fontSize = Integer.parseInt(size);
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}