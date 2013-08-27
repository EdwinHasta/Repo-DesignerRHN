/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
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
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")})
public class Usuarios implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioreporta")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(mappedBy = "usuarioaprueba")
    private Collection<Novedades> novedadesCollection1;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 1)
    @Column(name = "ACTIVO")
    private String activo;
    @Size(max = 15)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 1)
    @Column(name = "PROPIETARIO")
    private String propietario;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @OneToOne
    private Personas persona;
    @JoinColumn(name = "PERFIL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Perfiles perfil;
    @JoinColumn(name = "PANTALLAINICIO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Pantallas pantallainicio;

    public Usuarios() {
    }

    public Usuarios(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public Pantallas getPantallainicio() {
        return pantallainicio;
    }

    public void setPantallainicio(Pantallas pantallainicio) {
        this.pantallainicio = pantallainicio;
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Usuarios[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection1() {
        return novedadesCollection1;
    }

    public void setNovedadesCollection1(Collection<Novedades> novedadesCollection1) {
        this.novedadesCollection1 = novedadesCollection1;
    }
    
}
