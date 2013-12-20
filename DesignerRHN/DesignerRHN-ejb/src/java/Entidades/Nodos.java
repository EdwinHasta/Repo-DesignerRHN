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
 * @author Administrator
 */
@Entity
@Table(name = "NODOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nodos.findAll", query = "SELECT n FROM Nodos n")})
public class Nodos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSICION")
    private short posicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 400)
    @Column(name = "FORMULA")
    private String formula;
    @OneToMany(mappedBy = "nodo")
    private Collection<SolucionesNodos> solucionesnodosCollection;
    @JoinColumn(name = "OPERANDO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Operandos operando;
    @JoinColumn(name = "OPERADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Operadores operador;
    @JoinColumn(name = "HISTORIAFORMULA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Historiasformulas historiaformula;

    public Nodos() {
    }

    public Nodos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Nodos(BigInteger secuencia, short posicion) {
        this.secuencia = secuencia;
        this.posicion = posicion;
    }

    public short getPosicion() {
        return posicion;
    }

    public void setPosicion(short posicion) {
        this.posicion = posicion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @XmlTransient
    public Collection<SolucionesNodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<SolucionesNodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    public Operandos getOperando() {
        return operando;
    }

    public void setOperando(Operandos operando) {
        this.operando = operando;
    }

    public Operadores getOperador() {
        return operador;
    }

    public void setOperador(Operadores operador) {
        this.operador = operador;
    }

    public Historiasformulas getHistoriaformula() {
        return historiaformula;
    }

    public void setHistoriaformula(Historiasformulas historiaformula) {
        this.historiaformula = historiaformula;
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
        if (!(object instanceof Nodos)) {
            return false;
        }
        Nodos other = (Nodos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Nodos[ secuencia=" + secuencia + " ]";
    }
    
}
