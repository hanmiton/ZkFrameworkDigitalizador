package zkexample.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import zkexample.utilities.FieldMatch;

@Entity
@Table(name = "fileprofile")
//@NamedQuery(name = "FileProfile.findFileByFileID", query = "SELECT file  FROM FileProfile  as file WHERE file.nombre = ?")
@NamedQuery(name = "FileProfile.findFileByUserID", query = "SELECT file  FROM FileProfile  as file WHERE file.id = ?")
public class FileProfile implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Path no puede estar en blanco")
	private String nameFile;

	@NotBlank(message = "Apellido no puede estar en blanco")
	private String pathFile;
	
	private String cedula;
	private String apelpate;
	private String apelmate;
	private String nombres;
	private String nomjuri;
	private String tipocont;
	private Integer codigo_ciudad = 0;
	private String direccion;
	private String observaciones;

	private Integer system;
	
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	@Column(name = "ID")
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "NAMEFILE")
	public String getNameFile() {
		return nameFile;
	}
	
	@Column(name = "NAMEFILE")
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
	@Column(name = "PATHFILE")
	public String getPathFile() {
		return pathFile;
	}

	@Column(name = "PATHFILE")
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
	@Column(name = "SYSTEM")
	public Integer getSystem() {
		return system;
	}

	@Column(name = "SYSTEM")
	public void setSystem(Integer system) {
		this.system = system;
	}
	
	@Column(name = "CEDULA")
	public String getCedula() {
		return cedula;
	}
	
	@Column(name = "CEDULA")
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	@Column(name = "APELPATE")
	public String getApelpate() {
		return apelpate;
	}
	@Column(name = "APELPATE")
	public void setApelpate(String apelpate) {
		this.apelpate = apelpate;
	}
	@Column(name = "APELMATE")
	public String getApelmate() {
		return apelmate;
	}
	@Column(name = "APELMATE")
	public void setApelmate(String apelmate) {
		this.apelmate = apelmate;
	}
	@Column(name = "NOMBRES")
	public String getNombres() {
		return nombres;
	}
	@Column(name = "NOMBRES")
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	@Column(name = "NOMJURI")
	public String getNomjuri() {
		return nomjuri;
	}
	@Column(name = "NOMJURI")
	public void setNomjuri(String nomjuri) {
		this.nomjuri = nomjuri;
	}
	@Column(name = "TIPOCONT")
	public String getTipocont() {
		return tipocont;
	}
	@Column(name = "TIPOCONT")
	public void setTipocont(String tipocont) {
		this.tipocont = tipocont;
	}
	@Column(name = "CODIGO_CIUDAD")
	public Integer getCodigo_ciudad() {
		return codigo_ciudad;
	}
	@Column(name = "CODIGO_CIUDAD")
	public void setCod_ciudad(Integer codigo_ciudad) {
		this.codigo_ciudad = codigo_ciudad;
	}
	@Column(name = "DIRECCION")
	public String getDireccion() {
		return direccion;
	}
	@Column(name = "DIRECCION")
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@Column(name = "OBSERVACIONES")
	public String getObservaciones() {
		return observaciones;
	}
	@Column(name = "OBSERVACIONES")
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
	
}
