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
@Table(name = "DIAGNOSTICOSCATEGORIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diagnosticoscategorias.findAll", query = "SELECT d FROM Diagnosticoscategorias d")})
public class Diagnosticoscategorias implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
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
    @JoinColumn(name = "SECCION", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Diagnosticossecciones seccion;
    @OneToMany(mappedBy = "diagnosticocategoria")
    private Collection<Soausentismos> soausentismosCollection;

    public Diagnosticoscategorias() {
    }

    public Diagnosticoscategorias(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Diagnosticoscategorias(BigDecimal secuencia, String codigo, String descripcion) {
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
        if(descripcion == null){
            descripcion = (" ");
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Diagnosticossecciones getSeccion() {
        return seccion;
    }

    public void setSeccion(Diagnosticossecciones seccion) {
        this.seccion = seccion;
    }

    @XmlTransient
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
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
        if (!(object instanceof Diagnosticoscategorias)) {
            return false;
        }
        Diagnosticoscategorias other = (Diagnosticoscategorias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Diagnosticoscategorias[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<EnfermeadadesProfesionales> getEnfermeadadesProfesionalesCollection() {
        return enfermeadadesProfesionalesCollection;
    }

    public void setEnfermeadadesProfesionalesCollection(Collection<EnfermeadadesProfesionales> enfermeadadesProfesionalesCollection) {
        this.enfermeadadesProfesionalesCollection = enfermeadadesProfesionalesCollection;
    }
    
}
