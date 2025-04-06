package com.example.aurigraph.farmers.DTO;

public class DynamicLocationNodeDTO {
    private String levelName;
    private Long id;
    private String name;
    private String code;
    private DynamicLocationNodeDTO child;


    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DynamicLocationNodeDTO getChild() {
        return child;
    }

    public void setChild(DynamicLocationNodeDTO child) {
        this.child = child;
    }
}
