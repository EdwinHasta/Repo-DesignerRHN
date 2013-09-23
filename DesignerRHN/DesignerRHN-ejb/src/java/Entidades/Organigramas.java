/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ORGANIGRAMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organigramas.findAll", query = "SELECT o FROM Organigramas o")})
public class Organigramas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organigrama")
    private Collection<Estructuras> estructurasCollection;
    @Transient
    private String estadoOrg;
    

    public Organigramas() {
    }

    public Organigramas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Organigramas(BigInteger secuencia, String estado) {
        this.secuencia = secuencia;
        this.estado = estado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public String getEstadoOrg() {
        if (estadoOrg == null) {
            if (estado == null) {
                estadoOrg = "ACTIVO";
            } else {
                if (estado.equalsIgnoreCase("A")) {
                    estadoOrg = "ACTIVO";
                } else if (estado.equalsIgnoreCase("I")) {
                    estadoOrg = "INACTIVO";
                }
            }
        }
        return estadoOrg;
    }

    public void setEstadoOrg(String estadoOrg) {
        this.estadoOrg = estadoOrg;
    }

    @XmlTransient
    public Collection<Estructuras> getEstructurasCollection() {
        return estructurasCollection;
    }

    public void setEstructurasCollection(Collection<Estructuras> estructurasCollection) {
        this.estructurasCollection = estructurasCollection;
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
        if (!(object instanceof Organigramas)) {
            return false;
        }
        Organigramas other = (Organigramas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Organigramas[ secuencia=" + secuencia + " ]";
    }
}
