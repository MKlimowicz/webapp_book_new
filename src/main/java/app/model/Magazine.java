package app.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@DiscriminatorValue("magazine")
public class Magazine extends Publication  {


    private LocalDate timePublication;
    private String language;


    public LocalDate getTimePublication() {
        return timePublication;
    }

    public void setTimePublication(LocalDate timePublication) {
        this.timePublication = timePublication;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Magazine magazine = (Magazine) o;
        return Objects.equals(timePublication, magazine.timePublication) &&
                Objects.equals(language, magazine.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timePublication, language);
    }
}
