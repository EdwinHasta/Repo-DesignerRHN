

package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "TSGRUPOSTIPOSENTIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TSGruposTiposEntidades.findAll", query = "SELECT t FROM TSGruposTiposEntidades t"),
    @NamedQuery(name = "TSGruposTiposEntidades.findBySecuencia", query = "SELECT t FROM TSGruposTiposEntidades t WHERE t.secuencia = :secuencia")})
public class TSGruposTiposEntidades implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @JoinColumn(name = "TIPOSUELDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposSueldos tiposueldo;
    @JoinColumn(name = "GRUPOTIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Grupostiposentidades grupotipoentidad;
    @OneToMany(mappedBy = "tsgrupotipoentidad")
    private Collection<TEFormulasConceptos> tEFormulasConceptosCollection;

    public TSGruposTiposEntidades() {
    }

    public TSGruposTiposEntidades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposSueldos getTiposueldo() {
        return tiposueldo;
    }

    public void setTiposueldo(TiposSueldos tiposueldo) {
        this.tiposueldo = tiposueldo;
    }

    public Grupostiposentidades getGrupotipoentidad() {
        return grupotipoentidad;
    }

    public void setGrupotipoentidad(Grupostiposentidades grupotipoentidad) {
        this.grupotipoentidad = grupotipoentidad;
    }

    @XmlTransient
    public Collection<TEFormulasConceptos> getTEFormulasConceptosCollection() {
        return tEFormulasConceptosCollection;
    }

    public void setTEFormulasConceptosCollection(Collection<TEFormulasConceptos> tEFormulasConceptosCollection) {
        this.tEFormulasConceptosCollection = tEFormulasConceptosCollection;
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
        if (!(object instanceof TSGruposTiposEntidades)) {
            return false;
        }
        TSGruposTiposEntidades other = (TSGruposTiposEntidades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TSGruposTiposEntidades[ secuencia=" + secuencia + " ]";
    }
    
}
