/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author user
 */
@Entity
@Table(name = "INFOREPORTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inforeportes.findAll", query = "SELECT i FROM Inforeportes i"),
    @NamedQuery(name = "Inforeportes.findBySecuencia", query = "SELECT i FROM Inforeportes i WHERE i.secuencia = :secuencia"),
    @NamedQuery(name = "Inforeportes.findByCodigo", query = "SELECT i FROM Inforeportes i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "Inforeportes.findByNombre", query = "SELECT i FROM Inforeportes i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Inforeportes.findByTipo", query = "SELECT i FROM Inforeportes i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Inforeportes.findByNombrereporte", query = "SELECT i FROM Inforeportes i WHERE i.nombrereporte = :nombrereporte"),
    @NamedQuery(name = "Inforeportes.findByFecdesde", query = "SELECT i FROM Inforeportes i WHERE i.fecdesde = :fecdesde"),
    @NamedQuery(name = "Inforeportes.findByFechasta", query = "SELECT i FROM Inforeportes i WHERE i.fechasta = :fechasta"),
    @NamedQuery(name = "Inforeportes.findByEmdesde", query = "SELECT i FROM Inforeportes i WHERE i.emdesde = :emdesde"),
    @NamedQuery(name = "Inforeportes.findByEmhasta", query = "SELECT i FROM Inforeportes i WHERE i.emhasta = :emhasta"),
    @NamedQuery(name = "Inforeportes.findByLocalizacion", query = "SELECT i FROM Inforeportes i WHERE i.localizacion = :localizacion"),
    @NamedQuery(name = "Inforeportes.findByEstado", query = "SELECT i FROM Inforeportes i WHERE i.estado = :estado"),
    @NamedQuery(name = "Inforeportes.findByGrupo", query = "SELECT i FROM Inforeportes i WHERE i.grupo = :grupo"),
    @NamedQuery(name = "Inforeportes.findByTercero", query = "SELECT i FROM Inforeportes i WHERE i.tercero = :tercero"),
    @NamedQuery(name = "Inforeportes.findByTrabajador", query = "SELECT i FROM Inforeportes i WHERE i.trabajador = :trabajador"),
    @NamedQuery(name = "Inforeportes.findByTipotrabajador", query = "SELECT i FROM Inforeportes i WHERE i.tipotrabajador = :tipotrabajador"),
    @NamedQuery(name = "Inforeportes.findBySolicitud", query = "SELECT i FROM Inforeportes i WHERE i.solicitud = :solicitud"),
    @NamedQuery(name = "Inforeportes.findByCiudad", query = "SELECT i FROM Inforeportes i WHERE i.ciudad = :ciudad"),
    @NamedQuery(name = "Inforeportes.findByEstadocivil", query = "SELECT i FROM Inforeportes i WHERE i.estadocivil = :estadocivil"),
    @NamedQuery(name = "Inforeportes.findByTipotelefono", query = "SELECT i FROM Inforeportes i WHERE i.tipotelefono = :tipotelefono"),
    @NamedQuery(name = "Inforeportes.findByDeporte", query = "SELECT i FROM Inforeportes i WHERE i.deporte = :deporte"),
    @NamedQuery(name = "Inforeportes.findByIdioma", query = "SELECT i FROM Inforeportes i WHERE i.idioma = :idioma"),
    @NamedQuery(name = "Inforeportes.findByAficion", query = "SELECT i FROM Inforeportes i WHERE i.aficion = :aficion"),
    @NamedQuery(name = "Inforeportes.findByJefedivision", query = "SELECT i FROM Inforeportes i WHERE i.jefedivision = :jefedivision"),
    @NamedQuery(name = "Inforeportes.findByRodamiento", query = "SELECT i FROM Inforeportes i WHERE i.rodamiento = :rodamiento"),
    @NamedQuery(name = "Inforeportes.findByEnviomasivo", query = "SELECT i FROM Inforeportes i WHERE i.enviomasivo = :enviomasivo"),
    @NamedQuery(name = "Inforeportes.findByContador", query = "SELECT i FROM Inforeportes i WHERE i.contador = :contador"),
    @NamedQuery(name = "Inforeportes.findByParametros", query = "SELECT i FROM Inforeportes i WHERE i.parametros = :parametros"),
    @NamedQuery(name = "Inforeportes.findByAyuda", query = "SELECT i FROM Inforeportes i WHERE i.ayuda = :ayuda"),
    @NamedQuery(name = "Inforeportes.findByCodigomemo", query = "SELECT i FROM Inforeportes i WHERE i.codigomemo = :codigomemo")})
