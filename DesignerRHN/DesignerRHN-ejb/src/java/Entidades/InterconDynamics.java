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
 * @author Administrador
 */
@Entity
@Table(name = "INTERCON_DYNAMICS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterconDynamics.findAll", query = "SELECT i FROM InterconDynamics i"),
    @NamedQuery(name = "InterconDynamics.findByCodigotercero", query = "SELECT i FROM InterconDynamics i WHERE i.codigotercero = :codigotercero"),
    @NamedQuery(name = "InterconDynamics.findByCodigocuentacontable", query = "SELECT i FROM InterconDynamics i WHERE i.codigocuentacontable = :codigocuentacontable"),
    @NamedQuery(name = "InterconDynamics.findByFlag", query = "SELECT i FROM InterconDynamics i WHERE i.flag = :flag"),
    @NamedQuery(name = "InterconDynamics.findByFechaultimamodificacion", query = "SELECT i FROM InterconDynamics i WHERE i.fechaultimamodificacion = :fechaultimamodificacion"),
    @NamedQuery(name = "InterconDynamics.findByFechacontabilizacion", query = "SELECT i FROM InterconDynamics i WHERE i.fechacontabilizacion = :fechacontabilizacion"),
    @NamedQuery(name = "InterconDynamics.findBySalida", query = "SELECT i FROM InterconDynamics i WHERE i.salida = :salida"),
    @NamedQuery(name = "InterconDynamics.findByNaturaleza", query = "SELECT i FROM InterconDynamics i WHERE i.naturaleza = :naturaleza"),
    @NamedQuery(name = "InterconDynamics.findByValorc", query = "SELECT i FROM InterconDynamics i WHERE i.valorc = :valorc"),
    @NamedQuery(name = "InterconDynamics.findByValord", query = "SELECT i FROM InterconDynamics i WHERE i.valord = :valord"),
    @NamedQuery(name = "InterconDynamics.findBySecuencia", query = "SELECT i FROM InterconDynamics i WHERE i.secuencia = :secuencia"),
    @NamedQuery(name = "InterconDynamics.findByEmpresaCodigo", query = "SELECT i FROM InterconDynamics i WHERE i.empresaCodigo = :empresaCodigo"),
    @NamedQuery(name = "InterconDynamics.findByPreradicacion", query = "SELECT i FROM InterconDynamics i WHERE i.preradicacion = :preradicacion"),
    @NamedQuery(name = "InterconDynamics.findByRadicacion", query = "SELECT i FROM InterconDynamics i WHERE i.radicacion = :radicacion"),
    @NamedQuery(name = "InterconDynamics.findByCodigocuentacontablealterna", query = "SELECT i FROM InterconDynamics i WHERE i.codigocuentacontablealterna = :codigocuentacontablealterna"),
    @NamedQuery(name = "InterconDynamics.findByValororiginalc", query = "SELECT i FROM InterconDynamics i WHERE i.valororiginalc = :valororiginalc"),
    @NamedQuery(name = "InterconDynamics.findByValororiginald", query = "SELECT i FROM InterconDynamics i WHERE i.valororiginald = :valororiginald"),
    @NamedQuery(name = "InterconDynamics.findByFechavencimiento", query = "SELECT i FROM InterconDynamics i WHERE i.fechavencimiento = :fechavencimiento"),
    @NamedQuery(name = "InterconDynamics.findByTipocuenta", query = "SELECT i FROM InterconDynamics i WHERE i.tipocuenta = :tipocuenta"),
    @NamedQuery(name = "InterconDynamics.findByCodigocuentacontablevt", query = "SELECT i FROM InterconDynamics i WHERE i.codigocuentacontablevt = :codigocuentacontablevt")})
