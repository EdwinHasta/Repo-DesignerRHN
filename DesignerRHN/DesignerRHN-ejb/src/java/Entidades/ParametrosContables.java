/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "PARAMETROSCONTABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosContables.findAll", query = "SELECT p FROM ParametrosContables p"),
    @NamedQuery(name = "ParametrosContables.findBySecuencia", query = "SELECT p FROM ParametrosContables p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "ParametrosContables.findByEmpresaCodigo", query = "SELECT p FROM ParametrosContables p WHERE p.empresaCodigo = :empresaCodigo"),
    @NamedQuery(name = "ParametrosContables.findByMonedaCodigo", query = "SELECT p FROM ParametrosContables p WHERE p.monedaCodigo = :monedaCodigo"),
    @NamedQuery(name = "ParametrosContables.findByFechainicialcontabilizacion", query = "SELECT p FROM ParametrosContables p WHERE p.fechainicialcontabilizacion = :fechainicialcontabilizacion"),
    @NamedQuery(name = "ParametrosContables.findByFechafinalcontabilizacion", query = "SELECT p FROM ParametrosContables p WHERE p.fechafinalcontabilizacion = :fechafinalcontabilizacion"),
    @NamedQuery(name = "ParametrosContables.findByUsuario", query = "SELECT p FROM ParametrosContables p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "ParametrosContables.findByArchivo", query = "SELECT p FROM ParametrosContables p WHERE p.archivo = :archivo"),
    @NamedQuery(name = "ParametrosContables.findByRespuestaxml", query = "SELECT p FROM ParametrosContables p WHERE p.respuestaxml = :respuestaxml"),
    @NamedQuery(name = "ParametrosContables.findByFechageneracion", query = "SELECT p FROM ParametrosContables p WHERE p.fechageneracion = :fechageneracion"),
    @NamedQuery(name = "ParametrosContables.findByEstado", query = "SELECT p FROM ParametrosContables p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametrosContables.findByComentario", query = "SELECT p FROM ParametrosContables p WHERE p.comentario = :comentario"),
    @NamedQuery(name = "ParametrosContables.findByDocumentoContable", query = "SELECT p FROM ParametrosContables p WHERE p.documentoContable = :documentoContable"),
    @NamedQuery(name = "ParametrosContables.findByUltimonumerodocumento", query = "SELECT p FROM ParametrosContables p WHERE p.ultimonumerodocumento = :ultimonumerodocumento"),
    @NamedQuery(name = "ParametrosContables.findByCodigoempleadodesde", query = "SELECT p FROM ParametrosContables p WHERE p.codigoempleadodesde = :codigoempleadodesde"),
    @NamedQuery(name = "ParametrosContables.findByCodigoempleadohasta", query = "SELECT p FROM ParametrosContables p WHERE p.codigoempleadohasta = :codigoempleadohasta"),
    @NamedQuery(name = "ParametrosContables.findByDocumentoalternativo", query = "SELECT p FROM ParametrosContables p WHERE p.documentoalternativo = :documentoalternativo"),
    @NamedQuery(name = "ParametrosContables.findByDestino", query = "SELECT p FROM ParametrosContables p WHERE p.destino = :destino"),
    @NamedQuery(name = "ParametrosContables.findByChequera", query = "SELECT p FROM ParametrosContables p WHERE p.chequera = :chequera"),
    @NamedQuery(name = "ParametrosContables.findByFechacontabilizacion", query = "SELECT p FROM ParametrosContables p WHERE p.fechacontabilizacion = :fechacontabilizacion")})
