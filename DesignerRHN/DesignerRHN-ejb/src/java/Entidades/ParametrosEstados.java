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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PARAMETROSESTADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosEstados.findAll", query = "SELECT p FROM ParametrosEstados p")})
public class ParametrosEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @JoinColumn(name = "PARAMETRO", referencedColumnName = "SECUENCIA")
    @OneToOne(optional = false)
    private Parametros parametros;
    @Size(max = 20)
    @Column(name = "ESTADO")
    private String estado;

    public ParametrosEstados() {
    }

    public ParametrosEstados(Parametros parametros) {
        this.parametros = parametros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parametros != null ? parametros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrosEstados)) {
            return false;
        }
        ParametrosEstados other = (ParametrosEstados) object;
        if ((this.parametros == null && other.parametros != null) || (this.parametros != null && !this.parametros.equals(other.parametros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametrosEstados[ parametro=" + parametros + " ]";
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }
    
}
