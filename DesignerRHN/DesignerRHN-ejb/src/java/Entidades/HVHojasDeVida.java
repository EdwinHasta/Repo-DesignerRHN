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
import javax.persistence.OneToOne;
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
@Table(name = "HVHOJASDEVIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HVHojasDeVida.findAll", query = "SELECT h FROM HVHojasDeVida h")})
public class HVHojasDeVida implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hojadevida")
    private Collection<HvReferencias> hvReferenciasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hojadevida")
    private Collection<HvExperienciasLaborales> hvExperienciasLaboralesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hojadevida")
    private Collection<HvEntrevistas> hvEntrevistasCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Size(max = 1000)
    @Column(name = "PERFILPROFESIONAL")
    private String perfilprofesional;
    @Column(name = "HIJOS")
    private Short hijos;
    @Size(max = 1000)
    @Column(name = "INFORMEEJECUTIVO")
    private String informeejecutivo;
    @Size(max = 10)
    @Column(name = "CONCLUSION")
    private String conclusion;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @OneToOne(optional = false)
    private Personas persona;
    @JoinColumn(name = "ESTADOCIVIL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EstadosCiviles estadocivil;
    @JoinColumn(name = "CARGO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Cargos cargo;

    public HVHojasDeVida() {
    }

    public HVHojasDeVida(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getPerfilprofesional() {
        return perfilprofesional;
    }

    public void setPerfilprofesional(String perfilprofesional) {
        this.perfilprofesional = perfilprofesional;
    }

    public Short getHijos() {
        return hijos;
    }

    public void setHijos(Short hijos) {
        this.hijos = hijos;
    }

    public String getInformeejecutivo() {
        return informeejecutivo;
    }

    public void setInformeejecutivo(String informeejecutivo) {
        this.informeejecutivo = informeejecutivo;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public EstadosCiviles getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(EstadosCiviles estadocivil) {
        this.estadocivil = estadocivil;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof HVHojasDeVida)) {
            return false;
        }
        HVHojasDeVida other = (HVHojasDeVida) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HVHojasDeVida[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<HvReferencias> getHvReferenciasCollection() {
        return hvReferenciasCollection;
    }

    public void setHvReferenciasCollection(Collection<HvReferencias> hvReferenciasCollection) {
        this.hvReferenciasCollection = hvReferenciasCollection;
    }

    @XmlTransient
    public Collection<HvExperienciasLaborales> getHvExperienciasLaboralesCollection() {
        return hvExperienciasLaboralesCollection;
    }

    public void setHvExperienciasLaboralesCollection(Collection<HvExperienciasLaborales> hvExperienciasLaboralesCollection) {
        this.hvExperienciasLaboralesCollection = hvExperienciasLaboralesCollection;
    }

    @XmlTransient
    public Collection<HvEntrevistas> getHvEntrevistasCollection() {
        return hvEntrevistasCollection;
    }

    public void setHvEntrevistasCollection(Collection<HvEntrevistas> hvEntrevistasCollection) {
        this.hvEntrevistasCollection = hvEntrevistasCollection;
    }
    
}
