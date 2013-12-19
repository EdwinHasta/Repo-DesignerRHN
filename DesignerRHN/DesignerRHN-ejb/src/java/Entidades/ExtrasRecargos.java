/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "EXTRASRECARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExtrasRecargos.findAll", query = "SELECT e FROM ExtrasRecargos e"),
    @NamedQuery(name = "ExtrasRecargos.findBySecuencia", query = "SELECT e FROM ExtrasRecargos e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "ExtrasRecargos.findByCodigo", query = "SELECT e FROM ExtrasRecargos e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "ExtrasRecargos.findByDescripcion", query = "SELECT e FROM ExtrasRecargos e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "ExtrasRecargos.findByTurno", query = "SELECT e FROM ExtrasRecargos e WHERE e.turno = :turno"),
    @NamedQuery(name = "ExtrasRecargos.findByAprobacionautomatica", query = "SELECT e FROM ExtrasRecargos e WHERE e.aprobacionautomatica = :aprobacionautomatica")})
public class ExtrasRecargos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "TURNO")
    private String turno;
    @Size(max = 1)
    @Column(name = "APROBACIONAUTOMATICA")
    private String aprobacionautomatica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "extrarecargo")
    private List<DetallesExtrasRecargos> detallesExtrasRecargosList;
    @JoinColumn(name = "TIPOJORNADA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposJornadas tipojornada;
    @JoinColumn(name = "TIPODIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposDias tipodia;
    @JoinColumn(name = "TIPOLEGISLACION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Contratos tipolegislacion;
    @Transient
    private boolean checkTurno;
    @Transient
    private boolean checkAprovacion;

    public ExtrasRecargos() {
    }

    public ExtrasRecargos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ExtrasRecargos(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTurno() {
        if (turno == null) {
            turno = "N";
        }
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getAprobacionautomatica() {
        if (aprobacionautomatica == null) {
            aprobacionautomatica = "N";
        }
        return aprobacionautomatica;
    }

    public void setAprobacionautomatica(String aprobacionautomatica) {
        this.aprobacionautomatica = aprobacionautomatica;
    }

    @XmlTransient
    public List<DetallesExtrasRecargos> getDetallesExtrasRecargosList() {
        return detallesExtrasRecargosList;
    }

    public void setDetallesExtrasRecargosList(List<DetallesExtrasRecargos> detallesExtrasRecargosList) {
        this.detallesExtrasRecargosList = detallesExtrasRecargosList;
    }

    public TiposJornadas getTipojornada() {
        return tipojornada;
    }

    public void setTipojornada(TiposJornadas tipojornada) {
        this.tipojornada = tipojornada;
    }

    public TiposDias getTipodia() {
        return tipodia;
    }

    public void setTipodia(TiposDias tipodia) {
        this.tipodia = tipodia;
    }

    public Contratos getTipolegislacion() {
        return tipolegislacion;
    }

    public void setTipolegislacion(Contratos tipolegislacion) {
        this.tipolegislacion = tipolegislacion;
    }

    public boolean isCheckTurno() {
        if (turno == null || turno.equalsIgnoreCase("N")) {
            checkTurno = false;
        } else {
            checkTurno = true;
        }
        return checkTurno;
    }

    public void setCheckTurno(boolean checkTurno) {
        if (checkTurno == false) {
            turno = "N";
        } else {
            turno = "S";
        }
        this.checkTurno = checkTurno;
    }

    public boolean isCheckAprovacion() {
        if (aprobacionautomatica == null || aprobacionautomatica.equalsIgnoreCase("N")) {
            checkAprovacion = false;
        } else {
            checkAprovacion = true;
        }
        return checkAprovacion;
    }

    public void setCheckAprovacion(boolean checkAprovacion) {
        if (checkTurno == false) {
            aprobacionautomatica = "N";
        } else {
            aprobacionautomatica = "S";
        }
        this.checkAprovacion = checkAprovacion;
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
        if (!(object instanceof ExtrasRecargos)) {
            return false;
        }
        ExtrasRecargos other = (ExtrasRecargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ExtrasRecargos[ secuencia=" + secuencia + " ]";
    }

}
