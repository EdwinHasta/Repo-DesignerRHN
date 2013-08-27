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
@Table(name = "SOAUSENTISMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Soausentismos.findAll", query = "SELECT s FROM Soausentismos s")})
public class Soausentismos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "DIAS")
    private BigInteger dias;
    @Column(name = "HORAS")
    private BigInteger horas;
    @Size(max = 15)
    @Column(name = "CLASEAUSENTISMO")
    private String claseausentismo;
    @Size(max = 1)
    @Column(name = "RELACIONADA")
    private String relacionada;
    @Column(name = "FECHAINIPAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainipago;
    @Column(name = "FECHAFINPAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinpago;
    @Column(name = "FECHAFINAUS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinaus;
    @Column(name = "FECHAEXPEDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaexpedicion;
    @Size(max = 50)
    @Column(name = "NUMEROCERTIFICADO")
    private String numerocertificado;
    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Size(max = 60)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 50)
    @Column(name = "FORMALIQUIDACION")
    private String formaliquidacion;
    @Column(name = "PORCENTAJEINDIVIDUAL")
    private BigInteger porcentajeindividual;
    @Size(max = 30)
    @Column(name = "USUARIOBD")
    private String usuariobd;
    @Column(name = "BASELIQUIDACION")
    private BigInteger baseliquidacion;
    @JoinColumn(name = "TIPO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Tiposausentismos tipo;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Terceros tercero;
    @OneToMany(mappedBy = "prorroga")
    private Collection<Soausentismos> soausentismosCollection;
    @JoinColumn(name = "PRORROGA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Soausentismos prorroga;
    @JoinColumn(name = "ACCIDENTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Soaccidentes accidente;
    @JoinColumn(name = "ENFERMEDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Enfermedades enfermedad;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;
    @JoinColumn(name = "DIAGNOSTICOCATEGORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Diagnosticoscategorias diagnosticocategoria;
    @JoinColumn(name = "CLASE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Clasesausentismos clase;
    @JoinColumn(name = "CAUSA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Causasausentismos causa;
    @OneToMany(mappedBy = "soausentismo")
    private Collection<NovedadesSistema> novedadessistemaCollection;

    public Soausentismos() {
    }

    public Soausentismos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Soausentismos(BigDecimal secuencia, Date fecha) {
        this.secuencia = secuencia;
        this.fecha = fecha;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getDias() {
        return dias;
    }

    public void setDias(BigInteger dias) {
        this.dias = dias;
    }

    public BigInteger getHoras() {
        return horas;
    }

    public void setHoras(BigInteger horas) {
        this.horas = horas;
    }

    public String getClaseausentismo() {
        return claseausentismo;
    }

    public void setClaseausentismo(String claseausentismo) {
        this.claseausentismo = claseausentismo;
    }

    public String getRelacionada() {
        return relacionada;
    }

    public void setRelacionada(String relacionada) {
        this.relacionada = relacionada;
    }

    public Date getFechainipago() {
        return fechainipago;
    }

    public void setFechainipago(Date fechainipago) {
        this.fechainipago = fechainipago;
    }

    public Date getFechafinpago() {
        return fechafinpago;
    }

    public void setFechafinpago(Date fechafinpago) {
        this.fechafinpago = fechafinpago;
    }

    public Date getFechafinaus() {
        return fechafinaus;
    }

    public void setFechafinaus(Date fechafinaus) {
        this.fechafinaus = fechafinaus;
    }

    public Date getFechaexpedicion() {
        return fechaexpedicion;
    }

    public void setFechaexpedicion(Date fechaexpedicion) {
        this.fechaexpedicion = fechaexpedicion;
    }

    public String getNumerocertificado() {
        return numerocertificado;
    }

    public void setNumerocertificado(String numerocertificado) {
        this.numerocertificado = numerocertificado;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFormaliquidacion() {
        return formaliquidacion;
    }

    public void setFormaliquidacion(String formaliquidacion) {
        this.formaliquidacion = formaliquidacion;
    }

    public BigInteger getPorcentajeindividual() {
        return porcentajeindividual;
    }

    public void setPorcentajeindividual(BigInteger porcentajeindividual) {
        this.porcentajeindividual = porcentajeindividual;
    }

    public String getUsuariobd() {
        return usuariobd;
    }

    public void setUsuariobd(String usuariobd) {
        this.usuariobd = usuariobd;
    }

    public BigInteger getBaseliquidacion() {
        return baseliquidacion;
    }

    public void setBaseliquidacion(BigInteger baseliquidacion) {
        this.baseliquidacion = baseliquidacion;
    }

    public Tiposausentismos getTipo() {
        return tipo;
    }

    public void setTipo(Tiposausentismos tipo) {
        this.tipo = tipo;
    }

    public Terceros getTercero() {
        return tercero;
    }

    public void setTercero(Terceros tercero) {
        this.tercero = tercero;
    }

    @XmlTransient
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
    }

    public Soausentismos getProrroga() {
        return prorroga;
    }

    public void setProrroga(Soausentismos prorroga) {
        this.prorroga = prorroga;
    }

    public Soaccidentes getAccidente() {
        return accidente;
    }

    public void setAccidente(Soaccidentes accidente) {
        this.accidente = accidente;
    }

    public Enfermedades getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedades enfermedad) {
        this.enfermedad = enfermedad;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Diagnosticoscategorias getDiagnosticocategoria() {
        return diagnosticocategoria;
    }

    public void setDiagnosticocategoria(Diagnosticoscategorias diagnosticocategoria) {
        this.diagnosticocategoria = diagnosticocategoria;
    }

    public Clasesausentismos getClase() {
        return clase;
    }

    public void setClase(Clasesausentismos clase) {
        this.clase = clase;
    }

    public Causasausentismos getCausa() {
        return causa;
    }

    public void setCausa(Causasausentismos causa) {
        this.causa = causa;
    }

    @XmlTransient
    public Collection<NovedadesSistema> getNovedadessistemaCollection() {
        return novedadessistemaCollection;
    }

    public void setNovedadessistemaCollection(Collection<NovedadesSistema> novedadessistemaCollection) {
        this.novedadessistemaCollection = novedadessistemaCollection;
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
        if (!(object instanceof Soausentismos)) {
            return false;
        }
        Soausentismos other = (Soausentismos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Soausentismos[ secuencia=" + secuencia + " ]";
    }
    
}
