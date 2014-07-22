/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "PARAMETROSAUTOLIQ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosAutoliq.findAll", query = "SELECT p FROM ParametrosAutoliq p"),
    @NamedQuery(name = "ParametrosAutoliq.findBySecuencia", query = "SELECT p FROM ParametrosAutoliq p WHERE p.secuencia = :secuencia"),
    @NamedQuery(name = "ParametrosAutoliq.findByAno", query = "SELECT p FROM ParametrosAutoliq p WHERE p.ano = :ano"),
    @NamedQuery(name = "ParametrosAutoliq.findByMes", query = "SELECT p FROM ParametrosAutoliq p WHERE p.mes = :mes"),
    @NamedQuery(name = "ParametrosAutoliq.findByPagoactualmesanterior", query = "SELECT p FROM ParametrosAutoliq p WHERE p.pagoactualmesanterior = :pagoactualmesanterior")})
public class ParametrosAutoliq implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANO")
    private short ano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MES")
    private short mes;
    @Size(max = 1)
    @Column(name = "PAGOACTUALMESANTERIOR")
    private String pagoactualmesanterior;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposTrabajadores tipotrabajador;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Empresas empresa;
    @Transient
    private String strMes;

    public ParametrosAutoliq() {
    }

    public ParametrosAutoliq(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public ParametrosAutoliq(BigInteger secuencia, short ano, short mes) {
        this.secuencia = secuencia;
        this.ano = ano;
        this.mes = mes;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(short ano) {
        this.ano = ano;
    }

    public short getMes() {
        return mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public String getPagoactualmesanterior() {
        return pagoactualmesanterior;
    }

    public void setPagoactualmesanterior(String pagoactualmesanterior) {
        this.pagoactualmesanterior = pagoactualmesanterior;
    }

    public TiposTrabajadores getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(TiposTrabajadores tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public String getStrMes() {
        getMes();
        if (mes == 1) {
            strMes = "ENERO";
        }
        if (mes == 2) {
            strMes = "FEBRERO";
        }
        if (mes == 3) {
            strMes = "MARZO";
        }
        if (mes == 4) {
            strMes = "ABRIL";
        }
        if (mes == 5) {
            strMes = "MAYO";
        }
        if (mes == 6) {
            strMes = "JUNIO";
        }
        if (mes == 7) {
            strMes = "JULIO";
        }
        if (mes == 8) {
            strMes = "AGOSTO";
        }
        if (mes == 9) {
            strMes = "SEPTIEMBRE";
        }
        if (mes == 10) {
            strMes = "OCTUBRE";
        }
        if (mes == 11) {
            strMes = "NOVIEMBRE";
        }
        if (mes == 12) {
            strMes = "DICIEMBRE";
        }

        return strMes;
    }

    public void setStrMes(String strMes) {
        this.strMes = strMes;
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
        if (!(object instanceof ParametrosAutoliq)) {
            return false;
        }
        ParametrosAutoliq other = (ParametrosAutoliq) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ParametrosAutoliq[ secuencia=" + secuencia + " ]";
    }

}
