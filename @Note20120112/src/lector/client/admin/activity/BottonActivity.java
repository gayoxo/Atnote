package lector.client.admin.activity;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BottonActivity extends Button {

	private ReadingActivity readingActivity;
	private VerticalPanel Actual;
	private VerticalPanel Normal;
	private VerticalPanel Selected;

	public BottonActivity(VerticalPanel Normalin, VerticalPanel Selectedin,
			ReadingActivity readingActivityIn) {
		super(readingActivityIn.getName());
		this.readingActivity = readingActivityIn;
		Normal = Normalin;
		Selected = Selectedin;
		Actual = Normal;
		Actual.add(this);
	}

	public ReadingActivity getReadingActivity() {
		return readingActivity;
	}

	public void setReadingActivity(ReadingActivity readingActivity) {
		this.readingActivity = readingActivity;
	}

	public VerticalPanel getSelected() {
		return Selected;
	}

	public void setSelected(VerticalPanel selected) {
		Selected = selected;
	}

	public void swap() {
		if (Actual == Normal) {
			Actual.remove(this);
			Actual = Selected;
			Selected.add(this);
		} else if (Actual == Selected) {
			Selected.remove(this);
			Actual = Normal;
			Normal.add(this);
		}
	}

}
