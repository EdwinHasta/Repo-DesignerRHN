/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "EERSDETALLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EersDetalles.findAll", query = "SELECT e FROM EersDetalles e"),
    @NamedQuery(name = "EersDetalles.findBySecuencia", query = "SELECT e FROM EersDetalles e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "EersDetalles.findByValor", query = "SELECT e FROM EersDetalles e WHERE e.valor = :valor"),
    @NamedQuery(name = "EersDetalles.findByObservaciones", query = "SELECT e FROM EersDetalles e WHERE e.observaciones = :observaciones"),
    @NamedQuery(name = "EersDetalles.findByEstado", query = "SELECT e FROM EersDetalles e WHERE e.estado = :estado"),
    @NamedQuery(name = "EersDetalles.findBySaldo", query = "SELECT e FROM EersDetalles e WHERE e.saldo = :saldo"),
    @NamedQuery(name = "EersDetalles.findByFecha", query = "SELECT e FROM EersDetalles e WHERE e.fecha = :fecha")})
public class EersDetalles implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigInteger valor;
    @Size(max = 50)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "SALDO")
    private BigInteger saldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros tercero;
    @JoinColumn(name = "EERCABECERA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private EersCabeceras eercabecera;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public EersDetalles() {
    }

    public EersDetalles(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EersDetalles(BigInteger secuencia, BigInteger valor, Date fecha) {
        this.secuencia = secuencia;
        this.valor = valor;
        this.fecha = fecha;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getSaldo() {
        return saldo;
    }

    public void setSaldo(BigInteger saldo) {
        this.saldo = saldo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Terceros getTercero() {
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    public EersCabeceras getEercabecera() {
        return eercabecera;
    }

    public void setEercabecera(EersCabeceras eercabecera) {
        this.eercabecera = eercabecera;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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
        if (!(object instanceof EersDetalles)) {
            return false;
        }
        EersDetalles other = (EersDetalles) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EersDetalles[ secuencia=" + secuencia + " ]";
    }
    
}
