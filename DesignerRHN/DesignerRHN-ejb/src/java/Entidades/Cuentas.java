/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
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
 * @author Administrador
 */
@Entity
@Table(name = "CUENTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c"),
    @NamedQuery(name = "Cuentas.findBySecuencia", query = "SELECT c FROM Cuentas c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "Cuentas.findByCodigo", query = "SELECT c FROM Cuentas c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cuentas.findByDescripcion", query = "SELECT c FROM Cuentas c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Cuentas.findByManejanit", query = "SELECT c FROM Cuentas c WHERE c.manejanit = :manejanit"),
    @NamedQuery(name = "Cuentas.findByProrrateo", query = "SELECT c FROM Cuentas c WHERE c.prorrateo = :prorrateo"),
    @NamedQuery(name = "Cuentas.findByManejanitempleado", query = "SELECT c FROM Cuentas c WHERE c.manejanitempleado = :manejanitempleado"),
    @NamedQuery(name = "Cuentas.findByCentrocostoc", query = "SELECT c FROM Cuentas c WHERE c.centrocostoc = :centrocostoc"),
    @NamedQuery(name = "Cuentas.findByCentrocostog", query = "SELECT c FROM Cuentas c WHERE c.centrocostog = :centrocostog"),
    @NamedQuery(name = "Cuentas.findByTerceroasociado", query = "SELECT c FROM Cuentas c WHERE c.terceroasociado = :terceroasociado"),
    @NamedQuery(name = "Cuentas.findByTerceroalternativo", query = "SELECT c FROM Cuentas c WHERE c.terceroalternativo = :terceroalternativo"),
    @NamedQuery(name = "Cuentas.findByNaturaleza", query = "SELECT c FROM Cuentas c WHERE c.naturaleza = :naturaleza"),
    @NamedQuery(name = "Cuentas.findByTipo", query = "SELECT c FROM Cuentas c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Cuentas.findByCambianaturaleza", query = "SELECT c FROM Cuentas c WHERE c.cambianaturaleza = :cambianaturaleza"),
    @NamedQuery(name = "Cuentas.findByManejacentrocosto", query = "SELECT c FROM Cuentas c WHERE c.manejacentrocosto = :manejacentrocosto"),
    @NamedQuery(name = "Cuentas.findByIncluyecentrocostocodigocuenta", query = "SELECT c FROM Cuentas c WHERE c.incluyecentrocostocodigocuenta = :incluyecentrocostocodigocuenta"),
    @NamedQuery(name = "Cuentas.findByCodigoalternativo", query = "SELECT c FROM Cuentas c WHERE c.codigoalternativo = :codigoalternativo"),
    @NamedQuery(name = "Cuentas.findByConsolidanitempresa", query = "SELECT c FROM Cuentas c WHERE c.consolidanitempresa = :consolidanitempresa"),
    @NamedQuery(name = "Cuentas.findByIncluyeshortnamesapbo", query = "SELECT c FROM Cuentas c WHERE c.incluyeshortnamesapbo = :incluyeshortnamesapbo"),
    @NamedQuery(name = "Cuentas.findByIdentificaretencion", query = "SELECT c FROM Cuentas c WHERE c.identificaretencion = :identificaretencion"),
    @NamedQuery(name = "Cuentas.findByCuentaasociadasap", query = "SELECT c FROM Cuentas c WHERE c.cuentaasociadasap = :cuentaasociadasap"),
    @NamedQuery(name = "Cuentas.findByCodigoespecial", query = "SELECT c FROM Cuentas c WHERE c.codigoespecial = :codigoespecial"),
    @NamedQuery(name = "Cuentas.findByManejasubcuenta", query = "SELECT c FROM Cuentas c WHERE c.manejasubcuenta = :manejasubcuenta")})
