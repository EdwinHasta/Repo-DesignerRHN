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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "PERSONAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p")})
public class Personas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsable")
    private Collection<EnfermeadadesProfesionales> enfermeadadesProfesionalesCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 1)
    @Column(name = "FACTORRH")
    private String factorrh;
    @Column(name = "FECHANACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechanacimiento;
    @Column(name = "FECHAVENCIMIENTOCERTIFICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavencimientocertificado;
    @Column(name = "FECHAFALLECIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafallecimiento;
    @Size(max = 2)
    @Column(name = "GRUPOSANGUINEO")
    private String gruposanguineo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PRIMERAPELLIDO")
    private String primerapellido;
    @Size(max = 20)
    @Column(name = "SEGUNDOAPELLIDO")
    private String segundoapellido;
    @Size(max = 1)
    @Column(name = "SEXO")
    private String sexo;
    @Size(max = 1)
    @Column(name = "VIVIENDAPROPIA")
    private String viviendapropia;
    @Column(name = "CLASELIBRETAMILITAR")
    private Short claselibretamilitar;
    @Column(name = "NUMEROLIBRETAMILITAR")
    private Long numerolibretamilitar;
    @Column(name = "DISTRITOMILITAR")
    private Short distritomilitar;
    @Size(max = 15)
    @Column(name = "CERTIFICADOJUDICIAL")
    private String certificadojudicial;
    @Size(max = 100)
    @Column(name = "PATHFOTO")
    private String pathfoto;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 10)
    @Column(name = "PLACAVEHICULO")
    private String placavehiculo;
    @Size(max = 20)
    @Column(name = "NUMEROMATRICULAPROF")
    private String numeromatriculaprof;
    @Column(name = "FECHAEXPMATRICULA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpmatricula;
    @Column(name = "DIGITOVERIFICACIONDOCUMENTO")
    private Short digitoverificaciondocumento;
    @Size(max = 50)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 1)
    @Column(name = "VEHICULOEMPRESA")
    private String vehiculoempresa;
    @Size(max = 30)
    @Column(name = "SEGUNDONOMBRE")
    private String segundonombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERODOCUMENTO")
    private BigInteger numerodocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<Empleados> empleadosCollection;
    @JoinColumn(name = "TIPODOCUMENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposDocumentos tipodocumento;
    @JoinColumn(name = "CIUDADDOCUMENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Ciudades ciudaddocumento;
    @JoinColumn(name = "CIUDADNACIMIENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Ciudades ciudadnacimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<MVRSPersona> mvrspersonaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jefe")
    private Collection<Soaccidentes> soaccidentesCollection;
    @OneToMany(mappedBy = "testigo")
    private Collection<Soaccidentes> soaccidentesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personareporta")
    private Collection<Soaccidentes> soaccidentesCollection2;
    @OneToMany(mappedBy = "jefearea")
    private Collection<Soaccidentes> soaccidentesCollection3;
    @OneToOne(mappedBy = "persona")
    private Usuarios usuarios;
    @OneToMany(mappedBy = "personafirmaconstancia")
    private Collection<DetallesEmpresas> detallesempresasCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private HVHojasDeVida hVHojasDeVida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<Telefonos> telefonosCollection;
    @OneToMany(mappedBy = "persona")
    private Collection<VigenciasNoFormales> vigenciasNoFormalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<VigenciasAficiones> vigenciasAficionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personafamiliar")
    private Collection<Familiares> familiaresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<Familiares> familiaresCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<IdiomasPersonas> idiomasPersonasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<VigenciasDeportes> vigenciasDeportesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<VigenciasEstadosCiviles> vigenciasEstadosCivilesCollection;
    @OneToMany(mappedBy = "persona")
    private Collection<VigenciasFormales> vigenciasFormalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona")
    private Collection<VigenciasDomiciliarias> vigenciasDomiciliariasCollection;
    @OneToMany(mappedBy = "persona")
    private Collection<Direcciones> direccionesCollection;
    @Transient
    private String nombreCompleto;
    @Transient
    private int edad;

    public Personas() {
    }

    public Personas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Personas(BigInteger secuencia, String nombre, BigInteger numerodocumento, String primerapellido) {
        this.secuencia = secuencia;
        this.nombre = nombre;
        this.numerodocumento = numerodocumento;
        this.primerapellido = primerapellido;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getFactorrh() {
        if (factorrh == null) {
            factorrh = "";
        }
        return factorrh;
    }

    public void setFactorrh(String factorrh) {
        this.factorrh = factorrh;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Date getFechavencimientocertificado() {
        return fechavencimientocertificado;
    }

    public void setFechavencimientocertificado(Date fechavencimientocertificado) {
        this.fechavencimientocertificado = fechavencimientocertificado;
    }

    public Date getFechafallecimiento() {
        return fechafallecimiento;
    }

    public void setFechafallecimiento(Date fechafallecimiento) {
        this.fechafallecimiento = fechafallecimiento;
    }

    public String getGruposanguineo() {
        if (gruposanguineo == null) {
            gruposanguineo = "";
        }
        return gruposanguineo;
    }

    public void setGruposanguineo(String gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getNombre() {
        if (nombre == null) {
            return "";
        } else {
            return nombre;
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getPrimerapellido() {
        if (primerapellido == null) {
            return "";
        } else {
            return primerapellido;
        }
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido.toUpperCase();
    }

    public String getSegundoapellido() {
        if (segundoapellido == null) {
            return "";
        } else {
            return segundoapellido;
        }
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido.toUpperCase();
    }

    public String getSexo() {
        if (sexo == null) {
            sexo = "";
        }
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getViviendapropia() {
        if (viviendapropia == null) {
            viviendapropia = "";
        }
        return viviendapropia;
    }

    public void setViviendapropia(String viviendapropia) {
        this.viviendapropia = viviendapropia;
    }

    public Short getClaselibretamilitar() {
        return claselibretamilitar;
    }

    public void setClaselibretamilitar(Short claselibretamilitar) {
        this.claselibretamilitar = claselibretamilitar;
    }

    public Long getNumerolibretamilitar() {
        return numerolibretamilitar;
    }

    public void setNumerolibretamilitar(Long numerolibretamilitar) {
        this.numerolibretamilitar = numerolibretamilitar;
    }

    public Short getDistritomilitar() {
        return distritomilitar;
    }

    public void setDistritomilitar(Short distritomilitar) {
        this.distritomilitar = distritomilitar;
    }

    public String getCertificadojudicial() {
        return certificadojudicial;
    }

    public void setCertificadojudicial(String certificadojudicial) {
        this.certificadojudicial = certificadojudicial;
    }

    public String getPathfoto() {
        return pathfoto;
    }

    public void setPathfoto(String pathfoto) {
        this.pathfoto = pathfoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlacavehiculo() {
        return placavehiculo;
    }

    public void setPlacavehiculo(String placavehiculo) {
        this.placavehiculo = placavehiculo;
    }

    public String getNumeromatriculaprof() {
        return numeromatriculaprof;
    }

    public void setNumeromatriculaprof(String numeromatriculaprof) {
        this.numeromatriculaprof = numeromatriculaprof.toUpperCase();
    }

    public Date getFechaexpmatricula() {
        return fechaexpmatricula;
    }

    public void setFechaexpmatricula(Date fechaexpmatricula) {
        this.fechaexpmatricula = fechaexpmatricula;
    }

    public Short getDigitoverificaciondocumento() {
        return digitoverificaciondocumento;
    }

    public void setDigitoverificaciondocumento(Short digitoverificaciondocumento) {
        this.digitoverificaciondocumento = digitoverificaciondocumento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.toUpperCase();
    }

    public String getVehiculoempresa() {
        if (vehiculoempresa == null) {
            vehiculoempresa = "";
        }
        return vehiculoempresa;
    }

    public void setVehiculoempresa(String vehiculoempresa) {
        this.vehiculoempresa = vehiculoempresa;
    }

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre.toUpperCase();
    }

    @XmlTransient
    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    public TiposDocumentos getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(TiposDocumentos tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Ciudades getCiudaddocumento() {
        return ciudaddocumento;
    }

    public void setCiudaddocumento(Ciudades ciudaddocumento) {
        this.ciudaddocumento = ciudaddocumento;
    }

    public Ciudades getCiudadnacimiento() {
        return ciudadnacimiento;
    }

    public void setCiudadnacimiento(Ciudades ciudadnacimiento) {
        this.ciudadnacimiento = ciudadnacimiento;
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Personas[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection() {
        return detallesempresasCollection;
    }

    public void setDetallesempresasCollection(Collection<DetallesEmpresas> detallesempresasCollection) {
        this.detallesempresasCollection = detallesempresasCollection;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombreCompleto() {
        if (nombreCompleto == null) {
            nombreCompleto = getPrimerapellido() + " " + getSegundoapellido() + " " + getNombre();
            return nombreCompleto;
        } else {
            return nombreCompleto;
        }
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        if (fechanacimiento != null) {
            Date fechaHoy = new Date();
            edad = fechaHoy.getYear() - fechanacimiento.getYear();
            if ((fechanacimiento.getMonth() - fechaHoy.getMonth()) > 0) {
                edad--;
            } else if ((fechanacimiento.getMonth() - fechaHoy.getMonth()) == 0) {
                if ((fechanacimiento.getDate() - fechaHoy.getDate()) > 0) {
                    edad--;
                }
            }
        }
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @XmlTransient
    public Collection<MVRSPersona> getMvrspersonaCollection() {
        return mvrspersonaCollection;
    }

    public void setMvrspersonaCollection(Collection<MVRSPersona> mvrspersonaCollection) {
        this.mvrspersonaCollection = mvrspersonaCollection;
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection() {
        return soaccidentesCollection;
    }

    public void setSoaccidentesCollection(Collection<Soaccidentes> soaccidentesCollection) {
        this.soaccidentesCollection = soaccidentesCollection;
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection1() {
        return soaccidentesCollection1;
    }

    public void setSoaccidentesCollection1(Collection<Soaccidentes> soaccidentesCollection1) {
        this.soaccidentesCollection1 = soaccidentesCollection1;
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection2() {
        return soaccidentesCollection2;
    }

    public void setSoaccidentesCollection2(Collection<Soaccidentes> soaccidentesCollection2) {
        this.soaccidentesCollection2 = soaccidentesCollection2;
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection3() {
        return soaccidentesCollection3;
    }

    public void setSoaccidentesCollection3(Collection<Soaccidentes> soaccidentesCollection3) {
        this.soaccidentesCollection3 = soaccidentesCollection3;
    }

    public HVHojasDeVida getHVHojasDeVida() {
        return hVHojasDeVida;
    }

    public void setHVHojasDeVida(HVHojasDeVida hVHojasDeVida) {
        this.hVHojasDeVida = hVHojasDeVida;
    }

    @XmlTransient
    public Collection<Telefonos> getTelefonosCollection() {
        return telefonosCollection;
    }

    public void setTelefonosCollection(Collection<Telefonos> telefonosCollection) {
        this.telefonosCollection = telefonosCollection;
    }

    @XmlTransient
    public Collection<VigenciasNoFormales> getVigenciasNoFormalesCollection() {
        return vigenciasNoFormalesCollection;
    }

    public void setVigenciasNoFormalesCollection(Collection<VigenciasNoFormales> vigenciasNoFormalesCollection) {
        this.vigenciasNoFormalesCollection = vigenciasNoFormalesCollection;
    }

    @XmlTransient
    public Collection<VigenciasAficiones> getVigenciasAficionesCollection() {
        return vigenciasAficionesCollection;
    }

    public void setVigenciasAficionesCollection(Collection<VigenciasAficiones> vigenciasAficionesCollection) {
        this.vigenciasAficionesCollection = vigenciasAficionesCollection;
    }

    @XmlTransient
    public Collection<Familiares> getFamiliaresCollection() {
        return familiaresCollection;
    }

    public void setFamiliaresCollection(Collection<Familiares> familiaresCollection) {
        this.familiaresCollection = familiaresCollection;
    }

    @XmlTransient
    public Collection<Familiares> getFamiliaresCollection1() {
        return familiaresCollection1;
    }

    public void setFamiliaresCollection1(Collection<Familiares> familiaresCollection1) {
        this.familiaresCollection1 = familiaresCollection1;
    }

    @XmlTransient
    public Collection<IdiomasPersonas> getIdiomasPersonasCollection() {
        return idiomasPersonasCollection;
    }

    public void setIdiomasPersonasCollection(Collection<IdiomasPersonas> idiomasPersonasCollection) {
        this.idiomasPersonasCollection = idiomasPersonasCollection;
    }

    @XmlTransient
    public Collection<VigenciasDeportes> getVigenciasDeportesCollection() {
        return vigenciasDeportesCollection;
    }

    public void setVigenciasDeportesCollection(Collection<VigenciasDeportes> vigenciasDeportesCollection) {
        this.vigenciasDeportesCollection = vigenciasDeportesCollection;
    }

    @XmlTransient
    public Collection<VigenciasEstadosCiviles> getVigenciasEstadosCivilesCollection() {
        return vigenciasEstadosCivilesCollection;
    }

    public void setVigenciasEstadosCivilesCollection(Collection<VigenciasEstadosCiviles> vigenciasEstadosCivilesCollection) {
        this.vigenciasEstadosCivilesCollection = vigenciasEstadosCivilesCollection;
    }

    @XmlTransient
    public Collection<VigenciasFormales> getVigenciasFormalesCollection() {
        return vigenciasFormalesCollection;
    }

    public void setVigenciasFormalesCollection(Collection<VigenciasFormales> vigenciasFormalesCollection) {
        this.vigenciasFormalesCollection = vigenciasFormalesCollection;
    }

    @XmlTransient
    public Collection<VigenciasDomiciliarias> getVigenciasDomiciliariasCollection() {
        return vigenciasDomiciliariasCollection;
    }

    public void setVigenciasDomiciliariasCollection(Collection<VigenciasDomiciliarias> vigenciasDomiciliariasCollection) {
        this.vigenciasDomiciliariasCollection = vigenciasDomiciliariasCollection;
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
    }

    @XmlTransient
    public Collection<EnfermeadadesProfesionales> getEnfermeadadesProfesionalesCollection() {
        return enfermeadadesProfesionalesCollection;
    }

    public void setEnfermeadadesProfesionalesCollection(Collection<EnfermeadadesProfesionales> enfermeadadesProfesionalesCollection) {
        this.enfermeadadesProfesionalesCollection = enfermeadadesProfesionalesCollection;
    }

    public BigInteger getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(BigInteger numerodocumento) {
        this.numerodocumento = numerodocumento;
    }
}
