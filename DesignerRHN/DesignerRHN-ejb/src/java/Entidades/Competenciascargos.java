/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "COMPETENCIASCARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competenciascargos.findAll", query = "SELECT c FROM Competenciascargos c"),
    @NamedQuery(name = "Competenciascargos.findBySecuencia", query = "SELECT c FROM Competenciascargos c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "Competenciascargos.findByPeso", query = "SELECT c FROM Competenciascargos c WHERE c.peso = :peso")})
public class Competenciascargos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "PESO")
    private Integer peso;
    @JoinColumn(name = "EVALCOMPETENCIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Evalcompetencias evalcompetencia;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;
    @OneToMany(mappedBy = "competenciacargo")
    private Collection<Evalplanillas> evalplanillasCollection;

    public Competenciascargos() {
    }

    public Competenciascargos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Evalcompetencias getEvalcompetencia() {
        return evalcompetencia;
    }

    public void setEvalcompetencia(Evalcompetencias evalcompetencia) {
        this.evalcompetencia = evalcompetencia;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    @XmlTransient
    public Collection<Evalplanillas> getEvalplanillasCollection() {
        return evalplanillasCollection;
    }

    public void setEvalplanillasCollection(Collection<Evalplanillas> evalplanillasCollection) {
        this.evalplanillasCollection = evalplanillasCollection;
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
        if (!(object instanceof Competenciascargos)) {
            return false;
        }
        Competenciascargos other = (Competenciascargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Competenciascargos[ secuencia=" + secuencia + " ]";
    }
    
}
