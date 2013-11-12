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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Viktor
 */
@Entity
@Table(name = "ENFERMEADADESPROFESIONALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnfermeadadesProfesionales.findAll", query = "SELECT e FROM EnfermeadadesProfesionales e"),
    @NamedQuery(name = "EnfermeadadesProfesionales.findBySecuencia", query = "SELECT e FROM EnfermeadadesProfesionales e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "EnfermeadadesProfesionales.findByCodigo", query = "SELECT e FROM EnfermeadadesProfesionales e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "EnfermeadadesProfesionales.findByFechanotificacion", query = "SELECT e FROM EnfermeadadesProfesionales e WHERE e.fechanotificacion = :fechanotificacion")})
public class EnfermeadadesProfesionales implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHANOTIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechanotificacion;
    @JoinColumn(name = "RESPONSABLE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas responsable;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "SECCION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Diagnosticossecciones seccion;
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Diagnosticoscategorias categoria;
    @JoinColumn(name = "CAPITULO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Diagnosticoscapitulos capitulo;

    public EnfermeadadesProfesionales() {
    }

    public EnfermeadadesProfesionales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EnfermeadadesProfesionales(BigInteger secuencia, short codigo, Date fechanotificacion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.fechanotificacion = fechanotificacion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public Date getFechanotificacion() {
        return fechanotificacion;
    }

    public void setFechanotificacion(Date fechanotificacion) {
        this.fechanotificacion = fechanotificacion;
    }

    public Personas getResponsable() {
        return responsable;
    }

    public void setResponsable(Personas responsable) {
        this.responsable = responsable;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Diagnosticossecciones getSeccion() {
        return seccion;
    }

    public void setSeccion(Diagnosticossecciones seccion) {
        this.seccion = seccion;
    }

    public Diagnosticoscategorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Diagnosticoscategorias categoria) {
        this.categoria = categoria;
    }

    public Diagnosticoscapitulos getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Diagnosticoscapitulos capitulo) {
        this.capitulo = capitulo;
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
        if (!(object instanceof EnfermeadadesProfesionales)) {
            return false;
        }
        EnfermeadadesProfesionales other = (EnfermeadadesProfesionales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EnfermeadadesProfesionales[ secuencia=" + secuencia + " ]";
    }
    
}
