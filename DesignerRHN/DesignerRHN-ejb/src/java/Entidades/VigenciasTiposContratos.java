/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASTIPOSCONTRATOS")
@NamedQueries({
    @NamedQuery(name = "VigenciasTiposContratos.findAll", query = "SELECT v FROM VigenciasTiposContratos v")})
public class VigenciasTiposContratos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @Size(max = 1024)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 1)
    @Column(name = "RECONOCEPRIMAVACA")
    private String reconoceprimavaca;
    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Size(max = 1)
    @Column(name = "SOLUCIONCONTINUIDAD")
    private String solucioncontinuidad;
    @Column(name = "INICIOSUSTITUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iniciosustitucion;
    @Column(name = "INICIOFLEXIBILIZA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioflexibiliza;
    @JoinColumn(name = "TIPOCONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposContratos tipocontrato;
    @JoinColumn(name = "MOTIVOCONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private MotivosContratos motivocontrato;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA", nullable = true)
    @ManyToOne
    private Ciudades ciudad;

    public VigenciasTiposContratos() {
    }

    public VigenciasTiposContratos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasTiposContratos(BigInteger secuencia, Date fechavigencia) {
        this.secuencia = secuencia;
        this.fechavigencia = fechavigencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getReconoceprimavaca() {
        return reconoceprimavaca;
    }

    public void setReconoceprimavaca(String reconoceprimavaca) {
        this.reconoceprimavaca = reconoceprimavaca;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public String getSolucioncontinuidad() {
        return solucioncontinuidad;
    }

    public void setSolucioncontinuidad(String solucioncontinuidad) {
        this.solucioncontinuidad = solucioncontinuidad;
    }

    public Date getIniciosustitucion() {
        return iniciosustitucion;
    }

    public void setIniciosustitucion(Date iniciosustitucion) {
        this.iniciosustitucion = iniciosustitucion;
    }

    public Date getInicioflexibiliza() {
        return inicioflexibiliza;
    }

    public void setInicioflexibiliza(Date inicioflexibiliza) {
        this.inicioflexibiliza = inicioflexibiliza;
    }

    public TiposContratos getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(TiposContratos tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public MotivosContratos getMotivocontrato() {
        return motivocontrato;
    }

    public void setMotivocontrato(MotivosContratos motivocontrato) {
        this.motivocontrato = motivocontrato;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Ciudades getCiudad() {
        if(ciudad == null){
            ciudad = new Ciudades();
            ciudad.setNombre(" ");
         return ciudad;
        }else{
        return ciudad;    
        }
    }

    public void setCiudad(Ciudades ciudad) {
            this.ciudad = ciudad;
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
        if (!(object instanceof VigenciasTiposContratos)) {
            return false;
        }
        VigenciasTiposContratos other = (VigenciasTiposContratos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vigenciastiposcontratos[ secuencia=" + secuencia + " ]";
    }
}
