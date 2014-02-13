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
@Table(name = "TABLASAUXILIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablasAuxilios.findAll", query = "SELECT t FROM TablasAuxilios t")})
public class TablasAuxilios implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigInteger valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORMAXIMO")
    private BigInteger valormaximo;
    @OneToMany(mappedBy = "tablaauxilio")
    private Collection<EersAuxilios> eersAuxiliosCollection;
    @JoinColumn(name = "VIGENCIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private VigenciasAuxilios vigencia;
    @JoinColumn(name = "TIPOAUXILIO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposAuxilios tipoauxilio;
    @JoinColumn(name = "CONCEPTOTERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Conceptos conceptotercero;
    @JoinColumn(name = "CONCEPTO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Conceptos concepto;

    public TablasAuxilios() {
    }

    public TablasAuxilios(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TablasAuxilios(BigDecimal secuencia, short codigo, String descripcion, BigInteger valor, BigInteger valormaximo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valor = valor;
        this.valormaximo = valormaximo;
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

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public BigInteger getValormaximo() {
        return valormaximo;
    }

    public void setValormaximo(BigInteger valormaximo) {
        this.valormaximo = valormaximo;
    }

    @XmlTransient
    public Collection<EersAuxilios> getEersAuxiliosCollection() {
        return eersAuxiliosCollection;
    }

    public void setEersAuxiliosCollection(Collection<EersAuxilios> eersAuxiliosCollection) {
        this.eersAuxiliosCollection = eersAuxiliosCollection;
    }

    public VigenciasAuxilios getVigencia() {
        return vigencia;
    }

    public void setVigencia(VigenciasAuxilios vigencia) {
        this.vigencia = vigencia;
    }

    public TiposAuxilios getTipoauxilio() {
        return tipoauxilio;
    }

    public void setTipoauxilio(TiposAuxilios tipoauxilio) {
        this.tipoauxilio = tipoauxilio;
    }

    public Conceptos getConceptotercero() {
        return conceptotercero;
    }

    public void setConceptotercero(Conceptos conceptotercero) {
        this.conceptotercero = conceptotercero;
    }

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos concepto) {
        this.concepto = concepto;
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
        if (!(object instanceof TablasAuxilios)) {
            return false;
        }
        TablasAuxilios other = (TablasAuxilios) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TablasAuxilios[ secuencia=" + secuencia + " ]";
    }
    
}
