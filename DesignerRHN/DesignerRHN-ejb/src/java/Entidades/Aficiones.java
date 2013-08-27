package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "AFICIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aficiones.findAll", query = "SELECT a FROM Aficiones a"),
    @NamedQuery(name = "Aficiones.findBySecuencia", query = "SELECT a FROM Aficiones a WHERE a.secuencia = :secuencia"),
    @NamedQuery(name = "Aficiones.findByCodigo", query = "SELECT a FROM Aficiones a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "Aficiones.findByDescripcion", query = "SELECT a FROM Aficiones a WHERE a.descripcion = :descripcion")})
public class Aficiones implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aficion")
    private Collection<VigenciasAficiones> vigenciasAficionesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Aficiones() {
    }

    public Aficiones(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Aficiones(BigInteger secuencia, short codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Aficiones)) {
            return false;
        }
        Aficiones other = (Aficiones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Aficiones[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<VigenciasAficiones> getVigenciasAficionesCollection() {
        return vigenciasAficionesCollection;
    }

    public void setVigenciasAficionesCollection(Collection<VigenciasAficiones> vigenciasAficionesCollection) {
        this.vigenciasAficionesCollection = vigenciasAficionesCollection;
    }
}
