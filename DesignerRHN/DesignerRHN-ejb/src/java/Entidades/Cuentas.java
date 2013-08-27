/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CUENTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c")})
public class Cuentas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentad")
    private Collection<Solucionesnodos> solucionesnodosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentac")
    private Collection<Solucionesnodos> solucionesnodosCollection1;
    @OneToMany(mappedBy = "cuentad")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(mappedBy = "cuentac")
    private Collection<Novedades> novedadesCollection1;
    @OneToMany(mappedBy = "cuentaneto")
    private Collection<Procesos> procesosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
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
    @OneToMany(mappedBy = "cuenta")
    private Collection<Rubrospresupuestales> rubrospresupuestalesCollection;

    public Cuentas() {
    }

    public Cuentas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Cuentas(BigDecimal secuencia, String codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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
        return manejanit;
    }

    public void setManejanit(String manejanit) {
        this.manejanit = manejanit;
    }

    public String getProrrateo() {
        return prorrateo;
    }

    public void setProrrateo(String prorrateo) {
        this.prorrateo = prorrateo;
    }

    public String getManejanitempleado() {
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
        return consolidanitempresa;
    }

    public void setConsolidanitempresa(String consolidanitempresa) {
        this.consolidanitempresa = consolidanitempresa;
    }

    public String getIncluyeshortnamesapbo() {
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

    public Rubrospresupuestales getRubropresupuestal() {
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

    @XmlTransient
    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    public Cuentas getContracuentatesoreria() {
        return contracuentatesoreria;
    }

    public void setContracuentatesoreria(Cuentas contracuentatesoreria) {
        this.contracuentatesoreria = contracuentatesoreria;
    }

    @XmlTransient
    public Collection<Rubrospresupuestales> getRubrospresupuestalesCollection() {
        return rubrospresupuestalesCollection;
    }

    public void setRubrospresupuestalesCollection(Collection<Rubrospresupuestales> rubrospresupuestalesCollection) {
        this.rubrospresupuestalesCollection = rubrospresupuestalesCollection;
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

    @XmlTransient
    public Collection<Procesos> getProcesosCollection() {
        return procesosCollection;
    }

    public void setProcesosCollection(Collection<Procesos> procesosCollection) {
        this.procesosCollection = procesosCollection;
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<Solucionesnodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection1() {
        return solucionesnodosCollection1;
    }

    public void setSolucionesnodosCollection1(Collection<Solucionesnodos> solucionesnodosCollection1) {
        this.solucionesnodosCollection1 = solucionesnodosCollection1;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection1() {
        return novedadesCollection1;
    }

    public void setNovedadesCollection1(Collection<Novedades> novedadesCollection1) {
        this.novedadesCollection1 = novedadesCollection1;
    }
    
}
