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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposueldo")
    private Collection<TSFormulasConceptos> tSFormulasConceptosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposueldo")
    private Collection<TSGruposTiposEntidades> tSGruposTiposEntidadesCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 30)
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
    @Transient
    private String strCapacidad;
    @Transient
    private String strBasico;
    @Transient
    private String strAdicional;

    public TiposSueldos() {
    }

    public TiposSueldos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposSueldos(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
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

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getCapacidadendeudamiento() {
        if (capacidadendeudamiento == null) {
            capacidadendeudamiento = "N";
        }
        return capacidadendeudamiento;
    }

    public void setCapacidadendeudamiento(String capacidadendeudamiento) {
        this.capacidadendeudamiento = capacidadendeudamiento;
    }

    public String getBasico() {
        if (basico == null) {
            basico = "N";
        }
        return basico;
    }

    public void setBasico(String basico) {
        this.basico = basico;
    }

    public String getAdicionalbasico() {
        if (adicionalbasico == null) {
            adicionalbasico = "N";
        }
        return adicionalbasico;
    }

    public void setAdicionalbasico(String adicionalbasico) {
        this.adicionalbasico = adicionalbasico;
    }

    public String getStrCapacidad() {
        getCapacidadendeudamiento();
        if (capacidadendeudamiento.equalsIgnoreCase("N")) {
            strCapacidad = "SI";
        } else {
            strCapacidad = "NO";
        }
        return strCapacidad;
    }

    public void setStrCapacidad(String strCapacidad) {
        this.strCapacidad = strCapacidad;
    }

    public String getStrBasico() {
        getBasico();
        if (basico.equalsIgnoreCase("N")) {
            strBasico = "SI";
        } else {
            strBasico = "NO";
        }
        return strBasico;
    }

    public void setStrBasico(String strBasico) {
        this.strBasico = strBasico;
    }

    public String getStrAdicional() {
        getAdicionalbasico();
        if (adicionalbasico.equalsIgnoreCase("N")) {
            strAdicional = "SI";
        } else {
            strAdicional = "NO";
        }
        return strAdicional;
    }

    public void setStrAdicional(String strAdicional) {
        this.strAdicional = strAdicional;
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

    @XmlTransient
    public Collection<TSFormulasConceptos> getTSFormulasConceptosCollection() {
        return tSFormulasConceptosCollection;
    }

    public void setTSFormulasConceptosCollection(Collection<TSFormulasConceptos> tSFormulasConceptosCollection) {
        this.tSFormulasConceptosCollection = tSFormulasConceptosCollection;
    }

    @XmlTransient
    public Collection<TSGruposTiposEntidades> getTSGruposTiposEntidadesCollection() {
        return tSGruposTiposEntidadesCollection;
    }

    public void setTSGruposTiposEntidadesCollection(Collection<TSGruposTiposEntidades> tSGruposTiposEntidadesCollection) {
        this.tSGruposTiposEntidadesCollection = tSGruposTiposEntidadesCollection;
    }

}
