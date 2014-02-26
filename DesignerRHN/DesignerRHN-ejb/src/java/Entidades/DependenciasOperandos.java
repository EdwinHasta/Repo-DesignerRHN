/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "DEPENDENCIASOPERANDOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DependenciasOperandos.findAll", query = "SELECT d FROM DependenciasOperandos d")})
public class DependenciasOperandos implements Serializable {
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
    private int codigo;
    @Size(max = 2000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CONSECUTIVO")
    private Short consecutivo;
    @JoinColumn(name = "OPERANDO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Operandos operando;
    @Transient
    private String nombre;

    public DependenciasOperandos() {
    }

    public DependenciasOperandos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public DependenciasOperandos(BigInteger secuencia, int codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public Short getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Short consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Operandos getOperando() {
        if (operando == null){
            operando = new Operandos();
        }
        return operando;
    }

    public void setOperando(Operandos operando) {
        this.operando = operando;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof DependenciasOperandos)) {
            return false;
        }
        DependenciasOperandos other = (DependenciasOperandos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DependenciasOperandos[ secuencia=" + secuencia + " ]";
    }
    
}
