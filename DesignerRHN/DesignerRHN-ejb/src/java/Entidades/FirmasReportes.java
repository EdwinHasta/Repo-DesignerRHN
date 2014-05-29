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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FIRMASREPORTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FirmasReportes.findAll", query = "SELECT f FROM FirmasReportes f")})
public class FirmasReportes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "SUBTITULOFIRMA")
    private String subtitulofirma;
    @JoinColumn(name = "EMPRESA", referencedColumnName = "SECUENCIA")
    private Empresas empresa;
    @JoinColumn(name = "PERSONAFIRMA", referencedColumnName = "SECUENCIA")
    private Personas personaFirma;
    @JoinColumn(name = "CARGOFIRMA", referencedColumnName = "SECUENCIA")
    private Cargos cargo;

    public FirmasReportes() {
    }

    public FirmasReportes(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public FirmasReportes(BigInteger secuencia, Integer codigo, String descripcion, String subtitulofirma) {
        this.secuencia = secuencia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.subtitulofirma = subtitulofirma;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSubtitulofirma() {
        return subtitulofirma;
    }

    public void setSubtitulofirma(String subtitulofirma) {
        this.subtitulofirma = subtitulofirma;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Personas getPersonaFirma() {
        return personaFirma;
    }

    public void setPersonaFirma(Personas personaFirma) {
        this.personaFirma = personaFirma;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof FirmasReportes)) {
            return false;
        }
        FirmasReportes other = (FirmasReportes) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FirmasReportes[ secuencia=" + secuencia + " ]";
    }

}
