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
import javax.persistence.OneToOne;
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
@Table(name = "RETIRADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retirados.findAll", query = "SELECT r FROM Retirados r"),
    @NamedQuery(name = "Retirados.findBySecuencia", query = "SELECT r FROM Retirados r WHERE r.secuencia = :secuencia"),
    @NamedQuery(name = "Retirados.findByFecharetiro", query = "SELECT r FROM Retirados r WHERE r.fecharetiro = :fecharetiro"),
    @NamedQuery(name = "Retirados.findByDescripcion", query = "SELECT r FROM Retirados r WHERE r.descripcion = :descripcion")})
public class Retirados implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHARETIRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharetiro;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "VIGENCIATIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @OneToOne
    private VigenciasTiposTrabajadores vigenciatipotrabajador;
    @JoinColumn(name = "MOTIVORETIRO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private MotivosRetiros motivoretiros;

    public Retirados() {
    }

    public Retirados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Retirados(BigInteger secuencia, Date fecharetiro) {
        this.secuencia = secuencia;
        this.fecharetiro = fecharetiro;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFecharetiro() {
        return fecharetiro;
    }

    public void setFecharetiro(Date fecharetiro) {
        this.fecharetiro = fecharetiro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public VigenciasTiposTrabajadores getVigenciatipotrabajador() {
        return vigenciatipotrabajador;
    }

    public void setVigenciatipotrabajador(VigenciasTiposTrabajadores vigenciatipotrabajador) {
        this.vigenciatipotrabajador = vigenciatipotrabajador;
    }

    public MotivosRetiros getMotivoretiro() {
        return motivoretiros;
    }

    public void setMotivoretiro(MotivosRetiros motivoretiro) {
        this.motivoretiros = motivoretiro;
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
        if (!(object instanceof Retirados)) {
            return false;
        }
        Retirados other = (Retirados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Retirados[ secuencia=" + secuencia + " ]";
    }
}
