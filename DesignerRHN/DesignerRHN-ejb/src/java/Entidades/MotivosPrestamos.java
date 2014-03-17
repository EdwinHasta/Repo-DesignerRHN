/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MOTIVOSPRESTAMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivosPrestamos.findAll", query = "SELECT m FROM MotivosPrestamos m")})
public class MotivosPrestamos implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @OneToMany(mappedBy = "motivoprestamo")
    private Collection<EersPrestamos> eersPrestamosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "NOMBRE")
    private String nombre;

    public MotivosPrestamos() {
    }

    public MotivosPrestamos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public MotivosPrestamos(BigInteger secuencia, Integer codigo, String nombre) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MotivosPrestamos)) {
            return false;
        }
        MotivosPrestamos other = (MotivosPrestamos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivosprestamos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<EersPrestamos> getEersPrestamosCollection() {
        return eersPrestamosCollection;
    }

    public void setEersPrestamosCollection(Collection<EersPrestamos> eersPrestamosCollection) {
        this.eersPrestamosCollection = eersPrestamosCollection;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
}
