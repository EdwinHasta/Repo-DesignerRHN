/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VWACTUALUSUARIO")
@XmlRootElement
@Cacheable(true)
@NamedQueries({
    @NamedQuery(name = "ActualUsuario.findAll", query = "SELECT a FROM ActualUsuario a"),
    @NamedQuery(name = "ActualUsuario.findBySecuencia", query = "SELECT a FROM ActualUsuario a WHERE a.secuencia = :secuencia"),
    @NamedQuery(name = "ActualUsuario.findByPersona", query = "SELECT a FROM ActualUsuario a WHERE a.persona = :persona"),
    @NamedQuery(name = "ActualUsuario.findByPerfil", query = "SELECT a FROM ActualUsuario a WHERE a.perfil = :perfil"),
    @NamedQuery(name = "ActualUsuario.findByActivo", query = "SELECT a FROM ActualUsuario a WHERE a.activo = :activo"),
    @NamedQuery(name = "ActualUsuario.findByAlias", query = "SELECT a FROM ActualUsuario a WHERE a.alias = :alias"),
    @NamedQuery(name = "ActualUsuario.findByPropietario", query = "SELECT a FROM ActualUsuario a WHERE a.propietario = :propietario"),
    @NamedQuery(name = "ActualUsuario.findByPantallainicio", query = "SELECT a FROM ActualUsuario a WHERE a.pantallainicio = :pantallainicio")})
public class ActualUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    @Id
    private BigInteger secuencia;
    @Column(name = "PERSONA")
    private BigInteger persona;
    @Column(name = "PERFIL")
    private BigInteger perfil;
    @Size(max = 1)
    @Column(name = "ACTIVO")
    private String activo;
    @Size(max = 15)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 1)
    @Column(name = "PROPIETARIO")
    private String propietario;
    @Column(name = "PANTALLAINICIO")
    private BigInteger pantallainicio;

    public ActualUsuario() {
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getPersona() {
        return persona;
    }

    public void setPersona(BigInteger persona) {
        this.persona = persona;
    }

    public BigInteger getPerfil() {
        return perfil;
    }

    public void setPerfil(BigInteger perfil) {
        this.perfil = perfil;
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

    public BigInteger getPantallainicio() {
        return pantallainicio;
    }

    public void setPantallainicio(BigInteger pantallainicio) {
        this.pantallainicio = pantallainicio;
    }
    
}
