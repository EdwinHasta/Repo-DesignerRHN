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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASCARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasCargos.findAll", query = "SELECT v FROM VigenciasCargos v"),
    @NamedQuery(name = "VigenciasCargos.findByEmpleado", query = "SELECT v FROM VigenciasCargos v where v.empleado = :empleado order by v.fechavigencia desc")})
public class VigenciasCargos implements Serializable {
    @JoinColumn(name = "PAPEL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Papeles papel;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechavigencia;
    @Size(max = 1)
    @Column(name = "TURNOROTATIVO")
    private String turnorotativo;
    @Size(max = 1)
    @Column(name = "LIQUIDAHE")
    private String liquidahe;
    @Column(name = "CALIFICACION")
    private Short calificacion;
    @JoinColumn(name = "MOTIVOCAMBIOCARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private MotivosCambiosCargos motivocambiocargo;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Estructuras estructura;
    @JoinColumn(name = "ESCALAFON", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Escalafones escalafon;
    @JoinColumn(name = "EMPLEADOJEFE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados empleadojefe;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;

    public VigenciasCargos() {
    }

    public VigenciasCargos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasCargos(BigInteger secuencia, Date fechavigencia) {
        this.secuencia = secuencia;
        this.fechavigencia = fechavigencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public String getTurnorotativo() {
        return turnorotativo;
    }

    public void setTurnorotativo(String turnorotativo) {
        this.turnorotativo = turnorotativo;
    }

    public String getLiquidahe() {
        return liquidahe;
    }

    public void setLiquidahe(String liquidahe) {
        this.liquidahe = liquidahe;
    }

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }

    public MotivosCambiosCargos getMotivocambiocargo() {
        return motivocambiocargo;
    }

    public void setMotivocambiocargo(MotivosCambiosCargos motivocambiocargo) {
        this.motivocambiocargo = motivocambiocargo;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Escalafones getEscalafon() {
        return escalafon;
    }

    public void setEscalafon(Escalafones escalafon) {
        this.escalafon = escalafon;
    }

    public Empleados getEmpleadojefe() {
        if (empleadojefe == null) {
            empleadojefe = new Empleados();
            empleadojefe.setPersona(new Personas());
           // empleadojefe.getPersona().setNombreCompleto(empleadojefe.getPersona().getPrimerapellido() + " " + empleadojefe.getPersona().getSegundoapellido() + " " + empleadojefe.getPersona().getNombre());
            return empleadojefe;
        } else {
            //empleadojefe.getPersona().setNombreCompleto(empleadojefe.getPersona().getPrimerapellido() + " " + empleadojefe.getPersona().getSegundoapellido() + " " + empleadojefe.getPersona().getNombre());
            return empleadojefe;
        }

    }

    public void setEmpleadojefe(Empleados empleadojefe) {
        this.empleadojefe = empleadojefe;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
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
        if (!(object instanceof VigenciasCargos)) {
            return false;
        }
        VigenciasCargos other = (VigenciasCargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciascargos[ secuencia=" + secuencia + " ]";
    }

    public Papeles getPapel() {
        return papel;
    }

    public void setPapel(Papeles papel) {
        this.papel = papel;
    }
}
