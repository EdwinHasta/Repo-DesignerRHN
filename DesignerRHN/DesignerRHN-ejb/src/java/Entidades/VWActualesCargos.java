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
@Table(name = "VWACTUALESCARGOS")
public class VWActualesCargos implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTRUCTURA")
    private BigInteger estructura;
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;
    @Size(max = 1)
    @Column(name = "TURNOROTATIVO")
    private String turnoRotativo;
    @Size(max = 1)
    @Column(name = "LIQUIDAHE")
    private String liquidaHE;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOTIVOCAMBIOCARGO")
    private BigInteger motivoCambioCargo;
    @Column(name = "CALIFICACION")
    private Integer calificacion;
    @Basic(optional = false)
    @Column(name = "EMPLEADOJEFE")
    private BigInteger empleadoJefe;
    @Basic(optional = false)
    @Column(name = "ESCALAFON")
    private BigInteger escalafon;

    public VWActualesCargos() {
        fechaVigencia = new Date();
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public BigInteger getEstructura() {
        return estructura;
    }

    public void setEstructura(BigInteger estructura) {
        this.estructura = estructura;
    }

    public Date getFechaVigencia() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.format(fechaVigencia);
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String f =formato.format(fechaVigencia);
        this.fechaVigencia = formato.parse(f);
    }

    public String getTurnoRotativo() {
        return turnoRotativo;
    }

    public void setTurnoRotativo(String turnoRotativo) {
        this.turnoRotativo = turnoRotativo;
    }

    public String getLiquidaHE() {
        return liquidaHE;
    }

    public void setLiquidaHE(String liquidaHE) {
        this.liquidaHE = liquidaHE;
    }

    public BigInteger getMotivoCambioCargo() {
        return motivoCambioCargo;
    }

    public void setMotivoCambioCargo(BigInteger motivoCambioCargo) {
        this.motivoCambioCargo = motivoCambioCargo;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public BigInteger getEmpleadoJefe() {
        return empleadoJefe;
    }

    public void setEmpleadoJefe(BigInteger empleadoJefe) {
        this.empleadoJefe = empleadoJefe;
    }

    public BigInteger getEscalafon() {
        return escalafon;
    }

    public void setEscalafon(BigInteger escalafon) {
        this.escalafon = escalafon;
    }
    
    
    
    
    
    

    
    
}
