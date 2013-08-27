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
 * @author user
 */
@Entity
@Table(name = "ASOCIACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asociaciones.findAll", query = "SELECT a FROM Asociaciones a"),
    @NamedQuery(name = "Asociaciones.findBySecuencia", query = "SELECT a FROM Asociaciones a WHERE a.secuencia = :secuencia"),
    @NamedQuery(name = "Asociaciones.findByCodigo", query = "SELECT a FROM Asociaciones a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Asociaciones.findByDescripcion", query = "SELECT a FROM Asociaciones a WHERE a.descripcion = :descripcion")})
public class Asociaciones implements Serializable {
    @OneToMany(mappedBy = "asociacion")
    private Collection<ParametrosInformes> parametrosinformesCollection;
   
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @JoinColumn(name = "TIPOASOCIACION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Tiposasociaciones tipoasociacion;

    public Asociaciones() {
    }

    public Asociaciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Asociaciones(BigDecimal secuencia, BigInteger codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if(descripcion == null){
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosinformesCollection() {
        return parametrosinformesCollection;
    }

    public void setParametrosinformesCollection(Collection<ParametrosInformes> parametrosinformesCollection) {
        this.parametrosinformesCollection = parametrosinformesCollection;
    }

    public Tiposasociaciones getTipoasociacion() {
        return tipoasociacion;
    }

    public void setTipoasociacion(Tiposasociaciones tipoasociacion) {
        this.tipoasociacion = tipoasociacion;
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
        if (!(object instanceof Asociaciones)) {
            return false;
        }
        Asociaciones other = (Asociaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Asociaciones[ secuencia=" + secuencia + " ]";
    }

    

    
    
}
