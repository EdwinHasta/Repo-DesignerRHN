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
 * @author user
 */
@Entity
@Table(name = "VIGENCIASPRORRATEOSPROYECTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasProrrateosProyectos.findAll", query = "SELECT v FROM VigenciasProrrateosProyectos v"),
    @NamedQuery(name = "VigenciasProrrateosProyectos.findBySecuencia", query = "SELECT v FROM VigenciasProrrateosProyectos v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasProrrateosProyectos.findByPorcentaje", query = "SELECT v FROM VigenciasProrrateosProyectos v WHERE v.porcentaje = :porcentaje"),
    @NamedQuery(name = "VigenciasProrrateosProyectos.findByFechainicial", query = "SELECT v FROM VigenciasProrrateosProyectos v WHERE v.fechainicial = :fechainicial"),
    @NamedQuery(name = "VigenciasProrrateosProyectos.findByFechafinal", query = "SELECT v FROM VigenciasProrrateosProyectos v WHERE v.fechafinal = :fechafinal")})
public class VigenciasProrrateosProyectos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORCENTAJE")
    private int porcentaje;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @JoinColumn(name = "VIGENCIALOCALIZACION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private VigenciasLocalizaciones vigencialocalizacion;
    @JoinColumn(name = "PROYECTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Proyectos proyecto;

    public VigenciasProrrateosProyectos() {
    }

    public VigenciasProrrateosProyectos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasProrrateosProyectos(BigInteger secuencia, int porcentaje, Date fechainicial) {
        this.secuencia = secuencia;
        this.porcentaje = porcentaje;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public VigenciasLocalizaciones getVigencialocalizacion() {
        return vigencialocalizacion;
    }

    public void setVigencialocalizacion(VigenciasLocalizaciones vigencialocalizacion) {
        this.vigencialocalizacion = vigencialocalizacion;
    }

    public Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyectos proyecto) {
        this.proyecto = proyecto;
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
        if (!(object instanceof VigenciasProrrateosProyectos)) {
            return false;
        }
        VigenciasProrrateosProyectos other = (VigenciasProrrateosProyectos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciasprorrateosproyectos[ secuencia=" + secuencia + " ]";
    }
    
}
