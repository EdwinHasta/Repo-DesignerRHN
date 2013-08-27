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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "DETALLESEMPRESAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesEmpresas.findAll", query = "SELECT d FROM DetallesEmpresas d")})
public class DetallesEmpresas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELEFONO")
    private String telefono;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FAX")
    private String fax;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBREREPRESENTANTE")
    private String nombrerepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DOCUMENTOREPRESENTANTE")
    private String documentorepresentante;
    @Size(max = 5)
    @Column(name = "TIPONIT")
    private String tiponit;
    @Column(name = "DIGITOVERIFICACION")
    private Short digitoverificacion;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 10)
    @Column(name = "ZONA")
    private String zona;
    @Size(max = 200)
    @Column(name = "ACTIVIDADECONOMICA")
    private String actividadeconomica;
    @Column(name = "FECHAPENSIONES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapensiones;
    @Column(name = "SUCURSAL")
    private BigInteger sucursal;
    @Size(max = 2)
    @Column(name = "TIPODOCUMENTO")
    private String tipodocumento;
    @Size(max = 1)
    @Column(name = "CLASEAPORTANTE")
    private String claseaportante;
    @Size(max = 1)
    @Column(name = "NATURALEZAJURIDICA")
    private String naturalezajuridica;
    @Size(max = 1)
    @Column(name = "TIPOPERSONA")
    private String tipopersona;
    @Size(max = 1)
    @Column(name = "FORMAPRESENTACION")
    private String formapresentacion;
    @Column(name = "TIPOAPORTANTE")
    private BigInteger tipoaportante;
    @Column(name = "TIPOACCION")
    private BigInteger tipoaccion;
    @Size(max = 10)
    @Column(name = "CIIU")
    private String ciiu;
    @Size(max = 100)
    @Column(name = "NOMBREARQUITECTO")
    private String nombrearquitecto;
    @Size(max = 50)
    @Column(name = "CARGOARQUITECTO")
    private String cargoarquitecto;
    @Size(max = 20)
    @Column(name = "PILAULTIMAPLANILLA")
    private String pilaultimaplanilla;
    @Size(max = 1)
    @Column(name = "AFILIADOARP")
    private String afiliadoarp;
    @Size(max = 1)
    @Column(name = "AFILIADOCCF")
    private String afiliadoccf;
    @Column(name = "TERCEROARP")
    private BigInteger terceroarp;
    @Column(name = "TERCEROCCF")
    private BigInteger terceroccf;
    @Column(name = "TIPOACTIVIDADECONOMICA")
    private BigInteger tipoactividadeconomica;
    @Column(name = "FECHACAMARACOMERCIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacamaracomercio;
    @Column(name = "ANOSINICIALESEXENTOPRF")
    private Short anosinicialesexentoprf;
    @JoinColumn(name = "PERSONAFIRMACONSTANCIA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Personas personafirmaconstancia;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @OneToOne(optional = false)
    private Empresas empresa;
    @JoinColumn(name = "REPRESENTANTECIR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados representantecir;
    @JoinColumn(name = "SUBGERENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados subgerente;
    @JoinColumn(name = "ARQUITECTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados arquitecto;
    @JoinColumn(name = "GERENTEGENERAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empleados gerentegeneral;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Ciudades ciudad;
    @JoinColumn(name = "CIUDADDOCUMENTOREPRESENTANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Ciudades ciudaddocumentorepresentante;
    @JoinColumn(name = "CARGOFIRMACONSTANCIA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cargos cargofirmaconstancia;

    public DetallesEmpresas() {
    }

    public DetallesEmpresas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public DetallesEmpresas(BigDecimal secuencia, String tipo, String direccion, String telefono, String fax, String nombrerepresentante, String documentorepresentante) {
        this.secuencia = secuencia;
        this.tipo = tipo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.nombrerepresentante = nombrerepresentante;
        this.documentorepresentante = documentorepresentante;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNombrerepresentante() {
        return nombrerepresentante;
    }

    public void setNombrerepresentante(String nombrerepresentante) {
        this.nombrerepresentante = nombrerepresentante;
    }

    public String getDocumentorepresentante() {
        return documentorepresentante;
    }

    public void setDocumentorepresentante(String documentorepresentante) {
        this.documentorepresentante = documentorepresentante;
    }

    public String getTiponit() {
        return tiponit;
    }

    public void setTiponit(String tiponit) {
        this.tiponit = tiponit;
    }

    public Short getDigitoverificacion() {
        return digitoverificacion;
    }

    public void setDigitoverificacion(Short digitoverificacion) {
        this.digitoverificacion = digitoverificacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getActividadeconomica() {
        return actividadeconomica;
    }

    public void setActividadeconomica(String actividadeconomica) {
        this.actividadeconomica = actividadeconomica;
    }

    public Date getFechapensiones() {
        return fechapensiones;
    }

    public void setFechapensiones(Date fechapensiones) {
        this.fechapensiones = fechapensiones;
    }

    public BigInteger getSucursal() {
        return sucursal;
    }

    public void setSucursal(BigInteger sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getClaseaportante() {
        return claseaportante;
    }

    public void setClaseaportante(String claseaportante) {
        this.claseaportante = claseaportante;
    }

    public String getNaturalezajuridica() {
        return naturalezajuridica;
    }

    public void setNaturalezajuridica(String naturalezajuridica) {
        this.naturalezajuridica = naturalezajuridica;
    }

    public String getTipopersona() {
        return tipopersona;
    }

    public void setTipopersona(String tipopersona) {
        this.tipopersona = tipopersona;
    }

    public String getFormapresentacion() {
        return formapresentacion;
    }

    public void setFormapresentacion(String formapresentacion) {
        this.formapresentacion = formapresentacion;
    }

    public BigInteger getTipoaportante() {
        return tipoaportante;
    }

    public void setTipoaportante(BigInteger tipoaportante) {
        this.tipoaportante = tipoaportante;
    }

    public BigInteger getTipoaccion() {
        return tipoaccion;
    }

    public void setTipoaccion(BigInteger tipoaccion) {
        this.tipoaccion = tipoaccion;
    }

    public String getCiiu() {
        return ciiu;
    }

    public void setCiiu(String ciiu) {
        this.ciiu = ciiu;
    }

    public String getNombrearquitecto() {
        return nombrearquitecto;
    }

    public void setNombrearquitecto(String nombrearquitecto) {
        this.nombrearquitecto = nombrearquitecto;
    }

    public String getCargoarquitecto() {
        return cargoarquitecto;
    }

    public void setCargoarquitecto(String cargoarquitecto) {
        this.cargoarquitecto = cargoarquitecto;
    }

    public String getPilaultimaplanilla() {
        return pilaultimaplanilla;
    }

    public void setPilaultimaplanilla(String pilaultimaplanilla) {
        this.pilaultimaplanilla = pilaultimaplanilla;
    }

    public String getAfiliadoarp() {
        return afiliadoarp;
    }

    public void setAfiliadoarp(String afiliadoarp) {
        this.afiliadoarp = afiliadoarp;
    }

    public String getAfiliadoccf() {
        return afiliadoccf;
    }

    public void setAfiliadoccf(String afiliadoccf) {
        this.afiliadoccf = afiliadoccf;
    }

    public BigInteger getTerceroarp() {
        return terceroarp;
    }

    public void setTerceroarp(BigInteger terceroarp) {
        this.terceroarp = terceroarp;
    }

    public BigInteger getTerceroccf() {
        return terceroccf;
    }

    public void setTerceroccf(BigInteger terceroccf) {
        this.terceroccf = terceroccf;
    }

    public BigInteger getTipoactividadeconomica() {
        return tipoactividadeconomica;
    }

    public void setTipoactividadeconomica(BigInteger tipoactividadeconomica) {
        this.tipoactividadeconomica = tipoactividadeconomica;
    }

    public Date getFechacamaracomercio() {
        return fechacamaracomercio;
    }

    public void setFechacamaracomercio(Date fechacamaracomercio) {
        this.fechacamaracomercio = fechacamaracomercio;
    }

    public Short getAnosinicialesexentoprf() {
        return anosinicialesexentoprf;
    }

    public void setAnosinicialesexentoprf(Short anosinicialesexentoprf) {
        this.anosinicialesexentoprf = anosinicialesexentoprf;
    }

    public Personas getPersonafirmaconstancia() {
        return personafirmaconstancia;
    }

    public void setPersonafirmaconstancia(Personas personafirmaconstancia) {
        this.personafirmaconstancia = personafirmaconstancia;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Empleados getRepresentantecir() {
        return representantecir;
    }

    public void setRepresentantecir(Empleados representantecir) {
        this.representantecir = representantecir;
    }

    public Empleados getSubgerente() {
        return subgerente;
    }

    public void setSubgerente(Empleados subgerente) {
        this.subgerente = subgerente;
    }

    public Empleados getArquitecto() {
        return arquitecto;
    }

    public void setArquitecto(Empleados arquitecto) {
        this.arquitecto = arquitecto;
    }

    public Empleados getGerentegeneral() {
        return gerentegeneral;
    }

    public void setGerentegeneral(Empleados gerentegeneral) {
        this.gerentegeneral = gerentegeneral;
    }

    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
    }

    public Ciudades getCiudaddocumentorepresentante() {
        return ciudaddocumentorepresentante;
    }

    public void setCiudaddocumentorepresentante(Ciudades ciudaddocumentorepresentante) {
        this.ciudaddocumentorepresentante = ciudaddocumentorepresentante;
    }

    public Cargos getCargofirmaconstancia() {
        return cargofirmaconstancia;
    }

    public void setCargofirmaconstancia(Cargos cargofirmaconstancia) {
        this.cargofirmaconstancia = cargofirmaconstancia;
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
        if (!(object instanceof DetallesEmpresas)) {
            return false;
        }
        DetallesEmpresas other = (DetallesEmpresas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Detallesempresas[ secuencia=" + secuencia + " ]";
    }
    
}
