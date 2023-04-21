package com.example.auto_warehouse.bean;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rule {
    @Id
    @Basic
    @Column(name = "export", nullable = false, length = 255)
    private String export;
    @Basic
    @Column(name = "dispatch", nullable = false, length = 255)
    private String dispatch;

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (export != null ? !export.equals(rule.export) : rule.export != null) return false;
        if (dispatch != null ? !dispatch.equals(rule.dispatch) : rule.dispatch != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = export != null ? export.hashCode() : 0;
        result = 31 * result + (dispatch != null ? dispatch.hashCode() : 0);
        return result;
    }

}
