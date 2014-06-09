/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSTRABAJADORES")
@XmlRootElement
@Cacheable(true)
@NamedQueries({
    @NamedQuery(name = "TiposTrabajadores.findAll", query = "SELECT t FROM TiposTrabajadores t ORDER BY t.nombre"),
    @NamedQuery(name = "TiposTrabajadores.findByCodigo", query = "SELECT t FROM TiposTrabajadores t WHERE t.codigo = :codigo")})
public class TiposTrabajadores implements Serializable {
    @OneToMany(mappedBy = "tipotrabajador")
    private Collection<ParametrosInformes> parametrosInformesCollection;

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
    @Size(max = 1)
    @Column(name = "MODALIDAD")
    private String modalidad;
    //@Basic(optional = true)
    //@NotNull
   // @Size(min = 1, max = 40)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 15)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "NIVELENDEUDAMIENTO")
    private Short nivelendeudamiento;
    @Size(max = 20)
    @Column(name = "BASEENDEUDAMIENTO")
    private String baseendeudamiento;
    @Column(name = "PORCENTAJESML")
    private Short porcentajesml;
    @Column(name = "DERECHODIASVACACIONES")
    private BigInteger derechodiasvacaciones;
    @Column(name = "FACTORDESALARIZACION")
    private BigInteger factordesalarizacion;
    @Column(name = "DIASVACACIONESNOORDINARIOS")
    private BigInteger diasvacacionesnoordinarios;
    @Size(max = 1)
    @Column(name = "PATRONPAGASALUD")
    private String patronpagasalud;
    @Size(max = 1)
    @Column(name = "PATRONPAGAPENSION")
    private String patronpagapension;
    @Size(max = 1)
    @Column(name = "PATRONPAGARETENCION")
    private String patronpagaretencion;
    @Size(max = 1)
    @Column(name = "DOCENTECOLEGIO")
    private String docentecolegio;
    @Size(max = 1)
    @Column(name = "MODALIDADPENSIONSECTORSALUD")
    private String modalidadpensionsectorsalud;
    @Size(max = 1)
    @Column(name = "SEMESTREESPECIAL")
    private String semestreespecial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipotrabajador")
    private Collection<EscalafonesSalariales> escalafonessalarialesCollection;
    @JoinColumn(name = "TIPOCOTIZANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposCotizantes tipocotizante;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipotrabajador")
     private Collection<VigenciasConceptosTT> vigenciasconceptosttCollection;*/
    @OneToMany(mappedBy = "tipotrabajadorretiro")
    private Collection<MotivosRetiros> motivosretirosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipotrabajador")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @OneToMany(mappedBy = "tipotrabajador")
    private Collection<Parametrospresupuestos> parametrospresupuestosCollection;
    @OneToMany(mappedBy = "tipotrabajador")
    private Collection<ParametrosEstructuras> parametrosestructurasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipotrabajador")
    private Collection<VigenciasTiposTrabajadores> vigenciastipostrabajadoresCollection;

    public TiposTrabajadores() {
    }

