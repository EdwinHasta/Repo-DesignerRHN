/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "TIPOSDIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDias.findAll", query = "SELECT t FROM TiposDias t"),
    @NamedQuery(name = "TiposDias.findBySecuencia", query = "SELECT t FROM TiposDias t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposDias.findByCodigo", query = "SELECT t FROM TiposDias t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TiposDias.findByDescripcion", query = "SELECT t FROM TiposDias t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposDias.findByTipo", query = "SELECT t FROM TiposDias t WHERE t.tipo = :tipo")})
public class TiposDias implements Serializable {

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
    @Column(name = "TIPO")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipodia")
    private List<ExtrasRecargos> extrasRecargosList;

    public TiposDias() {
    }

    public TiposDias(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposDias(BigInteger secuencia, Integer codigo, String descripcion) {
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
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    @XmlTransient
    public List<ExtrasRecargos> getExtrasRecargosList() {
        return extrasRecargosList;
    }

    public void setExtrasRecargosList(List<ExtrasRecargos> extrasRecargosList) {
        this.extrasRecargosList = extrasRecargosList;
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
        if (!(object instanceof TiposDias)) {
            return false;
        }
        TiposDias other = (TiposDias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposdias[ secuencia=" + secuencia + " ]";
    }

}
