package com.helmes.proovitoo.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Data {

    @NotEmpty(message = "Name is mandatory")
    String name;
    @NotNull(message = "Selection of sectors is mandatory.")
    List<String> selectedSectors;
    @NotNull
    @AssertTrue(message = "Selection of checkbox is mandatory.")
    boolean agreedToTerms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSelectedSectors() {
        return selectedSectors;
    }

    public void setSelectedSectors(List<String> selectedSectors) {
        this.selectedSectors = selectedSectors;
    }

    public boolean isAgreedToTerms() {
        return agreedToTerms;
    }

    public void setAgreedToTerms(boolean agreedToTerms) {
        this.agreedToTerms = agreedToTerms;
    }

}
