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
 * @author Administrator
 */
@Entity
@Table(name = "GRUPOSTIPOSENTIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupostiposentidades.findAll", query = "SELECT g FROM Grupostiposentidades g")})
public class Grupostiposentidades implements Serializable {

    @Column(name = "CODIGO")
    private Integer codigo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupotipoentidad")
    private Collection<TSGruposTiposEntidades> tSGruposTiposEntidadesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 1)
    @Column(name = "REQUERIDOPILA")
    private String requeridopila;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private Collection<TiposEntidades> tiposentidadesCollection;

    public Grupostiposentidades() {
    }

    public Grupostiposentidades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Grupostiposentidades(BigInteger secuencia, Integer codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getRequeridopila() {
        return requeridopila;
    }

    public void setRequeridopila(String requeridopila) {
        this.requeridopila = requeridopila;
    }

    @XmlTransient
    public Collection<TiposEntidades> getTiposentidadesCollection() {
        return tiposentidadesCollection;
    }

    public void setTiposentidadesCollection(Collection<TiposEntidades> tiposentidadesCollection) {
        this.tiposentidadesCollection = tiposentidadesCollection;
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
        if (!(object instanceof Grupostiposentidades)) {
            return false;
        }
        Grupostiposentidades other = (Grupostiposentidades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Grupostiposentidades[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<TSGruposTiposEntidades> getTSGruposTiposEntidadesCollection() {
        return tSGruposTiposEntidadesCollection;
    }

    public void setTSGruposTiposEntidadesCollection(Collection<TSGruposTiposEntidades> tSGruposTiposEntidadesCollection) {
        this.tSGruposTiposEntidadesCollection = tSGruposTiposEntidadesCollection;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

}
