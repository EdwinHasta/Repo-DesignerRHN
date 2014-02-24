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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "DETALLESCARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesCargos.findAll", query = "SELECT d FROM DetallesCargos d"),
    @NamedQuery(name = "DetallesCargos.findBySecuencia", query = "SELECT d FROM DetallesCargos d WHERE d.secuencia = :secuencia"),
    @NamedQuery(name = "DetallesCargos.findByOrden", query = "SELECT d FROM DetallesCargos d WHERE d.orden = :orden"),
    @NamedQuery(name = "DetallesCargos.findByPalabraclave", query = "SELECT d FROM DetallesCargos d WHERE d.palabraclave = :palabraclave"),
    @NamedQuery(name = "DetallesCargos.findByDescripcion", query = "SELECT d FROM DetallesCargos d WHERE d.descripcion = :descripcion")})
public class DetallesCargos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 10)
    @Column(name = "ORDEN")
    private String orden;
    @Size(max = 20)
    @Column(name = "PALABRACLAVE")
    private String palabraclave;
    @Size(max = 4000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "TIPODETALLE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposDetalles tipodetalle;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;

    public DetallesCargos() {
    }

    public DetallesCargos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getPalabraclave() {
        return palabraclave;
    }

    public void setPalabraclave(String palabraclave) {
        this.palabraclave = palabraclave;
    }

    public String getDescripcion() {
        if(descripcion == null){
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public TiposDetalles getTipodetalle() {
        return tipodetalle;
    }

    public void setTipodetalle(TiposDetalles tipodetalle) {
        this.tipodetalle = tipodetalle;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof DetallesCargos)) {
            return false;
        }
        DetallesCargos other = (DetallesCargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetallesCargos[ secuencia=" + secuencia + " ]";
    }
    
}
