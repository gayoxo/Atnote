package lector.client.reader;

import lector.client.language.Language;
import lector.client.login.ActualUser;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;

public class TechnicalSpecs extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Book book;

    public TechnicalSpecs(Book book) {
        this.book = book;
        SimplePanel decoratorPanel = new SimplePanel();
        initWidget(decoratorPanel);
        decoratorPanel.setWidget(verticalPanel);
        richTextArea.setHeight("300px");
        verticalPanel.add(richTextArea);
        richTextArea.setEnabled(false);
        richTextArea.setVisible(true);
        if (ActualUser.getLanguage()!=null)setHTMLInRichTextArea();
    }

    public void setHTMLInRichTextArea() {
    	Language Lang=ActualUser.getLanguage();
        richTextArea.setHTML("<table>"
                + "<tr><td><strong>" + Lang.getID() +": </strong></td><td>" + this.book.getId() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getAuthors() +": </strong></td><td>" + this.book.getAuthor() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getPages() +": </strong></td><td>" + this.book.getPagesCount() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getPublication_Year() +": </strong></td><td>" + this.book.getPublishedYear() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getTitle() +": </strong></td><td>" + this.book.getTitle() + "</td></tr>"
                + "<tr><td><strong>" + Lang.getFront_Cover() +": </strong></td><td><img src=\"" + this.book.getTbURL() + "\"></img></td></tr>"
                + "</table>");
    }

    public void clear() {
        richTextArea.setHTML("");

    }

    public Book getBook() {
        return book;

    }

    public void setBook(Book book) {
        this.book = book;
        setHTMLInRichTextArea();
    }
}
