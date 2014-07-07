/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "COMPROBANTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobantes.findAll", query = "SELECT c FROM Comprobantes c")})
public class Comprobantes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO")
    private BigInteger numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Size(max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 1)
    @Column(name = "ENTREGADO")
    private String entregado;
    @Column(name = "FECHAENTREGADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaentregado;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @Transient
    private boolean checkEntregado;
    @Transient
    private boolean readOnlyFecha;
    @Transient
    private boolean readOnlyFechaEntregado;

    public Comprobantes() {
    }

    public Comprobantes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Comprobantes(BigInteger secuencia, Date fecha, BigInteger numero, BigDecimal valor) {
        this.secuencia = secuencia;
        this.fecha = fecha;
        this.numero = numero;
        this.valor = valor;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEntregado() {
        if (entregado == null) {
            entregado = "N";
        }
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public boolean isCheckEntregado() {
        getEntregado();
        if (entregado.equalsIgnoreCase("S")) {
            checkEntregado = true;
            if (fechaentregado == null) {
                fechaentregado = new Date();
            }
        }
        else {
            checkEntregado = false;
        }
        return checkEntregado;
    }

    public void setCheckEntregado(boolean check) {
        if (check == false) {
            entregado = "N";
        } else {
            entregado = "S";
        }
        this.checkEntregado = check;
    }

    public Date getFechaentregado() {
        return fechaentregado;
    }

    public void setFechaentregado(Date fechaentregado) {
        this.fechaentregado = fechaentregado;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public boolean isReadOnlyFecha() {
        return readOnlyFecha;
    }

    public void setReadOnlyFecha(boolean readOnlyFecha) {
        this.readOnlyFecha = readOnlyFecha;
    }

    public boolean isReadOnlyFechaEntregado() {
        return readOnlyFechaEntregado;
    }

    public void setReadOnlyFechaEntregado(boolean readOnlyFechaEntregado) {
        this.readOnlyFechaEntregado = readOnlyFechaEntregado;
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
        if (!(object instanceof Comprobantes)) {
            return false;
        }
        Comprobantes other = (Comprobantes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Comprobantes[ secuencia=" + secuencia + " ]";
    }

    public BigInteger getNumero() {
        return numero;
    }

    public void setNumero(BigInteger numero) {
        this.numero = numero;
    }

}
