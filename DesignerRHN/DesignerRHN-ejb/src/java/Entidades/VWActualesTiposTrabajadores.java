/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrator
 */
@Entity
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "VWActualesTiposTrabajadores.findAll", query = "SELECT vwatt FROM VWActualesTiposTrabajadores vwatt"),
    @NamedQuery(name = "VWActualesTiposTrabajadores.findBySecuencia", query = "SELECT vwatt FROM VWActualesTiposTrabajadores vwatt WHERE vwatt.secuencia = :secuencia"),
    @NamedQuery(name = "VWActualesTiposTrabajadores.findByTipoTrabajador", query = "SELECT vwatt FROM VWActualesTiposTrabajadores vwatt WHERE vwatt.tipoTrabajador.tipo = :tipotrabajador"),
    @NamedQuery(name = "VWActualesTiposTrabajadores.findByEmpleado", query = "SELECT vwatt FROM VWActualesTiposTrabajadores vwatt WHERE vwatt.empleado.secuencia = :empleado")
})
public class VWActualesTiposTrabajadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposTrabajadores tipoTrabajador;
    @Column(name = "FECHARETIRO")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @Column(name = "MOTIVORETIRO")
    private BigInteger motivoRetiro;

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public TiposTrabajadores getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(TiposTrabajadores tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public BigInteger getMotivoRetiro() {
        return motivoRetiro;
    }

    public void setMotivoRetiro(BigInteger motivoRetiro) {
        this.motivoRetiro = motivoRetiro;
    }
}
