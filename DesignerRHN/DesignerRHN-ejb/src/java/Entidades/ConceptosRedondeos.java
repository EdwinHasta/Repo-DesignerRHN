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
@Table(name = "CONCEPTOSREDONDEOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptosRedondeos.findAll", query = "SELECT c FROM ConceptosRedondeos c")})
public class ConceptosRedondeos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "TIPOREDONDEO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposRedondeos tiporedondeo;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public ConceptosRedondeos() {
    }

    public ConceptosRedondeos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposRedondeos getTiporedondeo() {
        if (tiporedondeo == null) {
            tiporedondeo = new TiposRedondeos();
        }
        return tiporedondeo;
    }

    public void setTiporedondeo(TiposRedondeos tiporedondeo) {
        this.tiporedondeo = tiporedondeo;
    }

    public Conceptos getConcepto() {
        if (concepto == null) {
            concepto = new Conceptos();
        }
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
        if (!(object instanceof ConceptosRedondeos)) {
            return false;
        }
        ConceptosRedondeos other = (ConceptosRedondeos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ConceptosRedondeos[ secuencia=" + secuencia + " ]";
    }

}
