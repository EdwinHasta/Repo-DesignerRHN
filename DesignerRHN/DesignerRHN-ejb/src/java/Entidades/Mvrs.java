/*
 * To change this template, choose Tools | Templates
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MVRS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mvrs.findAll", query = "SELECT m FROM Mvrs m"),
    @NamedQuery(name = "Mvrs.findBySecuencia", query = "SELECT m FROM Mvrs m WHERE m.secuencia = :secuencia"),
    @NamedQuery(name = "Mvrs.findByFechafinal", query = "SELECT m FROM Mvrs m WHERE m.fechafinal = :fechafinal"),
    @NamedQuery(name = "Mvrs.findByFechainicial", query = "SELECT m FROM Mvrs m WHERE m.fechainicial = :fechainicial"),
    @NamedQuery(name = "Mvrs.findByPorcentaje", query = "SELECT m FROM Mvrs m WHERE m.porcentaje = :porcentaje"),
    @NamedQuery(name = "Mvrs.findByValor", query = "SELECT m FROM Mvrs m WHERE m.valor = :valor"),
    @NamedQuery(name = "Mvrs.findByValoranualoriginal", query = "SELECT m FROM Mvrs m WHERE m.valoranualoriginal = :valoranualoriginal")})
public class Mvrs implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "VALORANUALORIGINAL")
    private BigInteger valoranualoriginal;
    @JoinColumn(name = "MOTIVO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Motivosmvrs motivo;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public Mvrs() {
    }

    public Mvrs(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Mvrs(BigInteger secuencia, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigInteger getValoranualoriginal() {
        return valoranualoriginal;
    }

    public void setValoranualoriginal(BigInteger valoranualoriginal) {
        this.valoranualoriginal = valoranualoriginal;
    }

    public Motivosmvrs getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivosmvrs motivo) {
        this.motivo = motivo;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
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
        if (!(object instanceof Mvrs)) {
            return false;
        }
        Mvrs other = (Mvrs) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Mvrs[ secuencia=" + secuencia + " ]";
    }
    
}
