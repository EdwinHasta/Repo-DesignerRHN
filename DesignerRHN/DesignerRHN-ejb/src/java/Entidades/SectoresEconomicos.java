/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author user
 */
@Entity
@Table(name = "SECTORESECONOMICOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SectoresEconomicos.findAll", query = "SELECT s FROM SectoresEconomicos s"),
    @NamedQuery(name = "SectoresEconomicos.findBySecuencia", query = "SELECT s FROM SectoresEconomicos s WHERE s.secuencia = :secuencia"),
    @NamedQuery(name = "SectoresEconomicos.findByCodigo", query = "SELECT s FROM SectoresEconomicos s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SectoresEconomicos.findByDescripcion", query = "SELECT s FROM SectoresEconomicos s WHERE s.descripcion = :descripcion")})
public class SectoresEconomicos implements Serializable {
    @OneToMany(mappedBy = "sectoreconomico")
    private Collection<HvExperienciasLaborales> hvExperienciasLaboralesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empresas empresa;

    public SectoresEconomicos() {
    }

    public SectoresEconomicos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public SectoresEconomicos(BigInteger secuencia, String codigo, String descripcion) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if(descripcion == null){
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof SectoresEconomicos)) {
            return false;
        }
        SectoresEconomicos other = (SectoresEconomicos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.SectoresEconomicos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<HvExperienciasLaborales> getHvExperienciasLaboralesCollection() {
        return hvExperienciasLaboralesCollection;
    }

    public void setHvExperienciasLaboralesCollection(Collection<HvExperienciasLaborales> hvExperienciasLaboralesCollection) {
        this.hvExperienciasLaboralesCollection = hvExperienciasLaboralesCollection;
    }
    
}
