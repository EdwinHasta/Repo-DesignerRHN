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
@Table(name = "RETENCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retenciones.findAll", query = "SELECT r FROM Retenciones r"),
    @NamedQuery(name = "Retenciones.findBySecuencia", query = "SELECT r FROM Retenciones r WHERE r.secuencia = :secuencia"),
    @NamedQuery(name = "Retenciones.findByPorcentaje", query = "SELECT r FROM Retenciones r WHERE r.porcentaje = :porcentaje"),
    @NamedQuery(name = "Retenciones.findByValor", query = "SELECT r FROM Retenciones r WHERE r.valor = :valor"),
    @NamedQuery(name = "Retenciones.findByValormaximo", query = "SELECT r FROM Retenciones r WHERE r.valormaximo = :valormaximo"),
    @NamedQuery(name = "Retenciones.findByValorminimo", query = "SELECT r FROM Retenciones r WHERE r.valorminimo = :valorminimo"),
    @NamedQuery(name = "Retenciones.findByAdicionauvt", query = "SELECT r FROM Retenciones r WHERE r.adicionauvt = :adicionauvt")})
public class Retenciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORMAXIMO")
    private BigDecimal valormaximo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORMINIMO")
    private BigDecimal valorminimo;
    @Column(name = "ADICIONAUVT")
    private BigInteger adicionauvt;
    @JoinColumn(name = "VIGENCIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private VigenciasRetenciones vigencia;

    public Retenciones() {
    }

    public Retenciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Retenciones(BigInteger secuencia, BigDecimal porcentaje, BigDecimal valor, BigDecimal valormaximo, BigDecimal valorminimo) {
        this.secuencia = secuencia;
        this.porcentaje = porcentaje;
        this.valor = valor;
        this.valormaximo = valormaximo;
        this.valorminimo = valorminimo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValormaximo() {
        return valormaximo;
    }

    public void setValormaximo(BigDecimal valormaximo) {
        this.valormaximo = valormaximo;
    }

    public BigDecimal getValorminimo() {
        return valorminimo;
    }

    public void setValorminimo(BigDecimal valorminimo) {
        this.valorminimo = valorminimo;
    }

    public BigInteger getAdicionauvt() {
        return adicionauvt;
    }

    public void setAdicionauvt(BigInteger adicionauvt) {
        this.adicionauvt = adicionauvt;
    }

    public VigenciasRetenciones getVigencia() {
        return vigencia;
    }

    public void setVigencia(VigenciasRetenciones vigencia) {
        this.vigencia = vigencia;
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
        if (!(object instanceof Retenciones)) {
            return false;
        }
        Retenciones other = (Retenciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Retenciones[ secuencia=" + secuencia + " ]";
    }
    
}
