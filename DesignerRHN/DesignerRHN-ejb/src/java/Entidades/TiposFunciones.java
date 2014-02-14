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
@Table(name = "TIPOSFUNCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposFunciones.findAll", query = "SELECT t FROM TiposFunciones t"),
    @NamedQuery(name = "TiposFunciones.findBySecuencia", query = "SELECT t FROM TiposFunciones t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposFunciones.findByNombreobjeto", query = "SELECT t FROM TiposFunciones t WHERE t.nombreobjeto = :nombreobjeto"),
    @NamedQuery(name = "TiposFunciones.findByFechafinal", query = "SELECT t FROM TiposFunciones t WHERE t.fechafinal = :fechafinal"),
    @NamedQuery(name = "TiposFunciones.findByFechainicial", query = "SELECT t FROM TiposFunciones t WHERE t.fechainicial = :fechainicial")})
public class TiposFunciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 250)
    @Column(name = "NOMBREOBJETO")
    private String nombreobjeto;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @JoinColumn(name = "OPERANDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Operandos operando;

    public TiposFunciones() {
    }

    public TiposFunciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposFunciones(BigInteger secuencia, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombreobjeto() {
        return nombreobjeto.toUpperCase();
    }

    public void setNombreobjeto(String nombreobjeto) {
        this.nombreobjeto = nombreobjeto;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Operandos getOperando() {
        return operando;
    }

    public void setOperando(Operandos operando) {
        this.operando = operando;
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
        if (!(object instanceof TiposFunciones)) {
            return false;
        }
        TiposFunciones other = (TiposFunciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposFunciones[ secuencia=" + secuencia + " ]";
    }
    
}
