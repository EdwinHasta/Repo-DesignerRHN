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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CORTESPROCESOS")
@NamedQueries({
    @NamedQuery(name = "CortesProcesos.findAll", query = "SELECT c FROM CortesProcesos c"),
    @NamedQuery(name = "CortesProcesos.findBySecuencia", query = "SELECT c FROM CortesProcesos c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "CortesProcesos.findByCorte", query = "SELECT c FROM CortesProcesos c WHERE c.corte = :corte")})
public class CortesProcesos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "COMPROBANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Comprobantes comprobante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date corte;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Procesos proceso;

    public CortesProcesos() {
    }

    public CortesProcesos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public CortesProcesos(BigInteger secuencia, Date corte) {
        this.secuencia = secuencia;
        this.corte = corte;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getCorte() {
        return corte;
    }

    public void setCorte(Date corte) {
        this.corte = corte;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Comprobantes getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobantes comprobante) {
        this.comprobante = comprobante;
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
        if (!(object instanceof CortesProcesos)) {
            return false;
        }
        CortesProcesos other = (CortesProcesos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CortesProcesos[ secuencia=" + secuencia + " ]";
    }

}
