/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PARAMETROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")})
public class Parametros implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTACAUSADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahastacausado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDECAUSADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesdecausado;
    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Column(name = "USUARIO")
    private BigInteger usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametro")
    private List<ParametrosInstancias> parametrosInstanciasList;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Procesos proceso;
    @JoinColumn(name = "PARAMETROESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private ParametrosEstructuras parametroestructura;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public Parametros() {
    }

    public Parametros(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Parametros(BigDecimal secuencia, Date fechahastacausado, Date fechadesdecausado) {
        this.secuencia = secuencia;
        this.fechahastacausado = fechahastacausado;
        this.fechadesdecausado = fechadesdecausado;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechahastacausado() {
        return fechahastacausado;
    }

    public void setFechahastacausado(Date fechahastacausado) {
        this.fechahastacausado = fechahastacausado;
    }

    public Date getFechadesdecausado() {
        return fechadesdecausado;
    }

    public void setFechadesdecausado(Date fechadesdecausado) {
        this.fechadesdecausado = fechadesdecausado;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public BigInteger getUsuario() {
        return usuario;
    }

    public void setUsuario(BigInteger usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<ParametrosInstancias> getParametrosInstanciasList() {
        return parametrosInstanciasList;
    }

    public void setParametrosInstanciasList(List<ParametrosInstancias> parametrosInstanciasList) {
        this.parametrosInstanciasList = parametrosInstanciasList;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public ParametrosEstructuras getParametroestructura() {
        return parametroestructura;
    }

    public void setParametroestructura(ParametrosEstructuras parametroestructura) {
        this.parametroestructura = parametroestructura;
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
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametros[ secuencia=" + secuencia + " ]";
    }
    
}
