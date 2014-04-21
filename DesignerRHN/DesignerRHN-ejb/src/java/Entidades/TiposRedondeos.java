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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "TIPOSREDONDEOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposRedondeos.findAll", query = "SELECT t FROM TiposRedondeos t")})
public class TiposRedondeos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REDONDEO")
    private short redondeo;
    @Size(max = 10)
    @Column(name = "FORMAREDONDEO")
    private String formaredondeo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiporedondeo")
    private Collection<ConceptosRedondeos> conceptosRedondeosCollection;

    public TiposRedondeos() {
    }

    public TiposRedondeos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposRedondeos(BigInteger secuencia, String descripcion, short redondeo) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
        this.redondeo = redondeo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getRedondeo() {
        return redondeo;
    }

    public void setRedondeo(short redondeo) {
        this.redondeo = redondeo;
    }

    public String getFormaredondeo() {
        return formaredondeo;
    }

    public void setFormaredondeo(String formaredondeo) {
        this.formaredondeo = formaredondeo;
    }

    @XmlTransient
    public Collection<ConceptosRedondeos> getConceptosRedondeosCollection() {
        return conceptosRedondeosCollection;
    }

    public void setConceptosRedondeosCollection(Collection<ConceptosRedondeos> conceptosRedondeosCollection) {
        this.conceptosRedondeosCollection = conceptosRedondeosCollection;
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
        if (!(object instanceof TiposRedondeos)) {
            return false;
        }
        TiposRedondeos other = (TiposRedondeos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposRedondeos[ secuencia=" + secuencia + " ]";
    }
    
}
