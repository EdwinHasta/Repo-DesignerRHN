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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "BANCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bancos.findAll", query = "SELECT b FROM Bancos b")})
public class Bancos implements Serializable {
    @OneToMany(mappedBy = "banco")
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
    private short codigo;
    @Size(max = 40)
    @Column(name = "GERENTE")
    private String gerente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 15)
    @Column(name = "CUENTAEMPRESA")
    private String cuentaempresa;
    @Column(name = "CODIGOALTERNATIVO")
    private Short codigoalternativo;
    @Size(max = 1)
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @Size(max = 10)
    @Column(name = "PREFIJOCUENTAS")
    private String prefijocuentas;
    @Size(max = 5)
    @Column(name = "CODIGOCOMPENSACION")
    private String codigocompensacion;
    @Size(max = 15)
    @Column(name = "NUMEROIDENTIFICACIONT")
    private String numeroidentificaciont;
    @OneToMany(mappedBy = "banco")
    private Collection<Direcciones> direccionesCollection;

    public Bancos() {
    }

    public Bancos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Bancos(BigInteger secuencia, short codigo, String nombre) {
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

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
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

    public String getCuentaempresa() {
        return cuentaempresa;
    }

    public void setCuentaempresa(String cuentaempresa) {
        this.cuentaempresa = cuentaempresa;
    }

    public Short getCodigoalternativo() {
        return codigoalternativo;
    }

    public void setCodigoalternativo(Short codigoalternativo) {
        this.codigoalternativo = codigoalternativo;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getPrefijocuentas() {
        return prefijocuentas;
    }

    public void setPrefijocuentas(String prefijocuentas) {
        this.prefijocuentas = prefijocuentas;
    }

    public String getCodigocompensacion() {
        return codigocompensacion;
    }

    public void setCodigocompensacion(String codigocompensacion) {
        this.codigocompensacion = codigocompensacion;
    }

    public String getNumeroidentificaciont() {
        return numeroidentificaciont;
    }

    public void setNumeroidentificaciont(String numeroidentificaciont) {
        this.numeroidentificaciont = numeroidentificaciont;
    }

    @XmlTransient
    public Collection<Direcciones> getDireccionesCollection() {
        return direccionesCollection;
    }

    public void setDireccionesCollection(Collection<Direcciones> direccionesCollection) {
        this.direccionesCollection = direccionesCollection;
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
        if (!(object instanceof Bancos)) {
            return false;
        }
        Bancos other = (Bancos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Bancos[ secuencia=" + secuencia + " ]";
    }

    @XmlTransient
    public Collection<ParametrosInformes> getParametrosInformesCollection() {
        return parametrosInformesCollection;
    }

    public void setParametrosInformesCollection(Collection<ParametrosInformes> parametrosInformesCollection) {
        this.parametrosInformesCollection = parametrosInformesCollection;
    }
    
}
