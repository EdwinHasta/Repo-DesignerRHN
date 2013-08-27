/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INFORMACIONESADICIONALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformacionesAdicionales.findAll", query = "SELECT i FROM InformacionesAdicionales i")})
public class InformacionesAdicionales implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPODATO")
    private String tipodato;
    @Column(name = "RESULTADONUMERICO")
    private Long resultadonumerico;
    @Size(max = 100)
    @Column(name = "RESULTADOCARACTER")
    private String resultadocaracter;
    @Column(name = "RESULTADOFECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadofecha;
    @JoinColumn(name = "GRUPO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private GruposInfAdicionales grupo;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public InformacionesAdicionales() {
    }

    public InformacionesAdicionales(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public InformacionesAdicionales(BigDecimal secuencia, Date fechafinal, Date fechainicial, String tipodato) {
        this.secuencia = secuencia;
        this.fechafinal = fechafinal;
        this.fechainicial = fechainicial;
        this.tipodato = tipodato;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public String getTipodato() {
        return tipodato;
    }

    public void setTipodato(String tipodato) {
        this.tipodato = tipodato;
    }

    public Long getResultadonumerico() {
        return resultadonumerico;
    }

    public void setResultadonumerico(Long resultadonumerico) {
        this.resultadonumerico = resultadonumerico;
    }

    public String getResultadocaracter() {
        return resultadocaracter;
    }

    public void setResultadocaracter(String resultadocaracter) {
        this.resultadocaracter = resultadocaracter;
    }

    public Date getResultadofecha() {
        return resultadofecha;
    }

    public void setResultadofecha(Date resultadofecha) {
        this.resultadofecha = resultadofecha;
    }

    public GruposInfAdicionales getGrupo() {
        return grupo;
    }

    public void setGrupo(GruposInfAdicionales grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof InformacionesAdicionales)) {
            return false;
        }
        InformacionesAdicionales other = (InformacionesAdicionales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.InformacionesAdicionales[ secuencia=" + secuencia + " ]";
    }
    
}
