/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASCONCEPTOSTT")
@NamedQueries({
    @NamedQuery(name = "VigenciasConceptosTT.findAll", query = "SELECT v FROM VigenciasConceptosTT v")})
public class VigenciasConceptosTT implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 1)
    @Column(name = "UNICO")
    private String unico;
    @Basic(optional = false)
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private TiposTrabajadores tipotrabajador;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public VigenciasConceptosTT() {
    }

    public VigenciasConceptosTT(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasConceptosTT(BigInteger secuencia, Date fechainicial, Date fechafinal) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getUnico() {
        return unico;
    }

    public void setUnico(String unico) {
        this.unico = unico;
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

    public TiposTrabajadores getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(TiposTrabajadores tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
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
        if (!(object instanceof VigenciasConceptosTT)) {
            return false;
        }
        VigenciasConceptosTT other = (VigenciasConceptosTT) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciasconceptostt[ secuencia=" + secuencia + " ]";
    }
    
}
