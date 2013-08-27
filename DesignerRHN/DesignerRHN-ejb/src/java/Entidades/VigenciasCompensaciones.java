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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VIGENCIASCOMPENSACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasCompensaciones.findAll", query = "SELECT v FROM VigenciasCompensaciones v"),
    @NamedQuery(name = "VigenciasCompensaciones.findBySecuencia", query = "SELECT v FROM VigenciasCompensaciones v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasCompensaciones.findByFechainicial", query = "SELECT v FROM VigenciasCompensaciones v WHERE v.fechainicial = :fechainicial"),
    @NamedQuery(name = "VigenciasCompensaciones.findByFechafinal", query = "SELECT v FROM VigenciasCompensaciones v WHERE v.fechafinal = :fechafinal"),
    @NamedQuery(name = "VigenciasCompensaciones.findByTipocompensacion", query = "SELECT v FROM VigenciasCompensaciones v WHERE v.tipocompensacion = :tipocompensacion"),
    @NamedQuery(name = "VigenciasCompensaciones.findByComentario", query = "SELECT v FROM VigenciasCompensaciones v WHERE v.comentario = :comentario")})
public class VigenciasCompensaciones implements Serializable { 
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPOCOMPENSACION")
    private String tipocompensacion;
    @Size(max = 50)
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "VIGENCIAJORNADA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private VigenciasJornadas vigenciajornada;
    @JoinColumn(name = "NOVEDADTURNOROTATIVO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Novedadesturnosrotativos novedadturnorotativo;

    public VigenciasCompensaciones() {
    }

    public VigenciasCompensaciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasCompensaciones(BigInteger secuencia, String tipocompensacion) {
        this.secuencia = secuencia;
        this.tipocompensacion = tipocompensacion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
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

    public String getTipocompensacion() {
        return tipocompensacion;
    }

    public void setTipocompensacion(String tipocompensacion) {
        this.tipocompensacion = tipocompensacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public VigenciasJornadas getVigenciajornada() {
        return vigenciajornada;
    }

    public void setVigenciajornada(VigenciasJornadas vigenciajornada) {
        this.vigenciajornada = vigenciajornada;
    }

    public Novedadesturnosrotativos getNovedadturnorotativo() {
        return novedadturnorotativo;
    }

    public void setNovedadturnorotativo(Novedadesturnosrotativos novedadturnorotativo) {
        this.novedadturnorotativo = novedadturnorotativo;
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
        if (!(object instanceof VigenciasCompensaciones)) {
            return false;
        }
        VigenciasCompensaciones other = (VigenciasCompensaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciascompensaciones[ secuencia=" + secuencia + " ]";
    }
    
}
