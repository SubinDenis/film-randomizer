package by.personal.filmrandomizer.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "film", schema = "data")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer filmId;

    @Column(name = "name")
    private String name;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_tag",
            schema = "data",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
