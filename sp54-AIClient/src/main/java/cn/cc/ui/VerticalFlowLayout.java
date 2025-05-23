package cn.cc.ui;

import java.awt.*;

public class VerticalFlowLayout extends FlowLayout {
    private boolean vgap;
    private boolean hgap;
    private boolean vfill;
    private boolean hfill;

    public VerticalFlowLayout(int align, boolean hfill, boolean vfill) {
        super(align);
        this.hgap = true;
        this.vgap = true;
        this.hfill = hfill;
        this.vfill = vfill;
    }

    public VerticalFlowLayout(int align, int hgap, int vgap, boolean hfill, boolean vfill) {
        super(align, hgap, vgap);
        this.hgap = hgap > 0;
        this.vgap = vgap > 0;
        this.hfill = hfill;
        this.vfill = vfill;
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        boolean firstVisibleComponent = true;

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                dim.width = Math.max(dim.width, d.width);
                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.height += vgap ? getVgap() : 0;
                }
                dim.height += d.height;
            }
        }

        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + (hgap ? getHgap() * 2 : 0);
        dim.height += insets.top + insets.bottom + (vgap ? getVgap() * 2 : 0);
        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        boolean firstVisibleComponent = true;

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getMinimumSize();
                dim.width = Math.max(dim.width, d.width);
                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.height += vgap ? getVgap() : 0;
                }
                dim.height += d.height;
            }
        }

        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + (hgap ? getHgap() * 2 : 0);
        dim.height += insets.top + insets.bottom + (vgap ? getVgap() * 2 : 0);
        return dim;
    }

    @Override
    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        int maxwidth = target.getWidth() - (insets.left + insets.right + (hgap ? getHgap() * 2 : 0));
        int maxheight = target.getHeight() - (insets.top + insets.bottom + (vgap ? getVgap() * 2 : 0));
        int nmembers = target.getComponentCount();
        int x = insets.left + (hgap ? getHgap() : 0);
        int y = insets.top + (vgap ? getVgap() : 0);
        int roww = 0;
        int start = 0;

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                if (hfill) {
                    d.width = maxwidth;
                }
                m.setSize(d.width, d.height);

                if (roww == 0) {
                    roww = d.width;
                }

                if (vfill) {
                    d.height = maxheight;
                }

                m.setBounds(x, y, d.width, d.height);
                y += vgap ? getVgap() + d.height : d.height;
            }
        }
    }
}