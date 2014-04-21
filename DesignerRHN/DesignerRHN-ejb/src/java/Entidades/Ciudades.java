/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CIUDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudades.findAll", query = "SELECT c FROM Ciudades c")})
public class Ciudades implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudad")
    private Collection<Juzgados> juzgadosCollection;
    @OneToMany(mappedBy = "ciudad")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    @OneToMany(mappedBy = "ciudad")
    private Collection<Telefonos> telefonosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudad")
    private Collection<Direcciones> direccionesCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "SECUENCIA", nullable = true)
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 4)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @JoinColumn(name = "DEPARTAMENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Departamentos departamento;
    @OneToMany(mappedBy = "ciudad")
    private Collection<Terceros> tercerosCollection;
    @OneToMany(mappedBy = "ciudaddocumento")
    private Collection<Personas> personasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudadnacimiento")
    private Collection<Personas> personasCollection1;
    @OneToMany(mappedBy = "ciudad")
    private Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudad")
    private Collection<DetallesEmpresas> detallesempresasCollection;
    @OneToMany(mappedBy = "ciudaddocumentorepresentante")
    private Collection<DetallesEmpresas> detallesempresasCollection1;
    @OneToMany(mappedBy = "ciudad")
    private Collection<UbicacionesGeograficas> ubicacionesgeograficasCollection;
    @OneToMany(mappedBy = "ciudad")
    private Collection<TercerosSucursales> tercerossucursalesCollection;

    public Ciudades() {
    }

    public Ciudades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Ciudades(BigInteger secuencia, String nombre) {
        this.secuencia = secuencia;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        //if (nombre == null) {
       //     nombre = " ";
            return nombre;
        //} else {
         //   return nombre.toUpperCase();
       // }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public Departamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamentos departamento) {
        this.departamento = departamento;
    }

    @XmlTransient
    public Collection<Terceros> getTercerosCollection() {
        return tercerosCollection;
    }

    public void setTercerosCollection(Collection<Terceros> tercerosCollection) {
        this.tercerosCollection = tercerosCollection;
    }

    @XmlTransient
    public Collection<Personas> getPersonasCollection() {
        return personasCollection;
    }

    public void setPersonasCollection(Collection<Personas> personasCollection) {
        this.personasCollection = personasCollection;
    }

    @XmlTransient
    public Collection<Personas> getPersonasCollection1() {
        return personasCollection1;
    }

    public void setPersonasCollection1(Collection<Personas> personasCollection1) {
        this.personasCollection1 = personasCollection1;
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
        if (!(object instanceof Ciudades)) {
            return false;
        }
        Ciudades other = (Ciudades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ciudades[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<TercerosSucursales> getTercerossucursalesCollection() {
        return tercerossucursalesCollection;
    }

    public void setTercerossucursalesCollection(Collection<TercerosSucursales> tercerossucursalesCollection) {
        this.tercerossucursalesCollection = tercerossucursalesCollection;
    }

    @XmlTransient
    public Collection<UbicacionesGeograficas> getUbicacionesgeograficasCollection() {
        return ubicacionesgeograficasCollection;
    }

    public void setUbicacionesgeograficasCollection(Collection<UbicacionesGeograficas> ubicacionesgeograficasCollection) {
        this.ubicacionesgeograficasCollection = ubicacionesgeograficasCollection;
    }

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection() {
        return detallesempresasCollection;
    }

    public void setDetallesempresasCollection(Collection<DetallesEmpresas> detallesempresasCollection) {
        this.detallesempresasCollection = detallesempresasCollection;
    }

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection1() {
        return detallesempresasCollection1;
    }

    public void setDetallesempresasCollection1(Collection<DetallesEmpresas> detallesempresasCollection1) {
        this.detallesempresasCollection1 = detallesempresasCollection1;
    }

    public Collection<VigenciasTiposContratos> getVigenciastiposcontratosCollection() {
        return vigenciastiposcontratosCollection;
    }

    public void setVigenciastiposcontratosCollection(Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection) {
        this.vigenciastiposcontratosCollection = vigenciastiposcontratosCollection;
    }

    @XmlTransient
    public Collection<Telefonos> getTelefonosCollection() {
        return telefonosCollection;
    }

    public void setTelefonosCollection(Collection<Telefonos> telefonosCollection) {
        this.telefonosCollection = telefonosCollection;
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }

    @XmlTransient
    public Collection<Juzgados> getJuzgadosCollection() {
        return juzgadosCollection;
    }

    public void setJuzgadosCollection(Collection<Juzgados> juzgadosCollection) {
        this.juzgadosCollection = juzgadosCollection;
    }
}
