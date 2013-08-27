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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ESCALAFONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escalafones.findAll", query = "SELECT e FROM Escalafones e")})
public class Escalafones implements Serializable {
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
    @Column(name = "CODIGO")
    private String codigo;
    @JoinColumn(name = "SUBCATEGORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private SubCategorias subcategoria;
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Categorias categoria;
    @OneToMany(mappedBy = "escalafon")
    private Collection<VigenciasCargos> vigenciascargosCollection;

    public Escalafones() {
    }

    public Escalafones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Escalafones(BigDecimal secuencia, String codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public SubCategorias getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(SubCategorias subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<VigenciasCargos> getVigenciascargosCollection() {
        return vigenciascargosCollection;
    }

    public void setVigenciascargosCollection(Collection<VigenciasCargos> vigenciascargosCollection) {
        this.vigenciascargosCollection = vigenciascargosCollection;
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
        if (!(object instanceof Escalafones)) {
            return false;
        }
        Escalafones other = (Escalafones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Escalafones[ secuencia=" + secuencia + " ]";
    }
    
}
