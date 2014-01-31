/*
 * To change this template, choose Tools | Templates
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "REFORMASLABORALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReformasLaborales.findAll", query = "SELECT r FROM ReformasLaborales r"),
    @NamedQuery(name = "ReformasLaborales.findBySecuencia", query = "SELECT r FROM ReformasLaborales r WHERE r.secuencia = :secuencia"),
    @NamedQuery(name = "ReformasLaborales.findByCodigo", query = "SELECT r FROM ReformasLaborales r WHERE r.codigo = :codigo"),
    @NamedQuery(name = "ReformasLaborales.findByNombre", query = "SELECT r FROM ReformasLaborales r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "ReformasLaborales.findByIntegral", query = "SELECT r FROM ReformasLaborales r WHERE r.integral = :integral")})
public class ReformasLaborales implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reformalaboral")
    private Collection<DetallesReformasLaborales> detallesReformasLaboralesCollection;
    @OneToMany(mappedBy = "reformalaboral")
    private Collection<SolucionesNodos> solucionesNodosCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 1)
    @Column(name = "INTEGRAL")
    private String integral;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reformalaboral")
    private Collection<VigenciasReformasLaborales> vigenciasreformaslaboralesCollection;

    public ReformasLaborales() {
    }

    public ReformasLaborales(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ReformasLaborales(BigInteger secuencia, short codigo, String nombre) {
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

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    @XmlTransient
    public Collection<VigenciasReformasLaborales> getVigenciasreformaslaboralesCollection() {
        return vigenciasreformaslaboralesCollection;
    }

    public void setVigenciasreformaslaboralesCollection(Collection<VigenciasReformasLaborales> vigenciasreformaslaboralesCollection) {
        this.vigenciasreformaslaboralesCollection = vigenciasreformaslaboralesCollection;
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
        if (!(object instanceof ReformasLaborales)) {
            return false;
        }
        ReformasLaborales other = (ReformasLaborales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ReformasLaborales[ secuencia=" + secuencia + " ]";
    }

    public Collection<SolucionesNodos> getSolucionesNodosCollection() {
        return solucionesNodosCollection;
    }

    public void setSolucionesNodosCollection(Collection<SolucionesNodos> solucionesNodosCollection) {
        this.solucionesNodosCollection = solucionesNodosCollection;
    }

    @XmlTransient
    public Collection<DetallesReformasLaborales> getDetallesReformasLaboralesCollection() {
        return detallesReformasLaboralesCollection;
    }

    public void setDetallesReformasLaboralesCollection(Collection<DetallesReformasLaborales> detallesReformasLaboralesCollection) {
        this.detallesReformasLaboralesCollection = detallesReformasLaboralesCollection;
    }
    
}
