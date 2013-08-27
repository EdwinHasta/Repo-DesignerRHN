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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASESTADOSCIVILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasEstadosCiviles.findAll", query = "SELECT v FROM VigenciasEstadosCiviles v")})
public class VigenciasEstadosCiviles implements Serializable {
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
    @Column(name = "CODIGO")
    private Long codigo;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas persona;
    @JoinColumn(name = "ESTADOCIVIL", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private EstadosCiviles estadocivil;

    public VigenciasEstadosCiviles() {
    }

    public VigenciasEstadosCiviles(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasEstadosCiviles(BigDecimal secuencia, Date fechavigencia) {
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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public EstadosCiviles getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(EstadosCiviles estadocivil) {
        this.estadocivil = estadocivil;
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
        if (!(object instanceof VigenciasEstadosCiviles)) {
            return false;
        }
        VigenciasEstadosCiviles other = (VigenciasEstadosCiviles) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasEstadosCiviles[ secuencia=" + secuencia + " ]";
    }
    
}
