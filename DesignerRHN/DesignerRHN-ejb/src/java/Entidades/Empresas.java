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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "EMPRESAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresas.findAll", query = "SELECT e FROM Empresas e")})
public class Empresas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<SectoresEconomicos> sectoresEconomicosCollection;
    @OneToMany(mappedBy = "empresas")
    private Collection<RelacionesIncapacidades> relacionesIncapacidadesCollection;
    @Size(max = 1)
    @Column(name = "BARRACONSULTADATOS")
    private String barraconsultadatos;
    @OneToMany(mappedBy = "empresa")
    private Collection<GruposProvisiones> gruposProvisionesCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Evalconvocatorias> evalconvocatoriasCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Evalplanillas> evalplanillasCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Pdgestrategias> pdgestrategiasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<TiposAsociaciones> tiposAsociacionesCollection;
    
    @OneToMany(mappedBy = "empresa")
    private Collection<Proyectos> proyectosCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Direcciones> direccionesCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Pantallas> pantallasCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empresa")
    private DetallesEmpresas detallesempresas;
    @OneToMany(mappedBy = "empresa")
    private Collection<UbicacionesGeograficas> ubicacionesgeograficasCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<SucursalesPila> sucursalesPilaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Cuentas> cuentasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NIT")
    private long nit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Lob
    @Column(name = "REGLAMENTO")
    private String reglamento;
    @Lob
    @Column(name = "MANUALADMINISTRATIVO")
    private String manualadministrativo;
    @Size(max = 10)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @Size(max = 50)
    @Column(name = "LOGO")
    private String logo;
    @Column(name = "CONTROLEMPLEADOS")
    private BigInteger controlempleados;
    @Column(name = "CONTROLINICIOPERIODOACTIVO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date controlinicioperiodoactivo;
    @Column(name = "CONTROLFINPERIODOACTIVO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date controlfinperiodoactivo;
    @Size(max = 50)
    @Column(name = "PRODUCTO")
    private String producto;
    @Column(name = "FECHAINSTALACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainstalacion;
    @Column(name = "FECHARENOVARSOPORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharenovarsoporte;
    @Column(name = "CANTIDADUSUARIO")
    private Short cantidadusuario;
    @Column(name = "FECHASALIDAPRODUCCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasalidaproduccion;
    @Size(max = 1)
    @Column(name = "PROMEDIOBASICOVACACIONES")
    private String promediobasicovacaciones;
    @Size(max = 1)
    @Column(name = "FECHAHASTAINIVACA")
    private String fechahastainivaca;
    @Column(name = "MEDIAADELANTODTOVACACIONES")
    private BigInteger mediaadelantodtovacaciones;
    @Column(name = "DOCUMENTOCONTABLECDI")
    private BigInteger documentocontablecdi;
    @Size(max = 15)
    @Column(name = "PRESENTACIONPILAINGRET")
    private String presentacionpilaingret;
    @Size(max = 1)
    @Column(name = "ENTREGAPARCIALINTERESCESANTIA")
    private String entregaparcialinterescesantia;
    @Size(max = 1)
    @Column(name = "VARIABLEPROMEDIABASICOCESANTIA")
    private String variablepromediabasicocesantia;
    @Size(max = 1)
    @Column(name = "SALUDACTIVOPENSIONADOIGUAL")
    private String saludactivopensionadoigual;
    @Size(max = 1)
    @Column(name = "USAGRUPOVARIABLEINDEMNIZA")
    private String usagrupovariableindemniza;
    @Size(max = 1)
    @Column(name = "REDONDEAPARAFISCALES")
    private String redondeaparafiscales;
    @Size(max = 1)
    @Column(name = "PROPORCIONTOPESUPERIORIBC")
    private String proporciontopesuperioribc;
    @Size(max = 1)
    @Column(name = "RETENCIONDISMINUYESALUDCONTOPE")
    private String retenciondisminuyesaludcontope;
    @Size(max = 1)
    @Column(name = "PROMEDIO3MVARIABLEVACACIONES")
    private String promedio3mvariablevacaciones;
    @Size(max = 1)
    @Column(name = "VALIDAPARAFISCALESAPORTEMINIMO")
    private String validaparafiscalesaporteminimo;
    @Size(max = 1)
    @Column(name = "PRIMAPROMEDIABASICOSEMESTRE")
    private String primapromediabasicosemestre;
    @Size(max = 1)
    @Column(name = "SUBTRANSPORTEPROMCESAPRIMA")
    private String subtransportepromcesaprima;
    @Size(max = 1)
    @Column(name = "PAGAVACACIONESDIFERENTENOMINA")
    private String pagavacacionesdiferentenomina;
    @Size(max = 1)
    @Column(name = "RETROACTIVOEN1PAGO")
    private String retroactivoen1pago;
    @Size(max = 1)
    @Column(name = "LIMITAPARAFISCALESMAXIMO25SML")
    private String limitaparafiscalesmaximo25sml;
    @Size(max = 100)
    @Column(name = "ODBCSQLSERVER")
    private String odbcsqlserver;
    @Size(max = 1)
    @Column(name = "VERIFICANOVEDADPAGADA")
    private String verificanovedadpagada;
    @Size(max = 1)
    @Column(name = "INTERCONADICIONACENTROCOSTO")
    private String interconadicionacentrocosto;
    @Size(max = 1)
    @Column(name = "PAGOINTCES1QUINCENA")
    private String pagointces1quincena;
    @Size(max = 1)
    @Column(name = "VACARESTADIASNOLABOR")
    private String vacarestadiasnolabor;
    @Size(max = 1)
    @Column(name = "USOIBCSUPERIOR25SMLDIASCOTI")
    private String usoibcsuperior25smldiascoti;
    @Size(max = 1)
    @Column(name = "PARAFISCALESSECTORGOBIERNO")
    private String parafiscalessectorgobierno;
    @Size(max = 1)
    @Column(name = "EXENTORETEFTEINCLUYEPRIMA")
    private String exentoretefteincluyeprima;
    @Size(max = 1)
    @Column(name = "BASERETEFTEMESINDIVIDUAL")
    private String basereteftemesindividual;
    @Size(max = 60)
    @Column(name = "CADENACONEXIONKIOSKO")
    private String cadenaconexionkiosko;
    @Size(max = 20)
    @Column(name = "ARCHIVOFIRMA")
    private String archivofirma;
    @Size(max = 30)
    @Column(name = "SUBLOGO")
    private String sublogo;
    @Size(max = 1)
    @Column(name = "FINVACACIONNEXTDIAHABIL")
    private String finvacacionnextdiahabil;
    @Size(max = 1)
    @Column(name = "PRESENTAPILADIASBENEFICIARIO")
    private String presentapiladiasbeneficiario;
    @Size(max = 1)
    @Column(name = "PATRONPENSIONSECTORSALUD")
    private String patronpensionsectorsalud;
    @Size(max = 1)
    @Column(name = "PROMEDIO3MVARIABLECESANTIAS")
    private String promedio3mvariablecesantias;
    @Size(max = 1)
    @Column(name = "RETENCIONYSEGSOCXPERSONA")
    private String retencionysegsocxpersona;
    @Size(max = 1)
    @Column(name = "CODIGOEMPLEADOAUTOMATICO")
    private String codigoempleadoautomatico;
    @Size(max = 1)
    @Column(name = "FORZAIBCSALUDPENSIONMINSML")
    private String forzaibcsaludpensionminsml;
    @Size(max = 30)
    @Column(name = "LOGOSGS")
    private String logosgs;
    @Size(max = 1)
    @Column(name = "FORZAIBCPARAFISCALMINSML")
    private String forzaibcparafiscalminsml;
    @Size(max = 1)
    @Column(name = "FORZAIBCRIESGOMINSML")
    private String forzaibcriesgominsml;
    @Size(max = 1)
    @Column(name = "DEFINITIVARESTASALDOPROVISION")
    private String definitivarestasaldoprovision;
    @Size(max = 20)
    @Column(name = "ODBCFORMATOFECHA")
    private String odbcformatofecha;
    @Size(max = 1)
    @Column(name = "RETMET1EXTRAPOLAINGRESO")
    private String retmet1extrapolaingreso;
    @Size(max = 30)
    @Column(name = "FIRMACIR")
    private String firmacir;
    @Size(max = 1)
    @Column(name = "INTERADICIONACCPREFIJO")
    private String interadicionaccprefijo;
    @Size(max = 1)
    @Column(name = "RECALCULORETEFTETOPEHISTORICO")
    private String recalculoreteftetopehistorico;
    @Size(max = 1)
    @Column(name = "RECALCULORETEFTETOPEUSADO")
    private String recalculoreteftetopeusado;
    @Size(max = 1)
    @Column(name = "FECHAPAGOUNIDADPRODUCIDA")
    private String fechapagounidadproducida;
    @Size(max = 1)
    @Column(name = "INTEGRAGESTIONDOCUMENTAL")
    private String integragestiondocumental;
    @Size(max = 1)
    @Column(name = "PRIMAUSAPROMEDIOACUMSUELDO")
    private String primausapromedioacumsueldo;
    @Size(max = 1)
    @Column(name = "CESANUSAPROMEDIOACUMSUELDO")
    private String cesanusapromedioacumsueldo;
    @Size(max = 1)
    @Column(name = "VACACUSAPROMEDIOACUMSUELDO")
    private String vacacusapromedioacumsueldo;
    @Size(max = 1)
    @Column(name = "CESASIEMPREPROMEDIAANO")
    private String cesasiemprepromediaano;
    @Size(max = 5)
    @Column(name = "SAPBOPREFIJOEMPLEADO")
    private String sapboprefijoempleado;
    @Size(max = 5)
    @Column(name = "SAPBOPREFIJOTERCERO")
    private String sapboprefijotercero;
    @Size(max = 1)
    @Column(name = "ENVIOINTERFASECONTABILIDAD")
    private String enviointerfasecontabilidad;
    @Size(max = 1)
    @Column(name = "SAPBODETALLADA")
    private String sapbodetallada;
    @OneToMany(mappedBy = "empresa")
    private Collection<Terceros> tercerosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Organigramas> organigramasCollection;
    @JoinColumn(name = "MINIMAPERIODICIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Periodicidades minimaperiodicidad;
    @JoinColumn(name = "CENTROCOSTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocosto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Empleados> empleadosCollection;
    @OneToMany(mappedBy = "empresa")
    private Collection<Cargos> cargosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<Conceptos> conceptosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private Collection<CentrosCostos> centroscostosCollection;

    public Empresas() {
    }

    public Empresas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Empresas(BigInteger secuencia, short codigo, long nit, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nit = nit;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public long getNit() {
        return nit;
    }

    public void setNit(long nit) {
        this.nit = nit;
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

    public String getReglamento() {
        return reglamento;
    }

    public void setReglamento(String reglamento) {
        this.reglamento = reglamento;
    }

    public String getManualadministrativo() {
        return manualadministrativo;
    }

    public void setManualadministrativo(String manualadministrativo) {
        this.manualadministrativo = manualadministrativo;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigInteger getControlempleados() {
        return controlempleados;
    }

    public void setControlempleados(BigInteger controlempleados) {
        this.controlempleados = controlempleados;
    }

    public Date getControlinicioperiodoactivo() {
        return controlinicioperiodoactivo;
    }

    public void setControlinicioperiodoactivo(Date controlinicioperiodoactivo) {
        this.controlinicioperiodoactivo = controlinicioperiodoactivo;
    }

    public Date getControlfinperiodoactivo() {
        return controlfinperiodoactivo;
    }

    public void setControlfinperiodoactivo(Date controlfinperiodoactivo) {
        this.controlfinperiodoactivo = controlfinperiodoactivo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Date getFechainstalacion() {
        return fechainstalacion;
    }

    public void setFechainstalacion(Date fechainstalacion) {
        this.fechainstalacion = fechainstalacion;
    }

    public Date getFecharenovarsoporte() {
        return fecharenovarsoporte;
    }

    public void setFecharenovarsoporte(Date fecharenovarsoporte) {
        this.fecharenovarsoporte = fecharenovarsoporte;
    }

    public Short getCantidadusuario() {
        return cantidadusuario;
    }

    public void setCantidadusuario(Short cantidadusuario) {
        this.cantidadusuario = cantidadusuario;
    }

    public Date getFechasalidaproduccion() {
        return fechasalidaproduccion;
    }

    public void setFechasalidaproduccion(Date fechasalidaproduccion) {
        this.fechasalidaproduccion = fechasalidaproduccion;
    }

    public String getPromediobasicovacaciones() {
        return promediobasicovacaciones;
    }

    public void setPromediobasicovacaciones(String promediobasicovacaciones) {
        this.promediobasicovacaciones = promediobasicovacaciones;
    }

    public String getFechahastainivaca() {
        return fechahastainivaca;
    }

    public void setFechahastainivaca(String fechahastainivaca) {
        this.fechahastainivaca = fechahastainivaca;
    }

    public BigInteger getMediaadelantodtovacaciones() {
        return mediaadelantodtovacaciones;
    }

    public void setMediaadelantodtovacaciones(BigInteger mediaadelantodtovacaciones) {
        this.mediaadelantodtovacaciones = mediaadelantodtovacaciones;
    }

    public BigInteger getDocumentocontablecdi() {
        return documentocontablecdi;
    }

    public void setDocumentocontablecdi(BigInteger documentocontablecdi) {
        this.documentocontablecdi = documentocontablecdi;
    }

    public String getPresentacionpilaingret() {
        return presentacionpilaingret;
    }

    public void setPresentacionpilaingret(String presentacionpilaingret) {
        this.presentacionpilaingret = presentacionpilaingret;
    }

    public String getEntregaparcialinterescesantia() {
        return entregaparcialinterescesantia;
    }

    public void setEntregaparcialinterescesantia(String entregaparcialinterescesantia) {
        this.entregaparcialinterescesantia = entregaparcialinterescesantia;
    }

    public String getVariablepromediabasicocesantia() {
        return variablepromediabasicocesantia;
    }

    public void setVariablepromediabasicocesantia(String variablepromediabasicocesantia) {
        this.variablepromediabasicocesantia = variablepromediabasicocesantia;
    }

    public String getSaludactivopensionadoigual() {
        return saludactivopensionadoigual;
    }

    public void setSaludactivopensionadoigual(String saludactivopensionadoigual) {
        this.saludactivopensionadoigual = saludactivopensionadoigual;
    }

    public String getUsagrupovariableindemniza() {
        return usagrupovariableindemniza;
    }

    public void setUsagrupovariableindemniza(String usagrupovariableindemniza) {
        this.usagrupovariableindemniza = usagrupovariableindemniza;
    }

    public String getRedondeaparafiscales() {
        return redondeaparafiscales;
    }

    public void setRedondeaparafiscales(String redondeaparafiscales) {
        this.redondeaparafiscales = redondeaparafiscales;
    }

    public String getProporciontopesuperioribc() {
        return proporciontopesuperioribc;
    }

    public void setProporciontopesuperioribc(String proporciontopesuperioribc) {
        this.proporciontopesuperioribc = proporciontopesuperioribc;
    }

    public String getRetenciondisminuyesaludcontope() {
        return retenciondisminuyesaludcontope;
    }

    public void setRetenciondisminuyesaludcontope(String retenciondisminuyesaludcontope) {
        this.retenciondisminuyesaludcontope = retenciondisminuyesaludcontope;
    }

    public String getPromedio3mvariablevacaciones() {
        return promedio3mvariablevacaciones;
    }

    public void setPromedio3mvariablevacaciones(String promedio3mvariablevacaciones) {
        this.promedio3mvariablevacaciones = promedio3mvariablevacaciones;
    }

    public String getValidaparafiscalesaporteminimo() {
        return validaparafiscalesaporteminimo;
    }

    public void setValidaparafiscalesaporteminimo(String validaparafiscalesaporteminimo) {
        this.validaparafiscalesaporteminimo = validaparafiscalesaporteminimo;
    }

    public String getPrimapromediabasicosemestre() {
        return primapromediabasicosemestre;
    }

    public void setPrimapromediabasicosemestre(String primapromediabasicosemestre) {
        this.primapromediabasicosemestre = primapromediabasicosemestre;
    }

    public String getSubtransportepromcesaprima() {
        return subtransportepromcesaprima;
    }

    public void setSubtransportepromcesaprima(String subtransportepromcesaprima) {
        this.subtransportepromcesaprima = subtransportepromcesaprima;
    }

    public String getPagavacacionesdiferentenomina() {
        return pagavacacionesdiferentenomina;
    }

    public void setPagavacacionesdiferentenomina(String pagavacacionesdiferentenomina) {
        this.pagavacacionesdiferentenomina = pagavacacionesdiferentenomina;
    }

    public String getRetroactivoen1pago() {
        return retroactivoen1pago;
    }

    public void setRetroactivoen1pago(String retroactivoen1pago) {
        this.retroactivoen1pago = retroactivoen1pago;
    }

    public String getLimitaparafiscalesmaximo25sml() {
        return limitaparafiscalesmaximo25sml;
    }

    public void setLimitaparafiscalesmaximo25sml(String limitaparafiscalesmaximo25sml) {
        this.limitaparafiscalesmaximo25sml = limitaparafiscalesmaximo25sml;
    }

    public String getOdbcsqlserver() {
        return odbcsqlserver;
    }

    public void setOdbcsqlserver(String odbcsqlserver) {
        this.odbcsqlserver = odbcsqlserver;
    }

    public String getVerificanovedadpagada() {
        return verificanovedadpagada;
    }

    public void setVerificanovedadpagada(String verificanovedadpagada) {
        this.verificanovedadpagada = verificanovedadpagada;
    }

    public String getInterconadicionacentrocosto() {
        return interconadicionacentrocosto;
    }

    public void setInterconadicionacentrocosto(String interconadicionacentrocosto) {
        this.interconadicionacentrocosto = interconadicionacentrocosto;
    }

    public String getPagointces1quincena() {
        return pagointces1quincena;
    }

    public void setPagointces1quincena(String pagointces1quincena) {
        this.pagointces1quincena = pagointces1quincena;
    }

    public String getVacarestadiasnolabor() {
        return vacarestadiasnolabor;
    }

    public void setVacarestadiasnolabor(String vacarestadiasnolabor) {
        this.vacarestadiasnolabor = vacarestadiasnolabor;
    }

    public String getUsoibcsuperior25smldiascoti() {
        return usoibcsuperior25smldiascoti;
    }

    public void setUsoibcsuperior25smldiascoti(String usoibcsuperior25smldiascoti) {
        this.usoibcsuperior25smldiascoti = usoibcsuperior25smldiascoti;
    }

    public String getParafiscalessectorgobierno() {
        return parafiscalessectorgobierno;
    }

    public void setParafiscalessectorgobierno(String parafiscalessectorgobierno) {
        this.parafiscalessectorgobierno = parafiscalessectorgobierno;
    }

    public String getExentoretefteincluyeprima() {
        return exentoretefteincluyeprima;
    }

    public void setExentoretefteincluyeprima(String exentoretefteincluyeprima) {
        this.exentoretefteincluyeprima = exentoretefteincluyeprima;
    }

    public String getBasereteftemesindividual() {
        return basereteftemesindividual;
    }

    public void setBasereteftemesindividual(String basereteftemesindividual) {
        this.basereteftemesindividual = basereteftemesindividual;
    }

    public String getCadenaconexionkiosko() {
        return cadenaconexionkiosko;
    }

    public void setCadenaconexionkiosko(String cadenaconexionkiosko) {
        this.cadenaconexionkiosko = cadenaconexionkiosko;
    }

    public String getArchivofirma() {
        return archivofirma;
    }

    public void setArchivofirma(String archivofirma) {
        this.archivofirma = archivofirma;
    }

    public String getSublogo() {
        return sublogo;
    }

    public void setSublogo(String sublogo) {
        this.sublogo = sublogo;
    }

    public String getFinvacacionnextdiahabil() {
        return finvacacionnextdiahabil;
    }

    public void setFinvacacionnextdiahabil(String finvacacionnextdiahabil) {
        this.finvacacionnextdiahabil = finvacacionnextdiahabil;
    }

    public String getPresentapiladiasbeneficiario() {
        return presentapiladiasbeneficiario;
    }

    public void setPresentapiladiasbeneficiario(String presentapiladiasbeneficiario) {
        this.presentapiladiasbeneficiario = presentapiladiasbeneficiario;
    }

    public String getPatronpensionsectorsalud() {
        return patronpensionsectorsalud;
    }

    public void setPatronpensionsectorsalud(String patronpensionsectorsalud) {
        this.patronpensionsectorsalud = patronpensionsectorsalud;
    }

    public String getPromedio3mvariablecesantias() {
        return promedio3mvariablecesantias;
    }

    public void setPromedio3mvariablecesantias(String promedio3mvariablecesantias) {
        this.promedio3mvariablecesantias = promedio3mvariablecesantias;
    }

    public String getRetencionysegsocxpersona() {
        return retencionysegsocxpersona;
    }

    public void setRetencionysegsocxpersona(String retencionysegsocxpersona) {
        this.retencionysegsocxpersona = retencionysegsocxpersona;
    }

    public String getCodigoempleadoautomatico() {
        return codigoempleadoautomatico;
    }

    public void setCodigoempleadoautomatico(String codigoempleadoautomatico) {
        this.codigoempleadoautomatico = codigoempleadoautomatico;
    }

    public String getForzaibcsaludpensionminsml() {
        return forzaibcsaludpensionminsml;
    }

    public void setForzaibcsaludpensionminsml(String forzaibcsaludpensionminsml) {
        this.forzaibcsaludpensionminsml = forzaibcsaludpensionminsml;
    }

    public String getLogosgs() {
        return logosgs;
    }

    public void setLogosgs(String logosgs) {
        this.logosgs = logosgs;
    }

    public String getForzaibcparafiscalminsml() {
        return forzaibcparafiscalminsml;
    }

    public void setForzaibcparafiscalminsml(String forzaibcparafiscalminsml) {
        this.forzaibcparafiscalminsml = forzaibcparafiscalminsml;
    }

    public String getForzaibcriesgominsml() {
        return forzaibcriesgominsml;
    }

    public void setForzaibcriesgominsml(String forzaibcriesgominsml) {
        this.forzaibcriesgominsml = forzaibcriesgominsml;
    }

    public String getDefinitivarestasaldoprovision() {
        return definitivarestasaldoprovision;
    }

    public void setDefinitivarestasaldoprovision(String definitivarestasaldoprovision) {
        this.definitivarestasaldoprovision = definitivarestasaldoprovision;
    }

    public String getOdbcformatofecha() {
        return odbcformatofecha;
    }

    public void setOdbcformatofecha(String odbcformatofecha) {
        this.odbcformatofecha = odbcformatofecha;
    }

    public String getRetmet1extrapolaingreso() {
        return retmet1extrapolaingreso;
    }

    public void setRetmet1extrapolaingreso(String retmet1extrapolaingreso) {
        this.retmet1extrapolaingreso = retmet1extrapolaingreso;
    }

    public String getFirmacir() {
        return firmacir;
    }

    public void setFirmacir(String firmacir) {
        this.firmacir = firmacir;
    }

    public String getInteradicionaccprefijo() {
        return interadicionaccprefijo;
    }

    public void setInteradicionaccprefijo(String interadicionaccprefijo) {
        this.interadicionaccprefijo = interadicionaccprefijo;
    }

    public String getRecalculoreteftetopehistorico() {
        return recalculoreteftetopehistorico;
    }

    public void setRecalculoreteftetopehistorico(String recalculoreteftetopehistorico) {
        this.recalculoreteftetopehistorico = recalculoreteftetopehistorico;
    }

    public String getRecalculoreteftetopeusado() {
        return recalculoreteftetopeusado;
    }

    public void setRecalculoreteftetopeusado(String recalculoreteftetopeusado) {
        this.recalculoreteftetopeusado = recalculoreteftetopeusado;
    }

    public String getFechapagounidadproducida() {
        return fechapagounidadproducida;
    }

    public void setFechapagounidadproducida(String fechapagounidadproducida) {
        this.fechapagounidadproducida = fechapagounidadproducida;
    }

    public String getIntegragestiondocumental() {
        return integragestiondocumental;
    }

    public void setIntegragestiondocumental(String integragestiondocumental) {
        this.integragestiondocumental = integragestiondocumental;
    }

    public String getPrimausapromedioacumsueldo() {
        return primausapromedioacumsueldo;
    }

    public void setPrimausapromedioacumsueldo(String primausapromedioacumsueldo) {
        this.primausapromedioacumsueldo = primausapromedioacumsueldo;
    }

    public String getCesanusapromedioacumsueldo() {
        return cesanusapromedioacumsueldo;
    }

    public void setCesanusapromedioacumsueldo(String cesanusapromedioacumsueldo) {
        this.cesanusapromedioacumsueldo = cesanusapromedioacumsueldo;
    }

    public String getVacacusapromedioacumsueldo() {
        return vacacusapromedioacumsueldo;
    }

    public void setVacacusapromedioacumsueldo(String vacacusapromedioacumsueldo) {
        this.vacacusapromedioacumsueldo = vacacusapromedioacumsueldo;
    }

    public String getCesasiemprepromediaano() {
        return cesasiemprepromediaano;
    }

    public void setCesasiemprepromediaano(String cesasiemprepromediaano) {
        this.cesasiemprepromediaano = cesasiemprepromediaano;
    }

    public String getSapboprefijoempleado() {
        return sapboprefijoempleado;
    }

    public void setSapboprefijoempleado(String sapboprefijoempleado) {
        this.sapboprefijoempleado = sapboprefijoempleado;
    }

    public String getSapboprefijotercero() {
        return sapboprefijotercero;
    }

    public void setSapboprefijotercero(String sapboprefijotercero) {
        this.sapboprefijotercero = sapboprefijotercero;
    }

    public String getEnviointerfasecontabilidad() {
        return enviointerfasecontabilidad;
    }

    public void setEnviointerfasecontabilidad(String enviointerfasecontabilidad) {
        this.enviointerfasecontabilidad = enviointerfasecontabilidad;
    }

    public String getSapbodetallada() {
        return sapbodetallada;
    }

    public void setSapbodetallada(String sapbodetallada) {
        this.sapbodetallada = sapbodetallada;
    }

    @XmlTransient
    public Collection<Terceros> getTercerosCollection() {
        return tercerosCollection;
    }

    public void setTercerosCollection(Collection<Terceros> tercerosCollection) {
        this.tercerosCollection = tercerosCollection;
    }

    @XmlTransient
    public Collection<Organigramas> getOrganigramasCollection() {
        return organigramasCollection;
    }

    public void setOrganigramasCollection(Collection<Organigramas> organigramasCollection) {
        this.organigramasCollection = organigramasCollection;
    }

    public Periodicidades getMinimaperiodicidad() {
        return minimaperiodicidad;
    }

    public void setMinimaperiodicidad(Periodicidades minimaperiodicidad) {
        this.minimaperiodicidad = minimaperiodicidad;
    }

    public CentrosCostos getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(CentrosCostos centrocosto) {
        this.centrocosto = centrocosto;
    }

    @XmlTransient
    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    @XmlTransient
    public Collection<Cargos> getCargosCollection() {
        return cargosCollection;
    }

    public void setCargosCollection(Collection<Cargos> cargosCollection) {
        this.cargosCollection = cargosCollection;
    }

    @XmlTransient
    public Collection<Conceptos> getConceptosCollection() {
        return conceptosCollection;
    }

    public void setConceptosCollection(Collection<Conceptos> conceptosCollection) {
        this.conceptosCollection = conceptosCollection;
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
        if (!(object instanceof Empresas)) {
            return false;
        }
        Empresas other = (Empresas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Empresas[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    @XmlTransient
    public Collection<UbicacionesGeograficas> getUbicacionesgeograficasCollection() {
        return ubicacionesgeograficasCollection;
    }

    public void setUbicacionesgeograficasCollection(Collection<UbicacionesGeograficas> ubicacionesgeograficasCollection) {
        this.ubicacionesgeograficasCollection = ubicacionesgeograficasCollection;
    }

    @XmlTransient
    public Collection<SucursalesPila> getSucursalesPilaCollection() {
        return sucursalesPilaCollection;
    }

    public void setSucursalesPilaCollection(Collection<SucursalesPila> sucursalesPilaCollection) {
        this.sucursalesPilaCollection = sucursalesPilaCollection;
    }

    public DetallesEmpresas getDetallesempresas() {
        return detallesempresas;
    }

    public void setDetallesempresas(DetallesEmpresas detallesempresas) {
        this.detallesempresas = detallesempresas;
    }

    @XmlTransient
    public Collection<Pantallas> getPantallasCollection() {
        return pantallasCollection;
    }

    public void setPantallasCollection(Collection<Pantallas> pantallasCollection) {
        this.pantallasCollection = pantallasCollection;
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
    }

    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }


   // @XmlTransient

    public Collection<TiposAsociaciones> getTiposAsociacionesCollection() {
        return tiposAsociacionesCollection;
    }

    public void setTiposAsociacionesCollection(Collection<TiposAsociaciones> tiposAsociacionesCollection) {
        this.tiposAsociacionesCollection = tiposAsociacionesCollection;
    }

    @XmlTransient
    public Collection<Evalconvocatorias> getEvalconvocatoriasCollection() {
        return evalconvocatoriasCollection;
    }

    public void setEvalconvocatoriasCollection(Collection<Evalconvocatorias> evalconvocatoriasCollection) {
        this.evalconvocatoriasCollection = evalconvocatoriasCollection;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }

    @XmlTransient
    public Collection<Evalplanillas> getEvalplanillasCollection() {
        return evalplanillasCollection;
    }

    public void setEvalplanillasCollection(Collection<Evalplanillas> evalplanillasCollection) {
        this.evalplanillasCollection = evalplanillasCollection;
    }

    @XmlTransient
    public Collection<Pdgestrategias> getPdgestrategiasCollection() {
        return pdgestrategiasCollection;
    }

    public void setPdgestrategiasCollection(Collection<Pdgestrategias> pdgestrategiasCollection) {
        this.pdgestrategiasCollection = pdgestrategiasCollection;
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection() {
        return gruposProvisionesCollection;
    }

    public void setGruposProvisionesCollection(Collection<GruposProvisiones> gruposProvisionesCollection) {
        this.gruposProvisionesCollection = gruposProvisionesCollection;
    }

    public String getBarraconsultadatos() {
        return barraconsultadatos;
    }

    public void setBarraconsultadatos(String barraconsultadatos) {
        this.barraconsultadatos = barraconsultadatos;
    }

    @XmlTransient
    public Collection<RelacionesIncapacidades> getRelacionesIncapacidadesCollection() {
        return relacionesIncapacidadesCollection;
    }

    public void setRelacionesIncapacidadesCollection(Collection<RelacionesIncapacidades> relacionesIncapacidadesCollection) {
        this.relacionesIncapacidadesCollection = relacionesIncapacidadesCollection;
    }

    @XmlTransient
    public Collection<SectoresEconomicos> getSectoresEconomicosCollection() {
        return sectoresEconomicosCollection;
    }

    public void setSectoresEconomicosCollection(Collection<SectoresEconomicos> sectoresEconomicosCollection) {
        this.sectoresEconomicosCollection = sectoresEconomicosCollection;
    }
    

    

    

    
}
