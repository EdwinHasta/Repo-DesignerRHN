/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author Administrador
 */
@Entity
@Table(name = "LUGARESOCURRENCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LugaresOcurrencias.findAll", query = "SELECT l FROM LugaresOcurrencias l")})
public class LugaresOcurrencias implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public LugaresOcurrencias() {
    }

    public LugaresOcurrencias(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public LugaresOcurrencias(BigInteger secuencia, Integer codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null) {
            this.descripcion = descripcion.toUpperCase();
        } else {
            this.descripcion = descripcion;
        }

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
        if (!(object instanceof LugaresOcurrencias)) {
            return false;
        }
        LugaresOcurrencias other = (LugaresOcurrencias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LugaresOcurrencias[ secuencia=" + secuencia + " ]";
    }

}
