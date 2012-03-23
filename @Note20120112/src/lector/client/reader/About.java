package lector.client.reader;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.HTML;

public class About extends PopupPanel {
	public About() {
		super(true);
		setAnimationEnabled(true);
		
		HTML htmlNewHtml = new HTML("@Note is a collaborative annotation system developed under the auspices of the Google\u2019s 2010 <a href=\"http://googleblog.blogspot.com/2010/07/our-commitment-to-digital-humanities.html\" target=\"_blank\">Digital Humanities Awards program</a>. It is developed by <a href=\"http://ilsa.fdi.ucm.es/ilsa/ilsa.php\" target=\"_blank\">ILSA</a> (Research Group on Implementation of Language-Driven Software and Applications) and <a href=\"http://www.ucm.es/info/leethi/\" target=\"_blank\">LEETHI</a> Groups (Research Group on \u201CSpanish & European Literatures: from Text to Hypertext\u201D) at  <a href=\"http://www.ucm.es/\" target=\"_blank\">Complutense University</a> (Spain).\r\n\r\n@Note tool retrieves digitized works from <a href=\"http://books.google.com/\" target=\"_blank\">Google Books</a> and, in particular, from the free-access <a href=\"http://www.ucm.es/BUCM/atencion/25403.php\" target=\"_blank\">UCM-Google collection</a>. It lets the reader add annotations to enrich texts for researching and learning purposes such as reading activities, e-learning tasks, proposal of critical editions, etc.\r\n\r\n@Note promotes the collaborative creation of free-text and semantic annotation schemas on literary works by communities of researchers, teachers and students, and the use of these schemas in a very flexible and adaptative model for the definition of annotation activities.\r\n", true);
		htmlNewHtml.setStyleName("gwt-TextAreaAbout");
		setWidget(htmlNewHtml);
		htmlNewHtml.setSize("475px", "100%");
		
	}

}
