package com.example.employees.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmployeeParamMapper {

    private String names;
    private String surnames;
    private List<String> positions;
    private String emails;
    private List<String> cities;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private LocalDate minHireDate;
    private LocalDate maxHireDate;

    public EmployeeParamMapper() {
    }

    public static EmployeeParamMapper getInstance(Map<String, String[]> params) {
        EmployeeParamMapper mapper = new EmployeeParamMapper();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getKey().equals("name")) {
                mapper.setNames(entry.getValue()[0]);
            }
            if (entry.getKey().equals("surname")) {
                mapper.setSurnames(entry.getValue()[0]);
            }
            if (entry.getKey().equals("email")) {
                mapper.setEmails(entry.getValue()[0]);
            }
            if (entry.getKey().equals("position")) {
                mapper.setPositions(Arrays.asList(entry.getValue()));
            }
            if (entry.getKey().equals("city")) {
                mapper.setCities(Arrays.asList(entry.getValue()));
            }
            if (entry.getKey().equals("salary")) {
                mapper.minSalary = new BigDecimal(entry.getValue()[0].split(":")[0]);
                mapper.maxSalary = new BigDecimal(entry.getValue()[0].split(":")[1]);
            }
            if (entry.getKey().equals("hire_date")) {
                mapper.minHireDate = LocalDate.parse(entry.getValue()[0].split(":-:")[0]);
                mapper.maxHireDate = LocalDate.parse(entry.getValue()[0].split(":-:")[1]);
            }
        }
        return mapper;
    }

    public boolean hasNames() {
        return this.names != null;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public boolean hasSurnames() {
        return this.surnames != null;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public List<String> getPositions() {
        return positions;
    }

    public boolean hasPositions() {
        return (this.positions != null && this.positions.size() != 0);
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public boolean hasEmails() {
        return this.emails != null;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public boolean hasCities() {
        return (this.cities != null && this.cities.size() != 0);
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public boolean hasSalary() {
        return (this.maxSalary != null && this.minSalary != null);
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public boolean hasDate(){
        return (this.minHireDate != null && this.maxHireDate != null);
    }
    public LocalDate getMinHireDate() {
        return minHireDate;
    }

    public void setMinHireDate(LocalDate minHireDate) {
        this.minHireDate = minHireDate;
    }

    public LocalDate getMaxHireDate() {
        return maxHireDate;
    }

    public void setMaxHireDate(LocalDate maxHireDate) {
        this.maxHireDate = maxHireDate;
    }
}
