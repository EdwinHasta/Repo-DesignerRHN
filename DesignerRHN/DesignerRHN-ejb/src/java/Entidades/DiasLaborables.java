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
 * @author PROYECTO01
 */
@Entity
@Table(name = "DIASLABORABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiasLaborables.findAll", query = "SELECT d FROM DiasLaborables d"),
    @NamedQuery(name = "DiasLaborables.findBySecuencia", query = "SELECT d FROM DiasLaborables d WHERE d.secuencia = :secuencia"),
    @NamedQuery(name = "DiasLaborables.findByDia", query = "SELECT d FROM DiasLaborables d WHERE d.dia = :dia"),
    @NamedQuery(name = "DiasLaborables.findByHoraslaborables", query = "SELECT d FROM DiasLaborables d WHERE d.horaslaborables = :horaslaborables")})
public class DiasLaborables implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "DIA")
    private String dia;
    @Column(name = "HORASLABORABLES")
    private BigInteger horaslaborables;
    @JoinColumn(name = "TIPODIA", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposDias tipodia;
    @JoinColumn(name = "TIPOCONTRATO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private TiposContratos tipocontrato;
    @Transient
    private String strDia;

    public DiasLaborables() {
    }

    public DiasLaborables(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public DiasLaborables(BigInteger secuencia, String dia) {
        this.secuencia = secuencia;
        this.dia = dia;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getStrDia() {
        getDia();
        if (dia != null) {
            if (dia.equalsIgnoreCase("ALL")) {
                strDia = "TODOS";
            } else if (dia.equalsIgnoreCase("LUN")) {
                strDia = "LUNES";
            } else if (dia.equalsIgnoreCase("MAR")) {
                strDia = "MARTES";
            } else if (dia.equalsIgnoreCase("MIE")) {
                strDia = "MIERCOLES";
            } else if (dia.equalsIgnoreCase("JUE")) {
                strDia = "JUEVES";
            } else if (dia.equalsIgnoreCase("VIE")) {
                strDia = "VIERNES";
            } else if (dia.equalsIgnoreCase("SAB")) {
                strDia = "SABADO";
            } else if (dia.equalsIgnoreCase("DOM")) {
                strDia = "DOMINGO";
            } else {
                strDia = " ";
            }
        } else {
            strDia = " ";
        }
        return strDia;
    }

    public void setStrDia(String strDia) {
        this.strDia = strDia;
    }

    public BigInteger getHoraslaborables() {
        return horaslaborables;
    }

    public void setHoraslaborables(BigInteger horaslaborables) {
        this.horaslaborables = horaslaborables;
    }

    public TiposDias getTipodia() {
        return tipodia;
    }

    public void setTipodia(TiposDias tipodia) {
        this.tipodia = tipodia;
    }

    public TiposContratos getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(TiposContratos tipocontrato) {
        this.tipocontrato = tipocontrato;
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
        if (!(object instanceof DiasLaborables)) {
            return false;
        }
        DiasLaborables other = (DiasLaborables) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.DiasLaborables[ secuencia=" + secuencia + " ]";
    }

}
