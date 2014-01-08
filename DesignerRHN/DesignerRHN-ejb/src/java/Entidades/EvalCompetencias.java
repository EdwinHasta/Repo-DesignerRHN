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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "EVALCOMPETENCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvalCompetencias.findAll", query = "SELECT e FROM EvalCompetencias e")})
public class EvalCompetencias implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "DESCOMPETENCIA")
    private String desCompetencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evalcompetencia")
    private Collection<Competenciascargos> competenciascargosCollection;

    public EvalCompetencias() {
    }

    public EvalCompetencias(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EvalCompetencias(BigInteger secuencia, Integer codigo, String descripcion) {
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public String getDesCompetencia() {
        return desCompetencia;
    }

    public void setDesCompetencia(String desCompetencia) {
        this.desCompetencia = desCompetencia.toUpperCase();
    }

    @XmlTransient
    public Collection<Competenciascargos> getCompetenciascargosCollection() {
        return competenciascargosCollection;
    }

    public void setCompetenciascargosCollection(Collection<Competenciascargos> competenciascargosCollection) {
        this.competenciascargosCollection = competenciascargosCollection;
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
        if (!(object instanceof EvalCompetencias)) {
            return false;
        }
        EvalCompetencias other = (EvalCompetencias) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Evalcompetencias[ secuencia=" + secuencia + " ]";
    }
}
