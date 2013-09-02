/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
 * @author Administrator
 */
@Entity
@Cacheable(true)
@Table(name = "EMPLEADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e"),
    @NamedQuery(name = "Empleados.findBySecuencia", query = "SELECT e FROM Empleados e where e.secuencia = :secuencia")})
public class Empleados implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<EvalResultadosConv> evalResultadosConvCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasIndicadores> vigenciasIndicadoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Demandas> demandasCollection;
    @OneToMany(mappedBy = "reemplazado")
    private Collection<Encargaturas> encargaturasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Encargaturas> encargaturasCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<InformacionesAdicionales> informacionesAdicionalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasProyectos> vigenciasProyectosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasEventos> vigenciasEventosCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasUbicaciones> vigenciasubicacionesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGOEMPLEADO")
    private BigInteger codigoempleado;
    @Column(name = "RUTATRANSPORTE")
    private Integer rutatransporte;
    @Column(name = "TELEFONO")
    private Long telefono;
    @Column(name = "EXTENSION")
    private Integer extension;
    @Size(max = 6)
    @Column(name = "PARQUEADERO")
    private String parqueadero;
    @Size(max = 1)
    @Column(name = "SERVICIORESTAURANTE")
    private String serviciorestaurante;
    @Column(name = "NIVELENDEUDAMIENTO")
    private BigDecimal nivelendeudamiento;
    @Column(name = "TOTALULTIMOPAGO")
    private BigInteger totalultimopago;
    @Column(name = "TOTALULTIMODESCUENTO")
    private BigInteger totalultimodescuento;
    @Column(name = "TOTALULTIMOSOBREGIRO")
    private BigInteger totalultimosobregiro;
    @Size(max = 1)
    @Column(name = "EXCLUIRLIQUIDACION")
    private String excluirliquidacion;
    @Column(name = "CODIGOALTERNATIVODEUDOR")
    private Integer codigoalternativodeudor;
    @Column(name = "CODIGOALTERNATIVOACREEDOR")
    private Long codigoalternativoacreedor;
    @Column(name = "FECHACREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Size(max = 20)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @Column(name = "TEMPTOTALINGRESOS")
    private BigInteger temptotalingresos;
    @Size(max = 1)
    @Column(name = "EXTRANJERO")
    private String extranjero;
    @Size(max = 1)
    @Column(name = "PAGASUBSIDIOTRANSPORTELEGAL")
    private String pagasubsidiotransportelegal;
    @Column(name = "TEMPBASERECALCULO")
    private BigInteger tempbaserecalculo;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas persona;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasSueldos> vigenciassueldosCollection;
    @OneToMany(mappedBy = "empleadojefe")
    private Collection<VigenciasCargos> vigenciascargosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasCargos> vigenciascargosCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Soausentismos> soausentismosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<NovedadesSistema> novedadessistemaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Comprobantes> comprobantesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Solucionesnodos> solucionesnodosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Soaccidentes> soaccidentesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Cortesprocesos> cortesprocesosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(mappedBy = "representantecir")
    private Collection<DetallesEmpresas> detallesempresasCollection;
    @OneToMany(mappedBy = "subgerente")
    private Collection<DetallesEmpresas> detallesempresasCollection1;
    @OneToMany(mappedBy = "arquitecto")
    private Collection<DetallesEmpresas> detallesempresasCollection2;
    @OneToMany(mappedBy = "gerentegeneral")
    private Collection<DetallesEmpresas> detallesempresasCollection3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<VigenciasTiposTrabajadores> vigenciastipostrabajadoresCollection;

    public Empleados() {
        persona = new Personas();
    }

    public Empleados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Empleados(BigInteger secuencia, BigInteger codigoempleado) {
        this.secuencia = secuencia;
        this.codigoempleado = codigoempleado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigoempleado() {
        return codigoempleado;
    }

    public void setCodigoempleado(BigInteger codigoempleado) {
        this.codigoempleado = codigoempleado;
    }

    public Integer getRutatransporte() {
        return rutatransporte;
    }

    public void setRutatransporte(Integer rutatransporte) {
        this.rutatransporte = rutatransporte;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public String getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(String parqueadero) {
        this.parqueadero = parqueadero;
    }

    public String getServiciorestaurante() {
        return serviciorestaurante;
    }

    public void setServiciorestaurante(String serviciorestaurante) {
        this.serviciorestaurante = serviciorestaurante;
    }

    public BigDecimal getNivelendeudamiento() {
        return nivelendeudamiento;
    }

    public void setNivelendeudamiento(BigDecimal nivelendeudamiento) {
        this.nivelendeudamiento = nivelendeudamiento;
    }

    public BigInteger getTotalultimopago() {
        return totalultimopago;
    }

    public void setTotalultimopago(BigInteger totalultimopago) {
        this.totalultimopago = totalultimopago;
    }

    public BigInteger getTotalultimodescuento() {
        return totalultimodescuento;
    }

    public void setTotalultimodescuento(BigInteger totalultimodescuento) {
        this.totalultimodescuento = totalultimodescuento;
    }

    public BigInteger getTotalultimosobregiro() {
        return totalultimosobregiro;
    }

    public void setTotalultimosobregiro(BigInteger totalultimosobregiro) {
        this.totalultimosobregiro = totalultimosobregiro;
    }

    public String getExcluirliquidacion() {
        return excluirliquidacion;
    }

    public void setExcluirliquidacion(String excluirliquidacion) {
        this.excluirliquidacion = excluirliquidacion;
    }

    public Integer getCodigoalternativodeudor() {
        return codigoalternativodeudor;
    }

    public void setCodigoalternativodeudor(Integer codigoalternativodeudor) {
        this.codigoalternativodeudor = codigoalternativodeudor;
    }

    public Long getCodigoalternativoacreedor() {
        return codigoalternativoacreedor;
    }

    public void setCodigoalternativoacreedor(Long codigoalternativoacreedor) {
        this.codigoalternativoacreedor = codigoalternativoacreedor;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public BigInteger getTemptotalingresos() {
        return temptotalingresos;
    }

    public void setTemptotalingresos(BigInteger temptotalingresos) {
        this.temptotalingresos = temptotalingresos;
    }

    public String getExtranjero() {
        return extranjero;
    }

    public void setExtranjero(String extranjero) {
        this.extranjero = extranjero;
    }

    public String getPagasubsidiotransportelegal() {
        return pagasubsidiotransportelegal;
    }

    public void setPagasubsidiotransportelegal(String pagasubsidiotransportelegal) {
        this.pagasubsidiotransportelegal = pagasubsidiotransportelegal;
    }

    public BigInteger getTempbaserecalculo() {
        return tempbaserecalculo;
    }

    public void setTempbaserecalculo(BigInteger tempbaserecalculo) {
        this.tempbaserecalculo = tempbaserecalculo;
    }

    public Personas getPersona() {
            return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    @XmlTransient
    public Collection<VigenciasSueldos> getVigenciassueldosCollection() {
        return vigenciassueldosCollection;
    }

    public void setVigenciassueldosCollection(Collection<VigenciasSueldos> vigenciassueldosCollection) {
        this.vigenciassueldosCollection = vigenciassueldosCollection;
    }

    @XmlTransient
    public Collection<VigenciasCargos> getVigenciascargosCollection() {
        return vigenciascargosCollection;
    }

    public void setVigenciascargosCollection(Collection<VigenciasCargos> vigenciascargosCollection) {
        this.vigenciascargosCollection = vigenciascargosCollection;
    }

    @XmlTransient
    public Collection<VigenciasCargos> getVigenciascargosCollection1() {
        return vigenciascargosCollection1;
    }

    public void setVigenciascargosCollection1(Collection<VigenciasCargos> vigenciascargosCollection1) {
        this.vigenciascargosCollection1 = vigenciascargosCollection1;
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
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Empleados[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasTiposTrabajadores> getVigenciastipostrabajadoresCollection() {
        return vigenciastipostrabajadoresCollection;
    }

    public void setVigenciastipostrabajadoresCollection(Collection<VigenciasTiposTrabajadores> vigenciastipostrabajadoresCollection) {
        this.vigenciastipostrabajadoresCollection = vigenciastipostrabajadoresCollection;
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

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection2() {
        return detallesempresasCollection2;
    }

    public void setDetallesempresasCollection2(Collection<DetallesEmpresas> detallesempresasCollection2) {
        this.detallesempresasCollection2 = detallesempresasCollection2;
    }

    @XmlTransient
    public Collection<DetallesEmpresas> getDetallesempresasCollection3() {
        return detallesempresasCollection3;
    }

    public void setDetallesempresasCollection3(Collection<DetallesEmpresas> detallesempresasCollection3) {
        this.detallesempresasCollection3 = detallesempresasCollection3;
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
    public Collection<Comprobantes> getComprobantesCollection() {
        return comprobantesCollection;
    }

    public void setComprobantesCollection(Collection<Comprobantes> comprobantesCollection) {
        this.comprobantesCollection = comprobantesCollection;
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<Solucionesnodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<Soaccidentes> getSoaccidentesCollection() {
        return soaccidentesCollection;
    }

    public void setSoaccidentesCollection(Collection<Soaccidentes> soaccidentesCollection) {
        this.soaccidentesCollection = soaccidentesCollection;
    }

    @XmlTransient
    public Collection<Cortesprocesos> getCortesprocesosCollection() {
        return cortesprocesosCollection;
    }

    public void setCortesprocesosCollection(Collection<Cortesprocesos> cortesprocesosCollection) {
        this.cortesprocesosCollection = cortesprocesosCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }

    public Collection<VigenciasTiposContratos> getVigenciastiposcontratosCollection() {
        return vigenciastiposcontratosCollection;
    }

    public void setVigenciastiposcontratosCollection(Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection) {
        this.vigenciastiposcontratosCollection = vigenciastiposcontratosCollection;
    }

    public Collection<VigenciasUbicaciones> getVigenciasubicacionesCollection() {
        return vigenciasubicacionesCollection;
    }

    public void setVigenciasubicacionesCollection(Collection<VigenciasUbicaciones> vigenciasubicacionesCollection) {
        this.vigenciasubicacionesCollection = vigenciasubicacionesCollection;
    }

    @XmlTransient
    public Collection<VigenciasIndicadores> getVigenciasIndicadoresCollection() {
        return vigenciasIndicadoresCollection;
    }

    public void setVigenciasIndicadoresCollection(Collection<VigenciasIndicadores> vigenciasIndicadoresCollection) {
        this.vigenciasIndicadoresCollection = vigenciasIndicadoresCollection;
    }

    @XmlTransient
    public Collection<Demandas> getDemandasCollection() {
        return demandasCollection;
    }

    public void setDemandasCollection(Collection<Demandas> demandasCollection) {
        this.demandasCollection = demandasCollection;
    }

    @XmlTransient
    public Collection<Encargaturas> getEncargaturasCollection() {
        return encargaturasCollection;
    }

    public void setEncargaturasCollection(Collection<Encargaturas> encargaturasCollection) {
        this.encargaturasCollection = encargaturasCollection;
    }

    @XmlTransient
    public Collection<Encargaturas> getEncargaturasCollection1() {
        return encargaturasCollection1;
    }

    public void setEncargaturasCollection1(Collection<Encargaturas> encargaturasCollection1) {
        this.encargaturasCollection1 = encargaturasCollection1;
    }

    @XmlTransient
    public Collection<InformacionesAdicionales> getInformacionesAdicionalesCollection() {
        return informacionesAdicionalesCollection;
    }

    public void setInformacionesAdicionalesCollection(Collection<InformacionesAdicionales> informacionesAdicionalesCollection) {
        this.informacionesAdicionalesCollection = informacionesAdicionalesCollection;
    }

    @XmlTransient
    public Collection<VigenciasProyectos> getVigenciasProyectosCollection() {
        return vigenciasProyectosCollection;
    }

    public void setVigenciasProyectosCollection(Collection<VigenciasProyectos> vigenciasProyectosCollection) {
        this.vigenciasProyectosCollection = vigenciasProyectosCollection;
    }

    @XmlTransient
    public Collection<VigenciasEventos> getVigenciasEventosCollection() {
        return vigenciasEventosCollection;
    }

    public void setVigenciasEventosCollection(Collection<VigenciasEventos> vigenciasEventosCollection) {
        this.vigenciasEventosCollection = vigenciasEventosCollection;
    }

    public Collection<EvalResultadosConv> getEvalResultadosConvCollection() {
        return evalResultadosConvCollection;
    }

    public void setEvalResultadosConvCollection(Collection<EvalResultadosConv> evalResultadosConvCollection) {
        this.evalResultadosConvCollection = evalResultadosConvCollection;
    }
}
