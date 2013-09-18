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
@Table(name = "TIPOSEDUCACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposEducaciones.findAll", query = "SELECT t FROM TiposEducaciones t")})
public class TiposEducaciones implements Serializable {
    @OneToMany(mappedBy = "niveleducativo")
    private Collection<ParametrosInformes> parametrosInformesCollection;
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
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NIVELEDUCATIVO")
    private BigInteger niveleducativo;
    @OneToMany(mappedBy = "tipoeducacion")
    private Collection<AdiestramientosF> adiestramientosFCollection;
    @OneToMany(mappedBy = "tipoeducacion")
    private Collection<VigenciasFormales> vigenciasFormalesCollection;

    public TiposEducaciones() {
    }

    public TiposEducaciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposEducaciones(BigDecimal secuencia, short codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getNiveleducativo() {
        return niveleducativo;
    }

    public void setNiveleducativo(BigInteger niveleducativo) {
        this.niveleducativo = niveleducativo;
    }

    @XmlTransient
    public Collection<AdiestramientosF> getAdiestramientosFCollection() {
        return adiestramientosFCollection;
    }

    public void setAdiestramientosFCollection(Collection<AdiestramientosF> adiestramientosFCollection) {
        this.adiestramientosFCollection = adiestramientosFCollection;
    }

    @XmlTransient
    public Collection<VigenciasFormales> getVigenciasFormalesCollection() {
        return vigenciasFormalesCollection;
    }

    public void setVigenciasFormalesCollection(Collection<VigenciasFormales> vigenciasFormalesCollection) {
        this.vigenciasFormalesCollection = vigenciasFormalesCollection;
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
        if (!(object instanceof TiposEducaciones)) {
            return false;
        }
        TiposEducaciones other = (TiposEducaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposEducaciones[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }
    
}
