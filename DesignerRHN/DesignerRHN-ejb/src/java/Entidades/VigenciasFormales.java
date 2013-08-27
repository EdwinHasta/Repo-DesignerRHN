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
@Table(name = "VIGENCIASFORMALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasFormales.findAll", query = "SELECT v FROM VigenciasFormales v")})
public class VigenciasFormales implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @Size(max = 1)
    @Column(name = "ACARGO")
    private String acargo;
    @Size(max = 150)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 10)
    @Column(name = "CALIFICACIONOBTENIDA")
    private String calificacionobtenida;
    @Size(max = 1)
    @Column(name = "TARJETAPROFESIONAL")
    private String tarjetaprofesional;
    @Column(name = "FECHAEXPEDICIONTARJETA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpediciontarjeta;
    @Size(max = 20)
    @Column(name = "NUMEROTARJETA")
    private String numerotarjeta;
    @Column(name = "FECHAVENCIMIENTOTARJETA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencimientotarjeta;
    @JoinColumn(name = "TIPOEDUCACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposEducaciones tipoeducacion;
    @JoinColumn(name = "PROFESION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Profesiones profesion;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Personas persona;
    @JoinColumn(name = "INSTITUCION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Instituciones institucion;
    @JoinColumn(name = "ADIESTRAMIENTOF", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private AdiestramientosF adiestramientof;

    public VigenciasFormales() {
    }

    public VigenciasFormales(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasFormales(BigDecimal secuencia, Date fechavigencia) {
        this.secuencia = secuencia;
        this.fechavigencia = fechavigencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public String getAcargo() {
        return acargo;
    }

    public void setAcargo(String acargo) {
        this.acargo = acargo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCalificacionobtenida() {
        return calificacionobtenida;
    }

    public void setCalificacionobtenida(String calificacionobtenida) {
        this.calificacionobtenida = calificacionobtenida;
    }

    public String getTarjetaprofesional() {
        return tarjetaprofesional;
    }

    public void setTarjetaprofesional(String tarjetaprofesional) {
        this.tarjetaprofesional = tarjetaprofesional;
    }

    public Date getFechaexpediciontarjeta() {
        return fechaexpediciontarjeta;
    }

    public void setFechaexpediciontarjeta(Date fechaexpediciontarjeta) {
        this.fechaexpediciontarjeta = fechaexpediciontarjeta;
    }

    public String getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public Date getFechavencimientotarjeta() {
        return fechavencimientotarjeta;
    }

    public void setFechavencimientotarjeta(Date fechavencimientotarjeta) {
        this.fechavencimientotarjeta = fechavencimientotarjeta;
    }

    public TiposEducaciones getTipoeducacion() {
        return tipoeducacion;
    }

    public void setTipoeducacion(TiposEducaciones tipoeducacion) {
        this.tipoeducacion = tipoeducacion;
    }

    public Profesiones getProfesion() {
        return profesion;
    }

    public void setProfesion(Profesiones profesion) {
        this.profesion = profesion;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }

    public AdiestramientosF getAdiestramientof() {
        return adiestramientof;
    }

    public void setAdiestramientof(AdiestramientosF adiestramientof) {
        this.adiestramientof = adiestramientof;
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
        if (!(object instanceof VigenciasFormales)) {
            return false;
        }
        VigenciasFormales other = (VigenciasFormales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasFormales[ secuencia=" + secuencia + " ]";
    }
    
}
