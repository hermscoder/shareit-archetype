package ${package}.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ${package}.utils.commons.deserializer.LocalDateDeserializer;
import ${package}.utils.commons.serializer.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
//    private Media profilePicture;
//    private List<Interest> interests;

    public User(String email, String password, String name, LocalDate birthDate) {
        this(null,email, password, name, birthDate);
    }
}
