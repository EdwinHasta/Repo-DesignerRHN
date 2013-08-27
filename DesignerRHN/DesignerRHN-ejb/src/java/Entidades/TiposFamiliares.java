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
@Table(name = "TIPOSFAMILIARES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposFamiliares.findAll", query = "SELECT t FROM TiposFamiliares t")})
public class TiposFamiliares implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Column(name = "CODIGOALTERNATIVO")
    private Short codigoalternativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipofamiliar")
    private Collection<Familiares> familiaresCollection;
    @OneToMany(mappedBy = "parentesco")
    private Collection<HvReferencias> hvReferenciasCollection;

    public TiposFamiliares() {
    }

    public TiposFamiliares(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposFamiliares(BigDecimal secuencia, String tipo, short codigo) {
        this.secuencia = secuencia;
        this.tipo = tipo;
        this.codigo = codigo;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public Short getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(Short codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    @XmlTransient
    public Collection<Familiares> getFamiliaresCollection() {
        return familiaresCollection;
    }

    public void setFamiliaresCollection(Collection<Familiares> familiaresCollection) {
        this.familiaresCollection = familiaresCollection;
    }

    @XmlTransient
    public Collection<HvReferencias> getHvReferenciasCollection() {
        return hvReferenciasCollection;
    }

    public void setHvReferenciasCollection(Collection<HvReferencias> hvReferenciasCollection) {
        this.hvReferenciasCollection = hvReferenciasCollection;
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
        if (!(object instanceof TiposFamiliares)) {
            return false;
        }
        TiposFamiliares other = (TiposFamiliares) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposFamiliares[ secuencia=" + secuencia + " ]";
    }
    
}
