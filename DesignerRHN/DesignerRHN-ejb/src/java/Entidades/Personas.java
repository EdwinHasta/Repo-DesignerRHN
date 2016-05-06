package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PERSONAS")
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 1)
    @Column(name = "FACTORRH")
    private String factorrh;
    @Column(name = "FECHANACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Column(name = "FECHAVENCIMIENTOCERTIFICADO")
    @Temporal(TemporalType.DATE)
    private Date fechavencimientocertificado;
    @Column(name = "FECHAFALLECIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechafallecimiento;
    @Size(max = 2)
    @Column(name = "GRUPOSANGUINEO")
    private String gruposanguineo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "PRIMERAPELLIDO")
    private String primerapellido;
    @Size(max = 20)
    @Column(name = "SEGUNDOAPELLIDO")
    private String segundoapellido;
    @Size(max = 1)
    @Column(name = "SEXO")
    private String sexo;
    @Size(max = 1)
    @Column(name = "VIVIENDAPROPIA")
    private String viviendapropia;
    @Column(name = "CLASELIBRETAMILITAR")
    private Short claselibretamilitar;
    @Column(name = "NUMEROLIBRETAMILITAR")
    private Long numerolibretamilitar;
    @Column(name = "DISTRITOMILITAR")
    private Short distritomilitar;
    @Size(max = 15)
    @Column(name = "CERTIFICADOJUDICIAL")
    private String certificadojudicial;
    @Size(max = 100)
    @Column(name = "PATHFOTO")
    private String pathfoto;
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 10)
    @Column(name = "PLACAVEHICULO")
    private String placavehiculo;
    @Size(max = 20)
    @Column(name = "NUMEROMATRICULAPROF")
    private String numeromatriculaprof;
    @Column(name = "FECHAEXPMATRICULA")
    @Temporal(TemporalType.DATE)
    private Date fechaexpmatricula;
    @Column(name = "DIGITOVERIFICACIONDOCUMENTO")
    private Short digitoverificaciondocumento;
    @Size(max = 50)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 1)
    @Column(name = "VEHICULOEMPRESA")
    private String vehiculoempresa;
    @Size(max = 30)
    @Column(name = "SEGUNDONOMBRE")
    private String segundonombre;
    @Column(name = "NUMERODOCUMENTO")
    private BigInteger numerodocumento;
    @JoinColumn(name = "TIPODOCUMENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposDocumentos tipodocumento;
    @JoinColumn(name = "CIUDADDOCUMENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Ciudades ciudaddocumento;
    @JoinColumn(name = "CIUDADNACIMIENTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Ciudades ciudadnacimiento;
    @Transient
    private String nombreCompleto;
    @Transient
    private int edad;
    @Transient
    private String strNumeroDocumento;

    public Personas() {
    }

    public Personas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Personas(BigInteger secuencia, String nombre, BigInteger numerodocumento, String primerapellido) {
        this.secuencia = secuencia;
        this.nombre = nombre;
        this.numerodocumento = numerodocumento;
        this.primerapellido = primerapellido;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getFactorrh() {
        if (factorrh == null) {
            factorrh = "";
        }
        return factorrh;
    }

    public void setFactorrh(String factorrh) {
        this.factorrh = factorrh;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        System.out.println("setFechanacimiento fechanacimiento: " + fechanacimiento);
        this.fechanacimiento = fechanacimiento;
    }

    public Date getFechavencimientocertificado() {
        return fechavencimientocertificado;
    }

    public void setFechavencimientocertificado(Date fechavencimientocertificado) {
        this.fechavencimientocertificado = fechavencimientocertificado;
    }

    public Date getFechafallecimiento() {
        return fechafallecimiento;
    }

    public void setFechafallecimiento(Date fechafallecimiento) {
        this.fechafallecimiento = fechafallecimiento;
    }

    public String getGruposanguineo() {
        if (gruposanguineo == null) {
            gruposanguineo = "";
        }
        return gruposanguineo;
    }

    public void setGruposanguineo(String gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getNombre() {
        if (nombre == null) {
            return "";
        } else {
            return nombre;
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getPrimerapellido() {
        if (primerapellido == null) {
            return "";
        } else {
            return primerapellido;
        }
    }

    public void setPrimerapellido(String primerapellido) {
        System.out.println("setPrimerapellido primerapellido : " + primerapellido);
        this.primerapellido = primerapellido.toUpperCase();
    }

    public String getSegundoapellido() {
        if (segundoapellido == null) {
            return "";
        } else {
            return segundoapellido;
        }
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido.toUpperCase();
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getViviendapropia() {
        if (viviendapropia == null) {
            viviendapropia = "";
        }
        return viviendapropia;
    }

    public void setViviendapropia(String viviendapropia) {
        this.viviendapropia = viviendapropia;
    }

    public Short getClaselibretamilitar() {
        return claselibretamilitar;
    }

    public void setClaselibretamilitar(Short claselibretamilitar) {
        this.claselibretamilitar = claselibretamilitar;
    }

    public Long getNumerolibretamilitar() {
        return numerolibretamilitar;
    }

    public void setNumerolibretamilitar(Long numerolibretamilitar) {
        this.numerolibretamilitar = numerolibretamilitar;
    }

    public Short getDistritomilitar() {
        return distritomilitar;
    }

    public void setDistritomilitar(Short distritomilitar) {
        this.distritomilitar = distritomilitar;
    }

    public String getCertificadojudicial() {
        return certificadojudicial;
    }

    public void setCertificadojudicial(String certificadojudicial) {
        this.certificadojudicial = certificadojudicial;
    }

    public String getPathfoto() {
        return pathfoto;
    }

    public void setPathfoto(String pathfoto) {
        this.pathfoto = pathfoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlacavehiculo() {
        return placavehiculo;
    }

    public void setPlacavehiculo(String placavehiculo) {
        this.placavehiculo = placavehiculo;
    }

    public String getNumeromatriculaprof() {
        return numeromatriculaprof;
    }

    public void setNumeromatriculaprof(String numeromatriculaprof) {
        this.numeromatriculaprof = numeromatriculaprof.toUpperCase();
    }

    public Date getFechaexpmatricula() {
        return fechaexpmatricula;
    }

    public void setFechaexpmatricula(Date fechaexpmatricula) {
        this.fechaexpmatricula = fechaexpmatricula;
    }

    public Short getDigitoverificaciondocumento() {
        return digitoverificaciondocumento;
    }

    public void setDigitoverificaciondocumento(Short digitoverificaciondocumento) {
        this.digitoverificaciondocumento = digitoverificaciondocumento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.toUpperCase();
    }

    public String getVehiculoempresa() {
        if (vehiculoempresa == null) {
            vehiculoempresa = "";
        }
        return vehiculoempresa;
    }

    public void setVehiculoempresa(String vehiculoempresa) {
        this.vehiculoempresa = vehiculoempresa;
    }

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre.toUpperCase();
    }

    public TiposDocumentos getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(TiposDocumentos tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Ciudades getCiudaddocumento() {
        return ciudaddocumento;
    }

    public void setCiudaddocumento(Ciudades ciudaddocumento) {
        this.ciudaddocumento = ciudaddocumento;
    }

    public Ciudades getCiudadnacimiento() {
        return ciudadnacimiento;
    }

    public void setCiudadnacimiento(Ciudades ciudadnacimiento) {
        this.ciudadnacimiento = ciudadnacimiento;
    }

    public String getStrNumeroDocumento() {
        if (strNumeroDocumento == null) {
            strNumeroDocumento = "";
        }
        return strNumeroDocumento;
    }

    public void setStrNumeroDocumento(String strNumeroDocumento) {
        this.strNumeroDocumento = strNumeroDocumento;
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Personas[ secuencia=" + secuencia + " ]";
    }

    public String getNombreCompleto() {
        if (nombreCompleto == null) {
            nombreCompleto = getPrimerapellido() + " " + getSegundoapellido() + " " + getNombre();
            if (nombreCompleto.equals("  ")) {
                nombreCompleto = null;
            }
            return nombreCompleto;
        } else {
            return nombreCompleto;
        }
    }

    public String getNombreCompletoOrden2() {
        if (nombreCompleto == null) {
            nombreCompleto = getNombre() + " " + getPrimerapellido() + " " + getSegundoapellido();
            if (nombreCompleto.equals("  ")) {
                nombreCompleto = null;
            }
            return nombreCompleto;
        } else {
            return nombreCompleto;
        }
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto != null) {
            this.nombreCompleto = nombreCompleto.toUpperCase();
        } else {
            this.nombreCompleto = nombreCompleto;
        }
    }

    public int getEdad() {
        if (fechanacimiento != null) {
            Date fechaHoy = new Date();
            edad = fechaHoy.getYear() - fechanacimiento.getYear();
            if ((fechanacimiento.getMonth() - fechaHoy.getMonth()) > 0) {
                edad--;
            } else if ((fechanacimiento.getMonth() - fechaHoy.getMonth()) == 0) {
                if ((fechanacimiento.getDate() - fechaHoy.getDate()) > 0) {
                    edad--;
                }
            }
        }
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public BigInteger getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(BigInteger numerodocumento) {
        this.numerodocumento = numerodocumento;
    }
}
