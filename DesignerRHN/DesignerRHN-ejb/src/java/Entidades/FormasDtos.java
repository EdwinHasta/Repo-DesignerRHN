/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "FORMASDTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormasDtos.findAll", query = "SELECT f FROM FormasDtos f")})
public class FormasDtos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formadto")
    private Collection<EersPrestamos> eersPrestamosCollection;
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
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "TIPOEMBARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposEmbargos tipoembargo;

    public FormasDtos() {
    }

    public FormasDtos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public FormasDtos(BigInteger secuencia, short codigo, String descripcion, String tipo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if(descripcion == null){
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TiposEmbargos getTipoembargo() {
        return tipoembargo;
    }

    public void setTipoembargo(TiposEmbargos tipoembargo) {
        this.tipoembargo = tipoembargo;
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
        if (!(object instanceof FormasDtos)) {
            return false;
        }
        FormasDtos other = (FormasDtos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FormasDtos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<EersPrestamos> getEersPrestamosCollection() {
        return eersPrestamosCollection;
    }

    public void setEersPrestamosCollection(Collection<EersPrestamos> eersPrestamosCollection) {
        this.eersPrestamosCollection = eersPrestamosCollection;
    }
    
}
