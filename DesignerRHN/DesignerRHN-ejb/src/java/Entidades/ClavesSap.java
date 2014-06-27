/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
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
 * @author Administrator
 */
@Entity
@Table(name = "CLAVES_SAP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClavesSap.findAll", query = "SELECT c FROM ClavesSap c")})
public class ClavesSap implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 2)
    @Column(name = "CLAVE")
    private String clave;
    @Size(max = 1)
    @Column(name = "CLASIFICACION")
    private String clasificacion;
    @Size(max = 1)
    @Column(name = "NATURALEZA")
    private String naturaleza;
    @Column(name = "CLAVEAJUSTE")
    private BigInteger claveajuste;
    @OneToMany(mappedBy = "clavecontabledebito")
    private Collection<Conceptos> conceptosCollection;
    @OneToMany(mappedBy = "clavecontablecredito")
    private Collection<Conceptos> conceptosCollection1;

    public ClavesSap() {
    }

    public ClavesSap(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        if (clave != null) {
            this.clave = clave.toUpperCase();
        } else {
            this.clave = clave;
        }
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public BigInteger getClaveajuste() {
        return claveajuste;
    }

    public void setClaveajuste(BigInteger claveajuste) {
        this.claveajuste = claveajuste;
    }

    @XmlTransient
    public Collection<Conceptos> getConceptosCollection() {
        return conceptosCollection;
    }

    public void setConceptosCollection(Collection<Conceptos> conceptosCollection) {
        this.conceptosCollection = conceptosCollection;
    }

    @XmlTransient
    public Collection<Conceptos> getConceptosCollection1() {
        return conceptosCollection1;
    }

    public void setConceptosCollection1(Collection<Conceptos> conceptosCollection1) {
        this.conceptosCollection1 = conceptosCollection1;
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
        if (!(object instanceof ClavesSap)) {
            return false;
        }
        ClavesSap other = (ClavesSap) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ClavesSap[ secuencia=" + secuencia + " ]";
    }

}
