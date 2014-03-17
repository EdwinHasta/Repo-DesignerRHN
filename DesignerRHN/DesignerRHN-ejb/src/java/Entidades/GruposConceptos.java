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
import javax.persistence.Transient;
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
    @OneToMany(mappedBy = "basegrupo")
    private Collection<DetallesFormasDtos> detallesFormasDtosCollection;
    @OneToMany(mappedBy = "grupodisparador")
    private Collection<DetallesFormasDtos> detallesFormasDtosCollection1;
    @OneToMany(mappedBy = "grupoconcepto")
    private Collection<OperandosGruposConceptos> operandosGruposConceptosCollection;

    @OneToMany(mappedBy = "grupoacumulado")
    private Collection<GruposProvisiones> gruposProvisionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupopago")
    private Collection<GruposProvisiones> gruposProvisionesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoprovision")
    private Collection<GruposProvisiones> gruposProvisionesCollection2;
    @OneToMany(mappedBy = "grupoajusteprovision")
    private Collection<GruposProvisiones> gruposProvisionesCollection3;
    @OneToMany(mappedBy = "grupodefinitiva")
    private Collection<GruposProvisiones> gruposProvisionesCollection4;
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
    @Transient
    private String strCodigo;

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

    public String getStrCodigo() {
        if(codigo>0){
            strCodigo = String.valueOf(codigo);
        }
        else {
            strCodigo = " ";
        }
        return strCodigo;
    }

    public void setStrCodigo(String strCodigo) {
        if(!strCodigo.isEmpty()){
            codigo = Integer.parseInt(strCodigo);
        }
        else {
            codigo = 0;
        }
        this.strCodigo = strCodigo;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
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

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection() {
        return gruposProvisionesCollection;
    }

    public void setGruposProvisionesCollection(Collection<GruposProvisiones> gruposProvisionesCollection) {
        this.gruposProvisionesCollection = gruposProvisionesCollection;
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection1() {
        return gruposProvisionesCollection1;
    }

    public void setGruposProvisionesCollection1(Collection<GruposProvisiones> gruposProvisionesCollection1) {
        this.gruposProvisionesCollection1 = gruposProvisionesCollection1;
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection2() {
        return gruposProvisionesCollection2;
    }

    public void setGruposProvisionesCollection2(Collection<GruposProvisiones> gruposProvisionesCollection2) {
        this.gruposProvisionesCollection2 = gruposProvisionesCollection2;
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection3() {
        return gruposProvisionesCollection3;
    }

    public void setGruposProvisionesCollection3(Collection<GruposProvisiones> gruposProvisionesCollection3) {
        this.gruposProvisionesCollection3 = gruposProvisionesCollection3;
    }

    @XmlTransient
    public Collection<GruposProvisiones> getGruposProvisionesCollection4() {
        return gruposProvisionesCollection4;
    }

    public void setGruposProvisionesCollection4(Collection<GruposProvisiones> gruposProvisionesCollection4) {
        this.gruposProvisionesCollection4 = gruposProvisionesCollection4;
    }

    @XmlTransient
    public Collection<OperandosGruposConceptos> getOperandosGruposConceptosCollection() {
        return operandosGruposConceptosCollection;
    }

    public void setOperandosGruposConceptosCollection(Collection<OperandosGruposConceptos> operandosGruposConceptosCollection) {
        this.operandosGruposConceptosCollection = operandosGruposConceptosCollection;
    }

    @XmlTransient
    public Collection<DetallesFormasDtos> getDetallesFormasDtosCollection() {
        return detallesFormasDtosCollection;
    }

    public void setDetallesFormasDtosCollection(Collection<DetallesFormasDtos> detallesFormasDtosCollection) {
        this.detallesFormasDtosCollection = detallesFormasDtosCollection;
    }

    @XmlTransient
    public Collection<DetallesFormasDtos> getDetallesFormasDtosCollection1() {
        return detallesFormasDtosCollection1;
    }

    public void setDetallesFormasDtosCollection1(Collection<DetallesFormasDtos> detallesFormasDtosCollection1) {
        this.detallesFormasDtosCollection1 = detallesFormasDtosCollection1;
    }

}
