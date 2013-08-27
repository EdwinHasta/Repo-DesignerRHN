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
@Table(name = "PRY_CLIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PryClientes.findAll", query = "SELECT p FROM PryClientes p"),
    @NamedQuery(name = "PryClientes.findBySecuencia", query = "SELECT p FROM PryClientes p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "PryClientes.findByNombre", query = "SELECT p FROM PryClientes p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PryClientes.findByDireccion", query = "SELECT p FROM PryClientes p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "PryClientes.findByTelefono", query = "SELECT p FROM PryClientes p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "PryClientes.findByContacto", query = "SELECT p FROM PryClientes p WHERE p.contacto = :contacto")})
public class PryClientes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 30)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 30)
    @Column(name = "CONTACTO")
    private String contacto;
    @OneToMany(mappedBy = "pryCliente")
    private Collection<Proyectos> proyectosCollection;

    public PryClientes() {
    }

    public PryClientes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public PryClientes(BigDecimal secuencia, String nombre) {
        this.secuencia = secuencia;
        this.nombre = nombre;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
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
        if (!(object instanceof PryClientes)) {
            return false;
        }
        PryClientes other = (PryClientes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PryClientes[ secuencia=" + secuencia + " ]";
    }
    
}
