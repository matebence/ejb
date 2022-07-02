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
        @NamedQuery(name="Car.findAll", query="select c from Car c")
})
@Table(name="CAR")
public class Car {

    @Id
    @Getter
    @Setter
    @Column(name="CAR_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Size(min = 2, max = 10, message = "Brand Me must be between 2 and 10 characters")
    @Column(name="BRAND")
    private String brand;

    @Getter
    @Setter
    @Column(name="TYPE")
    private String type;

    @Override
    public String toString() {
        return String.format("Car: {id: %d, brand: %s, type: %s}", id, brand, type);
    }
}