public class Cuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "MANEJANIT")
    private String manejanit;
    @Size(max = 1)
    @Column(name = "PRORRATEO")
    private String prorrateo;
    @Size(max = 1)
    @Column(name = "MANEJANITEMPLEADO")
    private String manejanitempleado;
    @Size(max = 1)
    @Column(name = "CENTROCOSTOC")
    private String centrocostoc;
    @Size(max = 1)
    @Column(name = "CENTROCOSTOG")
    private String centrocostog;
    @Size(max = 1)
    @Column(name = "TERCEROASOCIADO")
    private String terceroasociado;
    @Size(max = 1)
    @Column(name = "TERCEROALTERNATIVO")
    private String terceroalternativo;
    @Size(max = 10)
    @Column(name = "NATURALEZA")
    private String naturaleza;
    @Size(max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 1)
    @Column(name = "CAMBIANATURALEZA")
    private String cambianaturaleza;
    @Size(max = 1)
    @Column(name = "MANEJACENTROCOSTO")
    private String manejacentrocosto;
    @Size(max = 1)
    @Column(name = "INCLUYECENTROCOSTOCODIGOCUENTA")
    private String incluyecentrocostocodigocuenta;
    @Size(max = 30)
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;
    @Size(max = 1)
    @Column(name = "CONSOLIDANITEMPRESA")
    private String consolidanitempresa;
    @Size(max = 1)
    @Column(name = "INCLUYESHORTNAMESAPBO")
    private String incluyeshortnamesapbo;
    @Size(max = 1)
    @Column(name = "IDENTIFICARETENCION")
    private String identificaretencion;
    @Size(max = 1)
    @Column(name = "CUENTAASOCIADASAP")
    private String cuentaasociadasap;
    @Size(max = 20)
    @Column(name = "CODIGOESPECIAL")
    private String codigoespecial;
    @Size(max = 1)
    @Column(name = "MANEJASUBCUENTA")
    private String manejasubcuenta;
    @OneToMany(mappedBy = "cuenta")
    private Collection<Rubrospresupuestales> rubrospresupuestalesCollection;
    @JoinColumn(name = "RUBROPRESUPUESTAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Rubrospresupuestales rubropresupuestal;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;
    @OneToMany(mappedBy = "contracuentatesoreria")
    private Collection<Cuentas> cuentasCollection;
    @JoinColumn(name = "CONTRACUENTATESORERIA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuentas contracuentatesoreria;
    @Transient
    private boolean checkManejaNit;
    @Transient
    private boolean checkCCEmpleado;
    @Transient
    private boolean checkProrrateo;
    @Transient
    private boolean checkConsolidaNITEmpresa;
    @Transient
    private boolean checkShortName;
    @Transient
    private boolean checkAsociadaSAP;
    @Transient
    private boolean checkSubCuenta;

    public Cuentas() {
    }

    public Cuentas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Cuentas(BigInteger secuencia, String codigo, String descripcion) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getManejanit() {
        if (manejanit == null) {
            manejanit = "N";
        }
        return manejanit;
    }

    public void setManejanit(String manejanit) {
        this.manejanit = manejanit;
    }

    public String getProrrateo() {
        if (prorrateo == null) {
            prorrateo = "N";
        }
        return prorrateo;
    }

    public void setProrrateo(String prorrateo) {
        this.prorrateo = prorrateo;
    }

    public String getManejanitempleado() {
        if (manejanitempleado == null) {
            manejanitempleado = "N";
        }
        return manejanitempleado;
    }

    public void setManejanitempleado(String manejanitempleado) {
        this.manejanitempleado = manejanitempleado;
    }

    public String getCentrocostoc() {
        return centrocostoc;
    }

    public void setCentrocostoc(String centrocostoc) {
        this.centrocostoc = centrocostoc;
    }

    public String getCentrocostog() {
        return centrocostog;
    }

    public void setCentrocostog(String centrocostog) {
        this.centrocostog = centrocostog;
    }

    public String getTerceroasociado() {
        return terceroasociado;
    }

    public void setTerceroasociado(String terceroasociado) {
        this.terceroasociado = terceroasociado;
    }

    public String getTerceroalternativo() {
        return terceroalternativo;
    }

    public void setTerceroalternativo(String terceroalternativo) {
        this.terceroalternativo = terceroalternativo;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCambianaturaleza() {
        return cambianaturaleza;
    }

    public void setCambianaturaleza(String cambianaturaleza) {
        this.cambianaturaleza = cambianaturaleza;
    }

    public String getManejacentrocosto() {
        return manejacentrocosto;
    }

    public void setManejacentrocosto(String manejacentrocosto) {
        this.manejacentrocosto = manejacentrocosto;
    }

    public String getIncluyecentrocostocodigocuenta() {
        return incluyecentrocostocodigocuenta;
    }

    public void setIncluyecentrocostocodigocuenta(String incluyecentrocostocodigocuenta) {
        this.incluyecentrocostocodigocuenta = incluyecentrocostocodigocuenta;
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public String getConsolidanitempresa() {
        if (consolidanitempresa == null) {
            consolidanitempresa = "N";
        }
        return consolidanitempresa;
    }

    public void setConsolidanitempresa(String consolidanitempresa) {
        this.consolidanitempresa = consolidanitempresa;
    }

    public String getIncluyeshortnamesapbo() {
        if (incluyeshortnamesapbo == null) {
            incluyeshortnamesapbo = "N";
        }
        return incluyeshortnamesapbo;
    }

    public void setIncluyeshortnamesapbo(String incluyeshortnamesapbo) {
        this.incluyeshortnamesapbo = incluyeshortnamesapbo;
    }

    public String getIdentificaretencion() {
        return identificaretencion;
    }

    public void setIdentificaretencion(String identificaretencion) {
        this.identificaretencion = identificaretencion;
    }

    public String getCuentaasociadasap() {
        if (cuentaasociadasap == null) {
            cuentaasociadasap = "N";
        }
        return cuentaasociadasap;
    }

    public void setCuentaasociadasap(String cuentaasociadasap) {
        this.cuentaasociadasap = cuentaasociadasap;
    }

    public String getCodigoespecial() {
        return codigoespecial;
    }

    public void setCodigoespecial(String codigoespecial) {
        this.codigoespecial = codigoespecial;
    }

    public String getManejasubcuenta() {
        if (manejasubcuenta == null) {
            manejasubcuenta = "N";
        }
        return manejasubcuenta;
    }

    public void setManejasubcuenta(String manejasubcuenta) {
        this.manejasubcuenta = manejasubcuenta;
    }

    @XmlTransient
    public Collection<Rubrospresupuestales> getRubrospresupuestalesCollection() {
        return rubrospresupuestalesCollection;
    }

    public void setRubrospresupuestalesCollection(Collection<Rubrospresupuestales> rubrospresupuestalesCollection) {
        this.rubrospresupuestalesCollection = rubrospresupuestalesCollection;
    }

    public Rubrospresupuestales getRubropresupuestal() {
        if (rubropresupuestal == null) {
            rubropresupuestal = new Rubrospresupuestales();
        }
        return rubropresupuestal;
    }

    public void setRubropresupuestal(Rubrospresupuestales rubropresupuestal) {
        this.rubropresupuestal = rubropresupuestal;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public boolean isCheckManejaNit() {
        getManejanit();
        if (manejanit == null || manejanit.equalsIgnoreCase("N")) {
            checkManejaNit = false;
        }
        if (manejanit.equalsIgnoreCase("S")) {
            checkManejaNit = true;
        }
        return checkManejaNit;
    }

    public void setCheckManejaNit(boolean check) {
        if (check == false) {
            manejanit = "N";
        }
        if (check == true) {
            manejanit = "S";
        }
        this.checkManejaNit = check;
    }

    public boolean isCheckCCEmpleado() {
        getManejanitempleado();
        if (manejanitempleado == null || manejanitempleado.equalsIgnoreCase("N")) {
            checkCCEmpleado = false;
        }
        if (manejanitempleado.equalsIgnoreCase("S")) {
            checkCCEmpleado = true;
        }
        return checkCCEmpleado;
    }

    public void setCheckCCEmpleado(boolean check) {
        if (check == true) {
            manejanitempleado = "S";
        }
        if (check == false) {
            manejanitempleado = "N";
        }
        this.checkCCEmpleado = check;
    }

    public boolean isCheckProrrateo() {
        getProrrateo();
        if (prorrateo == null || prorrateo.equalsIgnoreCase("N")) {
            checkProrrateo = false;
        }
        if (prorrateo.equalsIgnoreCase("S")) {
            checkProrrateo = true;
        }
        return checkProrrateo;
    }

    public void setCheckProrrateo(boolean check) {
        if (check == false) {
            prorrateo = "N";
        }
        if (check == true) {
            prorrateo = "S";
        }
        this.checkProrrateo = check;
    }

    public boolean isCheckConsolidaNITEmpresa() {
        getConsolidanitempresa();
        if (consolidanitempresa.equalsIgnoreCase("N") || consolidanitempresa == null) {
            checkConsolidaNITEmpresa = false;
        }
        if (consolidanitempresa.equalsIgnoreCase("S")) {
            checkConsolidaNITEmpresa = true;
        }
        return checkConsolidaNITEmpresa;
    }

    public void setCheckConsolidaNITEmpresa(boolean check) {
        if (check == false) {
            consolidanitempresa = "N";
        }
        if (check == true) {
            consolidanitempresa = "S";
        }
        this.checkConsolidaNITEmpresa = check;
    }

    public boolean isCheckShortName() {
        getIncluyeshortnamesapbo();
        if (incluyeshortnamesapbo.equalsIgnoreCase("N") || incluyeshortnamesapbo == null) {
            checkShortName = false;
        }
        if (incluyeshortnamesapbo.equalsIgnoreCase("S")) {
            checkShortName = true;
        }
        return checkShortName;
    }

    public void setCheckShortName(boolean check) {
        if (check == true) {
            incluyeshortnamesapbo = "S";
        }
        if (check == false) {
            incluyeshortnamesapbo = "N";
        }
        this.checkShortName = check;
    }

    public boolean isCheckAsociadaSAP() {
        getCuentaasociadasap();
        if (cuentaasociadasap == null || cuentaasociadasap.equalsIgnoreCase("N")) {
            checkAsociadaSAP = false;
        }
        if (cuentaasociadasap.equalsIgnoreCase("S")) {
            checkAsociadaSAP = true;
        }
        return checkAsociadaSAP;
    }

    public void setCheckAsociadaSAP(boolean check) {
        if (check == true) {
            cuentaasociadasap = "S";
        }
        if (check == false) {
            cuentaasociadasap = "N";
        }
        this.checkAsociadaSAP = check;
    }

    public boolean isCheckSubCuenta() {
        getManejasubcuenta();
        if (manejasubcuenta == null || manejasubcuenta.equalsIgnoreCase("N")) {
            checkSubCuenta = false;
        } else {
            if (manejasubcuenta.equalsIgnoreCase("S")) {
                checkSubCuenta = true;
            }
        }
        return checkSubCuenta;
    }

    public void setCheckSubCuenta(boolean check) {
        if (check == false) {
            manejasubcuenta = "N";
        }
        if (check == true) {
            manejasubcuenta = "S";
        }
        this.checkSubCuenta = check;
    }

    @XmlTransient
    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    public Cuentas getContracuentatesoreria() {
        if (contracuentatesoreria == null) {
            contracuentatesoreria = new Cuentas();
        }
        return contracuentatesoreria;
    }

    public void setContracuentatesoreria(Cuentas contracuentatesoreria) {
        this.contracuentatesoreria = contracuentatesoreria;
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
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cuentas[ secuencia=" + secuencia + " ]";
    }

}
