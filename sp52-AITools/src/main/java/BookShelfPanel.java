// 修改说明：
// 1. 添加书籍列表和存储结构
// 2. 重构布局支持书籍列表展示
// 3. 添加双击打开书籍功能

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class BookShelfPanel extends JPanel {
    private static final String CACHE_PATH = System.getProperty("user.home") + "/.book_cache.properties";
    private DefaultListModel<File> bookListModel = new DefaultListModel<>();
    private JList<File> bookList = new JList<>(bookListModel);
    private JButton selectBookButton = new JButton("选择书籍文件");
    
    public BookShelfPanel() {
        super(new BorderLayout()); // 修改布局为BorderLayout（必须作为构造方法的第一条语句）
        loadBookListFromCache();
        setBackground(Color.WHITE);
        
        // 设置按钮样式
        selectBookButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        selectBookButton.setForeground(Color.WHITE);
        selectBookButton.setBackground(new Color(70, 130, 180));
        selectBookButton.setFocusPainted(false);
        
        // 书籍列表配置
        bookList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
                File file = (File) value;
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setText(file.getName());
                label.setIcon(UIManager.getIcon("FileView.fileIcon"));
                // 新增美化样式
                label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // 增加内边距
                label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                label.setIconTextGap(15); // 增加图标文字间距
                label.setOpaque(true); // 启用背景绘制
                
                // 设置背景颜色
                if (isSelected) {
                    label.setBackground(new Color(230, 240, 250)); // 选中背景色
                    label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 3, 1, 1, new Color(70, 130, 180)), // 左侧高亮边框
                        BorderFactory.createEmptyBorder(8, 9, 8, 12) // 调整内边距
                    ));
                } else {
                    label.setBackground(index % 2 == 0 ? new Color(248, 248, 248) : Color.WHITE); // 斑马条纹
                }
                
                // 添加悬停效果
                if (cellHasFocus) {
                    label.setBackground(new Color(245, 245, 245));
                }
                return label;
            }
        });
        bookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击打开
                    File selectedFile = bookList.getSelectedValue();
                    if (selectedFile != null) {
                        new BookReaderDialog(selectedFile).setVisible(true);
                    }
                }
            }
        });

        // 添加文件选择事件
        selectBookButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("文本文件", "txt"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (!bookListModel.contains(selectedFile)) {
                    bookListModel.addElement(selectedFile);
                    saveBookListToCache();
                }
            }
        });

        // 修改滚动面板边框
        JScrollPane scrollPane = new JScrollPane(bookList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15), // 外边框
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true) // 圆角边框
        ));
        
        // 修改按钮样式
        selectBookButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1), // 边框
            BorderFactory.createEmptyBorder(8, 25, 8, 25) // 内边距
        ));
        selectBookButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 手型光标
        selectBookButton.addChangeListener(e -> { // 添加按钮状态变化
            if (selectBookButton.getModel().isRollover()) {
                selectBookButton.setBackground(new Color(60, 110, 160));
            } else {
                selectBookButton.setBackground(new Color(70, 130, 180));
            }
        });

        // 布局调整
        add(selectBookButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void saveBookListToCache() {
        try (OutputStream out = Files.newOutputStream(Paths.get(CACHE_PATH))) {
            Properties props = new Properties();
            for (int i = 0; i < bookListModel.size(); i++) {
                props.setProperty("book." + i, bookListModel.get(i).getAbsolutePath());
            }
            props.store(out, "Book Shelf Cache");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadBookListFromCache() {
        if (!Files.exists(Paths.get(CACHE_PATH))) {
            return;
        }
        
        try (InputStream in = Files.newInputStream(Paths.get(CACHE_PATH))) {
            Properties props = new Properties();
            props.load(in);
            for (int i = 0; props.containsKey("book." + i); i++) {
                File bookFile = new File(props.getProperty("book." + i));
                if (bookFile.exists() && !bookListModel.contains(bookFile)) {
                    bookListModel.addElement(bookFile);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}