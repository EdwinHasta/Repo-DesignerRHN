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
 * @author Viktor
 */
@Entity
@Table(name = "OTROSCERTIFICADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtrosCertificados.findAll", query = "SELECT o FROM OtrosCertificados o")})
public class OtrosCertificados implements Serializable {
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
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAINICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "VALOR")
    private BigInteger valor;
    @Column(name = "BASELIMITE")
    private BigInteger baselimite;
    @Column(name = "BASEDESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date basedesde;
    @Column(name = "BASEHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date basehasta;
    @Column(name = "DIASCONTRATADOS")
    private BigInteger diascontratados;
    @JoinColumn(name = "TIPOCERTIFICADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposCertificados tipocertificado;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Empleados empleado;

    public OtrosCertificados() {
    }

    public OtrosCertificados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public OtrosCertificados(BigInteger secuencia, String estado, Date fechainicial, Date fechafinal) {
        this.secuencia = secuencia;
        this.estado = estado;
        this.fechainicial = fechainicial;
        this.fechafinal = fechafinal;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public BigInteger getBaselimite() {
        return baselimite;
    }

    public void setBaselimite(BigInteger baselimite) {
        this.baselimite = baselimite;
    }

    public Date getBasedesde() {
        return basedesde;
    }

    public void setBasedesde(Date basedesde) {
        this.basedesde = basedesde;
    }

    public Date getBasehasta() {
        return basehasta;
    }

    public void setBasehasta(Date basehasta) {
        this.basehasta = basehasta;
    }

    public BigInteger getDiascontratados() {
        return diascontratados;
    }

    public void setDiascontratados(BigInteger diascontratados) {
        this.diascontratados = diascontratados;
    }

    public TiposCertificados getTipocertificado() {
        return tipocertificado;
    }

    public void setTipocertificado(TiposCertificados tipocertificado) {
        this.tipocertificado = tipocertificado;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
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
        if (!(object instanceof OtrosCertificados)) {
            return false;
        }
        OtrosCertificados other = (OtrosCertificados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OtrosCertificados[ secuencia=" + secuencia + " ]";
    }
    
}
