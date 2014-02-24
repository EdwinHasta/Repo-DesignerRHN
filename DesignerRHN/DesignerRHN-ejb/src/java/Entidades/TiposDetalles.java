/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author PROYECTO01
 */
@Entity
@Table(name = "TIPOSDETALLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDetalles.findAll", query = "SELECT t FROM TiposDetalles t"),
    @NamedQuery(name = "TiposDetalles.findBySecuencia", query = "SELECT t FROM TiposDetalles t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposDetalles.findByCodigo", query = "SELECT t FROM TiposDetalles t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TiposDetalles.findByDescripcion", query = "SELECT t FROM TiposDetalles t WHERE t.descripcion = :descripcion")})
public class TiposDetalles implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    //@Size(max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ENFOQUE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Enfoques enfoque;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipodetalle")
    private Collection<DetallesCargos> detallesCargosCollection;

    public TiposDetalles() {
    }

    public TiposDetalles(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Enfoques getEnfoque() {
        return enfoque;
    }

    public void setEnfoque(Enfoques enfoque) {
        this.enfoque = enfoque;
    }

    @XmlTransient
    public Collection<DetallesCargos> getDetallesCargosCollection() {
        return detallesCargosCollection;
    }

    public void setDetallesCargosCollection(Collection<DetallesCargos> detallesCargosCollection) {
        this.detallesCargosCollection = detallesCargosCollection;
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
        if (!(object instanceof TiposDetalles)) {
            return false;
        }
        TiposDetalles other = (TiposDetalles) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposDetalles[ secuencia=" + secuencia + " ]";
    }
    
}
