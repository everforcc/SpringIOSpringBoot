// 修改说明：
// 1. 添加书籍列表和存储结构
// 2. 重构布局支持书籍列表展示
// 3. 添加双击打开书籍功能

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
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

/**
 * 书架功能主面板
 * 核心功能：
 * 1. 管理本地书籍文件缓存
 * 2. 展示带视觉效果的书籍列表
 * 3. 支持文件选择与双击阅读
 */
public class BookShelfPanel extends JPanel {
    private static final String CACHE_PATH = System.getProperty("user.home") + "/.book_cache.properties";
    private DefaultListModel<File> bookListModel = new DefaultListModel<>();
    private JList<File> bookList = new JList<>(bookListModel);
    private JButton selectBookButton = new JButton("选择书籍文件");


    /**
     * 构造方法 - 初始化书架界面
     * 1. 加载缓存数据
     * 2. 配置UI组件样式
     * 3. 设置事件监听器
     */
    public BookShelfPanel() {
        super(new BorderLayout()); // 修改布局为BorderLayout（必须作为构造方法的第一条语句）
        loadBookListFromCache();
        setBackground(Color.WHITE);
        
        // 设置按钮样式（新增圆角、阴影效果）
        selectBookButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        selectBookButton.setForeground(Color.WHITE);
        selectBookButton.setBackground(new Color(70, 130, 180));
        selectBookButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        selectBookButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectBookButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                
                // 添加按钮渐变和阴影
                if (model.isPressed()) {
                    g2.setColor(new Color(50, 100, 160));
                } else if (model.isRollover()) {
                    g2.setColor(new Color(60, 110, 160));
                } else {
                    g2.setColor(b.getBackground());
                }
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight()/2, 25, 25);
                super.paint(g2, c);
                g2.dispose();
            }
        });

        // 书籍列表配置（增强视觉效果）
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
                    label.setForeground(Color.BLACK); // 新增：设置选中文字颜色为黑色
                    label.setBackground(new Color(50, 100, 160)); // 修改：加深背景颜色
                    label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 3, 1, 1, new Color(30, 80, 140)), // 修改：加深边框颜色
                        BorderFactory.createEmptyBorder(8, 9, 8, 12)
                    ));
                } else {
                    label.setBackground(index % 2 == 0 ? new Color(248, 248, 248) : Color.WHITE); // 斑马条纹
                }
                
                // 增强悬停效果
                if (cellHasFocus) {
                    label.setBackground(new Color(235, 245, 255));
                    label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 3, 1, 1, new Color(200, 220, 240)),
                        BorderFactory.createEmptyBorder(8, 9, 8, 12)
                    ));
                }
                
                // 新增文件类型图标
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    // 恢复早期图标加载方式
                    try {
                        InputStream iconStream = MainApp.class.getResourceAsStream("/txt_icon.png"); // 恢复：使用主应用类加载资源
                        if (iconStream != null) {
                            // 添加图片缩放逻辑（新增）
                            Image original = ImageIO.read(iconStream);
                            Image scaled = original.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                            label.setIcon(new ImageIcon(scaled));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        // 修改滚动面板样式（新增自定义滚动条）
        JScrollPane scrollPane = new JScrollPane(bookList) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 245, 245));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2d);
                g2d.dispose();
            }
        };
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200, 200, 200);
                this.trackColor = new Color(245, 245, 245);
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        // 设置面板背景渐变
        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());
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