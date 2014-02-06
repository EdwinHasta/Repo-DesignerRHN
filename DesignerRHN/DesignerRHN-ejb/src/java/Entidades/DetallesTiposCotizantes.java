/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "DETALLESTIPOSCOTIZANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesTiposCotizantes.findAll", query = "SELECT d FROM DetallesTiposCotizantes d")})
public class DetallesTiposCotizantes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "MINIMOSML")
    private BigInteger minimosml;
    @Column(name = "MAXIMOSML")
    private BigInteger maximosml;
    @JoinColumn(name = "TIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposEntidades tipoentidad;
    @JoinColumn(name = "TIPOCOTIZANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposCotizantes tipocotizante;

    public DetallesTiposCotizantes() {
    }

    public DetallesTiposCotizantes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getMinimosml() {
        return minimosml;
    }

    public void setMinimosml(BigInteger minimosml) {
        this.minimosml = minimosml;
    }

    public BigInteger getMaximosml() {
        return maximosml;
    }

    public void setMaximosml(BigInteger maximosml) {
        this.maximosml = maximosml;
    }

    public TiposEntidades getTipoentidad() {
        if(tipoentidad == null){
            tipoentidad = new TiposEntidades();
        }
        return tipoentidad;
    }

    public void setTipoentidad(TiposEntidades tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public TiposCotizantes getTipocotizante() {
        return tipocotizante;
    }

    public void setTipocotizante(TiposCotizantes tipocotizante) {
        this.tipocotizante = tipocotizante;
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
        if (!(object instanceof DetallesTiposCotizantes)) {
            return false;
        }
        DetallesTiposCotizantes other = (DetallesTiposCotizantes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetallesTiposCotizantes[ secuencia=" + secuencia + " ]";
    }
    
}
