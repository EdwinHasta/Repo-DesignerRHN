/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PROYECTO01
 */
@Entity
@Table(name = "PLANTASPERSONALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlantasPersonales.findAll", query = "SELECT p FROM PlantasPersonales p"),
    @NamedQuery(name = "PlantasPersonales.findBySecuencia", query = "SELECT p FROM PlantasPersonales p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "PlantasPersonales.findByTipo", query = "SELECT p FROM PlantasPersonales p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "PlantasPersonales.findByCantidad", query = "SELECT p FROM PlantasPersonales p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "PlantasPersonales.findByDescripcion", query = "SELECT p FROM PlantasPersonales p WHERE p.descripcion = :descripcion")})
public class PlantasPersonales implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Size(max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "CANTIDAD")
    private Short cantidad;
    @Size(max = 1000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "NIVEL", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Niveles nivel;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Estructuras estructura;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Cargos cargo;

    public PlantasPersonales() {
    }

    public PlantasPersonales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Niveles getNivel() {
        return nivel;
    }

    public void setNivel(Niveles nivel) {
        this.nivel = nivel;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof PlantasPersonales)) {
            return false;
        }
        PlantasPersonales other = (PlantasPersonales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PlantasPersonales[ secuencia=" + secuencia + " ]";
    }
    
}
