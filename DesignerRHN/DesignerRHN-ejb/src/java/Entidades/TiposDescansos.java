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
@Table(name = "TIPOSDESCANSOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDescansos.findAll", query = "SELECT t FROM TiposDescansos t"),
    @NamedQuery(name = "TiposDescansos.findByCodigo", query = "SELECT t FROM TiposDescansos t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TiposDescansos.findByDescripcion", query = "SELECT t FROM TiposDescansos t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposDescansos.findBySecuencia", query = "SELECT t FROM TiposDescansos t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposDescansos.findByDiastrabajados", query = "SELECT t FROM TiposDescansos t WHERE t.diastrabajados = :diastrabajados"),
    @NamedQuery(name = "TiposDescansos.findByDiasdescansados", query = "SELECT t FROM TiposDescansos t WHERE t.diasdescansados = :diasdescansados")})
public class TiposDescansos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "DIASTRABAJADOS")
    private BigInteger diastrabajados;
    @Column(name = "DIASDESCANSADOS")
    private BigInteger diasdescansados;
    @OneToMany(mappedBy = "tipodescanso")
    private Collection<VigenciasJornadas> vigenciasjornadasCollection;

    public TiposDescansos() {
    }

    public TiposDescansos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposDescansos(BigInteger secuencia, String codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getDiastrabajados() {
        return diastrabajados;
    }

    public void setDiastrabajados(BigInteger diastrabajados) {
        this.diastrabajados = diastrabajados;
    }

    public BigInteger getDiasdescansados() {
        return diasdescansados;
    }

    public void setDiasdescansados(BigInteger diasdescansados) {
        this.diasdescansados = diasdescansados;
    }

    @XmlTransient
    public Collection<VigenciasJornadas> getVigenciasjornadasCollection() {
        return vigenciasjornadasCollection;
    }

    public void setVigenciasjornadasCollection(Collection<VigenciasJornadas> vigenciasjornadasCollection) {
        this.vigenciasjornadasCollection = vigenciasjornadasCollection;
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
        if (!(object instanceof TiposDescansos)) {
            return false;
        }
        TiposDescansos other = (TiposDescansos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposdescansos[ secuencia=" + secuencia + " ]";
    }
    
}
