/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
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
@Table(name = "MOTIVOSRETIROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivosRetiros.findAll", query = "SELECT m FROM MotivosRetiros m"),
    @NamedQuery(name = "MotivosRetiros.findBySecuencia", query = "SELECT m FROM MotivosRetiros m WHERE m.secuencia = :secuencia"),
    @NamedQuery(name = "MotivosRetiros.findByCodigo", query = "SELECT m FROM MotivosRetiros m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "MotivosRetiros.findByNombre", query = "SELECT m FROM MotivosRetiros m WHERE m.nombre = :nombre")})
public class MotivosRetiros implements Serializable {

    @OneToMany(mappedBy = "motivoretiro")
    private List<HvExperienciasLaborales> hvExperienciasLaboralesList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "motivoretiro")
    private Collection<Retirados> retiradosCollection;
    @JoinColumn(name = "TIPOTRABAJADORRETIRO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposTrabajadores tipotrabajadorretiro;

    public MotivosRetiros() {
    }

    public MotivosRetiros(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public MotivosRetiros(BigInteger secuencia, Integer codigo, String nombre) {
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        if (nombre == null) {
            nombre = " ";
        }

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    @XmlTransient
    public Collection<Retirados> getRetiradosCollection() {
        return retiradosCollection;
    }

    public void setRetiradosCollection(Collection<Retirados> retiradosCollection) {
        this.retiradosCollection = retiradosCollection;
    }

    public TiposTrabajadores getTipotrabajadorretiro() {
        return tipotrabajadorretiro;
    }

    public void setTipotrabajadorretiro(TiposTrabajadores tipotrabajadorretiro) {
        this.tipotrabajadorretiro = tipotrabajadorretiro;
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
        if (!(object instanceof MotivosRetiros)) {
            return false;
        }
        MotivosRetiros other = (MotivosRetiros) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivosretiros[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public List<HvExperienciasLaborales> getHvExperienciasLaboralesList() {
        return hvExperienciasLaboralesList;
    }

    public void setHvExperienciasLaboralesList(List<HvExperienciasLaborales> hvExperienciasLaboralesList) {
        this.hvExperienciasLaboralesList = hvExperienciasLaboralesList;
    }
}
