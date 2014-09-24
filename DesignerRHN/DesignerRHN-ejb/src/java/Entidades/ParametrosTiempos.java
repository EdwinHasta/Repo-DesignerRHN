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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "PARAMETROSTIEMPOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosTiempos.findAll", query = "SELECT p FROM ParametrosTiempos p"),
    @NamedQuery(name = "ParametrosTiempos.findBySecuencia", query = "SELECT p FROM ParametrosTiempos p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "ParametrosTiempos.findByFechadesde", query = "SELECT p FROM ParametrosTiempos p WHERE p.fechadesde = :fechadesde"),
    @NamedQuery(name = "ParametrosTiempos.findByFechahasta", query = "SELECT p FROM ParametrosTiempos p WHERE p.fechahasta = :fechahasta"),
    @NamedQuery(name = "ParametrosTiempos.findByCodigoempleadodesde", query = "SELECT p FROM ParametrosTiempos p WHERE p.codigoempleadodesde = :codigoempleadodesde"),
    @NamedQuery(name = "ParametrosTiempos.findByCodigoempleadohasta", query = "SELECT p FROM ParametrosTiempos p WHERE p.codigoempleadohasta = :codigoempleadohasta"),
    @NamedQuery(name = "ParametrosTiempos.findByFechacontrolnovedad", query = "SELECT p FROM ParametrosTiempos p WHERE p.fechacontrolnovedad = :fechacontrolnovedad"),
    @NamedQuery(name = "ParametrosTiempos.findByFormaliquidacion", query = "SELECT p FROM ParametrosTiempos p WHERE p.formaliquidacion = :formaliquidacion"),
    @NamedQuery(name = "ParametrosTiempos.findByUsuariobd", query = "SELECT p FROM ParametrosTiempos p WHERE p.usuariobd = :usuariobd")})
public class ParametrosTiempos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahasta;
    @Column(name = "CODIGOEMPLEADODESDE")
    private BigInteger codigoempleadodesde;
    @Column(name = "CODIGOEMPLEADOHASTA")
    private BigInteger codigoempleadohasta;
    @Column(name = "FECHACONTROLNOVEDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacontrolnovedad;
    @Size(max = 2)
    @Column(name = "FORMALIQUIDACION")
    private String formaliquidacion;
    @Size(max = 30)
    @Column(name = "USUARIOBD")
    private String usuariobd;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Estructuras estructura;
    @JoinColumn(name = "CUADRILLA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cuadrillas cuadrilla;

    public ParametrosTiempos() {
    }

    public ParametrosTiempos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ParametrosTiempos(BigInteger secuencia, Date fechadesde, Date fechahasta) {
        this.secuencia = secuencia;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
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

    public Date getFechacontrolnovedad() {
        return fechacontrolnovedad;
    }

    public void setFechacontrolnovedad(Date fechacontrolnovedad) {
        this.fechacontrolnovedad = fechacontrolnovedad;
    }

    public String getFormaliquidacion() {
        return formaliquidacion;
    }

    public void setFormaliquidacion(String formaliquidacion) {
        this.formaliquidacion = formaliquidacion;
    }

    public String getUsuariobd() {
        return usuariobd;
    }

    public void setUsuariobd(String usuariobd) {
        this.usuariobd = usuariobd;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Cuadrillas getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrillas cuadrilla) {
        this.cuadrilla = cuadrilla;
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
        if (!(object instanceof ParametrosTiempos)) {
            return false;
        }
        ParametrosTiempos other = (ParametrosTiempos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametrosTiempos[ secuencia=" + secuencia + " ]";
    }
    
}
