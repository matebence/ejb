package entities;

import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

import javax.validation.constraints.Size;
import javax.persistence.GenerationType;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name="Record.findAll", query="select r from Record r")
})
@Table(name="RECORD")
public class Record {

    @Id
    @Getter
    @Setter
    @Column(name="RECORD_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Size(min = 1, max = 10, message = "Name Me must be between 1 and 10 characters")
    @Column(name="NAME")
    private String name;

    @Getter
    @Setter
    @Column(name="ARTIST")
    private String artist;

    @Override
    public String toString() {
        return String.format("Record: {id: %d, name: %s, artist: %s}", id, name, artist);
    }
}
