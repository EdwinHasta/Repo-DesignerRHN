/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "OBJETOSDB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjetosDB.findAll", query = "SELECT o FROM ObjetosDB o")})
public class ObjetosDB implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 80)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 30)
    @Column(name = "CLASIFICACION")
    private String clasificacion;
    @Size(max = 1)
    @Column(name = "EXCLUIRASIGNACIONPERFIL")
    private String excluirasignacionperfil;
    @Size(max = 1)
    @Column(name = "AUTORIZADA")
    private String autorizada;
    @OneToMany(mappedBy = "objeto")
    private Collection<RastrosTablas> rastrosTablasCollection;
    @JoinColumn(name = "MODULO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Modulos modulo;

    public ObjetosDB() {
    }

    public ObjetosDB(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ObjetosDB(BigInteger secuencia, String tipo, String nombre) {
        this.secuencia = secuencia;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getExcluirasignacionperfil() {
        return excluirasignacionperfil;
    }

    public void setExcluirasignacionperfil(String excluirasignacionperfil) {
        this.excluirasignacionperfil = excluirasignacionperfil;
    }

    public String getAutorizada() {
        return autorizada;
    }

    public void setAutorizada(String autorizada) {
        this.autorizada = autorizada;
    }

    @XmlTransient
    public Collection<RastrosTablas> getRastrosTablasCollection() {
        return rastrosTablasCollection;
    }

    public void setRastrosTablasCollection(Collection<RastrosTablas> rastrosTablasCollection) {
        this.rastrosTablasCollection = rastrosTablasCollection;
    }

    public Modulos getModulo() {
        return modulo;
    }

    public void setModulo(Modulos modulo) {
        this.modulo = modulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetosDB)) {
            return false;
        }
        ObjetosDB other = (ObjetosDB) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ObjetosDB[ secuencia=" + secuencia + " ]";
    }
    
}
