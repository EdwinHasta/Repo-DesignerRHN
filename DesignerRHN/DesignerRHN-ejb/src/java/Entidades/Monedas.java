/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
 * @author user
 */
@Entity
@Table(name = "MONEDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Monedas.findAll", query = "SELECT m FROM Monedas m")})
public class Monedas implements Serializable {
    @OneToMany(mappedBy = "tipomoneda")
    private Collection<Proyectos> proyectosCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moneda")
    private Collection<VigenciasMonedasBases> vigenciasMonedasBasesCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CODIGOALTERNATIVO")
    private String codigoalternativo;

    public Monedas() {
    }

    public Monedas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Monedas(BigInteger secuencia, String codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (nombre != null) {
            this.codigo = codigo.toUpperCase();
        } else {
            this.codigo = codigo;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre.toUpperCase();
        } else {
            this.nombre = nombre;
        }
    }

    public String getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(String codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
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
        if (!(object instanceof Monedas)) {
            return false;
        }
        Monedas other = (Monedas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Monedas[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasMonedasBases> getVigenciasMonedasBasesCollection() {
        return vigenciasMonedasBasesCollection;
    }

    public void setVigenciasMonedasBasesCollection(Collection<VigenciasMonedasBases> vigenciasMonedasBasesCollection) {
        this.vigenciasMonedasBasesCollection = vigenciasMonedasBasesCollection;
    }

    @XmlTransient
    public Collection<Proyectos> getProyectosCollection() {
        return proyectosCollection;
    }

    public void setProyectosCollection(Collection<Proyectos> proyectosCollection) {
        this.proyectosCollection = proyectosCollection;
    }

}
