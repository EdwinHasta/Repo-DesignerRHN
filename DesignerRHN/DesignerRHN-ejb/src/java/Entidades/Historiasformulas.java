/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "HISTORIASFORMULAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historiasformulas.findAll", query = "SELECT h FROM Historiasformulas h")})
public class Historiasformulas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historiaformula")
    private Collection<Nodos> nodosCollection;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @Transient
    private String strFechaIni;
    @Transient
    private String strFechaFin;

    public Historiasformulas() {
    }

    public Historiasformulas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Historiasformulas(BigInteger secuencia, Date fechainicial, Date fechafinal) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
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

    public String getStrFechaIni() {
        if (fechainicial != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            strFechaIni = formatoFecha.format(fechainicial);
        } else {
            strFechaIni = " ";
        }
        return strFechaIni;
    }

    public void setStrFechaIni(String strFechaIni) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechainicial = formatoFecha.parse(strFechaIni);
        this.strFechaIni = strFechaIni;
    }

    public String getStrFechaFin() {
        if (fechafinal != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            strFechaFin = formatoFecha.format(fechafinal);
        } else {
            strFechaFin = " ";
        }
        return strFechaFin;
    }

    public void setStrFechaFin(String strFechaFin) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        fechafinal = formatoFecha.parse(strFechaFin);
        this.strFechaFin = strFechaFin;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getObservaciones() {
        if (observaciones == null) {
            observaciones = " ";
        }
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public Collection<Nodos> getNodosCollection() {
        return nodosCollection;
    }

    public void setNodosCollection(Collection<Nodos> nodosCollection) {
        this.nodosCollection = nodosCollection;
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
        if (!(object instanceof Historiasformulas)) {
            return false;
        }
        Historiasformulas other = (Historiasformulas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Historiasformulas[ secuencia=" + secuencia + " ]";
    }

}
