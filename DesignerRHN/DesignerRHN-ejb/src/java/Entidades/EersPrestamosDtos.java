/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author user
 */
@Entity
@Table(name = "EERSPRESTAMOSDTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EersPrestamosDtos.findAll", query = "SELECT e FROM EersPrestamosDtos e")})
public class EersPrestamosDtos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "VALOR")
    private BigInteger valor;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "SALDOINICIAL")
    private BigInteger saldoinicial;
    @Size(max = 20)
    @Column(name = "TIPONOVEDAD")
    private String tiponovedad;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @JoinColumn(name = "PERIODICIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Periodicidades periodicidad;
    @OneToMany(mappedBy = "abonoeerprestamodto")
    private Collection<EersPrestamosDtos> eersPrestamosDtosCollection;
    @JoinColumn(name = "ABONOEERPRESTAMODTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EersPrestamosDtos abonoeerprestamodto;
    @JoinColumn(name = "EERPRESTAMO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private EersPrestamos eerprestamo;
    @JoinColumn(name = "DETALLEFORMADTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private DetallesFormasDtos detalleformadto;

    public EersPrestamosDtos() {
    }

    public EersPrestamosDtos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigInteger getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(BigInteger saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public String getTiponovedad() {
        return tiponovedad;
    }

    public void setTiponovedad(String tiponovedad) {
        this.tiponovedad = tiponovedad;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Periodicidades getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidades periodicidad) {
        this.periodicidad = periodicidad;
    }

    @XmlTransient
    public Collection<EersPrestamosDtos> getEersPrestamosDtosCollection() {
        return eersPrestamosDtosCollection;
    }

    public void setEersPrestamosDtosCollection(Collection<EersPrestamosDtos> eersPrestamosDtosCollection) {
        this.eersPrestamosDtosCollection = eersPrestamosDtosCollection;
    }

    public EersPrestamosDtos getAbonoeerprestamodto() {
        return abonoeerprestamodto;
    }

    public void setAbonoeerprestamodto(EersPrestamosDtos abonoeerprestamodto) {
        this.abonoeerprestamodto = abonoeerprestamodto;
    }

    public EersPrestamos getEerprestamo() {
        return eerprestamo;
    }

    public void setEerprestamo(EersPrestamos eerprestamo) {
        this.eerprestamo = eerprestamo;
    }

    public DetallesFormasDtos getDetalleformadto() {
        return detalleformadto;
    }

    public void setDetalleformadto(DetallesFormasDtos detalleformadto) {
        this.detalleformadto = detalleformadto;
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
        if (!(object instanceof EersPrestamosDtos)) {
            return false;
        }
        EersPrestamosDtos other = (EersPrestamosDtos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EersPrestamosDtos[ secuencia=" + secuencia + " ]";
    }
    
}
