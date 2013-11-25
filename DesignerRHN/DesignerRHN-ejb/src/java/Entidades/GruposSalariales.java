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
import javax.persistence.CascadeType;
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
@Table(name = "GRUPOSSALARIALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GruposSalariales.findAll", query = "SELECT g FROM GruposSalariales g")})
public class GruposSalariales implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gruposalarial")
    private Collection<VigenciasGruposSalariales> vigenciasGruposSalarialesCollection;
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "SALARIO")
    private BigDecimal salario;
    @OneToMany(mappedBy = "gruposalarial")
    private Collection<Cargos> cargosCollection;
    @JoinColumn(name = "ESCALAFONSALARIAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EscalafonesSalariales escalafonsalarial;

    public GruposSalariales() {
    }

    public GruposSalariales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public GruposSalariales(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @XmlTransient
    public Collection<Cargos> getCargosCollection() {
        return cargosCollection;
    }

    public void setCargosCollection(Collection<Cargos> cargosCollection) {
        this.cargosCollection = cargosCollection;
    }

    public EscalafonesSalariales getEscalafonsalarial() {
        return escalafonsalarial;
    }

    public void setEscalafonsalarial(EscalafonesSalariales escalafonsalarial) {
        this.escalafonsalarial = escalafonsalarial;
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
        if (!(object instanceof GruposSalariales)) {
            return false;
        }
        GruposSalariales other = (GruposSalariales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Grupossalariales[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasGruposSalariales> getVigenciasGruposSalarialesCollection() {
        return vigenciasGruposSalarialesCollection;
    }

    public void setVigenciasGruposSalarialesCollection(Collection<VigenciasGruposSalariales> vigenciasGruposSalarialesCollection) {
        this.vigenciasGruposSalarialesCollection = vigenciasGruposSalarialesCollection;
    }

    
}
