package zkexample.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import zkexample.utilities.FieldMatch;

@Entity
@Table(name = "userprofile")
@NamedQuery(name = "UserProfile.findUserByUserID", query = "SELECT usr  FROM UserProfile  as usr WHERE usr.userLoginID = ?")
@FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"), })
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_userprofile")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Nombre no puede estar en blanco")
	@Size(min = 2, message = "Nombre demasiado corto")
	private String firstName;

	@NotBlank(message = "Apellido no puede estar en blanco")
	@Length(min = 2, max = 50, message = "Apellido debe tener entre 2 a 50 caracteres")
	private String lastName;

	@NotBlank(message = "Tipo Usuario no puede estar en blanco")
	private String type;

	@NotBlank(message = "Cedula no debe estar en blanco")
	private String cedula;
	//@Pattern(regexp = "^[a-zA-Z]{2}-\\d+$", message = "Invalid Account Number Number. First two letter should be Alphabets and then one hyphen and then any digits. For example AA-333, BB-44")
	
	@NotBlank(message = "Direccion Principal no debe estar en blanco")
	private String address1;

	private String address2;

	@NotBlank(message = "Ciudad no debe estar en blanco")
	private String city;

	@NotBlank(message = "Correo no debe estar en blanco")
	@org.hibernate.validator.constraints.Email(message = "Correo formato invalido")
	private String email;

	@NotBlank(message = "Username no debe estar en blanco")
	private String userLoginID;

	@NotBlank(message = "Contraseña no debe estar en blanco")
	private String password;
	
	@OneToMany(mappedBy="userprofile")
	private Set<MetaProfile> metaProfiles;

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}
	
	public Set<MetaProfile> getMetaProfiles() {
		return metaProfiles;
	}

	public void setMetaProfiles(Set<MetaProfile> metaProfiles) {
		this.metaProfiles = metaProfiles;
	}

	@Column(name = "TYPE")
	public void setType(String type) {
		this.type = type;
	}

	@Transient
	private String confirmPassword;

	private Integer system;

	@NotBlank(message = "Tema no debe estar en blanco")
	private String theme;

	@Column(name = "userPhoto")
	@Lob
	private byte[] userPhoto;

	@Temporal(TemporalType.DATE)
	@Past(message = "Fecha cumpleaños debe ser pasado")
	private Date DOB;
	
	@Column(name = "ID_USERPROFILE")
	public long getId() {
		return id;
	}
	
	@Column(name = "ID_USERPROFILE")
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstName() {
		return firstName;
	}

	@Column(name = "FIRSTNAME")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LASTNAME")
	public String getLastName() {
		return lastName;
	}

	@Column(name = "LASTNAME")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "DOB")
	public Date getDOB() {
		return DOB;
	}
	
	@Column(name = "DOB")
	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	@Column(name = "ADDRESS1")
	public String getAddress1() {
		return address1;
	}

	@Column(name = "ADDRESS1")
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2")
	public String getAddress2() {
		return address2;
	}

	@Column(name = "ADDRESS2")
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	@Column(name = "CITY")
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	@Column(name = "EMAIL")
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "USERLOGINID")
	public String getUserLoginID() {
		return userLoginID;
	}
	
	@Column(name = "USERLOGINID")
	public void setUserLoginID(String userLoginID) {
		this.userLoginID = userLoginID;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	@Column(name = "PASSWORD")
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "SYSTEM")
	public Integer getSystem() {
		return system;
	}

	@Column(name = "SYSTEM")
	public void setSystem(Integer system) {
		this.system = system;
	}

	@Column(name = "THEME")
	public String getTheme() {
		return theme;
	}

	@Column(name = "THEME")
	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "USERPHOTO")
	public byte[] getUserPhoto() {
		return userPhoto;
	}

	@Column(name = "USERPHOTO")
	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}
	
	
	@Column(name = "CEDULA")
	public String getCedula() {
		return cedula;
	}
	
	@Column(name = "CEDULA")
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
