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
@Table(name = "TIPOSCOTIZANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposCotizantes.findAll", query = "SELECT t FROM TiposCotizantes t")})
public class TiposCotizantes implements Serializable {

    @OneToMany(mappedBy = "tipocotizante")
    private Collection<Contratos> contratosCollection;
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
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "COTIZAPENSION")
    private String cotizapension;
    @Size(max = 1)
    @Column(name = "COTIZASALUD")
    private String cotizasalud;
    @Size(max = 1)
    @Column(name = "COTIZARIESGO")
    private String cotizariesgo;
    @Size(max = 1)
    @Column(name = "COTIZAPARAFISCAL")
    private String cotizaparafiscal;
    @Size(max = 1)
    @Column(name = "COTIZAESAP")
    private String cotizaesap;
    @Size(max = 1)
    @Column(name = "COTIZAMEN")
    private String cotizamen;
    @Column(name = "CODIGOALTERNATIVO")
    private BigInteger codigoalternativo;
    @Column(name = "SUBTIPOCOTIZANTE")
    private Short subtipocotizante;
    @Size(max = 1)
    @Column(name = "EXTRANJERO")
    private String extranjero;
    @OneToMany(mappedBy = "tipocotizante")
    private Collection<TiposTrabajadores> tipostrabajadoresCollection;

    public TiposCotizantes() {
    }

    public TiposCotizantes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposCotizantes(BigDecimal secuencia, BigInteger codigo, String descripcion) {
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
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCotizapension() {
        return cotizapension;
    }

    public void setCotizapension(String cotizapension) {
        this.cotizapension = cotizapension;
    }

    public String getCotizasalud() {
        return cotizasalud;
    }

    public void setCotizasalud(String cotizasalud) {
        this.cotizasalud = cotizasalud;
    }

    public String getCotizariesgo() {
        return cotizariesgo;
    }

    public void setCotizariesgo(String cotizariesgo) {
        this.cotizariesgo = cotizariesgo;
    }

    public String getCotizaparafiscal() {
        return cotizaparafiscal;
    }

    public void setCotizaparafiscal(String cotizaparafiscal) {
        this.cotizaparafiscal = cotizaparafiscal;
    }

    public String getCotizaesap() {
        return cotizaesap;
    }

    public void setCotizaesap(String cotizaesap) {
        this.cotizaesap = cotizaesap;
    }

    public String getCotizamen() {
        return cotizamen;
    }

    public void setCotizamen(String cotizamen) {
        this.cotizamen = cotizamen;
    }

    public BigInteger getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(BigInteger codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public Short getSubtipocotizante() {
        return subtipocotizante;
    }

    public void setSubtipocotizante(Short subtipocotizante) {
        this.subtipocotizante = subtipocotizante;
    }

    public String getExtranjero() {
        return extranjero;
    }

    public void setExtranjero(String extranjero) {
        this.extranjero = extranjero;
    }

    @XmlTransient
    public Collection<TiposTrabajadores> getTipostrabajadoresCollection() {
        return tipostrabajadoresCollection;
    }

    public void setTipostrabajadoresCollection(Collection<TiposTrabajadores> tipostrabajadoresCollection) {
        this.tipostrabajadoresCollection = tipostrabajadoresCollection;
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
        if (!(object instanceof TiposCotizantes)) {
            return false;
        }
        TiposCotizantes other = (TiposCotizantes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposcotizantes[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Contratos> getContratosCollection() {
        return contratosCollection;
    }

    public void setContratosCollection(Collection<Contratos> contratosCollection) {
        this.contratosCollection = contratosCollection;
    }
}
