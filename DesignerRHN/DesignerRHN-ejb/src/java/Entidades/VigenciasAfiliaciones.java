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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "VIGENCIASAFILIACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasAfiliaciones.findAll", query = "SELECT v FROM VigenciasAfiliaciones v"),
    @NamedQuery(name = "VigenciasAfiliaciones.findBySecuencia", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.secuencia = :secuencia"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByFechainicial", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.fechainicial = :fechainicial"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByFechafinal", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.fechafinal = :fechafinal"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByCodigo", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByModalidad", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.modalidad = :modalidad"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByValor", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.valor = :valor"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByObservaciones", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.observaciones = :observaciones"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByObservacion", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.observacion = :observacion"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByRegimen", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.regimen = :regimen"),
    @NamedQuery(name = "VigenciasAfiliaciones.findByTipoafiliado", query = "SELECT v FROM VigenciasAfiliaciones v WHERE v.tipoafiliado = :tipoafiliado")})
public class VigenciasAfiliaciones implements Serializable { 
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Size(max = 12)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "MODALIDAD")
    private Short modalidad;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Size(max = 100)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 100)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 1)
    @Column(name = "REGIMEN")
    private String regimen;
    @Size(max = 1)
    @Column(name = "TIPOAFILIADO")
    private String tipoafiliado;
    @JoinColumn(name = "VIGENCIASUELDO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private VigenciasSueldos vigenciasueldo;
    @JoinColumn(name = "TIPOENTIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposEntidades tipoentidad;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TercerosSucursales tercerosucursal;
    @JoinColumn(name = "FORMULACONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Formulascontratos formulacontrato;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Formulas formula;
    @JoinColumn(name = "ESTADOAFILIACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EstadosAfiliaciones estadoafiliacion;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Conceptos concepto;

    public VigenciasAfiliaciones() {
    }

    public VigenciasAfiliaciones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasAfiliaciones(BigInteger secuencia, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
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

    public Short getModalidad() {
        return modalidad;
    }

    public void setModalidad(Short modalidad) {
        this.modalidad = modalidad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getTipoafiliado() {
        return tipoafiliado;
    }

    public void setTipoafiliado(String tipoafiliado) {
        this.tipoafiliado = tipoafiliado;
    }

    public VigenciasSueldos getVigenciasueldo() {
        return vigenciasueldo;
    }

    public void setVigenciasueldo(VigenciasSueldos vigenciasueldo) {
        this.vigenciasueldo = vigenciasueldo;
    }

    public TiposEntidades getTipoentidad() {
        if(tipoentidad == null){
            tipoentidad = new TiposEntidades();
        }
        return tipoentidad;
    }

    public void setTipoentidad(TiposEntidades tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public TercerosSucursales getTercerosucursal() {
        return tercerosucursal;
    }

    public void setTercerosucursal(TercerosSucursales tercerosucursal) {
        this.tercerosucursal = tercerosucursal;
    }

   
    public Formulascontratos getFormulacontrato() {
        return formulacontrato;
    }

    public void setFormulacontrato(Formulascontratos formulacontrato) {
        this.formulacontrato = formulacontrato;
    }

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public EstadosAfiliaciones getEstadoafiliacion() {
        return estadoafiliacion;
    }

    public void setEstadoafiliacion(EstadosAfiliaciones estadoafiliacion) {
        this.estadoafiliacion = estadoafiliacion;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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
        if (!(object instanceof VigenciasAfiliaciones)) {
            return false;
        }
        VigenciasAfiliaciones other = (VigenciasAfiliaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciasafiliaciones[ secuencia=" + secuencia + " ]";
    }
    
}
