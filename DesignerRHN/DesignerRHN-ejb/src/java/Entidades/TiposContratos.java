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
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSCONTRATOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposContratos.findAll", query = "SELECT t FROM TiposContratos t")})
public class TiposContratos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocontrato")
    private Collection<VigenciasConceptosTC> vigenciasconceptostcCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocontrato")
    private Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection;
    @OneToMany(mappedBy = "tipocontrato")
    private Collection<Solucionesnodos> solucionesnodosCollection;
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
    @Column(name = "DURACIONPERIODOPRUEBA")
    private Short duracionperiodoprueba;
    @Size(max = 1)
    @Column(name = "VINCULACIONEMPRESA")
    private String vinculacionempresa;
    @Size(max = 1)
    @Column(name = "FORZACOTIZACIONPILA30DIAS")
    private String forzacotizacionpila30dias;

    public TiposContratos() {
    }

    public TiposContratos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposContratos(BigInteger secuencia, short codigo, String nombre) {
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
        if (nombre == null) {
            nombre = " ";
            return nombre;
        } else {
            return nombre;
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getDuracionperiodoprueba() {
        return duracionperiodoprueba;
    }

    public void setDuracionperiodoprueba(Short duracionperiodoprueba) {
        this.duracionperiodoprueba = duracionperiodoprueba;
    }

    public String getVinculacionempresa() {
        return vinculacionempresa;
    }

    public void setVinculacionempresa(String vinculacionempresa) {
        this.vinculacionempresa = vinculacionempresa;
    }

    public String getForzacotizacionpila30dias() {
        return forzacotizacionpila30dias;
    }

    public void setForzacotizacionpila30dias(String forzacotizacionpila30dias) {
        this.forzacotizacionpila30dias = forzacotizacionpila30dias;
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
        if (!(object instanceof TiposContratos)) {
            return false;
        }
        TiposContratos other = (TiposContratos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposcontratos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<Solucionesnodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    public Collection<VigenciasTiposContratos> getVigenciastiposcontratosCollection() {
        return vigenciastiposcontratosCollection;
    }

    public void setVigenciastiposcontratosCollection(Collection<VigenciasTiposContratos> vigenciastiposcontratosCollection) {
        this.vigenciastiposcontratosCollection = vigenciastiposcontratosCollection;
    }

    public Collection<VigenciasConceptosTC> getVigenciasconceptostcCollection() {
        return vigenciasconceptostcCollection;
    }

    public void setVigenciasconceptostcCollection(Collection<VigenciasConceptosTC> vigenciasconceptostcCollection) {
        this.vigenciasconceptostcCollection = vigenciasconceptostcCollection;
    }
}
