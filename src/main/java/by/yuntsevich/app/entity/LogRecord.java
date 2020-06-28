package by.yuntsevich.app.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecord implements Serializable {
    private static final long serialVersionUID = -6384175420550732733L;

    private LocalDateTime dateTime;
    private String userName;
    private String message;
}
