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
@Table(name = "TERCEROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terceros.findAll", query = "SELECT t FROM Terceros t"),
    @NamedQuery(name = "Terceros.findBySecuencia", query = "SELECT t FROM Terceros t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "Terceros.findByNit", query = "SELECT t FROM Terceros t WHERE t.nit = :nit"),
    @NamedQuery(name = "Terceros.findByCodigoalternativo", query = "SELECT t FROM Terceros t WHERE t.codigoalternativo = :codigoalternativo"),
    @NamedQuery(name = "Terceros.findByNombre", query = "SELECT t FROM Terceros t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Terceros.findByDigitoverificacion", query = "SELECT t FROM Terceros t WHERE t.digitoverificacion = :digitoverificacion"),
    @NamedQuery(name = "Terceros.findByCodigo", query = "SELECT t FROM Terceros t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Terceros.findByCodigoss", query = "SELECT t FROM Terceros t WHERE t.codigoss = :codigoss"),
    @NamedQuery(name = "Terceros.findByCodigosp", query = "SELECT t FROM Terceros t WHERE t.codigosp = :codigosp"),
    @NamedQuery(name = "Terceros.findByCodigosc", query = "SELECT t FROM Terceros t WHERE t.codigosc = :codigosc"),
    @NamedQuery(name = "Terceros.findByNitalternativo", query = "SELECT t FROM Terceros t WHERE t.nitalternativo = :nitalternativo"),
    @NamedQuery(name = "Terceros.findByCodigoalternativodeudor", query = "SELECT t FROM Terceros t WHERE t.codigoalternativodeudor = :codigoalternativodeudor"),
    @NamedQuery(name = "Terceros.findByTiponit", query = "SELECT t FROM Terceros t WHERE t.tiponit = :tiponit"),
    @NamedQuery(name = "Terceros.findByCodigotercerosap", query = "SELECT t FROM Terceros t WHERE t.codigotercerosap = :codigotercerosap")})
public class Terceros implements Serializable {

    @OneToMany(mappedBy = "tercero")
    private Collection<Soausentismos> soausentismosCollection;
    @OneToMany(mappedBy = "tercero")
    private Collection<NovedadesSistema> novedadessistemaCollection;
    @OneToMany(mappedBy = "nit")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @OneToMany(mappedBy = "tercero")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tercero")
    private Collection<TercerosSucursales> tercerossucursalesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIT")
    private long nit;
    @Column(name = "CODIGOALTERNATIVO")
    private Long codigoalternativo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIGITOVERIFICACION")
    private Short digitoverificacion;
    @Column(name = "CODIGO")
    private Long codigo;
    @Size(max = 6)
    @Column(name = "CODIGOSS")
    private String codigoss;
    @Size(max = 6)
    @Column(name = "CODIGOSP")
    private String codigosp;
    @Size(max = 6)
    @Column(name = "CODIGOSC")
    private String codigosc;
    @Size(max = 30)
    @Column(name = "NITALTERNATIVO")
    private String nitalternativo;
    @Column(name = "CODIGOALTERNATIVODEUDOR")
    private Long codigoalternativodeudor;
    @Size(max = 1)
    @Column(name = "TIPONIT")
    private String tiponit;
    @Size(max = 5)
    @Column(name = "CODIGOTERCEROSAP")
    private String codigotercerosap;
    @OneToMany(mappedBy = "terceroconsolidador")
    private Collection<Terceros> tercerosCollection;
    @JoinColumn(name = "TERCEROCONSOLIDADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros terceroconsolidador;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Ciudades ciudad;
    @JoinColumn(name = "CENTROCOSTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocosto;
    @OneToMany(mappedBy = "tercero")
    private Collection<Conceptos> conceptosCollection;

    public Terceros() {
    }

    public Terceros(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Terceros(BigDecimal secuencia, long nit, String nombre) {
        this.secuencia = secuencia;
        this.nit = nit;
        this.nombre = nombre;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public long getNit() {
        return nit;
    }

    public void setNit(long nit) {
        this.nit = nit;
    }

    public Long getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(Long codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public String getNombre() {
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getDigitoverificacion() {
        return digitoverificacion;
    }

    public void setDigitoverificacion(Short digitoverificacion) {
        this.digitoverificacion = digitoverificacion;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCodigoss() {
        return codigoss;
    }

    public void setCodigoss(String codigoss) {
        this.codigoss = codigoss;
    }

    public String getCodigosp() {
        return codigosp;
    }

    public void setCodigosp(String codigosp) {
        this.codigosp = codigosp;
    }

    public String getCodigosc() {
        return codigosc;
    }

    public void setCodigosc(String codigosc) {
        this.codigosc = codigosc;
    }

    public String getNitalternativo() {
        return nitalternativo;
    }

    public void setNitalternativo(String nitalternativo) {
        this.nitalternativo = nitalternativo;
    }

    public Long getCodigoalternativodeudor() {
        return codigoalternativodeudor;
    }

    public void setCodigoalternativodeudor(Long codigoalternativodeudor) {
        this.codigoalternativodeudor = codigoalternativodeudor;
    }

    public String getTiponit() {
        return tiponit;
    }

    public void setTiponit(String tiponit) {
        this.tiponit = tiponit;
    }

    public String getCodigotercerosap() {
        return codigotercerosap;
    }

    public void setCodigotercerosap(String codigotercerosap) {
        this.codigotercerosap = codigotercerosap;
    }

    @XmlTransient
    public Collection<Terceros> getTercerosCollection() {
        return tercerosCollection;
    }

    public void setTercerosCollection(Collection<Terceros> tercerosCollection) {
        this.tercerosCollection = tercerosCollection;
    }

    public Terceros getTerceroconsolidador() {
        return terceroconsolidador;
    }

    public void setTerceroconsolidador(Terceros terceroconsolidador) {
        this.terceroconsolidador = terceroconsolidador;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
    }

    public CentrosCostos getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(CentrosCostos centrocosto) {
        this.centrocosto = centrocosto;
    }

    @XmlTransient
    public Collection<Conceptos> getConceptosCollection() {
        return conceptosCollection;
    }

    public void setConceptosCollection(Collection<Conceptos> conceptosCollection) {
        this.conceptosCollection = conceptosCollection;
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
        if (!(object instanceof Terceros)) {
            return false;
        }
        Terceros other = (Terceros) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Terceros[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<TercerosSucursales> getTercerossucursalesCollection() {
        return tercerossucursalesCollection;
    }

    public void setTercerossucursalesCollection(Collection<TercerosSucursales> tercerossucursalesCollection) {
        this.tercerossucursalesCollection = tercerossucursalesCollection;
    }

    @XmlTransient
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
    }

    @XmlTransient
    public Collection<NovedadesSistema> getNovedadessistemaCollection() {
        return novedadessistemaCollection;
    }

    public void setNovedadessistemaCollection(Collection<NovedadesSistema> novedadessistemaCollection) {
        this.novedadessistemaCollection = novedadessistemaCollection;
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<SolucionesNodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }
}
