/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "VIGENCIASDEPORTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VigenciasDeportes.findAll", query = "SELECT v FROM VigenciasDeportes v")})
public class VigenciasDeportes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "VALORCUANTITATIVO")
    private BigInteger valorcuantitativo;
    @Size(max = 50)
    @Column(name = "VALORCUALITATIVO")
    private String valorcualitativo;
    @Column(name = "VALORCUANTITATIVOGRUPO")
    private BigInteger valorcuantitativogrupo;
    @Size(max = 50)
    @Column(name = "VALORCUALITATIVOGRUPO")
    private String valorcualitativogrupo;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Personas persona;
    @JoinColumn(name = "DEPORTE", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Deportes deporte;

    public VigenciasDeportes() {
    }

    public VigenciasDeportes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public VigenciasDeportes(BigInteger secuencia, Date fechainicial) {
        this.secuencia = secuencia;
        this.fechainicial = fechainicial;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public BigInteger getValorcuantitativo() {
        return valorcuantitativo;
    }

    public void setValorcuantitativo(BigInteger valorcuantitativo) {
        this.valorcuantitativo = valorcuantitativo;
    }

    public String getValorcualitativo() {
        return valorcualitativo;
    }

    public void setValorcualitativo(String valorcualitativo) {
        this.valorcualitativo = valorcualitativo;
    }

    public BigInteger getValorcuantitativogrupo() {
        return valorcuantitativogrupo;
    }

    public void setValorcuantitativogrupo(BigInteger valorcuantitativogrupo) {
        this.valorcuantitativogrupo = valorcuantitativogrupo;
    }

    public String getValorcualitativogrupo() {
        return valorcualitativogrupo;
    }

    public void setValorcualitativogrupo(String valorcualitativogrupo) {
        this.valorcualitativogrupo = valorcualitativogrupo;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Deportes getDeporte() {
        return deporte;
    }

    public void setDeporte(Deportes deporte) {
        this.deporte = deporte;
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
        if (!(object instanceof VigenciasDeportes)) {
            return false;
        }
        VigenciasDeportes other = (VigenciasDeportes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VigenciasDeportes[ secuencia=" + secuencia + " ]";
    }
    
}
