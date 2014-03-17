/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author user
 */
@Entity
@Table(name = "TIPOSDTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDtos.findAll", query = "SELECT t FROM TiposDtos t")})
public class TiposDtos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Short codigo;
    @Size(max = 1)
    @Column(name = "IKA")
    private String ika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipodto")
    private Collection<DetallesFormasDtos> detallesFormasDtosCollection;

    public TiposDtos() {
    }

    public TiposDtos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposDtos(BigDecimal secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
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

    public String getIka() {
        return ika;
    }

    public void setIka(String ika) {
        this.ika = ika;
    }

    @XmlTransient
    public Collection<DetallesFormasDtos> getDetallesFormasDtosCollection() {
        return detallesFormasDtosCollection;
    }

    public void setDetallesFormasDtosCollection(Collection<DetallesFormasDtos> detallesFormasDtosCollection) {
        this.detallesFormasDtosCollection = detallesFormasDtosCollection;
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
        if (!(object instanceof TiposDtos)) {
            return false;
        }
        TiposDtos other = (TiposDtos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposDtos[ secuencia=" + secuencia + " ]";
    }
    
}
