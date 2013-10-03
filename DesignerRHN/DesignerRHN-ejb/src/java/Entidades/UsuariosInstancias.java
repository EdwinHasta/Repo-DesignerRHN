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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "USUARIOSINSTANCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosInstancias.findAll", query = "SELECT u FROM UsuariosInstancias u")})
public class UsuariosInstancias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @JoinColumn(name = "USUARIO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Usuarios usuario;
    @JoinColumn(name = "INSTANCIA", referencedColumnName = "SECUENCIA")
    @OneToOne(optional = false)
    private Instancias instancia;

    public UsuariosInstancias() {
    }

    public UsuariosInstancias(BigDecimal secuencia) {
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

    public Instancias getInstancia() {
        return instancia;
    }

    public void setInstancia(Instancias instancia) {
        this.instancia = instancia;
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
        if (!(object instanceof UsuariosInstancias)) {
            return false;
        }
        UsuariosInstancias other = (UsuariosInstancias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UsuariosInstancias[ secuencia=" + secuencia + " ]";
    }
    
}
