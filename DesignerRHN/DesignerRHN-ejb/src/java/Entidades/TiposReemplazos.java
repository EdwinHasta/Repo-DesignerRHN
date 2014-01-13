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
@Table(name = "TIPOSREEMPLAZOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposReemplazos.findAll", query = "SELECT t FROM TiposReemplazos t")})
public class TiposReemplazos implements Serializable {
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
    @Column(name = "FACTORREEMPLAZADO")
    private BigDecimal factorreemplazado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiporeemplazo")
    private Collection<Encargaturas> encargaturasCollection;

    public TiposReemplazos() {
    }

    public TiposReemplazos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposReemplazos(BigInteger secuencia, Integer codigo, String nombre) {
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
        if(nombre == null){
            nombre = (" ");
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public BigDecimal getFactorreemplazado() {
        return factorreemplazado;
    }

    public void setFactorreemplazado(BigDecimal factorreemplazado) {
        this.factorreemplazado = factorreemplazado;
    }

    @XmlTransient
    public Collection<Encargaturas> getEncargaturasCollection() {
        return encargaturasCollection;
    }

    public void setEncargaturasCollection(Collection<Encargaturas> encargaturasCollection) {
        this.encargaturasCollection = encargaturasCollection;
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
        if (!(object instanceof TiposReemplazos)) {
            return false;
        }
        TiposReemplazos other = (TiposReemplazos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposReemplazos[ secuencia=" + secuencia + " ]";
    }
    
}
