/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "ERRORESLIQUIDACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ErroresLiquidacion.findAll", query = "SELECT e FROM ErroresLiquidacion e"),
    @NamedQuery(name = "ErroresLiquidacion.findBySecuencia", query = "SELECT e FROM ErroresLiquidacion e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "ErroresLiquidacion.findByFechadesde", query = "SELECT e FROM ErroresLiquidacion e WHERE e.fechadesde = :fechadesde"),
    @NamedQuery(name = "ErroresLiquidacion.findByFechahasta", query = "SELECT e FROM ErroresLiquidacion e WHERE e.fechahasta = :fechahasta"),
    @NamedQuery(name = "ErroresLiquidacion.findByFecha", query = "SELECT e FROM ErroresLiquidacion e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "ErroresLiquidacion.findByError", query = "SELECT e FROM ErroresLiquidacion e WHERE e.error = :error"),
    @NamedQuery(name = "ErroresLiquidacion.findByPaquete", query = "SELECT e FROM ErroresLiquidacion e WHERE e.paquete = :paquete")})
public class ErroresLiquidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHADESDE")
    @Temporal(TemporalType.DATE)
    private Date fechadesde;
    @Column(name = "FECHAHASTA")
    @Temporal(TemporalType.DATE)
    private Date fechahasta;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 300)
    @Column(name = "ERROR")
    private String error;
    @Size(max = 40)
    @Column(name = "PAQUETE")
    private String paquete;
    @Transient
    private VigenciasLocalizaciones vigenciaLocalizacion;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;

    public ErroresLiquidacion() {
    }

    public ErroresLiquidacion(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ErroresLiquidacion(BigInteger secuencia, Date fecha) {
        this.secuencia = secuencia;
        this.fecha = fecha;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
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
        if (!(object instanceof ErroresLiquidacion)) {
            return false;
        }
        ErroresLiquidacion other = (ErroresLiquidacion) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ErroresLiquidacion[ secuencia=" + secuencia + " ]";
    }

    public VigenciasLocalizaciones getVigenciaLocalizacion() {
        return vigenciaLocalizacion;
    }

    public void setVigenciaLocalizacion(VigenciasLocalizaciones vigenciaLocalizacion) {
        this.vigenciaLocalizacion = vigenciaLocalizacion;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }
    
    

}
