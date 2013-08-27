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
 * @author user
 */
@Entity
@Table(name = "TIPOSPENSIONADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposPensionados.findAll", query = "SELECT t FROM TiposPensionados t"),
    @NamedQuery(name = "TiposPensionados.findBySecuencia", query = "SELECT t FROM TiposPensionados t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposPensionados.findByCodigo", query = "SELECT t FROM TiposPensionados t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TiposPensionados.findByDescripcion", query = "SELECT t FROM TiposPensionados t WHERE t.descripcion = :descripcion")})
public class TiposPensionados implements Serializable {
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
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "tipopensionado")
    private Collection<Pensionados> pensionadosCollection;

    public TiposPensionados() {
    }

    public TiposPensionados(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposPensionados(BigDecimal secuencia, BigInteger codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Pensionados> getPensionadosCollection() {
        return pensionadosCollection;
    }

    public void setPensionadosCollection(Collection<Pensionados> pensionadosCollection) {
        this.pensionadosCollection = pensionadosCollection;
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
        if (!(object instanceof TiposPensionados)) {
            return false;
        }
        TiposPensionados other = (TiposPensionados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tipospensionados[ secuencia=" + secuencia + " ]";
    }
    
}
