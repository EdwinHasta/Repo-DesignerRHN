/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "ACTIVIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a"),
    @NamedQuery(name = "Actividades.findBySecuencia", query = "SELECT a FROM Actividades a WHERE a.secuencia = :secuencia"),
    @NamedQuery(name = "Actividades.findByCodigo", query = "SELECT a FROM Actividades a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Actividades.findByDescripcion", query = "SELECT a FROM Actividades a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividades.findByClaseactividad", query = "SELECT a FROM Actividades a WHERE a.claseactividad = :claseactividad")})
public class Actividades implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "CLASEACTIVIDAD")
    private String claseactividad;

    public Actividades() {
    }

    public Actividades(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Actividades(BigDecimal secuencia, short codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClaseactividad() {
        return claseactividad;
    }

    public void setClaseactividad(String claseactividad) {
        this.claseactividad = claseactividad;
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
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Actividades[ secuencia=" + secuencia + " ]";
    }
    
}
