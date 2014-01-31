/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VACACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacaciones.findAll", query = "SELECT v FROM Vacaciones v")})
public class Vacaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FINALCAUSACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalcausacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INICIALCAUSACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicialcausacion;
    @Column(name = "DIASPENDIENTES")
    private BigDecimal diaspendientes;
    @Column(name = "DIASPENDIENTESPRECIERRE")
    private BigDecimal diaspendientesprecierre;
    @OneToMany(mappedBy = "vacacion")
    private Collection<NovedadesSistema> novedadessistemaCollection;
    @JoinColumn(name = "NOVEDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Novedades novedad;
    @Transient
    private String periodo;

    public Vacaciones() {
    }

    public Vacaciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Vacaciones(BigDecimal secuencia, String estado, Date finalcausacion, Date inicialcausacion) {
        this.secuencia = secuencia;
        this.estado = estado;
        this.finalcausacion = finalcausacion;
        this.inicialcausacion = inicialcausacion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFinalcausacion() {
        return finalcausacion;
    }

    public void setFinalcausacion(Date finalcausacion) {
        this.finalcausacion = finalcausacion;
    }

    public Date getInicialcausacion() {
        return inicialcausacion;
    }

    public void setInicialcausacion(Date inicialcausacion) {
        this.inicialcausacion = inicialcausacion;
    }

    public BigDecimal getDiaspendientes() {
        if (diaspendientes == null){
            diaspendientes = BigDecimal.valueOf(0);
        }
        return diaspendientes;
    }

    public void setDiaspendientes(BigDecimal diaspendientes) {
        this.diaspendientes = diaspendientes;
    }

    public BigDecimal getDiaspendientesprecierre() {
        return diaspendientesprecierre;
    }

    public void setDiaspendientesprecierre(BigDecimal diaspendientesprecierre) {
        this.diaspendientesprecierre = diaspendientesprecierre;
    }

    @XmlTransient
    public Collection<NovedadesSistema> getNovedadessistemaCollection() {
        return novedadessistemaCollection;
    }

    public void setNovedadessistemaCollection(Collection<NovedadesSistema> novedadessistemaCollection) {
        this.novedadessistemaCollection = novedadessistemaCollection;
    }

    public Novedades getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedades novedad) {
        this.novedad = novedad;
    }

    public String getPeriodo() {
        if (periodo == null) {
            periodo = " ";

            if (inicialcausacion != null && finalcausacion != null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                periodo = formato.format(inicialcausacion) + " -> " + formato.format(finalcausacion);
            } else {
                periodo = null;
            }
        }
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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
        if (!(object instanceof Vacaciones)) {
            return false;
        }
        Vacaciones other = (Vacaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vacaciones[ secuencia=" + secuencia + " ]";
    }

}
