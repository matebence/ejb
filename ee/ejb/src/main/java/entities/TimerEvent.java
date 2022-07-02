package entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimerEvent {

    @Getter
    @Setter
    private String info;

    @Override
    public String toString() {
        return String.format("Record: {info: %s}", info);
    }
}
