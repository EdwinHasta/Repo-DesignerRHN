/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "PERIODICIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodicidades.findAll", query = "SELECT p FROM Periodicidades p")})
public class Periodicidades implements Serializable {
    @OneToMany(mappedBy = "periodicidad")
    private Collection<Novedades> novedadesCollection;
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
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 1)
    @Column(name = "INDEPENDIENTEADELANTO")
    private String independienteadelanto;
    @JoinColumn(name = "UNIDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Unidades unidad;
    @JoinColumn(name = "UNIDADBASE", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Unidades unidadbase;
    @OneToMany(mappedBy = "minimaperiodicidad")
    private Collection<Empresas> empresasCollection;

    public Periodicidades() {
    }

    public Periodicidades(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Periodicidades(BigDecimal secuencia, short codigo) {
        this.secuencia = secuencia;
        this.codigo = codigo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndependienteadelanto() {
        return independienteadelanto;
    }

    public void setIndependienteadelanto(String independienteadelanto) {
        this.independienteadelanto = independienteadelanto;
    }

    public Unidades getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidades unidad) {
        this.unidad = unidad;
    }

    public Unidades getUnidadbase() {
        return unidadbase;
    }

    public void setUnidadbase(Unidades unidadbase) {
        this.unidadbase = unidadbase;
    }

    @XmlTransient
    public Collection<Empresas> getEmpresasCollection() {
        return empresasCollection;
    }

    public void setEmpresasCollection(Collection<Empresas> empresasCollection) {
        this.empresasCollection = empresasCollection;
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
        if (!(object instanceof Periodicidades)) {
            return false;
        }
        Periodicidades other = (Periodicidades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Periodicidades[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }
    
}
