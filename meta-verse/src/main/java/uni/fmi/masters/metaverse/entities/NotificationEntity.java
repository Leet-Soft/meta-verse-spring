package uni.fmi.masters.metaverse.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notifaction")
public class NotificationEntity implements Serializable{
	public static final String STATUS_REQUESTED = "requested";
	public static final String STATUS_DENIED = "denied";
	public static final String STATUS_ACCEPTED = "accepted";
	public static final String STATUS_REMOVED = "removed";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity fromUser;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity toUser;
	
	@Column(name = "created_at")
	private Date date;
	
	@Column(name = "comment", length = 255)
	private String comment;
	
	
	//1.requested
	//2.denied
	//3.accepted
	//4.removed
	@Column(name = "status", length = 30)
	private String status;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserEntity getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserEntity fromUser) {
		this.fromUser = fromUser;
	}

	public UserEntity getToUser() {
		return toUser;
	}

	public void setToUser(UserEntity toUser) {
		this.toUser = toUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
