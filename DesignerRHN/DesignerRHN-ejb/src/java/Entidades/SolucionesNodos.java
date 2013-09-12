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
@Table(name = "SOLUCIONESNODOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolucionesNodos.findAll", query = "SELECT s FROM SolucionesNodos s")})
public class SolucionesNodos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solucionnodo")
    private Collection<SolucionesFormulas> solucionesFormulasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 10)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "AJUSTE")
    private BigInteger ajuste;
    @Size(max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "UNIDADES")
    private BigDecimal unidades;
    @Column(name = "ULTIMAMODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimamodificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahasta;
    @Size(max = 500)
    @Column(name = "PARCIALES")
    private String parciales;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @Column(name = "FECHAPAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;
    @Column(name = "VALORINCREMENTO")
    private BigInteger valorincremento;
    @Column(name = "PARAMETROTESORERIA")
    private BigInteger parametrotesoreria;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposTrabajadores tipotrabajador;
    @JoinColumn(name = "TIPOCONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposContratos tipocontrato;
    @JoinColumn(name = "NIT", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros nit;
    @JoinColumn(name = "REFORMALABORAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private ReformasLaborales reformalaboral;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Procesos proceso;
    @JoinColumn(name = "PARAMETROPRESUPUESTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Parametrospresupuestos parametropresupuesto;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cargos cargo;
    @JoinColumn(name = "CENTROCOSTOD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private CentrosCostos centrocostod;
    @JoinColumn(name = "CENTROCOSTOC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private CentrosCostos centrocostoc;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;
    @JoinColumn(name = "CORTEPROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CortesProcesos corteproceso;
    @JoinColumn(name = "CUENTAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cuentas cuentad;
    @JoinColumn(name = "CUENTAC", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cuentas cuentac;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Estructuras estructura;
    @JoinColumn(name = "LOCALIZACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Estructuras localizacion;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Formulas formula;
    @JoinColumn(name = "NODO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Nodos nodo;
    @OneToMany(mappedBy = "ajuste")
    private Collection<Novedades> novedadesCollection;

    public SolucionesNodos() {
    }

    public SolucionesNodos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public SolucionesNodos(BigDecimal secuencia, BigDecimal valor, Date fechadesde, Date fechahasta) {
        this.secuencia = secuencia;
        this.valor = valor;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getAjuste() {
        return ajuste;
    }

    public void setAjuste(BigInteger ajuste) {
        this.ajuste = ajuste;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getUnidades() {
        return unidades;
    }

    public void setUnidades(BigDecimal unidades) {
        this.unidades = unidades;
    }

    public Date getUltimamodificacion() {
        return ultimamodificacion;
    }

    public void setUltimamodificacion(Date ultimamodificacion) {
        this.ultimamodificacion = ultimamodificacion;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }

    public String getParciales() {
        return parciales;
    }

    public void setParciales(String parciales) {
        this.parciales = parciales;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public BigInteger getValorincremento() {
        return valorincremento;
    }

    public void setValorincremento(BigInteger valorincremento) {
        this.valorincremento = valorincremento;
    }

    public BigInteger getParametrotesoreria() {
        return parametrotesoreria;
    }

    public void setParametrotesoreria(BigInteger parametrotesoreria) {
        this.parametrotesoreria = parametrotesoreria;
    }

    public TiposTrabajadores getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(TiposTrabajadores tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public TiposContratos getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(TiposContratos tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public Terceros getNit() {
        return nit;
    }

    public void setNit(Terceros nit) {
        this.nit = nit;
    }

    public ReformasLaborales getReformalaboral() {
        return reformalaboral;
    }

    public void setReformalaboral(ReformasLaborales reformalaboral) {
        this.reformalaboral = reformalaboral;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public Parametrospresupuestos getParametropresupuesto() {
        return parametropresupuesto;
    }

    public void setParametropresupuesto(Parametrospresupuestos parametropresupuesto) {
        this.parametropresupuesto = parametropresupuesto;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public CentrosCostos getCentrocostod() {
        return centrocostod;
    }

    public void setCentrocostod(CentrosCostos centrocostod) {
        this.centrocostod = centrocostod;
    }

    public CentrosCostos getCentrocostoc() {
        return centrocostoc;
    }

    public void setCentrocostoc(CentrosCostos centrocostoc) {
        this.centrocostoc = centrocostoc;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public CortesProcesos getCorteproceso() {
        return corteproceso;
    }

    public void setCorteproceso(CortesProcesos corteproceso) {
        this.corteproceso = corteproceso;
    }

    public Cuentas getCuentad() {
        return cuentad;
    }

    public void setCuentad(Cuentas cuentad) {
        this.cuentad = cuentad;
    }

    public Cuentas getCuentac() {
        return cuentac;
    }

    public void setCuentac(Cuentas cuentac) {
        this.cuentac = cuentac;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Estructuras getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Estructuras localizacion) {
        this.localizacion = localizacion;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Nodos getNodo() {
        return nodo;
    }

    public void setNodo(Nodos nodo) {
        this.nodo = nodo;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
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
        if (!(object instanceof SolucionesNodos)) {
            return false;
        }
        SolucionesNodos other = (SolucionesNodos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Solucionesnodos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<SolucionesFormulas> getSolucionesFormulasCollection() {
        return solucionesFormulasCollection;
    }

    public void setSolucionesFormulasCollection(Collection<SolucionesFormulas> solucionesFormulasCollection) {
        this.solucionesFormulasCollection = solucionesFormulasCollection;
    }
    
}
