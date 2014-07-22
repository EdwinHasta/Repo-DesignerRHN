/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
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

    @Column(name = "CODIGO")
    private Integer codigo;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "GRUPOTIPOCC", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private GruposTiposCC grupotipocc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocentrocosto")
    private Collection<CentrosCostos> centroscostosCollection;

    public TiposCentrosCostos() {
    }

    public TiposCentrosCostos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposCentrosCostos(BigInteger secuencia, Integer codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            this.nombre = nombre;
        } else {
            this.nombre = nombre.toUpperCase();
        }

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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

}
