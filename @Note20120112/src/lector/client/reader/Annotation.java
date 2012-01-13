package lector.client.reader;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "annotation")
public class Annotation implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bookId;
	private Long userId;
	private String userName;
	private Boolean visibility = false;
	private Boolean updatability = false;
	private Integer pageNumber;
	@Basic
	@OneToOne(cascade = CascadeType.ALL)
	private TextSelector textSelector;
	@Basic
	private Text comment;
	@Basic
	private ArrayList<Long> fileIds;
	@Transient
	private boolean isPersisted;
	private Long readingActivity;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;
	@Basic
	private ArrayList<Long> visibilityGroupIds;
	@Basic
	private ArrayList<Long> updatableGroupIds;
	@Transient
	private boolean isEditable = false;

	public Annotation() {
		isPersisted = false;
		this.visibilityGroupIds = new ArrayList<Long>();
		this.updatableGroupIds = new ArrayList<Long>();
	}

	public Annotation(String bookId, Integer pageNumber,
			TextSelector textSelector, Text comment) {
		this();
		this.bookId = bookId;
		this.pageNumber = pageNumber;
		this.textSelector = textSelector;
		this.comment = comment;
	}

	public Annotation(String bookId, Integer pageNumber,
			TextSelector textSelector, Text comment, ArrayList<Long> fileId) {
		this.bookId = bookId;
		this.pageNumber = pageNumber;
		this.textSelector = textSelector;
		this.comment = comment;
		this.fileIds = fileId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Text getComment() {
		return comment;
	}

	public void setComment(Text comment) {
		this.comment = comment;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public TextSelector getTextSelector() {
		return textSelector;
	}

	public void setTextSelector(TextSelector textSelector) {
		this.textSelector = textSelector;
	}

	public ArrayList<Long> getFileIds() {
		return fileIds;
	}
	
	public void setFileIds(ArrayList<Long> fileIds) {
		this.fileIds = fileIds;
	}

	public boolean isIsEditable() {
		return isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isPersisted() {
		return isPersisted;
	}

	public void setPersisted(boolean isPersisted) {
		this.isPersisted = isPersisted;
	}

	public ArrayList<Long> getVisibilityGroupIds() {
		return visibilityGroupIds;
	}

	public void setVisibilityGroupIds(ArrayList<Long> visibilityGroupIds) {
		this.visibilityGroupIds = visibilityGroupIds;
	}

	public ArrayList<Long> getUpdatableGroupIds() {
		return updatableGroupIds;
	}

	public void setUpdatableGroupIds(ArrayList<Long> updatableGroupIds) {
		this.updatableGroupIds = updatableGroupIds;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getUpdatability() {
		return updatability;
	}

	public void setUpdatability(Boolean updatability) {
		this.updatability = updatability;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Long getReadingActivity() {
		return readingActivity;
	}

	public void setReadingActivity(Long readingActivity) {
		this.readingActivity = readingActivity;
	}

}
