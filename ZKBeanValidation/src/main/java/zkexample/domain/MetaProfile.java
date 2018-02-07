package zkexample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import zkexample.utilities.FieldMatch;

@Entity
@Table(name = "metaprofile")
//@NamedQuery(name = "MetaProfile.findFileByFileID", query = "SELECT file  FROM FileProfile  as file WHERE file.nombre = ?")
public class MetaProfile implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Path no puede estar en blanco")
	private String nameMeta;

	@NotBlank(message = "Apellido no puede estar en blanco")
	private String valorMeta;
	
	private String descripcion;
	/*
	@Column(name = "ID_USERPROFILE")
	private long id_userprofile;
	*/
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="id_userprofile")
	private UserProfile userprofile;
	
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	@Column(name = "ID")
	public void setId(long id) {
		this.id = id;
	}
	@Column(name = "NAMEMETA")
	public String getNameMeta() {
		return nameMeta;
	}
	@Column(name = "NAMEMETA")
	public void setNameMeta(String nameMeta) {
		this.nameMeta = nameMeta;
	}
	@Column(name = "VALORMETA")
	public String getValorMeta() {
		return valorMeta;
	}
	@Column(name = "VALORMETA")
	public void setValorMeta(String valorMeta) {
		this.valorMeta = valorMeta;
	}
	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	@Column(name = "DESCRIPCION")
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	
	
	
}

