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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "MODULOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulos.findAll", query = "SELECT m FROM Modulos m ORDER BY m.nombrecorto")})
public class Modulos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private Collection<ObjetosDB> objetosDBCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NOMBRECORTO")
    private String nombrecorto;
    @Size(max = 100)
    @Column(name = "OBSERVACION")
    private String observacion;
    @OneToMany(mappedBy = "modulo")
    private Collection<Pantallas> pantallasCollection;

    public Modulos() {
    }

    public Modulos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Modulos(BigInteger secuencia, String nombre, String nombrecorto) {
        this.secuencia = secuencia;
        this.nombre = nombre;
        this.nombrecorto = nombrecorto;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
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

    public String getNombrecorto() {
        return nombrecorto;
    }

    public void setNombrecorto(String nombrecorto) {
        this.nombrecorto = nombrecorto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @XmlTransient
    public Collection<Pantallas> getPantallasCollection() {
        return pantallasCollection;
    }

    public void setPantallasCollection(Collection<Pantallas> pantallasCollection) {
        this.pantallasCollection = pantallasCollection;
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
        if (!(object instanceof Modulos)) {
            return false;
        }
        Modulos other = (Modulos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Modulos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ObjetosDB> getObjetosDBCollection() {
        return objetosDBCollection;
    }

    public void setObjetosDBCollection(Collection<ObjetosDB> objetosDBCollection) {
        this.objetosDBCollection = objetosDBCollection;
    }
    
}
