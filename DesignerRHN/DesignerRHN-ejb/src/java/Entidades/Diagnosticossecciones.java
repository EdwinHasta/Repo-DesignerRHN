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
@Table(name = "DIAGNOSTICOSSECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diagnosticossecciones.findAll", query = "SELECT d FROM Diagnosticossecciones d")})
public class Diagnosticossecciones implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccion")
    private Collection<EnfermeadadesProfesionales> enfermeadadesProfesionalesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccion")
    private Collection<Diagnosticoscategorias> diagnosticoscategoriasCollection;
    @JoinColumn(name = "CAPITULO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Diagnosticoscapitulos capitulo;

    public Diagnosticossecciones() {
    }

    public Diagnosticossecciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Diagnosticossecciones(BigDecimal secuencia, String codigo, String descripcion) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Diagnosticoscategorias> getDiagnosticoscategoriasCollection() {
        return diagnosticoscategoriasCollection;
    }

    public void setDiagnosticoscategoriasCollection(Collection<Diagnosticoscategorias> diagnosticoscategoriasCollection) {
        this.diagnosticoscategoriasCollection = diagnosticoscategoriasCollection;
    }

    public Diagnosticoscapitulos getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Diagnosticoscapitulos capitulo) {
        this.capitulo = capitulo;
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
        if (!(object instanceof Diagnosticossecciones)) {
            return false;
        }
        Diagnosticossecciones other = (Diagnosticossecciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Diagnosticossecciones[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<EnfermeadadesProfesionales> getEnfermeadadesProfesionalesCollection() {
        return enfermeadadesProfesionalesCollection;
    }

    public void setEnfermeadadesProfesionalesCollection(Collection<EnfermeadadesProfesionales> enfermeadadesProfesionalesCollection) {
        this.enfermeadadesProfesionalesCollection = enfermeadadesProfesionalesCollection;
    }
    
}
