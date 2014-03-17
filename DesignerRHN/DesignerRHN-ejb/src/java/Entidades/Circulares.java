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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "CIRCULARES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Circulares.findAll", query = "SELECT c FROM Circulares c"),
    @NamedQuery(name = "Circulares.findBySecuencia", query = "SELECT c FROM Circulares c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "Circulares.findByFecha", query = "SELECT c FROM Circulares c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Circulares.findByExpedidopor", query = "SELECT c FROM Circulares c WHERE c.expedidopor = :expedidopor"),
    @NamedQuery(name = "Circulares.findByTexto", query = "SELECT c FROM Circulares c WHERE c.texto = :texto")})
public class Circulares implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 40)
    @Column(name = "EXPEDIDOPOR")
    private String expedidopor;
    @Size(max = 2000)
    @Column(name = "TEXTO")
    private String texto;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;

    public Circulares() {
    }

    public Circulares(BigInteger secuencia) {
        this.secuencia = secuencia;
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

    public String getExpedidopor() {
        return expedidopor;
    }

    public void setExpedidopor(String expedidopor) {
        this.expedidopor = expedidopor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
        if (!(object instanceof Circulares)) {
            return false;
        }
        Circulares other = (Circulares) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Circulares[ secuencia=" + secuencia + " ]";
    }
    
}
