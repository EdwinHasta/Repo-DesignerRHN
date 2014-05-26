/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "EMPRESASBANCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresasBancos.findAll", query = "SELECT e FROM EmpresasBancos e")})
public class EmpresasBancos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "NUMEROCUENTA")
    private String numerocuenta;
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    private Empresas empresa;
    @JoinColumn(name = "BANCO", referencedColumnName = "SECUENCIA")
    private Bancos banco;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "SECUENCIA")
    private Ciudades ciudad;
    @Transient
    private String trTipoCuenta;

    public EmpresasBancos() {
    }

    public EmpresasBancos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getNumerocuenta() {
        if (numerocuenta == null) {
            numerocuenta = " ";
        }
        return numerocuenta;
    }

    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        //System.out.println("ENTIDAD EMPRESASBANCOS SET TIPOCUENTA " + tipocuenta);
        this.tipocuenta = tipocuenta;
    }

    public String getTrTipoCuenta() {
        getTipocuenta();
        if (tipocuenta.equalsIgnoreCase("c")) {
            trTipoCuenta = "Corriente";
        }
        if (tipocuenta.equalsIgnoreCase("a")) {
            trTipoCuenta = "Ahorros";
        }
        System.out.println("trTipoCuenta : " + trTipoCuenta);
        return trTipoCuenta;
    }

    public void setTrTipoCuenta(String trTipoCuenta) {
        this.trTipoCuenta = trTipoCuenta;
        if (this.trTipoCuenta.equalsIgnoreCase("ahorro")) {
            System.out.println("EmpresasBancos setTrTipoCuenta a ");
            setTipocuenta("a");
        }
        if (this.trTipoCuenta.equalsIgnoreCase("corriente")) {
            System.out.println("EmpresasBancos setTrTipoCuenta c ");
            setTipocuenta("c");
        }
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Bancos getBanco() {
        return banco;
    }

    public void setBanco(Bancos banco) {
        this.banco = banco;
    }

    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
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
        if (!(object instanceof EmpresasBancos)) {
            return false;
        }
        EmpresasBancos other = (EmpresasBancos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EmpresasBancos[ secuencia=" + secuencia + " ]";
    }

}
