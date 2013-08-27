/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "USUARIOSINFOREPORTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosInforeportes.findAll", query = "SELECT u FROM UsuariosInforeportes u"),
    @NamedQuery(name = "UsuariosInforeportes.findBySecuencia", query = "SELECT u FROM UsuariosInforeportes u WHERE u.secuencia = :secuencia")})
public class UsuariosInforeportes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @JoinColumn(name = "USUARIO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "INFOREPORTE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Inforeportes inforeporte;

    public UsuariosInforeportes() {
    }

    public UsuariosInforeportes(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Inforeportes getInforeporte() {
        return inforeporte;
    }

    public void setInforeporte(Inforeportes inforeporte) {
        this.inforeporte = inforeporte;
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
        if (!(object instanceof UsuariosInforeportes)) {
            return false;
        }
        UsuariosInforeportes other = (UsuariosInforeportes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Usuariosinforeportes[ secuencia=" + secuencia + " ]";
    }
    
}
