/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "DETALLESTURNOSROTATIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesTurnosRotativos.findAll", query = "SELECT d FROM DetallesTurnosRotativos d"),
    @NamedQuery(name = "DetallesTurnosRotativos.findBySecuencia", query = "SELECT d FROM DetallesTurnosRotativos d WHERE d.secuencia = :secuencia"),
    @NamedQuery(name = "DetallesTurnosRotativos.findByOrden", query = "SELECT d FROM DetallesTurnosRotativos d WHERE d.orden = :orden")})
public class DetallesTurnosRotativos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDEN")
    private short orden;
    @JoinColumn(name = "TURNOROTATIVO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Turnosrotativos turnorotativo;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @OneToOne(optional = false)
    private Empleados empleado;

    public DetallesTurnosRotativos() {
    }

    public DetallesTurnosRotativos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public DetallesTurnosRotativos(BigInteger secuencia, short orden) {
        this.secuencia = secuencia;
        this.orden = orden;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public Turnosrotativos getTurnorotativo() {
        return turnorotativo;
    }

    public void setTurnorotativo(Turnosrotativos turnorotativo) {
        this.turnorotativo = turnorotativo;
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
        if (!(object instanceof DetallesTurnosRotativos)) {
            return false;
        }
        DetallesTurnosRotativos other = (DetallesTurnosRotativos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DetallesTurnosRotativos[ secuencia=" + secuencia + " ]";
    }
    
}
