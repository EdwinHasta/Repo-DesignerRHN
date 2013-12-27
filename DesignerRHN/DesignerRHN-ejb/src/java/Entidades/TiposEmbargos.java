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
@Table(name = "TIPOSEMBARGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposEmbargos.findAll", query = "SELECT t FROM TiposEmbargos t")})
public class TiposEmbargos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "MANEJASALDO")
    private String manejaSaldo;
    @Transient
    private Boolean manejaSaldoPromedio;

    public TiposEmbargos() {
    }

    public TiposEmbargos(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public TiposEmbargos(BigInteger secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
        if (!(object instanceof TiposEmbargos)) {
            return false;
        }
        TiposEmbargos other = (TiposEmbargos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposembargos[ secuencia=" + secuencia + " ]";
    }

    public String getManejaSaldo() {
        if (manejaSaldo == null) {
            manejaSaldo = "";
        }
        return manejaSaldo;
    }

    public void setManejaSaldo(String manejaSaldo) {
        this.manejaSaldo = manejaSaldo;
    }

    public Boolean getManejaSaldoPromedio() {
        if (manejaSaldoPromedio == null) {
            getManejaSaldo();
            if (manejaSaldo.equals("N")) {
                manejaSaldoPromedio = false;
            } else if (manejaSaldo.equals("S")) {
                manejaSaldoPromedio = true;
            }
        }
        return manejaSaldoPromedio;
    }

    public void setManejaSaldoPromedio(Boolean manejaSaldoPromedio) {
        this.manejaSaldoPromedio = manejaSaldoPromedio;
    }
}
