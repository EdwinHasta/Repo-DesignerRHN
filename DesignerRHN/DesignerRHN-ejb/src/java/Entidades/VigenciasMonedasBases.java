/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "VIGENCIASMONEDASBASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasMonedasBases.findAll", query = "SELECT v FROM VigenciasMonedasBases v"),
    @NamedQuery(name = "VigenciasMonedasBases.findBySecuencia", query = "SELECT v FROM VigenciasMonedasBases v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasMonedasBases.findByFecha", query = "SELECT v FROM VigenciasMonedasBases v WHERE v.fecha = :fecha")})
public class VigenciasMonedasBases implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "MONEDA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Monedas moneda;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;

    public VigenciasMonedasBases() {
    }

    public VigenciasMonedasBases(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasMonedasBases(BigInteger secuencia, Date fecha) {
        this.secuencia = secuencia;
        this.fecha = fecha;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Monedas getMoneda() {
        return moneda;
    }

    public void setMoneda(Monedas moneda) {
        this.moneda = moneda;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof VigenciasMonedasBases)) {
            return false;
        }
        VigenciasMonedasBases other = (VigenciasMonedasBases) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasMonedasBases[ secuencia=" + secuencia + " ]";
    }
    
}
