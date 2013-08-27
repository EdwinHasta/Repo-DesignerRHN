/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PDGPOLITICAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pdgpoliticas.findAll", query = "SELECT p FROM Pdgpoliticas p"),
    @NamedQuery(name = "Pdgpoliticas.findBySecuencia", query = "SELECT p FROM Pdgpoliticas p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "Pdgpoliticas.findByCodigo", query = "SELECT p FROM Pdgpoliticas p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Pdgpoliticas.findByDescripcion", query = "SELECT p FROM Pdgpoliticas p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Pdgpoliticas.findByFechainicial", query = "SELECT p FROM Pdgpoliticas p WHERE p.fechainicial = :fechainicial"),
    @NamedQuery(name = "Pdgpoliticas.findByFechafinal", query = "SELECT p FROM Pdgpoliticas p WHERE p.fechafinal = :fechafinal"),
    @NamedQuery(name = "Pdgpoliticas.findByPeso", query = "SELECT p FROM Pdgpoliticas p WHERE p.peso = :peso"),
    @NamedQuery(name = "Pdgpoliticas.findByCumplido", query = "SELECT p FROM Pdgpoliticas p WHERE p.cumplido = :cumplido"),
    @NamedQuery(name = "Pdgpoliticas.findByEmpresa", query = "SELECT p FROM Pdgpoliticas p WHERE p.empresa = :empresa")})
public class Pdgpoliticas implements Serializable {
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
    @Size(min = 1, max = 1000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "PESO")
    private BigInteger peso;
    @Column(name = "CUMPLIDO")
    private BigInteger cumplido;
    @Column(name = "EMPRESA")
    private BigInteger empresa;
    @JoinColumn(name = "EVALVIGCONVOCATORIA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Evalvigconvocatorias evalvigconvocatoria;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Estructuras estructura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pdgpolitica")
    private Collection<Pdgestrategias> pdgestrategiasCollection;

    public Pdgpoliticas() {
    }

    public Pdgpoliticas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Pdgpoliticas(BigDecimal secuencia, BigInteger codigo, String descripcion, Date fechainicial) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechainicial = fechainicial;
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

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
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

    public BigInteger getEmpresa() {
        return empresa;
    }

    public void setEmpresa(BigInteger empresa) {
        this.empresa = empresa;
    }

    public Evalvigconvocatorias getEvalvigconvocatoria() {
        return evalvigconvocatoria;
    }

    public void setEvalvigconvocatoria(Evalvigconvocatorias evalvigconvocatoria) {
        this.evalvigconvocatoria = evalvigconvocatoria;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    @XmlTransient
    public Collection<Pdgestrategias> getPdgestrategiasCollection() {
        return pdgestrategiasCollection;
    }

    public void setPdgestrategiasCollection(Collection<Pdgestrategias> pdgestrategiasCollection) {
        this.pdgestrategiasCollection = pdgestrategiasCollection;
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
        if (!(object instanceof Pdgpoliticas)) {
            return false;
        }
        Pdgpoliticas other = (Pdgpoliticas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pdgpoliticas[ secuencia=" + secuencia + " ]";
    }
    
}
