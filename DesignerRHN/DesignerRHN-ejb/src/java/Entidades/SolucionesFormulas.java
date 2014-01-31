/*
 * To change this template, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "SOLUCIONESFORMULAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolucionesFormulas.findAll", query = "SELECT s FROM SolucionesFormulas s")})
public class SolucionesFormulas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "SOLUCIONNODO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private SolucionesNodos solucionnodo;
    @JoinColumn(name = "PROCESODEPENDIENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private ProcesosDependientes procesodependiente;
    @JoinColumn(name = "NOVEDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Novedades novedad;
    @JoinColumn(name = "FORMULADEPENDIENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private FormulasDependientes formuladependiente;
    @JoinColumn(name = "FORMULACONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private FormulasConceptos formulaconcepto;

    public SolucionesFormulas() {
    }

    public SolucionesFormulas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public SolucionesNodos getSolucionnodo() {
        return solucionnodo;
    }

    public void setSolucionnodo(SolucionesNodos solucionnodo) {
        this.solucionnodo = solucionnodo;
    }

    public ProcesosDependientes getProcesodependiente() {
        return procesodependiente;
    }

    public void setProcesodependiente(ProcesosDependientes procesodependiente) {
        this.procesodependiente = procesodependiente;
    }

    public Novedades getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedades novedad) {
        this.novedad = novedad;
    }

    public FormulasDependientes getFormuladependiente() {
        return formuladependiente;
    }

    public void setFormuladependiente(FormulasDependientes formuladependiente) {
        this.formuladependiente = formuladependiente;
    }

    public FormulasConceptos getFormulaconcepto() {
        return formulaconcepto;
    }

    public void setFormulaconcepto(FormulasConceptos formulaconcepto) {
        this.formulaconcepto = formulaconcepto;
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
        if (!(object instanceof SolucionesFormulas)) {
            return false;
        }
        SolucionesFormulas other = (SolucionesFormulas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SolucionesFormulas[ secuencia=" + secuencia + " ]";
    }
    
}
