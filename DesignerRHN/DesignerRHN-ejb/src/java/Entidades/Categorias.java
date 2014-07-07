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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CATEGORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c")})
public class Categorias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private BigInteger codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "TIPOSUELDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposSueldos tiposueldo;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Conceptos concepto;
    @JoinColumn(name = "CLASECATEGORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private ClasesCategorias clasecategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Escalafones> escalafonesCollection;

    public Categorias() {
    }

    public Categorias(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Categorias(BigInteger secuencia, BigInteger codigo, String descripcion) {
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

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public TiposSueldos getTiposueldo() {
        return tiposueldo;
    }

    public void setTiposueldo(TiposSueldos tiposueldo) {
        this.tiposueldo = tiposueldo;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
    }

    public ClasesCategorias getClasecategoria() {
        return clasecategoria;
    }

    public void setClasecategoria(ClasesCategorias clasecategoria) {
        this.clasecategoria = clasecategoria;
    }

    @XmlTransient
    public Collection<Escalafones> getEscalafonesCollection() {
        return escalafonesCollection;
    }

    public void setEscalafonesCollection(Collection<Escalafones> escalafonesCollection) {
        this.escalafonesCollection = escalafonesCollection;
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
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Categorias[ secuencia=" + secuencia + " ]";
    }
    
}
