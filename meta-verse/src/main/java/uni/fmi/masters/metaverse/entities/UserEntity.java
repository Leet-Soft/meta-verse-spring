package uni.fmi.masters.metaverse.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"comments"})
public class UserEntity implements Serializable{
//			  CREATE TABLE USER(
//			   ID IDENTITY PRIMARY KEY,
//			   USERNAME VARCHAR(255) NOT NULL UNIQUE,
//			   PASSWORD VARCHAR(32) NOT NULL,
//			   EMAIL VARCHAR(255) NOT NULL UNIQUE,
//			   AVATAR_LOCATION VARCHAR(255) DEFAULT 'no_image.jpg');
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="username", length = 255, nullable = false, unique = true)
	private String username;
	@Column(name="password", length = 32, nullable = false)
	private String password;
	@Column(name="email", length = 255, nullable = false, unique = true)
	private String email;
	@Column(name="avatar_location", length = 255)
	private String avatarLocation;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<CommentEntity> comments;
	
	@ManyToMany
	@JoinTable(name = "account_role", 
	joinColumns=@JoinColumn(name="account_id"),
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;
		
	public UserEntity() {	}
	
	public UserEntity(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public UserEntity(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarLocation() {
		return avatarLocation;
	}

	public void setAvatarLocation(String avatarLocation) {
		this.avatarLocation = avatarLocation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}	
		
}
