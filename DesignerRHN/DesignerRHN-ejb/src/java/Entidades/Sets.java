/*
 * To change this template, choose Tools | Templates
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
@Table(name = "SETS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sets.findAll", query = "SELECT s FROM Sets s"),
    @NamedQuery(name = "Sets.findBySecuencia", query = "SELECT s FROM Sets s WHERE s.secuencia = :secuencia"),
    @NamedQuery(name = "Sets.findByFechainicial", query = "SELECT s FROM Sets s WHERE s.fechainicial = :fechainicial"),
    @NamedQuery(name = "Sets.findByFechafinal", query = "SELECT s FROM Sets s WHERE s.fechafinal = :fechafinal"),
    @NamedQuery(name = "Sets.findByPromedio", query = "SELECT s FROM Sets s WHERE s.promedio = :promedio"),
    @NamedQuery(name = "Sets.findByTiposet", query = "SELECT s FROM Sets s WHERE s.tiposet = :tiposet"),
    @NamedQuery(name = "Sets.findByPorcentaje", query = "SELECT s FROM Sets s WHERE s.porcentaje = :porcentaje"),
    @NamedQuery(name = "Sets.findByTotalingresos", query = "SELECT s FROM Sets s WHERE s.totalingresos = :totalingresos")})
public class Sets implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROMEDIO")
    private BigDecimal promedio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPOSET")
    private String tiposet;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "TOTALINGRESOS")
    private BigInteger totalingresos;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public Sets() {
    }

    public Sets(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Sets(BigInteger secuencia, Date fechainicial, BigDecimal promedio, String tiposet) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.promedio = promedio;
        this.tiposet = tiposet;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
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

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public String getTiposet() {
        return tiposet;
    }

    public void setTiposet(String tiposet) {
        this.tiposet = tiposet;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigInteger getTotalingresos() {
        return totalingresos;
    }

    public void setTotalingresos(BigInteger totalingresos) {
        this.totalingresos = totalingresos;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
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
        if (!(object instanceof Sets)) {
            return false;
        }
        Sets other = (Sets) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Sets[ secuencia=" + secuencia + " ]";
    }
    
}
