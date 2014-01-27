/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSSUELDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposSueldos.findAll", query = "SELECT t FROM TiposSueldos t")})
public class TiposSueldos implements Serializable {

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
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Short codigo;
    @Size(max = 1)
    @Column(name = "CAPACIDADENDEUDAMIENTO")
    private String capacidadendeudamiento;
    @Size(max = 1)
    @Column(name = "BASICO")
    private String basico;
    @Size(max = 1)
    @Column(name = "ADICIONALBASICO")
    private String adicionalbasico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposueldo")
    private Collection<VigenciasSueldos> vigenciassueldosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposueldo")
    private Collection<Categorias> categoriasCollection;

    public TiposSueldos() {
    }

    public TiposSueldos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposSueldos(BigDecimal secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getCapacidadendeudamiento() {
        return capacidadendeudamiento;
    }

    public void setCapacidadendeudamiento(String capacidadendeudamiento) {
        this.capacidadendeudamiento = capacidadendeudamiento;
    }

    public String getBasico() {
        return basico;
    }

    public void setBasico(String basico) {
        this.basico = basico;
    }

    public String getAdicionalbasico() {
        return adicionalbasico;
    }

    public void setAdicionalbasico(String adicionalbasico) {
        this.adicionalbasico = adicionalbasico;
    }

    @XmlTransient
    public Collection<VigenciasSueldos> getVigenciassueldosCollection() {
        return vigenciassueldosCollection;
    }

    public void setVigenciassueldosCollection(Collection<VigenciasSueldos> vigenciassueldosCollection) {
        this.vigenciassueldosCollection = vigenciassueldosCollection;
    }

    @XmlTransient
    public Collection<Categorias> getCategoriasCollection() {
        return categoriasCollection;
    }

    public void setCategoriasCollection(Collection<Categorias> categoriasCollection) {
        this.categoriasCollection = categoriasCollection;
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
        if (!(object instanceof TiposSueldos)) {
            return false;
        }
        TiposSueldos other = (TiposSueldos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tipossueldos[ secuencia=" + secuencia + " ]";
    }

}
