package by.personal.filmrandomizer.entity;

import javax.persistence.*;

@Entity
@Table(name = "film", schema = "data")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer filmId;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    /**
     * Ссылка на страницу, с которой был выдернут фильм.
     */
    @Column(name = "source_link")
    private String sourceLink;

    /**
     * Ссылка на сам фильм.
     */
    @Column(name = "media_link")
    private String mediaLink;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }
}
