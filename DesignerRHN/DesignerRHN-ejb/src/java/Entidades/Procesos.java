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
@Table(name = "PROCESOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procesos.findAll", query = "SELECT p FROM Procesos p")})
public class Procesos implements Serializable {
    @OneToMany(mappedBy = "proceso")
    private Collection<InterconDynamics> interconDynamicsCollection;
    @OneToMany(mappedBy = "proceso")
    private Collection<InterconSapBO> interconSapBOCollection;
    @OneToMany(mappedBy = "proceso")
    private Collection<ParametrosContables> parametrosContablesCollection;

    @OneToMany(mappedBy = "proceso")
    private Collection<OperandosGruposConceptos> operandosGruposConceptosCollection;

    @OneToMany(mappedBy = "proceso")
    private Collection<OperandosLogs> operandosLogsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso")
    private List<FormulasProcesos> formulasProcesosList;
    @OneToMany(mappedBy = "proceso")
    private Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection;
    @OneToMany(mappedBy = "procesoliquidado")
    private Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso")
    private List<Parametros> parametrosList;
    @OneToMany(mappedBy = "proceso")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dependiente")
    private Collection<ProcesosDependientes> procesosDependientesCollection;
    @OneToMany(mappedBy = "proceso")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @OneToMany(mappedBy = "proceso")
    private Collection<Parametrospresupuestos> parametrospresupuestosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso")
    private Collection<CortesProcesos> cortesprocesosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "CODIGO")
    private short codigo;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "VALORFINAL")
    private String valorfinal;
    @Size(max = 1)
    @Column(name = "CONTABILIZACION")
    private String contabilizacion;
    @Size(max = 500)
    @Column(name = "COMENTARIOS")
    private String comentarios;
    @Size(max = 1)
    @Column(name = "ELIMINARLIQSOLUCIONNODO")
    private String eliminarliqsolucionnodo;
    @Size(max = 1)
    @Column(name = "ELIMINARLIQADELANTO")
    private String eliminarliqadelanto;
    @Size(max = 20)
    @Column(name = "TIPONS")
    private String tipons;
    @Column(name = "MODALIDADLIQUIDAR")
    private Short modalidadliquidar;
    @Size(max = 1)
    @Column(name = "CONTROLSOBREGIRO")
    private String controlsobregiro;
    @Column(name = "NUMEROCIERREREQUERIDO")
    private BigInteger numerocierrerequerido;
    @Size(max = 1)
    @Column(name = "AUTOMATICO")
    private String automatico;
    @JoinColumn(name = "TIPOPAGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Tipospagos tipopago;
    @JoinColumn(name = "CUENTANETO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuentas cuentaneto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proceso")
    private Collection<ParametrosEstructuras> parametrosestructurasCollection;
    @Transient
    private String strContabilizacion;
    @Transient
    private String strEliminarSNodo;
    @Transient
    private String strEliminarAdelanto;
    @Transient
    private String strEliminarCSobregiro;
    @Transient
    private String strAutomatico;
    @Transient
    private boolean checkContabilizacion;
    @Transient
    private boolean checkEliminarSNodo;
    @Transient
    private boolean checkEliminarAdelanto;
    @Transient
    private boolean checkEliminarCSobregiro;
    @Transient
    private boolean checkAutomatico;

    public Procesos() {
    }

    public Procesos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Procesos(BigInteger secuencia, short codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(String valorfinal) {
        this.valorfinal = valorfinal;
    }

    public String getContabilizacion() {
        if (contabilizacion == null) {
            contabilizacion = "N";
        }
        return contabilizacion;
    }

    public void setContabilizacion(String contabilizacion) {
        this.contabilizacion = contabilizacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEliminarliqsolucionnodo() {
        if (eliminarliqsolucionnodo == null) {
            eliminarliqsolucionnodo = "N";
        }
        return eliminarliqsolucionnodo;
    }

    public void setEliminarliqsolucionnodo(String eliminarliqsolucionnodo) {
        this.eliminarliqsolucionnodo = eliminarliqsolucionnodo;
    }

    public String getEliminarliqadelanto() {
        if (eliminarliqadelanto == null) {
            eliminarliqadelanto = "N";
        }
        return eliminarliqadelanto;
    }

    public void setEliminarliqadelanto(String eliminarliqadelanto) {
        this.eliminarliqadelanto = eliminarliqadelanto;
    }

    public String getTipons() {
        return tipons;
    }

    public void setTipons(String tipons) {
        this.tipons = tipons;
    }

    public Short getModalidadliquidar() {
        return modalidadliquidar;
    }

    public void setModalidadliquidar(Short modalidadliquidar) {
        this.modalidadliquidar = modalidadliquidar;
    }

    public String getControlsobregiro() {
        if (controlsobregiro == null) {
            controlsobregiro = "N";
        }
        return controlsobregiro;
    }

    public void setControlsobregiro(String controlsobregiro) {
        this.controlsobregiro = controlsobregiro;
    }

    public BigInteger getNumerocierrerequerido() {
        return numerocierrerequerido;
    }

    public void setNumerocierrerequerido(BigInteger numerocierrerequerido) {
        this.numerocierrerequerido = numerocierrerequerido;
    }

    public String getAutomatico() {
        if (automatico == null) {
            automatico = "N";
        }
        return automatico;
    }

    public void setAutomatico(String automatico) {
        this.automatico = automatico;
    }

    public Tipospagos getTipopago() {
        return tipopago;
    }

    public void setTipopago(Tipospagos tipopago) {
        this.tipopago = tipopago;
    }

    public Cuentas getCuentaneto() {
        return cuentaneto;
    }

    public void setCuentaneto(Cuentas cuentaneto) {
        this.cuentaneto = cuentaneto;
    }

    public String getStrContabilizacion() {
        getContabilizacion();
        if (contabilizacion == null || contabilizacion.equalsIgnoreCase("N")) {
            strContabilizacion = "NO";
        } else {
            strContabilizacion = "SI";
        }
        return strContabilizacion;
    }

    public void setStrContabilizacion(String strContabilizacion) {
        this.strContabilizacion = strContabilizacion;
    }

    public String getStrEliminarAdelanto() {
        getEliminarliqadelanto();
        if (eliminarliqadelanto == null || eliminarliqadelanto.equalsIgnoreCase("N")) {
            strEliminarAdelanto = "NO";
        } else {
            strEliminarAdelanto = "SI";
        }
        return strEliminarAdelanto;
    }

    public void setStrEliminarAdelanto(String strEliminarAdelanto) {
        this.strEliminarAdelanto = strEliminarAdelanto;
    }

    public String getStrEliminarCSobregiro() {
        getControlsobregiro();
        if (controlsobregiro == null || controlsobregiro.equalsIgnoreCase("N")) {
            strEliminarCSobregiro = "NO";
        } else {
            strEliminarCSobregiro = "SI";
        }
        return strEliminarCSobregiro;
    }

    public void setStrEliminarCSobregiro(String strEliminarCSobregiro) {
        this.strEliminarCSobregiro = strEliminarCSobregiro;
    }

    public String getStrAutomatico() {
        getAutomatico();
        if (automatico == null || automatico.equalsIgnoreCase("N")) {
            strAutomatico = "NO";
        } else {
            strAutomatico = "SI";
        }
        return strAutomatico;
    }

    public void setStrAutomatico(String strAutomatico) {
        this.strAutomatico = strAutomatico;
    }

    public String getStrEliminarSNodo() {
        getEliminarliqsolucionnodo();
        if (eliminarliqsolucionnodo == null || eliminarliqsolucionnodo.equalsIgnoreCase("N")) {
            strEliminarSNodo = "NO";
        } else {
            strEliminarSNodo = "SI";
        }
        return strEliminarSNodo;
    }

    public void setStrEliminarSNodo(String strEliminarSNodo) {
        this.strEliminarSNodo = strEliminarSNodo;
    }

    public boolean isCheckContabilizacion() {
        getContabilizacion();
        if (contabilizacion.equalsIgnoreCase("N")) {
            checkContabilizacion = false;
        } else {
            checkContabilizacion = true;
        }
        return checkContabilizacion;
    }

    public void setCheckContabilizacion(boolean checkContabilizacion) {
        if (checkContabilizacion == false) {
            contabilizacion = "N";
        } else {
            contabilizacion = "S";
        }
        this.checkContabilizacion = checkContabilizacion;
    }

    public boolean isCheckEliminarSNodo() {
        getEliminarliqsolucionnodo();
        if (eliminarliqsolucionnodo.equalsIgnoreCase("N")) {
            checkEliminarSNodo = false;
        } else {
            checkEliminarSNodo = true;
        }
        return checkEliminarSNodo;
    }

    public void setCheckEliminarSNodo(boolean checkEliminarSNodo) {
        if (checkEliminarSNodo == false) {
            eliminarliqsolucionnodo = "N";
        } else {
            eliminarliqsolucionnodo = "S";
        }
        this.checkEliminarSNodo = checkEliminarSNodo;
    }

    public boolean isCheckEliminarAdelanto() {
        getEliminarliqadelanto();
        if (eliminarliqadelanto.equalsIgnoreCase("N")) {
            checkEliminarAdelanto = false;
        } else {
            checkEliminarAdelanto = true;
        }
        return checkEliminarAdelanto;
    }

    public void setCheckEliminarAdelanto(boolean checkEliminarAdelanto) {
        if (checkEliminarAdelanto == false) {
            eliminarliqadelanto = "N";
        } else {
            eliminarliqadelanto = "S";
        }
        this.checkEliminarAdelanto = checkEliminarAdelanto;
    }

    public boolean isCheckEliminarCSobregiro() {
        getControlsobregiro();
        if (controlsobregiro.equalsIgnoreCase("N")) {
            checkEliminarCSobregiro = false;
        } else {
            checkEliminarCSobregiro = true;
        }
        return checkEliminarCSobregiro;
    }

    public void setCheckEliminarCSobregiro(boolean checkEliminarCSobregiro) {
        if (checkEliminarCSobregiro == false) {
            controlsobregiro = "N";
        } else {
            controlsobregiro = "S";
        }
        this.checkEliminarCSobregiro = checkEliminarCSobregiro;
    }

    public boolean isCheckAutomatico() {
        getAutomatico();
        if (automatico.equalsIgnoreCase("N")) {
            checkAutomatico = false;
        } else {
            checkAutomatico = true;
        }
        return checkAutomatico;
    }

    public void setCheckAutomatico(boolean checkAutomatico) {
        if (checkAutomatico == false) {
            automatico = "N";
        } else {
            automatico = "S";
        }
        this.checkAutomatico = checkAutomatico;
    }

    @XmlTransient
    public Collection<ParametrosEstructuras> getParametrosestructurasCollection() {
        return parametrosestructurasCollection;
    }

    public void setParametrosestructurasCollection(Collection<ParametrosEstructuras> parametrosestructurasCollection) {
        this.parametrosestructurasCollection = parametrosestructurasCollection;
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
        if (!(object instanceof Procesos)) {
            return false;
        }
        Procesos other = (Procesos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Procesos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<SolucionesNodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<Parametrospresupuestos> getParametrospresupuestosCollection() {
        return parametrospresupuestosCollection;
    }

    public void setParametrospresupuestosCollection(Collection<Parametrospresupuestos> parametrospresupuestosCollection) {
        this.parametrospresupuestosCollection = parametrospresupuestosCollection;
    }

    @XmlTransient
    public Collection<CortesProcesos> getCortesprocesosCollection() {
        return cortesprocesosCollection;
    }

    public void setCortesprocesosCollection(Collection<CortesProcesos> cortesprocesosCollection) {
        this.cortesprocesosCollection = cortesprocesosCollection;
    }

    @XmlTransient
    public Collection<ProcesosDependientes> getProcesosDependientesCollection() {
        return procesosDependientesCollection;
    }

    public void setProcesosDependientesCollection(Collection<ProcesosDependientes> procesosDependientesCollection) {
        this.procesosDependientesCollection = procesosDependientesCollection;
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }

    @XmlTransient
    public List<Parametros> getParametrosList() {
        return parametrosList;
    }

    public void setParametrosList(List<Parametros> parametrosList) {
        this.parametrosList = parametrosList;
    }

    @XmlTransient
    public Collection<IbcsAutoliquidaciones> getIbcsAutoliquidacionesCollection() {
        return ibcsAutoliquidacionesCollection;
    }

    public void setIbcsAutoliquidacionesCollection(Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection) {
        this.ibcsAutoliquidacionesCollection = ibcsAutoliquidacionesCollection;
    }

    @XmlTransient
    public Collection<IbcsAutoliquidaciones> getIbcsAutoliquidacionesCollection1() {
        return ibcsAutoliquidacionesCollection1;
    }

    public void setIbcsAutoliquidacionesCollection1(Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection1) {
        this.ibcsAutoliquidacionesCollection1 = ibcsAutoliquidacionesCollection1;
    }

    @XmlTransient
    public List<FormulasProcesos> getFormulasProcesosList() {
        return formulasProcesosList;
    }

    public void setFormulasProcesosList(List<FormulasProcesos> formulasProcesosList) {
        this.formulasProcesosList = formulasProcesosList;
    }

    @XmlTransient
    public Collection<OperandosLogs> getOperandosLogsCollection() {
        return operandosLogsCollection;
    }

    public void setOperandosLogsCollection(Collection<OperandosLogs> operandosLogsCollection) {
        this.operandosLogsCollection = operandosLogsCollection;
    }

    @XmlTransient
    public Collection<OperandosGruposConceptos> getOperandosGruposConceptosCollection() {
        return operandosGruposConceptosCollection;
    }

    public void setOperandosGruposConceptosCollection(Collection<OperandosGruposConceptos> operandosGruposConceptosCollection) {
        this.operandosGruposConceptosCollection = operandosGruposConceptosCollection;
    }

    @XmlTransient
    public Collection<ParametrosContables> getParametrosContablesCollection() {
        return parametrosContablesCollection;
    }

    public void setParametrosContablesCollection(Collection<ParametrosContables> parametrosContablesCollection) {
        this.parametrosContablesCollection = parametrosContablesCollection;
    }

    @XmlTransient
    public Collection<InterconSapBO> getInterconSapBOCollection() {
        return interconSapBOCollection;
    }

    public void setInterconSapBOCollection(Collection<InterconSapBO> interconSapBOCollection) {
        this.interconSapBOCollection = interconSapBOCollection;
    }

    @XmlTransient
    public Collection<InterconDynamics> getInterconDynamicsCollection() {
        return interconDynamicsCollection;
    }

    public void setInterconDynamicsCollection(Collection<InterconDynamics> interconDynamicsCollection) {
        this.interconDynamicsCollection = interconDynamicsCollection;
    }

}
