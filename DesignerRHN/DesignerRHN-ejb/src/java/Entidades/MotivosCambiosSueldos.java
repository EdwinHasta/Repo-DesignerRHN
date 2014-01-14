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
@Table(name = "MOTIVOSCAMBIOSSUELDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivosCambiosSueldos.findAll", query = "SELECT m FROM MotivosCambiosSueldos m")})
public class MotivosCambiosSueldos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 1)
    @Column(name = "SUELDOPROMEDIO")
    private String sueldopromedio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motivocambiosueldo")
    private Collection<VigenciasSueldos> vigenciassueldosCollection;
    @Transient
    private Boolean estadoSueldoPromedio;

    public MotivosCambiosSueldos() {
    }

    public MotivosCambiosSueldos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public MotivosCambiosSueldos(BigInteger secuencia, Integer codigo, String nombre) {
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getSueldopromedio() {
        return sueldopromedio;
    }

    public void setSueldopromedio(String sueldopromedio) {
        this.sueldopromedio = sueldopromedio;
    }

    @XmlTransient
    public Collection<VigenciasSueldos> getVigenciassueldosCollection() {
        return vigenciassueldosCollection;
    }

    public void setVigenciassueldosCollection(Collection<VigenciasSueldos> vigenciassueldosCollection) {
        this.vigenciassueldosCollection = vigenciassueldosCollection;
    }

    public Boolean getEstadoSueldoPromedio() {
        if(this.sueldopromedio == null || this.sueldopromedio.equals("N")) {
            this.estadoSueldoPromedio = false;
        } else {
            this.estadoSueldoPromedio = true;
        }
        return estadoSueldoPromedio;
    }

    public void setEstadoSueldoPromedio(Boolean estadoSueldoPromedio) {
        this.estadoSueldoPromedio = estadoSueldoPromedio;
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
        if (!(object instanceof MotivosCambiosSueldos)) {
            return false;
        }
        MotivosCambiosSueldos other = (MotivosCambiosSueldos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivoscambiossueldos[ secuencia=" + secuencia + " ]";
    }

}
