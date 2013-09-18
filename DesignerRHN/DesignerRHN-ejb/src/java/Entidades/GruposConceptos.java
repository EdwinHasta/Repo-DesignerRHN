/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "GRUPOSCONCEPTOS")
@NamedQueries({
    @NamedQuery(name = "GruposConceptos.findAll", query = "SELECT g FROM GruposConceptos g")})
public class GruposConceptos implements Serializable {
    @OneToMany(mappedBy = "grupo")
    private Collection<ParametrosInformes> parametrosInformesCollection;
    
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
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "FUNDAMENTAL")
    private String fundamental;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoConcepto")
    private Collection<VigenciasGruposConceptos> vigenciasgruposconceptosCollection;*/

    public GruposConceptos() {
    }

    public GruposConceptos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public GruposConceptos(BigInteger secuencia, int codigo, String descripcion) {
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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if(descripcion == null){
            descripcion =  " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFundamental() {
        return fundamental;
    }

    public void setFundamental(String fundamental) {
        this.fundamental = fundamental;
    }
/*
    public Collection<VigenciasGruposConceptos> getVigenciasgruposconceptosCollection() {
        return vigenciasgruposconceptosCollection;
    }

    public void setVigenciasgruposconceptosCollection(Collection<VigenciasGruposConceptos> vigenciasgruposconceptosCollection) {
        this.vigenciasgruposconceptosCollection = vigenciasgruposconceptosCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposConceptos)) {
            return false;
        }
        GruposConceptos other = (GruposConceptos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Gruposconceptos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }
    
}
