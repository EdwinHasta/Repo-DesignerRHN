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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "GRUPOSINFADICIONALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GruposInfAdicionales.findAll", query = "SELECT g FROM GruposInfAdicionales g")})
public class GruposInfAdicionales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "TIPODATO")
    private String tipodato;
    @OneToMany(mappedBy = "grupo")
    private Collection<InformacionesAdicionales> informacionesAdicionalesCollection;

    public GruposInfAdicionales() {
    }

    public GruposInfAdicionales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public GruposInfAdicionales(BigInteger secuencia, Integer codigo, String descripcion, String estado) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado.toUpperCase();
    }

    public String getTipodato() {
        return tipodato;
    }

    public void setTipodato(String tipodato) {
        this.tipodato = tipodato;
    }

    @XmlTransient
    public Collection<InformacionesAdicionales> getInformacionesAdicionalesCollection() {
        return informacionesAdicionalesCollection;
    }

    public void setInformacionesAdicionalesCollection(Collection<InformacionesAdicionales> informacionesAdicionalesCollection) {
        this.informacionesAdicionalesCollection = informacionesAdicionalesCollection;
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
        if (!(object instanceof GruposInfAdicionales)) {
            return false;
        }
        GruposInfAdicionales other = (GruposInfAdicionales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.GruposInfAdicionales[ secuencia=" + secuencia + " ]";
    }
}
