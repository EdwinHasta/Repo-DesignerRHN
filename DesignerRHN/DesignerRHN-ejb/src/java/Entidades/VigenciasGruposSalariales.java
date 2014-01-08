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
@Table(name = "VIGENCIASGRUPOSSALARIALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasGruposSalariales.findAll", query = "SELECT v FROM VigenciasGruposSalariales v"),
    @NamedQuery(name = "VigenciasGruposSalariales.findBySecuencia", query = "SELECT v FROM VigenciasGruposSalariales v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasGruposSalariales.findByFechavigencia", query = "SELECT v FROM VigenciasGruposSalariales v WHERE v.fechavigencia = :fechavigencia"),
    @NamedQuery(name = "VigenciasGruposSalariales.findByValor", query = "SELECT v FROM VigenciasGruposSalariales v WHERE v.valor = :valor")})
public class VigenciasGruposSalariales implements Serializable {
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
    @Column(name = "VALOR")
    private BigInteger valor;
    @JoinColumn(name = "GRUPOSALARIAL", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private GruposSalariales gruposalarial;

    public VigenciasGruposSalariales() {
    }

    public VigenciasGruposSalariales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasGruposSalariales(BigInteger secuencia, Date fechavigencia, BigInteger valor) {
        this.secuencia = secuencia;
        this.fechavigencia = fechavigencia;
        this.valor = valor;
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

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public GruposSalariales getGruposalarial() {
        return gruposalarial;
    }

    public void setGruposalarial(GruposSalariales gruposalarial) {
        this.gruposalarial = gruposalarial;
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
        if (!(object instanceof VigenciasGruposSalariales)) {
            return false;
        }
        VigenciasGruposSalariales other = (VigenciasGruposSalariales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasGruposSalariales[ secuencia=" + secuencia + " ]";
    }
    
}
