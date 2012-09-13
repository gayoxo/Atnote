package lector.client.reader;

import lector.share.model.TextSelector;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class SelectorPanel extends PopupPanel {

    private SimplePanel transparentPanel;
    private int xPosori;
    private int yPosori;
    private TextSelector selector;

    public SelectorPanel(int x, int y, int i, int j, int width, int height) {
        super(false);
        setStyleName("gwt-PopUpTr-new");
        setSize("0", "0");
        transparentPanel = new SimplePanel();
        transparentPanel.setStyleName("gwt-PopUpTr-new");
        xPosori = x + i;
        yPosori = y + j;
        setPopupPosition(xPosori - 3, yPosori - 3);
        setWidget(transparentPanel);
        transparentPanel.setSize("0", "0");
        selector = new TextSelector((Long) (long) x, (Long) (long) y, (Long) (long) width, (Long) (long) height);

        transparentPanel.setHeight(height + "px");
        transparentPanel.setWidth(width + "px");
    }

    public void setTamagno(int x, int y) {
        if (x - selector.getX() >= 0) {
            selector.setWidth(x - selector.getX());
        }
        if (y - selector.getY() >= 0) {
            selector.setHeight(y - selector.getY());
        }
        transparentPanel.setHeight(selector.getHeight().intValue() + "px");
        transparentPanel.setWidth(selector.getWidth().intValue() + "px");
    }

    public TextSelector getSelector() {
        return selector;
    }

    public void setSelector(TextSelector selector) {
        this.selector = selector;
    }
    
    public boolean vacio_()
    {
    return !(transparentPanel.getOffsetHeight()>2 && transparentPanel.getOffsetWidth()>2);
    }
}
