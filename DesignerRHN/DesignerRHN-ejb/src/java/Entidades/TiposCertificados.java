/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
@Table(name = "TIPOSCERTIFICADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposCertificados.findAll", query = "SELECT t FROM TiposCertificados t")})
public class TiposCertificados implements Serializable {

    @Column(name = "CODIGO")
    private Short codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocertificado")
    private Collection<OtrosCertificados> otrosCertificadosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public TiposCertificados() {
    }

    public TiposCertificados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof TiposCertificados)) {
            return false;
        }
        TiposCertificados other = (TiposCertificados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposcertificados[ secuencia=" + secuencia + " ]";
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<OtrosCertificados> getOtrosCertificadosCollection() {
        return otrosCertificadosCollection;
    }

    public void setOtrosCertificadosCollection(Collection<OtrosCertificados> otrosCertificadosCollection) {
        this.otrosCertificadosCollection = otrosCertificadosCollection;
    }
}
