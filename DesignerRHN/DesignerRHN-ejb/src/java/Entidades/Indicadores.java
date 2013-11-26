/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
 * @author Administrator
 */
@Entity
@Table(name = "INDICADORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indicadores.findAll", query = "SELECT i FROM Indicadores i")})
public class Indicadores implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicador")
    private Collection<VigenciasIndicadores> vigenciasIndicadoresCollection;
    @JoinColumn(name = "TIPOINDICADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposIndicadores tipoindicador;

    public Indicadores() {
    }

    public Indicadores(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Indicadores(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
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
    public Collection<VigenciasIndicadores> getVigenciasIndicadoresCollection() {
        return vigenciasIndicadoresCollection;
    }

    public void setVigenciasIndicadoresCollection(Collection<VigenciasIndicadores> vigenciasIndicadoresCollection) {
        this.vigenciasIndicadoresCollection = vigenciasIndicadoresCollection;
    }

    public TiposIndicadores getTipoindicador() {
        return tipoindicador;
    }

    public void setTipoindicador(TiposIndicadores tipoindicador) {
        this.tipoindicador = tipoindicador;
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
        if (!(object instanceof Indicadores)) {
            return false;
        }
        Indicadores other = (Indicadores) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Indicadores[ secuencia=" + secuencia + " ]";
    }
    
}
