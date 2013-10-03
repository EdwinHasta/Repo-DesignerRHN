/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Administrator
 */
@Entity
@Table(name = "CONTRATOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratos.findAll", query = "SELECT c FROM Contratos c")})
public class Contratos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ESTADO")
    private String estado;
    @Lob
    @Size(max = 0)
    @Column(name = "CONVENCION")
    private String convencion;
    @Size(max = 1)
    @Column(name = "RELACIONCONECOPETROL")
    private String relacionconecopetrol;
    @JoinColumn(name = "TIPOCOTIZANTE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposCotizantes tipocotizante;
    @OneToMany(mappedBy = "contratopadre")
    private Collection<Contratos> contratosCollection;
    @JoinColumn(name = "CONTRATOPADRE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Contratos contratopadre;
    @OneToMany(mappedBy = "contratohijo")
    private Collection<Contratos> contratosCollection1;
    @JoinColumn(name = "CONTRATOHIJO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Contratos contratohijo;

    public Contratos() {
    }

    public Contratos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Contratos(BigInteger secuencia, String descripcion, String estado) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConvencion() {
        return convencion;
    }

    public void setConvencion(String convencion) {
        this.convencion = convencion;
    }

    public String getRelacionconecopetrol() {
        return relacionconecopetrol;
    }

    public void setRelacionconecopetrol(String relacionconecopetrol) {
        this.relacionconecopetrol = relacionconecopetrol;
    }

    public TiposCotizantes getTipocotizante() {
        if(tipocotizante == null){
            tipocotizante = new TiposCotizantes();
        }
        return tipocotizante;
    }

    public void setTipocotizante(TiposCotizantes tipocotizante) {
        this.tipocotizante = tipocotizante;
    }

    @XmlTransient
    public Collection<Contratos> getContratosCollection() {
        return contratosCollection;
    }

    public void setContratosCollection(Collection<Contratos> contratosCollection) {
        this.contratosCollection = contratosCollection;
    }

    public Contratos getContratopadre() {
        return contratopadre;
    }

    public void setContratopadre(Contratos contratopadre) {
        this.contratopadre = contratopadre;
    }

    @XmlTransient
    public Collection<Contratos> getContratosCollection1() {
        return contratosCollection1;
    }

    public void setContratosCollection1(Collection<Contratos> contratosCollection1) {
        this.contratosCollection1 = contratosCollection1;
    }

    public Contratos getContratohijo() {
        return contratohijo;
    }

    public void setContratohijo(Contratos contratohijo) {
        this.contratohijo = contratohijo;
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
        if (!(object instanceof Contratos)) {
            return false;
        }
        Contratos other = (Contratos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Contratos[ secuencia=" + secuencia + " ]";
    }
    
}
