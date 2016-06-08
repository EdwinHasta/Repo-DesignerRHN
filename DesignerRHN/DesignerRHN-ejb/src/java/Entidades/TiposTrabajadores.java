package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSTRABAJADORES")
public class TiposTrabajadores implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Column(name = "CESANTIASECTORCONSTRUCCION")
    private String cesantiasectorconstruccion;
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
    @Size(max = 1)
    @Column(name = "PROMEDIABASICOACUMULADOS")
    private String promediabasicoacumulados;
    @JoinColumn(name = "TIPOCOTIZANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposCotizantes tipocotizante;

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
        this.nombre = nombre.toUpperCase();
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

    public String getCesantiasectorconstruccion() {
        return cesantiasectorconstruccion;
    }

    public void setCesantiasectorconstruccion(String cesantiasectorconstruccion) {
        this.cesantiasectorconstruccion = cesantiasectorconstruccion;
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

    public String getPromediabasicoacumulados() {
        return promediabasicoacumulados;
    }

    public void setPromediabasicoacumulados(String promediabasicoacumulados) {
        this.promediabasicoacumulados = promediabasicoacumulados;
    }
}
