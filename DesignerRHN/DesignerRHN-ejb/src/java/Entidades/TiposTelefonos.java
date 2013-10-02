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
@Table(name = "TIPOSTELEFONOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposTelefonos.findAll", query = "SELECT t FROM TiposTelefonos t")})
public class TiposTelefonos implements Serializable {
    @OneToMany(mappedBy = "tipotelefono")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipotelefono")
    private Collection<Telefonos> telefonosCollection;

    public TiposTelefonos() {
    }

    public TiposTelefonos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposTelefonos(BigInteger secuencia, BigInteger codigo, String nombre) {
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
        this.nombre = nombre.toUpperCase();
    }

    @XmlTransient
    public Collection<Telefonos> getTelefonosCollection() {
        return telefonosCollection;
    }

    public void setTelefonosCollection(Collection<Telefonos> telefonosCollection) {
        this.telefonosCollection = telefonosCollection;
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
        if (!(object instanceof TiposTelefonos)) {
            return false;
        }
        TiposTelefonos other = (TiposTelefonos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TiposTelefonos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }
    
}
