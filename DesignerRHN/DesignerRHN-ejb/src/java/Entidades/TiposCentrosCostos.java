/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSCENTROSCOSTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposCentrosCostos.findAll", query = "SELECT t FROM TiposCentrosCostos t")})
public class TiposCentrosCostos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocc")
    private Collection<VigenciasCuentas> vigenciasCuentasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "GRUPOTIPOCC", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private GruposTiposCC grupotipocc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocentrocosto")
    private Collection<CentrosCostos> centroscostosCollection;

    public TiposCentrosCostos() {
    }

    public TiposCentrosCostos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposCentrosCostos(BigDecimal secuencia, short codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GruposTiposCC getGrupotipocc() {
        return grupotipocc;
    }

    public void setGrupotipocc(GruposTiposCC grupotipocc) {
        this.grupotipocc = grupotipocc;
    }

    @XmlTransient
    public Collection<CentrosCostos> getCentroscostosCollection() {
        return centroscostosCollection;
    }

    public void setCentroscostosCollection(Collection<CentrosCostos> centroscostosCollection) {
        this.centroscostosCollection = centroscostosCollection;
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
        if (!(object instanceof TiposCentrosCostos)) {
            return false;
        }
        TiposCentrosCostos other = (TiposCentrosCostos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposcentroscostos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasCuentas> getVigenciasCuentasCollection() {
        return vigenciasCuentasCollection;
    }

    public void setVigenciasCuentasCollection(Collection<VigenciasCuentas> vigenciasCuentasCollection) {
        this.vigenciasCuentasCollection = vigenciasCuentasCollection;
    }
    
}
