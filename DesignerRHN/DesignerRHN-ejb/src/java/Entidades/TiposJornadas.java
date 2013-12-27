/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author PROYECTO01
 */
@Entity
@Table(name = "TIPOSJORNADAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposJornadas.findAll", query = "SELECT t FROM TiposJornadas t"),
    @NamedQuery(name = "TiposJornadas.findBySecuencia", query = "SELECT t FROM TiposJornadas t WHERE t.secuencia = :secuencia"),
    @NamedQuery(name = "TiposJornadas.findByDescripcion", query = "SELECT t FROM TiposJornadas t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposJornadas.findByCodigo", query = "SELECT t FROM TiposJornadas t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TiposJornadas.findByMinutoinicial", query = "SELECT t FROM TiposJornadas t WHERE t.minutoinicial = :minutoinicial"),
    @NamedQuery(name = "TiposJornadas.findByHorafinal", query = "SELECT t FROM TiposJornadas t WHERE t.horafinal = :horafinal"),
    @NamedQuery(name = "TiposJornadas.findByMinutofinal", query = "SELECT t FROM TiposJornadas t WHERE t.minutofinal = :minutofinal"),
    @NamedQuery(name = "TiposJornadas.findByHorainicial", query = "SELECT t FROM TiposJornadas t WHERE t.horainicial = :horainicial")})
public class TiposJornadas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MINUTOINICIAL")
    private short minutoinicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORAFINAL")
    private short horafinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MINUTOFINAL")
    private short minutofinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORAINICIAL")
    private short horainicial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipojornada")
    private List<ExtrasRecargos> extrasRecargosList;

    public TiposJornadas() {
    }

    public TiposJornadas(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public TiposJornadas(BigDecimal secuencia, String descripcion, short minutoinicial, short horafinal, short minutofinal, short horainicial) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
        this.minutoinicial = minutoinicial;
        this.horafinal = horafinal;
        this.minutofinal = minutofinal;
        this.horainicial = horainicial;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public short getMinutoinicial() {
        return minutoinicial;
    }

    public void setMinutoinicial(short minutoinicial) {
        this.minutoinicial = minutoinicial;
    }

    public short getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(short horafinal) {
        this.horafinal = horafinal;
    }

    public short getMinutofinal() {
        return minutofinal;
    }

    public void setMinutofinal(short minutofinal) {
        this.minutofinal = minutofinal;
    }

    public short getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(short horainicial) {
        this.horainicial = horainicial;
    }

    @XmlTransient
    public List<ExtrasRecargos> getExtrasRecargosList() {
        return extrasRecargosList;
    }

    public void setExtrasRecargosList(List<ExtrasRecargos> extrasRecargosList) {
        this.extrasRecargosList = extrasRecargosList;
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
        if (!(object instanceof TiposJornadas)) {
            return false;
        }
        TiposJornadas other = (TiposJornadas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tiposjornadas[ secuencia=" + secuencia + " ]";
    }

}
