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
 * @author Administrator
 */
@Entity
@Table(name = "TIPOSENTIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposEntidades.findAll", query = "SELECT t FROM TiposEntidades t")})
public class TiposEntidades implements Serializable {
    @OneToMany(mappedBy = "tipoentidad")
    private Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection;
    @OneToMany(mappedBy = "tipoentidad")
    private Collection<ConceptosSoportes> conceptosSoportesCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private short codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "RUBROPRESUPUESTAL", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Rubrospresupuestales rubropresupuestal;
    @JoinColumn(name = "GRUPO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Grupostiposentidades grupo;

    public TiposEntidades() {
    }

    public TiposEntidades(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposEntidades(BigInteger secuencia, short codigo, String nombre) {
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
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rubrospresupuestales getRubropresupuestal() {
        return rubropresupuestal;
    }

    public void setRubropresupuestal(Rubrospresupuestales rubropresupuestal) {
        this.rubropresupuestal = rubropresupuestal;
    }

    public Grupostiposentidades getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupostiposentidades grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof TiposEntidades)) {
            return false;
        }
        TiposEntidades other = (TiposEntidades) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposentidades[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ConceptosSoportes> getConceptosSoportesCollection() {
        return conceptosSoportesCollection;
    }

    public void setConceptosSoportesCollection(Collection<ConceptosSoportes> conceptosSoportesCollection) {
        this.conceptosSoportesCollection = conceptosSoportesCollection;
    }

    @XmlTransient
    public Collection<IbcsAutoliquidaciones> getIbcsAutoliquidacionesCollection() {
        return ibcsAutoliquidacionesCollection;
    }

    public void setIbcsAutoliquidacionesCollection(Collection<IbcsAutoliquidaciones> ibcsAutoliquidacionesCollection) {
        this.ibcsAutoliquidacionesCollection = ibcsAutoliquidacionesCollection;
    }
    
}
