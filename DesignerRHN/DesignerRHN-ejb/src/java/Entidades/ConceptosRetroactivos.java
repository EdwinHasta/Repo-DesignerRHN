/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "CONCEPTOSRETROACTIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptosRetroactivos.findAll", query = "SELECT c FROM ConceptosRetroactivos c"),
    @NamedQuery(name = "ConceptosRetroactivos.findBySecuencia", query = "SELECT c FROM ConceptosRetroactivos c WHERE c.secuencia = :secuencia")})
public class ConceptosRetroactivos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    private Conceptos concepto;
    @JoinColumn(name = "CONCEPTORETRO", referencedColumnName = "SECUENCIA")
    private Conceptos conceptoRetroActivo;

    public ConceptosRetroactivos() {
    }

    public ConceptosRetroactivos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public Conceptos getConceptoRetroActivo() {
        return conceptoRetroActivo;
    }

    public void setConceptoRetroActivo(Conceptos conceptoRetroActivo) {
        this.conceptoRetroActivo = conceptoRetroActivo;
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
        if (!(object instanceof ConceptosRetroactivos)) {
            return false;
        }
        ConceptosRetroactivos other = (ConceptosRetroactivos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ConceptosRetroactivos[ secuencia=" + secuencia + " ]";
    }

}
