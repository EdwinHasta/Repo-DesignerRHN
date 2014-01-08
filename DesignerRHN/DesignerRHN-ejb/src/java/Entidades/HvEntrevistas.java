/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
 * @author Administrator
 */
@Entity
@Table(name = "HVENTREVISTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HvEntrevistas.findAll", query = "SELECT h FROM HvEntrevistas h")})
public class HvEntrevistas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "PUNTAJE")
    private BigInteger puntaje;
    @JoinColumn(name = "HOJADEVIDA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private HVHojasDeVida hojadevida;

    public HvEntrevistas() {
    }

    public HvEntrevistas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public HvEntrevistas(BigInteger secuencia, Date fecha, String tipo) {
        this.secuencia = secuencia;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    public BigInteger getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigInteger puntaje) {
        this.puntaje = puntaje;
    }

    public HVHojasDeVida getHojadevida() {
        return hojadevida;
    }

    public void setHojadevida(HVHojasDeVida hojadevida) {
        this.hojadevida = hojadevida;
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
        if (!(object instanceof HvEntrevistas)) {
            return false;
        }
        HvEntrevistas other = (HvEntrevistas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HvEntrevistas[ secuencia=" + secuencia + " ]";
    }

}
