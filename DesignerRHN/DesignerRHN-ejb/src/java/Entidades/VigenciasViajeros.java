package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VIGENCIASVIAJEROS")
public class VigenciasViajeros implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @JoinColumn(name = "TIPOVIAJERO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Tiposviajeros tipoViajero;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public VigenciasViajeros() {
    }

    public VigenciasViajeros(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasViajeros(BigInteger secuencia, Date fechavigencia) {
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

    public Tiposviajeros getTipoViajero() {
        return tipoViajero;
    }

    public void setTipoViajero(Tiposviajeros tipoViajero) {
        this.tipoViajero = tipoViajero;
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
        if (!(object instanceof VigenciasViajeros)) {
            return false;
        }
        VigenciasViajeros other = (VigenciasViajeros) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasViajeros[ secuencia=" + secuencia + " ]";
    }

}
