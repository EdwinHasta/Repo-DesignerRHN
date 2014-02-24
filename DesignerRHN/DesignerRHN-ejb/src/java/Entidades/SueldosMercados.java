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
 * @author PROYECTO01
 */
@Entity
@Table(name = "SUELDOSMERCADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SueldosMercados.findAll", query = "SELECT s FROM SueldosMercados s"),
    @NamedQuery(name = "SueldosMercados.findBySecuencia", query = "SELECT s FROM SueldosMercados s WHERE s.secuencia = :secuencia"),
    @NamedQuery(name = "SueldosMercados.findBySueldomin", query = "SELECT s FROM SueldosMercados s WHERE s.sueldomin = :sueldomin"),
    @NamedQuery(name = "SueldosMercados.findBySueldomax", query = "SELECT s FROM SueldosMercados s WHERE s.sueldomax = :sueldomax")})
public class SueldosMercados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "SUELDOMIN")
    private BigDecimal sueldomin;
    @Column(name = "SUELDOMAX")
    private BigDecimal sueldomax;
    @JoinColumn(name = "TIPOEMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposEmpresas tipoempresa;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;

    public SueldosMercados() {
    }

    public SueldosMercados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSueldomin() {
        return sueldomin;
    }

    public void setSueldomin(BigDecimal sueldomin) {
        this.sueldomin = sueldomin;
    }

    public BigDecimal getSueldomax() {
        return sueldomax;
    }

    public void setSueldomax(BigDecimal sueldomax) {
        this.sueldomax = sueldomax;
    }

    public TiposEmpresas getTipoempresa() {
        return tipoempresa;
    }

    public void setTipoempresa(TiposEmpresas tipoempresa) {
        this.tipoempresa = tipoempresa;
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
        if (!(object instanceof SueldosMercados)) {
            return false;
        }
        SueldosMercados other = (SueldosMercados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SueldosMercados[ secuencia=" + secuencia + " ]";
    }
    
}
