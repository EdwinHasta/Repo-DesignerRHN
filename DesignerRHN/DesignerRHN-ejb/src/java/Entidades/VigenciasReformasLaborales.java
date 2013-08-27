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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VIGENCIASREFORMASLABORALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasReformasLaborales.findAll", query = "SELECT v FROM VigenciasReformasLaborales v"),
    @NamedQuery(name = "VigenciasReformasLaborales.findBySecuencia", query = "SELECT v FROM VigenciasReformasLaborales v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasReformasLaborales.findByFechavigencia", query = "SELECT v FROM VigenciasReformasLaborales v WHERE v.fechavigencia = :fechavigencia")})
public class VigenciasReformasLaborales implements Serializable {
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @JoinColumn(name = "REFORMALABORAL", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private ReformasLaborales reformalaboral;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public VigenciasReformasLaborales() {
    }

    public VigenciasReformasLaborales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasReformasLaborales(BigInteger secuencia, Date fechavigencia) {
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

    public ReformasLaborales getReformalaboral() {
        return reformalaboral;
    }

    public void setReformalaboral(ReformasLaborales reformalaboral) {
        this.reformalaboral = reformalaboral;
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
        if (!(object instanceof VigenciasReformasLaborales)) {
            return false;
        }
        VigenciasReformasLaborales other = (VigenciasReformasLaborales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasReformasLaborales[ secuencia=" + secuencia + " ]";
    }
    
}
