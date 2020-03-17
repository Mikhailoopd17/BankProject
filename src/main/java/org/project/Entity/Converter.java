package org.project.Entity;

public class Converter {
    private Scope scope1;
    private Scope scope2;

    public Converter(Scope scope1, Scope scope2) {
        this.scope1 = scope1;
        this.scope2 = scope2;
    }

    public boolean isConverted(){
        return !this.scope1.getCurrency().equals(this.scope2.getCurrency());
    }

    public Float toConverted(Float sum){
        Float COURSE = 74.38f;
        if(scope1.getCurrency().toLowerCase().contains("руб"))
            return sum/ COURSE;
        else if(scope1.getCurrency().toLowerCase().contains("дол"))
            return sum* COURSE;
        else
            return sum;
    }

}
