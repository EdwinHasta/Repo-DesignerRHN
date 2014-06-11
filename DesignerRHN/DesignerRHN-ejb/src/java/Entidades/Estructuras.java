/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ESTRUCTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estructuras.findAll", query = "SELECT e FROM Estructuras e"),
    @NamedQuery(name = "Estructuras.findFiltradas", query = "SELECT es FROM Estructuras es where (es.centrocosto.obsoleto ='N' or es.centrocosto.obsoleto is null)"),
    @NamedQuery(name = "Estructuras.findBySecOrganigrama", query = "SELECT es FROM Estructuras es where (es.centrocosto.obsoleto ='N' or es.centrocosto.obsoleto is null) AND es.organigrama.secuencia = :secOrganigrama"),
    @NamedQuery(name = "Estructuras.findByOrganigrama", query = "SELECT e FROM Estructuras e where e.organigrama = :Organigrama")})

public class Estructuras implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estructuras")
    private List<VigenciasArps> vigenciasArpsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estructura")
    private Collection<PlantasPersonales> plantasPersonalesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estructura")
    private Collection<Pdgpoliticas> pdgpoliticasCollection;
    @OneToMany(mappedBy = "localizacion")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    @OneToMany(mappedBy = "estructura")
    private Collection<Evalconvocatorias> evalconvocatoriasCollection;
    @OneToMany(mappedBy = "estructura")
    private Collection<Encargaturas> encargaturasCollection;
    @OneToMany(mappedBy = "estructura")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @OneToMany(mappedBy = "localizacion")
    private Collection<SolucionesNodos> solucionesnodosCollection1;
    @OneToMany(mappedBy = "estructura")
    private Collection<ParametrosEstructuras> parametrosestructurasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Long codigo;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 20)
    @Column(name = "CODIGOALFA")
    private String codigoalfa;
    @Size(max = 20)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @JoinColumn(name = "ORGANIGRAMA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Organigramas organigrama;
    @OneToMany(mappedBy = "estructurapadre")
    private Collection<Estructuras> estructurasCollection;
    @JoinColumn(name = "ESTRUCTURAPADRE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Estructuras estructurapadre;
    @JoinColumn(name = "CENTROCOSTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocosto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estructura")
    private Collection<VigenciasCargos> vigenciascargosCollection;
    @Transient
    private String cantidadCargosControlar;
    @Transient
    private String cantidadCargosEmplActivos;

    public Estructuras() {
    }

    public Estructuras(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Estructuras(BigInteger secuencia, String nombre) {
        this.secuencia = secuencia;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getCodigoalfa() {
        return codigoalfa;
    }

    public void setCodigoalfa(String codigoalfa) {
        this.codigoalfa = codigoalfa;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public Organigramas getOrganigrama() {
        return organigrama;
    }

    public void setOrganigrama(Organigramas organigrama) {
        this.organigrama = organigrama;
    }

    @XmlTransient
    public Collection<Estructuras> getEstructurasCollection() {
        return estructurasCollection;
    }

    public void setEstructurasCollection(Collection<Estructuras> estructurasCollection) {
        this.estructurasCollection = estructurasCollection;
    }

    public Estructuras getEstructurapadre() {
        return estructurapadre;
    }

    public void setEstructurapadre(Estructuras estructurapadre) {
        this.estructurapadre = estructurapadre;
    }

    public CentrosCostos getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(CentrosCostos centrocosto) {
        this.centrocosto = centrocosto;
    }

    public String getCantidadCargosControlar() {
        return cantidadCargosControlar;
    }

    public void setCantidadCargosControlar(String cantidadCargosControlar) {
        this.cantidadCargosControlar = cantidadCargosControlar;
    }

    public String getCantidadCargosEmplActivos() {
        return cantidadCargosEmplActivos;
    }

    public void setCantidadCargosEmplActivos(String cantidadCargosEmplActivos) {
        this.cantidadCargosEmplActivos = cantidadCargosEmplActivos;
    }

    @XmlTransient
    public Collection<VigenciasCargos> getVigenciascargosCollection() {
        return vigenciascargosCollection;
    }

    public void setVigenciascargosCollection(Collection<VigenciasCargos> vigenciascargosCollection) {
        this.vigenciascargosCollection = vigenciascargosCollection;
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
        if (!(object instanceof Estructuras)) {
            return false;
        }
        Estructuras other = (Estructuras) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Estructuras[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ParametrosEstructuras> getParametrosestructurasCollection() {
        return parametrosestructurasCollection;
    }

    public void setParametrosestructurasCollection(Collection<ParametrosEstructuras> parametrosestructurasCollection) {
        this.parametrosestructurasCollection = parametrosestructurasCollection;
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<SolucionesNodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection1() {
        return solucionesnodosCollection1;
    }

    public void setSolucionesnodosCollection1(Collection<SolucionesNodos> solucionesnodosCollection1) {
        this.solucionesnodosCollection1 = solucionesnodosCollection1;
    }

    @XmlTransient
    public Collection<Encargaturas> getEncargaturasCollection() {
        return encargaturasCollection;
    }

    public void setEncargaturasCollection(Collection<Encargaturas> encargaturasCollection) {
        this.encargaturasCollection = encargaturasCollection;
    }

    public Collection<Evalconvocatorias> getEvalconvocatoriasCollection() {
        return evalconvocatoriasCollection;
    }

    public void setEvalconvocatoriasCollection(Collection<Evalconvocatorias> evalconvocatoriasCollection) {
        this.evalconvocatoriasCollection = evalconvocatoriasCollection;
    }

    @XmlTransient
    public Collection<Pdgpoliticas> getPdgpoliticasCollection() {
        return pdgpoliticasCollection;
    }

    public void setPdgpoliticasCollection(Collection<Pdgpoliticas> pdgpoliticasCollection) {
        this.pdgpoliticasCollection = pdgpoliticasCollection;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }

    @XmlTransient
    public Collection<PlantasPersonales> getPlantasPersonalesCollection() {
        return plantasPersonalesCollection;
    }

    public void setPlantasPersonalesCollection(Collection<PlantasPersonales> plantasPersonalesCollection) {
        this.plantasPersonalesCollection = plantasPersonalesCollection;
    }

    @XmlTransient
    public List<VigenciasArps> getVigenciasArpsList() {
        return vigenciasArpsList;
    }

    public void setVigenciasArpsList(List<VigenciasArps> vigenciasArpsList) {
        this.vigenciasArpsList = vigenciasArpsList;
    }
}