public class InterconDynamics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "CODIGOTERCERO")
    private String codigotercero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CODIGOCUENTACONTABLE")
    private String codigocuentacontable;
    @Size(max = 28)
    @Column(name = "FLAG")
    private String flag;
    @Column(name = "FECHAULTIMAMODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaultimamodificacion;
    @Column(name = "FECHACONTABILIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacontabilizacion;
    @Size(max = 50)
    @Column(name = "SALIDA")
    private String salida;
    @Size(max = 20)
    @Column(name = "NATURALEZA")
    private String naturaleza;
    @Column(name = "VALORC")
    private Long valorc;
    @Column(name = "VALORD")
    private Long valord;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "EMPRESA_CODIGO")
    private Short empresaCodigo;
    @Column(name = "PRERADICACION")
    private BigInteger preradicacion;
    @Column(name = "RADICACION")
    private BigInteger radicacion;
    @Size(max = 60)
    @Column(name = "CODIGOCUENTACONTABLEALTERNA")
    private String codigocuentacontablealterna;
    @Column(name = "VALORORIGINALC")
    private BigInteger valororiginalc;
    @Column(name = "VALORORIGINALD")
    private BigInteger valororiginald;
    @Column(name = "FECHAVENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencimiento;
    @Size(max = 10)
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @Size(max = 100)
    @Column(name = "CODIGOCUENTACONTABLEVT")
    private String codigocuentacontablevt;
    @JoinColumn(name = "PROYECTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Proyectos proyecto;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Procesos proceso;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados empleado;
    @JoinColumn(name = "CONTABILIZACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Contabilizaciones contabilizacion;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Conceptos concepto;
    @JoinColumn(name = "CENTROCOSTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocosto;
    @Transient
    private String cargo;

    public InterconDynamics() {
    }

    public InterconDynamics(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public InterconDynamics(BigInteger secuencia, String codigocuentacontable) {
        this.secuencia = secuencia;
        this.codigocuentacontable = codigocuentacontable;
    }

    public String getCodigotercero() {
        return codigotercero;
    }

    public void setCodigotercero(String codigotercero) {
        this.codigotercero = codigotercero;
    }

    public String getCodigocuentacontable() {
        return codigocuentacontable;
    }

    public void setCodigocuentacontable(String codigocuentacontable) {
        this.codigocuentacontable = codigocuentacontable;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getFechaultimamodificacion() {
        return fechaultimamodificacion;
    }

    public void setFechaultimamodificacion(Date fechaultimamodificacion) {
        this.fechaultimamodificacion = fechaultimamodificacion;
    }

    public Date getFechacontabilizacion() {
        return fechacontabilizacion;
    }

    public void setFechacontabilizacion(Date fechacontabilizacion) {
        this.fechacontabilizacion = fechacontabilizacion;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public Long getValorc() {
        return valorc;
    }

    public void setValorc(Long valorc) {
        this.valorc = valorc;
    }

    public Long getValord() {
        return valord;
    }

    public void setValord(Long valord) {
        this.valord = valord;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getEmpresaCodigo() {
        return empresaCodigo;
    }

    public void setEmpresaCodigo(Short empresaCodigo) {
        this.empresaCodigo = empresaCodigo;
    }

    public BigInteger getPreradicacion() {
        return preradicacion;
    }

    public void setPreradicacion(BigInteger preradicacion) {
        this.preradicacion = preradicacion;
    }

    public BigInteger getRadicacion() {
        return radicacion;
    }

    public void setRadicacion(BigInteger radicacion) {
        this.radicacion = radicacion;
    }

    public String getCodigocuentacontablealterna() {
        return codigocuentacontablealterna;
    }

    public void setCodigocuentacontablealterna(String codigocuentacontablealterna) {
        this.codigocuentacontablealterna = codigocuentacontablealterna;
    }

    public BigInteger getValororiginalc() {
        return valororiginalc;
    }

    public void setValororiginalc(BigInteger valororiginalc) {
        this.valororiginalc = valororiginalc;
    }

    public BigInteger getValororiginald() {
        return valororiginald;
    }

    public void setValororiginald(BigInteger valororiginald) {
        this.valororiginald = valororiginald;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getCodigocuentacontablevt() {
        return codigocuentacontablevt;
    }

    public void setCodigocuentacontablevt(String codigocuentacontablevt) {
        this.codigocuentacontablevt = codigocuentacontablevt;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
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

    public Contabilizaciones getContabilizacion() {
        return contabilizacion;
    }

    public void setContabilizacion(Contabilizaciones contabilizacion) {
        this.contabilizacion = contabilizacion;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public CentrosCostos getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(CentrosCostos centrocosto) {
        this.centrocosto = centrocosto;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof InterconDynamics)) {
            return false;
        }
        InterconDynamics other = (InterconDynamics) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.InterconDynamics[ secuencia=" + secuencia + " ]";
    }

}
