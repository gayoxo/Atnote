package lector.client.reader.annotthread;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

import javax.persistence.Table;

@Entity
@Table(name = "annotation_thread")
public class AnnotationThread implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long threadFatherId;
	private Long annotationId;
	@Basic
	private ArrayList<Long> threadIds;
	@Basic
	private Text comment;
	private Long userId;
	private String userName;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	public AnnotationThread() {
		super();
		this.threadIds = new ArrayList<Long>();

	}

	public AnnotationThread(Long threadFatherId, Long annotationId,
			ArrayList<Long> threadIds, Text comment, Long userId,
			String userName) {
		super();
		this.threadFatherId = threadFatherId;
		this.annotationId = annotationId;
		this.threadIds = threadIds;
		this.comment = comment;
		this.userId = userId;
		this.userName = userName;
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

	public Text getComment() {
		return comment;
	}

	public void setComment(Text comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getThreadFatherId() {
		return threadFatherId;
	}

	public void setThreadFatherId(Long threadFatherId) {
		this.threadFatherId = threadFatherId;
	}

	public Long getAnnotationId() {
		return annotationId;
	}

	public void setAnnotationId(Long annotationId) {
		this.annotationId = annotationId;
	}

	public ArrayList<Long> getThreadIds() {
		return threadIds;
	}

	public void setThreadIds(ArrayList<Long> threadIds) {
		this.threadIds = threadIds;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
