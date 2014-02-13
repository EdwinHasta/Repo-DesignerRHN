/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "EERSAUXILIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EersAuxilios.findAll", query = "SELECT e FROM EersAuxilios e")})
public class EersAuxilios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigInteger valor;
    @Size(max = 50)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "FECHAPAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;
    @Column(name = "UNIDAD")
    private Short unidad;
    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Size(max = 1)
    @Column(name = "PAGARPORFUERA")
    private String pagarporfuera;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros tercero;
    @JoinColumn(name = "TABLAAUXILIO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TablasAuxilios tablaauxilio;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public EersAuxilios() {
    }

    public EersAuxilios(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public EersAuxilios(BigDecimal secuencia, Date fechainicial, Date fechafinal, BigInteger valor) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
        this.valor = valor;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public Short getUnidad() {
        return unidad;
    }

    public void setUnidad(Short unidad) {
        this.unidad = unidad;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public String getPagarporfuera() {
        return pagarporfuera;
    }

    public void setPagarporfuera(String pagarporfuera) {
        this.pagarporfuera = pagarporfuera;
    }

    public Terceros getTercero() {
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    public TablasAuxilios getTablaauxilio() {
        return tablaauxilio;
    }

    public void setTablaauxilio(TablasAuxilios tablaauxilio) {
        this.tablaauxilio = tablaauxilio;
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
        if (!(object instanceof EersAuxilios)) {
            return false;
        }
        EersAuxilios other = (EersAuxilios) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EersAuxilios[ secuencia=" + secuencia + " ]";
    }
    
}
