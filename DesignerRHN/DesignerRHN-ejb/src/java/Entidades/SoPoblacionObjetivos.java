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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "SOPOBLACIONOBJETIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SoPoblacionObjetivos.findAll", query = "SELECT s FROM SoPoblacionObjetivos s"),
    @NamedQuery(name = "SoPoblacionObjetivos.findBySecuencia", query = "SELECT s FROM SoPoblacionObjetivos s WHERE s.secuencia = :secuencia"),
    @NamedQuery(name = "SoPoblacionObjetivos.findByCodigo", query = "SELECT s FROM SoPoblacionObjetivos s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SoPoblacionObjetivos.findByDescripcion", query = "SELECT s FROM SoPoblacionObjetivos s WHERE s.descripcion = :descripcion")})
public class SoPoblacionObjetivos implements Serializable {
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

    public SoPoblacionObjetivos() {
    }

    public SoPoblacionObjetivos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public SoPoblacionObjetivos(BigInteger secuencia, Integer codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
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
        if(descripcion==null)
        {
        descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
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
        if (!(object instanceof SoPoblacionObjetivos)) {
            return false;
        }
        SoPoblacionObjetivos other = (SoPoblacionObjetivos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SoPoblacionObjetivos[ secuencia=" + secuencia + " ]";
    }
    
}
