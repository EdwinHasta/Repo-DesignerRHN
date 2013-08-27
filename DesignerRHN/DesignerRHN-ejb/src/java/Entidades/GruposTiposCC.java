/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "GRUPOSTIPOSCC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GruposTiposCC.findAll", query = "SELECT g FROM GruposTiposCC g")})
public class GruposTiposCC implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupotipocc")
    private Collection<Rubrospresupuestales> rubrospresupuestalesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Short codigo;
    @OneToMany(mappedBy = "grupotipocc")
    private Collection<TiposCentrosCostos> tiposcentroscostosCollection;

    public GruposTiposCC() {
    }

    public GruposTiposCC(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<TiposCentrosCostos> getTiposcentroscostosCollection() {
        return tiposcentroscostosCollection;
    }

    public void setTiposcentroscostosCollection(Collection<TiposCentrosCostos> tiposcentroscostosCollection) {
        this.tiposcentroscostosCollection = tiposcentroscostosCollection;
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
        if (!(object instanceof GruposTiposCC)) {
            return false;
        }
        GruposTiposCC other = (GruposTiposCC) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Grupostiposcc[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Rubrospresupuestales> getRubrospresupuestalesCollection() {
        return rubrospresupuestalesCollection;
    }

    public void setRubrospresupuestalesCollection(Collection<Rubrospresupuestales> rubrospresupuestalesCollection) {
        this.rubrospresupuestalesCollection = rubrospresupuestalesCollection;
    }
    
}
