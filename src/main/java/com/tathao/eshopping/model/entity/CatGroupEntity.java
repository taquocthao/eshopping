package com.tathao.eshopping.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CatGroup")
public class CatGroupEntity {

    private Long catGroupId;
    private CatGroupEntity parent;
    private String code;
    private String name;
    private String image;
    private String description;
    private Boolean status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Id
    @Column(name = "catGroupId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "catGroupId")
    public CatGroupEntity getParent() {
        return parent;
    }

    public void setParent(CatGroupEntity parent) {
        this.parent = parent;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column(name = "createdDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modifiedDate")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
