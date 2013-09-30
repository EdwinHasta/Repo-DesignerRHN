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
@Table(name = "ESTADOSAFILIACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosAfiliaciones.findAll", query = "SELECT e FROM EstadosAfiliaciones e"),
    @NamedQuery(name = "EstadosAfiliaciones.findBySecuencia", query = "SELECT e FROM EstadosAfiliaciones e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "EstadosAfiliaciones.findByCodigo", query = "SELECT e FROM EstadosAfiliaciones e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "EstadosAfiliaciones.findByNombre", query = "SELECT e FROM EstadosAfiliaciones e WHERE e.nombre = :nombre")})
public class EstadosAfiliaciones implements Serializable { 
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "estadoafiliacion")
    private Collection<VigenciasAfiliaciones> vigenciasafiliacionesCollection;

    public EstadosAfiliaciones() {
    }

    public EstadosAfiliaciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
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

    public String getNombre() {
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<VigenciasAfiliaciones> getVigenciasafiliacionesCollection() {
        return vigenciasafiliacionesCollection;
    }

    public void setVigenciasafiliacionesCollection(Collection<VigenciasAfiliaciones> vigenciasafiliacionesCollection) {
        this.vigenciasafiliacionesCollection = vigenciasafiliacionesCollection;
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
        if (!(object instanceof EstadosAfiliaciones)) {
            return false;
        }
        EstadosAfiliaciones other = (EstadosAfiliaciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Estadosafiliaciones[ secuencia=" + secuencia + " ]";
    }
    
}
