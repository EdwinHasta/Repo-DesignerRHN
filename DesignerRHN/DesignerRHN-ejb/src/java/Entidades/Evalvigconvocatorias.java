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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "EVALVIGCONVOCATORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evalvigconvocatorias.findAll", query = "SELECT e FROM Evalvigconvocatorias e"),
    @NamedQuery(name = "Evalvigconvocatorias.findBySecuencia", query = "SELECT e FROM Evalvigconvocatorias e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "Evalvigconvocatorias.findByCodigo", query = "SELECT e FROM Evalvigconvocatorias e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Evalvigconvocatorias.findByFechavigencia", query = "SELECT e FROM Evalvigconvocatorias e WHERE e.fechavigencia = :fechavigencia")})
public class Evalvigconvocatorias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @OneToMany(mappedBy = "evalvigconvocatoria")
    private Collection<Pdgpoliticas> pdgpoliticasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalvigconvocatoria")
    private Collection<Evalconvocatorias> evalconvocatoriasCollection;

    public Evalvigconvocatorias() {
    }

    public Evalvigconvocatorias(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Evalvigconvocatorias(BigDecimal secuencia, BigInteger codigo, Date fechavigencia) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.fechavigencia = fechavigencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    @XmlTransient
    public Collection<Pdgpoliticas> getPdgpoliticasCollection() {
        return pdgpoliticasCollection;
    }

    public void setPdgpoliticasCollection(Collection<Pdgpoliticas> pdgpoliticasCollection) {
        this.pdgpoliticasCollection = pdgpoliticasCollection;
    }

    @XmlTransient
    public Collection<Evalconvocatorias> getEvalconvocatoriasCollection() {
        return evalconvocatoriasCollection;
    }

    public void setEvalconvocatoriasCollection(Collection<Evalconvocatorias> evalconvocatoriasCollection) {
        this.evalconvocatoriasCollection = evalconvocatoriasCollection;
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
        if (!(object instanceof Evalvigconvocatorias)) {
            return false;
        }
        Evalvigconvocatorias other = (Evalvigconvocatorias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Evalvigconvocatorias[ secuencia=" + secuencia + " ]";
    }
    
}