public class ParametrosContables implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPRESA_CODIGO")
    private short empresaCodigo;
    @Column(name = "MONEDA_CODIGO")
    private Short monedaCodigo;
    @Column(name = "FECHAINICIALCONTABILIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicialcontabilizacion;
    @Column(name = "FECHAFINALCONTABILIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinalcontabilizacion;
    @Size(max = 30)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ARCHIVO")
    private String archivo;
    @Size(max = 4000)
    @Column(name = "RESPUESTAXML")
    private String respuestaxml;
    @Column(name = "FECHAGENERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechageneracion;
    @Size(max = 15)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 200)
    @Column(name = "COMENTARIO")
    private String comentario;
    @Size(max = 4)
    @Column(name = "DOCUMENTO_CONTABLE")
    private String documentoContable;
    @Column(name = "ULTIMONUMERODOCUMENTO")
    private BigInteger ultimonumerodocumento;
    @Column(name = "CODIGOEMPLEADODESDE")
    private BigInteger codigoempleadodesde;
    @Column(name = "CODIGOEMPLEADOHASTA")
    private BigInteger codigoempleadohasta;
    @Size(max = 10)
    @Column(name = "DOCUMENTOALTERNATIVO")
    private String documentoalternativo;
    @Size(max = 10)
    @Column(name = "DESTINO")
    private String destino;
    @Size(max = 20)
    @Column(name = "CHEQUERA")
    private String chequera;
    @Column(name = "FECHACONTABILIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacontabilizacion;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Procesos proceso;
    @Transient
    private Empresas empresaRegistro;

    public ParametrosContables() {
    }

    public ParametrosContables(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ParametrosContables(BigInteger secuencia, short empresaCodigo, String archivo) {
        this.secuencia = secuencia;
        this.empresaCodigo = empresaCodigo;
        this.archivo = archivo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getEmpresaCodigo() {
        return empresaCodigo;
    }

    public void setEmpresaCodigo(short empresaCodigo) {
        this.empresaCodigo = empresaCodigo;
    }

    public Empresas getEmpresaRegistro() {
        return empresaRegistro;
    }

    public void setEmpresaRegistro(Empresas empresaRegistro) {
        this.empresaRegistro = empresaRegistro;
    }

    public Short getMonedaCodigo() {
        return monedaCodigo;
    }

    public void setMonedaCodigo(Short monedaCodigo) {
        this.monedaCodigo = monedaCodigo;
    }

    public Date getFechainicialcontabilizacion() {
        return fechainicialcontabilizacion;
    }

    public void setFechainicialcontabilizacion(Date fechainicialcontabilizacion) {
        this.fechainicialcontabilizacion = fechainicialcontabilizacion;
    }

    public Date getFechafinalcontabilizacion() {
        return fechafinalcontabilizacion;
    }

    public void setFechafinalcontabilizacion(Date fechafinalcontabilizacion) {
        this.fechafinalcontabilizacion = fechafinalcontabilizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getRespuestaxml() {
        return respuestaxml;
    }

    public void setRespuestaxml(String respuestaxml) {
        this.respuestaxml = respuestaxml;
    }

    public Date getFechageneracion() {
        return fechageneracion;
    }

    public void setFechageneracion(Date fechageneracion) {
        this.fechageneracion = fechageneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDocumentoContable() {
        return documentoContable;
    }

    public void setDocumentoContable(String documentoContable) {
        this.documentoContable = documentoContable;
    }

    public BigInteger getUltimonumerodocumento() {
        return ultimonumerodocumento;
    }

    public void setUltimonumerodocumento(BigInteger ultimonumerodocumento) {
        this.ultimonumerodocumento = ultimonumerodocumento;
    }

    public BigInteger getCodigoempleadodesde() {
        return codigoempleadodesde;
    }

    public void setCodigoempleadodesde(BigInteger codigoempleadodesde) {
        this.codigoempleadodesde = codigoempleadodesde;
    }

    public BigInteger getCodigoempleadohasta() {
        return codigoempleadohasta;
    }

    public void setCodigoempleadohasta(BigInteger codigoempleadohasta) {
        this.codigoempleadohasta = codigoempleadohasta;
    }

    public String getDocumentoalternativo() {
        return documentoalternativo;
    }

    public void setDocumentoalternativo(String documentoalternativo) {
        this.documentoalternativo = documentoalternativo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getChequera() {
        return chequera;
    }

    public void setChequera(String chequera) {
        this.chequera = chequera;
    }

    public Date getFechacontabilizacion() {
        return fechacontabilizacion;
    }

    public void setFechacontabilizacion(Date fechacontabilizacion) {
        this.fechacontabilizacion = fechacontabilizacion;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
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
        if (!(object instanceof ParametrosContables)) {
            return false;
        }
        ParametrosContables other = (ParametrosContables) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametrosContables[ secuencia=" + secuencia + " ]";
    }

}
