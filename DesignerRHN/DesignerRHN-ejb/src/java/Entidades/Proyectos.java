/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PROYECTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyectos.findAll", query = "SELECT p FROM Proyectos p"),
    @NamedQuery(name = "Proyectos.findBySecuencia", query = "SELECT p FROM Proyectos p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "Proyectos.findByNombreproyecto", query = "SELECT p FROM Proyectos p WHERE p.nombreproyecto = :nombreproyecto"),
    @NamedQuery(name = "Proyectos.findByDescripcionproyecto", query = "SELECT p FROM Proyectos p WHERE p.descripcionproyecto = :descripcionproyecto"),
    @NamedQuery(name = "Proyectos.findByMonto", query = "SELECT p FROM Proyectos p WHERE p.monto = :monto"),
    @NamedQuery(name = "Proyectos.findByCantidadpersonas", query = "SELECT p FROM Proyectos p WHERE p.cantidadpersonas = :cantidadpersonas"),
    @NamedQuery(name = "Proyectos.findByFechainicial", query = "SELECT p FROM Proyectos p WHERE p.fechainicial = :fechainicial"),
    @NamedQuery(name = "Proyectos.findByFechafinal", query = "SELECT p FROM Proyectos p WHERE p.fechafinal = :fechafinal"),
    @NamedQuery(name = "Proyectos.findByCodigo", query = "SELECT p FROM Proyectos p WHERE p.codigo = :codigo")})
public class Proyectos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private Collection<VigenciasProyectos> vigenciasProyectosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto")
    private Collection<VigenciasProrrateosProyectos> vigenciasprorrateosproyectosCollection;
    @OneToMany(mappedBy = "proyecto")
    private Collection<VigenciasProrrateos> vigenciasprorrateosCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "NOMBREPROYECTO")
    private String nombreproyecto;
    @Size(max = 200)
    @Column(name = "DESCRIPCIONPROYECTO")
    private String descripcionproyecto;
    @Column(name = "MONTO")
    private BigInteger monto;
    @Column(name = "CANTIDADPERSONAS")
    private BigInteger cantidadpersonas;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(mappedBy = "proyecto")
    private Collection<VigenciasLocalizaciones> vigenciaslocalizacionesCollection;
    @JoinColumn(name = "PRY_PLATAFORMA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private PryPlataformas pryPlataforma;
    @JoinColumn(name = "PRY_CLIENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private PryClientes pryCliente;
    @JoinColumn(name = "TIPOMONEDA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Monedas tipomoneda;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;

    public Proyectos() {
    }

    public Proyectos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Proyectos(BigInteger secuencia, String nombreproyecto, String codigo) {
        this.secuencia = secuencia;
        this.nombreproyecto = nombreproyecto;
        this.codigo = codigo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombreproyecto() {
            return nombreproyecto;
    }

    public void setNombreproyecto(String nombreproyecto) {
        this.nombreproyecto = nombreproyecto;
    }

    public String getDescripcionproyecto() {
        if(descripcionproyecto!=null){
            return descripcionproyecto.toUpperCase();
        }
        return descripcionproyecto;
    }

    public void setDescripcionproyecto(String descripcionproyecto) {
        this.descripcionproyecto = descripcionproyecto.toUpperCase();
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public BigInteger getCantidadpersonas() {
        return cantidadpersonas;
    }

    public void setCantidadpersonas(BigInteger cantidadpersonas) {
        this.cantidadpersonas = cantidadpersonas;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<VigenciasLocalizaciones> getVigenciaslocalizacionesCollection() {
        return vigenciaslocalizacionesCollection;
    }

    public void setVigenciaslocalizacionesCollection(Collection<VigenciasLocalizaciones> vigenciaslocalizacionesCollection) {
        this.vigenciaslocalizacionesCollection = vigenciaslocalizacionesCollection;
    }

    public PryPlataformas getPryPlataforma() {
        return pryPlataforma;
    }

    public void setPryPlataforma(PryPlataformas pryPlataforma) {
        this.pryPlataforma = pryPlataforma;
    }

    public PryClientes getPryCliente() {
        return pryCliente;
    }

    public void setPryCliente(PryClientes pryCliente) {
        this.pryCliente = pryCliente;
    }

    public Monedas getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(Monedas tipomoneda) {
        this.tipomoneda = tipomoneda;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof Proyectos)) {
            return false;
        }
        Proyectos other = (Proyectos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Proyectos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasProrrateos> getVigenciasprorrateosCollection() {
        return vigenciasprorrateosCollection;
    }

    public void setVigenciasprorrateosCollection(Collection<VigenciasProrrateos> vigenciasprorrateosCollection) {
        this.vigenciasprorrateosCollection = vigenciasprorrateosCollection;
    }

    @XmlTransient
    public Collection<VigenciasProrrateosProyectos> getVigenciasprorrateosproyectosCollection() {
        return vigenciasprorrateosproyectosCollection;
    }

    public void setVigenciasprorrateosproyectosCollection(Collection<VigenciasProrrateosProyectos> vigenciasprorrateosproyectosCollection) {
        this.vigenciasprorrateosproyectosCollection = vigenciasprorrateosproyectosCollection;
    }
    
    public Collection<VigenciasProyectos> getVigenciasProyectosCollection() {
        return vigenciasProyectosCollection;
    }

    public void setVigenciasProyectosCollection(Collection<VigenciasProyectos> vigenciasProyectosCollection) {
        this.vigenciasProyectosCollection = vigenciasProyectosCollection;
    }
}
