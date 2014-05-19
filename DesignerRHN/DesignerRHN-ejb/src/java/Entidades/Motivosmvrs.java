/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "MOTIVOSMVRS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motivosmvrs.findAll", query = "SELECT m FROM Motivosmvrs m")})
public class Motivosmvrs implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motivo")
    private Collection<Mvrs> mvrsCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motivo")
    private Collection<MVRSPersona> mvrspersonaCollection;

    public Motivosmvrs() {
    }

    public Motivosmvrs(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Motivosmvrs(BigInteger secuencia, String nombre, Integer codigo) {
        this.secuencia = secuencia;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombre() {
        if (nombre == null) {
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<MVRSPersona> getMvrspersonaCollection() {
        return mvrspersonaCollection;
    }

    public void setMvrspersonaCollection(Collection<MVRSPersona> mvrspersonaCollection) {
        this.mvrspersonaCollection = mvrspersonaCollection;
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
        if (!(object instanceof Motivosmvrs)) {
            return false;
        }
        Motivosmvrs other = (Motivosmvrs) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivosmvrs[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Mvrs> getMvrsCollection() {
        return mvrsCollection;
    }

    public void setMvrsCollection(Collection<Mvrs> mvrsCollection) {
        this.mvrsCollection = mvrsCollection;
    }
}
