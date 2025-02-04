package test.api.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity{

	public static enum Role {
		admin, user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	Role role;

	@OneToOne
	@JoinColumn(name = "employee_id", nullable=true)
	private Employee employee;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private List<RefreshToken> refreshTokens;

	public User(){

	}

	public User(String username, String password, Role role, Employee employee) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.employee = employee;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<RefreshToken> getRefreshTokens() {
		return refreshTokens;
	}
	public void setRefreshTokens(List<RefreshToken> refreshTokens) {
		this.refreshTokens = refreshTokens;
	}
}
