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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "TURNOSROTATIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turnosrotativos.findAll", query = "SELECT t FROM Turnosrotativos t"),
    @NamedQuery(name = "Turnosrotativos.findBySecuencia", query = "SELECT t FROM Turnosrotativos t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "Turnosrotativos.findByCodigo", query = "SELECT t FROM Turnosrotativos t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Turnosrotativos.findByDescripcion", query = "SELECT t FROM Turnosrotativos t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Turnosrotativos.findByFechasemilla", query = "SELECT t FROM Turnosrotativos t WHERE t.fechasemilla = :fechasemilla"),
    @NamedQuery(name = "Turnosrotativos.findByHorainicial", query = "SELECT t FROM Turnosrotativos t WHERE t.horainicial = :horainicial"),
    @NamedQuery(name = "Turnosrotativos.findByHorafinal", query = "SELECT t FROM Turnosrotativos t WHERE t.horafinal = :horafinal"),
    @NamedQuery(name = "Turnosrotativos.findByMinutoinicial", query = "SELECT t FROM Turnosrotativos t WHERE t.minutoinicial = :minutoinicial"),
    @NamedQuery(name = "Turnosrotativos.findByMinutofinal", query = "SELECT t FROM Turnosrotativos t WHERE t.minutofinal = :minutofinal"),
    @NamedQuery(name = "Turnosrotativos.findByFinsemilla", query = "SELECT t FROM Turnosrotativos t WHERE t.finsemilla = :finsemilla")})
public class Turnosrotativos implements Serializable {
    @OneToMany(mappedBy = "turnorotativo")
    private Collection<TurnosEmpleados> turnosEmpleadosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnorotativo")
    private Collection<DetallesTurnosRotativos> detallesTurnosRotativosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "CODIGO")
    private short codigo;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "FECHASEMILLA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasemilla;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "HORAINICIAL")
    private short horainicial;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "HORAFINAL")
    private short horafinal;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "MINUTOINICIAL")
    private short minutoinicial;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "MINUTOFINAL")
    private short minutofinal;
    @Column(name = "FINSEMILLA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finsemilla;
    @JoinColumn(name = "CUADRILLA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cuadrillas cuadrilla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnorotativo")
    private Collection<Novedadesturnosrotativos> novedadesturnosrotativosCollection;

    public Turnosrotativos() {
    }

    public Turnosrotativos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Turnosrotativos(BigInteger secuencia, short codigo, String descripcion, Date fechasemilla, short horainicial, short horafinal, short minutoinicial, short minutofinal) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechasemilla = fechasemilla;
        this.horainicial = horainicial;
        this.horafinal = horafinal;
        this.minutoinicial = minutoinicial;
        this.minutofinal = minutofinal;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechasemilla() {
        return fechasemilla;
    }

    public void setFechasemilla(Date fechasemilla) {
        this.fechasemilla = fechasemilla;
    }

    public short getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(short horainicial) {
        this.horainicial = horainicial;
    }

    public short getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(short horafinal) {
        this.horafinal = horafinal;
    }

    public short getMinutoinicial() {
        return minutoinicial;
    }

    public void setMinutoinicial(short minutoinicial) {
        this.minutoinicial = minutoinicial;
    }

    public short getMinutofinal() {
        return minutofinal;
    }

    public void setMinutofinal(short minutofinal) {
        this.minutofinal = minutofinal;
    }

    public Date getFinsemilla() {
        return finsemilla;
    }

    public void setFinsemilla(Date finsemilla) {
        this.finsemilla = finsemilla;
    }

    public Cuadrillas getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrillas cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    @XmlTransient
    public Collection<Novedadesturnosrotativos> getNovedadesturnosrotativosCollection() {
        return novedadesturnosrotativosCollection;
    }

    public void setNovedadesturnosrotativosCollection(Collection<Novedadesturnosrotativos> novedadesturnosrotativosCollection) {
        this.novedadesturnosrotativosCollection = novedadesturnosrotativosCollection;
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
        if (!(object instanceof Turnosrotativos)) {
            return false;
        }
        Turnosrotativos other = (Turnosrotativos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Turnosrotativos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<DetallesTurnosRotativos> getDetallesTurnosRotativosCollection() {
        return detallesTurnosRotativosCollection;
    }

    public void setDetallesTurnosRotativosCollection(Collection<DetallesTurnosRotativos> detallesTurnosRotativosCollection) {
        this.detallesTurnosRotativosCollection = detallesTurnosRotativosCollection;
    }

    @XmlTransient
    public Collection<TurnosEmpleados> getTurnosEmpleadosCollection() {
        return turnosEmpleadosCollection;
    }

    public void setTurnosEmpleadosCollection(Collection<TurnosEmpleados> turnosEmpleadosCollection) {
        this.turnosEmpleadosCollection = turnosEmpleadosCollection;
    }
    
}
