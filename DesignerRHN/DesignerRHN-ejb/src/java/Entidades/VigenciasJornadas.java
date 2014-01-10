/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
 * @author user
 */
@Entity
@Table(name = "VIGENCIASJORNADAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasJornadas.findAll", query = "SELECT v FROM VigenciasJornadas v"),
    @NamedQuery(name = "VigenciasJornadas.findBySecuencia", query = "SELECT v FROM VigenciasJornadas v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasJornadas.findByFechavigencia", query = "SELECT v FROM VigenciasJornadas v WHERE v.fechavigencia = :fechavigencia")})
public class VigenciasJornadas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @JoinColumn(name = "TIPODESCANSO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposDescansos tipodescanso;
    @JoinColumn(name = "JORNADATRABAJO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private JornadasLaborales jornadatrabajo;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vigenciajornada")
    private Collection<VigenciasCompensaciones> vigenciascompensacionesCollection;

    public VigenciasJornadas() {
    }

    public VigenciasJornadas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasJornadas(BigInteger secuencia, Date fechavigencia) {
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

    public TiposDescansos getTipodescanso() {
        if(tipodescanso == null){
            return new TiposDescansos();
        }
        return tipodescanso;
    }

    public void setTipodescanso(TiposDescansos tipodescanso) {
        this.tipodescanso = tipodescanso;
    }

    public JornadasLaborales getJornadatrabajo() {
        if(jornadatrabajo==null){
            return new JornadasLaborales();
        }
        return jornadatrabajo;
    }

    public void setJornadatrabajo(JornadasLaborales jornadatrabajo) {
        this.jornadatrabajo = jornadatrabajo;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    @XmlTransient
    public Collection<VigenciasCompensaciones> getVigenciascompensacionesCollection() {
        return vigenciascompensacionesCollection;
    }

    public void setVigenciascompensacionesCollection(Collection<VigenciasCompensaciones> vigenciascompensacionesCollection) {
        this.vigenciascompensacionesCollection = vigenciascompensacionesCollection;
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
        if (!(object instanceof VigenciasJornadas)) {
            return false;
        }
        VigenciasJornadas other = (VigenciasJornadas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciasjornadas[ secuencia=" + secuencia + " ]";
    }
    
}
