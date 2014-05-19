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
@Table(name = "VIGENCIASLOCALIZACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasLocalizaciones.findAll", query = "SELECT v FROM VigenciasLocalizaciones v"),
    @NamedQuery(name = "VigenciasLocalizaciones.findBySecuencia", query = "SELECT v FROM VigenciasLocalizaciones v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasLocalizaciones.findByFechavigencia", query = "SELECT v FROM VigenciasLocalizaciones v WHERE v.fechavigencia = :fechavigencia")})
public class VigenciasLocalizaciones implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vigencialocalizacion")
    private Collection<VigenciasProrrateosProyectos> vigenciasprorrateosproyectosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "viglocalizacion")
    private Collection<VigenciasProrrateos> vigenciasprorrateosCollection;
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
    @JoinColumn(name = "PROYECTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = true)
    private Proyectos proyecto;
    @JoinColumn(name = "MOTIVO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private MotivosLocalizaciones motivo;
    @JoinColumn(name = "LOCALIZACION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Estructuras localizacion;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public VigenciasLocalizaciones() {
    }

    public VigenciasLocalizaciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasLocalizaciones(BigInteger secuencia, Date fechavigencia) {
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

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    public MotivosLocalizaciones getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivosLocalizaciones motivo) {
        this.motivo = motivo;
    }

    public Estructuras getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Estructuras localizacion) {
        this.localizacion = localizacion;
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
        if (!(object instanceof VigenciasLocalizaciones)) {
            return false;
        }
        VigenciasLocalizaciones other = (VigenciasLocalizaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciaslocalizaciones[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasProrrateos> getVigenciasprorrateosCollection() {
        return vigenciasprorrateosCollection;
    }

    public void setVigenciasprorrateosCollection(Collection<VigenciasProrrateos> vigenciasprorrateosCollection) {
        this.vigenciasprorrateosCollection = vigenciasprorrateosCollection;
    }

    @XmlTransient
    public Collection<VigenciasProrrateosProyectos> getVigenciasprorrateosproyectosCollection() {
        return vigenciasprorrateosproyectosCollection;
    }

    public void setVigenciasprorrateosproyectosCollection(Collection<VigenciasProrrateosProyectos> vigenciasprorrateosproyectosCollection) {
        this.vigenciasprorrateosproyectosCollection = vigenciasprorrateosproyectosCollection;
    }

}
