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
@Table(name = "TIPOSINDICADORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposIndicadores.findAll", query = "SELECT t FROM TiposIndicadores t")})
public class TiposIndicadores implements Serializable {
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
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "tipoindicador")
    private Collection<VigenciasIndicadores> vigenciasIndicadoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoindicador")
    private Collection<Indicadores> indicadoresCollection;

    public TiposIndicadores() {
    }

    public TiposIndicadores(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposIndicadores(BigDecimal secuencia, short codigo, String descripcion) {
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

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<VigenciasIndicadores> getVigenciasIndicadoresCollection() {
        return vigenciasIndicadoresCollection;
    }

    public void setVigenciasIndicadoresCollection(Collection<VigenciasIndicadores> vigenciasIndicadoresCollection) {
        this.vigenciasIndicadoresCollection = vigenciasIndicadoresCollection;
    }

    @XmlTransient
    public Collection<Indicadores> getIndicadoresCollection() {
        return indicadoresCollection;
    }

    public void setIndicadoresCollection(Collection<Indicadores> indicadoresCollection) {
        this.indicadoresCollection = indicadoresCollection;
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
        if (!(object instanceof TiposIndicadores)) {
            return false;
        }
        TiposIndicadores other = (TiposIndicadores) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposIndicadores[ secuencia=" + secuencia + " ]";
    }
    
}
