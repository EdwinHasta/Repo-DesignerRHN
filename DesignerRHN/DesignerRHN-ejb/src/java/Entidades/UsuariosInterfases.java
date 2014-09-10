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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "USUARIOSINTERFASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosInterfases.findAll", query = "SELECT u FROM UsuariosInterfases u"),
    @NamedQuery(name = "UsuariosInterfases.findBySecuencia", query = "SELECT u FROM UsuariosInterfases u WHERE u.secuencia = :secuencia"),
    @NamedQuery(name = "UsuariosInterfases.findByUsuariolocal", query = "SELECT u FROM UsuariosInterfases u WHERE u.usuariolocal = :usuariolocal"),
    @NamedQuery(name = "UsuariosInterfases.findByPasswordlocal", query = "SELECT u FROM UsuariosInterfases u WHERE u.passwordlocal = :passwordlocal"),
    @NamedQuery(name = "UsuariosInterfases.findByServernamelocal", query = "SELECT u FROM UsuariosInterfases u WHERE u.servernamelocal = :servernamelocal"),
    @NamedQuery(name = "UsuariosInterfases.findByPortnumberlocal", query = "SELECT u FROM UsuariosInterfases u WHERE u.portnumberlocal = :portnumberlocal"),
    @NamedQuery(name = "UsuariosInterfases.findBySidlocal", query = "SELECT u FROM UsuariosInterfases u WHERE u.sidlocal = :sidlocal"),
    @NamedQuery(name = "UsuariosInterfases.findByUsuarioremoto", query = "SELECT u FROM UsuariosInterfases u WHERE u.usuarioremoto = :usuarioremoto"),
    @NamedQuery(name = "UsuariosInterfases.findByPasswordremoto", query = "SELECT u FROM UsuariosInterfases u WHERE u.passwordremoto = :passwordremoto"),
    @NamedQuery(name = "UsuariosInterfases.findByServernameremoto", query = "SELECT u FROM UsuariosInterfases u WHERE u.servernameremoto = :servernameremoto"),
    @NamedQuery(name = "UsuariosInterfases.findByPortnumberremoto", query = "SELECT u FROM UsuariosInterfases u WHERE u.portnumberremoto = :portnumberremoto"),
    @NamedQuery(name = "UsuariosInterfases.findBySidremoto", query = "SELECT u FROM UsuariosInterfases u WHERE u.sidremoto = :sidremoto"),
    @NamedQuery(name = "UsuariosInterfases.findByInterfase", query = "SELECT u FROM UsuariosInterfases u WHERE u.interfase = :interfase")})
public class UsuariosInterfases implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIOLOCAL")
    private String usuariolocal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PASSWORDLOCAL")
    private String passwordlocal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SERVERNAMELOCAL")
    private String servernamelocal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PORTNUMBERLOCAL")
    private String portnumberlocal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SIDLOCAL")
    private String sidlocal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIOREMOTO")
    private String usuarioremoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PASSWORDREMOTO")
    private String passwordremoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SERVERNAMEREMOTO")
    private String servernameremoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PORTNUMBERREMOTO")
    private String portnumberremoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SIDREMOTO")
    private String sidremoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "INTERFASE")
    private String interfase;

    public UsuariosInterfases() {
    }

    public UsuariosInterfases(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public UsuariosInterfases(BigInteger secuencia, String usuariolocal, String passwordlocal, String servernamelocal, String portnumberlocal, String sidlocal, String usuarioremoto, String passwordremoto, String servernameremoto, String portnumberremoto, String sidremoto, String interfase) {
        this.secuencia = secuencia;
        this.usuariolocal = usuariolocal;
        this.passwordlocal = passwordlocal;
        this.servernamelocal = servernamelocal;
        this.portnumberlocal = portnumberlocal;
        this.sidlocal = sidlocal;
        this.usuarioremoto = usuarioremoto;
        this.passwordremoto = passwordremoto;
        this.servernameremoto = servernameremoto;
        this.portnumberremoto = portnumberremoto;
        this.sidremoto = sidremoto;
        this.interfase = interfase;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getUsuariolocal() {
        return usuariolocal;
    }

    public void setUsuariolocal(String usuariolocal) {
        this.usuariolocal = usuariolocal;
    }

    public String getPasswordlocal() {
        return passwordlocal;
    }

    public void setPasswordlocal(String passwordlocal) {
        this.passwordlocal = passwordlocal;
    }

    public String getServernamelocal() {
        return servernamelocal;
    }

    public void setServernamelocal(String servernamelocal) {
        this.servernamelocal = servernamelocal;
    }

    public String getPortnumberlocal() {
        return portnumberlocal;
    }

    public void setPortnumberlocal(String portnumberlocal) {
        this.portnumberlocal = portnumberlocal;
    }

    public String getSidlocal() {
        return sidlocal;
    }

    public void setSidlocal(String sidlocal) {
        this.sidlocal = sidlocal;
    }

    public String getUsuarioremoto() {
        return usuarioremoto;
    }

    public void setUsuarioremoto(String usuarioremoto) {
        this.usuarioremoto = usuarioremoto;
    }

    public String getPasswordremoto() {
        return passwordremoto;
    }

    public void setPasswordremoto(String passwordremoto) {
        this.passwordremoto = passwordremoto;
    }

    public String getServernameremoto() {
        return servernameremoto;
    }

    public void setServernameremoto(String servernameremoto) {
        this.servernameremoto = servernameremoto;
    }

    public String getPortnumberremoto() {
        return portnumberremoto;
    }

    public void setPortnumberremoto(String portnumberremoto) {
        this.portnumberremoto = portnumberremoto;
    }

    public String getSidremoto() {
        return sidremoto;
    }

    public void setSidremoto(String sidremoto) {
        this.sidremoto = sidremoto;
    }

    public String getInterfase() {
        return interfase;
    }

    public void setInterfase(String interfase) {
        this.interfase = interfase;
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
        if (!(object instanceof UsuariosInterfases)) {
            return false;
        }
        UsuariosInterfases other = (UsuariosInterfases) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UsuariosInterfases[ secuencia=" + secuencia + " ]";
    }
    
}
