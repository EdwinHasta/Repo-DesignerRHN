/*
 * To change this template, choose Tools | Templates
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
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FORMULASNOVEDADES")
@NamedQueries({
    @NamedQuery(name = "FormulasNovedades.findAll", query = "SELECT f FROM FormulasNovedades f")})
public class FormulasNovedades implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 1)
    @Column(name = "SUGERIDA")
    private String sugerida;
    @Size(max = 1)
    @Column(name = "CARGUE")
    private String cargue;
    @Size(max = 1)
    @Column(name = "USAORDENFORMULACONCEPTO")
    private String usaordenformulaconcepto;
    @Size(max = 1)
    @Column(name = "GARANTIZADA")
    private String garantizada;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;

    public FormulasNovedades() {
    }

    public FormulasNovedades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getSugerida() {
        return sugerida;
    }

    public void setSugerida(String sugerida) {
        this.sugerida = sugerida;
    }

    public String getCargue() {
        return cargue;
    }

    public void setCargue(String cargue) {
        this.cargue = cargue;
    }

    public String getUsaordenformulaconcepto() {
        return usaordenformulaconcepto;
    }

    public void setUsaordenformulaconcepto(String usaordenformulaconcepto) {
        this.usaordenformulaconcepto = usaordenformulaconcepto;
    }

    public String getGarantizada() {
        return garantizada;
    }

    public void setGarantizada(String garantizada) {
        this.garantizada = garantizada;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
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
        if (!(object instanceof FormulasNovedades)) {
            return false;
        }
        FormulasNovedades other = (FormulasNovedades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formulasnovedades[ secuencia=" + secuencia + " ]";
    }
    
}
