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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "FORMULASPROCESOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormulasProcesos.findAll", query = "SELECT f FROM FormulasProcesos f"),
    @NamedQuery(name = "FormulasProcesos.findBySecuencia", query = "SELECT f FROM FormulasProcesos f WHERE f.secuencia = :secuencia"),
    @NamedQuery(name = "FormulasProcesos.findByPeriodicidadindependiente", query = "SELECT f FROM FormulasProcesos f WHERE f.periodicidadindependiente = :periodicidadindependiente")})
public class FormulasProcesos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 1)
    @Column(name = "PERIODICIDADINDEPENDIENTE")
    private String periodicidadindependiente;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Procesos proceso;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @Transient
    private boolean checkPeriodicidad;

    public FormulasProcesos() {
    }

    public FormulasProcesos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getPeriodicidadindependiente() {
        if (periodicidadindependiente == null) {
            periodicidadindependiente = "N";
        }
        return periodicidadindependiente;
    }

    public void setPeriodicidadindependiente(String periodicidadindependiente) {
        this.periodicidadindependiente = periodicidadindependiente;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public boolean isCheckPeriodicidad() {
        if (periodicidadindependiente == null || periodicidadindependiente.equalsIgnoreCase("N")) {
            checkPeriodicidad = false;
        } else {
            checkPeriodicidad = true;
        }
        return checkPeriodicidad;
    }

    public void setCheckPeriodicidad(boolean checkPeriodicidad) {
        if (checkPeriodicidad == false) {
            periodicidadindependiente = "N";
        } else {
            periodicidadindependiente = "S";
        }
        this.checkPeriodicidad = checkPeriodicidad;
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
        if (!(object instanceof FormulasProcesos)) {
            return false;
        }
        FormulasProcesos other = (FormulasProcesos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FormulasProcesos[ secuencia=" + secuencia + " ]";
    }

}
