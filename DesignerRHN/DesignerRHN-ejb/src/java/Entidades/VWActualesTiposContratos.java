/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "VWACTUALESTIPOSCONTRATOS")
public class VWActualesTiposContratos implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOTIVOCONTRATO")
    private BigInteger motivoContrato;
    @JoinColumn(name = "TIPOCONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposContratos tipoContrato;
    @Size(max = 1024)
    @Column(name = "OBSERVACIONES")
    private String observaciones;

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

    public Date getFechaVigencia() throws ParseException {
        /*SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String f = formato.format(fechaVigencia);
        fechaVigencia=formato.parse(f);
        System.out.println(fechaVigencia);*/
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) throws ParseException {
       /* SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String f =formato.format(fechaVigencia);*/
        this.fechaVigencia =fechaVigencia; //formato.parse(f);
        
    }

    public BigInteger getMotivoContrato() {
        return motivoContrato;
    }

    public void setMotivoContrato(BigInteger motivoContrato) {
        this.motivoContrato = motivoContrato;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TiposContratos getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TiposContratos tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
}
