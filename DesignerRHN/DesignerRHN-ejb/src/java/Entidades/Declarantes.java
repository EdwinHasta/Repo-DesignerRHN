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
 * @author user
 */
@Entity
@Table(name = "DECLARANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Declarantes.findAll", query = "SELECT d FROM Declarantes d")})
public class Declarantes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "RETENCIONDESEADA")
    private BigInteger retenciondeseada;
    @Size(max = 1)
    @Column(name = "DECLARANTE")
    private String declarante;
    @JoinColumn(name = "RETENCIONMINIMA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private RetencionesMinimas retencionminima;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas persona;

    public Declarantes() {
    }

    public Declarantes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Declarantes(BigDecimal secuencia, Date fechainicial, Date fechafinal) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public BigInteger getRetenciondeseada() {
        return retenciondeseada;
    }

    public void setRetenciondeseada(BigInteger retenciondeseada) {
        this.retenciondeseada = retenciondeseada;
    }

    public String getDeclarante() {
        return declarante;
    }

    public void setDeclarante(String declarante) {
        this.declarante = declarante;
    }

    public RetencionesMinimas getRetencionminima() {
        return retencionminima;
    }

    public void setRetencionminima(RetencionesMinimas retencionminima) {
        this.retencionminima = retencionminima;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
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
        if (!(object instanceof Declarantes)) {
            return false;
        }
        Declarantes other = (Declarantes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Declarantes[ secuencia=" + secuencia + " ]";
    }
    
}
