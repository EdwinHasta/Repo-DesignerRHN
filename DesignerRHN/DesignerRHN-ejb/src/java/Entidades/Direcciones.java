/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "DIRECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direcciones.findAll", query = "SELECT d FROM Direcciones d")})
public class Direcciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PPAL")
    private String ppal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPOPPAL")
    private String tipoppal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPOSECUNDARIO")
    private String tiposecundario;
    @Size(max = 15)
    @Column(name = "SECUNDARIO")
    private String secundario;
    @Size(max = 20)
    @Column(name = "INTERIOR")
    private String interior;
    @Size(max = 10)
    @Column(name = "ZONADIR")
    private String zonadir;
    @Size(max = 15)
    @Column(name = "TIPOVIVIENDA")
    private String tipovivienda;
    @Size(max = 15)
    @Column(name = "VIVIENDA")
    private String vivienda;
    @Size(max = 100)
    @Column(name = "DETALLEOTRO")
    private String detalleotro;
    @Size(max = 1)
    @Column(name = "HIPOTECA")
    private String hipoteca;
    @Size(max = 50)
    @Column(name = "ENTIDADHIPOTECA")
    private String entidadhipoteca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechavigencia;
    @Size(max = 100)
    @Column(name = "DIRECCIONALTERNATIVA")
    private String direccionalternativa;
    @JoinColumn(name = "TERCERO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TercerosSucursales tercero;
    @JoinColumn(name = "PERSONA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Personas persona;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Ciudades ciudad;
    @JoinColumn(name = "BANCO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Bancos banco;

    public Direcciones() {
    }

    public Direcciones(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Direcciones(BigDecimal secuencia, String ppal, String tipoppal, String tiposecundario, Date fechavigencia) {
        this.secuencia = secuencia;
        this.ppal = ppal;
        this.tipoppal = tipoppal;
        this.tiposecundario = tiposecundario;
        this.fechavigencia = fechavigencia;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getPpal() {
        return ppal;
    }

    public void setPpal(String ppal) {
        this.ppal = ppal;
    }

    public String getTipoppal() {
        return tipoppal;
    }

    public void setTipoppal(String tipoppal) {
        this.tipoppal = tipoppal;
    }

    public String getTiposecundario() {
        return tiposecundario;
    }

    public void setTiposecundario(String tiposecundario) {
        this.tiposecundario = tiposecundario;
    }

    public String getSecundario() {
        return secundario;
    }

    public void setSecundario(String secundario) {
        this.secundario = secundario;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getZonadir() {
        return zonadir;
    }

    public void setZonadir(String zonadir) {
        this.zonadir = zonadir;
    }

    public String getTipovivienda() {
        return tipovivienda;
    }

    public void setTipovivienda(String tipovivienda) {
        this.tipovivienda = tipovivienda;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getDetalleotro() {
        return detalleotro;
    }

    public void setDetalleotro(String detalleotro) {
        this.detalleotro = detalleotro;
    }

    public String getHipoteca() {
        return hipoteca;
    }

    public void setHipoteca(String hipoteca) {
        this.hipoteca = hipoteca;
    }

    public String getEntidadhipoteca() {
        return entidadhipoteca;
    }

    public void setEntidadhipoteca(String entidadhipoteca) {
        this.entidadhipoteca = entidadhipoteca;
    }

    public Date getFechavigencia() {
        return fechavigencia;
    }

    public void setFechavigencia(Date fechavigencia) {
        this.fechavigencia = fechavigencia;
    }

    public String getDireccionalternativa() {
        return direccionalternativa;
    }

    public void setDireccionalternativa(String direccionalternativa) {
        this.direccionalternativa = direccionalternativa;
    }

    public TercerosSucursales getTercero() {
        return tercero;
    }

    public void setTercero(TercerosSucursales tercero) {
        this.tercero = tercero;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
    }

    public Bancos getBanco() {
        return banco;
    }

    public void setBanco(Bancos banco) {
        this.banco = banco;
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
        if (!(object instanceof Direcciones)) {
            return false;
        }
        Direcciones other = (Direcciones) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Direcciones[ secuencia=" + secuencia + " ]";
    }
    
}
