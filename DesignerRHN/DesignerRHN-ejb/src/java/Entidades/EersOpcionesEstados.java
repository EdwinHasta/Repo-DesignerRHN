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
 * @author Administrador
 */
@Entity
@Table(name = "EERSOPCIONESESTADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EersOpcionesEstados.findAll", query = "SELECT e FROM EersOpcionesEstados e"),
    @NamedQuery(name = "EersOpcionesEstados.findBySecuencia", query = "SELECT e FROM EersOpcionesEstados e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "EersOpcionesEstados.findByNombrecorto", query = "SELECT e FROM EersOpcionesEstados e WHERE e.nombrecorto = :nombrecorto"),
    @NamedQuery(name = "EersOpcionesEstados.findByNombrelargo", query = "SELECT e FROM EersOpcionesEstados e WHERE e.nombrelargo = :nombrelargo"),
    @NamedQuery(name = "EersOpcionesEstados.findByTipoeer", query = "SELECT e FROM EersOpcionesEstados e WHERE e.tipoeer = :tipoeer")})
public class EersOpcionesEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NOMBRECORTO")
    private String nombrecorto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBRELARGO")
    private String nombrelargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPOEER")
    private String tipoeer;
    @OneToMany(mappedBy = "eeropcionestadoaprueba")
    private Collection<EersEstados> eersEstadosCollection;
    @OneToMany(mappedBy = "eeropcionestadocancela")
    private Collection<EersEstados> eersEstadosCollection1;
    @OneToMany(mappedBy = "eeropcionestado")
    private Collection<EersFlujos> eersFlujosCollection;
    @OneToMany(mappedBy = "eeropcionestado")
    private Collection<EersCabeceras> eersCabecerasCollection;

    public EersOpcionesEstados() {
    }

    public EersOpcionesEstados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EersOpcionesEstados(BigInteger secuencia, String nombrecorto, String nombrelargo, String tipoeer) {
        this.secuencia = secuencia;
        this.nombrecorto = nombrecorto;
        this.nombrelargo = nombrelargo;
        this.tipoeer = tipoeer;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNombrecorto() {
        return nombrecorto;
    }

    public void setNombrecorto(String nombrecorto) {
        this.nombrecorto = nombrecorto;
    }

    public String getNombrelargo() {
        return nombrelargo;
    }

    public void setNombrelargo(String nombrelargo) {
        this.nombrelargo = nombrelargo;
    }

    public String getTipoeer() {
        return tipoeer;
    }

    public void setTipoeer(String tipoeer) {
        this.tipoeer = tipoeer;
    }

    @XmlTransient
    public Collection<EersEstados> getEersEstadosCollection() {
        return eersEstadosCollection;
    }

    public void setEersEstadosCollection(Collection<EersEstados> eersEstadosCollection) {
        this.eersEstadosCollection = eersEstadosCollection;
    }

    @XmlTransient
    public Collection<EersEstados> getEersEstadosCollection1() {
        return eersEstadosCollection1;
    }

    public void setEersEstadosCollection1(Collection<EersEstados> eersEstadosCollection1) {
        this.eersEstadosCollection1 = eersEstadosCollection1;
    }

    @XmlTransient
    public Collection<EersFlujos> getEersFlujosCollection() {
        return eersFlujosCollection;
    }

    public void setEersFlujosCollection(Collection<EersFlujos> eersFlujosCollection) {
        this.eersFlujosCollection = eersFlujosCollection;
    }

    @XmlTransient
    public Collection<EersCabeceras> getEersCabecerasCollection() {
        return eersCabecerasCollection;
    }

    public void setEersCabecerasCollection(Collection<EersCabeceras> eersCabecerasCollection) {
        this.eersCabecerasCollection = eersCabecerasCollection;
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
        if (!(object instanceof EersOpcionesEstados)) {
            return false;
        }
        EersOpcionesEstados other = (EersOpcionesEstados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EersOpcionesEstados[ secuencia=" + secuencia + " ]";
    }
    
}
