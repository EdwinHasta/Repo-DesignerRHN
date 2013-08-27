/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "RASTROSVALORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RastrosValores.findAll", query = "SELECT r FROM RastrosValores r")})
public class RastrosValores implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRECOLUMNA")
    private String nombrecolumna;
    @Size(max = 100)
    @Column(name = "VALORPREVIO")
    private String valorprevio;
    @Size(max = 100)
    @Column(name = "VALORPOSTERIOR")
    private String valorposterior;
    @JoinColumn(name = "RASTRO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Rastros rastro;

    public RastrosValores() {
    }

    public RastrosValores(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public RastrosValores(BigDecimal secuencia, String nombrecolumna) {
        this.secuencia = secuencia;
        this.nombrecolumna = nombrecolumna;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombrecolumna() {
        return nombrecolumna;
    }

    public void setNombrecolumna(String nombrecolumna) {
        this.nombrecolumna = nombrecolumna;
    }

    public String getValorprevio() {
        return valorprevio;
    }

    public void setValorprevio(String valorprevio) {
        this.valorprevio = valorprevio;
    }

    public String getValorposterior() {
        return valorposterior;
    }

    public void setValorposterior(String valorposterior) {
        this.valorposterior = valorposterior;
    }

    public Rastros getRastro() {
        return rastro;
    }

    public void setRastro(Rastros rastro) {
        this.rastro = rastro;
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
        if (!(object instanceof RastrosValores)) {
            return false;
        }
        RastrosValores other = (RastrosValores) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RastrosValores[ secuencia=" + secuencia + " ]";
    }
    
}
