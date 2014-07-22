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
@Table(name = "CONCEPTOSPROYECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptosProyecciones.findAll", query = "SELECT c FROM ConceptosProyecciones c"),
    @NamedQuery(name = "ConceptosProyecciones.findByConcepto", query = "SELECT c FROM ConceptosProyecciones c WHERE c.concepto = :concepto"),
    @NamedQuery(name = "ConceptosProyecciones.findByPorcentajeproyeccion", query = "SELECT c FROM ConceptosProyecciones c WHERE c.porcentajeproyeccion = :porcentajeproyeccion"),
    @NamedQuery(name = "ConceptosProyecciones.findBySecuencia", query = "SELECT c FROM ConceptosProyecciones c WHERE c.secuencia = :secuencia")})
public class ConceptosProyecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    private Conceptos concepto;
    @Column(name = "PORCENTAJEPROYECCION")
    private Short porcentajeproyeccion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;

    public ConceptosProyecciones() {
    }

    public ConceptosProyecciones(BigInteger secuencia, Conceptos concepto) {
        this.secuencia = secuencia;
        this.concepto = concepto;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public Short getPorcentajeproyeccion() {
        return porcentajeproyeccion;
    }

    public void setPorcentajeproyeccion(Short porcentajeproyeccion) {
        this.porcentajeproyeccion = porcentajeproyeccion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (concepto != null ? concepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptosProyecciones)) {
            return false;
        }
        ConceptosProyecciones other = (ConceptosProyecciones) object;
        if ((this.concepto == null && other.concepto != null) || (this.concepto != null && !this.concepto.equals(other.concepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ConceptosProyecciones[ concepto=" + concepto + " ]";
    }

}
