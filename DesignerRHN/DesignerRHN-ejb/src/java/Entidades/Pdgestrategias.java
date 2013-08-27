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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PDGESTRATEGIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pdgestrategias.findAll", query = "SELECT p FROM Pdgestrategias p"),
    @NamedQuery(name = "Pdgestrategias.findBySecuencia", query = "SELECT p FROM Pdgestrategias p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "Pdgestrategias.findByCodigo", query = "SELECT p FROM Pdgestrategias p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pdgestrategias.findByDescripcion", query = "SELECT p FROM Pdgestrategias p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Pdgestrategias.findByPeso", query = "SELECT p FROM Pdgestrategias p WHERE p.peso = :peso"),
    @NamedQuery(name = "Pdgestrategias.findByCumplido", query = "SELECT p FROM Pdgestrategias p WHERE p.cumplido = :cumplido")})
public class Pdgestrategias implements Serializable {
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
    private BigInteger codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PESO")
    private BigInteger peso;
    @Column(name = "CUMPLIDO")
    private BigInteger cumplido;
    @OneToMany(mappedBy = "pdgestrategia")
    private Collection<Evalplanillas> evalplanillasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pdgestrategia")
    private Collection<Pdgmetas> pdgmetasCollection;
    @JoinColumn(name = "PDGPOLITICA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Pdgpoliticas pdgpolitica;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;

    public Pdgestrategias() {
    }

    public Pdgestrategias(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Pdgestrategias(BigDecimal secuencia, BigInteger codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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
        this.descripcion = descripcion;
    }

    public BigInteger getPeso() {
        return peso;
    }

    public void setPeso(BigInteger peso) {
        this.peso = peso;
    }

    public BigInteger getCumplido() {
        return cumplido;
    }

    public void setCumplido(BigInteger cumplido) {
        this.cumplido = cumplido;
    }

    @XmlTransient
    public Collection<Evalplanillas> getEvalplanillasCollection() {
        return evalplanillasCollection;
    }

    public void setEvalplanillasCollection(Collection<Evalplanillas> evalplanillasCollection) {
        this.evalplanillasCollection = evalplanillasCollection;
    }

    @XmlTransient
    public Collection<Pdgmetas> getPdgmetasCollection() {
        return pdgmetasCollection;
    }

    public void setPdgmetasCollection(Collection<Pdgmetas> pdgmetasCollection) {
        this.pdgmetasCollection = pdgmetasCollection;
    }

    public Pdgpoliticas getPdgpolitica() {
        return pdgpolitica;
    }

    public void setPdgpolitica(Pdgpoliticas pdgpolitica) {
        this.pdgpolitica = pdgpolitica;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof Pdgestrategias)) {
            return false;
        }
        Pdgestrategias other = (Pdgestrategias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pdgestrategias[ secuencia=" + secuencia + " ]";
    }
    
}
