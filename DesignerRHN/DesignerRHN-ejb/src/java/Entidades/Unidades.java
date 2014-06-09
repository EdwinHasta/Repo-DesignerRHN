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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "UNIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidades.findAll", query = "SELECT u FROM Unidades u")})
public class Unidades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 4)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidad")
    private Collection<Periodicidades> periodicidadesCollection;
    @OneToMany(mappedBy = "unidadbase")
    private Collection<Periodicidades> periodicidadesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadpago")
    private Collection<VigenciasSueldos> vigenciassueldosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidad")
    private Collection<Conceptos> conceptosCollection;
    @JoinColumn(name = "TIPOUNIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposUnidades tipounidad;

    public Unidades() {
    }

    public Unidades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Unidades(BigInteger secuencia, String nombre) {
        this.secuencia = secuencia;
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
        this.nombre = nombre;
    }

    public String getCodigo() {
        if (codigo == null) {
            codigo = " ";
        }
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<Periodicidades> getPeriodicidadesCollection() {
        return periodicidadesCollection;
    }

    public void setPeriodicidadesCollection(Collection<Periodicidades> periodicidadesCollection) {
        this.periodicidadesCollection = periodicidadesCollection;
    }

    @XmlTransient
    public Collection<Periodicidades> getPeriodicidadesCollection1() {
        return periodicidadesCollection1;
    }

    public void setPeriodicidadesCollection1(Collection<Periodicidades> periodicidadesCollection1) {
        this.periodicidadesCollection1 = periodicidadesCollection1;
    }

    @XmlTransient
    public Collection<VigenciasSueldos> getVigenciassueldosCollection() {
        return vigenciassueldosCollection;
    }

    public void setVigenciassueldosCollection(Collection<VigenciasSueldos> vigenciassueldosCollection) {
        this.vigenciassueldosCollection = vigenciassueldosCollection;
    }

    @XmlTransient
    public Collection<Conceptos> getConceptosCollection() {
        return conceptosCollection;
    }

    public void setConceptosCollection(Collection<Conceptos> conceptosCollection) {
        this.conceptosCollection = conceptosCollection;
    }

    public TiposUnidades getTipounidad() {
        return tipounidad;
    }

    public void setTipounidad(TiposUnidades tipounidad) {
        this.tipounidad = tipounidad;
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
        if (!(object instanceof Unidades)) {
            return false;
        }
        Unidades other = (Unidades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Unidades[ secuencia=" + secuencia + " ]";
    }
}