    public TiposTrabajadores(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposTrabajadores(BigInteger secuencia, short codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Short getNivelendeudamiento() {
        return nivelendeudamiento;
    }

    public void setNivelendeudamiento(Short nivelendeudamiento) {
        this.nivelendeudamiento = nivelendeudamiento;
    }

    public String getBaseendeudamiento() {
        return baseendeudamiento;
    }

    public void setBaseendeudamiento(String baseendeudamiento) {
        this.baseendeudamiento = baseendeudamiento;
    }

    public Short getPorcentajesml() {
        return porcentajesml;
    }

    public void setPorcentajesml(Short porcentajesml) {
        this.porcentajesml = porcentajesml;
    }

    public BigInteger getDerechodiasvacaciones() {
        return derechodiasvacaciones;
    }

    public void setDerechodiasvacaciones(BigInteger derechodiasvacaciones) {
        this.derechodiasvacaciones = derechodiasvacaciones;
    }

    public BigInteger getFactordesalarizacion() {
        return factordesalarizacion;
    }

    public void setFactordesalarizacion(BigInteger factordesalarizacion) {
        this.factordesalarizacion = factordesalarizacion;
    }

    public BigInteger getDiasvacacionesnoordinarios() {
        return diasvacacionesnoordinarios;
    }

    public void setDiasvacacionesnoordinarios(BigInteger diasvacacionesnoordinarios) {
        this.diasvacacionesnoordinarios = diasvacacionesnoordinarios;
    }

    public String getPatronpagasalud() {
        return patronpagasalud;
    }

    public void setPatronpagasalud(String patronpagasalud) {
        this.patronpagasalud = patronpagasalud;
    }

    public String getPatronpagapension() {
        return patronpagapension;
    }

    public void setPatronpagapension(String patronpagapension) {
        this.patronpagapension = patronpagapension;
    }

    public String getPatronpagaretencion() {
        return patronpagaretencion;
    }

    public void setPatronpagaretencion(String patronpagaretencion) {
        this.patronpagaretencion = patronpagaretencion;
    }

    public String getDocentecolegio() {
        return docentecolegio;
    }

    public void setDocentecolegio(String docentecolegio) {
        this.docentecolegio = docentecolegio;
    }

    public String getModalidadpensionsectorsalud() {
        return modalidadpensionsectorsalud;
    }

    public void setModalidadpensionsectorsalud(String modalidadpensionsectorsalud) {
        this.modalidadpensionsectorsalud = modalidadpensionsectorsalud;
    }

    public String getSemestreespecial() {
        return semestreespecial;
    }

    public void setSemestreespecial(String semestreespecial) {
        this.semestreespecial = semestreespecial;
    }

    @XmlTransient
    public Collection<EscalafonesSalariales> getEscalafonessalarialesCollection() {
        return escalafonessalarialesCollection;
    }

    public void setEscalafonessalarialesCollection(Collection<EscalafonesSalariales> escalafonessalarialesCollection) {
        this.escalafonessalarialesCollection = escalafonessalarialesCollection;
    }

    public TiposCotizantes getTipocotizante() {
        return tipocotizante;
    }

    public void setTipocotizante(TiposCotizantes tipocotizante) {
        this.tipocotizante = tipocotizante;
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
        if (!(object instanceof TiposTrabajadores)) {
            return false;
        }
        TiposTrabajadores other = (TiposTrabajadores) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tipostrabajadores[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasTiposTrabajadores> getVigenciastipostrabajadoresCollection() {
        return vigenciastipostrabajadoresCollection;
    }

    public void setVigenciastipostrabajadoresCollection(Collection<VigenciasTiposTrabajadores> vigenciastipostrabajadoresCollection) {
        this.vigenciastipostrabajadoresCollection = vigenciastipostrabajadoresCollection;
    }

    @XmlTransient
    public Collection<ParametrosEstructuras> getParametrosestructurasCollection() {
        return parametrosestructurasCollection;
    }

    public void setParametrosestructurasCollection(Collection<ParametrosEstructuras> parametrosestructurasCollection) {
        this.parametrosestructurasCollection = parametrosestructurasCollection;
    }

    @XmlTransient
    public Collection<MotivosRetiros> getMotivosretirosCollection() {
        return motivosretirosCollection;
    }

    public void setMotivosretirosCollection(Collection<MotivosRetiros> motivosretirosCollection) {
        this.motivosretirosCollection = motivosretirosCollection;
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
    /*
     public Collection<VigenciasConceptosTT> getVigenciasconceptosttCollection() {
     return vigenciasconceptosttCollection;
     }

     public void setVigenciasconceptosttCollection(Collection<VigenciasConceptosTT> vigenciasconceptosttCollection) {
     this.vigenciasconceptosttCollection = vigenciasconceptosttCollection;
     }
     */

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }
}
