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
@Table(name = "NOVEDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Novedades.findAll", query = "SELECT n FROM Novedades n")})
public class Novedades implements Serializable {
    @OneToMany(mappedBy = "novedad")
    private Collection<SolucionesFormulas> solucionesFormulasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAREPORTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechareporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TERMINAL")
    private String terminal;
    @Size(max = 20)
    @Column(name = "DOCUMENTOSOPORTE")
    private String documentosoporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORTOTAL")
    private BigDecimal valortotal;
    @Size(max = 100)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @Column(name = "UNIDADESPARTEENTERA")
    private Integer unidadesparteentera;
    @Column(name = "UNIDADESPARTEFRACCION")
    private Integer unidadespartefraccion;
    @Column(name = "TEMPNOVEDADSINCONSTRAINT")
    private BigInteger tempnovedadsinconstraint;
    @Column(name = "MOTIVONOVEDAD")
    private BigInteger motivonovedad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "novedad")
    private Collection<Vacaciones> vacacionesCollection;
    @JoinColumn(name = "USUARIOREPORTA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Usuarios usuarioreporta;
    @JoinColumn(name = "CENTROCOSTOD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocostod;
    @JoinColumn(name = "CENTROCOSTOC", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private CentrosCostos centrocostoc;
    @JoinColumn(name = "ANULACION", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Comprobantes anulacion;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;
    @JoinColumn(name = "CUENTAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuentas cuentad;
    @JoinColumn(name = "CUENTAC", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuentas cuentac;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "FORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Formulas formula;
    @JoinColumn(name = "PERIODICIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Periodicidades periodicidad;
    @JoinColumn(name = "AJUSTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private SolucionesNodos ajuste;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros tercero;
    @JoinColumn(name = "USUARIOAPRUEBA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Usuarios usuarioaprueba;

    public Novedades() {
    }

    public Novedades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Novedades(BigInteger secuencia, Date fechainicial, Date fechareporte, String terminal, String tipo, BigDecimal valortotal) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
        this.fechareporte = fechareporte;
        this.terminal = terminal;
        this.tipo = tipo;
        this.valortotal = valortotal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechareporte() {
        return fechareporte;
    }

    public void setFechareporte(Date fechareporte) {
        this.fechareporte = fechareporte;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDocumentosoporte() {
        return documentosoporte;
    }

    public void setDocumentosoporte(String documentosoporte) {
        this.documentosoporte = documentosoporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getUnidadesparteentera() {
        return unidadesparteentera;
    }

    public void setUnidadesparteentera(Integer unidadesparteentera) {
        this.unidadesparteentera = unidadesparteentera;
    }

    public Integer getUnidadespartefraccion() {
        return unidadespartefraccion;
    }

    public void setUnidadespartefraccion(Integer unidadespartefraccion) {
        this.unidadespartefraccion = unidadespartefraccion;
    }

    public BigInteger getTempnovedadsinconstraint() {
        return tempnovedadsinconstraint;
    }

    public void setTempnovedadsinconstraint(BigInteger tempnovedadsinconstraint) {
        this.tempnovedadsinconstraint = tempnovedadsinconstraint;
    }

    public BigInteger getMotivonovedad() {
        return motivonovedad;
    }

    public void setMotivonovedad(BigInteger motivonovedad) {
        this.motivonovedad = motivonovedad;
    }

    @XmlTransient
    public Collection<Vacaciones> getVacacionesCollection() {
        return vacacionesCollection;
    }

    public void setVacacionesCollection(Collection<Vacaciones> vacacionesCollection) {
        this.vacacionesCollection = vacacionesCollection;
    }

    public Usuarios getUsuarioreporta() {
        return usuarioreporta;
    }

    public void setUsuarioreporta(Usuarios usuarioreporta) {
        this.usuarioreporta = usuarioreporta;
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

    public Comprobantes getAnulacion() {
        return anulacion;
    }

    public void setAnulacion(Comprobantes anulacion) {
        this.anulacion = anulacion;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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

    public Formulas getFormula() {
        return formula;
    }

    public void setFormula(Formulas formula) {
        this.formula = formula;
    }

    public Periodicidades getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidades periodicidad) {
        this.periodicidad = periodicidad;
    }

    public SolucionesNodos getAjuste() {
        return ajuste;
    }

    public void setAjuste(SolucionesNodos ajuste) {
        this.ajuste = ajuste;
    }

    public Terceros getTercero() {
        if(tercero == null){
            tercero = new Terceros();
        }
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    public Usuarios getUsuarioaprueba() {
        return usuarioaprueba;
    }

    public void setUsuarioaprueba(Usuarios usuarioaprueba) {
        this.usuarioaprueba = usuarioaprueba;
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
        if (!(object instanceof Novedades)) {
            return false;
        }
        Novedades other = (Novedades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Novedades[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<SolucionesFormulas> getSolucionesFormulasCollection() {
        return solucionesFormulasCollection;
    }

    public void setSolucionesFormulasCollection(Collection<SolucionesFormulas> solucionesFormulasCollection) {
        this.solucionesFormulasCollection = solucionesFormulasCollection;
    }
    
}
