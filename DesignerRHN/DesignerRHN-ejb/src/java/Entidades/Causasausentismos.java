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
@Table(name = "CAUSASAUSENTISMOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Causasausentismos.findAll", query = "SELECT c FROM Causasausentismos c")})
public class Causasausentismos implements Serializable {
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
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "DESCUENTATIEMPOCONTINUO")
    private String descuentatiempocontinuo;
    @Size(max = 3)
    @Column(name = "ORIGENINCAPACIDAD")
    private String origenincapacidad;
    @Size(max = 1)
    @Column(name = "REMUNERADA")
    private String remunerada;
    @Column(name = "PORCENTAJELIQUIDACION")
    private BigDecimal porcentajeliquidacion;
    @Column(name = "RESTADIASINCAPACIDAD")
    private Short restadiasincapacidad;
    @Size(max = 50)
    @Column(name = "FORMALIQUIDACION")
    private String formaliquidacion;
    @Size(max = 1)
    @Column(name = "RESTATIEMPOSOLOPRESTACIONES")
    private String restatiemposoloprestaciones;
    @Size(max = 1)
    @Column(name = "GARANTIZABASESML")
    private String garantizabasesml;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causa")
    private Collection<Soausentismos> soausentismosCollection;
    @OneToMany(mappedBy = "causaausentismo")
    private Collection<NovedadesSistema> novedadessistemaCollection;
    @JoinColumn(name = "CLASE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Clasesausentismos clase;

    public Causasausentismos() {
    }

    public Causasausentismos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Causasausentismos(BigDecimal secuencia, short codigo) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescuentatiempocontinuo() {
        return descuentatiempocontinuo;
    }

    public void setDescuentatiempocontinuo(String descuentatiempocontinuo) {
        this.descuentatiempocontinuo = descuentatiempocontinuo;
    }

    public String getOrigenincapacidad() {
        return origenincapacidad;
    }

    public void setOrigenincapacidad(String origenincapacidad) {
        this.origenincapacidad = origenincapacidad;
    }

    public String getRemunerada() {
        return remunerada;
    }

    public void setRemunerada(String remunerada) {
        this.remunerada = remunerada;
    }

    public BigDecimal getPorcentajeliquidacion() {
        return porcentajeliquidacion;
    }

    public void setPorcentajeliquidacion(BigDecimal porcentajeliquidacion) {
        this.porcentajeliquidacion = porcentajeliquidacion;
    }

    public Short getRestadiasincapacidad() {
        return restadiasincapacidad;
    }

    public void setRestadiasincapacidad(Short restadiasincapacidad) {
        this.restadiasincapacidad = restadiasincapacidad;
    }

    public String getFormaliquidacion() {
        return formaliquidacion;
    }

    public void setFormaliquidacion(String formaliquidacion) {
        this.formaliquidacion = formaliquidacion;
    }

    public String getRestatiemposoloprestaciones() {
        return restatiemposoloprestaciones;
    }

    public void setRestatiemposoloprestaciones(String restatiemposoloprestaciones) {
        this.restatiemposoloprestaciones = restatiemposoloprestaciones;
    }

    public String getGarantizabasesml() {
        return garantizabasesml;
    }

    public void setGarantizabasesml(String garantizabasesml) {
        this.garantizabasesml = garantizabasesml;
    }

    @XmlTransient
    public Collection<Soausentismos> getSoausentismosCollection() {
        return soausentismosCollection;
    }

    public void setSoausentismosCollection(Collection<Soausentismos> soausentismosCollection) {
        this.soausentismosCollection = soausentismosCollection;
    }

    @XmlTransient
    public Collection<NovedadesSistema> getNovedadessistemaCollection() {
        return novedadessistemaCollection;
    }

    public void setNovedadessistemaCollection(Collection<NovedadesSistema> novedadessistemaCollection) {
        this.novedadessistemaCollection = novedadessistemaCollection;
    }

    public Clasesausentismos getClase() {
        return clase;
    }

    public void setClase(Clasesausentismos clase) {
        this.clase = clase;
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
        if (!(object instanceof Causasausentismos)) {
            return false;
        }
        Causasausentismos other = (Causasausentismos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Causasausentismos[ secuencia=" + secuencia + " ]";
    }
    
}
