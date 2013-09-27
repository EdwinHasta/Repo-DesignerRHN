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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASNOFORMALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasNoFormales.findAll", query = "SELECT v FROM VigenciasNoFormales v")})
public class VigenciasNoFormales implements Serializable {
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
    @Size(max = 50)
    @Column(name = "TITULO")
    private String titulo;
    @Size(max = 1)
    @Column(name = "ACARGO")
    private String acargo;
    @Size(max = 150)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 20)
    @Column(name = "CALIFICACIONOBTENIDA")
    private String calificacionobtenida;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Personas persona;
    @JoinColumn(name = "INSTITUCION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Instituciones institucion;
    @JoinColumn(name = "CURSO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cursos curso;
    @JoinColumn(name = "ADIESTRAMIENTONF", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private AdiestramientosNF adiestramientonf;

    public VigenciasNoFormales() {
    }

    public VigenciasNoFormales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasNoFormales(BigInteger secuencia, Date fechavigencia) {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public AdiestramientosNF getAdiestramientonf() {
        return adiestramientonf;
    }

    public void setAdiestramientonf(AdiestramientosNF adiestramientonf) {
        this.adiestramientonf = adiestramientonf;
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
        if (!(object instanceof VigenciasNoFormales)) {
            return false;
        }
        VigenciasNoFormales other = (VigenciasNoFormales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasNoFormales[ secuencia=" + secuencia + " ]";
    }
    
}
