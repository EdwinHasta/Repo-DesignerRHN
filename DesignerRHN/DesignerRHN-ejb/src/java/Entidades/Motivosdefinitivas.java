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
@Table(name = "MOTIVOSDEFINITIVAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motivosdefinitivas.findAll", query = "SELECT m FROM Motivosdefinitivas m")})
public class Motivosdefinitivas implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 1)
    @Column(name = "RETIRO")
    private String retiro;
    @Size(max = 1)
    @Column(name = "CAMBIOREGIMEN")
    private String cambioregimen;
    @Size(max = 1)
    @Column(name = "CATEDRATICOSEMESTRAL")
    private String catedraticosemestral;
    @OneToMany(mappedBy = "motivodefinitiva")
    private Collection<NovedadesSistema> novedadessistemaCollection;

    public Motivosdefinitivas() {
    }

    public Motivosdefinitivas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Motivosdefinitivas(BigDecimal secuencia, short codigo, String nombre) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.nombre = nombre;
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
        if(nombre == null){
            nombre = " ";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRetiro() {
        return retiro;
    }

    public void setRetiro(String retiro) {
        this.retiro = retiro;
    }

    public String getCambioregimen() {
        return cambioregimen;
    }

    public void setCambioregimen(String cambioregimen) {
        this.cambioregimen = cambioregimen;
    }

    public String getCatedraticosemestral() {
        return catedraticosemestral;
    }

    public void setCatedraticosemestral(String catedraticosemestral) {
        this.catedraticosemestral = catedraticosemestral;
    }

    @XmlTransient
    public Collection<NovedadesSistema> getNovedadessistemaCollection() {
        return novedadessistemaCollection;
    }

    public void setNovedadessistemaCollection(Collection<NovedadesSistema> novedadessistemaCollection) {
        this.novedadessistemaCollection = novedadessistemaCollection;
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
        if (!(object instanceof Motivosdefinitivas)) {
            return false;
        }
        Motivosdefinitivas other = (Motivosdefinitivas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Motivosdefinitivas[ secuencia=" + secuencia + " ]";
    }
    
}