public class Inforeportes implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inforeporte")
    private Collection<UsuariosInforeportes> usuariosinforeportesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 15)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 60)
    @Column(name = "NOMBREREPORTE")
    private String nombrereporte;
    @Size(max = 2)
    @Column(name = "FECDESDE")
    private String fecdesde;
    @Size(max = 2)
    @Column(name = "FECHASTA")
    private String fechasta;
    @Size(max = 2)
    @Column(name = "EMDESDE")
    private String emdesde;
    @Size(max = 2)
    @Column(name = "EMHASTA")
    private String emhasta;
    @Size(max = 2)
    @Column(name = "LOCALIZACION")
    private String localizacion;
    @Size(max = 2)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 2)
    @Column(name = "GRUPO")
    private String grupo;
    @Size(max = 2)
    @Column(name = "TERCERO")
    private String tercero;
    @Size(max = 2)
    @Column(name = "TRABAJADOR")
    private String trabajador;
    @Size(max = 2)
    @Column(name = "TIPOTRABAJADOR")
    private String tipotrabajador;
    @Size(max = 2)
    @Column(name = "SOLICITUD")
    private String solicitud;
    @Size(max = 2)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 2)
    @Column(name = "ESTADOCIVIL")
    private String estadocivil;
    @Size(max = 2)
    @Column(name = "TIPOTELEFONO")
    private String tipotelefono;
    @Size(max = 2)
    @Column(name = "DEPORTE")
    private String deporte;
    @Size(max = 2)
    @Column(name = "IDIOMA")
    private String idioma;
    @Size(max = 2)
    @Column(name = "AFICION")
    private String aficion;
    @Size(max = 2)
    @Column(name = "JEFEDIVISION")
    private String jefedivision;
    @Size(max = 2)
    @Column(name = "RODAMIENTO")
    private String rodamiento;
    @Size(max = 1)
    @Column(name = "ENVIOMASIVO")
    private String enviomasivo;
    @Column(name = "CONTADOR")
    private BigInteger contador;
    @Size(max = 1000)
    @Column(name = "PARAMETROS")
    private String parametros;
    @Size(max = 3000)
    @Column(name = "AYUDA")
    private String ayuda;
    @Size(max = 10)
    @Column(name = "CODIGOMEMO")
    private String codigomemo;
    @JoinColumn(name = "MODULO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Modulos modulo;

    public Inforeportes() {
    }

    public Inforeportes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombrereporte() {
        return nombrereporte;
    }

    public void setNombrereporte(String nombrereporte) {
        this.nombrereporte = nombrereporte;
    }

    public String getFecdesde() {
        return fecdesde;
    }

    public void setFecdesde(String fecdesde) {
        this.fecdesde = fecdesde;
    }

    public String getFechasta() {
        return fechasta;
    }

    public void setFechasta(String fechasta) {
        this.fechasta = fechasta;
    }

    public String getEmdesde() {
        return emdesde;
    }

    public void setEmdesde(String emdesde) {
        this.emdesde = emdesde;
    }

    public String getEmhasta() {
        return emhasta;
    }

    public void setEmhasta(String emhasta) {
        this.emhasta = emhasta;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTercero() {
        return tercero;
    }

    public void setTercero(String tercero) {
        this.tercero = tercero;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(String tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getTipotelefono() {
        return tipotelefono;
    }

    public void setTipotelefono(String tipotelefono) {
        this.tipotelefono = tipotelefono;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAficion() {
        return aficion;
    }

    public void setAficion(String aficion) {
        this.aficion = aficion;
    }

    public String getJefedivision() {
        return jefedivision;
    }

    public void setJefedivision(String jefedivision) {
        this.jefedivision = jefedivision;
    }

    public String getRodamiento() {
        return rodamiento;
    }

    public void setRodamiento(String rodamiento) {
        this.rodamiento = rodamiento;
    }

    public String getEnviomasivo() {
        return enviomasivo;
    }

    public void setEnviomasivo(String enviomasivo) {
        this.enviomasivo = enviomasivo;
    }

    public BigInteger getContador() {
        return contador;
    }

    public void setContador(BigInteger contador) {
        this.contador = contador;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getCodigomemo() {
        return codigomemo;
    }

    public void setCodigomemo(String codigomemo) {
        this.codigomemo = codigomemo;
    }

    public Modulos getModulo() {
        return modulo;
    }

    public void setModulo(Modulos modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Inforeportes)) {
            return false;
        }
        Inforeportes other = (Inforeportes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Inforeportes[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<UsuariosInforeportes> getUsuariosinforeportesCollection() {
        return usuariosinforeportesCollection;
    }

    public void setUsuariosinforeportesCollection(Collection<UsuariosInforeportes> usuariosinforeportesCollection) {
        this.usuariosinforeportesCollection = usuariosinforeportesCollection;
    }
    
}
