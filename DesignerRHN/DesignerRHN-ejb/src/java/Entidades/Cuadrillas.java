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
@Table(name = "CUADRILLAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuadrillas.findAll", query = "SELECT c FROM Cuadrillas c"),
    @NamedQuery(name = "Cuadrillas.findBySecuencia", query = "SELECT c FROM Cuadrillas c WHERE c.secuencia = :secuencia"),
    @NamedQuery(name = "Cuadrillas.findByCodigo", query = "SELECT c FROM Cuadrillas c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cuadrillas.findByDescripcion", query = "SELECT c FROM Cuadrillas c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Cuadrillas.findByModulo", query = "SELECT c FROM Cuadrillas c WHERE c.modulo = :modulo"),
    @NamedQuery(name = "Cuadrillas.findByMetodorotacion", query = "SELECT c FROM Cuadrillas c WHERE c.metodorotacion = :metodorotacion"),
    @NamedQuery(name = "Cuadrillas.findByDiasciclo", query = "SELECT c FROM Cuadrillas c WHERE c.diasciclo = :diasciclo"),
    @NamedQuery(name = "Cuadrillas.findByEstado", query = "SELECT c FROM Cuadrillas c WHERE c.estado = :estado")})
public class Cuadrillas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "MODULO")
    private Short modulo;
    @Size(max = 10)
    @Column(name = "METODOROTACION")
    private String metodorotacion;
    @Column(name = "DIASCICLO")
    private Short diasciclo;
    @Size(max = 10)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuadrilla")
    private Collection<Turnosrotativos> turnosrotativosCollection;

    public Cuadrillas() {
    }

    public Cuadrillas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Cuadrillas(BigDecimal secuencia, short codigo, String descripcion) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
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

    public Short getModulo() {
        return modulo;
    }

    public void setModulo(Short modulo) {
        this.modulo = modulo;
    }

    public String getMetodorotacion() {
        return metodorotacion;
    }

    public void setMetodorotacion(String metodorotacion) {
        this.metodorotacion = metodorotacion;
    }

    public Short getDiasciclo() {
        return diasciclo;
    }

    public void setDiasciclo(Short diasciclo) {
        this.diasciclo = diasciclo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Turnosrotativos> getTurnosrotativosCollection() {
        return turnosrotativosCollection;
    }

    public void setTurnosrotativosCollection(Collection<Turnosrotativos> turnosrotativosCollection) {
        this.turnosrotativosCollection = turnosrotativosCollection;
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
        if (!(object instanceof Cuadrillas)) {
            return false;
        }
        Cuadrillas other = (Cuadrillas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cuadrillas[ secuencia=" + secuencia + " ]";
    }
    
}
